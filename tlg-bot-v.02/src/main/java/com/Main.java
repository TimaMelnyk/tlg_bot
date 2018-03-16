package com;

/**
 * Created by yaoun on 17.08.2017.
 */
import com.controllers.StartController;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class Main {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new StartController());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}