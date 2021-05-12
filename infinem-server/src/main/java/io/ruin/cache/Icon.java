package io.ruin.cache;

public enum Icon {
    YELLOW_INFO_BADGE(84),
    MYSTERY_BOX(33),
    BLUE_INFO_BADGE(85),
    GREEN_INFO_BADGE(86),
    WILDERNESS(46),
    ANNOUNCEMENT(55),
    HCIM_DEATH(32),
    DONATOR(25),
    INFO(15),
    GOLD_STAR(32)
    ;

    public final int imgId;

    Icon(int imgId) {
        this.imgId = imgId;
    }


    public String tag() {
        return "<img=" + imgId + ">";
    }

}
