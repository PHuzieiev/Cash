package com.apps.newstudio.cash.data.managers;

import com.apps.newstudio.cash.utils.ConstantsManager;

public abstract class LanguageManager {

    public abstract void engLanguage();
    public abstract void ukrLanguage();
    public abstract void rusLanguage();


    public LanguageManager() {
        switch (DataManager.getInstance().getPreferenceManager()
                .loadString(ConstantsManager.LANGUAGE_KEY,ConstantsManager.LANGUAGE_ENG)){
            case ConstantsManager.LANGUAGE_ENG:
                engLanguage();
                break;
            case ConstantsManager.LANGUAGE_RUS:
                rusLanguage();
                break;
            case ConstantsManager.LANGUAGE_UKR:
                ukrLanguage();
                break;
        }
    }
}
