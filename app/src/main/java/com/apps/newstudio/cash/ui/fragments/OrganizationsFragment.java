package com.apps.newstudio.cash.ui.fragments;

import android.Manifest;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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
import com.apps.newstudio.cash.data.adapters.RecyclerViewLangFragment;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
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


public class OrganizationsFragment extends Fragment {

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;

    private String mTitleOfDialogFilter;
    private String failureCall;

    private RecyclerViewLangFragment mRecycleViewLang;
    private List<OrganizationsEntity> mMainDataForList;
    private List<RecyclerViewDataDialogList> mDataForDialogList;
    private RecyclerViewAdapterFragment mRecycleViewAdapter;
    private SearchView mSearchView = null;
    private Unbinder mUnbinder;
    private DialogList mDialogFilter;
    private DatabaseManager mDatabaseManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * Creates main objects of Fragment object
     *
     * @param inflater           object for inflating process
     * @param container          root for inflating process
     * @param savedInstanceState Bundle object of saved data
     * @return main object of fragment interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizations, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDatabaseManager = DataManager.getInstance().getDatabaseManager();
        setLang();
        createList();
        return view;
    }

    /**
     * Does unbind process when Fragment object is on destroy stage
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * Changes main menu of Activity and sets parameters for SearchView object
     *
     * @param menu     Main menu for inflation
     * @param inflater object for inflation process
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        String queryHint = "";
        inflater.inflate(R.menu.menu_fragment, menu);
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                queryHint = getString(R.string.menu_item_search_hint_eng);
                break;
            case ConstantsManager.LANGUAGE_RUS:
                queryHint = getString(R.string.menu_item_search_hint_rus);
                break;
            case ConstantsManager.LANGUAGE_UKR:
                queryHint = getString(R.string.menu_item_search_hint_ukr);
                break;
        }

        MenuItem menuItemSearch = menu.findItem(R.id.search_in_list);
        mSearchView = (SearchView) menuItemSearch.getActionView();
        mSearchView.setQueryHint(queryHint);
        mSearchView.findViewById(android.support.v7.appcompat.R.id.search_button).setOnLongClickListener(null);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DataManager.getInstance().getPreferenceManager().setOrganizationsSearchParameter(newText);
                updateList();
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                DataManager.getInstance().getPreferenceManager().setOrganizationsSearchParameter("");
                updateList();
                return false;
            }
        });
        if (!DataManager.getInstance().getPreferenceManager().getOrganizationsSearchParameter().equals("")) {
            mSearchView.setQuery(DataManager.getInstance().getPreferenceManager().getOrganizationsSearchParameter(), false);
            mSearchView.setIconified(false);
        }
    }

    /**
     * Prepares data for main list of this fragment
     */
    public void prepareDataForList() {
        mMainDataForList = mDatabaseManager.getDataForFragmentOrganization();
    }

    /**
     * Sets main parameters for RecyclerView object
     */
    public void createList() {
        prepareDataForList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecycleViewAdapter = new RecyclerViewAdapterFragment(mMainDataForList,
                mRecycleViewLang,
                new RecyclerViewAdapterFragment.ActionForIcon() {
                    @Override
                    public void action(int position) {
                        if (ActivityCompat.checkSelfPermission(CashApplication.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ((MainActivity) getActivity()).showToast(failureCall);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mMainDataForList.get(position).getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                },
                new RecyclerViewAdapterFragment.ActionForItem() {
                    @Override
                    public void action(int position) {
                        Intent intent = new Intent(((MainActivity) getActivity()).getContext(), OrganizationActivity.class);
                        intent.putExtra(ConstantsManager.ORGANIZATION_ID, mMainDataForList.get(position).getId());
                        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
                            case ConstantsManager.LANGUAGE_ENG:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE, mMainDataForList.get(position).getTitleEng());
                                break;
                            case ConstantsManager.LANGUAGE_RUS:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE, mMainDataForList.get(position).getTitleRus());
                                break;
                            case ConstantsManager.LANGUAGE_UKR:
                                intent.putExtra(ConstantsManager.ORGANIZATION_TITLE, mMainDataForList.get(position).getTitleUkr());
                                break;
                        }
                        intent.putExtra(ConstantsManager.ORGANIZATION_DATE, mMainDataForList.get(position).getDate());
                        intent.putExtra(ConstantsManager.ORGANIZATION_PHONE, mMainDataForList.get(position).getPhone());
                        intent.putExtra(ConstantsManager.ORGANIZATION_TYPE, mMainDataForList.get(position).getOrgType());
                        startActivityForResult(intent, ConstantsManager.ORGANIZATION_FRAGMENT_REQUEST_CODE);
                    }
                });
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    /**
     * Updates main list of this fragment
     */
    public void updateList() {
        mMainDataForList.clear();
        prepareDataForList();
        mRecycleViewAdapter.setLang(mRecycleViewLang);
        mRecycleViewAdapter.setOrganizations(mMainDataForList);
        mRecycleViewAdapter.notifyDataSetChanged();
    }

    /**
     * Sets main Language parameters of Strings, TextView objects
     */
    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment(getString(R.string.org_list_item_type_bank_eng),
                        getString(R.string.nav_header_subtitle_eng),
                        getString(R.string.org_list_item_type_other_eng),
                        getString(R.string.org_list_item_button_eng));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_eng));
                }

                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_eng);
                failureCall = getString(R.string.toast_phone_no_permissions_eng);
            }

            @Override
            public void ukrLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment(getString(R.string.org_list_item_type_bank_ukr),
                        getString(R.string.nav_header_subtitle_ukr),
                        getString(R.string.org_list_item_type_other_ukr),
                        getString(R.string.org_list_item_button_ukr));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_ukr));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_ukr);
                failureCall = getString(R.string.toast_phone_no_permissions_ukr);
            }

            @Override
            public void rusLanguage() {
                mRecycleViewLang = new RecyclerViewLangFragment(getString(R.string.org_list_item_type_bank_rus),
                        getString(R.string.nav_header_subtitle_rus),
                        getString(R.string.org_list_item_type_other_rus),
                        getString(R.string.org_list_item_button_rus));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_rus));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_rus);
                failureCall = getString(R.string.toast_phone_no_permissions_rus);
            }
        };
    }

    /**
     * Method for onClick event of FAB object,
     * creates dialog for filtration of main list
     */
    @OnClick(R.id.fragment_fab)
    public void getFilter() {
        mDataForDialogList = mDatabaseManager.getDataForListDialogCurrencies();
        checkDialogListData();
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
                            for (int i = 1; i < mDataForDialogList.size(); i++) {
                                if (mDataForDialogList.get(i).isChecked()) {
                                    checked = 1;
                                    break;
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
                DataManager.getInstance().getPreferenceManager().setOrganizationsFilterParameter(getFilterParameter());
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

    /**
     * Sets checked parameter for items of RecyclerViewDataDialogList list object of Filter dialog
     */
    public void checkDialogListData() {
        String filter = DataManager.getInstance().getPreferenceManager().getOrganizationsFilterParameter();
        if (!filter.equals("")) {
            mDataForDialogList.get(0).setChecked(false);
            for (int i = 1; i < mDataForDialogList.size(); i++) {
                if (filter.contains(mDataForDialogList.get(i).getTitleEng())) {
                    mDataForDialogList.get(i).setChecked(true);
                }
            }
        }
    }

    /**
     * Creates String object for filtration of main list,
     * String object contains values from checked items of Filter dialog
     *
     * @return object for filtration process
     */
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


}
