package io.ruin.network;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/7/2024
 */
@Getter
@AllArgsConstructor
public enum ClientPacket {
    UPDATE_PLAYER_MODEL_V2(0, 26),
    UPDATE_PLAYER_MODEL_V1(1, 13),  // Unused
    IDLE(2, 0),                     // Unused
    OPPLAYER7(3, 3),
    EVENT_MOUSE_MOVE(4, -1),        // Unused
    IF_BUTTON2(5, 8),
    EVENT_NATIVE_MOUSE_MOVE(6, -1), // Unused
    OPPLAYERU(7, 11),               // Unused
    OPPLAYERT(8, 11),
    OPOBJ2(9, 7),
    OPLOC1(10, 7),
    OPHELD6(11, 2),                 // Unused
    REFLECTION_CHECK_REPLY(12, -1), // Unused
    IF_BUTTON1(13, 8),
    OPOBJ6(14, 6),
    OPPLAYER1(15, 3),
    OPNPC2(16, 3),
    RESUME_P_COUNTDIALOG(17, 4),
    IF_BUTTON3(18, 8),
    FRIENDLIST_DEL(19, -1),
    NO_TIMEOUT(20, 0),              // Unused
    OPLOCU(21, 15),                 // Unused
    SET_HEADING(22, 1),             // Unused NEW IN 226
    IF_BUTTON8(23, 8),
    WINDOW_STATUS(24, 5),
    MOVE_GAMECLICK(25, -1),
    OPOBJ1(26, 7),
    MAP_BUILD_COMPLETE(27, 0),      // Unused
    SEND_SNAPSHOT(28, -1),          // Not used atm, for player reporting
    CLOSE_MODAL(29, 0),
    OPOBJ5(30, 7),
    OPNPCT(31, 11),
    CLIENT_CHEAT(32, -1),
    IF_SUBOP(33, 10),               // Unused
    OPNPC3(34, 3),
    AFFINEDCLANSETTINGS_ADDBANNED_FROMCHANNEL(35, -1),  // Unused
    EVENT_KEYBOARD(36, -2),         // Unused
    EVENT_APPLET_FOCUS(37, 1),      // Unused
    IGNORELIST_ADD(38, -1),
    EVENT_MOUSE_SCROLL(39, 2),      // Unused
    OPLOC3(40, 7),
    IF_BUTTON5(41, 8),
    SOUNG_JINGLEEND(42, 4),         // Unused
    OPPLAYER6(43, 3),
    OPPLAYER8(44, 3),
    OPNPC6(45, 2),
    IF_BUTTON7(46, 8),
    OPLOC5(47, 7),
    CLICKWORLDMAP(48, 4),           // Unused
    OPPLAYER3(49, 3),
    DETECT_MODIFIED_CLIENT(50, 4),  // Unused
    OPLOC4(51, 7),
    OPLOCT(52, 15),
    OPPLAYER5(53, 3),
    RESUME_PAUSEBUTTON(54, 6),
    EVENT_MOUSE_CLICK(55, 6),       // Unused
    IF_BUTTON6(56, 8),
    BUG_REPORT(57, -2),
    OPNPC1(58, 3),
    CONNECTION_TELEMETRY(59, -1),   // Unused
    MESSAGE_PRIVATE(60, -2),
    IF_BUTTOND(61, 16),
    FRIENDCHAT_JOIN_LEAVE(62, -1),
    RESUME_P_NAMEDIALOG(63, -1),
    OPNPC5(64, 3),
    IF_BUTTONT(65, 16),
    SET_CHATFILTERSETTINGS(66, 3),
    SEND_PING_REPLY(67, 10),        // Unused
    CLANCHANNEL_FULL_REQUEST(68, 1),// Unused
    OCULUS_LEAVE(69, 0),            // Unused
    MEMBERSHIP_PROMOTION_ELIGIBILITY(70, 2),    // Unused
    EVENT_NATIVE_MOUSE_CLICK(71, 7),// Unused
    MOVE_MINIMAPCLICK(72, -1),
    IF_BUTTON(73, 4),
    RESUME_P_STRINGDIALOG(74, -1),
    IF_BUTTON4(75, 8),
    HISCORE_REQUEST(76, -1),        // Unused
    FRIENDCHAT_SETRANK(77, -1),
    OPLOCE(78, 2),
    IF_BUTTON9(79, 8),
    OPOBJ4(80, 7),
    MESSAGE_PUBLIC(81, -1),
    RESUME_P_OBJDIALOG(82, 2),
    FRIENDLIST_ADD(83, -1),
    OPPLAYER2(84, 3),
    CLANCHANNEL_KICKUSER(85, -1),   // Unused
    IGNORELIST_DEL(86, -1),
    AFFINEDCLANSETTINGS_SETMUTED_FROMCHANNEL(87, -1),   // Unused
    OPOBJT(88, 15),
    OPOBJU(89, 15),                 // Unused
    FRIENDCHAT_KICK(90, -1),
    TELEPORT(91, 9),
    OPPLAYER4(92, 3),
    IF_BUTTON10(93, 8),
    EVENT_CAMERA_POSITION(94, 4),   // Unused
    OPLOC2(95, 7),
    IF_CRMVIEW_CLICK(96, 22),       // Unused
    CLANSETTINGS_FULL_REQUEST(97, 1),   // Unused
    OPNPC4(98, 3),
    OPNPCU(99, 11),                 // Unused
    OPOBJ3(100, 7);

    public final int packetId, packetSize;

    public static int getPacketSize(int id) {
        for (ClientPacket p : values()) {
            if (p.getPacketId() == id)
                return p.getPacketSize();
        }

        return Byte.MIN_VALUE;
    }
}
