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
import com.apps.newstudio.cash.data.storage.models.OrganizationsEntity;
import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapterFragment extends RecyclerView.Adapter<RecyclerViewAdapterFragment.ViewHolder> {

    public static final int TYPE_ONE = 1;
    public static final int TYPE_TWO = 2;
    private List<OrganizationsEntity> organizations;
    private List<RecyclerViewDataFragment> currencies;
    private RecyclerViewLangFragment lang;
    private int type;
    private ActionForIcon actionForIcon;
    private ActionForItem mActionForItem;

    /**
     * Constructor for the first type of RecyclerViewAdapterFragment object
     *
     * @param organizations OrganizationsEntity List object
     * @param lang          RecyclerViewLangFragment object
     * @param actionForIcon ActionForIcon object
     * @param actionForItem ActionForItem object
     */
    public RecyclerViewAdapterFragment(List<OrganizationsEntity> organizations,
                                       RecyclerViewLangFragment lang, ActionForIcon actionForIcon, ActionForItem actionForItem) {
        this.organizations = organizations;
        this.lang = lang;
        this.type = TYPE_ONE;
        this.actionForIcon = actionForIcon;
        mActionForItem = actionForItem;
    }

    /**
     * Constructor for the second type of RecyclerViewAdapterFragment object,
     * sets NULL value for ActionForIcon object actionForIcon
     *
     * @param currencies    RecyclerViewDataFragment List object
     * @param lang          RecyclerViewLangFragment object
     * @param actionForItem ActionForItem object
     */
    public RecyclerViewAdapterFragment(List<RecyclerViewDataFragment> currencies,
                                       RecyclerViewLangFragment lang, ActionForItem actionForItem) {
        this.currencies = currencies;
        this.lang = lang;
        this.type = TYPE_TWO;
        this.actionForIcon = null;
        mActionForItem = actionForItem;
    }

    /**
     * Setter for OrganizationsEntity List object organizations
     * @param organizations OrganizationsEntity List object
     */
    public void setOrganizations(List<OrganizationsEntity> organizations) {
        this.organizations = organizations;
    }

    /**
     * Setter for RecyclerViewDataFragment List object currencies
     * @param currencies RecyclerViewDataFragment List object
     */
    public void setCurrencies(List<RecyclerViewDataFragment> currencies) {
        this.currencies = currencies;
    }

    public void setLang(RecyclerViewLangFragment lang) {
        this.lang = lang;
    }

    /**
     * Creates ViewHolder object for items in list
     * @param parent ViewGroup object
     * @param viewType viewType int value
     * @return ViewHolder object
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.item_for_fragment_recycle_view;
        switch (type) {
            case TYPE_ONE:
                layout = R.layout.item_for_fragment_recycle_view;
                break;
            case TYPE_TWO:
                layout = R.layout.item_for_fragment_recycle_view_two;
                break;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(layout,
                parent, false);
        return new ViewHolder(view, actionForIcon, mActionForItem);
    }

    /**
     * Calls method for changing data in items layout
     * @param holder ViewHolder object
     * @param position position of item in list
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        switch (type) {
            case TYPE_ONE:
                bindTypeOne(holder, organizations.get(position));
                break;
            case TYPE_TWO:
                bindTypeTwo(holder, currencies.get(position));
                break;
        }

        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) holder.cardView.getLayoutParams();
        if (position == getItemCount() - 1) {
            layoutParams.bottomMargin = CashApplication.getContext().getResources()
                    .getDimensionPixelSize(R.dimen.spacing_big_88dp);
        } else {
            layoutParams.bottomMargin = 0;
        }
        holder.cardView.setLayoutParams(layoutParams);
        holder.buttonShow.setText(lang.getButton());
        if (type == TYPE_ONE) {
            holder.phoneImage.setImageResource(R.drawable.ic_phone);
        } else if (type == TYPE_TWO) {
            holder.phoneImage.setImageResource(R.drawable.ic_tr);
        }
    }

    /**
     * Changes data in ViewHolder if  type = TYPE_ONE
     * @param holder ViewHolder object
     * @param data OrganizationsEntity object
     */
    public void bindTypeOne(ViewHolder holder, OrganizationsEntity data) {
        switch (data.getOrgType()) {
            case "1":
                holder.type.setText(lang.getTypeBank());
                holder.mainImage.setImageResource(R.drawable.bank_org);
                break;
            case "2":
                holder.type.setText(lang.getTypeOther());
                holder.mainImage.setImageResource(R.drawable.other_org);
                break;
        }

        String update = lang.getUpdate() + data.getDate().substring(0, 10);
        holder.date.setText(update);

        String currency;
        String about = "";
        String title = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                title = data.getTitleEng();
                for (int i = 0; i < data.getCurrenciesTwo().size(); i++) {
                    currency = data.getCurrenciesTwo().get(i).getTitleEng().toUpperCase();
                    about = about + currency;
                    if (i == data.getCurrenciesTwo().size() - 1) {
                        about = about + ".";
                    } else {
                        about = about + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_RUS:
                title = data.getTitleRus();
                for (int i = 0; i < data.getCurrenciesTwo().size(); i++) {
                    currency = data.getCurrenciesTwo().get(i).getTitleRus().toUpperCase();
                    about = about + currency;
                    if (i == data.getCurrenciesTwo().size() - 1) {
                        about = about + ".";
                    } else {
                        about = about + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_UKR:
                title = data.getTitleUkr();
                for (int i = 0; i < data.getCurrenciesTwo().size(); i++) {
                    currency = data.getCurrenciesTwo().get(i).getTitleUkr().toUpperCase();
                    about = about + currency;
                    if (i != data.getCurrenciesTwo().size() - 1) {
                        about = about + ", ";
                    }
                }
                break;

        }

        if (title.contains("(")) {
            int end = title.length() - 1;
            m:
            for (int i = 0; i < title.length(); i++) {
                if (title.charAt(i) == '(') {
                    end = i - 1;
                    break m;
                }
            }
            title = title.substring(0, end);
        }
        holder.title.setText(title);
        holder.about.setText(about);
    }

    /**
     * Changes data in ViewHolder if  type = TYPE_TWO
     * @param holder ViewHolder object
     * @param data RecyclerViewDataFragment object
     */
    public void bindTypeTwo(ViewHolder holder, RecyclerViewDataFragment data) {
        String count = lang.getUpdate() + data.getOrganizations().size();
        holder.date.setText(count);
        holder.shortForm.setText(data.getCurrency().getShortTitle().toUpperCase());
        String organizations = "";
        switch (DataManager.getInstance().getPreferenceManager().getLanguage()) {
            case ConstantsManager.LANGUAGE_ENG:
                holder.title.setText(data.getCurrency().getTitleEng().toUpperCase());
                for (int i = 0; i < data.getOrganizations().size(); i++) {
                    organizations = organizations + data.getOrganizations().get(i).getTitleEng();
                    if (i != data.getOrganizations().size() - 1) {
                        organizations = organizations + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_RUS:
                holder.title.setText(data.getCurrency().getTitleRus().toUpperCase());
                for (int i = 0; i < data.getOrganizations().size(); i++) {
                    organizations = organizations + data.getOrganizations().get(i).getTitleEng();
                    if (i != data.getOrganizations().size() - 1) {
                        organizations = organizations + ", ";
                    }
                }
                break;
            case ConstantsManager.LANGUAGE_UKR:
                holder.title.setText(data.getCurrency().getTitleUkr().toUpperCase());
                for (int i = 0; i < data.getOrganizations().size(); i++) {
                    organizations = organizations + data.getOrganizations().get(i).getTitleUkr();
                    if (i != data.getOrganizations().size() - 1) {
                        organizations = organizations + ", ";
                    }
                }
                break;

        }
        holder.about.setText(organizations);

    }


    /**
     * Getter for items count of list
     * @return size of organizations list if type = TYPE_ONE, else - size of currencies list
     */
    @Override
    public int getItemCount() {
        if (type == TYPE_ONE) {
            return organizations.size();
        } else {
            return currencies.size();
        }
    }

    /**
     * Class for list ViewHolder object
     */
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.item_card)
        public CardView cardView;

        @BindView(R.id.item_type_tv)
        public TextView type;

        @BindView(R.id.item_title_tv)
        public TextView title;

        @BindView(R.id.item_date_tv)
        public TextView date;


        @BindView(R.id.item_about_tv)
        public TextView about;

        @BindView(R.id.item_show_b)
        public Button buttonShow;

        @BindView(R.id.item_call_iv)
        public ImageView phoneImage;

        public TextView shortForm;
        public ImageView mainImage;

        private ActionForIcon actionForIcon;
        private ActionForItem mActionForItem;

        /**
         * Constructor for ViewHolder object
         * @param itemView some item in list
         * @param actionForIcon action for ImageView object phoneImage
         * @param actionForItem action for all item
         */
        public ViewHolder(View itemView, ActionForIcon actionForIcon, ActionForItem actionForItem) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.actionForIcon = actionForIcon;
            this.mActionForItem = actionForItem;
            if (actionForIcon != null) {
                mainImage = itemView.findViewById(R.id.item_iv);
                phoneImage.setOnClickListener(this);
            } else {
                shortForm = itemView.findViewById(R.id.item_short_tv);
            }
            cardView.setOnClickListener(this);
            buttonShow.setOnClickListener(this);
        }

        /**
         * Sets events for some objects of item
         * @param v view which was clicked
         */
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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

    /**
     * Interface for ImageView phoneImage click event
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
