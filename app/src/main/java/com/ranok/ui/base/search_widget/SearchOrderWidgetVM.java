package com.ranok.ui.base.search_widget;

import com.ranok.utils.StringUtils;

public class SearchOrderWidgetVM  extends BaseSearchWidgetVM {

    public SearchOrderWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(6, hawkTag, callbacks, "######", true, false);
    }

    public SearchOrderWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks, boolean searchBtnVisibility) {
        super(6, hawkTag, callbacks, "######", searchBtnVisibility);
    }

    @Override
    protected boolean isInputCorrect() {
        return !(StringUtils.isEmpty(getInputText()));
    }
}
