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
    // Finalized
    EVENT_MOUSE_IDLE(0, 0),
    FRIENDCHAT_JOIN_LEAVE(1, -1),
    IF_BUTTON3(2, 8),
    IF_BUTTON5(3, 8),
    OPLOC5(4, 7),
    EVENT_APPLET_FOCUS(5, 1),
    OPLOC3(6, 7),
    IF_BUTTON7(7, 8),
    HISCORE_REQUEST(8, -1),
    FRIENDCHAT_SETRANK(9, -1),
    OPPLAYER2(10, 3),
    IF_BUTTON4(11, 8),
    EVENT_WINDOW_SETTING(12, 5),
    OPPLAYER7(13, 3),
    IF_BUTTOND(14, 16),
    OPLOCU(15, 15),
    OPOBJ3(16, 7),
    OPNPCE(17, 2),
    WIDGET_TYPE(18, 4),
    OPOBJE(19, 6),
    OPNPC4(20, 3),
    IF_BUTTON6(21, 8),
    SEND_PING_REPLY(22, 10),
    IF_BUTTON8(23, 8),
    OPPLAYER6 (24, 3),
    IGNORELIST_DEL(25, -1),
    LOGIN_TIMINGS(26, -1),
    MESSAGE_PRIVATE(27, -2),  // TODO TEST
    CLOSE_MODAL(28, 0),
    OCULUS_LEAVE(29, 0),
    OPOBJ5(30, 7),
    AFFINEDCLANSETTINGS_SETMUTED_FROMCHANNEL(31, -1),
    TELEPORT(32, 9),
    OPNPCT(33, 11),  // TODO TEST
    UPDATE_PLAYER_MODEL_OLD(34, 13),
    RESUME_NAMEDIALOG(35, -1),
    IF_SUBOP(36, 10),
    IF_BUTTONT(37, 16),
    EVENT_MOUSE_MOVE(38, -1),
    EVENT_MOUSE_CLICK(39, 6),
    EVENT_NATIVE_MOUSE_MOVE(40, -1),
    OPLOC4(41, 7),
    OPLOC1(42, 7),
    OPLOC2(43, 7),
    OPNPC1(44, 3),
    REFLECTION_CHECK_REPLY(45, -1),
    FRIEND_DELUSER(46, -1),
    OPNPC3(47, 3),
    SEND_SNAPSHOT(48, -1),  // TODO TEST
    IF_BUTTON1(49, 8),
    NO_TIMEOUT(50, 0),
    OPPLAYER8(51, 3),
    OPPLAYER5(52, 3),
    OPHELD6(53, 2),
    IF_BUTTON2(54, 8),
    OPPLAYERU(55, 11),
    OPPLAYER1(56, 3),
    BUG_REPORT(57, -2),  // TODO TEST
    SOUND_SONGEND(58, 4),
    OPLOCT(59, 15),  // TODO TEST
    FRIENDCHAT_KICK(60, -1),  // TODO BUFFER
    MESSAGE_PUBLIC(61, -1),
    IF_BUTTON10(62, 8),
    MOVE_GAMECLICK(63, -1),
    OPNPC5(64, 3),
    OPNPCU(65, 11),
    OPPLAYERT(66, 11),  // TODO TEST
    EVENT_CAMERA_POSITION(67, 4),
    CLANCHANNEL_REQUEST_FULL(68, 1),
    OPOBJT(69, 15),  // TODO TEST
    EVENT_MOUSE_SCROLL(70, 2),
    RESUME_OBJDIALOG(71, 2),
    EVENT_KEYBOARD(72, -2),
    IF_BUTTON9(73, 8),
    IGNORELIST_ADD(74, -1),
    RESUME_STRINGDIALOG(75, -1),
    CHAT_SETFILTER(76, 3),
    OPOBJ2(77, 7),
    CLANSETTINGS_REQUEST_FULL(78, 1),
    OPPLAYER3(79, 3),
    DOCHEAT(80, -1),
    DETECT_MODIFIED_CLIENT(81, 4),
    CLANCHANNEL_KICKUSER(82, -1),
    OPNPC2(83, 3),
    MAP_BUILD_COMPLETE(84, 0),
    OPOBJ4(85, 7),
    MEMBERSHIP_PROMOTION_ELIGIBILITY(86, 2),
    UPDATE_PLAYER_MODEL(87, 26),
    OPPLAYER4(88, 3),
    OPOBJU(89, 15),
    OPOBJ1(90, 7),
    RESUME_COUNTDIALOG(91, 4),
    FRIENDLIST_ADD(92, -1),
    RESUME_PAUSEBUTTON(93, 6),
    CLICKWORLDMAP(94, 4),
    IF_CRMVIEW_CLICK(95, 22),
    MOVE_MINIMAPCLICK(96, -1),
    EVENT_NATIVE_MOUSE_CLICK(97, 7),
    AFFINEDCLANSETTINGS_ADDBANNED_FROMCHANNEL(98, -1),
    OPLOCE(99, 2);

    public final int packetId, packetSize;

    public static int getPacketSize(int id) {
        for (ClientPacket p : values()) {
            if (p.getPacketId() == id)
                return p.getPacketSize();
        }

        return Byte.MIN_VALUE;
    }
}
