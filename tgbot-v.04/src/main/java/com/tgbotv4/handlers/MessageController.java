package com.tgbotv4.handlers;

import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.handlers.handlerServices.MessageParser;
import com.tgbotv4.handlers.handlerServices.ShowChannels;
import com.tgbotv4.handlers.handlerServices.WelcomeService;
import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.services.ChannelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoun on 26.03.2018.
 */
@Controller
public class MessageController {
    @Autowired
    MessageParser messageParser;
    @Autowired
    WelcomeService welcomeService;
    @Autowired
    BuyService buyService;
    @Autowired
    ShowChannels showChannels;
    @Autowired
    ChannelInfoService channelInfoService;

    private static final Logger logger = LogManager.getLogger(QueryController.class);

    public SendMessage handleIncomingMessage(Message message, ReplyKeyboardMarkup keyboardMarkup) throws TelegramApiException {

        SendMessage sendMessage = new SendMessage();

        sendMessage.enableMarkdown(true);
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        sendMessage.setChatId(message.getChatId().toString());

//        for(ChannelInfo c : channelInfoService.searchForChannelInfo("4", PageRequest.of(0, 10))) {
//            logger.info(c.getChannelUrl());
//        }
//        for(ChannelInfo c : channelInfoService.searchForChannelInfo("af", PageRequest.of(0, 10))) {
//            logger.info(c.getChannelUrl());
//        }
//        for(ChannelInfo c : channelInfoService.searchForChannelInfo("@tt4ch", PageRequest.of(0, 10))) {
//            logger.info(c.getChannelUrl());
//        }
//        for(ChannelInfo c : channelInfoService.searchForChannelInfo("tt4", PageRequest.of(0, 10))) {
//            logger.info(c.getChannelUrl());
//        }
//
//        logger.info(channelInfoService.searchForChannelInfo("tt", PageRequest.of(0, 10)).getTotalElements());
//        logger.info(channelInfoService.searchForChannelInfo("af", PageRequest.of(0, 10)).getTotalElements());
//        logger.info(channelInfoService.searchForChannelInfo("@tt4ch", PageRequest.of(0, 10)).getTotalElements());
//        logger.info(channelInfoService.searchForChannelInfo("tt4", PageRequest.of(0, 10)).getTotalElements());

        int state = messageParser.commandParser(message);//        этот метод стоит убрать(лишние действия)

        switch (state) {
            case MenuBut.WELCOME:
                row.add(BotTextMessage.BUYAD);
                row.add(BotTextMessage.SELLAD);
                keyboard.add(row);
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);

                return welcomeService.welcomePage(message, sendMessage);
            case MenuBut.BUYAD:
                row2.add(BotTextMessage.BUYAD_FILTER_SEARCH);
                row.add(BotTextMessage.BUYAD_FILTER_CATEGORY);
                row.add(BotTextMessage.BUYAD_FILTER_LOCATION);
                row2.add(BotTextMessage.BUYAD_FILTER_QUANTITY);
                row3.add(BotTextMessage.BACK);

                keyboard.add(row);
                keyboard.add(row2);
                keyboard.add(row3);
                keyboardMarkup.setOneTimeKeyboard(true);
                keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setResizeKeyboard(true);
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage = buyService.chooseFilter(sendMessage, keyboard);

                return sendMessage;
            case MenuBut.BACK:
                sendMessage.setText("back");
                break;
            case MenuBut.BUYAD_FILTER_CATEGORY:
                sendMessage.setText( "Категории : \n");
                return buyService.categoriesList(sendMessage);
            case MenuBut.BUYAD_FILTER_LOCATION:
                sendMessage.setText("here i get all locations from db and put one by one");
//                return buyService.locationList();
                break;
            case MenuBut.BUYAD_FILTER_QUANTITY:
                sendMessage.setText("here i let user put two number or give him basic (>1,000, <10,000)");
//                return buyService.getquantity();
                break;
            case MenuBut.BUYAD_FILTER_SEARCH: {
                if(message.getText().equals(BotTextMessage.BUYAD_FILTER_SEARCH)) {
                    return sendMessage.setText("Для поиска канала отправьте нам название канала(ссылку) в виде : @название");
                }
                InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                String searchStr = "";
                if (messageParser.validateSearchQuery(message.getText())) {
                    if (message.getText().startsWith("@")) {
                        searchStr = message.getText().split("[@]")[1];
                    } else
                        searchStr = message.getText();
                } else {
                    return sendMessage.setText("Вы ввели неправильно...");
                }
                Page<ChannelInfo> channelInfos = channelInfoService.searchForChannelInfo(searchStr, PageRequest.of(0, 10));
                if (channelInfos.getTotalElements() == 1) {
                    int id = channelInfos.stream().findFirst().get().getId();
                    ChannelInfo channelInfo = channelInfoService.getChannelInfoById(id);
                    return sendMessage.setText(showChannels.showChannelById(channelInfo).toString())
                            .enableHtml(true);
                }
                if(channelInfos.getTotalElements() == 0) {
                    return sendMessage.setText("Нам не удалось найти такой канал. \n Попробуйте снова");
                }
                else {
                    showChannels.setTotalPages(channelInfos.getTotalPages());
                    String filterType = "search@" + searchStr;
                    rowsInline.add(showChannels.getListOfCurrentPages(rowInline, 0, filterType));
                    markupInline.setKeyboard(rowsInline);
                    return sendMessage.setText(showChannels.showChannels(channelInfos, 0))
                            .setReplyMarkup(markupInline)
                            .enableHtml(true)
                            .disableWebPagePreview();
                }
            }
            case MenuBut.SHOW_CHANNEL_INFO:
                int id = messageParser.getChannelIdToShow(message);
                ChannelInfo channelInfo = channelInfoService.getChannelInfoById(id);
                return sendMessage.setText(showChannels.showChannelById(channelInfo).toString())
                        .enableHtml(true);
            default:
                return sendMessage.setText("hello");
        }
        return sendMessage;
    }
}
