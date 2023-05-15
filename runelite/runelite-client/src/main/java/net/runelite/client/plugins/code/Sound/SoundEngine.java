/*BSD 2-Clause License

Copyright (c) 2021, m0bilebtw <github:https://github.com/m0bilebtw>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.runelite.client.plugins.code.Sound;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Singleton
@Slf4j
public class SoundEngine {

    private static final long CLIP_MTIME_UNLOADED = -2;

    private long lastClipMTime = CLIP_MTIME_UNLOADED;
    private Clip clip = null;

    private boolean loadClip(SoundEnum sound) {
        try (InputStream resourceStream = SoundEngine.class.getResourceAsStream(sound.getResourceName())) {
            if (resourceStream == null) {
                log.warn("Failed to load C Engineer sound " + sound + " as resource stream was null!");
            } else {
                try (InputStream fileStream = new BufferedInputStream(resourceStream);
                     AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(fileStream)) {
                    clip.open(audioInputStream); // liable to error with pulseaudio, works on windows, no clue about mac
                }
                return true;
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            log.warn("Failed to load C Engineer sound " + sound, e);
        }
        return false;
    }

    public void playClip(SoundEnum sound) {
        long currentMTime = System.currentTimeMillis();
        if (clip == null || currentMTime != lastClipMTime || !clip.isOpen()) {
            if (clip != null && clip.isOpen()) {
                clip.close();
            }

            try {
                clip = AudioSystem.getClip();
            } catch (LineUnavailableException e) {
                lastClipMTime = CLIP_MTIME_UNLOADED;
                log.warn("Failed to get clip for C Engineer sound " + sound, e);
                return;
            }

            lastClipMTime = currentMTime;
            if (!loadClip(sound)) {
                return;
            }
        }

        // From RuneLite base client Notifier class:
        // Using loop instead of start + setFramePosition prevents the clip
        // from not being played sometimes, presumably a race condition in the
        // underlying line driver
        clip.loop(0);
    }
}
