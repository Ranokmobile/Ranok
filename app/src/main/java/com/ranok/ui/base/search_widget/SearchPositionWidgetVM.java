package com.ranok.ui.base.search_widget;

import java.util.regex.Pattern;

public class SearchPositionWidgetVM extends BaseSearchWidgetVM {

    private ItemType itemType;

    public ItemType getItemType() {
        return itemType;
    }

    public SearchPositionWidgetVM(String screenTag, SearchWidgetCallbacks callbacks) {
        super(13, screenTag, callbacks,"#############");
    }

    @Override
    protected boolean isInputCorrect() {
        if (ItemType.ITEM_CODE.pattern.matcher(getInputText()).matches()){
            itemType = ItemType.ITEM_CODE;
            return true;
        } else if (ItemType.PIECE_BARCODE.pattern.matcher(getInputText()).matches()){
            itemType = ItemType.PIECE_BARCODE;
            return true;
        } else if (ItemType.PACK_BARCODE.pattern.matcher(getInputText()).matches()){
            itemType = ItemType.PACK_BARCODE;
            return true;
        } else if (ItemType.ITEM_BARCODE.pattern.matcher(getInputText()).matches()){
            itemType = ItemType.ITEM_BARCODE;
            return true;
        } else {
            itemType = ItemType.UNKNOWN;
            return false;
        }
    }

    public enum ItemType {
        ITEM_CODE("^[1-2]{1,1}\\d{5,5}$", "")
        , PIECE_BARCODE("^[1-9]{1,1}\\d{12,12}$", "GTIN")
        , PACK_BARCODE("^[1]{1,1}\\d{7,7}$", "GTIN_BOX")
        , ITEM_BARCODE("^[2]{1,1}\\d{7,7}$", "GTIN_ITEM")
        , UNKNOWN("","");

        public final String type;
        public final Pattern pattern;

        ItemType(String patternStr, String type) {
            this.type = type;
            this.pattern = Pattern.compile(patternStr);
        }
    }
}
