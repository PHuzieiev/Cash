package com.apps.newstudio.cash.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.storage.models.CurrenciesEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterOrganizationOrCurrency
        extends RecyclerView.Adapter<RecyclerViewAdapterOrganizationOrCurrency.ViewHolder> {

    private List<CurrenciesEntity> mData;
    private List<RecyclerViewDataOrganizationOrCurrency> mDataTwo;

    private ActionForIcon mActionForIcon;
    private ActionForIconTwo mActionForIconTwo;
    private ActionForIconConverter mActionForIconConverter;


    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;
    private int mTypeOfList;

    /**
     * Constructor for RecyclerViewAdapterOrganizationOrCurrency object
     * @param data main data for items in list
     * @param actionForIcon main action for items ImageView
     * @param actionForIconConverter main action for ImageView object which opens converter
     */
    public RecyclerViewAdapterOrganizationOrCurrency(List<CurrenciesEntity> data, ActionForIcon actionForIcon,
                                                     ActionForIconConverter actionForIconConverter) {
        mData = data;
        mActionForIcon = actionForIcon;
        mActionForIconConverter = actionForIconConverter;
        mActionForIconTwo = null;
        mTypeOfList = TYPE_ONE;
    }

    /**
     * Constructor number two for RecyclerViewAdapterOrganizationOrCurrency
     * @param data main data for items in list
     * @param actionForIcon main action for ImageView object in items
     * @param actionForIconTwo main action for ImageView object in items
     * @param actionForIconConverter main action for ImageView object which opens converter
     */
    public RecyclerViewAdapterOrganizationOrCurrency(List<RecyclerViewDataOrganizationOrCurrency> data, ActionForIcon actionForIcon,
                                                     ActionForIconTwo actionForIconTwo, ActionForIconConverter actionForIconConverter) {
        mDataTwo = data;
        mActionForIcon = actionForIcon;
        mActionForIconConverter = actionForIconConverter;
        mActionForIconTwo = actionForIconTwo;
        mTypeOfList = TYPE_TWO;
    }

    /**
     * Setter for dataTwo RecyclerViewDataOrganizationOrCurrency List;
     * @param dataTwo main data for items of list
     */
    public void setDataTwo(List<RecyclerViewDataOrganizationOrCurrency> dataTwo) {
        mDataTwo = dataTwo;
    }

    /**
     * Setter for data CurrenciesEntity List;
     * @param data main data for items of list
     */
    public void setData(List<CurrenciesEntity> data) {
        mData = data;
    }

    /**
     * Creates ViewHolder object for adding data in items
     * @param parent object for inflating
     * @param viewType int value
     * @return ViewHolder object
     */
    @NonNull
    @Override
    public RecyclerViewAdapterOrganizationOrCurrency.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (mTypeOfList) {
            case 1:
                layoutId = R.layout.item_currencies_list;
                break;
            case 2:
                layoutId = R.layout.item_organizations_list;
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RecyclerViewAdapterOrganizationOrCurrency.ViewHolder(view, mActionForIcon,
                mActionForIconTwo, mActionForIconConverter);
    }

    /**
     * Adds data in all elements of items
     * @param holder object witch is contained all elements of items
     * @param position position of item in list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterOrganizationOrCurrency.ViewHolder holder, int position) {
        switch (mTypeOfList) {
            case RecyclerViewAdapterOrganizationOrCurrency.TYPE_ONE:
                bindTypeOne(holder, mData.get(position), position);
                break;
            case RecyclerViewAdapterOrganizationOrCurrency.TYPE_TWO:
                bindTypeTwo(holder, mDataTwo.get(position), position);
                break;
        }
    }

    /**
     * Sets information for items in list
     * @param holder object witch is contained all elements of item
     * @param data object which is contained information for elements of item
     * @param position position of item in list
     */
    private void bindTypeOne(RecyclerViewAdapterOrganizationOrCurrency.ViewHolder holder, CurrenciesEntity data, int position) {
        holder.shortTitle.setText(data.getShortTitle().toUpperCase());
        String saleTitle = "";
        String purchaseTitle = "";
        String title = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_eng);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_eng);
                title = data.getTitleEng().toUpperCase();
                break;
            case ConstantsManager.LANGUAGE_RUS:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_rus);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_rus);
                title = data.getTitleRus().toUpperCase();
                break;
            case ConstantsManager.LANGUAGE_UKR:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_ukr);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_ukr);
                title = data.getTitleUkr().toUpperCase();
                break;
        }
        holder.title.setText(title);
        holder.sale.setText(getInfoString(saleTitle, data.getAsk()));
        holder.purchase.setText(getInfoString(purchaseTitle, data.getBid()));
        holder.date.setText(data.getDate().substring(0, 10));
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemLayout.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.bottomMargin = CashApplication.getContext().getResources()
                    .getDimensionPixelSize(R.dimen.spacing_big_88dp);
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.tr));
        } else {
            layoutParams.bottomMargin = 0;
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.grey_zero));
        }
        holder.itemLayout.setLayoutParams(layoutParams);
    }

    /**
     * Creates SpannableStringBuilder object for some elements in items
     * @param title main title for elements
     * @param parameter value which will be added to title
     * @return object which will be added like title for some elements in items
     */
    private SpannableStringBuilder getInfoString(String title, String parameter) {
        int i = title.length();
        String result = title + "\n" + parameter;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(result);
        spannableStringBuilder.setSpan(
                new ForegroundColorSpan(CashApplication.getContext().getResources().getColor(R.color.color_accent)),
                i, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    /**
     * Sets information for items in list
     * @param holder object witch is contained all elements of item
     * @param data object which is contained information for elements of item
     * @param position position of item in list
     */
    private void bindTypeTwo(RecyclerViewAdapterOrganizationOrCurrency.ViewHolder holder,
                            RecyclerViewDataOrganizationOrCurrency data, int position) {
        String saleTitle = "";
        String purchaseTitle = "";
        String title = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_eng);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_eng);
                title = data.getOrganization().getTitleEng();
                break;
            case ConstantsManager.LANGUAGE_RUS:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_rus);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_rus);
                title = data.getOrganization().getTitleRus();
                break;
            case ConstantsManager.LANGUAGE_UKR:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_ukr);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_ukr);
                title = data.getOrganization().getTitleUkr();
                break;
        }
        holder.title.setText(title);
        holder.sale.setText(getInfoString(saleTitle, data.getCurrency().getAsk()));
        holder.purchase.setText(getInfoString(purchaseTitle, data.getCurrency().getBid()));
        holder.date.setText(data.getCurrency().getDate().substring(0, 10));

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemLayout.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.bottomMargin = CashApplication.getContext().getResources()
                    .getDimensionPixelSize(R.dimen.spacing_big_88dp);
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.tr));
        } else {
            layoutParams.bottomMargin = 0;
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.grey_zero));
        }
        holder.itemLayout.setLayoutParams(layoutParams);
    }

    /**
     * Getter for count of items in list
     * @return count of items
     */
    @Override
    public int getItemCount() {
        int result = 0;
        switch (mTypeOfList) {
            case RecyclerViewAdapterOrganizationOrCurrency.TYPE_ONE:
                result = mData.size();
                break;
            case RecyclerViewAdapterOrganizationOrCurrency.TYPE_TWO:
                result = mDataTwo.size();
                break;
        }
        return result;
    }

    /**
     * Class which identificates all elements in item of list
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_ll)
        LinearLayout itemLayout;

        @BindView(R.id.item_title_tv)
        TextView title;

        @BindView(R.id.item_purchase_tv)
        TextView purchase;

        @BindView(R.id.item_sale_tv)
        TextView sale;

        @BindView(R.id.item_show_details)
        ImageView details;

        @BindView(R.id.item_converter)
        ImageView converter;

        @BindView(R.id.item_v)
        View line;

        @BindView(R.id.item_date_tv)
        TextView date;

        ImageView call;

        TextView shortTitle;

        private ActionForIcon mActionForIcon;
        private ActionForIconTwo mActionForIconTwo;
        private ActionForIconConverter mActionForIconConverter;


        /**
         * Constructor for ViewHolder object
         * @param itemView item main View
         * @param actionForIcon action for item ImageView
         * @param actionForIconTwo action for item ImageView
         */
        public ViewHolder(View itemView, RecyclerViewAdapterOrganizationOrCurrency.ActionForIcon actionForIcon,
                          ActionForIconTwo actionForIconTwo, ActionForIconConverter actionForIconConverter) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mActionForIcon = actionForIcon;
            mActionForIconTwo = actionForIconTwo;
            mActionForIconConverter = actionForIconConverter;
            details.setOnClickListener(this);
            converter.setOnClickListener(this);
            if (actionForIconTwo == null) {
                shortTitle = itemView.findViewById(R.id.item_short_tv);
            } else {
                call = itemView.findViewById(R.id.item_call_iv);
                call.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_show_details:
                    mActionForIcon.action(getAdapterPosition());
                    break;
                case R.id.item_call_iv:
                    mActionForIconTwo.action(getAdapterPosition());
                    break;
                case R.id.item_converter:
                    mActionForIconConverter.action(getAdapterPosition());
                    break;
            }
        }
    }

    /**
     * Interface which creates action method for onClick event of ImageView of item in list
     */
    public interface ActionForIcon {
        void action(int position);
    }


    /**
     * Interface which creates action method for onClick event of ImageView of item in list
     */
    public interface ActionForIconTwo {
        void action(int position);
    }


    /**
     * Interface which creates action method for onClick event of ImageView Converter of item in list
     */
    public interface ActionForIconConverter {
        void action(int position);
    }
}
