package com.tgbotv4.conf;

/**
 * Created by yaoun on 26.03.2018.
 *  values : 1 - 30 кнопки меню
 *
 *  values : 31 - 99 инлайн кнопки
 *  values : 100 - 30 кнопки
 */
public class MenuBut {
    public static final int WELCOME = 1;

    public static final String LOGTAG = "CommandHandler";

    public static final int BUYAD = 3;
    public static final int BUYAD_FILTER_CATEGORY = 31;
    public static final int BUYAD_FILTER_LOCATION = 32;
    public static final int BUYAD_FILTER_QUANTITY = 33;
    public static final int BUYAD_FILTER_TOP = 34;


    public static final int SELLAD = 2;

    public static final int BACK = 11;

    public static final Integer DESIGN_CATEGORY = 101;
    public static final Integer IT_CATEGORY = 102;

    /*этот текст позже перенесем в бд, но вообще ще стоит подумать,
     или делать отдельный каталог с этими фразами, но адм должен иметь доступ*/
}