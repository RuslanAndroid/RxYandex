package ru.translator.util;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by Ruslan on 05.06.2017.
 */

public abstract class TextChangeListener implements TextWatcher {
    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
