/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package net.runelite.client.plugins.donator;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.ClientUI;
import net.runelite.client.ui.DrawManager;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import javax.inject.Named;
import java.awt.image.BufferedImage;
import java.util.concurrent.ScheduledExecutorService;

import static net.runelite.client.RuneLite.SCREENSHOT_DIR;

@PluginDescriptor(
        name = "Donator",
        description = "Enables the Donator store icon",
        tags = {"donator", "store", "donate"}
)
@Slf4j
public class DonatorPlugin extends Plugin {

    @Inject
    private DonatorConfig config;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private Client client;

    @Inject
    private ClientUI clientUi;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private DrawManager drawManager;

    @Inject
    private ScheduledExecutorService executor;
    @com.google.inject.Inject
    @Named("runelite.store")
    private String openStore;
    @Inject
    private KeyManager keyManager;

    @Getter(AccessLevel.PACKAGE)
    private BufferedImage reportButton;

    private NavigationButton titleBarButton;

    private String kickPlayerName;

    @Provides
    DonatorConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(DonatorConfig.class);
    }

    @Override
    protected void startUp() throws Exception {
        SCREENSHOT_DIR.mkdirs();

        final BufferedImage iconImage = ImageUtil.loadImageResource(getClass(), "5.png");

        titleBarButton = NavigationButton.builder()
                .tab(false)
                .tooltip("Open Store")
                .icon(iconImage)
                .onClick(() -> LinkBrowser.browse(openStore))
                .build();

        clientToolbar.addNavigation(titleBarButton);
    }

    @Override
    protected void shutDown() throws Exception {
        clientToolbar.removeNavigation(titleBarButton);
        kickPlayerName = null;
    }
}
