package com.apps.newstudio.cash.ui.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.LanguageManager;

public class DialogInfoCall {

    private Dialog mDialog;
    private Context mContext;
    private String mTitle;
    private String mMessage;
    private String mTitleButtonOne;

    /**
     * Initialises Context, onClickListenerButtonOne, onClickListenerButtonTwo
     *
     * @param context object which is used for creation Dialog object
     */
    public DialogInfoCall(Context context) {
        mContext = context;
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mTitle = mContext.getString(R.string.dialog_info_title_warning_eng);
                mMessage = mContext.getString(R.string.toast_phone_no_permissions_eng);
                mTitleButtonOne = mContext.getString(R.string.button_settings_title_eng);
            }

            @Override
            public void ukrLanguage() {
                mTitle = mContext.getString(R.string.dialog_info_title_warning_ukr);
                mMessage = mContext.getString(R.string.toast_phone_no_permissions_ukr);
                mTitleButtonOne = mContext.getString(R.string.button_settings_title_ukr);
            }

            @Override
            public void rusLanguage() {
                mTitle = mContext.getString(R.string.dialog_info_title_warning_rus);
                mMessage = mContext.getString(R.string.toast_phone_no_permissions_rus);
                mTitleButtonOne = mContext.getString(R.string.button_settings_title_rus);
            }
        };
        createDialog();
    }

    /**
     * Creates default Dialog object, sets OnClickListeners for buttons
     */
    private void createDialog() {
        mDialog = new Dialog(mContext);
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mDialog.getWindow().setBackgroundDrawable(drawable);
        mDialog.setContentView(R.layout.dialog_info_with_one_button);

        ((TextView) mDialog.getWindow().findViewById(R.id.dialog_title_info)).setText(mTitle);
        ((TextView) mDialog.getWindow().findViewById(R.id.dialog_text_info)).setText(mMessage);
        ((Button) mDialog.getWindow().findViewById(R.id.dialog_button_one)).setText(mTitleButtonOne);

        mDialog.getWindow().findViewById(R.id.dialog_button_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent appSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + mContext.getPackageName()));
                mContext.startActivity(appSettings);
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }
}
