package com.apps.newstudio.cash.ui.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.utils.ConstantsManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    private SearchView mSearchView;
    private MenuItem mSearchItem;

    static final String TAG = ConstantsManager.TAG + "Main activity";
    private DataManager mDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        mDataManager = DataManager.getInstance();
        setLang();

        checkItemOfNavigationView(R.id.item_converter);
        hideMenu();
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
        checkItemOfNavigationView(item.getItemId());
        return true;
    }

    public void checkItemOfNavigationView(int id){
        toolbar.setTitle(navigationView.getMenu().findItem(id).getTitle());
        navigationView.setCheckedItem(id);
        if (id == R.id.item_organizations) {
            showFloatingActionButton();
            fab.setImageResource(R.drawable.ic_filter);
            showMenu();
        } else if (id == R.id.item_currencies) {
            showFloatingActionButton();
            fab.setImageResource(R.drawable.ic_filter);
            showMenu();
        } else if (id == R.id.item_converter) {
            showFloatingActionButton();
            fab.setImageResource(R.drawable.ic_done);
            hideMenu();
        } else if (id == R.id.item_results) {
            hideFloatingActionButton();
            hideMenu();
        } else if (id == R.id.item_update) {

        } else if (id == R.id.item_language) {

        } else if (id == R.id.item_about) {
            hideFloatingActionButton();
            hideMenu();
        }
        drawer.closeDrawer(GravityCompat.START);
    }

    public void showFloatingActionButton() {
        fab.setVisibility(View.VISIBLE);
    }

    public void hideFloatingActionButton() {
        fab.setVisibility(View.GONE);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mSearchItem = menu.findItem(R.id.search_in_list);
        mSearchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
        mSearchView.setQueryHint(getResources().getString(R.string.menu_item_search_hint_eng));
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showToast(TAG);

                return true;
            }
        });
        setLang();
        mSearchItem.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    public void hideMenu() {
        if (mSearchItem != null) {
            mSearchItem.setVisible(false);
        }
    }

    public void showMenu() {
        if (mSearchItem != null) {
            mSearchItem.setVisible(true);
        }
    }


    @OnClick(R.id.fab)
    public void fabClick(View v) {
        showToast(TAG);
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_eng));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_eng));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_eng));
                navigationMenu.findItem(R.id.item_results).setTitle(getString(R.string.drawer_item_results_eng));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_eng));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_eng));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_eng));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_eng));

                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle_tv))
                        .setText(getString(R.string.nav_header_subtitle_eng));
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_eng));

                if (mSearchItem != null) {
                    mSearchView.setQueryHint(getResources().getString(R.string.menu_item_search_hint_eng));
                    mSearchItem.setTitle(getString(R.string.menu_item_search_title_eng));
                }
            }

            @Override
            public void ukrLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_ukr));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_ukr));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_ukr));
                navigationMenu.findItem(R.id.item_results).setTitle(getString(R.string.drawer_item_results_ukr));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_ukr));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_ukr));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_ukr));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_ukr));

                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle_tv))
                        .setText(getString(R.string.nav_header_subtitle_ukr));
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_ukr));

                if (mSearchItem != null) {
                    mSearchView.setQueryHint(getResources().getString(R.string.menu_item_search_hint_ukr));
                    mSearchItem.setTitle(getString(R.string.menu_item_search_title_ukr));
                }
            }

            @Override
            public void rusLanguage() {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.item_organizations).setTitle(getString(R.string.drawer_item_organizations_rus));
                navigationMenu.findItem(R.id.item_currencies).setTitle(getString(R.string.drawer_item_currencies_rus));
                navigationMenu.findItem(R.id.item_converter).setTitle(getString(R.string.drawer_item_converter_rus));
                navigationMenu.findItem(R.id.item_results).setTitle(getString(R.string.drawer_item_results_rus));
                navigationMenu.findItem(R.id.item_others).setTitle(getString(R.string.drawer_item_others_rus));
                navigationMenu.findItem(R.id.item_update).setTitle(getString(R.string.drawer_item_update_rus));
                navigationMenu.findItem(R.id.item_language).setTitle(getString(R.string.drawer_item_language_rus));
                navigationMenu.findItem(R.id.item_about).setTitle(getString(R.string.drawer_item_about_rus));

                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle_tv))
                        .setText(getString(R.string.nav_header_subtitle_rus));
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_rus));

                if (mSearchItem != null) {
                    mSearchView.setQueryHint(getResources().getString(R.string.menu_item_search_hint_rus));
                    mSearchItem.setTitle(getString(R.string.menu_item_search_title_rus));
                }
            }
        };

    }
}
