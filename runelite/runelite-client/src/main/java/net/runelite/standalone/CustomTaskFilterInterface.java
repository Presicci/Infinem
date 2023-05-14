package net.runelite.standalone;

import java.util.List;

public class CustomTaskFilterInterface {

    private static class Item {
        public int itemId;
        public int amount;

        public Item(int itemId, int amount) {
            this.itemId = itemId;
            this.amount = amount;
        }

        public Item(int itemId) {
            this.itemId = itemId;
            this.amount = 1;
        }
    }

    // DIFFICULTY, AREA, COMPLETED,
    // DIFFICULTY, AREA

    public static void init(String[] filters, String sort) {
        Widget infoX = Canvas.get(522, 17);
        if (infoX != null)
            infoX.onClick = null;

        String difficultyFilter = filters[0];
        String areaFilter = filters[1];
        String completedFilter = filters[2];

        Canvas.getWidget(383 << 16 | 1).children[1].text = "Task Filters"; //set title
        Canvas.getWidget(383 << 16 | 2).isHidden = true; //hide "Exchange" button

        Widget parent = Canvas.getWidget(383 << 16 | 3);
        parent.children = null; //reset previous children

        int childId = 0;

        Widget filterLabel = Widget.addChild(parent.id, 4, childId++);
        filterLabel.rawX = 35;
        filterLabel.rawY = 5;
        filterLabel.rawWidth = 380;
        filterLabel.rawHeight = 25;
        filterLabel.fontId = 645;
        filterLabel.textShadowed = true;
        filterLabel.color = 16750623;
        filterLabel.text = "<col=ff9040>Filters";
        filterLabel.textXAlignment = 0;
        filterLabel.textYAlignment = 0;
        WorldMapSectionType.method116(filterLabel);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) filterLabel);

        /* Difficulty */
        Widget difficultyLabel = Widget.addChild(parent.id, 4, childId++);
        difficultyLabel.rawX = 5;
        difficultyLabel.rawY = 45;
        difficultyLabel.rawWidth = 380;
        difficultyLabel.rawHeight = 25;
        difficultyLabel.fontId = 495;
        difficultyLabel.textShadowed = true;
        difficultyLabel.color = 16750623;
        difficultyLabel.text = "<col=ff9040>Difficulty: ";
        difficultyLabel.textXAlignment = 0;
        difficultyLabel.textYAlignment = 0;
        WorldMapSectionType.method116(difficultyLabel);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) difficultyLabel);

        final Widget difficultyBorder = Widget.addChild(parent.id, 3, childId++);
        difficultyBorder.rawX = 74;
        difficultyBorder.rawY = 40;
        difficultyBorder.rawWidth = 52;
        difficultyBorder.rawHeight = 27;
        difficultyBorder.fill = true;
        difficultyBorder.color = 0;
        difficultyBorder.transparencyTop = 100;
        difficultyBorder.hasListener = true;
        WorldMapSectionType.method116(difficultyBorder);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) difficultyBorder);

        final Widget difficultyBackground = Widget.addChild(parent.id, 3, childId++);
        difficultyBackground.rawX = 75;
        difficultyBackground.rawY = 41;
        difficultyBackground.rawWidth = 50;
        difficultyBackground.rawHeight = 25;
        difficultyBackground.fill = true;
        difficultyBackground.dataText = "";
        difficultyBackground.color = 10586218;
        difficultyBackground.setAction(0, "Reset");
        difficultyBackground.setAction(1, "Easy");
        difficultyBackground.setAction(2, "Medium");
        difficultyBackground.setAction(3, "Hard");
        difficultyBackground.setAction(4, "Elite");
        difficultyBackground.setAction(5, "Master");
        difficultyBackground.transparencyTop = 100;
        difficultyBackground.onMouseOver = new Object[]{1015, -2147483645, -2147483643, class12.method154("a88a65", 16), 100};
        difficultyBackground.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, class12.method154("a1886a", 16), 100};
        difficultyBackground.hasListener = true;
        WorldMapSectionType.method116(difficultyBackground);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) difficultyBackground);

        final Widget difficultyText = Widget.addChild(parent.id, 4, childId++);
        difficultyText.rawX = 75;
        difficultyText.rawY = 41;
        difficultyText.rawWidth = 50;
        difficultyText.rawHeight = 25;
        difficultyText.fontId = 496;
        difficultyText.text = "<col=F5DEB3>" + difficultyFilter + "</col>";
        difficultyText.textXAlignment = 1;
        difficultyText.textYAlignment = 1;
        difficultyText.textShadowed = true;
        difficultyText.color = 16758847;
        WorldMapSectionType.method116(difficultyText);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) difficultyText);

        /* Area */
        Widget areaLabel = Widget.addChild(parent.id, 4, childId++);
        areaLabel.rawX = 5;
        areaLabel.rawY = 85;
        areaLabel.rawWidth = 380;
        areaLabel.rawHeight = 25;
        areaLabel.fontId = 495;
        areaLabel.textShadowed = true;
        areaLabel.color = 16750623;
        areaLabel.text = "<col=ff9040>Area: ";
        areaLabel.textXAlignment = 0;
        areaLabel.textYAlignment = 0;
        WorldMapSectionType.method116(areaLabel);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) areaLabel);

        final Widget areaBorder = Widget.addChild(parent.id, 3, childId++);
        areaBorder.rawX = 74;
        areaBorder.rawY = 80;
        areaBorder.rawWidth = 52;
        areaBorder.rawHeight = 27;
        areaBorder.fill = true;
        areaBorder.color = 0;
        areaBorder.transparencyTop = 100;
        areaBorder.hasListener = true;
        WorldMapSectionType.method116(areaBorder);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) areaBorder);

        final Widget areaBackground = Widget.addChild(parent.id, 3, childId++);
        areaBackground.rawX = 75;
        areaBackground.rawY = 81;
        areaBackground.rawWidth = 50;
        areaBackground.rawHeight = 25;
        areaBackground.fill = true;
        areaBackground.dataText = "";
        areaBackground.color = 10586218;
        areaBackground.setAction(0, "Reset");
        areaBackground.setAction(1, "Asgarnia");
        areaBackground.setAction(2, "Fremennik");
        areaBackground.setAction(3, "General");
        areaBackground.setAction(4, "Kandarin");
        areaBackground.setAction(5, "Karamja");
        areaBackground.setAction(6, "Desert");
        areaBackground.setAction(7, "Misthalin");
        areaBackground.setAction(8, "Morytania");
        areaBackground.setAction(9, "Tirannwn");
        areaBackground.setAction(10, "Wilderness");
        areaBackground.setAction(11, "Zeah");
        areaBackground.transparencyTop = 100;
        areaBackground.onMouseOver = new Object[]{1015, -2147483645, -2147483643, class12.method154("a88a65", 16), 100};
        areaBackground.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, class12.method154("a1886a", 16), 100};
        areaBackground.hasListener = true;
        WorldMapSectionType.method116(areaBackground);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) areaBackground);

        final Widget areaText = Widget.addChild(parent.id, 4, childId++);
        areaText.rawX = 75;
        areaText.rawY = 81;
        areaText.rawWidth = 50;
        areaText.rawHeight = 25;
        areaText.fontId = 496;
        areaText.text = "<col=F5DEB3>" + areaFilter + "</col>";
        areaText.textXAlignment = 1;
        areaText.textYAlignment = 1;
        areaText.textShadowed = true;
        areaText.color = 16758847;
        WorldMapSectionType.method116(areaText);
        ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) areaText);

        /**
         * Unlocking
         */
        CustomInterfaceEdits.unlock(parent.id, 0, childId, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);

        /**
         * Refresh
         */
        WorldMapSectionType.method116(parent);
    }

}
