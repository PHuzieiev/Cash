package com.apps.newstudio.cash.ui.activities;

import android.Manifest;
import android.content.DialogInterface;
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
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterOrganizationOrCurrency;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataOrganizationOrCurrency;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.ui.dialogs.DialogList;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    private String mShortTitle, mSecondTitle;
    private String mTitleOfDialogFilter;
    private String[] mItemsDialogSort;
    private List<RecyclerViewDataOrganizationOrCurrency> mData;
    private List<RecyclerViewDataDialogList> mDataForDialogList;
    private DialogList mDialogSort;
    private RecyclerViewAdapterOrganizationOrCurrency mAdapter;
    private PreferenceManager mPreferenceManager;

    /**
     * Creates all elements and do all work to show information in elements
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        ButterKnife.bind(this);
        mPreferenceManager=DataManager.getInstance().getPreferenceManager();
        setupToolbar();
        setLang();
        getDataForDialogSort();
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

    /**
     * if item is selected, Dialog is opened
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.order) {
            createDialogSort();
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
                mSecondTitle = getString(R.string.currency_activity_count_eng);
                mTitleOfDialogFilter = getString(R.string.menu_order_item_title_eng);
                mItemsDialogSort = getResources().getStringArray(R.array.currencies_order_by_options_eng);
            }

            @Override
            public void ukrLanguage() {
                mSecondTitle = getString(R.string.currency_activity_count_ukr);
                mTitleOfDialogFilter = getString(R.string.menu_order_item_title_ukr);
                mItemsDialogSort = getResources().getStringArray(R.array.currencies_order_by_options_ukr);
            }

            @Override
            public void rusLanguage() {
                mSecondTitle = getString(R.string.currency_activity_count_rus);
                mTitleOfDialogFilter = getString(R.string.menu_order_item_title_rus);
                mItemsDialogSort = getResources().getStringArray(R.array.currencies_order_by_options_rus);
            }
        };
    }

    /**
     * Initializes some String object using main Intent
     */
    public void setData() {
        mShortTitle = getIntent().getStringExtra(ConstantsManager.CURRENCY_SHORT_FORM);
        shortForm.setText(mShortTitle.toUpperCase());
        titleEditText.setText(getIntent().getStringExtra(ConstantsManager.CURRENCY_TITLE).toUpperCase());
    }

    /**
     * Gets data for list and sets text information for countTextView object
     */
    public void prepareDataForList() {
        mData = DataManager.getInstance().getDatabaseManager().getOrganizationsByCurrency(mShortTitle);
        countTextView.setText(mSecondTitle + mData.size());
        sortMainList();
    }

    /**
     * Does sort for list
     */
    public void sortMainList(){
        switch (DataManager.getInstance().getPreferenceManager().getCurrenciesSortParameter()) {
            case ConstantsManager.CURRENCY_SORT_PARAMETER_PURCHASE:
                mData = sortCurrenciesByPurchase(mData);
                break;
            case ConstantsManager.CURRENCY_SORT_PARAMETER_SALE:
                mData = sortCurrenciesBySale(mData);
                break;
        }
    }

    /**
     * Sorts list using values from Ask field of items in Currency List
     * @param data main list
     * @return sorted list
     */
    public List<RecyclerViewDataOrganizationOrCurrency> sortCurrenciesBySale(List<RecyclerViewDataOrganizationOrCurrency> data){
        for(int i=data.size()-1;i>0;i--){
            for (int j=0;j<i;j++){
                if(Double.parseDouble(data.get(j).getCurrency().getAsk())>
                        Double.parseDouble(data.get(j+1).getCurrency().getAsk())){
                    RecyclerViewDataOrganizationOrCurrency tmp = data.get(j);
                    data.set(j,data.get(j+1));
                    data.set(j+1,tmp);
                }
            }
        }
        return data;
    }

    /**
     * Sorts list using values from Bid field of items in Currency List
     * @param data main list
     * @return sorted list
     */
    public List<RecyclerViewDataOrganizationOrCurrency> sortCurrenciesByPurchase(List<RecyclerViewDataOrganizationOrCurrency> data){
        for(int i=data.size()-1;i>0;i--){
            for (int j=0;j<i;j++){
                if(Double.parseDouble(data.get(j).getCurrency().getBid())<
                        Double.parseDouble(data.get(j+1).getCurrency().getBid())){
                    RecyclerViewDataOrganizationOrCurrency tmp = data.get(j);
                    data.set(j,data.get(j+1));
                    data.set(j+1,tmp);
                }
            }
        }
        return data;
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
                        if(getIntent().getIntExtra(ConstantsManager.START_ACTIVITY_MODE,0)==
                        ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE) {
                            intent = new Intent();
                        }else{
                            intent = new Intent(CurrencyActivity.this,OrganizationActivity.class);
                            intent.putExtra(ConstantsManager.START_ACTIVITY_MODE, ConstantsManager.CURRENCY_ACTIVITY_REQUEST_CODE);
                        }
                        intent.putExtra(ConstantsManager.ORGANIZATION_ID, mData.get(position).getOrganization().getId());
                        switch (DataManager.getInstance().getPreferenceManager().getLanguage()){
                            case ConstantsManager.LANGUAGE_ENG:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE,
                                        mData.get(position).getOrganization().getTitleEng());
                                break;
                            case ConstantsManager.LANGUAGE_RUS:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE,
                                        mData.get(position).getOrganization().getTitleRus());
                                break;
                            case ConstantsManager.LANGUAGE_UKR:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE,
                                        mData.get(position).getOrganization().getTitleUkr());
                                break;
                        }
                        intent.putExtra(ConstantsManager.ORGANIZATION_DATE, mData.get(position).getOrganization().getDate());
                        intent.putExtra(ConstantsManager.ORGANIZATION_PHONE, mData.get(position).getOrganization().getPhone());
                        intent.putExtra(ConstantsManager.ORGANIZATION_TYPE, mData.get(position).getOrganization().getOrgType());

                        if(getIntent().getIntExtra(ConstantsManager.START_ACTIVITY_MODE,0)==
                                ConstantsManager.ORGANIZATION_ACTIVITY_REQUEST_CODE) {
                            setResult(ConstantsManager.CURRENCY_ACTIVITY_RESULT_CODE_CHANGE_DATA, intent);
                            finish();
                        }else{
                            startActivityForResult(intent,ConstantsManager.CURRENCY_ACTIVITY_REQUEST_CODE);
                        }
                    }
                }, new RecyclerViewAdapterOrganizationOrCurrency.ActionForIconTwo() {
            @Override
            public void action(int position) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mData.get(position)
                        .getOrganization().getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(CashApplication.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(intent);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * Updates main list using new information
     */
    public void updateList() {
        mData.clear();
        prepareDataForList();
        mAdapter.setDataTwo(mData);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Gets data for Dialog of Sort
     */
    public void getDataForDialogSort() {
        mDataForDialogList = new ArrayList<>();
        for (int i = 0; i < mItemsDialogSort.length; i++) {
            mDataForDialogList.add(new RecyclerViewDataDialogList(false, mItemsDialogSort[i],
                    mItemsDialogSort[i], mItemsDialogSort[i]));
        }
        mDataForDialogList = setParameterForDialogSort(mDataForDialogList);
    }

    /**
     * Sets boolean value for items in list for Sort Dialog
     * @param data main list of Sort Dialog
     * @return changed main list of Sort Dialog
     */
    public List<RecyclerViewDataDialogList> setParameterForDialogSort(List<RecyclerViewDataDialogList> data) {
        for (int i = 0; i < data.size(); i++) {
            data.get(i).setChecked(false);
        }
        switch (DataManager.getInstance().getPreferenceManager().getCurrenciesSortParameter()) {
            case ConstantsManager.CURRENCY_SORT_PARAMETER_PURCHASE:
                data.get(1).setChecked(true);
            break;
            case ConstantsManager.CURRENCY_SORT_PARAMETER_SALE:
                data.get(2).setChecked(true);
            break;
            default:
                data.get(0).setChecked(true);
            break;
        }
        return data;
    }

    /**
     * Creates DialogList object for making sort of main list in Activity
     */
    public void createDialogSort() {
        mDialogSort = new DialogList(CurrencyActivity.this,
                mTitleOfDialogFilter, mDataForDialogList, null,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        switch (position){
                            case 0:
                                DataManager.getInstance().getPreferenceManager()
                                        .setCurrenciesSortParameter(ConstantsManager.CURRENCY_SORT_PARAMETER_TITLE);
                                break;
                            case 1:
                                DataManager.getInstance().getPreferenceManager()
                                        .setCurrenciesSortParameter(ConstantsManager.CURRENCY_SORT_PARAMETER_PURCHASE);
                                break;
                            case 2:
                                DataManager.getInstance().getPreferenceManager()
                                        .setCurrenciesSortParameter(ConstantsManager.CURRENCY_SORT_PARAMETER_SALE);
                                break;
                        }
                        mDataForDialogList = setParameterForDialogSort(mDataForDialogList);
                        mDialogSort.updateList(mDataForDialogList);
                        mDialogSort.getDialog().dismiss();
                    }
                });
        mDialogSort.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateList();
            }
        });

        ((ImageView)mDialogSort.getDialog().getWindow().findViewById(R.id.dialog_list_done)).setImageResource(R.drawable.ic_tr);
        mDialogSort.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackgroundColor(getResources().getColor(R.color.tr));
    }

    /**
     * Change some information in Activity or closes Activity
     * @param requestCode
     * @param resultCode value which defines what will be done
     * @param data object which is contains information from previous Activity
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==ConstantsManager.ORGANIZATION_ACTIVITY_RESULT_CODE_CHANGE_DATA){
            data.putExtra(ConstantsManager.START_ACTIVITY_MODE,0);
            setIntent(data);
            setData();
            createList();
        }
        if(resultCode==ConstantsManager.ACTIVITY_RESULT_CODE_OPEN_CONVERTER){
            setResult(ConstantsManager.ACTIVITY_RESULT_CODE_OPEN_CONVERTER);
            finish();
        }
    }

    /**
     * OnClick event for FAB object in Activity, opens Converter with special parameters
     */
    @OnClick(R.id.fab)
    public void showConverter(){
        setResult(ConstantsManager.ACTIVITY_RESULT_CODE_OPEN_CONVERTER);
        mPreferenceManager.setConverterOrganizationId(mData.get(0).getOrganization().getId());
        mPreferenceManager.setConverterCurrencyShortForm(mShortTitle);
        mPreferenceManager.setConverterAction(ConstantsManager.CONVERTER_ACTION_SALE);
        mPreferenceManager.setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
        mPreferenceManager.setConverterValue(ConstantsManager.CONVERTER_VALUE_DEFAULT);
        mPreferenceManager.setTemplateId(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
        finish();
    }
}
