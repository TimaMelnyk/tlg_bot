package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.conf.BotTextMessage;
import com.tgbotv4.persistence.entities.Categories;
import com.tgbotv4.persistence.repositories.CategoriesRepository;
import com.tgbotv4.persistence.repositories.ChannelInfoRepository;
import com.tgbotv4.services.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaoun on 26.03.2018.
 */
@Service
public class BuyService {
    @Autowired
    ChannelInfoRepository channelInfoRepository;

    @Autowired
    CategoriesRepository categoriesRepository;

    @Autowired
    CategoriesService categoriesService;

    public SendMessage chooseFilter(SendMessage sendMessage, List<KeyboardRow> keyboard){
        sendMessage.setText(BotTextMessage.CHOOSE_FILTER_TEXT);
        return sendMessage;
    }

    public SendMessage categoriesList(SendMessage sendMessage){
        List <Categories>categories = categoriesService.getCategories();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> rowIn;
        for(Categories category : categories) {
            rowIn = new ArrayList<InlineKeyboardButton>();
            rowIn.add(new InlineKeyboardButton()
                    .setText(category.getCategoryName())
                    .setCallbackData(category.getId().toString()));
            rowsInline.add(rowIn);
        }
        markupInline.setKeyboard(rowsInline);
        sendMessage.setReplyMarkup(markupInline);
        return sendMessage;
    }

}
