package net.runelite.standalone;

import java.util.Arrays;
import java.util.List;

public class CustomTaskInterface {
    private static final int EASY_SPRITE = 2316, MEDIUM_SPRITE = 2317, HARD_SPRITE = 2318, ELITE_SPRITE = 2319, MASTER_SPRITE = 2320;

    public static void init(List<String> tasks, List<Integer> pointsList, List<Integer> completeList, List<Integer> areas, int playerPoints, int areaFilter, int skillFilter, int tierFilter, int completedFilter, int sortBy) {
        Widget infoX = Canvas.get(522, 17);
        if (infoX != null)
            infoX.onClick = null;

        Canvas.getWidget(383 << 16 | 1).children[1].text = ""; //set title
        Canvas.getWidget(383 << 16 | 2).isHidden = true; //hide "Exchange" button

        Widget parent = Canvas.getWidget(383 << 16 | 3);
        Widget filterParent = Canvas.getWidget(383 << 16 | 1);
        parent.children = null; //reset previous children

        /*Widget progressBarDivider = Widget.addChild(filterParent.id, 5, 12);
        progressBarDivider.rawX = 5;
        progressBarDivider.rawY = 50;
        progressBarDivider.rawWidth = 450;
        progressBarDivider.rawHeight = 36;
        progressBarDivider.spriteId2 = 173;
        progressBarDivider.spriteTiling = true;
        WorldMapSectionType.method116(progressBarDivider);
        ViewportMouse.client.revalidateWidget(progressBarDivider);*/
        int filterChildId = 12;

        /**
         * Vertical divider
         */
        Widget progressBarDivider = Widget.addChild(filterParent.id, 5, filterChildId++);
        progressBarDivider.rawX = 71;
        progressBarDivider.rawY = 33;
        progressBarDivider.rawWidth = 36;
        progressBarDivider.rawHeight = 261;
        progressBarDivider.spriteId2 = 315;
        progressBarDivider.spriteTiling = true;
        WorldMapSectionType.method116(progressBarDivider);
        ViewportMouse.client.revalidateWidget(progressBarDivider);

        filterChildId = addProgressBar(filterParent, filterChildId, playerPoints);
        filterChildId = addSearchButton(filterParent, filterChildId);

        filterChildId = addFilterElement(filterParent, filterChildId, "Region:", 50, getAreaFilterText(areaFilter));
        filterChildId = addFilterElement(filterParent, filterChildId, "Skill:", 90, getSkillFilterText(skillFilter));
        filterChildId = addFilterElement(filterParent, filterChildId, "Tier:", 130, getTierFilterText(tierFilter));
        filterChildId = addFilterElement(filterParent, filterChildId, "Completed:", 170, getCompletedFilterText(completedFilter));
        filterChildId = addFilterElement(filterParent, filterChildId, "Sort by:", 210, getSortText(sortBy));

        int childId = 0;
        int xOffset = 82;
        for (int i = 0; i < tasks.size(); i++) {
            String name = tasks.get(i);
            int points = getDifficultyPoints(pointsList.get(i));
            boolean completed = completeList.get(i) == 1;
            int areaId = areas.get(i);

            Widget bg = Widget.addChild(parent.id, 3, childId++);
            bg.rawX = xOffset;
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
            ViewportMouse.client.revalidateWidget(bg);

            Widget itemName = Widget.addChild(parent.id, 4, childId++);
            itemName.rawX = xOffset + 25;
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
            ViewportMouse.client.revalidateWidget(itemName);

            Widget examine = Widget.addChild(parent.id, 5, childId++);
            examine.rawX = xOffset;
            examine.rawY = bg.rawY;
            examine.rawWidth = 424;
            examine.rawHeight = 25;
            examine.setAction(0, "<col=ff9040>" + name + "</br>" + getAreaName(areaId) + "</br>" + getDifficultyName(points) + " - " + points + " points</col>");
            WorldMapSectionType.method116(examine);
            ViewportMouse.client.revalidateWidget(examine);

            Widget sprite = Widget.addChild(parent.id, 5, childId++);
            sprite.rawX = xOffset + 1;
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
            ViewportMouse.client.revalidateWidget(sprite);
        }

        Widget areaParent = Canvas.getWidget(383 << 16 | 5);
        areaParent.isHidden = true;
        int areaChildren = drawPopup(areaParent, Arrays.asList("All", "General", "Asgarnia", "Fremennik", "Kandarin", "Karamja", "Desert", "Misthalin", "Morytania", "Tirannwn", "Wilderness", "Zeah"), 54);

        Widget skillParent = Canvas.getWidget(383 << 16 | 6);
        skillParent.isHidden = true;
        int skillChildren = drawPopup(skillParent, Arrays.asList("All", "Combat", "Fishing", "Woodcutting", "Construction"), 94);

        Widget tierParent = Canvas.getWidget(383 << 16 | 7);
        tierParent.isHidden = true;
        int tierChildren = drawPopup(tierParent, Arrays.asList("All", "Easy", "Medium", "Hard", "Elite", "Master"), 134);

        Widget completedParent = Canvas.getWidget(383 << 16 | 8);
        completedParent.isHidden = true;
        int completedChildren = drawPopup(completedParent, Arrays.asList("All", "Completed", "Uncompleted"), 174);

        Widget sortParent = Canvas.getWidget(383 << 16 | 9);
        sortParent.isHidden = true;
        int sortChildren = drawPopup(sortParent, Arrays.asList("Region", "Tier", "Name"), 214);

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
        CustomInterfaceEdits.unlock(filterParent.id, 12, filterChildId, 0);
        CustomInterfaceEdits.unlock(areaParent.id, 0, areaChildren, 0);
        CustomInterfaceEdits.unlock(tierParent.id, 0, tierChildren, 0);
        CustomInterfaceEdits.unlock(skillParent.id, 0, skillChildren, 0);
        CustomInterfaceEdits.unlock(completedParent.id, 0, completedChildren, 0);
        CustomInterfaceEdits.unlock(sortParent.id, 0, sortChildren, 0);

        /**
         * Refresh
         */
        WorldMapSectionType.method116(parent);
        ViewportMouse.client.revalidateWidget(parent);
    }

    private static int drawPopup(Widget popupParent, List<String> elements, int originY) {
        int childId = 0;
        Widget border = Widget.addChild(popupParent.id, 3, childId++);
        border.rawX = 5;
        border.rawY = originY + 16;
        border.rawWidth = 80;
        border.rawHeight = (elements.size() * 18) + 4;
        border.color = 0;
        border.color2 = 0;
        border.fill = true;
        WorldMapSectionType.method116(border);
        ViewportMouse.client.revalidateWidget(border);

        Widget background = Widget.addChild(popupParent.id, 5, childId++);
        background.rawX = 7;
        background.rawY = originY + 18;
        background.rawWidth = 76;
        background.rawHeight = elements.size() * 18;
        background.spriteId2 = 297;
        background.fill = true;
        WorldMapSectionType.method116(background);
        ViewportMouse.client.revalidateWidget(background);

        int child = 0;
        for (String name : elements) {
            Widget text = Widget.addChild(popupParent.id, 4, childId++);
            text.rawX = 8;
            text.rawY = background.rawY + (child * 18);
            text.rawWidth = 76;
            text.rawHeight = 20;
            text.fontId = 494;
            text.textShadowed = true;
            text.color = 16750623;
            text.text = name;
            text.textXAlignment = 0;
            text.textYAlignment = 1;
            WorldMapSectionType.method116(text);
            ViewportMouse.client.revalidateWidget(text);

            Widget clickbox = Widget.addChild(popupParent.id, 5, childId++);
            clickbox.rawX = 8;
            clickbox.rawY = background.rawY + (child * 18);
            clickbox.rawWidth = 80;
            clickbox.rawHeight = 20;
            clickbox.hasListener = true;
            clickbox.clickMask = 0;
            clickbox.noClickThrough = true;
            clickbox.setAction(0, "Filter by");
            clickbox.dataText = name;
            WorldMapSectionType.method116(clickbox);
            ViewportMouse.client.revalidateWidget(clickbox);
            ++child;
        }
        return childId;
    }

    private static int addFilterElement(Widget filterParent, int filterChildId, String name, int y, String currentSelection) {
        int x = 5;
        Widget areaFilterLabel = Widget.addChild(filterParent.id, 4, filterChildId++);
        areaFilterLabel.rawX = x + 5;
        areaFilterLabel.rawY = y - 12;
        areaFilterLabel.rawWidth = 82;
        areaFilterLabel.rawHeight = 23;
        areaFilterLabel.fontId = 494;
        areaFilterLabel.textShadowed = true;
        areaFilterLabel.color = 14069891;
        areaFilterLabel.text = name;
        WorldMapSectionType.method116(areaFilterLabel);
        ViewportMouse.client.revalidateWidget(areaFilterLabel);

        Widget areaFilterBackground = Widget.addChild(filterParent.id, 3, filterChildId++);
        areaFilterBackground.rawX = x;
        areaFilterBackground.rawY = y;
        areaFilterBackground.rawWidth = 82;
        areaFilterBackground.rawHeight = 23;
        areaFilterBackground.color = 3024925;
        areaFilterBackground.color2 = 3024925;
        areaFilterBackground.fill = true;
        WorldMapSectionType.method116(areaFilterBackground);
        ViewportMouse.client.revalidateWidget(areaFilterBackground);

        Widget areaFilterBorder = Widget.addChild(filterParent.id, 3, filterChildId++);
        areaFilterBorder.rawX = x;
        areaFilterBorder.rawY = y;
        areaFilterBorder.rawWidth = 82;
        areaFilterBorder.rawHeight = 23;
        areaFilterBorder.color = 5523508;
        areaFilterBorder.color2 = 5523508;
        areaFilterBorder.fill = false;
        WorldMapSectionType.method116(areaFilterBorder);
        ViewportMouse.client.revalidateWidget(areaFilterBorder);

        Widget areaFilterText = Widget.addChild(filterParent.id, 4, filterChildId++);
        areaFilterText.rawX = x + 5;
        areaFilterText.rawY = y + 5;
        areaFilterText.rawWidth = 57;
        areaFilterText.rawHeight = 23;
        areaFilterText.fontId = 495;
        areaFilterText.textShadowed = true;
        areaFilterText.color = 14069891;
        areaFilterText.text = currentSelection;
        areaFilterText.textXAlignment = 1;  // Center text
        WorldMapSectionType.method116(areaFilterText);
        ViewportMouse.client.revalidateWidget(areaFilterText);

        Widget areaFilterButton = Widget.addChild(filterParent.id, 5, filterChildId++);
        areaFilterButton.rawX = x + 64;
        areaFilterButton.rawY = y + 4;
        areaFilterButton.rawWidth = 16;
        areaFilterButton.rawHeight = 16;
        areaFilterButton.spriteId2 = 788;
        areaFilterButton.hasListener = true;
        areaFilterButton.fill = true;
        areaFilterButton.setAction(0, "Select");
        WorldMapSectionType.method116(areaFilterButton);
        ViewportMouse.client.revalidateWidget(areaFilterButton);
        return filterChildId;
    }

    private static int addProgressBar(Widget filterParent, int filterChildId, int points) {
        Widget progressBarBackground = Widget.addChild(filterParent.id, 3, filterChildId++);
        progressBarBackground.rawX = 9;
        progressBarBackground.rawY = 8;
        progressBarBackground.rawWidth = 410;
        progressBarBackground.rawHeight = 18;
        progressBarBackground.color = 6512736;
        progressBarBackground.color2 = 6512736;
        progressBarBackground.fill = true;
        WorldMapSectionType.method116(progressBarBackground);
        ViewportMouse.client.revalidateWidget(progressBarBackground);

        Widget progressBarFill = Widget.addChild(filterParent.id, 3, filterChildId++);
        progressBarFill.rawX = 9;
        progressBarFill.rawY = 8;
        progressBarFill.rawWidth = getProgressWidth(points, 410);
        progressBarFill.rawHeight = 18;
        progressBarFill.color = 3452858;
        progressBarFill.color2 = 3452858;
        progressBarFill.fill = true;
        WorldMapSectionType.method116(progressBarFill);
        ViewportMouse.client.revalidateWidget(progressBarFill);

        Widget progressBarShadow = Widget.addChild(filterParent.id, 3, filterChildId++);
        progressBarShadow.rawX = 9;
        progressBarShadow.rawY = 24;
        progressBarShadow.rawWidth = 410;
        progressBarShadow.rawHeight = 2;
        progressBarShadow.color = 0;
        progressBarShadow.color2 = 0;
        progressBarShadow.transparencyTop = 200;
        progressBarShadow.fill = true;
        WorldMapSectionType.method116(progressBarShadow);
        ViewportMouse.client.revalidateWidget(progressBarShadow);

        Widget progressBarHighlight = Widget.addChild(filterParent.id, 3, filterChildId++);
        progressBarHighlight.rawX = 9;
        progressBarHighlight.rawY = 8;
        progressBarHighlight.rawWidth = 410;
        progressBarHighlight.rawHeight = 2;
        progressBarHighlight.color = 16777215;
        progressBarHighlight.color2 = 16777215;
        progressBarHighlight.transparencyTop = 200;
        progressBarHighlight.fill = true;
        WorldMapSectionType.method116(progressBarHighlight);
        ViewportMouse.client.revalidateWidget(progressBarHighlight);

        Widget progressBarBorder = Widget.addChild(filterParent.id, 3, filterChildId++);
        progressBarBorder.rawX = 8;
        progressBarBorder.rawY = 7;
        progressBarBorder.rawWidth = 412;
        progressBarBorder.rawHeight = 20;
        progressBarBorder.color = 0;
        progressBarBorder.color2 = 0;
        progressBarBorder.fill = false;
        WorldMapSectionType.method116(progressBarBorder);
        ViewportMouse.client.revalidateWidget(progressBarBorder);

        Widget progressBarText = Widget.addChild(filterParent.id, 4, filterChildId++);
        progressBarText.rawX = 10;
        progressBarText.rawY = 8;
        progressBarText.rawWidth = 403;
        progressBarText.rawHeight = 20;
        progressBarText.fontId = 495;
        progressBarText.textShadowed = true;
        progressBarText.color = 16777215;
        progressBarText.text = getProgressString(points);
        progressBarText.textXAlignment = 1;  // Center text
        progressBarText.textYAlignment = 1;  // Center text
        WorldMapSectionType.method116(progressBarText);
        ViewportMouse.client.revalidateWidget(progressBarText);

        Widget progressSprite = Widget.addChild(filterParent.id, 5, filterChildId++);
        progressSprite.rawX = 400;
        progressSprite.rawY = 2;
        progressSprite.rawWidth = 30;
        progressSprite.rawHeight = 30;
        progressSprite.spriteId2 = 4114;
        progressSprite.hasListener = true;
        progressSprite.setAction(0, "View Relics");
        WorldMapSectionType.method116(progressSprite);
        ViewportMouse.client.revalidateWidget(progressSprite);
        return filterChildId;
    }

    private static int addSearchButton(Widget filterParent, int filterChildId) {
        Widget background = Widget.addChild(filterParent.id, 5, filterChildId++);
        background.rawX = 6;
        background.rawY = 263;
        background.rawWidth = 80;
        background.rawHeight = 30;
        background.spriteId2 = 297;
        background.fill = true;
        WorldMapSectionType.method116(background);
        ViewportMouse.client.revalidateWidget(background);

        Widget topleft = Widget.addChild(filterParent.id, 5, filterChildId++);
        topleft.rawX = 5;
        topleft.rawY = 262;
        topleft.rawWidth = 9;
        topleft.rawHeight = 9;
        topleft.spriteId2 = 913;
        WorldMapSectionType.method116(topleft);
        ViewportMouse.client.revalidateWidget(topleft);

        Widget topright = Widget.addChild(filterParent.id, 5, filterChildId++);
        topright.rawX = 78;
        topright.rawY = 262;
        topright.rawWidth = 9;
        topright.rawHeight = 9;
        topright.spriteId2 = 914;
        WorldMapSectionType.method116(topright);
        ViewportMouse.client.revalidateWidget(topright);

        Widget bottomleft = Widget.addChild(filterParent.id, 5, filterChildId++);
        bottomleft.rawX = 5;
        bottomleft.rawY = 285;
        bottomleft.rawWidth = 9;
        bottomleft.rawHeight = 9;
        bottomleft.spriteId2 = 915;
        WorldMapSectionType.method116(bottomleft);
        ViewportMouse.client.revalidateWidget(bottomleft);

        Widget bottomright = Widget.addChild(filterParent.id, 5, filterChildId++);
        bottomright.rawX = 78;
        bottomright.rawY = 285;
        bottomright.rawWidth = 9;
        bottomright.rawHeight = 9;
        bottomright.spriteId2 = 916;
        WorldMapSectionType.method116(bottomright);
        ViewportMouse.client.revalidateWidget(bottomright);

        Widget left = Widget.addChild(filterParent.id, 5, filterChildId++);
        left.rawX = 5;
        left.rawY = 270;
        left.rawWidth = 9;
        left.rawHeight = 14;
        left.spriteId2 = 917;
        WorldMapSectionType.method116(left);
        ViewportMouse.client.revalidateWidget(left);

        Widget right = Widget.addChild(filterParent.id, 5, filterChildId++);
        right.rawX = 78;
        right.rawY = 270;
        right.rawWidth = 9;
        right.rawHeight = 14;
        right.spriteId2 = 919;
        WorldMapSectionType.method116(right);
        ViewportMouse.client.revalidateWidget(right);

        Widget top = Widget.addChild(filterParent.id, 5, filterChildId++);
        top.rawX = 13;
        top.rawY = 262;
        top.rawWidth = 65;
        top.rawHeight = 9;
        top.spriteId2 = 918;
        WorldMapSectionType.method116(top);
        ViewportMouse.client.revalidateWidget(top);

        Widget bottom = Widget.addChild(filterParent.id, 5, filterChildId++);
        bottom.rawX = 13;
        bottom.rawY = 285;
        bottom.rawWidth = 65;
        bottom.rawHeight = 9;
        bottom.spriteId2 = 920;
        WorldMapSectionType.method116(bottom);
        ViewportMouse.client.revalidateWidget(bottom);

        Widget text = Widget.addChild(filterParent.id, 4, filterChildId++);
        text.rawX = 5;
        text.rawY = 262;
        text.rawWidth = 82;
        text.rawHeight = 32;
        text.fontId = 495;
        text.textShadowed = true;
        text.color = 14069891;
        text.text = "Search";
        text.textXAlignment = 1;  // Center text
        text.textYAlignment = 1;  // Center text
        WorldMapSectionType.method116(text);
        ViewportMouse.client.revalidateWidget(text);

        Widget button = Widget.addChild(filterParent.id, 3, filterChildId++);
        button.rawX = 5;
        button.rawY = 262;
        button.rawWidth = 82;
        button.rawHeight = 32;
        button.hasListener = true;
        button.setAction(0, "Search");
        button.isIf3 = true;
        button.fill = true;
        button.transparencyTop = 255;
        button.onMouseOver = new Object[]{1015, -2147483645, -2147483643, class12.method154("FFFFFF", 16), 236};
        button.onMouseLeave = new Object[]{1015, -2147483645, -2147483643, 16777215,  255};
        WorldMapSectionType.method116(button);
        ViewportMouse.client.revalidateWidget(button);
        return filterChildId;
    }

    public static final int[] tierRequirements = {
            15000,
            7500,
            4000,
            2000,
            500,
            0
    };

    private static String getProgressString(int points) {
        for (int index = 0; index < tierRequirements.length; index++) {
            if (points > tierRequirements[index]) {
                if (index == 0) {
                    return points + " Points";
                }
                return points + " / " + tierRequirements[index - 1] + " Points until next Relic";
            }
        }
        return points + " Points";
    }

    private static int getProgressWidth(int points, int maxWidth) {
        for (int index = 0; index < tierRequirements.length; index++) {
            if (points > tierRequirements[index]) {
                if (index == 0) {
                    return maxWidth;
                }
                points -= tierRequirements[index];
                return (int) (maxWidth * ((double) points / (double) (tierRequirements[index-1] - tierRequirements[index])));
            }
        }
        return 0;
    }

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
                return "Asgarnia";
            case 2:
                return "Fremennik Provinces";
            case 3:
                return "Kandarin";
            case 4:
                return "Karamja";
            case 5:
                return "Kharidian Desert";
            case 6:
                return "Morytania";
            case 7:
                return "Misthalin";
            case 8:
                return "Tirannwn";
            case 9:
                return "Wilderness";
            case 10:
                return "Zeah";
        }
    }

    private static String getAreaFilterText(Integer index) {
        switch (index) {
            default:
                return "All";
            case 1:
                return "General";
            case 2:
                return "Asgarnia";
            case 3:
                return "Fremennik";
            case 4:
                return "Kandarin";
            case 5:
                return "Karamja";
            case 6:
                return "Desert";
            case 8:
                return "Morytania";
            case 7:
                return "Misthalin";
            case 9:
                return "Tirannwn";
            case 10:
                return "Wilderness";
            case 11:
                return "Zeah";
        }
    }

    private static String getSkillFilterText(Integer index) {
        switch (index) {
            default:
                return "All";
            case 1:
                return "Combat";
            case 5:
                return "Fishing";
            case 2:
                return "Woodcutting";
            case 3:
                return "Construction";
        }
    }

    private static String getTierFilterText(int index) {
        switch (index) {
            default:
                return "All";
            case 1:
                return "Easy";
            case 2:
                return "Medium";
            case 3:
                return "Hard";
            case 4:
                return "Elite";
            case 5:
                return "Master";
        }
    }

    private static String getCompletedFilterText(int index) {
        switch (index) {
            default:
                return "All";
            case 1:
                return "Completed";
            case 2:
                return "Uncompleted";
        }
    }

    private static String getSortText(int index) {
        switch (index) {
            default:
                return "Region";
            case 1:
                return "Tier";
            case 2:
                return "Name";
        }
    }

}
