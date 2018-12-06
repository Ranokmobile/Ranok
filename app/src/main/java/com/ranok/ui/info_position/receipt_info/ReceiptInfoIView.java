package com.ranok.ui.info_position.receipt_info;

import com.ranok.network.models.PosInReceiptModel;
import com.ranok.ui.base.BaseIView;


public interface ReceiptInfoIView extends BaseIView {
    void showMenu(PosInReceiptModel item);

}
