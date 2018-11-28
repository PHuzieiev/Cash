package com.apps.newstudio.cash.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.utils.ConstantsManager;

public class BaseActivity extends AppCompatActivity {

    static final String TAG= ConstantsManager.TAG+"Base activity";
    protected Dialog mProgressDialog=null;


    /**
     * Shows Toast message
     * @param message is String object
     */
    public void showToast(String message){
        int toastPadding=getResources().getDimensionPixelSize(R.dimen.spacing_smaller_8dp);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(message);
        spannableStringBuilder.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.white)),
                0, message.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        Toast toast = Toast.makeText(this, spannableStringBuilder, Toast.LENGTH_LONG);
        View viewToast = toast.getView();
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            viewToast.setBackgroundDrawable(getResources().getDrawable(R.drawable.rectungle_for_toast));
        } else {
            viewToast.setBackground(getResources().getDrawable(R.drawable.rectungle_for_toast));
        }

        toast.show();
    }

    /**
     * Shows Toast and Log with error message
     * @param message is String object
     * @param e is Exception object
     */

    public void showError(String message, Exception e){
        showToast(message);
        Log.d(TAG,String.valueOf(e));
    }

    /**
     * Creates and shows Dialog with ProgressView
     */
    public void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog=new Dialog(this,R.style.custom_dialog);
            mProgressDialog.setCancelable(false);
            mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.dialog_progress);
        }else{
            mProgressDialog.show();
            mProgressDialog.setContentView(R.layout.dialog_progress);
        }
    }

    /**
     * Hides Dialog with ProgressView
     */
    public void hideProgressDialog(){
        if(mProgressDialog!=null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.hide();
            }
        }

    }

}
