package com.apps.newstudio.cash.ui.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.utils.ConstantsManager;

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

    static final String TEG = ConstantsManager.TAG + "Org Activity";
    private String mOrganizationTitle;
    private String mTypeBank, mTypeOther;
    private OrganizationsEntity mData = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organization);
        ButterKnife.bind(this);
        setupToolbar();
        mData = DataManager.getInstance().getDatabaseManager()
                .getOrganizationData(getIntent().getStringExtra(ConstantsManager.ORGANIZATION_ID));
        setLang();
        setData();


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
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()){
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
            if (mData != null) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mData.getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return false;
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
                dateTextView.setText(getString(R.string.nav_header_subtitle_eng) + mData.getDate().substring(0, 10));
                getString(R.string.nav_header_subtitle_eng);
                mTypeBank = getString(R.string.org_list_item_type_bank_eng);
                mTypeOther = getString(R.string.org_list_item_type_other_eng);
                mOrganizationTitle = mData.getTitleEng();
            }

            @Override
            public void ukrLanguage() {
                dateTextView.setText(getString(R.string.nav_header_subtitle_ukr) + mData.getDate().substring(0, 10));
                getString(R.string.nav_header_subtitle_ukr);
                mTypeBank = getString(R.string.org_list_item_type_bank_ukr);
                mTypeOther = getString(R.string.org_list_item_type_other_ukr);
                mOrganizationTitle = mData.getTitleUkr();
            }

            @Override
            public void rusLanguage() {
                dateTextView.setText(getString(R.string.nav_header_subtitle_rus) + mData.getDate().substring(0, 10));
                getString(R.string.nav_header_subtitle_rus);
                mTypeBank = getString(R.string.org_list_item_type_bank_rus);
                mTypeOther = getString(R.string.org_list_item_type_other_rus);
                mOrganizationTitle = mData.getTitleRus();
            }
        };
    }

    public void setData() {
        switch (mData.getOrgType()) {
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
    }
}
