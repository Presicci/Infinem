package net.runelite.standalone;

import java.util.List;

public class CustomTaskInterface {

    private static String getDifficultyName(int points) {
        switch (points) {
            default:
                return "Easy";
            case 50:
                return "Medium";
            case 100:
                return "Hard";
            case 250:
                return "Elite";
            case 500:
                return "Master";
        }
    }

    private static int getDifficultyPoints(Integer diffId) {
        switch (diffId) {
            default:
                return 10;
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 250;
            case 4:
                return 500;
        }
    }

    private static String getAreaName(Integer areaId) {
        switch (areaId) {
            default:
                return "General/Multiple Regions";
            case 1:
                return "Misthalin";
            case 2:
                return "Karamja";
            case 3:
                return "Asgarnia";
            case 4:
                return "Fremennik Provinces";
            case 5:
                return "Kandarin";
            case 6:
                return "Kharidian Desert";
            case 7:
                return "Morytania";
            case 8:
                return "Tirannwn";
            case 9:
                return "Wilderness";
            case 10:
                return "Zeah";
        }
    }

    private static final int EASY_SPRITE = 2316, MEDIUM_SPRITE = 2317, HARD_SPRITE = 2318, ELITE_SPRITE = 2319, MASTER_SPRITE = 2320;

    public static void init(List<String> tasks, List<Integer> pointsList, List<Integer> completeList, List<Integer> areas) {
        Widget infoX = Canvas.get(522, 17);
        if (infoX != null)
            infoX.onClick = null;

        Canvas.getWidget(383 << 16 | 1).children[1].text = "Task List"; //set title
        Canvas.getWidget(383 << 16 | 2).isHidden = true; //hide "Exchange" button

        Widget parent = Canvas.getWidget(383 << 16 | 3);
        parent.children = null; //reset previous children

        /*Collections.sort(drops, new Comparator<Integer[]>() {
            @Override
            public int compare(Integer[] d1, Integer[] d2) {
                int x = d1[4];
                int y = d2[4];
                return (x < y) ? -1 : ((x == y) ? 0 : 1);
            }
        });*/

        int childId = 0;
        for (int i = 0; i < tasks.size(); i++) {
            String name = tasks.get(i);
            int points = getDifficultyPoints(pointsList.get(i));
            boolean completed = completeList.get(i) == 1;
            int areaId = areas.get(i);

            Widget bg = Widget.addChild(parent.id, 3, childId++);
            bg.rawX = 0;
            bg.rawY = i * 25;
            bg.rawWidth = 424;
            bg.rawHeight = 25;
            bg.spriteId = 1040;
            bg.setTextColor(16777215);
            bg.spriteTiling = true;
            bg.fill = true;
            bg.onMouseOver = new Object[]{16777215, -2147483645, -2147483643, 0, 255};
            if (i % 2 == 0) {
                bg.transparencyTop = 255;
                bg.onMouseLeave = new Object[]{16777215, -2147483645, -2147483643, 0, 0};
            } else {
                bg.transparencyTop = 225;
                bg.onMouseLeave = new Object[]{16777215, -2147483645, -2147483643, 0, 125};
            }
            bg.hasListener = true;
            WorldMapSectionType.method116(bg);

            ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) bg);

            /*Widget examine = Widget.addChild(parent.id, 5, childId++);
            examine.rawX = 0;
            examine.rawY = bg.rawY;
            examine.rawWidth = 40;
            examine.rawHeight = bg.rawHeight;
            examine.setAction(0, "<col=ff9040>" + name + "</col>");
            WorldMapSectionType.method116(examine);
            ViewportMouse.client.revalidateWidget(examine);*/

            Widget itemName = Widget.addChild(parent.id, 4, childId++);
            itemName.rawX = 25;
            itemName.rawY = bg.rawY + 5;
            itemName.rawWidth = 380;
            itemName.rawHeight = 25;
            itemName.fontId = 495;
            itemName.textShadowed = true;
            itemName.color = 16750623;
            itemName.text = (completed ? "<col=00FF00>" : "<col=F5DEB3>") + name;
            //itemName.text = "<col=ffb83f>" + name; Orange
            //itemName.text = "<col=00FF00>" + name; Green
            itemName.textXAlignment = 0;
            itemName.textYAlignment = 0;
            WorldMapSectionType.method116(itemName);
            ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) itemName);

            Widget examine = Widget.addChild(parent.id, 5, childId++);
            examine.rawX = 0;
            examine.rawY = bg.rawY;
            examine.rawWidth = 424;
            examine.rawHeight = 25;
            examine.setAction(0, "<col=ff9040>" + name + "</br>" + getAreaName(areaId) + "</br>" + getDifficultyName(points) + " - " + points + " points</col>");
            WorldMapSectionType.method116(examine);
            ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) examine);

            Widget sprite = Widget.addChild(parent.id, 5, childId++);
            sprite.rawX = 1;
            sprite.rawY = bg.rawY+3;
            sprite.rawWidth = 18;
            sprite.rawHeight = 18;
            switch (points) {
                default:
                case 10:
                    sprite.spriteId2 = EASY_SPRITE;
                    break;
                case 50:
                    sprite.spriteId2 = MEDIUM_SPRITE;
                    break;
                case 100:
                    sprite.spriteId2 = HARD_SPRITE;
                    break;
                case 250:
                    sprite.spriteId2 = ELITE_SPRITE;
                    break;
                case 500:
                    sprite.spriteId2 = MASTER_SPRITE;
                    break;
            }
            WorldMapSectionType.method116(sprite);
            ViewportMouse.client.revalidateWidget((net.runelite.api.widgets.Widget) sprite);

            /*Widget value = Widget.addChild(parent.id, 4, childId++);
            value.rawX = 390;
            value.rawY = bg.rawY + 5;
            value.rawWidth = 40;
            value.rawHeight = 10;
            value.fontId = 494;
            value.textShadowed = true;
            value.color = 16750623;
            value.textXAlignment = 1;

            value.text = "<col=ffb83f>" + points;

            WorldMapSectionType.method116(value);
            ViewportMouse.client.revalidateWidget(value);*/

            /*int x = 170;
            for (int c = 0; c < columns.length; c++) {
                /*String s = columns[c];
                Widget column = Widget.addChild(parent.id, 4, childId++);
                column.rawX = x;
                column.rawY = bg.rawY + 7;
                column.rawWidth = 40;
                column.rawHeight = 32;
                column.fontId = 494;
                column.textShadowed = true;
                column.color = 16750623;
                column.textXAlignment = 1;
                column.text = s + ":";
                WorldMapSectionType.method116(column);
                ViewportMouse.client.revalidateWidget(column);
                x += 85;

                Widget value = Widget.addChild(parent.id, 4, childId++);
                value.rawX = column.rawX;
                value.rawY = column.rawY + 12;
                value.rawWidth = column.rawWidth;
                value.rawHeight = 10;
                value.fontId = 494;
                value.textShadowed = true;
                value.color = 16750623;
                value.textXAlignment = 1;

                value.text = "<col=ffb83f>" + points;

                WorldMapSectionType.method116(value);
                ViewportMouse.client.revalidateWidget(value);
            }*/
        }

        /**
         * Scrolling
         */
        parent.scrollY = 0;
        parent.scrollHeight = Math.max(parent.rawHeight, tasks.size() * 25);
        final ScriptEvent scriptevent_0 = new ScriptEvent();
        scriptevent_0.args = new Object[]{31, 25100292, 25100291, 792, 789, 790, 791, 773, 788};
        ParamDefinition.method4313(scriptevent_0);


        /**
         * Unlocking
         */
        CustomInterfaceEdits.unlock(parent.id, 0, childId, 0);

        /**
         * Refresh
         */
        WorldMapSectionType.method116(parent);
    }

}
