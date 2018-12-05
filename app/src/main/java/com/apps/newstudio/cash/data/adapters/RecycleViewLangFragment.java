package com.apps.newstudio.cash.data.adapters;

public class RecycleViewLangFragment {

    private String typeBank;
    private String update;
    private String typeOther;
    private String button;

    public RecycleViewLangFragment(String typeBank, String update, String typeOther, String button) {
        this.typeBank = typeBank;
        this.typeOther = typeOther;
        this.button = button;
        this.update = update;
    }

    public String getTypeBank() {
        return typeBank;
    }

    public String getTypeOther() {
        return typeOther;
    }

    public String getButton() {
        return button;
    }

    public String getUpdate() {
        return update;
    }
}
