package com.apps.newstudio.cash.data.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.apps.newstudio.cash.R;
import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecycleViewAdapterFragment extends RecyclerView.Adapter<RecycleViewAdapterFragment.ViewHolder> {

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    private List<OrganizationsEntity> organizations;
    private RecycleViewLangFragment lang;
    private int type;
    private ActionForIcon actionForIcon;
    private ActionForItem mActionForItem;

    public RecycleViewAdapterFragment(List<OrganizationsEntity> organizations,
                                      RecycleViewLangFragment lang,
                                      int type, ActionForIcon actionForIcon, ActionForItem actionForItem) {
        this.organizations = organizations;
        this.lang = lang;
        this.type = type;
        this.actionForIcon = actionForIcon;
        mActionForItem=actionForItem;
    }

    public void setOrganizations(List<OrganizationsEntity> organizations) {
        this.organizations = organizations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_for_fragment_recycle_view,
                parent, false);
        return new ViewHolder(view, actionForIcon, mActionForItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (organizations.get(position).getOrgType()) {
            case "1":
                holder.type.setText(lang.getTypeBank());
                holder.mainImage.setImageResource(R.drawable.bank_org);
                break;
            case "2":
                holder.type.setText(lang.getTypeOther());
                holder.mainImage.setImageResource(R.drawable.other_org);
                break;
        }

        String update=lang.getUpdate()+organizations.get(position).getDate().substring(0,10);
        holder.date.setText(update);

        String currency;
        String about = "";
        String title = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                title = organizations.get(position).getTitleEng();
                for (int i = 0; i < organizations.get(position).getCurrenciesTwo().size(); i++) {
                    currency = organizations.get(position).getCurrenciesTwo().get(i).getTitleEng().toUpperCase();
                    about = about + currency;
                    if (i == organizations.get(position).getCurrenciesTwo().size() - 1) {
                        about = about + ".";
                    } else {
                        about = about + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_RUS:
                title = organizations.get(position).getTitleRus();
                for (int i = 0; i < organizations.get(position).getCurrenciesTwo().size(); i++) {
                    currency = organizations.get(position).getCurrenciesTwo().get(i).getTitleRus().toUpperCase();
                    about = about + currency;
                    if (i == organizations.get(position).getCurrenciesTwo().size() - 1) {
                        about = about + ".";
                    } else {
                        about = about + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_UKR:
                title = organizations.get(position).getTitleUkr();
                for (int i = 0; i < organizations.get(position).getCurrenciesTwo().size(); i++) {
                    currency = organizations.get(position).getCurrenciesTwo().get(i).getTitleUkr().toUpperCase();
                    about = about + currency;
                    if (i != organizations.get(position).getCurrenciesTwo().size() - 1) {
                        about = about + ", ";
                    }
                }
                break;

        }

        if(title.contains("(")){
            int end=title.length()-1;
            m:for(int i=0;i<title.length();i++){
                if(title.charAt(i)=='('){
                    end=i-1;
                    break m;
                }
            }
            title=title.substring(0, end);
        }
        holder.title.setText(title);
        holder.about.setText(about);
        holder.buttonShow.setText(lang.getButton());
        if (type == TYPE_ONE) {
            holder.phoneImage.setImageResource(R.drawable.ic_phone);
        } else if (type == TYPE_TWO) {
            holder.phoneImage.setImageResource(R.drawable.ic_tr);
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.cardView.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.bottomMargin = CashApplication.getContext().getResources()
                    .getDimensionPixelSize(R.dimen.spacing_big_88dp);
        } else {
            layoutParams.bottomMargin = 0;
        }
        holder.cardView.setLayoutParams(layoutParams);

    }

    @Override
    public int getItemCount() {
        return organizations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.item_card)
        public CardView cardView;

        @BindView(R.id.item_type_tv)
        public TextView type;

        @BindView(R.id.item_title_tv)
        public TextView title;

        @BindView(R.id.item_date_tv)
        public TextView date;

        @BindView(R.id.item_iv)
        public ImageView mainImage;

        @BindView(R.id.item_about_tv)
        public TextView about;

        @BindView(R.id.item_show_b)
        public Button buttonShow;

        @BindView(R.id.item_call_iv)
        public ImageView phoneImage;

        private ActionForIcon actionForIcon;
        private ActionForItem mActionForItem;

        public ViewHolder(View itemView, ActionForIcon actionForIcon, ActionForItem actionForItem) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.actionForIcon = actionForIcon;
            this.mActionForItem = actionForItem;
            phoneImage.setOnClickListener(this);
            cardView.setOnClickListener(this);
            buttonShow.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.item_card:
                case R.id.item_show_b:
                    if (mActionForItem != null) {
                        mActionForItem.action(getAdapterPosition());
                    }
                    break;
                case R.id.item_call_iv:
                    if (actionForIcon != null) {
                        actionForIcon.action(getAdapterPosition());
                    }
                    break;
            }


        }
    }

    public interface ActionForIcon{
        void action(int position);
    }

    public interface ActionForItem{
        void action(int position);
    }
}
