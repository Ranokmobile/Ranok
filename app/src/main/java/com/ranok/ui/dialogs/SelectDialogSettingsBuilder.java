package com.ranok.ui.dialogs;

public class SelectDialogSettingsBuilder {
    private String header;

    public SelectDialogSettingsBuilder setHeader(String header) {
        this.header = header;
        return this;
    }


    public SelectDialogSettings createSelectDialogSettings() {
        return new SelectDialogSettings(header);
    }
}