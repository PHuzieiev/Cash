package com.apps.newstudio.cash.ui.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterFragment;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataFragment;
import com.apps.newstudio.cash.data.adapters.RecyclerViewLangFragment;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.ui.activities.CurrencyActivity;
import com.apps.newstudio.cash.ui.activities.MainActivity;
import com.apps.newstudio.cash.ui.activities.OrganizationActivity;
import com.apps.newstudio.cash.ui.dialogs.DialogList;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CurrenciesFragment extends Fragment {

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;

    static final String TEG = ConstantsManager.TAG + "Curr Fragment";
    private String mTitleOfDialogFilter;

    private SearchView mSearchView = null;
    private Unbinder mUnbinder;
    private DialogList mDialogFilter;
    private DatabaseManager mDatabaseManager;
    private MenuItem mMenuItemSearch;
    private List<RecyclerViewDataFragment> mMainDataForList;
    private List<RecyclerViewDataDialogList> mDataForDialogList;
    private RecyclerViewAdapterFragment mRecycleViewAdapter;
    private RecyclerViewLangFragment mRecycleViewLang;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currencies, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDatabaseManager = DataManager.getInstance().getDatabaseManager();
        mDataForDialogList = mDatabaseManager.getDataForListDialogCurrencies();
        checkDialogListData();
        setLang();
        createList();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        String queryHint = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()){
            case ConstantsManager.LANGUAGE_ENG:
                inflater.inflate(R.menu.menu_fragment_eng, menu);
                queryHint=getString(R.string.menu_item_search_hint_eng);
                break;
            case ConstantsManager.LANGUAGE_RUS:
                inflater.inflate(R.menu.menu_fragment_rus, menu);
                queryHint=getString(R.string.menu_item_search_hint_rus);
                break;
            case ConstantsManager.LANGUAGE_UKR:
                inflater.inflate(R.menu.menu_fragment_ukr, menu);
                queryHint=getString(R.string.menu_item_search_hint_ukr);
                break;
        }
        mMenuItemSearch = menu.findItem(R.id.search_in_list);
        mSearchView = (SearchView) mMenuItemSearch.getActionView();
        mSearchView.setQueryHint(queryHint);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DataManager.getInstance().getPreferenceManager().setCurrenciesSearchParameter(newText);
                updateList();
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                DataManager.getInstance().getPreferenceManager().setCurrenciesSearchParameter("");
                updateList();
                return false;
            }
        });
        if(!DataManager.getInstance().getPreferenceManager().getCurrenciesSearchParameter().equals("")) {
            mSearchView.setQuery(DataManager.getInstance().getPreferenceManager().getCurrenciesSearchParameter(),false);
            mSearchView.setIconified(false);
        }
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment("", getString(R.string.currency_activity_count_eng),
                        "", getString(R.string.org_list_item_button_eng));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_eng));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_eng);
            }

            @Override
            public void ukrLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment("", getString(R.string.currency_activity_count_ukr),
                        "", getString(R.string.org_list_item_button_ukr));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_ukr));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_ukr);
            }
            @Override
            public void rusLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment("", getString(R.string.currency_activity_count_rus),
                        "", getString(R.string.org_list_item_button_rus));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_rus));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_rus);
            }
        };
    }

    public void prepareDataForList() {
        mMainDataForList = mDatabaseManager.getAllCurrenciesForCurrenciesFragment(true);
    }

    public void createList() {
        prepareDataForList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecycleViewAdapter = new RecyclerViewAdapterFragment(mMainDataForList,
                mRecycleViewLang,
                new RecyclerViewAdapterFragment.ActionForItem() {
                    @Override
                    public void action(int position) {
                        Intent intent = new Intent(((MainActivity) getActivity()).getContext(),CurrencyActivity.class);
                        intent.putExtra(ConstantsManager.CURRENCY_SHORT_FORM, mMainDataForList.get(position)
                                .getCurrency()
                                .getShortTitle());
                        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
                            case ConstantsManager.LANGUAGE_ENG:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mMainDataForList.get(position)
                                        .getCurrency().getTitleEng());
                                break;
                            case ConstantsManager.LANGUAGE_RUS:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mMainDataForList.get(position)
                                        .getCurrency().getTitleRus());
                                break;
                            case ConstantsManager.LANGUAGE_UKR:
                                intent.putExtra(ConstantsManager.CURRENCY_TITLE, mMainDataForList.get(position)
                                        .getCurrency().getTitleUkr());
                                break;
                        }
                        startActivityForResult(intent,ConstantsManager.CURRENCY_FRAGMENT_REQUEST_CODE);
                    }
                });
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    public void updateList() {
        mMainDataForList.clear();
        prepareDataForList();
        mRecycleViewAdapter.setCurrencies(mMainDataForList);
        mRecycleViewAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.fragment_fab)
    public void getFilter() {
        mDialogFilter = new DialogList(((MainActivity) getActivity()).getContext(),
                mTitleOfDialogFilter, mDataForDialogList, null,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (position != 0) {
                            if (mDataForDialogList.get(position).isChecked()) {
                                mDataForDialogList.get(position).setChecked(false);
                            } else {
                                mDataForDialogList.get(position).setChecked(true);
                            }
                            int checked = 0;
                            m:
                            for (int i = 1; i < mDataForDialogList.size(); i++) {
                                if (mDataForDialogList.get(i).isChecked()) {
                                    checked = 1;
                                    break m;
                                }
                            }
                            if (checked == 0) {
                                mDataForDialogList.get(0).setChecked(true);
                            } else {
                                mDataForDialogList.get(0).setChecked(false);
                            }
                        } else {
                            for (int i = 0; i < mDataForDialogList.size(); i++) {
                                if (i == 0) {
                                    mDataForDialogList.get(i).setChecked(true);
                                } else {
                                    mDataForDialogList.get(i).setChecked(false);
                                }
                            }
                        }
                        mDialogFilter.updateList(mDataForDialogList);
                    }
                });
        mDialogFilter.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                DataManager.getInstance().getPreferenceManager().setCurrenciesFilterParameter(getFilterParameter());
                updateList();
            }
        });

        mDialogFilter.getDialog().getWindow().findViewById(R.id.dialog_list_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogFilter.getDialog().dismiss();
            }
        });

    }

    public void checkDialogListData() {
        String filter = DataManager.getInstance().getPreferenceManager().getOrganizationsFilterParameter();
        if (!filter.equals("")) {
            mDataForDialogList.get(0).setChecked(false);
            for(int i=1;i<mDataForDialogList.size();i++){
                if(filter.contains(mDataForDialogList.get(i).getTitleEng())){
                    mDataForDialogList.get(i).setChecked(true);
                }
            }
        }
    }

    public String getFilterParameter() {
        String result = "(";
        if (mDataForDialogList.get(0).isChecked()) {
            return "";
        } else {
            for (RecyclerViewDataDialogList data : mDataForDialogList) {
                if (data.isChecked()) {
                    result = result + "\"" + data.getTitleEng() + "\", ";
                }
            }
            result = result.substring(0, result.length() - 2) + ")";
            return result;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==ConstantsManager.ACTIVITY_RESULT_CODE_OPEN_CONVERTER) {
            ((MainActivity) getActivity()).checkItemOfNavigationView(R.id.item_converter);
        }
    }
}
