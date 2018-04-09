package com.tgbotv4.handlers;

import com.tgbotv4.conf.MenuBut;
import com.tgbotv4.handlers.handlerServices.BuyService;
import com.tgbotv4.handlers.handlerServices.MessageParser;
import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.services.CategoriesService;
import com.tgbotv4.services.ChannelInfoService;
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
    ChannelInfoService channelInfoService;
    @Autowired
    CategoriesService categoriesService;


    public EditMessageText handleIncomingMessage(Update update, EditMessageText editMessageText) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        String call_data = update.getCallbackQuery().getData();
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        int currState = messageParser.currState;

        switch (currState) {
            case MenuBut.BUYAD_FILTER_CATEGORY:
                if(categoriesService.categoryExist(Integer.parseInt(call_data))){
                    System.out.println("hello category");
                    List<InlineKeyboardButton> rowInline = new ArrayList<>();
                    rowInline.add(new InlineKeyboardButton()
                            .setText("twerk")
                            .setCallbackData("twerk"));
                    rowsInline.add(rowInline);

                    markupInline.setKeyboard(rowsInline);
                    String answer = "";
                    int counter = 1;
                    List<ChannelInfo> channelInfos = channelInfoService.getChannelInfoByCategory(Integer.valueOf(call_data));
                    for(ChannelInfo i : channelInfos) {
                        answer+= counter + ". "
                                + i.getChannelName() + "\n"
                                + i.getChannelUrl() + "\n"
                                + i.getChannelDescriptione() + "\n";
                        counter++;
                    }
                    editMessageText
                            .setChatId(chat_id)
                            .setMessageId(toIntExact(message_id))
                            .setText(answer)
                            .setReplyMarkup(markupInline);
                    return editMessageText;
                }else
                    break;
            case 32: break;

            case 33: break;
            case 34: break;
            case MenuBut.BACK:

                return editMessageText;
            default:
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
