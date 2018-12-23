package com.apps.newstudio.cash.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.LanguageManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class AboutFragment extends Fragment {

    @BindView(R.id.about_title_tv)
    public EditText mTitle;

    @BindView(R.id.about_app_title_tv)
    public TextView mAppTitle;

    @BindView(R.id.about_text_info)
    public TextView mAbout;

    private Unbinder mUnbinder;
    private String mLink;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates main objects of Fragment object
     * @param inflater object for inflating process
     * @param container root for inflating process
     * @param savedInstanceState Bundle object of saved data
     * @return main object of fragment interface
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        setLang();
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
     * Sets main Language parameters of Strings, TextView objects
     */
    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mTitle.setText(getString(R.string.drawer_item_about_eng));
                mAppTitle.setText(getString(R.string.nav_header_title_eng));
                mLink=getString(R.string.finance_link_ukr);
                mAbout.setText(getString(R.string.about_text_eng));
            }

            @Override
            public void ukrLanguage() {
                mTitle.setText(getString(R.string.drawer_item_about_ukr));
                mAppTitle.setText(getString(R.string.nav_header_title_ukr));
                mLink=getString(R.string.finance_link_ukr);
                mAbout.setText(getString(R.string.about_text_ukr));
            }

            @Override
            public void rusLanguage() {
                mTitle.setText(getString(R.string.drawer_item_about_rus));
                mAppTitle.setText(getString(R.string.nav_header_title_rus));
                mLink=getString(R.string.finance_link_rus);
                mAbout.setText(getString(R.string.about_text_rus));
            }
        };

    }

    /**
     * Opens link to show information of resource
     */
    @OnClick(R.id.about_source_b)
    public void goOnPageOfSource(){
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(mLink));
        startActivity(intent);
    }
}
