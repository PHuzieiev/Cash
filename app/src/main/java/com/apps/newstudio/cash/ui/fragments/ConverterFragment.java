package com.apps.newstudio.cash.ui.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.adapters.RecyclerViewAdapterDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogList;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataDialogListOrganizations;
import com.apps.newstudio.cash.data.adapters.RecyclerViewDataFragment;
import com.apps.newstudio.cash.data.adapters.TextWatcherAdapter;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.managers.DatabaseManager;
import com.apps.newstudio.cash.data.managers.LanguageManager;
import com.apps.newstudio.cash.data.managers.PreferenceManager;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.data.storage.models.TemplateEntity;
import com.apps.newstudio.cash.ui.activities.MainActivity;
import com.apps.newstudio.cash.ui.activities.SplashActivity;
import com.apps.newstudio.cash.ui.dialogs.DialogInfoWithTwoButtons;
import com.apps.newstudio.cash.ui.dialogs.DialogList;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ConverterFragment extends Fragment {

    @BindView(R.id.converter_count_tv)
    public TextView tVCount;

    @BindView(R.id.converter_input_value_currency_one_title)
    public TextView tVFirstCurrency;

    @BindView(R.id.converter_input_value_currency_two_title)
    public TextView tVSecondCurrency;

    @BindView(R.id.converter_title_tv)
    public EditText eTTitle;

    @BindView(R.id.converter_short_tv)
    public TextView tVShortTitle;

    @BindView(R.id.converter_currency_tv)
    public TextView tVChosenCurrency;

    @BindView(R.id.converter_organization_title_tv)
    public TextView tVChosenOrganizationTitle;

    @BindView(R.id.converter_organization_purchase_tv)
    public TextView tVChosenOrganizationPurchase;

    @BindView(R.id.converter_organization_sale_tv)
    public TextView tVChosenOrganizationSale;

    @BindView(R.id.converter_organization_date_tv)
    public TextView tVChosenOrganizationDate;

    @BindView(R.id.converter_sale_or_purchase_tv)
    public TextView tVChosenAction;

    @BindView(R.id.converter_input_value_currency_one_value)
    public EditText eTFirstValue;

    @BindView(R.id.converter_input_value_currency_two_value)
    public EditText eTSecondValue;

    private Unbinder mUnbinder;
    private PreferenceManager mPreferenceManager;
    private DatabaseManager mDatabaseManager;
    private List<RecyclerViewDataFragment> mMainListForActions;
    private List<RecyclerViewDataDialogList> mListForCurrencyDialog = new ArrayList<>();
    private List<RecyclerViewDataDialogList> mListForActionDialog = new ArrayList<>();
    private List<RecyclerViewDataDialogListOrganizations> mListForOrganizationsDialog = new ArrayList<>();
    private DialogList mDialog;
    private TextWatcherAdapter mTextWatcherAdapter;
    private DialogInfoWithTwoButtons mDialogInfoWithTwoButtons;

    static final String TEG = ConstantsManager.TAG + "Con Fragment";
    private String mCurrencyShortForm;
    private String mCurrencyDialogTitle;
    private String mOrganizationId;
    private String mOrganizationDialogTitle;
    private String mAction;
    private String mActionDialogTitle;
    private String mFirstCurrencyTitle;
    private String mSaleTitle;
    private String mPurchaseTitle;
    private String mCountTitle;
    private String mSaleValue;
    private String mPurchaseValue;
    private String mDate;
    private String mDirection;
    private String mStartValue;
    private String mToastFailure;
    private String mToastEdited;
    private String mToastCreated;
    private String mTitle;
    private String mMessage;
    private String mTitleButtonOne;
    private String mTitleButtonTwo;
    private String mTemplateId;

    private int mPositionOfCurInList = -1;
    private int mPositionOfOrgInList = -1;

    private boolean isConverted = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_converter, container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPreferenceManager = DataManager.getInstance().getPreferenceManager();
        mDatabaseManager = DataManager.getInstance().getDatabaseManager();
        mTextWatcherAdapter = new TextWatcherAdapter(eTFirstValue, eTSecondValue, false, false,
                new TextWatcherAdapter.SomeAction() {
                    @Override
                    public void action() {
                        convertValue();
                    }
                });
        getDefaultData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    public void getDefaultData() {
        mCurrencyShortForm = mPreferenceManager.getConverterCurrencyShortForm().toUpperCase();
        mOrganizationId = mPreferenceManager.getConverterOrganizationId();
        mAction = mPreferenceManager.getConverterAction();
        mStartValue = mPreferenceManager.getConverterValue();
        mDirection = mPreferenceManager.getConverterDirection();
        getMainListForActions();
    }

    public void getMainListForActions() {
        mMainListForActions = mDatabaseManager.getAllCurrenciesForCurrenciesFragment(false);
        checkDefaultData();
        setLang();
    }

    public void checkDefaultData() {
        mListForCurrencyDialog.clear();
        mListForOrganizationsDialog.clear();

        mPositionOfCurInList = -1;
        mPositionOfOrgInList = -1;
        for (int i = 0; i < mMainListForActions.size(); i++) {
            if (mMainListForActions.get(i).getCurrency().getShortTitle().toUpperCase().equals(mCurrencyShortForm)) {
                mPositionOfCurInList = i;
                List<OrganizationsEntity> org = mMainListForActions.get(i).getOrganizations();
                for (int j = 0; j < org.size(); j++) {
                    if (org.get(j).getId().equals(mOrganizationId)) {
                        mPositionOfOrgInList = j;
                    }
                }
            }
            mListForCurrencyDialog.add(new RecyclerViewDataDialogList(false,
                    mMainListForActions.get(i).getCurrency().getTitleUkr(),
                    mMainListForActions.get(i).getCurrency().getTitleRus(),
                    mMainListForActions.get(i).getCurrency().getTitleEng()));
        }

        if (mPositionOfCurInList == -1) {
            mPositionOfCurInList = 0;
            mCurrencyShortForm = mMainListForActions.get(0).getCurrency().getShortTitle().toUpperCase();
        }
        if (mPositionOfOrgInList == -1) {
            mPositionOfOrgInList = 0;
            mOrganizationId = mMainListForActions.get(mPositionOfCurInList)
                    .getOrganizations().get(mPositionOfOrgInList).getId();
        }
        mPreferenceManager.setConverterCurrencyShortForm(mCurrencyShortForm);
        mPreferenceManager.setConverterOrganizationId(mOrganizationId);
        mListForCurrencyDialog.get(mPositionOfCurInList).setChecked(true);
        List<OrganizationsEntity> org = mMainListForActions.get(mPositionOfCurInList).getOrganizations();
        for (int i = 0; i < org.size(); i++) {
            boolean isChecked = false;
            if (mPositionOfOrgInList == i) {
                isChecked = true;
            }
            for (CurrenciesEntity cur : org.get(i).getCurrencies()) {
                if (cur.getShortTitle().toUpperCase().equals(mCurrencyShortForm)) {
                    mListForOrganizationsDialog.add(new RecyclerViewDataDialogListOrganizations(isChecked,
                            org.get(i), cur));
                }
            }
        }
        getPurchaseAndSaleValue(mPositionOfCurInList, mPositionOfOrgInList);
    }

    public void getPurchaseAndSaleValue(int curPos, int orgPos) {
        OrganizationsEntity org = mMainListForActions.get(curPos).getOrganizations().get(orgPos);
        m:
        for (CurrenciesEntity cur : org.getCurrencies()) {
            if (cur.getShortTitle().toUpperCase().equals(mCurrencyShortForm)) {
                mSaleValue = cur.getAsk();
                mPurchaseValue = cur.getBid();
                mDate = cur.getDate();
                break m;
            }
        }
    }

    public void setLang() {
        new LanguageManager() {
            @Override
            public void engLanguage() {
                mFirstCurrencyTitle = getString(R.string.converter_main_currency_eng);
                mSaleTitle = getString(R.string.converter_sale_eng);
                mPurchaseTitle = getString(R.string.converter_purchase_eng);
                mCountTitle = getString(R.string.converter_count_of_currencies_eng);
                eTTitle.setText(getResources().getString(R.string.drawer_item_converter_eng));
                tVChosenCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleEng().toUpperCase());
                tVChosenOrganizationTitle.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getOrganizations().get(mPositionOfOrgInList).getTitleEng());
                tVSecondCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleEng().toUpperCase() + " (" + mCurrencyShortForm + ")");
                mCurrencyDialogTitle = getString(R.string.drawer_item_currencies_eng);
                mOrganizationDialogTitle = getString(R.string.drawer_item_organizations_eng);
                mActionDialogTitle = getString(R.string.converter_dialog_action_title_eng);
                mToastFailure = getString(R.string.converter_toast_failure_eng);
                mTitle = getResources().getString(R.string.drawer_item_converter_eng);
                mMessage = getString(R.string.dialog_template_text_eng);
                mTitleButtonOne = getString(R.string.dialog_template_edit_eng);
                mTitleButtonTwo = getString(R.string.dialog_template_create_eng);
                mToastEdited = getString(R.string.converter_toast_template_edit_eng);
                mToastCreated = getString(R.string.converter_toast_template_create_eng);
            }

            @Override
            public void ukrLanguage() {
                mFirstCurrencyTitle = getString(R.string.converter_main_currency_ukr);
                mSaleTitle = getString(R.string.converter_sale_ukr);
                mPurchaseTitle = getString(R.string.converter_purchase_ukr);
                mCountTitle = getString(R.string.converter_count_of_currencies_ukr);
                eTTitle.setText(getResources().getString(R.string.drawer_item_converter_ukr));
                tVChosenCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleUkr().toUpperCase());
                tVChosenOrganizationTitle.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getOrganizations().get(mPositionOfOrgInList).getTitleUkr());
                tVSecondCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleUkr().toUpperCase() + " (" + mCurrencyShortForm + ")");
                mCurrencyDialogTitle = getString(R.string.drawer_item_currencies_ukr);
                mOrganizationDialogTitle = getString(R.string.drawer_item_organizations_ukr);
                mActionDialogTitle = getString(R.string.converter_dialog_action_title_ukr);
                mToastFailure = getString(R.string.converter_toast_failure_ukr);
                mTitle = getResources().getString(R.string.drawer_item_converter_ukr);
                mMessage = getString(R.string.dialog_template_text_ukr);
                mTitleButtonOne = getString(R.string.dialog_template_edit_ukr);
                mTitleButtonTwo = getString(R.string.dialog_template_create_ukr);
                mToastEdited = getString(R.string.converter_toast_template_edit_ukr);
                mToastCreated = getString(R.string.converter_toast_template_create_ukr);
            }

            @Override
            public void rusLanguage() {
                mFirstCurrencyTitle = getString(R.string.converter_main_currency_rus);
                mSaleTitle = getString(R.string.converter_sale_rus);
                mPurchaseTitle = getString(R.string.converter_purchase_rus);
                mCountTitle = getString(R.string.converter_count_of_currencies_rus);
                eTTitle.setText(getResources().getString(R.string.drawer_item_converter_rus));
                tVChosenCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleRus().toUpperCase());
                tVChosenOrganizationTitle.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getOrganizations().get(mPositionOfOrgInList).getTitleRus());
                tVSecondCurrency.setText(mMainListForActions.get(mPositionOfCurInList)
                        .getCurrency().getTitleRus().toUpperCase() + " (" + mCurrencyShortForm + ")");
                mCurrencyDialogTitle = getString(R.string.drawer_item_currencies_rus);
                mOrganizationDialogTitle = getString(R.string.drawer_item_organizations_rus);
                mActionDialogTitle = getString(R.string.converter_dialog_action_title_rus);
                mToastFailure = getString(R.string.converter_toast_failure_rus);
                mTitle = getResources().getString(R.string.drawer_item_converter_rus);
                mMessage = getString(R.string.dialog_template_text_rus);
                mTitleButtonOne = getString(R.string.dialog_template_edit_rus);
                mTitleButtonTwo = getString(R.string.dialog_template_create_rus);
                mToastEdited = getString(R.string.converter_toast_template_edit_rus);
                mToastCreated = getString(R.string.converter_toast_template_create_rus);
            }
        };
        getDataForActionDialog();
        setData();
    }

    public void setData() {
        tVShortTitle.setText(mCurrencyShortForm);
        tVCount.setText(mCountTitle + mMainListForActions.size());
        tVFirstCurrency.setText(mFirstCurrencyTitle);
        tVChosenOrganizationPurchase.setText(getInfoString(mPurchaseTitle + ": ", mPurchaseValue));
        tVChosenOrganizationSale.setText(getInfoString(mSaleTitle + ": ", mSaleValue));
        tVChosenOrganizationDate.setText(mDate.substring(0, 10));
        if (mAction.equals(ConstantsManager.CONVERTER_ACTION_SALE)) {
            tVChosenAction.setText(mSaleTitle.toUpperCase());
        } else {
            tVChosenAction.setText(mPurchaseTitle.toUpperCase());
        }
        mTextWatcherAdapter.setActionForFirst(false);
        mTextWatcherAdapter.setActionForSecond(false);
        convertValue();
    }

    public void getDataForActionDialog() {
        mListForActionDialog.clear();
        if (mAction.equals(ConstantsManager.CONVERTER_ACTION_PURCHASE)) {
            mListForActionDialog.add(new RecyclerViewDataDialogList(true,
                    mPurchaseTitle, mPurchaseTitle, mPurchaseTitle));
            mListForActionDialog.add(new RecyclerViewDataDialogList(false,
                    mSaleTitle, mSaleTitle, mSaleTitle));
        } else {
            mListForActionDialog.add(new RecyclerViewDataDialogList(false,
                    mPurchaseTitle, mPurchaseTitle, mPurchaseTitle));
            mListForActionDialog.add(new RecyclerViewDataDialogList(true,
                    mSaleTitle, mSaleTitle, mSaleTitle));
        }
    }

    public SpannableStringBuilder getInfoString(String title, String parameter) {
        int i = title.length();
        String result = title + "\n" + parameter;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(result);
        spannableStringBuilder.setSpan(
                new ForegroundColorSpan(CashApplication.getContext().getResources().getColor(R.color.color_accent)),
                i, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @OnClick(R.id.converter_organization_phone_iv)
    public void callToOrg() {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" +
                    mMainListForActions.get(mPositionOfCurInList).getOrganizations().get(mPositionOfOrgInList).getPhone()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            //Error
        }
    }


    @OnClick(R.id.converter_currency_cv)
    public void chooseCurrency() {
        mDialog = new DialogList(((MainActivity) getActivity()).getContext(),
                mCurrencyDialogTitle, mListForCurrencyDialog, null,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        mCurrencyShortForm = mMainListForActions.get(position).getCurrency().getShortTitle().toUpperCase();
                        mPreferenceManager.setConverterCurrencyShortForm(mCurrencyShortForm);
                        mDialog.getDialog().dismiss();
                        checkDefaultData();
                        setLang();
                    }
                });
        ((ImageView) mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done))
                .setImageResource(R.drawable.ic_tr);
        mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackground(getResources().getDrawable(R.drawable.ic_tr));
    }

    @OnClick(R.id.converter_organization_cv)
    public void chooseOrganization() {
        mDialog = new DialogList(((MainActivity) getActivity()).getContext(),
                mOrganizationDialogTitle, null, mListForOrganizationsDialog,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        mOrganizationId = mListForOrganizationsDialog.get(position).getOrganization().getId();
                        mPreferenceManager.setConverterOrganizationId(mOrganizationId);
                        mDialog.getDialog().dismiss();
                        checkDefaultData();
                        setLang();
                    }
                });
        ((ImageView) mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done))
                .setImageResource(R.drawable.ic_tr);
        mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackground(getResources().getDrawable(R.drawable.ic_tr));
    }

    @OnClick(R.id.converter_sale_or_purchase_cv)
    public void chooseAction() {
        mDialog = new DialogList(((MainActivity) getActivity()).getContext(),
                mActionDialogTitle, mListForActionDialog, null,
                new RecyclerViewAdapterDialogList.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        if (position == 0) {
                            mAction = ConstantsManager.CONVERTER_ACTION_PURCHASE;
                        } else {
                            mAction = ConstantsManager.CONVERTER_ACTION_SALE;
                        }
                        mPreferenceManager.setConverterAction(mAction);
                        mDialog.getDialog().dismiss();
                        setLang();
                    }
                });
        ((ImageView) mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done))
                .setImageResource(R.drawable.ic_tr);
        mDialog.getDialog().getWindow().findViewById(R.id.dialog_list_done)
                .setBackground(getResources().getDrawable(R.drawable.ic_tr));
    }

    public void convertValue() {

        try {
            mStartValue = mPreferenceManager.getConverterValue();
            mDirection = mPreferenceManager.getConverterDirection();

            String index = null;
            switch (mAction) {
                case ConstantsManager.CONVERTER_ACTION_PURCHASE:
                    index = mPurchaseValue;
                    break;
                case ConstantsManager.CONVERTER_ACTION_SALE:
                    index = mSaleValue;
                    break;
            }
            BigDecimal bDValue = new BigDecimal(mStartValue);
            BigDecimal bDIndex = new BigDecimal(index);
            BigDecimal bDResult;

            switch (mDirection) {
                case ConstantsManager.CONVERTER_DIRECTION_FROM_UAH:
                    bDResult = bDValue.divide(bDIndex, 4, BigDecimal.ROUND_HALF_UP);
                    eTFirstValue.setText(mStartValue);
                    if (bDResult.compareTo(new BigDecimal("0")) == 0) {
                        eTSecondValue.setText("0");
                    } else {
                        eTSecondValue.setText(bDResult.stripTrailingZeros().toPlainString());
                    }
                    break;
                case ConstantsManager.CONVERTER_DIRECTION_TO_UAH:
                    bDResult = bDValue.multiply(bDIndex);
                    if (bDResult.compareTo(new BigDecimal("0")) == 0) {
                        eTFirstValue.setText("0");
                    } else {
                        eTFirstValue.setText(bDResult.setScale(4, BigDecimal.ROUND_HALF_UP)
                                .stripTrailingZeros().toPlainString());
                    }
                    eTSecondValue.setText(mStartValue);
                    break;
            }
            eTFirstValue.setSelection(eTFirstValue.getText().toString().length());
            eTSecondValue.setSelection(eTSecondValue.getText().toString().length());
            isConverted = true;

        } catch (Exception e) {
            isConverted = false;
        }
        mTextWatcherAdapter.setActionForFirst(true);
        mTextWatcherAdapter.setActionForSecond(true);
    }

    @OnClick(R.id.converter_fragment_fab)
    public void addToTemplates() {
        mTemplateId = mPreferenceManager.getTemplateId();
        if (isConverted) {
            if (mPreferenceManager.getTemplateId().equals(ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT)) {
                mTemplateId = mDatabaseManager.getTemplateNewId();
                mPreferenceManager.setTemplateId(mTemplateId);
                saveTemplate(mToastCreated);
            } else {
                showDialog();
            }
        } else {
            ((MainActivity) getActivity()).showToast(mToastFailure);
        }
    }

    public void showDialog() {
        mDialogInfoWithTwoButtons = new DialogInfoWithTwoButtons(((MainActivity) getActivity()).getContext(),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        saveTemplate(mToastEdited);
                        mDialogInfoWithTwoButtons.getDialog().dismiss();
                    }
                }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTemplateId = mDatabaseManager.getTemplateNewId();
                mPreferenceManager.setTemplateId(mTemplateId);
                saveTemplate(mToastCreated);
                mDialogInfoWithTwoButtons.getDialog().dismiss();
            }
        }, mTitle, mMessage, mTitleButtonOne, mTitleButtonTwo);
        mDialogInfoWithTwoButtons.getDialog().show();
    }

    public void saveTemplate(String toastText) {
        SimpleDateFormat simpleDateFormatY = new SimpleDateFormat("yyyy");
        SimpleDateFormat simpleDateFormatM = new SimpleDateFormat("MM");
        SimpleDateFormat simpleDateFormatD = new SimpleDateFormat("dd");
        SimpleDateFormat simpleDateFormatH = new SimpleDateFormat("HH");
        SimpleDateFormat simpleDateFormatMin = new SimpleDateFormat("mm");
        SimpleDateFormat simpleDateFormatS = new SimpleDateFormat("ss");
        String year = simpleDateFormatY.format(System.currentTimeMillis());
        String month = simpleDateFormatM.format(System.currentTimeMillis());
        String day = simpleDateFormatD.format(System.currentTimeMillis());
        String hour = simpleDateFormatH.format(System.currentTimeMillis());
        String minute = simpleDateFormatMin.format(System.currentTimeMillis());
        String second = simpleDateFormatS.format(System.currentTimeMillis());
        mDatabaseManager.addDataInTemplateTable(
                new TemplateEntity(mTemplateId, mOrganizationId, mCurrencyShortForm,
                        mStartValue, mDirection, mAction, year+"-"+month+"-"+day+" "+hour+":"+minute+";"+second)
        );
        ((MainActivity) getActivity()).showToast(toastText);
    }
}