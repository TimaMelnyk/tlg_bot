package com.tgbotv4.handlers;

import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.handlers.handlerServices.MessageParser;
import com.tgbotv4.handlers.handlerServices.ShowChannels;
import com.tgbotv4.services.CategoriesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

/**
 * Created by yaoun on 27.03.2018.
 */
@Controller
public class QueryController {
    @Autowired
    MessageParser messageParser;
    @Autowired
    BuyService buyService;
    @Autowired
    CategoriesService categoriesService;
    @Autowired
    ShowChannels showChannels;

    private static final Logger logger = LogManager.getLogger(QueryController.class);

    public EditMessageText handleIncomingMessage(Update update, EditMessageText editMessageText) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int currState = messageParser.queryParser(call_data);
        int currPage = messageParser.getPageId(call_data);
        int categoryId = messageParser.getCategoryId(call_data);
        logger.info("current page : " + currPage );
        editMessageText
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id));

        switch (currState) {
            case MenuBut.BUYAD_FILTER_CATEGORY:
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();

                    editMessageText
                            .setText(showChannels.showChannelsByCategory(categoryId, currPage))
                            .disableWebPagePreview();
                    rowsInline.add(showChannels.getListOfCurrentPages(rowInline, currPage, categoryId));
                    markupInline.setKeyboard(rowsInline);
                    editMessageText
                            .setReplyMarkup(markupInline);

                    return editMessageText;
            case 32: break;
            case 33: break;
            case 34: break;
            case MenuBut.BACK:

                return editMessageText;
            default:
                editMessageText
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText("something went wrong :(");
                return editMessageText;
        }
        return editMessageText;
    }

    public SendMessage goBack(Message message){
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId().toString());
            return buyService.categoriesList(sendMessage);
        }
}
