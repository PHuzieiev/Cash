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
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterDialogListOrganizations
        extends RecyclerView.Adapter<RecyclerViewAdapterDialogListOrganizations.ViewHolder> {

    private List<RecyclerViewDataDialogListOrganizations> mData;
    private RecyclerViewAdapterDialogList.OnItemClickListener onItemClick;

    public RecyclerViewAdapterDialogListOrganizations(List<RecyclerViewDataDialogListOrganizations> data,
                                                      RecyclerViewAdapterDialogList.OnItemClickListener onItemClick) {
        mData = data;
        this.onItemClick = onItemClick;
    }

    public void setData(List<RecyclerViewDataDialogListOrganizations> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_list_organizations,
                parent, false);
        return new RecyclerViewAdapterDialogListOrganizations.ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String saleTitle = "";
        String purchaseTitle = "";
        String title = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_eng);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_eng);
                title = mData.get(position).getOrganization().getTitleEng();
                break;
            case ConstantsManager.LANGUAGE_RUS:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_rus);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_rus);
                title = mData.get(position).getOrganization().getTitleRus();
                break;
            case ConstantsManager.LANGUAGE_UKR:
                saleTitle = CashApplication.getContext().getString(R.string.currencies_sale_ukr);
                purchaseTitle = CashApplication.getContext().getString(R.string.currencies_purchase_ukr);
                title = mData.get(position).getOrganization().getTitleUkr();
                break;
        }


        if (mData.get(position).isChecked()) {
            holder.image.setBackground(CashApplication.getContext().getResources().getDrawable(R.drawable.circle_ckecked));
            holder.image.setImageResource(R.drawable.circle);
            holder.title.setTextColor(CashApplication.getContext().getResources().getColor(R.color.color_accent));
        } else {
            holder.image.setBackground(CashApplication.getContext().getResources().getDrawable(R.drawable.circle_unckecked));
            holder.image.setImageResource(R.drawable.ic_tr);
            holder.title.setTextColor(CashApplication.getContext().getResources().getColor(R.color.grey_three));
        }


        holder.title.setText(title);
        holder.sale.setText(getInfoString(saleTitle, mData.get(position).getCurrency().getAsk(),
                mData.get(position).isChecked()));
        holder.purchase.setText(getInfoString(purchaseTitle, mData.get(position).getCurrency().getBid(),
                mData.get(position).isChecked()));
        holder.date.setText(mData.get(position).getCurrency().getDate().substring(0, 10));
    }

    public SpannableStringBuilder getInfoString(String title, String parameter, boolean isChecked) {
        int i = title.length();
        String result = title + "\n" + parameter;

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(result);
        if (isChecked) {
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(CashApplication.getContext().getResources().getColor(R.color.color_accent)),
                    i, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannableStringBuilder.setSpan(
                    new ForegroundColorSpan(CashApplication.getContext().getResources().getColor(R.color.grey_three)),
                    i, result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableStringBuilder;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_dialog_list_iv)
        public ImageView image;

        @BindView(R.id.item_dialog_title_tv)
        public TextView title;

        @BindView(R.id.item_dialog_purchase_tv)
        public TextView purchase;

        @BindView(R.id.item_dialog_sale_tv)
        public TextView sale;

        @BindView(R.id.item_dialog_date_tv)
        public TextView date;

        @BindView(R.id.item_dialog_ll)
        public LinearLayout layout;


        public ViewHolder(View itemView, final RecyclerViewAdapterDialogList.OnItemClickListener onClick) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClick.onClick(getAdapterPosition());
                }
            });

        }
    }

    /**
     * Interface which creates onClick method for onClick event of item in list
     */
    public interface OnItemClickListener {
        void onClick(int position);
    }
}
