package ru.translator.interfaces;

import java.util.List;

import ru.translator.models.Tr;

/**
 * Created by Ruslan on 05.06.2017.
 */

public interface TranslateListener {
    void onShortTranslate(String s);
    void onFullTranslate(List<Tr> trs);
}
