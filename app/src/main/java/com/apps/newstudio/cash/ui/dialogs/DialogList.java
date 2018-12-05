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
import com.apps.newstudio.cash.data.adapters.RecycleViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecycleViewDataAdapterDialogList;
import com.apps.newstudio.cash.utils.CashApplication;

import java.util.List;

public class DialogList {

    private Context mContext;
    private String mTitle;
    private List<RecycleViewDataAdapterDialogList> mData;
    private Dialog mDialog;
    private RecyclerView mRecyclerView;
    private RecycleViewAdapterDialogList mAdapterDialogList;
    private RecycleViewAdapterDialogList.OnItemClickListener onItemClickListener;

    public DialogList(Context context, String title, List<RecycleViewDataAdapterDialogList> data,
                      RecycleViewAdapterDialogList.OnItemClickListener onItemClickListener) {
        mContext = context;
        mTitle = title;
        mData = data;
        this.onItemClickListener=onItemClickListener;
        createDialog();
    }

    public void createDialog(){
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mDialog.getWindow().setBackgroundDrawable(drawable);
        mDialog.setContentView(R.layout.dialog_list);
        ((TextView)mDialog.getWindow().findViewById(R.id.dialog_list_title)).setText(mTitle);
        mRecyclerView = mDialog.getWindow().findViewById(R.id.dialog_list_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapterDialogList = new RecycleViewAdapterDialogList(mData, onItemClickListener);
        mAdapterDialogList.notifyDataSetChanged();
        mRecyclerView.setAdapter(mAdapterDialogList);

        mDialog.show();
    }

    public void updateList(List<RecycleViewDataAdapterDialogList> data){
        mData=data;
        mAdapterDialogList.setData(mData);
        mAdapterDialogList.notifyDataSetChanged();
    }

    public Dialog getDialog() {
        return mDialog;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
