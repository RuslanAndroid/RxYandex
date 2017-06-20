package ru.translator.util;

import java.util.List;

import ru.translator.models.Syn;

/**
 * Created by Ruslan on 20.06.2017.
 */

public class StringUtils {
    public static synchronized String join(String separator, List list){
        String out = "";
        for (Object syn : list){
            out += syn + separator;
        }
        out = out.substring(0, out.length() - separator.length());
        return out;
    }
}
