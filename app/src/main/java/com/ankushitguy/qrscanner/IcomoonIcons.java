package com.ankushitguy.qrscanner;

import com.joanzapata.iconify.Icon;

public enum IcomoonIcons implements Icon {

    icon_pencil('\ue905'),
    icon_image('\ue90d'),
    icon_camera('\ue90f'),
    icon_copy('\ue92c'),
    icon_barcode('\ue937'),
    icon_qrcode('\ue938'),
    icon_floppy_disk('\ue962'),
    icon_power('\ue9b5'),
    icon_sphere('\ue9c9'),
    icon_loop2('\uea2e'),
    icon_arrow_right2('\uea3c'),
    icon_arrow_left2('\uea40');
    char character;

    IcomoonIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_','-');
    }

    @Override
    public char character() {
        return character;
    }
}
