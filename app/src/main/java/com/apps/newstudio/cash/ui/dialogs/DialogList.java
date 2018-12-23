package com.apps.newstudio.cash.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogListOrganizations;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogListOrganizations;
import com.apps.newstudio.cash.utils.CashApplication;

import java.util.List;

public class DialogList {

    private Context mContext;
    private String mTitle;
    private List<RecyclerViewDataDialogList> mData;
    private List<RecyclerViewDataDialogListOrganizations> mDataTwo;

    private Dialog mDialog;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapterDialogList mAdapterDialogList;
    private RecyclerViewAdapterDialogListOrganizations mAdapterDialogListTwo;
    private RecyclerViewAdapterDialogList.OnItemClickListener onItemClickListener;

    /**
     * Constructor of DialogList object
     *
     * @param context             - Context object
     * @param title               - String object for Dialog title
     * @param data                - RecyclerViewDataDialogList List object for RecycleView of Dialog
     * @param dataTwo             - RecyclerViewDataDialogListOrganizations List object for RecycleView of Dialog
     * @param onItemClickListener - OnItemClickListener object for RecycleView Item Click Event
     */
    public DialogList(Context context, String title, List<RecyclerViewDataDialogList> data,
                      List<RecyclerViewDataDialogListOrganizations> dataTwo,
                      RecyclerViewAdapterDialogList.OnItemClickListener onItemClickListener) {
        mContext = context;
        mTitle = title;
        mData = data;
        mDataTwo = dataTwo;
        this.onItemClickListener = onItemClickListener;
        createDialog();
    }

    /**
     * Creates Dialog with RecycleView object and shows it
     */
    private void createDialog() {
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mDialog.getWindow().setBackgroundDrawable(drawable);
        mDialog.setContentView(R.layout.dialog_list);
        ((TextView) mDialog.getWindow().findViewById(R.id.dialog_list_title)).setText(mTitle);
        mRecyclerView = mDialog.getWindow().findViewById(R.id.dialog_list_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        if (mData != null) {
            mAdapterDialogList = new RecyclerViewAdapterDialogList(mData, onItemClickListener);
            mAdapterDialogList.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapterDialogList);
            for (int i = 0; i < mData.size(); i++) {
                if (mData.get(i).isChecked()) {
                    mRecyclerView.scrollToPosition(i);
                    break;
                }
            }
        } else {
            mAdapterDialogListTwo = new RecyclerViewAdapterDialogListOrganizations(mDataTwo, onItemClickListener);
            mAdapterDialogListTwo.notifyDataSetChanged();
            mRecyclerView.setAdapter(mAdapterDialogListTwo);
            for (int i = 0; i < mDataTwo.size(); i++) {
                if (mDataTwo.get(i).isChecked()) {
                    mRecyclerView.scrollToPosition(i);
                    break;
                }
            }
        }
        mDialog.show();
    }

    /**
     * Updates RecycleView data
     *
     * @param data - RecyclerViewDataDialogList List object
     */
    public void updateList(List<RecyclerViewDataDialogList> data) {
        mData = data;
        mAdapterDialogList.setData(mData);
        mAdapterDialogList.notifyDataSetChanged();
    }

    /**
     * Updates RecycleView dataTwo
     *
     * @param dataTwo - RecyclerViewDataDialogListOrganizations List object
     */
    public void updateListTwo(List<RecyclerViewDataDialogListOrganizations> dataTwo) {
        mDataTwo = dataTwo;
        mAdapterDialogListTwo.setData(mDataTwo);
        mAdapterDialogListTwo.notifyDataSetChanged();
    }

    /**
     * Getter for Dialog object
     *
     * @return Dialog object mDialog
     */
    public Dialog getDialog() {
        return mDialog;
    }

    /**
     * Getter for RecyclerView object
     *
     * @return RecyclerView object mRecyclerView
     */
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
