package com.apps.newstudio.cash.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.ui.fragments.AboutFragment;
import com.apps.newstudio.cash.ui.fragments.ConverterFragment;
import com.apps.newstudio.cash.ui.fragments.CurrenciesFragment;
import com.apps.newstudio.cash.ui.fragments.OrganizationsFragment;
import com.apps.newstudio.cash.ui.fragments.ResultsFragment;
import com.apps.newstudio.cash.utils.ConstantsManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    public DrawerLayout drawer;

    @BindView(R.id.nav_view)
    public NavigationView navigationView;

    static final String TEG = ConstantsManager.TAG + "Main activity";
    private DataManager mDataManager;

    private String mStringUpdateTitle;
    private int checkedItemId = 0;

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
        updateDate();

        if (savedInstanceState != null) {
            checkItemOfNavigationView(savedInstanceState.getInt(ConstantsManager.SAVED_FRAGMENT_ID));
        } else {
            checkItemOfNavigationView(R.id.item_converter);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstantsManager.SAVED_FRAGMENT_ID, checkedItemId);
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
        if(checkedItemId!=item.getItemId()&&item.getItemId()==R.id.item_organizations){
            DataManager.getInstance().getPreferenceManager().setOrganizationsFilterParameter("");
            DataManager.getInstance().getPreferenceManager().setOrganizationsSearchParameter("");
        }
        checkItemOfNavigationView(item.getItemId());
        return true;
    }

    public void checkItemOfNavigationView(int id) {
        if (id != checkedItemId) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


            switch (id) {
                case R.id.item_organizations:
                    fragmentTransaction.replace(R.id.frame_for_fragments, new OrganizationsFragment());
                    break;
                case R.id.item_currencies:
                    fragmentTransaction.replace(R.id.frame_for_fragments, new CurrenciesFragment());
                    break;
                case R.id.item_converter:
                    fragmentTransaction.replace(R.id.frame_for_fragments, new ConverterFragment());
                    break;
                case R.id.item_results:
                    fragmentTransaction.replace(R.id.frame_for_fragments, new ResultsFragment());
                    break;
                case R.id.item_update:
                    break;
                case R.id.item_language:
                    break;
                case R.id.item_about:
                    fragmentTransaction.replace(R.id.frame_for_fragments, new AboutFragment());
                    break;

            }
            if (id != R.id.item_update && id != R.id.item_language) {
                toolbar.setTitle(navigationView.getMenu().findItem(id).getTitle());
                navigationView.setCheckedItem(id);
                checkedItemId = id;
                fragmentTransaction.commit();
            }
        }
        drawer.closeDrawer(GravityCompat.START);
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

                mStringUpdateTitle = getString(R.string.nav_header_subtitle_eng);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_eng));
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

                mStringUpdateTitle = getString(R.string.nav_header_subtitle_ukr);

                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_ukr));
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

                mStringUpdateTitle = getString(R.string.nav_header_subtitle_rus);
                ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_title_tv))
                        .setText(getString(R.string.nav_header_title_rus));
            }
        };

    }

    public void updateDate() {
        String updateDate = mStringUpdateTitle + mDataManager.getPreferenceManager()
                .loadString(ConstantsManager.LAST_UPDATE_DATE, ConstantsManager.EMPTY_STRING_VALUE).substring(0, 10);
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_header_subtitle_tv))
                .setText(updateDate);
    }

    public Context getContext() {
        return MainActivity.this;
    }


}
