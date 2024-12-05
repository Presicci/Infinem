package io.ruin.model.map.object.actions.impl.varlamore;

import io.ruin.model.entity.player.Player;
import io.ruin.model.inter.InterfaceType;
import io.ruin.model.map.object.actions.ObjectAction;
import lombok.AllArgsConstructor;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 11/3/2024
 */
public class GrandMuseum {

    @AllArgsConstructor
    private enum MuseumDisplay {
        ARMOUR_OF_THE_CLOUD_TITAN(
                new int[] { 52707 },
                "Armour of the Cloud Titan",
                52365,
                "The armour on display here was once worn by the Cloud Titan that inhabited the islands of Tempest Bay.<br><br>Despite the Cloud " +
                        "Titan being defeated around 900 years ago, the armour was not found until roughly 700 years later when it was donated to the museum " +
                        "by a traveller.<br><br>The traveller claimed to have slain the Cloud Titan himself, with the giant having survived its original defeat. " +
                        "However, experts consider this unlikely.",
                0, 400, 0, 1938, 0, 900, false),
        FANG_OF_THE_FOLOSSAL_WYRM(
                new int[] { 52709 },
                "Fang of the Colossal Wyrm",
                52356,
                "On display here is a fang from the Colossal Wyrm. The wyrm is believed to have originated from deep within the Hailstorm Mountains, " +
                        "but for unknown reasons, it travelled to the Avium Savannah around 200 years ago. The wyrm terrorised the region for many moons before " +
                        "warriors from across the kingdom finally banded together to defeat it.",
                -15, 140, 0, 1938, 0, 620, false),
        OLD_ONE_STATUE(
                new int[] { 52708 },
                "Old One Statue",
                52350,
                "On display here are various pieces of an Old One statue. The Old Ones were a race that inhabited Varlamore hundreds of years before the " +
                        "first human settlers arrived here, with them being strong worshippers of Ralos and Ranul.<br><br>This statue is believed to depict a spirit " +
                        "called a nagua. It is said that the Old Ones summoned these spirits for various reasons via a magical art called naguanil.",
                -10, 190, 0, 1938, 0, 800, false),
        ROYAL_ACCORD_OF_TWILL(
                new int[] { 52702 },
                "Royal Accord of Twill",
                52362,
                "The Royal Accord of Twill is an ancient document detailing the transfer of power from Kourend's Council of Elders to the Five Houses.<br><br>" +
                        "Following the War of Betrayal, King Kharedst III of Kourend arranged for the original document to be displayed here in the Grand Museum. However, " +
                        "a naval disaster resulted in the loss of the accord. As such, the document shown here is a replica.",
                -3, 135, 0, 1938, 0, 560, false),
        ICON_OF_RALOS(
                new int[] { 52703 },
                "Icon of Ralos",
                52368,
                "The icon on display here is believed to be the oldest icon of Ralos in existence. The icon predates the founding of Fortis by hundreds of years and " +
                        "acts as a reminder of just how long Ralos has been worshipped.<br><br>While the origin of the icon has long been debated, most historians now agree " +
                        "that it came to Varlamore alongside early settlers from Kourend.",
                -3, 135, 0, 1938, 0, 560, false),
        HISTORY_OF_KOUREND(
                new int[] { 52704 },
                "History of Kourend",
                52420,
                "The column on display here is one of two built at the end of the War of Betrayal. This column depicts various key events in Kourend's history, most " +
                        "notably the Age of Strife.<br><br>The Age of Strife was considered the darkest era in Kourend's history, but it was also during this period that refugees " +
                        "fleeing Kourend first braved the Custodia Mountains. It was these refugees who would go on to found the first settlements of Varlamore, including Fortis.",
                0, 135, 0, 1938, 0, 560, false),
        TEMPESTUS_MODEL(
                new int[] { 52706 },
                "Tempestus Model",
                53129,
                "The model normally displayed here is currently being cleaned.<br><br>100 years after the founding of Fortis, it was decreed that a second settlement be " +
                        "built to address issues caused by the fast growing population. At the time, the islands of Tempest Bay were not considered a strong contender due to the " +
                        "unstable ground and the presence of a band of hill giants. However, the unlikely defeat of the giants ultimately led to the construction of Tempestus. The " +
                        "model here shows the city as it exists today.",
                -15, 125, 0, 1938, 0, 650, false),
        STATUE_OF_KING_MAXIMUS(
                new int[] { 52716 },
                "Tempestus Model",
                53129,
                "The model normally displayed here is currently being cleaned.<br><br>100 years after the founding of Fortis, it was decreed that a second settlement be " +
                        "built to address issues caused by the fast growing population. At the time, the islands of Tempest Bay were not considered a strong contender due to the " +
                        "unstable ground and the presence of a band of hill giants. However, the unlikely defeat of the giants ultimately led to the construction of Tempestus. The " +
                        "model here shows the city as it exists today.",
                -15, 125, 0, 1938, 0, 650, false),
        IMAFORES_BETRAYAL(
                new int[] { 52705 },
                "Imafore's Betrayal",
                52419,
                "The column on display here is one of two built at the end of the War of Betrayal. This column depicts the event that ignited the war between Kourend and " +
                        "Varlamore: Imafore's killing of the Kourend royal family.<br><br>The war went on to last six years and ended at the Battle of the Proudspire when Varlamorian " +
                        "freedom fighters led by Imafore's own cousin captured him. Imafore was executed soon after, with his cousin, Mezpah Arkan, taking the throne.",
                0, 135, 0, 1938, 0, 560, false),
        KNIGHT_OF_VARLAMORE_ARMOUR(
                new int[] { 52697 },
                "Knight of Varlamore Armour",
                53130,
                "The armour normally displayed here is currently being cleaned.<br><br>The Knights of Varlamore were founded by King Mezpah after his ascension to the throne. " +
                        "These knights are selected by either the kingdom's ruler or their heir, with any citizen of Varlamore having the ability to become one.<br><br>Knights of " +
                        "Varlamore can serve in a variety of duties across the kingdom and are granted a set of prestigious armour like that shown here.",
                -3, 125, 0, 1938, 0, 560, false),
        KUALTI_DAGGERS(
                new int[] { 52698, 52699, 52700 },
                "Kualti Daggers",
                52367,
                "The Kualti were founded by King Mezpah after his ascension to the throne. These six elite warriors are chosen from the Knights of Varlamore and act as the " +
                        "royal guard for the ruler of the kingdom.<br><br>Upon their selection, each Kualti is granted a weapon of their choice alongside a Kualti dagger, with them " +
                        "keeping these weapons until death. After the passing of a Kualti, their dagger is displayed here in the Grand Museum.",
                -3, 123, 0, 1938, 0, 70, false),
        STATUE_OF_XERNA(
                new int[] { 52712 },
                "Statue of Xerna",
                52321,
                "This statue depicts the legendary hero Xerna, sometimes known as the Mother of Wisdom.",
                0, 152, 0, 1938, 0, 580, false),
        XERNAS_DIADEM(
                new int[] { 55377 },
                "Xerna's Diadem",
                53130,
                "Be aware that the diadem normally displayed here was recently stolen. The Grand Museum is willing to pay a high price for information that leads to its return.",
                -3, 135, 0, 1938, 0, 560, false),
        STATUE_OF_MARHUITZ(
                new int[] { 52713 },
                "Statue of Marhuitz",
                52327,
                "This statue depicts the legendary hero Marhuitz, the first king of Fortis. He was sometimes known as the Old Wolf.",
                0, 140, 0, 1938, 0, 580, false),
        CROWN_OF_RALOS(
                new int[] { 52695 },
                "The Crown of Ralos",
                52361,
                "The crown on display here is the original Crown of Ralos. When King Marhuitz was crowned the first king of Fortis, it was this crown that was placed upon his " +
                        "head.<br><br>Since its creation, the crown has been worn by every ruler of the Tullus dynasty. However, after the execution of Emperor Imafore and the passing of " +
                        "the throne to the Arkan dynasty, King Mezpah had a new crown created.",
                0, 125, 0, 1938, 0, 50, false),
        STATUE_OF_QUOATLOS(
                new int[] { 52715 },
                "Statue of Quoatlos",
                52317,
                "This statue depicts the legendary hero Quoatlos, sometimes known as the Feathered Serpent.",
                0, 150, 0, 1938, 0, 580, false),
        STATUE_OF_DIZANA(
                new int[] { 52711 },
                "Statue of Dizana",
                52318,
                "This statue depicts the legendary hero Dizana, sometimes known as the Heroine of the Hunt.",
                0, 130, 0, 1938, 0, 580, false),
        DIZANAS_QUIVER(
                new int[] { 52694 },
                "Dizana's Quiver",
                52366,
                "The quiver on display here is a replica of the one originally created by the hero Dizana. The hardiness of the quiver, along with Dizana's fame, led to many of " +
                        "these replicas being created. Some of them still exist today and are often used by fighters in the Fortis Colosseum.<br><br>The status of the original quiver is " +
                        "heavily debated. Many claim that it has long been lost, while others believe it exists among the replicas being used within the Colosseum.",
                0, 142, 0, 1938, 0, 80, false),
        STATUE_OF_TAUR(
                new int[] { 52710 },
                "Statue of Taur",
                52328,
                "This statue depicts the legendary hero Taur, sometimes known as the Young Bull.",
                0, 130, 0, 1938, 0, 580, false),
        CLOAK_OF_RANUL(
                new int[] { 52696 },
                "Cloak of Ranul",
                52352,
                "It is said that the cloak on display here was crafted during the first Teok Xiki, more commonly known as the Gathering.<br><br>The Gathering is the process used " +
                        "by both the Church of Ralos and the Sect of Ranul to select a new Teokan after the death of the previous one. This cloak was likely presented as a gift to the " +
                        "newly selected Teokan of Ranul.",
                -3, 135, 0, 906, 0, 560, false),
        STATUE_OF_ATLAZORA(
                new int[] { 52714 },
                "Statue of Atlazora",
                52322,
                "This statue depicts the legendary hero Atlazora, sometimes known as the Jaguar of Night's Shadow.",
                0, 130, 0, 1938, 0, 580, false);

        private final int[] objectIds;
        private final String title;
        private final int modelId;
        private final String description;
        private final int var1, var2, var3, var4, var5, var6;
        private final boolean bool1;

        private void sendInformationPanel(Player player) {
            player.openInterface(InterfaceType.MAIN, 873);
            player.getPacketSender().sendClientScript(5006, "sisiiiiiii", title, modelId, description, var1, var2, var3, var4, var5, var6, bool1 ? 1 : 0);
            player.getTaskManager().doLookupByUUID(1116);   // Learn Some of Varlamore's History
        }
    }


    static {
        for (MuseumDisplay display : MuseumDisplay.values()) {
            for (int objectId : display.objectIds) {
                ObjectAction.register(objectId, 1, (player, obj) -> display.sendInformationPanel(player));
            }
        }
    }
}
