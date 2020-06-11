package com.example.lop.androidnote.base;

/**
 * @author: lop
 * @createTime: 2020-05-14
 * @desc:
 */
public class Constants {
    public static final String APP_KEY="ba8f5e734fa442cd";
    public static final String HOSTS="https://api.jisuapi.com/";
    public static final String FOOD_MENU="recipe/";

    public static String getUrl(String urlName){
        return HOSTS+FOOD_MENU+urlName;
    }
}
