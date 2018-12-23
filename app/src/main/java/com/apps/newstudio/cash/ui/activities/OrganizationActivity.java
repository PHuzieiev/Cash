package com.apps.newstudio.cash.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterOrganizationOrCurrency;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrganizationActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.type_tv)
    public TextView typeTextView;

    @BindView(R.id.title_tv)
    public EditText titleEditText;

    @BindView(R.id.main_iv)
    public ImageView image;

    @BindView(R.id.date_tv)
    public TextView dateTextView;

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;

    static final String TEG = ConstantsManager.TAG + "Org Activity";
    private String mOrganizationTitle, mOrgType, mOrgPhone, mOrgDate;
    private String mTypeBank, mTypeOther;
    private String mOrganizationId = ConstantsManager.EMPTY_STRING_VALUE;
    private List<CurrenciesEntity> mData;
    private Intent mIntent;
    private RecyclerViewAdapterOrganizationOrCurrency mAdapter;
    private PreferenceManager mPreferenceManager;
    private String failureCall;

    /**
     * Creates all elements and do all work to show information in elements
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        ButterKnife.bind(this);
        mPreferenceManager = DataManager.getInstance().getPreferenceManager();
        setupToolbar();
        setLang();
        setData();
        createList();
    }

    /**
     * Sets all configuration parameters for ToolBar object
     */
    private void setupToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    /**
     * Closes Activity
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Sets menu for Activity
     * @param menu
     * @return value true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_phone, menu);
        MenuItem menuItemCall = menu.findItem(R.id.call);
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                menuItemCall.setTitle(getString(R.string.menu_phone_item_title_eng));
                break;
            case ConstantsManager.LANGUAGE_RUS:
                menuItemCall.setTitle(getString(R.string.menu_phone_item_title_rus));
                break;
            case ConstantsManager.LANGUAGE_UKR:
                menuItemCall.setTitle(getString(R.string.menu_phone_item_title_ukr));
                break;
        }
        return true;
    }

    /**
     * if item is selected, you will call organization
     * @param item
     * @return value true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.call) {
            if (mOrgPhone != null) {
                if (ActivityCompat.checkSelfPermission(CashApplication.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    showToast(failureCall);
                }else{
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mOrgPhone));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        }
        return true;
    }

    /**
     * Sets main Language parameters of Strings, TextView and EditText objects
     */
    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_eng);
                mTypeBank = getString(R.string.org_list_item_type_bank_eng);
                mTypeOther = getString(R.string.org_list_item_type_other_eng);
                failureCall = getString(R.string.toast_phone_no_permissions_eng);
            }

            @Override
            public void ukrLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_ukr);
                mTypeBank = getString(R.string.org_list_item_type_bank_ukr);
                mTypeOther = getString(R.string.org_list_item_type_other_ukr);
                failureCall = getString(R.string.toast_phone_no_permissions_ukr);
            }

            @Override
            public void rusLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_rus);
                mTypeBank = getString(R.string.org_list_item_type_bank_rus);
                mTypeOther = getString(R.string.org_list_item_type_other_rus);
                failureCall = getString(R.string.toast_phone_no_permissions_rus);
            }
        };
    }

    /**
     * Initializes some String object using main Intent
     */
    public void setData() {
        mIntent = getIntent();
        mOrganizationId = mIntent.getStringExtra(ConstantsManager.ORGANIZATION_ID);
        mOrganizationTitle = mIntent.getStringExtra(ConstantsManager.ORGANIZATION_TITLE);
        mOrgPhone = mIntent.getStringExtra(ConstantsManager.ORGANIZATION_PHONE);
        mOrgType = mIntent.getStringExtra(ConstantsManager.ORGANIZATION_TYPE);
        String date = mOrgDate + mIntent.getStringExtra(ConstantsManager.ORGANIZATION_DATE).substring(0, 10);
        switch (mOrgType) {
            case "1":
                typeTextView.setText(mTypeBank);
                image.setImageResource(R.drawable.bank_org);
                break;
            case "2":
                typeTextView.setText(mTypeOther);
                image.setImageResource(R.drawable.other_org);
                break;
        }
        if (mOrganizationTitle.contains("(")) {
            int end = mOrganizationTitle.length() - 1;
            m:
            for (int i = 0; i < mOrganizationTitle.length(); i++) {
                if (mOrganizationTitle.charAt(i) == '(') {
                    end = i - 1;
                    break m;
                }
            }
            mOrganizationTitle = mOrganizationTitle.substring(0, end);
        }
        titleEditText.setText(mOrganizationTitle);
        dateTextView.setText(date);
    }

    /**
     * Gets data for list
     */
    public void prepareDataForList() {
        mData = DataManager.getInstance().getDatabaseManager().getCurrenciesByOrgId(mOrganizationId);
    }

    /**
     * Create RecyclerView object, sets RecyclerViewAdapterOrganizationOrCurrency for this object
     */
    public void createList() {
        prepareDataForList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new RecyclerViewAdapterOrganizationOrCurrency(mData,
                new RecyclerViewAdapterOrganizationOrCurrency.ActionForIcon() {
                    @Override
                    public void action(int position) {
                        Intent intent;
                        if (getIntent().getIntExtra(ConstantsManager.START_ACTIVITY_MODE, 0) ==
                                ConstantsManager.CURRENCY_ACTIVITY_REQUEST_CODE) {
                            intent = new Intent();
                        } else {
                            intent = new Intent(OrganizationActivity.this, CurrencyActivity.class);
                            intent.putExtra(ConstantsManager.START_ACTIVITY_MODE, ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE);
                        }
                        intent.putExtra(ConstantsManager.CURRENCY_SHORT_FORM, mData.get(position).getShortTitle());
                        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
                            case ConstantsManager.LANGUAGE_ENG:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mData.get(position).getTitleEng());
                                break;
                            case ConstantsManager.LANGUAGE_RUS:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mData.get(position).getTitleRus());
                                break;
                            case ConstantsManager.LANGUAGE_UKR:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mData.get(position).getTitleUkr());
                                break;
                        }
                        intent.putExtra(ConstantsManager.START_ACTIVITY_MODE, ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE);
                        if (getIntent().getIntExtra(ConstantsManager.START_ACTIVITY_MODE, 0) ==
                                ConstantsManager.CURRENCY_ACTIVITY_REQUEST_CODE) {
                            setResult(ConstantsManager.ORGANIZATION_ACTIVITY_RESULT_CODE_CHANGE_DATA, intent);
                            finish();
                        } else {
                            startActivityForResult(intent, ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE);
                        }
                    }
                });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Change some information in Activity
     * @param requestCode
     * @param resultCode value which defines what will be done
     * @param data object which is contains information from previous Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == ConstantsManager.CURRENCY_ACTIVITY_RESULT_CODE_CHANGE_DATA) {
            data.putExtra(ConstantsManager.START_ACTIVITY_MODE,0);
            setIntent(data);
            setData();
            createList();
        }
    }


    /**
     * OnClick event for FAB object in Activity, opens Converter with special parameters
     */
    @OnClick(R.id.fab)
    public void showConverter(){
        mPreferenceManager.setConverterOrganizationId(mOrganizationId);
        mPreferenceManager.setConverterCurrencyShortForm(mData.get(0).getShortTitle());
        mPreferenceManager.setConverterAction(ConstantsManager.CONVERTER_ACTION_SALE);
        mPreferenceManager.setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
        mPreferenceManager.setConverterValue(ConstantsManager.CONVERTER_VALUE_DEFAULT);
        mPreferenceManager.setTemplateId(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
        mPreferenceManager.setConverterRoot(ConstantsManager.CONVERTER_OPEN_FROM_ORGANIZATION);
        Intent intent=new Intent(this,ConverterActivity.class);
        startActivity(intent);
    }
    /**
     * Getter for Context object of this Activity
     * @return Context object of OrganizationActivity
     */
    public Context getContext() {
        return OrganizationActivity.this;
    }
}
