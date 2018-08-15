package com.ranok.ui.base.search_widget;

public class SearchPlaceWidgetVM extends BaseSearchWidgetVM  {

    public SearchPlaceWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(11, hawkTag, callbacks, "###.###.#.#");
    }

    public SearchPlaceWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks, boolean searchBtnVisibility) {
        super(11, hawkTag, callbacks, "###.###.#.#", searchBtnVisibility);
    }

    @Override
    protected boolean isInputCorrect() {
        return (getInputText()!=null && getInputText().length()==11);
    }
}
