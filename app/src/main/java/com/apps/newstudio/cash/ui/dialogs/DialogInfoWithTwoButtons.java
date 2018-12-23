package com.apps.newstudio.cash.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.apps.newstudio.cash.R;

public class DialogInfoWithTwoButtons {

    private Dialog mDialog;
    private Context mContext;
    private View.OnClickListener onClickListenerButtonOne;
    private View.OnClickListener onClickListenerButtonTwo;
    private String mTitle;
    private String mMessage;
    private String mTitleButtonOne;
    private String mTitleButtonTwo;

    /**
     * Initialises Context, onClickListenerButtonOne, onClickListenerButtonTwo
     * @param context object which is used for creation Dialog object
     * @param onClickListenerButtonOne object for onClick event of the first button
     * @param onClickListenerButtonTwo object for onClick event of the second button
     */
    public DialogInfoWithTwoButtons(Context context,
                                    View.OnClickListener onClickListenerButtonOne,
                                    View.OnClickListener onClickListenerButtonTwo,
                                    String mTitle, String mMessage, String mTitleButtonOne, String mTitleButtonTwo) {
        mContext = context;
        this.onClickListenerButtonOne = onClickListenerButtonOne;
        this.onClickListenerButtonTwo = onClickListenerButtonTwo;
        this.mTitle = mTitle;
        this.mMessage = mMessage;
        this.mTitleButtonOne = mTitleButtonOne;
        this.mTitleButtonTwo = mTitleButtonTwo;
        createDialog();
    }

    /**
     * Creates default Dialog object, sets OnClickListeners for buttons
     */

    private void createDialog(){
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mDialog.getWindow().setBackgroundDrawable(drawable);
        mDialog.setContentView(R.layout.dialog_info_with_two_buttons);


        ((TextView)mDialog.getWindow().findViewById(R.id.dialog_title_info)).setText(mTitle);
        ((TextView)mDialog.getWindow().findViewById(R.id.dialog_text_info)).setText(mMessage);
        ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_one)).setText(mTitleButtonOne);
        ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_two)).setText(mTitleButtonTwo);

        mDialog.getWindow().findViewById(R.id.dialog_button_one).setOnClickListener(onClickListenerButtonOne);
        mDialog.getWindow().findViewById(R.id.dialog_button_two).setOnClickListener(onClickListenerButtonTwo);
    }

    /**
     * Getter for Dialog object
     * @return mDialog
     */
    public Dialog getDialog() {
        return mDialog;
    }
}
