package com.apps.newstudio.cash.data.adapters;

public class RecyclerViewDataDialogList {


    private boolean mIsChecked;
    private String mTitleUkr;
    private String mTitleRus;
    private String mTitleEng;

    /**
     * Creates RecyclerViewDataDialogList object which is used to put data in item of list in DialogList object
     * @param isChecked - true - you checked item, false - you did not check item
     * @param titleUkr - title of item in Ukraine
     * @param titleRus - title of item in Russian
     * @param titleEng - title of item in English
     */
    public RecyclerViewDataDialogList(boolean isChecked, String titleUkr, String titleRus, String titleEng) {
        mIsChecked = isChecked;
        mTitleUkr = titleUkr;
        mTitleRus = titleRus;
        mTitleEng = titleEng;
    }

    public boolean isChecked() {
        return mIsChecked;
    }

    public String getTitleUkr() {
        return mTitleUkr;
    }

    public String getTitleRus() {
        return mTitleRus;
    }

    public String getTitleEng() {
        return mTitleEng;
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
    }
}
