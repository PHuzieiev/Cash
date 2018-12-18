package com.apps.newstudio.cash.ui.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Binder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.ui.fragments.ConverterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConverterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    public Toolbar toolbar;

    private ConverterFragment mConverterFragment;

    /**
     * Creates all elements and do all work to show information in elements
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);

        setupToolbar();
        mConverterFragment = new ConverterFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_for_fragments, mConverterFragment);
        fragmentTransaction.commit();
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
        try {
            mConverterFragment.finalRequest();
        }catch (Exception e){
            //Error
        }
    }

    /**
     * Getter for Context object
     * @return Context object of ConverterActivity
     */
    public Context getContext() {
        return ConverterActivity.this;
    }
}
