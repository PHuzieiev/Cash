package com.apps.newstudio.cash.ui.activities;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.apps.newstudio.cash.R;

public class BaseActivity extends AppCompatActivity {

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
        viewToast.setPadding(toastPadding, toastPadding, toastPadding, toastPadding);

        viewToast.setBackground(getResources().getDrawable(R.drawable.rectangle_for_toast));

        toast.show();
    }



    /**
     * Creates and shows Dialog with ProgressView
     */
    public void showProgressDialog(){
        if(mProgressDialog==null){
            mProgressDialog = new Dialog(this);
            mProgressDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            Drawable drawable = new ColorDrawable(Color.TRANSPARENT);
            mProgressDialog.getWindow().setBackgroundDrawable(drawable);
            mProgressDialog.setContentView(R.layout.dialog_progress);
            mProgressDialog.show();
        }else{
            mProgressDialog.show();
        }
    }

    /**
     * Hides Dialog with ProgressView
     */
    public void hideProgressDialog(){
        if(mProgressDialog!=null){
            if(mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
        }

    }

}
