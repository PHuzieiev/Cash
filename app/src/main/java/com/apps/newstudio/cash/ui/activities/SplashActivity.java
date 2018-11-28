package com.apps.newstudio.cash.ui.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.ui.dialogs.DialogInfoWithTwoButtons;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;
import com.apps.newstudio.cash.utils.InternetConnection;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_progress_bar)
    public ProgressBar mProgressBar;

    private DataManager mDataManager;
    static final String TAG = ConstantsManager.TAG + "SplashActivity";
    private DialogInfoWithTwoButtons mDialogInfoWithTwoButtons;
    private DatabaseManager.FinalActionSuccess mFinalActionSuccess;
    private DatabaseManager.FinalActionFailure mFinalActionFailure;


    /**
     * Changes main background, initialises FinalActionSuccess, FinalActionFailure objects,
     * starts in new threat updating of data, starts DialogInfoWithTwoButtons object
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        int data[]=new int[]{getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary_dark),
                getResources().getColor(R.color.color_primary_dark)};

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                data);
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            findViewById(R.id.splash_rl).setBackgroundDrawable(gd);
        } else {
            findViewById(R.id.splash_rl).setBackground(gd);
        }

        mDataManager = DataManager.getInstance();
        getDialogInfo();

        mFinalActionFailure = new DatabaseManager.FinalActionFailure() {
            @Override
            public void finalFunctionFailure() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        if (!mDataManager.getDatabaseManager().isEmptyDatabase()) {
                            new LanguageManager() {
                                @Override
                                public void engLanguage() {
                                    showToast(getString(R.string.toast_message_server_error_eng));
                                }

                                @Override
                                public void ukrLanguage() {
                                    showToast(getString(R.string.toast_message_server_error_ukr));
                                }

                                @Override
                                public void rusLanguage() {
                                    showToast(getString(R.string.toast_message_server_error_rus));
                                }
                            };
                            startMainActivity();
                        } else {
                            mDialogInfoWithTwoButtons.getDialog().show();
                        }
                    }
                });
            }
        };

        mFinalActionSuccess = new DatabaseManager.FinalActionSuccess() {
            @Override
            public void finalFunctionSuccess() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        startMainActivity();
                        finish();
                    }
                });
            }
        };

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                updateData();
            }
        }, 2000);
    }

    /**
     * Tries to update data, changes visibility of ProgressBar and sets OnDismissListener for DialogInfoWithTwoButtons
     */
    public void updateData() {
        mDialogInfoWithTwoButtons.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        mProgressBar.setVisibility(View.VISIBLE);
        mDataManager.getDatabaseManager().updateDataBase(mFinalActionSuccess, mFinalActionFailure);
    }

    /**
     * Creates Intent for starting MainActivity and starts it
     */
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Creates DialogInfoWithTwoButtons object
     */
    public void getDialogInfo() {
        mDialogInfoWithTwoButtons = new DialogInfoWithTwoButtons(SplashActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialogInfoWithTwoButtons.getDialog().setOnDismissListener(null);
                        mDialogInfoWithTwoButtons.getDialog().dismiss();
                        new Handler().post(new Runnable() {
                            @Override
                            public void run() {
                                updateData();
                            }
                        });
                    }
                }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogInfoWithTwoButtons.getDialog().dismiss();
            }
        });
    }

}
