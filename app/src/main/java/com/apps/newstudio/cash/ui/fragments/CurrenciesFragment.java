package com.apps.newstudio.cash.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.utils.ConstantsManager;


public class CurrenciesFragment extends Fragment {

    private SearchView mSearchView;
    static final String TEG = ConstantsManager.TAG + "Curr Fragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_currencies, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main,menu);
        MenuItem menuItemSearch = menu.findItem(R.id.search_in_list);
        mSearchView = (SearchView) menuItemSearch.getActionView();
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_eng));
            }

            @Override
            public void ukrLanguage() {
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_ukr));
            }

            @Override
            public void rusLanguage() {
                mSearchView.setQueryHint(getString(R.string.menu_item_search_hint_rus));
            }
        };

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                return false;
            }
        });
    }
}
