package com.apps.newstudio.cash.data.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.apps.newstudio.cash.data.managers.DataManager;
import com.apps.newstudio.cash.utils.ConstantsManager;

public class TextWatcherAdapter {

    private EditText mFirstValue;
    private EditText mSecondValue;
    private boolean mActionForFirst;
    private boolean mActionForSecond;
    private SomeAction mSomeAction;

    /**
     * Constructor for TextWatcherAdapter object
     * @param firstValue field which gives us value of first currency
     * @param nSecondValue field which gives us value of second currency
     * @param actionForFirst true - action method of SomeAction object will be done, false - will not be
     * @param actionForSecond true - action method of SomeAction object will be done, false - will not be
     * @param someAction object is contained method ACTION for changed text in EditText
     */
    public TextWatcherAdapter(EditText firstValue, EditText nSecondValue,
                              boolean actionForFirst, boolean actionForSecond, SomeAction someAction) {
        mFirstValue = firstValue;
        this.mSecondValue = nSecondValue;
        mActionForFirst = actionForFirst;
        mActionForSecond = actionForSecond;
        mSomeAction = someAction;
        setTextWatchers();
    }

    /**
     * Setter for mActionForFirst object
     * @param actionForFirst value for mActionForFirst
     */
    public void setActionForFirst(boolean actionForFirst) {
        mActionForFirst = actionForFirst;
    }

    /**
     * Setter for mActionForSecond object
     * @param actionForSecond value for mActionForSecond
     */
    public void setActionForSecond(boolean actionForSecond) {
        mActionForSecond = actionForSecond;
    }

    /**
     * Adds TextWatcher objects for all EditTexts from Constructors,
     * if text is changed and boolean value is true, action method from SomeAction object is done
     */
    private void setTextWatchers(){
        mFirstValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mActionForFirst){
                    mActionForFirst=false;
                    mActionForSecond=false;
                    mSecondValue.setText(ConstantsManager.EMPTY_STRING_VALUE);
                    DataManager.getInstance().getPreferenceManager()
                            .setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_FROM_UAH);
                    DataManager.getInstance().getPreferenceManager()
                            .setConverterValue(s.toString().trim());
                    mSomeAction.action();
                }

            }
        });

        mSecondValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(mActionForSecond){
                    mActionForFirst=false;
                    mActionForSecond=false;
                    mFirstValue.setText(ConstantsManager.EMPTY_STRING_VALUE);
                    DataManager.getInstance().getPreferenceManager()
                            .setConverterDirection(ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
                    DataManager.getInstance().getPreferenceManager()
                            .setConverterValue(s.toString().trim());
                    mSomeAction.action();
                }

            }
        });
    }

    /**
     * Interface which creates action method for afterTextChanged method of TextWatcher
     */
    public interface SomeAction{
        void action();
    }
}
