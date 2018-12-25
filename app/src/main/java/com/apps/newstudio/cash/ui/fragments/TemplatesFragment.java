package com.apps.newstudio.cash.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterTemplates;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataTemplates;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.ui.activities.ConverterActivity;
import com.apps.newstudio.cash.ui.activities.MainActivity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TemplatesFragment extends Fragment {

    @BindView(R.id.list)
    public RecyclerView mRecyclerView;

    private Unbinder mUnbinder;
    private List<RecyclerViewDataTemplates> mMainDataForList;
    private DatabaseManager mDatabaseManager;
    private RecyclerViewAdapterTemplates mRecycleViewAdapter;
    private PreferenceManager mPreferenceManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        View view = inflater.inflate(R.layout.fragment_templates, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mDatabaseManager = DataManager.getInstance().getDatabaseManager();
        mPreferenceManager = DataManager.getInstance().getPreferenceManager();
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
     * Prepares data for main list of this fragment
     */
    public void prepareDataForList() {
        mMainDataForList = mDatabaseManager.getTemplateList();
        if (mMainDataForList.size() == 0) {
            new LanguageManager() {
                @Override
                public void engLanguage() {
                    ((MainActivity) getActivity()).showToast(getString(R.string.template_toast_list_empty_eng));
                }

                @Override
                public void ukrLanguage() {
                    ((MainActivity) getActivity()).showToast(getString(R.string.template_toast_list_empty_ukr));
                }

                @Override
                public void rusLanguage() {
                    ((MainActivity) getActivity()).showToast(getString(R.string.template_toast_list_empty_rus));
                }
            };
        } else {
            calculateValues();
        }
    }

    /**
     * Converts values
     */
    public void calculateValues() {
        for (int i = 0; i < mMainDataForList.size(); i++) {
            try {
                String mStartValue = mMainDataForList.get(i).getStartValue();
                String mDirection = mMainDataForList.get(i).getDirection();
                String mAction = mMainDataForList.get(i).getAction();

                String index = null;
                switch (mAction) {
                    case ConstantsManager.CONVERTER_ACTION_PURCHASE:
                        index = mMainDataForList.get(i).getPurchaseValue();
                        break;
                    case ConstantsManager.CONVERTER_ACTION_SALE:
                        index = mMainDataForList.get(i).getSaleValue();
                        break;
                }
                BigDecimal bDValue = new BigDecimal(mStartValue);
                BigDecimal bDIndex = new BigDecimal(index);
                BigDecimal bDResult;

                switch (mDirection) {
                    case ConstantsManager.CONVERTER_DIRECTION_FROM_UAH:
                        bDResult = bDValue.divide(bDIndex, 4, BigDecimal.ROUND_HALF_UP);
                        mMainDataForList.get(i).setFirstValue(mStartValue);
                        if (bDResult.compareTo(new BigDecimal("0")) == 0) {
                            mMainDataForList.get(i).setSecondValue("0");
                        } else {
                            mMainDataForList.get(i).setSecondValue(bDResult.stripTrailingZeros().toPlainString());
                        }
                        break;
                    case ConstantsManager.CONVERTER_DIRECTION_TO_UAH:
                        bDResult = bDValue.multiply(bDIndex);
                        if (bDResult.compareTo(new BigDecimal("0")) == 0) {
                            mMainDataForList.get(i).setFirstValue("0");
                        } else {
                            mMainDataForList.get(i).setFirstValue(bDResult.setScale(4, BigDecimal.ROUND_HALF_UP)
                                    .stripTrailingZeros().toPlainString());
                        }
                        mMainDataForList.get(i).setSecondValue(mStartValue);
                        break;
                }

            } catch (Exception e) {
                //Error
            }
        }

    }

    /**
     * Sets main parameters for RecyclerView object
     */
    public void createList() {
        prepareDataForList();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CashApplication.getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecycleViewAdapter = new RecyclerViewAdapterTemplates(mMainDataForList,
                new RecyclerViewAdapterTemplates.ActionForItem() {
                    @Override
                    public void action(int position) {
                        RecyclerViewDataTemplates template = mMainDataForList.get(position);
                        mPreferenceManager.setConverterOrganizationId(template.getOrganizationId());
                        mPreferenceManager.setConverterCurrencyShortForm(template.getShortCurrencyTitle());
                        mPreferenceManager.setConverterAction(template.getAction());
                        mPreferenceManager.setConverterDirection(template.getDirection());
                        mPreferenceManager.setConverterValue(template.getStartValue());
                        mPreferenceManager.setTemplateId(template.getId());
                        mPreferenceManager.setConverterRoot(ConstantsManager.CONVERTER_OPEN_FROM_TEMPLATES);

                        Intent intent = new Intent(((MainActivity) getActivity()).getContext(), ConverterActivity.class);
                        startActivityForResult(intent, ConstantsManager.TEMPLATES_FRAGMENT_REQUEST_CODE);

                    }
                },
                new RecyclerViewAdapterTemplates.ActionForIcon() {
                    @Override
                    public void action(int position) {
                        mDatabaseManager.deleteFromTemplatesById(mMainDataForList.get(position).getId());
                        updateList();
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
        mRecycleViewAdapter.setData(mMainDataForList);
        mRecycleViewAdapter.notifyDataSetChanged();
    }


    /**
     * Updates main list
     *
     * @param requestCode value of last request
     * @param resultCode  value of result
     * @param data        object which contains values from previous activity
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ConstantsManager.CONVERTER_ACTIVITY_RESULT_CODE_CHANGED) {
            updateList();
            mRecyclerView.scrollToPosition(mMainDataForList.size() - 1);
        }
    }

    /**
     * Method for onClick event of FAB object,
     */
    @OnClick(R.id.fragment_fab)
    public void openConverter() {
        mPreferenceManager.setConverterAction(ConstantsManager.CONVERTER_ACTION_SALE);
        mPreferenceManager.setConverterOrganizationId(ConstantsManager.EMPTY_STRING_VALUE);
        mPreferenceManager.setConverterCurrencyShortForm(ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_DEFAULT);
        mPreferenceManager.setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
        mPreferenceManager.setConverterValue(ConstantsManager.CONVERTER_VALUE_DEFAULT);
        mPreferenceManager.setTemplateId(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
        mPreferenceManager.setConverterRoot(ConstantsManager.CONVERTER_OPEN_FROM_TEMPLATES);
        Intent intent = new Intent(((MainActivity) getActivity()).getContext(), ConverterActivity.class);
        startActivityForResult(intent, ConstantsManager.TEMPLATES_FRAGMENT_REQUEST_CODE);
    }
}
