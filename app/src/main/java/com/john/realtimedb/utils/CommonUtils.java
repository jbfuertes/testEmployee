package com.john.realtimedb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 11/30/17.
 */

public final class CommonUtils {


    private CommonUtils(){

    }

    public static float setRatingBar(int starCount,float rating,float maxRate){
        float percentRating = (rating/maxRate)*100;
        float percentPerStar = (100/starCount);
        return percentRating/percentPerStar;
    }

    public static boolean isEmailValid(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isEmailValid(CharSequence target) {
        return target != null && EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isFieldsNotNull(String[] fields){
        for(String field:fields){
            if (field==null||field.isEmpty()){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidBday(String dateFormat,String bday,String minBday){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            Date strDate1 = sdf.parse(bday);
            Date strDate2 = sdf.parse(minBday);
            return !strDate1.after(strDate2);
        } catch (ParseException e) {
            e.printStackTrace();
            return true;
        }
    }
    private static Pattern EMAIL_ADDRESS
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );


}
