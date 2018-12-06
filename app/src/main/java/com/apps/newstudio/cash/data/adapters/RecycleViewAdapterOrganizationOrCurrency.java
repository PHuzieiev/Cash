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

public class RecycleViewAdapterOrganizationOrCurrency
        extends RecyclerView.Adapter<RecycleViewAdapterOrganizationOrCurrency.ViewHolder> {

    private List<CurrenciesEntity> mData;
    private ActionForIcon mActionForIcon;
    public final static int TYPE_ONE = 1;
    public final static int TYPE_TWO = 2;
    private int mTypeOfList;

    public RecycleViewAdapterOrganizationOrCurrency(List<CurrenciesEntity> data, ActionForIcon actionForIcon, int typeOfList) {
        mData = data;
        mActionForIcon = actionForIcon;
        mTypeOfList = typeOfList;
    }

    @NonNull
    @Override
    public RecycleViewAdapterOrganizationOrCurrency.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layoutId = 0;
        switch (mTypeOfList) {
            case 1:
                layoutId = R.layout.item_currencies_list;
                break;
            case 2:
                break;
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RecycleViewAdapterOrganizationOrCurrency.ViewHolder(view, mActionForIcon);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewAdapterOrganizationOrCurrency.ViewHolder holder, int position) {
        switch (mTypeOfList) {
            case RecycleViewAdapterOrganizationOrCurrency.TYPE_ONE:
                bindTypeOne(holder, mData.get(position), position);
                break;
            case RecycleViewAdapterOrganizationOrCurrency.TYPE_TWO:
                break;
        }
    }

    public void bindTypeOne(RecycleViewAdapterOrganizationOrCurrency.ViewHolder holder, CurrenciesEntity data, int position) {
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
        holder.date.setText(data.getDate().substring(0,10));
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.itemLayout.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.bottomMargin = CashApplication.getContext().getResources()
                    .getDimensionPixelSize(R.dimen.spacing_big_88dp);
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.tr));
        } else {
            layoutParams.bottomMargin = 0;
            holder.line.setBackgroundColor(CashApplication.getContext().getResources().getColor(R.color.grey_one));
        }
        holder.itemLayout.setLayoutParams(layoutParams);
    }

    public SpannableStringBuilder getInfoString(String title, String parameter) {
        int i = title.length();
        String result = title + "\n"+parameter;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(result);
        spannableStringBuilder.setSpan(
                new ForegroundColorSpan(CashApplication.getContext().getResources().getColor(R.color.color_accent)),
                i, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableStringBuilder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_cur_ll)
        public LinearLayout itemLayout;

        @BindView(R.id.item_cur_short_tv)
        public TextView shortTitle;

        @BindView(R.id.item_cur_title_tv)
        public TextView title;

        @BindView(R.id.item_cur_purchase_tv)
        public TextView purchase;

        @BindView(R.id.item_cur_sale_tv)
        public TextView sale;

        @BindView(R.id.item_cur_show_details)
        public ImageView details;

        @BindView(R.id.item_cur_v)
        public View line;

        @BindView(R.id.item_cur_date_tv)
        public  TextView date;

        private ActionForIcon mActionForIcon;

        public ViewHolder(View itemView, RecycleViewAdapterOrganizationOrCurrency.ActionForIcon actionForIcon) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mActionForIcon = actionForIcon;
            details.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_cur_show_details:
                    mActionForIcon.action(getAdapterPosition());
                    break;
            }
        }
    }

    public interface ActionForIcon {
        void action(int position);
    }
}
