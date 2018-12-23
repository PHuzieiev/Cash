package com.apps.newstudio.cash.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterTemplates extends RecyclerView.Adapter<RecyclerViewAdapterTemplates.ViewHolder> {

    private List<RecyclerViewDataTemplates> mData;
    private ActionForItem mActionForItem;
    private ActionForIcon mActionForIcon;

    /**
     * Constructor for the first type of RecyclerViewAdapterTemplates object
     *
     * @param data RecyclerViewDataTemplates List object
     * @param actionForIcon ActionForIcon object
     * @param actionForItem ActionForItem object
     */
    public RecyclerViewAdapterTemplates(List<RecyclerViewDataTemplates> data,
                                        ActionForItem actionForItem, ActionForIcon actionForIcon) {
        mData = data;
        mActionForItem = actionForItem;
        mActionForIcon = actionForIcon;
    }

    /**
     * Setter for RecyclerViewDataTemplates List object mData
     * @param data RecyclerViewDataFragment List object
     */
    public void setData(List<RecyclerViewDataTemplates> data) {
        mData = data;
    }


    /**
     * Creates ViewHolder object for items in list
     * @param parent ViewGroup object
     * @param viewType viewType int value
     * @return ViewHolder object
     */
    @NonNull
    @Override
    public RecyclerViewAdapterTemplates.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_fragment_recycle_view_three,
                parent, false);
        return new ViewHolder(view, mActionForItem, mActionForIcon);
    }

    /**
     * Changes data in items layout using ViewHolder object
     * @param holder ViewHolder object
     * @param position position of item in list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterTemplates.ViewHolder holder, int position) {
        RecyclerViewDataTemplates template = mData.get(position);
        holder.shortForm.setText(template.getShortCurrencyTitle());
        holder.title.setText(template.getCurrencyTitle().toUpperCase());
        holder.organization.setText(checkText(template.getOrganizationTitle()));
        String mPurchaseTitle = "";
        String mSaleTitle = "";
        String mFirstCurrencyTitle = "";
        String buttonTitle = "";
        String dateTitle = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                mFirstCurrencyTitle = CashApplication.getContext().getString(R.string.converter_main_currency_eng);
                mSaleTitle = CashApplication.getContext().getString(R.string.converter_sale_eng);
                mPurchaseTitle = CashApplication.getContext().getString(R.string.converter_purchase_eng);
                buttonTitle = CashApplication.getContext().getString(R.string.org_list_item_button_eng);
                dateTitle = CashApplication.getContext().getString(R.string.template_list_item_date_title_eng);
                break;
            case ConstantsManager.LANGUAGE_RUS:
                mFirstCurrencyTitle = CashApplication.getContext().getString(R.string.converter_main_currency_rus);
                mSaleTitle = CashApplication.getContext().getString(R.string.converter_sale_rus);
                mPurchaseTitle = CashApplication.getContext().getString(R.string.converter_purchase_rus);
                buttonTitle = CashApplication.getContext().getString(R.string.org_list_item_button_rus);
                dateTitle = CashApplication.getContext().getString(R.string.template_list_item_date_title_rus);
                break;
            case ConstantsManager.LANGUAGE_UKR:
                mFirstCurrencyTitle = CashApplication.getContext().getString(R.string.converter_main_currency_ukr);
                mSaleTitle = CashApplication.getContext().getString(R.string.converter_sale_ukr);
                mPurchaseTitle = CashApplication.getContext().getString(R.string.converter_purchase_ukr);
                buttonTitle = CashApplication.getContext().getString(R.string.org_list_item_button_ukr);
                dateTitle = CashApplication.getContext().getString(R.string.template_list_item_date_title_ukr);
                break;
        }
        if (template.getAction().equals(ConstantsManager.CONVERTER_ACTION_PURCHASE)) {
            holder.action.setText(mPurchaseTitle.toUpperCase());
        } else {
            holder.action.setText(mSaleTitle.toUpperCase());
        }

        holder.date.setText(dateTitle.concat(template.getDate().substring(0, 10)));
        holder.currencyOneTitle.setText(mFirstCurrencyTitle);
        holder.currencyTwoTitle.setText(template.getSecondCurrencyTitle().toUpperCase());
        holder.currencyOneValue.setText(template.getFirstValue());
        holder.currencyTwoValue.setText(template.getSecondValue());
        holder.buttonShow.setText(buttonTitle);
    }

    /**
     * Deletes useless chars form text
     * @param text input text
     * @return text after clearing
     */
    private String checkText(String text){
        if (text.contains("(")) {
            int end = text.length() - 1;
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) == '(') {
                    end = i - 1;
                    break;
                }
            }
            text = text.substring(0, end);
        }
        return text;
    }

    /**
     * Getter for items count of list
     * @return size of mData list
     */
    @Override
    public int getItemCount() {
        return mData.size();
    }

    /**
     * Class for list ViewHolder object
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_card)
        CardView cardView;

        @BindView(R.id.item_date_tv)
        TextView date;

        @BindView(R.id.item_short_tv)
        TextView shortForm;

        @BindView(R.id.item_title_tv)
        TextView title;

        @BindView(R.id.item_organization_tv)
        TextView organization;

        @BindView(R.id.item_action_tv)
        TextView action;

        @BindView(R.id.item_currency_title_one_tv)
        TextView currencyOneTitle;

        @BindView(R.id.item_currency_title_two_tv)
        TextView currencyTwoTitle;

        @BindView(R.id.item_currency_value_one_tv)
        TextView currencyOneValue;

        @BindView(R.id.item_currency_value_two_tv)
        TextView currencyTwoValue;

        @BindView(R.id.item_show_b)
        Button buttonShow;

        @BindView(R.id.item_delete_iv)
        ImageView delete;

        private ActionForItem mActionForItem;
        private ActionForIcon mActionForIcon;

        /**
         * Constructor for ViewHolder object
         * @param itemView some item in list
         * @param actionTwo action for ImageView object phoneImage
         * @param action action for all item
         */
        public ViewHolder(View itemView, ActionForItem action, ActionForIcon actionTwo) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mActionForItem = action;
            mActionForIcon = actionTwo;
            buttonShow.setOnClickListener(this);
            cardView.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_card:
                case R.id.item_show_b:
                    mActionForItem.action(getAdapterPosition());
                    break;
                case R.id.item_delete_iv:
                    mActionForIcon.action(getAdapterPosition());
                    break;
            }
        }
    }

    /**
     * Interface for ImageView delete click event
     */
    public interface ActionForIcon {
        void action(int position);
    }

    /**
     * Interface for item click event
     */
    public interface ActionForItem {
        void action(int position);
    }
}
