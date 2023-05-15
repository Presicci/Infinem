/*
 * Copyright (c) 2021, MakingStan
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
package net.runelite.client.plugins.code.overlay;

import net.runelite.client.plugins.code.BoredPanel;
import net.runelite.api.Client;
import net.runelite.client.plugins.code.BoredConfig;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;

//an overlay to show the current progress of the current task.
public class BoredOverlay extends Overlay {
    private final Client client;
    private final PanelComponent panelComponent = new PanelComponent();

    @Inject
    BoredConfig config;

    @Override
    public Dimension render(Graphics2D graphics) {
            panelComponent.getChildren().clear();
            String overlayTitle = "Current task:";

            // Build overlay title (Current task:)
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text(overlayTitle)
                    .color(Color.GREEN)
                    .build());

            // Set the size of the overlay (width)
            panelComponent.setPreferredSize(new Dimension(
                    graphics.getFontMetrics().stringWidth(overlayTitle) + 200,
                    80));

            panelComponent.getChildren().add(LineComponent.builder()
                    .right(String.valueOf(BoredPanel.points.getText()))
                    .left(BoredPanel.explain.getText())
                    .build());

            if(config.overlay()) {
                return panelComponent.render(graphics);
            } else {
                return null;
            }


    }
    @Inject
    private BoredOverlay(Client client)  {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        this.client = client;
    }
}
