package ru.translator.util;

import android.widget.Toast;

import static ru.translator.App.getContext;

/**
 * Created by Ruslan on 08.06.2017.
 */

public class Toaster {
    public static void show(String message){
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
