package com.ranok.ui.base.search_widget;

public class SearchLpnWidgetVM extends BaseSearchWidgetVM  {




    public SearchLpnWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(9, hawkTag, callbacks);
    }

    @Override
    protected boolean isInputCorrect() {
        return !getInputText().isEmpty();
    }
}
