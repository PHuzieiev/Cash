package com.apps.newstudio.cash.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.LanguageManager;

public class DialogInfoWithTwoButtons {

    private Dialog mDialog;
    private Context mContext;
    private View.OnClickListener onClickListenerButtonOne;
    private View.OnClickListener onClickListenerButtonTwo;

    /**
     * Initialises Context, onClickListenerButtonOne, onClickListenerButtonTwo
     * @param context
     * @param onClickListenerButtonOne
     * @param onClickListenerButtonTwo
     */
    public DialogInfoWithTwoButtons(Context context,
                                    View.OnClickListener onClickListenerButtonOne,
                                    View.OnClickListener onClickListenerButtonTwo) {
        mContext = context;
        this.onClickListenerButtonOne = onClickListenerButtonOne;
        this.onClickListenerButtonTwo = onClickListenerButtonTwo;
        createDialog();
    }

    /**
     * Creates default Dialog object, sets OnClickListeners for buttons
     */

    public void createDialog(){
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mDialog.getWindow().setBackgroundDrawable(drawable);
        mDialog.setContentView(R.layout.dialog_info_with_two_buttons);

        new LanguageManager() {
            @Override
            public void engLanguage() {
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_title_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_title_warning_eng));
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_text_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_message_eng));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_one))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_one_eng));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_two))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_two_eng));
            }

            @Override
            public void ukrLanguage() {
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_title_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_title_warning_ukr));
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_text_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_message_ukr));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_one))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_one_ukr));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_two))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_two_ukr));

            }

            @Override
            public void rusLanguage() {
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_title_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_title_warning_rus));
                ((TextView)mDialog.getWindow().findViewById(R.id.dialog_text_info))
                        .setText(mContext.getResources().getString(R.string.dialog_info_message_rus));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_one))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_one_rus));
                ((Button)mDialog.getWindow().findViewById(R.id.dialog_button_two))
                        .setText(mContext.getResources().getString(R.string.dialog_info_button_two_rus));
            }
        };

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
