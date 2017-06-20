package ru.translator.ui.chooseLanguage;

import java.util.List;

import ru.translator.models.Lang;

/**
 * Created by Ruslan on 13.06.2017.
 */

public interface LanguageChooseView {
    void onLangsLoaded(List<Lang> langsList);
}
