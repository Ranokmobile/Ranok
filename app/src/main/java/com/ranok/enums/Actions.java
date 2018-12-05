package com.ranok.enums;

import com.ranok.R;

public enum Actions {
    MOVE(1, R.drawable.ic_folder_move, "Переместить НЗ"),
    SPLIT(2, R.drawable.ic_call_split, "Разбить НЗ"),
    UNPACK(3, R.drawable.ic_package_up, "Распаковать НЗ" ),
    PRINT(4, R.drawable.ic_printer, "Печать НЗ" ),
    PACK(5, R.drawable.ic_package_down, "Упаковать в НЗ" ),
    LPN_INFO(6, R.drawable.ic_information_variant, "Информация о НЗ" ),
    POSITION_INFO(7, R.drawable.ic_information_variant, "Информация о позиции" ),
    PLACE_INFO(8, R.drawable.ic_information_variant, "Информация о ячейке" ),
    UNKNOWN(-1, -1, "");
    private int id, drawableId;
    private String text;

    public int getId() {
        return id;
    }

    public int getDrawableId() {
        return drawableId;
    }

    public String getText() {
        return text;
    }

    Actions(int id, int drawableId, String text) {
        this.id = id;
        this.drawableId = drawableId;
        this.text = text;
    }

    public static Actions getById(int id){
        for(Actions e : Actions.values()){
            if(id == e.id) return e;
        }
        return UNKNOWN;
    }
}
