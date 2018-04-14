package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.handlers.QueryController;
import com.tgbotv4.persistence.entities.ChannelInfo;
import com.tgbotv4.services.ChannelInfoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

/**
 * Created by yaoun on 10.04.2018.
 */
@Service
public class ShowChannels {
    @Autowired
    ChannelInfoService channelInfoService;

    private int totalPagesByCategory;
    private static final Logger logger = LogManager.getLogger(QueryController.class);

    public String showChannelsByCategory(int categoryId, int page){
        String answer = "";
        int counter = 1;
        try {
            Page<ChannelInfo> channelInfos = channelInfoService.getChannelInfoByCategory(categoryId, PageRequest.of(page, 10));

            totalPagesByCategory = channelInfos.getTotalPages();
            for(ChannelInfo i : channelInfos) {
                answer+= counter + ". "
                        + i.getChannelUrl() + "\n"
                        + i.getChannelDescriptione() + "\n"
                        + "/showInfo" + i.getId() + "\n\n";
                counter++;
            }
        }catch (Exception e) {
            logger.catching(e);
        }
        return answer;
    }

    public int getTotalPagesByCategory () {
        return totalPagesByCategory;
    }

    public List<InlineKeyboardButton> getListOfCurrentPages(List<InlineKeyboardButton> rowInLine, int currPage, int categoryId){
        int page=0;
        int prevPage = 0;
        if(currPage==0) {
            prevPage = 1;
        } else prevPage = currPage+1;

        if(currPage>=2) {
            page = currPage - 2;
        }
        if(currPage>=totalPagesByCategory)
        {
            page = 0;
        }

        for(int i=0;i<5;i++) {
            page+=1;
            if(i==4 && page<=totalPagesByCategory) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText(totalPagesByCategory + "»")
                        .setCallbackData("category" + categoryId + ".page" + (getTotalPagesByCategory()-1)));
                continue;
            }
            if(i==3 && (totalPagesByCategory-2)>page && page<=totalPagesByCategory) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText("" + page + "›")
                        .setCallbackData("category" + categoryId + ".page" + (page-1)));
                continue;
            }
            if(i==1 && prevPage>3 && page<=totalPagesByCategory) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText("‹" + page)
                        .setCallbackData("category" + categoryId + ".page" + (page - 1)));
                continue;
            }
            if(i==0 && prevPage>3 && page<=totalPagesByCategory){
                if(page==prevPage) {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("•" + page + "•")
                            .setCallbackData("category" + categoryId + ".page" + (page - 1)));
                }else {
                rowInLine.add(new InlineKeyboardButton()
                        .setText("«" + 1)
                        .setCallbackData("category" + categoryId + ".page0"));
                }
                continue;
            }
            else if(page<=totalPagesByCategory){
                if(page==prevPage) {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("•" + page + "•")
                            .setCallbackData("category" + categoryId + ".page" + (page - 1)));
                }
                else {
                        rowInLine.add(new InlineKeyboardButton()
                                .setText("" + page)
                                .setCallbackData("category" + categoryId + ".page" + (page-1)));
                }
            }
            else
                continue;
        }
        return rowInLine;
    }

    public SendMessage showChannel(SendMessage sendMessage, int id) {
        String answer = "";
        ChannelInfo channelInfo = channelInfoService.getChannelInfoById(id);
            answer = "<i>" + channelInfo.getChannelName() + "</i>\n"
                    + "<b>Описание : </b>\n"
                    + channelInfo.getChannelDescriptione() + "\n"
                    + channelInfo.getChannelUrl() + "\n";
            sendMessage.setText(answer)
                    .enableHtml(true);
        return sendMessage;
    }
}
