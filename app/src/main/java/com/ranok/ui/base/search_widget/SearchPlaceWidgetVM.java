package com.ranok.ui.base.search_widget;

public class SearchPlaceWidgetVM extends BaseSearchWidgetVM  {

    public SearchPlaceWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(11, hawkTag, callbacks, "###.###.#.#");
    }

    @Override
    protected boolean isInputCorrect() {
        return (getInputText()!=null && getInputText().length()==11);
    }
}
