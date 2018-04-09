package com.tgbotv4.conf;

/**
 * Created by yaoun on 06.04.2018.
 * пока что этот енам не нужен
 */
public enum MenuButText {
    BUYAD("Купить"),
    BUYAD_FILTER_CATEGORY("По категориям") {},
    BUYAD_FILTER_LOCATION("Локация") {},
    BUYAD_FILTER_QUANTITY("Кол-во подписчиков") {},
    BUYAD_FILTER_TOP("топ каналы") {},
    SELLAD("Продать") {},
    BACK("Назад") {};

    private final String claim;
    MenuButText(String claim) {
        this.claim = claim;
    }

    public String getClaim() {
        return claim;
    }
}
