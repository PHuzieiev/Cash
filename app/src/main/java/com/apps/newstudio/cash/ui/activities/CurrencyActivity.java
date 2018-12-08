package com.apps.newstudio.cash.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.apps.newstudio.cash.data.adapters.RecycleViewAdapterOrganizationOrCurrency;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CurrencyActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.title_tv)
    public EditText titleEditText;

    @BindView(R.id.short_tv)
    public TextView shortForm;

    @BindView(R.id.count_tv)
    public TextView countTextView;

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;

    static final String TEG = ConstantsManager.TAG + "Cur Activity";
    private String mShortTitle;
    private List<OrganizationsEntity> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        ButterKnife.bind(this);
        mShortTitle = getIntent().getStringExtra(ConstantsManager.CURRENCY_SHORT_FORM);
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
        getMenuInflater().inflate(R.menu.menu_order, menu);
        MenuItem menuItemCall = menu.findItem(R.id.order);
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                menuItemCall.setTitle(getString(R.string.menu_order_item_title_eng));
                break;
            case ConstantsManager.LANGUAGE_RUS:
                menuItemCall.setTitle(getString(R.string.menu_order_item_title_rus));
                break;
            case ConstantsManager.LANGUAGE_UKR:
                menuItemCall.setTitle(getString(R.string.menu_order_item_title_ukr));
                break;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.order) {

        }
        return true;
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                countTextView.setText(getString(R.string.currency_activity_count_eng));
            }

            @Override
            public void ukrLanguage() {
                countTextView.setText(getString(R.string.currency_activity_count_ukr));
            }

            @Override
            public void rusLanguage() {
                countTextView.setText(getString(R.string.currency_activity_count_rus));
            }
        };
    }

    public void setData() {
        shortForm.setText(mShortTitle.toUpperCase());
        titleEditText.setText(getIntent().getStringExtra(ConstantsManager.CURRENCY_TITLE));
    }

    public void prepareDataForList() {
        mData = DataManager.getInstance().getDatabaseManager().getOrganizationsByCurrency(mShortTitle);
        countTextView.setText(countTextView.getText().toString() + mData.size());
    }

    public void createList() {
        prepareDataForList();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        RecycleViewAdapterOrganizationOrCurrency adapter = new RecycleViewAdapterOrganizationOrCurrency(mData,
                new RecycleViewAdapterOrganizationOrCurrency.ActionForIcon() {
                    @Override
                    public void action(int position) {

                    }
                }, new RecycleViewAdapterOrganizationOrCurrency.ActionForIconTwo() {
            @Override
            public void action(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mData.get(position).getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CashApplication.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(adapter);
    }
}
