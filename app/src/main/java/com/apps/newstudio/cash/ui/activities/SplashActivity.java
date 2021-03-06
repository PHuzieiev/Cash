package com.apps.newstudio.cash.ui.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.ui.dialogs.DialogInfoWithTwoButtons;
import com.apps.newstudio.cash.ui.dialogs.DialogList;
import com.apps.newstudio.cash.utils.BackgroundUpdateReceiver;
import com.apps.newstudio.cash.utils.BackgroundUpdateTask;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.progress_bar)
    public ProgressBar mProgressBar;

    private DataManager mDataManager;
    private PreferenceManager mPreferenceManager;
    private DialogInfoWithTwoButtons mDialogInfoWithTwoButtons;
    private DialogList mDialogLang;
    private DatabaseManager.FinalActionSuccess mFinalActionSuccess;
    private DatabaseManager.FinalActionFailure mFinalActionFailure;
    private List<RecyclerViewDataDialogList> mDataForDialogList;

    private String mTitle;
    private String mMessage;
    private String mTitleButtonOne;
    private String mTitleButtonTwo;
    private String mDialogLangTitle;


    /**
     * Changes main background, initialises FinalActionSuccess, FinalActionFailure objects,
     * starts in new threat updating of data, starts DialogInfoWithTwoButtons object
     *
     * @param savedInstanceState object for loading saved data
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        setBackgroundTask();

        int data[] = new int[]{getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary),
                getResources().getColor(R.color.color_primary_dark),
                getResources().getColor(R.color.color_primary_dark)};

        GradientDrawable gd = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM,
                data);
        findViewById(R.id.splash_rl).setBackground(gd);

        mDataManager = DataManager.getInstance();
        mPreferenceManager = mDataManager.getPreferenceManager();
        getDialogInfo();

        mFinalActionFailure = new DatabaseManager.FinalActionFailure() {
            @Override
            public void finalFunctionFailure() {
                runOnUiThread(new Runnable() {
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mProgressBar.setVisibility(View.GONE);
                        startMainActivity();
                        finish();
                    }
                });
            }
        };
        mProgressBar.setVisibility(View.GONE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mPreferenceManager.loadString(ConstantsManager.LANGUAGE_CHOSEN_KEY, ConstantsManager.EMPTY_STRING_VALUE)
                        .equals(ConstantsManager.EMPTY_STRING_VALUE)) {
                    mPreferenceManager.saveString(ConstantsManager.LANGUAGE_CHOSEN_KEY, ConstantsManager.LANGUAGE_CHOSEN);
                    createDialogLang();
                } else {
                    updateData();
                }
            }
        }, 1000);
    }

    /**
     * Creates BackgroundUpdateTask objects
     */
    public void setBackgroundTask(){
        new BackgroundUpdateTask();
    }

    /**
     * Tries to update data, changes visibility of ProgressBar and sets OnDismissListener for DialogInfoWithTwoButtons
     */
    public void updateData() {
        mProgressBar.setVisibility(View.VISIBLE);
        mDialogInfoWithTwoButtons.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        mDataManager.getDatabaseManager().updateDataBase(mFinalActionSuccess, mFinalActionFailure);
    }

    /**
     * Creates Intent for starting MainActivity and starts it
     */
    public void startMainActivity() {

        mPreferenceManager.setConverterAction(ConstantsManager.CONVERTER_ACTION_SALE);
        mPreferenceManager.setConverterOrganizationId(ConstantsManager.EMPTY_STRING_VALUE);
        mPreferenceManager.setConverterCurrencyShortForm(ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_DEFAULT);
        mPreferenceManager.setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
        mPreferenceManager.setConverterValue(ConstantsManager.CONVERTER_VALUE_DEFAULT);
        mPreferenceManager.setTemplateId(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
        mPreferenceManager.setConverterRoot(ConstantsManager.EMPTY_STRING_VALUE);


        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * Defines all useful String object using LanguageManager
     */
    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_eng);
                mMessage = getString(R.string.dialog_info_message_eng);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_eng);
                mTitleButtonTwo = getString(R.string.dialog_info_button_two_eng);
                mDialogLangTitle = getString(R.string.drawer_item_language_eng);
            }

            @Override
            public void ukrLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_ukr);
                mMessage = getString(R.string.dialog_info_message_ukr);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_ukr);
                mTitleButtonTwo = getString(R.string.dialog_info_button_two_ukr);
                mDialogLangTitle = getString(R.string.drawer_item_language_ukr);
            }

            @Override
            public void rusLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_rus);
                mMessage = getString(R.string.dialog_info_message_rus);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_rus);
                mTitleButtonTwo = getString(R.string.dialog_info_button_two_rus);
                mDialogLangTitle = getString(R.string.drawer_item_language_rus);
            }
        };
    }

    /**
     * Creates DialogInfoWithTwoButtons object
     */
    public void getDialogInfo() {
        setLang();
        mDialogInfoWithTwoButtons = new DialogInfoWithTwoButtons(SplashActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialogInfoWithTwoButtons.getDialog().setOnDismissListener(null);
                        mDialogInfoWithTwoButtons.getDialog().dismiss();
                        updateData();
                    }
                }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogInfoWithTwoButtons.getDialog().dismiss();
            }
        }, mTitle, mMessage, mTitleButtonOne, mTitleButtonTwo);
    }

    /**
     * Creates DialogList object for choosing language of App
     */
    public void createDialogLang() {
        getDataForDialogLang();
        mDialogLang = new DialogList(SplashActivity.this,
                mDialogLangTitle, mDataForDialogList, null,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        changeLanguage(position);
                        mDataForDialogList = setParameterForDialogLang(mDataForDialogList);
                        mDialogLang.updateList(mDataForDialogList);
                        mDialogLang.getDialog().dismiss();
                    }
                });
        mDialogLang.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateData();
                getDialogInfo();
            }
        });
        ((ImageView) mDialogLang.getDialog().getWindow().findViewById(R.id.dialog_list_done)).setImageResource(R.drawable.ic_tr);
        mDialogLang.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackgroundColor(getResources().getColor(R.color.tr));
    }

    /**
     * Prepare main data for items in DialogList object
     */
    public void getDataForDialogLang() {
        mDataForDialogList = new ArrayList<>();
        mDataForDialogList.add(new RecyclerViewDataDialogList(false,
                getString(R.string.language_english_ukr), getString(R.string.language_english_rus),
                getString(R.string.language_english_eng)));
        mDataForDialogList.add(new RecyclerViewDataDialogList(false,
                getString(R.string.language_ukrainian_ukr), getString(R.string.language_ukrainian_rus),
                getString(R.string.language_ukrainian_eng)));
        mDataForDialogList.add(new RecyclerViewDataDialogList(false,
                getString(R.string.language_russian_ukr), getString(R.string.language_russian_rus),
                getString(R.string.language_russian_eng)));
        mDataForDialogList = setParameterForDialogLang(mDataForDialogList);
    }

    /**
     * Sets parameters for items using language of App
     *
     * @param data input data for list
     * @return changed data for list
     */
    public List<RecyclerViewDataDialogList> setParameterForDialogLang(List<RecyclerViewDataDialogList> data) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setChecked(false);
        }
        switch (mPreferenceManager.getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                data.get(0).setChecked(true);
                break;
            case ConstantsManager.LANGUAGE_UKR:
                data.get(1).setChecked(true);
                break;
            case ConstantsManager.LANGUAGE_RUS:
                data.get(2).setChecked(true);
                break;
        }
        return data;
    }

    /**
     * Changes language of App and translate all elements in SplashActivity
     *
     * @param position position of chosen item from DialogList object
     */
    public void changeLanguage(int position) {
        switch (position) {
            case 0:
                mPreferenceManager.setLanguage(ConstantsManager.LANGUAGE_ENG);
                break;
            case 1:
                mPreferenceManager.setLanguage(ConstantsManager.LANGUAGE_UKR);
                break;
            case 2:
                mPreferenceManager.setLanguage(ConstantsManager.LANGUAGE_RUS);
                break;
        }
        getDialogInfo();
    }

}
