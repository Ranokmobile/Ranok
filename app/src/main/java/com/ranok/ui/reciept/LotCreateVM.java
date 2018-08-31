package com.ranok.ui.reciept;

import com.ranok.ui.base.BaseViewModel;
import com.ranok.widgets.StringWrapper;


public class LotCreateVM extends BaseViewModel<LotCreateIView> {

    StringWrapper s = new StringWrapper("423");

    public StringWrapper getS() {
        return s;
    }

    public void setS(StringWrapper s) {
        this.s = s;
    }
}
