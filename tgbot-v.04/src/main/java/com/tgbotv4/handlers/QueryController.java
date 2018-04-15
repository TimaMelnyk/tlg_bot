package com.tgbotv4.handlers;

import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.handlers.handlerServices.MessageParser;
import com.tgbotv4.handlers.handlerServices.ShowChannels;
import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.services.CategoriesService;
import com.tgbotv4.services.ChannelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired
    ChannelInfoService channelInfoService;

    private static final Logger logger = LogManager.getLogger(QueryController.class);

    public EditMessageText handleIncomingMessage(Update update, EditMessageText editMessageText) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int currState = messageParser.queryParser(call_data);
        int currPage = messageParser.getPageId(call_data);

        editMessageText
                .setChatId(chat_id)
                .setMessageId(toIntExact(message_id));

        switch (currState) {
            case MenuBut.BUYAD_FILTER_CATEGORY: {
                int categoryId = messageParser.getCategoryId(call_data);
                Page<ChannelInfo> channelInfos = channelInfoService.getChannelInfoByCategory(categoryId, PageRequest.of(currPage, 10));
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                String filterType = "category" + categoryId; // here i set filter type (that help me to figure out which case to use)
                editMessageText
                        .setText(showChannels.showChannels(channelInfos, currPage))
                        .disableWebPagePreview();
                rowsInline.add(showChannels.getListOfCurrentPages(rowInline, currPage, filterType));
                markupInline.setKeyboard(rowsInline);
                editMessageText
                        .setReplyMarkup(markupInline);

                return editMessageText;
            }
            case MenuBut.BUYAD_FILTER_SEARCH: {
                String searchStr = messageParser.getSearchStr(call_data);
                Page<ChannelInfo> channelInfos = channelInfoService.searchForChannelInfo(searchStr, PageRequest.of(currPage, 10));
                List<InlineKeyboardButton> rowInline = new ArrayList<>();
                String filterType = "search@" + searchStr; // here i set filter type (that help me to figure out which case to use)
                showChannels.setTotalPages(channelInfos.getTotalPages());
                rowsInline.add(showChannels.getListOfCurrentPages(rowInline, currPage, filterType));
                markupInline.setKeyboard(rowsInline);

                editMessageText
                        .setText(showChannels.showChannels(channelInfos, currPage))
                        .disableWebPagePreview()
                        .setReplyMarkup(markupInline);

                return editMessageText;
            }
            case 33:
                break;
            case 34:
                break;
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

    public SendMessage goBack(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(message.getChatId().toString());
        return buyService.categoriesList(sendMessage);
    }
}
