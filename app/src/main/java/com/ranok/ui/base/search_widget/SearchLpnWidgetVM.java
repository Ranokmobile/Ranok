package com.ranok.ui.base.search_widget;

public class SearchLpnWidgetVM extends BaseSearchWidgetVM  {




    public SearchLpnWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(15, hawkTag, callbacks, "##########");
    }

    public SearchLpnWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks, boolean searchBtnVisibility) {
        super(15, hawkTag, callbacks, "##########", searchBtnVisibility);
    }

    @Override
    protected boolean isInputCorrect() {
        return getInputText() != null && !getInputText().isEmpty();
    }
}
