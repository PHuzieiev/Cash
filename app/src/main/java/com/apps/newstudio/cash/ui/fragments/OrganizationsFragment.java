package com.apps.newstudio.cash.ui.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.apps.newstudio.cash.data.adapters.RecycleViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecycleViewAdapterFragment;
import com.apps.newstudio.cash.data.adapters.RecycleViewDataAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecycleViewLangFragment;
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


    static final String TEG = ConstantsManager.TAG + "Org Fragment";
    private String mTitleOfDialogFilter;

    private RecycleViewLangFragment mRecycleViewLang;
    private List<OrganizationsEntity> mMainDataForList;
    private List<RecycleViewDataAdapterDialogList> mDataForDialogList;
    private RecycleViewAdapterFragment mRecycleViewAdapter;

    private SearchView mSearchView = null;
    private Unbinder mUnbinder;
    private DialogList mDialogFilter;
    private DatabaseManager mDatabaseManager;
    private MenuItem mMenuItemSearch;

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_organizations, container, false);
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
        inflater.inflate(R.menu.main, menu);
        mMenuItemSearch = menu.findItem(R.id.search_in_list);
        mSearchView = (SearchView) mMenuItemSearch.getActionView();

        switch (DataManager.getInstance().getPreferenceManager().getLanguage()){
            case ConstantsManager.LANGUAGE_ENG:
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_eng));
                mMenuItemSearch.setTitle(getString(R.string.menu_item_search_title_eng));
                break;
            case ConstantsManager.LANGUAGE_RUS:
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_rus));
                mMenuItemSearch.setTitle(getString(R.string.menu_item_search_title_rus));
                break;
            case ConstantsManager.LANGUAGE_UKR:
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_ukr));
                mMenuItemSearch.setTitle(getString(R.string.menu_item_search_title_ukr));
                break;
        }

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
        if(!DataManager.getInstance().getPreferenceManager().getOrganizationsSearchParameter().equals("")) {
            mSearchView.setQuery(DataManager.getInstance().getPreferenceManager().getOrganizationsSearchParameter(),false);
            mSearchView.setIconified(false);
        }

        super.onCreateOptionsMenu(menu, inflater);

    }

    public void prepareDataForList() {
        mMainDataForList = mDatabaseManager.getDataForFragmentOrganization();
    }

    public void createList() {
        prepareDataForList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecycleViewAdapter = new RecycleViewAdapterFragment(mMainDataForList,
                mRecycleViewLang, RecycleViewAdapterFragment.TYPE_ONE,
                new RecycleViewAdapterFragment.ActionForIcon() {
                    @Override
                    public void action(int position) {
                        try {
                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mMainDataForList.get(position).getPhone()));
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }catch (Exception e){
                            //Error
                        }
                    }
                },
                new RecycleViewAdapterFragment.ActionForItem() {
            @Override
            public void action(int position) {
                Intent intent = new Intent(((MainActivity) getActivity()).getContext(), OrganizationActivity.class);
                intent.putExtra(ConstantsManager.ORGANIZATION_ID, mMainDataForList.get(position).getId());
                startActivityForResult(intent,ConstantsManager.ORGANIZATION_FRAGMENT_REQUEST_CODE);
            }
        });
        mRecyclerView.setAdapter(mRecycleViewAdapter);
    }

    public void updateList() {
        mMainDataForList.clear();
        prepareDataForList();
        mRecycleViewAdapter.setOrganizations(mMainDataForList);
        mRecycleViewAdapter.notifyDataSetChanged();
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mRecycleViewLang = new RecycleViewLangFragment(getString(R.string.org_list_item_type_bank_eng),
                        getString(R.string.nav_header_subtitle_eng),
                        getString(R.string.org_list_item_type_other_eng),
                        getString(R.string.org_list_item_button_eng));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_eng));
                }

                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_eng);
            }

            @Override
            public void ukrLanguage() {
                mRecycleViewLang = new RecycleViewLangFragment(getString(R.string.org_list_item_type_bank_ukr),
                        getString(R.string.nav_header_subtitle_ukr),
                        getString(R.string.org_list_item_type_other_ukr),
                        getString(R.string.org_list_item_button_ukr));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_ukr));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_ukr);

            }

            @Override
            public void rusLanguage() {
                mRecycleViewLang = new RecycleViewLangFragment(getString(R.string.org_list_item_type_bank_rus),
                        getString(R.string.nav_header_subtitle_rus),
                        getString(R.string.org_list_item_type_other_rus),
                        getString(R.string.org_list_item_button_rus));

                if (mSearchView != null) {
                    mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_rus));
                }
                mTitleOfDialogFilter = getString(R.string.dialog_list_filter_title_rus);

            }
        };
    }

    @OnClick(R.id.fragment_fab)
    public void getFilter() {
        mDialogFilter = new DialogList(((MainActivity) getActivity()).getContext(),
                mTitleOfDialogFilter, mDataForDialogList,
                new RecycleViewAdapterDialogList.OnItemClickListener() {
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
            for (RecycleViewDataAdapterDialogList data : mDataForDialogList) {
                if (data.isChecked()) {
                    result = result + "\"" + data.getTitleEng() + "\", ";
                }
            }
            result = result.substring(0, result.length() - 2) + ")";
            return result;
        }
    }

}
