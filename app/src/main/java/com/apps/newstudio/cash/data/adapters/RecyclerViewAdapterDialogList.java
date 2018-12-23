package com.apps.newstudio.cash.data.adapters;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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

public class RecyclerViewAdapterDialogList extends RecyclerView.Adapter<RecyclerViewAdapterDialogList.ViewHolder> {

    private List<RecyclerViewDataDialogList> data;
    private OnItemClickListener onItemClick;

    /**
     * Constructor of RecyclerViewAdapterDialogList object
     * @param data information for elements of items
     * @param onItemClick object for onClick event
     */
    public RecyclerViewAdapterDialogList(List<RecyclerViewDataDialogList> data, OnItemClickListener onItemClick) {
        this.data = data;
        this.onItemClick = onItemClick;
    }

    /**
     * Setter of RecyclerViewDataDialogList List object data
     * @param data RecyclerViewDataDialogList List object
     */
    public void setData(List<RecyclerViewDataDialogList> data) {
        this.data = data;
    }

    /**
     * Creates ViewHolder object
     * @param parent ViewGroup object
     * @param viewType int object
     * @return ViewHolder object
     */
    @NonNull
    @Override
    public RecyclerViewAdapterDialogList.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dialog_list, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    /**
     * Sets data for ViewHolder object of items
     * @param holder ViewHolder object
     * @param position position of item in list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapterDialogList.ViewHolder holder, int position) {
        if (data.get(position).isChecked()) {
            holder.text.setTextColor(CashApplication.getContext().getResources().getColor(R.color.color_accent));
            holder.image.setBackground(CashApplication.getContext().getResources().getDrawable(R.drawable.circle_ckecked));
            holder.image.setImageResource(R.drawable.circle);
        } else {
            holder.text.setTextColor(CashApplication.getContext().getResources().getColor(R.color.grey_one));
            holder.image.setBackground(CashApplication.getContext().getResources().getDrawable(R.drawable.circle_unckecked));
            holder.image.setImageResource(R.drawable.ic_tr);
        }

        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                holder.text.setText(data.get(position).getTitleEng().toUpperCase());
                break;
            case ConstantsManager.LANGUAGE_RUS:
                holder.text.setText(data.get(position).getTitleRus().toUpperCase());
                break;
            case ConstantsManager.LANGUAGE_UKR:
                holder.text.setText(data.get(position).getTitleUkr().toUpperCase());
                break;
        }
    }

    /**
     * Getter for count of items
     * @return size of RecyclerViewDataDialogList List object
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Class of ViewHolder object
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_dialog_list_iv)
        public ImageView image;

        @BindView(R.id.item_dialog_list_tv)
        public TextView text;

        @BindView(R.id.item_dialog_list_ll)
        public LinearLayout layout;


        /**
         * Constructor of ViewHolder object
         * @param itemView View of item in list
         * @param onClick OnItemClickListener object for onClick Event of LinearLayout layout object
         */
        public ViewHolder(View itemView, final OnItemClickListener onClick) {
            super(itemView);
            ButterKnife.bind(this,itemView);
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
    public interface OnItemClickListener{
        void onClick(int position);
    }
}
