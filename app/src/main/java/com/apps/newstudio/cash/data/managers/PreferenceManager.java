package com.apps.newstudio.cash.data.managers;

import android.content.SharedPreferences;

import com.apps.newstudio.cash.utils.CashApplication;
import com.apps.newstudio.cash.utils.ConstantsManager;

public class PreferenceManager {
    private SharedPreferences mSharedPreferences;


    /**
     * Gets SharedPreferences object from CashApplication object
     *
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
        editor.apply();
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
     * Saves String value which is used to define App Language
     *
     * @param parameter String object which defines App Language
     */
    public void setLanguage(String parameter){
        saveString(ConstantsManager.LANGUAGE_KEY,parameter);
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
    public void setCurrenciesSortParameter(String parameter) {
        saveString(ConstantsManager.CURRENCY_SORT_PARAMETER, parameter);
    }

    /**
     * Loads String value which is used to define sort parameter for list in Currency Activity
     *
     * @return String object
     */
    public String getCurrenciesSortParameter() {
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


    /**
     * Loads String value which defines direction of convertation in Converter Fragment
     *
     * @return String object
     */
    public String getConverterDirection() {
        return loadString(ConstantsManager.CONVERTER_DIRECTION_KEY,
                ConstantsManager.CONVERTER_DIRECTION_TO_UAH);
    }

    /**
     * Saves String value which defines direction of convertation in Converter Fragment
     *
     * @param parameter String object for defining direction of convertation
     */
    public void setConverterDirection(String parameter) {
        saveString(ConstantsManager.CONVERTER_DIRECTION_KEY, parameter);
    }


    /**
     * Loads String value which defines value for convertation in Converter Fragment
     *
     * @return String object
     */
    public String getConverterValue() {
        return loadString(ConstantsManager.CONVERTER_VALUE_KEY,
                ConstantsManager.CONVERTER_VALUE_DEFAULT);
    }

    /**
     * Saves String value which defines value for convertation in Converter Fragment
     *
     * @param parameter String object for defining value for convertation
     */
    public void setConverterValue(String parameter) {
        saveString(ConstantsManager.CONVERTER_VALUE_KEY, parameter);
    }

    /**
     * Loads String value which defines value for template id
     *
     * @return String object
     */
    public String getTemplateId() {
        return loadString(ConstantsManager.CONVERTER_TEMPLATE_ID_KEY, ConstantsManager.CONVERTER_TEMPLATE_ID_DEFAULT);
    }

    /**
     * Saves String value which defines value for template id
     *
     * @param parameter String object for defining value for convertation
     */
    public void setTemplateId(String parameter) {
        saveString(ConstantsManager.CONVERTER_TEMPLATE_ID_KEY, parameter);
    }


    /**
     * Loads String value which defines what object opened converter
     *
     * @return String object
     */
    public String getConverterRoot() {
        return loadString(ConstantsManager.CONVERTER_OPEN_FROM_KEY, ConstantsManager.EMPTY_STRING_VALUE);
    }

    /**
     * Saves String value which defines what object opened converter
     *
     * @param parameter String object for defining what object opened converter
     */
    public void setConverterRoot(String parameter) {
        saveString(ConstantsManager.CONVERTER_OPEN_FROM_KEY, parameter);
    }
}
