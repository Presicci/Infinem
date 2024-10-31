package io.ruin.api.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mrbennjerry - https://github.com/Presicci
 * Created on 10/8/2024
 */
@Getter
@AllArgsConstructor
public enum ServerPacket {
    CAM_MODE(0, 1),                 // Unused
    CLANSETTINGS_DELTA(1, -2),      // Unused
    OBJ_CUSTOMISE(2, 17),           // Unused NEW IN 226
    IF_SETPLAYERMODEL_BASECOLOUR(3, 6), // Unused
    CAM_MOVETO_ARC(4, 10),          // Unused
    UPDATE_REBOOT_TIMER(5, 2),
    MIDI_SONG_V2(6, 10),
    PLAYER_ANIM_SPECIFIC(7, 3),     // Unused
    LOGOUT_WITHREASON(8, 1),        // Unused
    REBUILD_REGION(9, -2),
    CHAT_FILTER_SETTINGS(10, 2),    // Unused
    UPDATE_ZONE_PARTIAL_FOLLOWS(11, 3),
    TRIGGER_ONDIALOGABORT(12, 0),   // Unused
    SITE_SETTINGS(13, -1),          // Unused
    UPDATE_RUNWEIGHT(14, 2),
    MAP_ANIM_SPECIFIC(15, 8),       // Unused
    VARP_RESET(16, 0),              // Unused
    OBJ_ENABLE_OPS(17, 4),          // Unused
    MESSAGE_PRIVATE_ECHO(18, -2),
    SET_PLAYER_OP(19, -1),
    UPDATE_STAT_V1(20, 6),          // Unused
    MESSAGE_FRIENDCHANNEL(21, -1),
    CAM_LOOKAT_EASED_COORD(22, 7),  // Unused
    UPDATE_ZONE_FULL_FOLLOWS(23, 3),
    UPDATE_INV_PARTIAL(24, -2),
    IF_CLOSESUB(25, 4),
    UPDATE_INV_FULL(26, -2),
    CLANCHANNEL_FULL(27, -2),       // Unused
    OCULUS_SYNC(28, 4),             // Unused
    IF_OPENSUB(29, 7),
    CLANCHANNEL_DELTA(30, -2),      // Unused
    UPDATE_ZONE_PARTIAL_ENCLOSED(31, -2),   // Unused
    UPDATE_RUNENERGY(32, 2),
    CAM_TARGET_V2(33, 5),          // Unused
    IF_SETPLAYERMODEL_BODYTYPE(34, 5),  // Unused
    LOC_ADD_CHANGE(35, 5),
    UPDATE_IGNORELIST(36, -2),
    LOC_MERGE(37, 14),              // Unused
    SET_HEATMAP_ENABLED(38, 1),     // Unused
    CAM_LOOKAT(39, 7),              // Unused
    CAM_MOVETO_CYLCES(40, 8),       // Unused
    VARCLAN_ENABLE(41, 0),          // Unused
    IF_SETNPCHEAD_ACTIVE(42, 6),    // Unused
    OBJ_DEL(43, 7),
    IF_SETPLAYERMODEL_SELF(44, 5),  // Unused
    MIDI_SONG_STOP(45, 4),          // Unused
    OBJ_ADD(46, 14),
    MESSAGE_GAME(47, -1),
    PROJANIM_SPECIFIC_V2(48, 19),   // Unused
    UPDATE_FRIENDCHAT_CHANNEL_FULL_V1(49, -2),
    VARP_LARGE(50, 6),
    VARP_SYNC(51, 0),               // Unused
    PROJANIM_SPECIFIC_V1(52, 17),   // Unused
    LOC_ANIM_SPECIFIC(53, 6),       // Unused
    UPDATE_FRIENDCHAT_CHANNEL_SINGLEUSER(54, -1),   // Unused
    UPDATE_STOCKMARKET_SLOT(55, 20),// Unused
    MIDI_JINGLE(56, 5),             // Unused
    IF_SETHIDE(57, 5),
    IF_SETCOLOUR(58, 6),
    IF_SETMODEL(59, 6),             // Unused
    CAM_SHAKE(60, 4),
    UPDATE_UID192(61, 28),          // Unused
    RESET_ANIMS(62, 0),             // Unused
    PLAYER_INFO(63, -2),
    OBJ_UNCUSTOMISE(64, 7),         // Unused NEW IN 226
    NPC_INFO_LARGE_V4(65, -2),     // Unused
    IF_SETEVENTS(66, 12),
    IF_SETPLAYERMODEL_OBJ(67, 8),   // Unused
    LOGOUT(68, 0),
    VARCLAN_DISABLE(69, 0),         // Unused
    UPDATE_STAT_V2(70, 7),
    IF_MOVESUB(71, 8),
    UPDATE_INV_STOPTRANSMIT(72, 2), // Unused
    SYNTH_SOUND(73, 5),
    LOC_DEL(74, 2),
    HISCORE_REPLY(75, -2),          // Unused
    CAM_SMOOTHRESET(76, 4),         // Unused
    UPDATE_TRADINGPOST(77, -2),     // Unused
    REFLECTION_CHECKER(78, -2),     // Unused
    IF_SETPLAYERHEAD(79, 4),
    LOC_ANIM(80, 4),
    NPC_ANIM_SPECIFIC(81, 5),       // Unused
    CHAT_FILTER_SETTINGS_PRIVATECHAT(82, 1),
    NPC_SPOTANIM_SPECIFIC(83, 9),   // Unused
    MIDI_SONG_V1(84, 2),            // Unused
    FRIENDLIST_LOADED(85, 0),       // Unused
    MESSAGE_CLANCHANNEL_SYSTEM(86, -1), // Unused
    NPC_INFO_SMALL_V4(87, -2),     // Unused
    VARCLAN(88, -1),                // Unused
    MAP_ANIM(89, 6),
    CLANSETTINGS_FULL(90, -2),      // Unused
    IF_SETSCROLLPOS(91, 6),         // Unused
    SOUND_AREA(92, 7),
    IF_SETTEXT(93, -2),
    MIDI_SWAP(94, 8),               // Unused
    HINT_ARROW(95, 6),
    MINIMAP_TOGGLE(96, 1),
    RUNCLIENTSCRIPT(97, -2),
    MESSAGE_CLANCHANNEL(98, -1),    // Unused
    URL_OPEN(99, -2),               // Unused
    OBJ_COUNT(100, 11),
    VARP_SMALL(101, 3),
    SERVER_TICK_END(102, 0),        // Unused
    LOGOUT_TRANSFER(103, -1),
    CAM_MOVETO(104, 6),
    UPDATE_FRIENDCHAT_CHANNEL_FULL_V2(105, -2), // Unused
    IF_CLEARINV(106, 4),            // Unused
    IF_SETANIM(107, 6),
    CAM_ROTATETO(108, 6), // TODO wrong packet maybe
    REBUILD_NORMAL(109, -2),
    IF_OPENTOP(110, 2),
    NPC_HEADICON_SPECIFIC(111, 9),  // Unused
    SET_MAP_FLAG(112, 2),
    IF_SETROTATESPEED(113, 8),      // Unused
    IF_SETOBJECT(114, 10),
    IF_SETNPCHEAD(115, 6),
    PLAYER_SPOTANIM_SPECIFIC(116, 9),   // Unused
    IF_SETPOSITION(117, 8),
    SEND_PING(118, 8),              // Unused
    IF_RESYNC(119, -2),             // Unused
    MIDI_SONG_WITHSECONDARY(120, 12),   // Unused
    CAM_RESET(121, 0),
    UPDATE_FRIENDLIST(122, -2),
    MAP_PROJANIM(123, 20),
    IF_SETANGLE(124, 10),           // Unused
    CAM_ROTATEBY(125, 7),           // Unused
    CAM_TARGET_V1(126, 3),          // Unused
    MESSAGE_PRIVATE(127, -1),
    RESET_INTERACTION_MODE(128, 2), // Unused NEW IN 226
    SET_NPC_UPDATE_ORIGIN(129, 2),
    REBUILD_WORLDENTITY(130, -2),   // Unused
    SET_ACTIVE_WORLD(131, 4),
    CLEAR_ENTITIES(132, 0),         // Unused
    HIDEOBJOPS(133, 1),             // Unused
    WORLDENTITY_INFO_V1(134, -2),   // Unused
    PROJANIM_SPECIFIC_V3(135, 22),  // Unused
    UNKNOWN_STRING(136, -1),        // Unused
    SET_INTERACTION_MODE(137, 4),   // Unused NEW IN 226
    HIDELOCOPS(138, 1),             // Unused
    NPC_INFO_SMALL_V5(139, -2),
    WORLDENTITY_INFO_V3(140, -2),   // Unused NEW IN 226
    HIDENPCOPS(141, 1),             // Unused
    WORLDENTITY_INFO_V2(142, -2),   // Unused
    NPC_INFO_LARGE_V5(143, -2);

    private final int packetId, packetSize;

    public static ServerPacket getPacketByOpcode(int opcode) {
        for (ServerPacket packet : values()) {
            if (packet.packetId == opcode) return packet;
        }
        return null;
    }
}
