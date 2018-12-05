package com.apps.newstudio.cash.data.adapters;

public class RecycleViewDataAdapterDialogList {


    private boolean mIsChecked;
    private String mTitleUkr;
    private String mTitleRus;
    private String mTitleEng;

    public RecycleViewDataAdapterDialogList(boolean isChecked, String titleUkr, String titleRus, String titleEng) {
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
