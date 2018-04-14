package com.tgbotv4;


import com.tgbotv4.conf.BotConfigurations;
import com.tgbotv4.handlers.MessageController;
import com.tgbotv4.handlers.QueryController;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.services.CategoriesService;
import com.tgbotv4.services.ChannelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.logging.BotLogger;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


/**
 * Created by yaoun on 25.03.2018.
 */
@Component
public class BotInitializer extends TelegramLongPollingBot {

    @Autowired
    MessageController messageController;
    @Autowired
    QueryController queryController;
    @Autowired
    BuyService buyService;
    @Autowired
    ChannelInfoService channelInfoService;
    @Autowired
    CategoriesService categoriesService;
    private static final Logger logger = LogManager.getLogger(QueryController.class);
    static {
        ApiContextInitializer.init();
    }

    public String getBotUsername() {return BotConfigurations.CHANNEL_NAME;}

    public String getBotToken() {
        return BotConfigurations.KEY;
    }

    @PostConstruct
    public void registerBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            System.out.println(e);
        }
    }

    static String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

    public void onUpdateReceived(Update update) {
        try {
           /* String str = readFile("C:/Users/yaoun/Desktop/tg_stat.json", StandardCharsets.UTF_8);

            JSONObject obj = new JSONObject(str.substring(str.indexOf('{')));
            ChannelInfo channelInfo;
            Categories categories;

            JSONArray arr = obj.getJSONObject("items").getJSONArray("list");
            for (int i = 0; i < arr.length(); i++)
            {
                categories = new Categories();
                categories.setCategoryName(arr.getJSONObject(i).getString("category"));
                categoriesService.setCategory(categories);
                List<Categories> categories1 = categoriesService.getCategories();
                channelInfo = new ChannelInfo();
                channelInfo.setChannelUrl("@"+arr.getJSONObject(i).getString("username"));
                channelInfo.setChannelName(arr.getJSONObject(i).getString("title"));
                channelInfo.setChannelDescriptione("Мы предлагаем Вам открывать новые грани Киева и отбираем лучшие мероприятия для отдыха и развития.Связь: @glorymlory @spacef");
                channelInfo.setChannelCategory(categories1.get(i%21).getId());
                channelInfo.setChannelSubscribers(arr.getJSONObject(i).getInt("members"));
                channelInfoService.setChannelInfo(channelInfo);
            }*/

            if (update.hasMessage() && update.getMessage().hasText()) {
                Message message = update.getMessage();

                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                keyboardMarkup.setResizeKeyboard(true);
                if (message.hasText() || message.hasLocation()) {
                    SendMessage sendMessage;
                    sendMessage = messageController.handleIncomingMessage(message,keyboardMarkup);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
            else if (update.hasCallbackQuery()){
                EditMessageText editMessageText = new EditMessageText();
                    editMessageText = queryController.handleIncomingMessage(update,editMessageText);
                    try {
                        execute(editMessageText);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
            }
        } catch (Exception e) {
            BotLogger.error("CommandHandler", e);
        }
    }
}
