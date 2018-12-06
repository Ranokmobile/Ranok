package com.ranok.ui.base.search_widget;

public class SearchPlaceWidgetVM extends BaseSearchWidgetVM  {

    public SearchPlaceWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks) {
        super(15, hawkTag, callbacks, "###.###.#.#");
    }

    public SearchPlaceWidgetVM(String hawkTag, SearchWidgetCallbacks callbacks, boolean searchBtnVisibility) {
        super(15, hawkTag, callbacks, "###.###.#.#", searchBtnVisibility);
    }

    @Override
    public void onTextChanged(CharSequence text, int start, int before, int count) {
     //   Log.d("TEXT", text.toString());
        String s = text.toString();
        if (s.contains("$")) {
            s = s.substring(3);
        }
        if (s.length() == 2) {
            if (s.substring(0,2).equals("04" +
                    "") ) {
                s = "";
            }
        }
        super.onTextChanged(s, start, before, count);
    }




    @Override
    protected boolean isInputCorrect() {
        return (getInputText()!=null && getInputText().length()==11);
    }
}
