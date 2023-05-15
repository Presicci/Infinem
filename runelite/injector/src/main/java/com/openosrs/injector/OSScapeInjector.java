/*
 * Copyright (c) 2019, Lucas <https://github.com/Lucwousin>
 * All rights reserved.
 *
 * This code is licensed under GPL3, see the complete license in
 * the LICENSE file in the root directory of this submodule.
 */
package com.openosrs.injector;

import static net.runelite.deob.util.JarUtil.load;

import com.google.common.hash.Hashing;
import com.openosrs.injector.injection.InjectData;
import com.openosrs.injector.injection.InjectTaskHandler;
import com.openosrs.injector.injectors.CreateAnnotations;
import com.openosrs.injector.injectors.InjectConstruct;
import com.openosrs.injector.injectors.InterfaceInjector;
import com.openosrs.injector.injectors.MixinInjector;
import com.openosrs.injector.injectors.RSApiInjector;
import com.openosrs.injector.injectors.raw.AddPlayerToMenu;
import com.openosrs.injector.injectors.raw.ClearColorBuffer;
import com.openosrs.injector.injectors.raw.DrawMenu;
import com.openosrs.injector.injectors.raw.Occluder;
import com.openosrs.injector.injectors.raw.RasterizerAlpha;
import com.openosrs.injector.injectors.raw.RenderDraw;
import com.openosrs.injector.injectors.raw.ScriptVM;
import com.openosrs.injector.rsapi.RSApi;
import com.openosrs.injector.transformers.InjectTransformer;
import com.openosrs.injector.transformers.Java8Ifier;
import com.openosrs.injector.transformers.SourceChanger;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.util.EnumConverter;
import net.runelite.asm.*;
import net.runelite.asm.signature.util.VirtualMethods;
import net.runelite.deob.DeobAnnotations;
import net.runelite.deob.deobfuscators.Renamer;
import net.runelite.deob.util.JarUtil;
import net.runelite.deob.util.NameMappings;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;

public class OSScapeInjector extends InjectData implements InjectTaskHandler {
  static final Logger log = Logging.getLogger(Injector.class);
  static OSScapeInjector injector = new OSScapeInjector();

  public static void main(String[] args) {
    OptionParser parser = new OptionParser();

    ArgumentAcceptingOptionSpec<File> vanillaFileOption =
        parser
            .accepts("vanilla", "Vanilla OSRS gamepack file")
            .withRequiredArg()
            .ofType(File.class);

    ArgumentAcceptingOptionSpec<String> oprsVerOption =
        parser.accepts("version", "OpenOSRS version").withRequiredArg().ofType(String.class);

    ArgumentAcceptingOptionSpec<File> outFileOption =
        parser
            .accepts("output", "Output file, jar if outmode is jar, folder if outmode is files")
            .withRequiredArg()
            .ofType(File.class);

    ArgumentAcceptingOptionSpec<OutputMode> outModeOption =
        parser
            .accepts("outmode")
            .withRequiredArg()
            .ofType(OutputMode.class)
            .withValuesConvertedBy(
                new EnumConverter<>(OutputMode.class) {
                  @Override
                  public OutputMode convert(String value) {
                    return super.convert(value.toUpperCase());
                  }
                });

    OptionSet options = parser.parse(args);
    String oprsVer = options.valueOf(oprsVerOption);

    File vanillaFile = options.valueOf(vanillaFileOption);
    injector.vanilla = load(vanillaFile);

    final var toRemove = new ArrayList<ClassFile>();
    for (ClassFile classFile : injector.vanilla) {
      if (classFile.getName().contains("runelite")) {
        toRemove.add(classFile);
      }
    }
    toRemove.forEach(injector.vanilla::removeClass);

    injector.deobfuscated =
        load(new File("../runescape-client/build/libs/runescape-client-" + oprsVer + ".jar"));

    final var mappings = new NameMappings();
    var nameIndex = 0;
    for (ClassFile classFile : injector.vanilla) {
      for (Method method : classFile.getMethods()) {
        final var nameAnn = method.getAnnotations().get(DeobAnnotations.OBFUSCATED_NAME);
        if (nameAnn == null) {
          continue;
        }
        var mapped = mappings.get(method.getPoolMethod());
        if (mapped == null) {
          mapped = "_" + nameIndex;
          nameIndex++;
        }
        final var virtualMethods = VirtualMethods.getVirtualMethods(method);
        for (Method virtualMethod : virtualMethods) {
          final var deobAnnotation =
              injector
                  .deobfuscated
                  .findClass(virtualMethod.getClassFile().getName())
                  .findMethod(virtualMethod.getName(), virtualMethod.getDescriptor())
                  .getAnnotations()
                  .get(DeobAnnotations.OBFUSCATED_NAME);
          deobAnnotation.setElement("value", mapped);
          final var renamedName = deobAnnotation.getValueString();
          mappings.map(virtualMethod.getPoolMethod(), renamedName);
        }
      }
    }
    final var renamer = new Renamer(mappings);
    renamer.run(injector.vanilla);

    for (ClassFile classFile : injector.deobfuscated) {
      {
        final var annotations = classFile.getAnnotations();
        final var obfuscatedName = annotations.get(DeobAnnotations.OBFUSCATED_NAME);
        if (obfuscatedName != null) {
          obfuscatedName.setElement("value", classFile.getName());
        }
      }
      {
        for (Method method : classFile.getMethods()) {
          final var annotations = method.getAnnotations();
          final var obfuscatedSignature = annotations.get(DeobAnnotations.OBFUSCATED_SIGNATURE);
          if (obfuscatedSignature != null) {
            obfuscatedSignature.setElement("descriptor", method.getDescriptor().toString());
          }
        }
      }
      for (Field field : classFile.getFields()) {
        final var annotations = field.getAnnotations();
        final var obfuscatedName = annotations.get(DeobAnnotations.OBFUSCATED_NAME);
        if (obfuscatedName != null) {
          obfuscatedName.setElement("value", field.getName());
        }
        final var obfuscatedSignature = annotations.get(DeobAnnotations.OBFUSCATED_SIGNATURE);
        if (obfuscatedSignature != null) {
          obfuscatedSignature.setElement("descriptor", field.getType().toString());
        }
        final var obfuscatedGetter = annotations.get(DeobAnnotations.OBFUSCATED_GETTER);
        if (obfuscatedGetter != null) {
          annotations.remove(DeobAnnotations.OBFUSCATED_GETTER);
        }
      }
    }

    injector.rsApi =
        new RSApi(
            Objects.requireNonNull(
                new File("../runescape-api/build/classes/java/main/net/runelite/rs/api/")
                    .listFiles()));
    injector.mixins =
        load(new File("../runelite-mixins/build/libs/runelite-mixins-" + oprsVer + ".jar"));

    File oldInjected =
        new File("../runelite-client/src/main/resources/net/runelite/client/injected-client.oprs");
    if (oldInjected.exists()) {
      oldInjected.delete();
    }

    injector.initToVanilla();
    injector.injectVanilla();
    save(
        injector.getVanilla(),
        options.valueOf(outFileOption),
        options.valueOf(outModeOption),
        vanillaFile);
  }

  public void injectVanilla() {
    log.debug("[DEBUG] Starting injection");

    transform(new Java8Ifier(this));

    inject(new CreateAnnotations(this));

    inject(new InterfaceInjector(this));

    inject(new RasterizerAlpha(this));

    inject(new MixinInjector(this));

    // This is where field hooks runs

    // This is where method hooks runs

    inject(new InjectConstruct(this));

    inject(new RSApiInjector(this));

    // inject(new DrawAfterWidgets(this));

    inject(new ScriptVM(this));

    // All GPU raw injectors should probably be combined, especially RenderDraw and Occluder
    inject(new ClearColorBuffer(this));

    inject(new RenderDraw(this));

    inject(new Occluder(this));

    inject(new DrawMenu(this));

    inject(new AddPlayerToMenu(this));

    validate(new InjectorValidator(this));

    transform(new SourceChanger(this));
  }

  private void inject(com.openosrs.injector.injectors.Injector injector) {
    final String name = injector.getName();

    log.lifecycle("[INFO] Starting {}", name);

    injector.start();

    injector.inject();

    log.lifecycle("{} {}", name, injector.getCompletionMsg());

    if (injector instanceof Validator) {
      validate((Validator) injector);
    }
  }

  private void validate(Validator validator) {
    final String name = validator.getName();

    if (!validator.validate()) {
      throw new InjectException(name + " failed validation");
    }
  }

  private void transform(InjectTransformer transformer) {
    final String name = transformer.getName();

    log.info("[INFO] Starting {}", name);

    transformer.transform();

    log.lifecycle("{} {}", name, transformer.getCompletionMsg());
  }

  private static void save(ClassGroup group, File output, OutputMode mode, File vanillaFile) {
    if (output.exists()) {
      try {
        Files.walk(output.toPath())
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
      } catch (IOException e) {
        log.lifecycle("Failed to delete output directory contents.");
        throw new RuntimeException(e);
      }
    }

    switch (mode) {
      case FILES:
        saveFiles(group, output);
        break;
      case JAR:
        output.getParentFile().mkdirs();
        JarUtil.save(group, output);
        break;
    }

    try {
      String hash =
          com.google.common.io.Files.asByteSource(vanillaFile).hash(Hashing.sha256()).toString();
      log.lifecycle("Writing vanilla hash: {}", hash);
      Files.write(
          output.getParentFile().toPath().resolve("client.hash"),
          hash.getBytes(StandardCharsets.UTF_8));
    } catch (IOException ex) {
      log.lifecycle("Failed to write vanilla hash file");
      throw new RuntimeException(ex);
    }
  }

  private static void saveFiles(ClassGroup group, File outDir) {
    try {
      outDir.mkdirs();

      for (ClassFile cf : group.getClasses()) {
        File f = new File(outDir, cf.getName() + ".class");
        byte[] data = JarUtil.writeClass(group, cf);
        Files.write(f.toPath(), data);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void runChildInjector(com.openosrs.injector.injectors.Injector injector) {
    inject(injector);
  }
}
