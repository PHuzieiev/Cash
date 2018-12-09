package com.apps.newstudio.cash.data.adapters;

public class RecyclerViewLangFragment {

    private String typeBank;
    private String update;
    private String typeOther;
    private String button;

    /**
     * Creates RecyclerViewLangFragment object which is used to put data in list of Organizations Fragment
     * @param typeBank String value - type of organization
     * @param update String value
     * @param typeOther String value - type of organization
     * @param button String value - title for button in item of list
     */
    public RecyclerViewLangFragment(String typeBank, String update, String typeOther, String button) {
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
