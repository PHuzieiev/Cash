package com.apps.newstudio.cash.data.managers;

import android.content.SharedPreferences;

import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;


    /**
     * Gets SharedPreferences object from CashApplication object
     *
     * @return SharedPreferences mSharedPreferences
     */
    public PreferenceManager() {
        mSharedPreferences = CashApplication.getSharedPreferences();
    }

    /**
     * Saves String value using mSharedPreferences object
     *
     * @param key   is String identificator which is used to save value
     * @param value is String object which will be saved
     */
    public void saveString(String key, String value) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * Loads String value from mSharedPreferences
     *
     * @param key          is String identificator which was used to save value
     * @param defaultValue is String value which will be returned if function do not find result with this key
     * @return String object
     */
    public String loadString(String key, String defaultValue) {
        return mSharedPreferences.getString(key, defaultValue);
    }

    /**
     * Loads String value which is used to define App Language
     *
     * @return String object
     */
    public String getLanguage() {
        return loadString(ConstantsManager.LANGUAGE_KEY, ConstantsManager.LANGUAGE_ENG);
    }

    /**
     * Loads String value which is used to define filter parameter for list in Organization Fragment
     *
     * @return String object
     */
    public String getOrganizationsFilterParameter() {
        return loadString(ConstantsManager.ORGANIZATIONS_FILTER_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value which is used to define filter parameter for list in Organization Fragment
     *
     * @param parameter String object which defines filter parameter
     */
    public void setOrganizationsFilterParameter(String parameter) {
        saveString(ConstantsManager.ORGANIZATIONS_FILTER_PARAMETER, parameter);
    }

    /**
     * Loads String value which is used to define search parameter for list in Organization Fragment
     *
     * @return String object
     */
    public String getOrganizationsSearchParameter() {
        return loadString(ConstantsManager.ORGANIZATIONS_SEARCH_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value which is used to define search parameter for list in Organization Fragment
     *
     * @param parameter String object which defines filter parameter
     */
    public void setOrganizationsSearchParameter(String parameter) {
        saveString(ConstantsManager.ORGANIZATIONS_SEARCH_PARAMETER, parameter);
    }

    /**
     * Saves String value which is used to define sort parameter for list in Currency Activity
     *
     * @param parameter String object which defines sort parameter
     */
    public void setCurrenciesSortParameter(String parameter){
        saveString(ConstantsManager.CURRENCY_SORT_PARAMETER, parameter);
    }

    /**
     * Loads String value which is used to define sort parameter for list in Currency Activity
     *
     * @return String object
     */
    public String getCurrenciesSortParameter(){
        return loadString(ConstantsManager.CURRENCY_SORT_PARAMETER, ConstantsManager.CURRENCY_SORT_PARAMETER_TITLE);
    }

    /**
     * Loads String value which is used to define filter parameter for list in Currencies Fragment
     *
     * @return String object
     */
    public String getCurrenciesFilterParameter() {
        return loadString(ConstantsManager.CURRENCIES_FILTER_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value which is used to define filter parameter for list in Currencies Fragment
     *
     * @param parameter String object which defines filter parameter
     */
    public void setCurrenciesFilterParameter(String parameter) {
        saveString(ConstantsManager.CURRENCIES_FILTER_PARAMETER, parameter);
    }

    /**
     * Loads String value which is used to define search parameter for list in Currencies Fragment
     *
     * @return String object
     */
    public String getCurrenciesSearchParameter() {
        return loadString(ConstantsManager.CURRENCIES_SEARCH_PARAMETER, ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value which is used to define search parameter for list in Currencies Fragment
     *
     * @param parameter String object which defines filter parameter
     */
    public void setCurrenciesSearchParameter(String parameter) {
        saveString(ConstantsManager.CURRENCIES_SEARCH_PARAMETER, parameter);
    }

    /**
     * Loads String value of currency which will be converted in Converter Fragment
     *
     * @return String object
     */
    public String getConverterCurrencyShortForm() {
        return loadString(ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_KEY,
                ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_DEFAULT);
    }

    /**
     * Saves String value of currency which will be converted in Converter Fragment
     *
     * @param parameter String object of currency
     */
    public void setConverterCurrencyShortForm(String parameter) {
        saveString(ConstantsManager.CONVERTER_CURRENCY_SHORT_FORM_KEY, parameter);
    }


    /**
     * Loads String value of organization id which will be used in Converter Fragment
     *
     * @return String object
     */
    public String getConverterOrganizationId() {
        return loadString(ConstantsManager.CONVERTER_ORGANIZATION_ID_KEY,
                ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value of organization id which will be used in Converter Fragment
     *
     * @param parameter String object of organization id
     */
    public void setConverterOrganizationId(String parameter) {
        saveString(ConstantsManager.CONVERTER_ORGANIZATION_ID_KEY, parameter);
    }

    /**
     * Loads String value which defines action in Converter Fragment
     *
     * @return String object
     */
    public String getConverterAction() {
        return loadString(ConstantsManager.CONVERTER_ACTION_KEY,
                ConstantsManager.CONVERTER_ACTION_SALE);
    }

    /**
     * Saves String value which defines action in Converter Fragment
     *
     * @param parameter String object for defining action
     */
    public void setConverterAction(String parameter) {
        saveString(ConstantsManager.CONVERTER_ACTION_KEY, parameter);
    }
}
