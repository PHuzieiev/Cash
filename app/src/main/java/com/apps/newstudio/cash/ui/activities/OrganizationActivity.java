package com.apps.newstudio.cash.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        ButterKnife.bind(this);
        setupToolbar();
        setLang();
        setData();
        createList();
    }

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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.call) {
            if (mOrgPhone != null) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mOrgPhone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CashApplication.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
                startActivity(intent);
            }
        }
        return true;
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_eng);
                getString(R.string.nav_header_subtitle_eng);
                mTypeBank = getString(R.string.org_list_item_type_bank_eng);
                mTypeOther = getString(R.string.org_list_item_type_other_eng);
            }

            @Override
            public void ukrLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_ukr);
                getString(R.string.nav_header_subtitle_ukr);
                mTypeBank = getString(R.string.org_list_item_type_bank_ukr);
                mTypeOther = getString(R.string.org_list_item_type_other_ukr);
            }

            @Override
            public void rusLanguage() {
                mOrgDate = getString(R.string.nav_header_subtitle_rus);
                getString(R.string.nav_header_subtitle_rus);
                mTypeBank = getString(R.string.org_list_item_type_bank_rus);
                mTypeOther = getString(R.string.org_list_item_type_other_rus);
            }
        };
    }

    public void setData() {
        mOrganizationId = getIntent().getStringExtra(ConstantsManager.ORGANIZATION_ID);
        mOrganizationTitle = getIntent().getStringExtra(ConstantsManager.ORGANIZATION_TITLE);
        mOrgPhone = getIntent().getStringExtra(ConstantsManager.ORGANIZATION_PHONE);
        mOrgType = getIntent().getStringExtra(ConstantsManager.ORGANIZATION_TYPE);
        mOrgDate = mOrgDate + getIntent().getStringExtra(ConstantsManager.ORGANIZATION_DATE).substring(0,10);
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
        dateTextView.setText(mOrgDate);
    }

    public void prepareDataForList() {
        mData = DataManager.getInstance().getDatabaseManager().getCurrenciesByOrgId(mOrganizationId);
    }

    public void createList() {
        prepareDataForList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapterOrganizationOrCurrency adapter=new RecyclerViewAdapterOrganizationOrCurrency(mData,
                new RecyclerViewAdapterOrganizationOrCurrency.ActionForIcon() {
            @Override
            public void action(int position) {

                Intent intent = new Intent(OrganizationActivity.this,CurrencyActivity.class);
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
                startActivityForResult(intent,ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }
}
