package com.apps.newstudio.cash.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.data.network.models.Organization;
import com.apps.newstudio.cash.ui.dialogs.DialogInfoWithTwoButtons;
import com.apps.newstudio.cash.ui.dialogs.DialogList;
import com.apps.newstudio.cash.ui.fragments.AboutFragment;
import com.apps.newstudio.cash.ui.fragments.ConverterFragment;
import com.apps.newstudio.cash.ui.fragments.CurrenciesFragment;
import com.apps.newstudio.cash.ui.fragments.OrganizationsFragment;
import com.apps.newstudio.cash.ui.fragments.TemplatesFragment;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    private DataManager mDataManager;
    private PreferenceManager mPreferenceManager;
    private DialogInfoWithTwoButtons mDialogInfoWithTwoButtons;
    private DialogList mDialogLang;
    private DatabaseManager.FinalActionSuccess mFinalActionSuccess;
    private DatabaseManager.FinalActionFailure mFinalActionFailure;
    private Fragment mFragment;
    private FragmentManager mFragmentManager;
    private List<RecyclerViewDataDialogList> mDataForDialogList;

    private String mStringUpdateTitle, mTitle, mMessage, mTitleButtonOne, mTitleButtonTwo, mToastSuccessUpdate;
    private String mDialogLangTitle;
    private int checkedItemId = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mFragmentManager = getFragmentManager();

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mDataManager = DataManager.getInstance();
        mPreferenceManager = mDataManager.getPreferenceManager();
        setLang();
        prepareFinalActionsForUpdate();
        getDialogInfo();

        if (savedInstanceState != null) {
            checkItemOfNavigationView(savedInstanceState.getInt(ConstantsManager.SAVED_FRAGMENT_ID));
        } else {
            checkItemOfNavigationView(R.id.item_converter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantsManager.SAVED_FRAGMENT_ID, checkedItemId);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if (checkedItemId != item.getItemId() && item.getItemId() == R.id.item_organizations) {
            DataManager.getInstance().getPreferenceManager().setOrganizationsFilterParameter("");
            DataManager.getInstance().getPreferenceManager().setOrganizationsSearchParameter("");
        }

        if (checkedItemId != item.getItemId() && item.getItemId() == R.id.item_currencies) {
            DataManager.getInstance().getPreferenceManager().setCurrenciesFilterParameter("");
            DataManager.getInstance().getPreferenceManager().setCurrenciesSearchParameter("");
        }
        if (item.getItemId() == R.id.item_converter && item.getItemId() != checkedItemId) {
            mPreferenceManager.setConverterAction(ConstantsManager.CONVERTER_ACTION_SALE);
            mPreferenceManager.setConverterOrganizationId(ConstantsManager.EMPTY_STRING_VALUE);
            mPreferenceManager.setConverterCurrencyShortForm(ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_DEFAULT);
            mPreferenceManager.setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
            mPreferenceManager.setConverterValue(ConstantsManager.CONVERTER_VALUE_DEFAULT);
            mPreferenceManager.setTemplateId(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
            mPreferenceManager.setConverterRoot(ConstantsManager.EMPTY_STRING_VALUE);
        }
        checkItemOfNavigationView(item.getItemId());
        return true;
    }

    public void checkItemOfNavigationView(int id) {
        if (id != checkedItemId) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            switch (id) {
                case R.id.item_organizations:
                    mFragment = new OrganizationsFragment();
                    break;
                case R.id.item_currencies:
                    mFragment = new CurrenciesFragment();
                    break;
                case R.id.item_converter:
                    mFragment = new ConverterFragment();
                    break;
                case R.id.item_templates:
                    mFragment = new TemplatesFragment();
                    break;
                case R.id.item_update:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            updateData();
                        }
                    }, 600);
                    break;
                case R.id.item_language:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            createDialogLang();
                        }
                    }, 300);
                    break;
                case R.id.item_about:
                    mFragment = new AboutFragment();
                    break;

            }

            if (id != R.id.item_update && id != R.id.item_language) {
                if(id == R.id.item_converter||id == R.id.item_about){
                    toolbar.setTitle(ConstantsManager.EMPTY_STRING_VALUE);
                }else{
                    toolbar.setTitle(navigationView.getMenu().findItem(id).getTitle());
                }
                navigationView.setCheckedItem(id);
                checkedItemId = id;
                fragmentTransaction.replace(R.id.frame_for_fragments, mFragment);
                fragmentTransaction.commit();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
        updateDate();
    }


    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_eng));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_eng));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_eng));
                navigationMenu.findItem(R.id.item_templates).setTitle(getString(R.string.drawer_item_results_eng));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_eng));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_eng));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_eng));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_eng));
                mStringUpdateTitle = getString(R.string.nav_header_subtitle_eng);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_eng));
                mToastSuccessUpdate = getString(R.string.toast_update_success_eng);
                mDialogLangTitle = getString(R.string.drawer_item_language_eng);
            }

            @Override
            public void ukrLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_ukr));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_ukr));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_ukr));
                navigationMenu.findItem(R.id.item_templates).setTitle(getString(R.string.drawer_item_results_ukr));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_ukr));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_ukr));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_ukr));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_ukr));
                mStringUpdateTitle = getString(R.string.nav_header_subtitle_ukr);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_ukr));
                mToastSuccessUpdate = getString(R.string.toast_update_success_ukr);
                mDialogLangTitle = getString(R.string.drawer_item_language_ukr);
            }

            @Override
            public void rusLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_rus));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_rus));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_rus));
                navigationMenu.findItem(R.id.item_templates).setTitle(getString(R.string.drawer_item_results_rus));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_rus));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_rus));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_rus));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_rus));
                mStringUpdateTitle = getString(R.string.nav_header_subtitle_rus);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_rus));
                mToastSuccessUpdate = getString(R.string.toast_update_success_rus);
                mDialogLangTitle = getString(R.string.drawer_item_language_rus);
            }
        };

    }

    public void updateDate() {
        String updateDate = mStringUpdateTitle + mDataManager.getPreferenceManager()
                .loadString(ConstantsManager.LAST_UPDATE_DATE, ConstantsManager.EMPTY_STRING_VALUE).substring(0, 10);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle_tv))
                .setText(updateDate);
    }

    public Context getContext() {
        return MainActivity.this;
    }

    public void prepareFinalActionsForUpdate() {
        mFinalActionFailure = new DatabaseManager.FinalActionFailure() {
            @Override
            public void finalFunctionFailure() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        mDialogInfoWithTwoButtons.getDialog().show();
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
                        changeDataInFragmentAfterUpdate();
                    }
                });
            }
        };
    }

    public void updateData() {
        showProgressDialog();
        mDataManager.getDatabaseManager().updateDataBase(mFinalActionSuccess, mFinalActionFailure);
    }


    /**
     * Creates DialogInfoWithTwoButtons object
     */
    public void getDialogInfo() {

        mTitle = getString(R.string.dialog_info_title_warning_eng);
        mMessage = getString(R.string.dialog_info_message_eng);
        mTitleButtonOne = getString(R.string.dialog_info_button_one_eng);
        mTitleButtonTwo = getString(R.string.dialog_info_button_two_eng);
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_eng);
                mMessage = getString(R.string.toast_message_server_error_eng);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_eng);
                mTitleButtonTwo = getString(R.string.button_cancel_eng);
            }

            @Override
            public void ukrLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_ukr);
                mMessage = getString(R.string.toast_message_server_error_ukr);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_ukr);
                mTitleButtonTwo = getString(R.string.button_cancel_ukr);
            }

            @Override
            public void rusLanguage() {
                mTitle = getString(R.string.dialog_info_title_warning_rus);
                mMessage = getString(R.string.toast_message_server_error_rus);
                mTitleButtonOne = getString(R.string.dialog_info_button_one_rus);
                mTitleButtonTwo = getString(R.string.button_cancel_rus);
            }
        };

        mDialogInfoWithTwoButtons = new DialogInfoWithTwoButtons(MainActivity.this,
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
        }, mTitle, mMessage, mTitleButtonOne, mTitleButtonTwo);
    }

    public void changeDataInFragmentAfterUpdate() {
        hideProgressDialog();
        showToast(mToastSuccessUpdate);
        updateDate();
        switch (checkedItemId) {
            case R.id.item_organizations:
                ((OrganizationsFragment) mFragment).updateList();
                break;
            case R.id.item_currencies:
                ((CurrenciesFragment) mFragment).updateList();
                break;
            case R.id.item_converter:
                ((ConverterFragment) mFragment).getDefaultData();
                break;
            case R.id.item_templates:
                ((TemplatesFragment) mFragment).updateList();
                break;
        }
    }

    public void createDialogLang() {
        getDataForDialogLang();
        mDialogLang = new DialogList(MainActivity.this,
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
        ((ImageView) mDialogLang.getDialog().getWindow().findViewById(R.id.dialog_list_done)).setImageResource(R.drawable.ic_tr);
        mDialogLang.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackgroundColor(getResources().getColor(R.color.tr));
    }

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

        setLang();
        updateDate();

        switch (checkedItemId) {
            case R.id.item_organizations:
                ((OrganizationsFragment) mFragment).setLang();
                ((OrganizationsFragment) mFragment).updateList();
                break;
            case R.id.item_currencies:
                ((CurrenciesFragment) mFragment).setLang();
                ((CurrenciesFragment) mFragment).updateList();
                break;
            case R.id.item_converter:
                ((ConverterFragment) mFragment).getDefaultData();
                break;
            case R.id.item_templates:
                ((TemplatesFragment) mFragment).updateList();
                break;
            case R.id.item_about:
                ((AboutFragment) mFragment).setLang();
                break;

        }
        if (checkedItemId != R.id.item_converter) {
            toolbar.setTitle(navigationView.getMenu().findItem(checkedItemId).getTitle());
        }
    }
}
