package com.tgbotv4.handlers.handlerServices;

import com.tgbotv4.handlers.QueryController;
import com.tgbotv4.persistence.entities.ChannelInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

/**
 * Created by yaoun on 10.04.2018.
 */
@Service
public class ShowChannels {
    private int totalPages;
    private static final Logger logger = LogManager.getLogger(QueryController.class);

    public String showChannels(Page<ChannelInfo> channelInfos, int page) {
        String answer = "";
        int counter;
        if (page == 0) {
            counter = 1;
        } else counter = page * 10;

        try {
            totalPages = channelInfos.getTotalPages();
            for (ChannelInfo i : channelInfos) {
                answer += counter + ". "
                        + i.getChannelUrl() + "\n"
                        + i.getChannelDescription() + "\n"
                        + "/showInfo" + i.getId() + "\n\n";
                counter++;
            }
        } catch (Exception e) {
            logger.catching(e);
        }
        return answer;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPagesByCategory) {
        this.totalPages = totalPagesByCategory;
    }

    public List<InlineKeyboardButton> getListOfCurrentPages(List<InlineKeyboardButton> rowInLine, int currPage, String filterType) {
        int page = 0;
        int prevPage = 0;
        if (currPage == 0) {
            prevPage = 1;
        } else prevPage = currPage + 1;

        if (currPage >= 2) {
            page = currPage - 2;
        }
        if (currPage >= totalPages) {
            page = 0;
        }

        for (int i = 0; i < 5; i++) {
            page += 1;
            if (i == 4 && page <= totalPages) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText(totalPages + "»")
                        .setCallbackData(filterType + ".page" + (totalPages - 1)));
                continue;
            }
            if (i == 3 && (totalPages - 2) > page && page <= totalPages) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText("" + page + "›")
                        .setCallbackData(filterType + ".page" + (page - 1)));
                continue;
            }
            if (i == 1 && prevPage > 3 && page <= totalPages) {
                rowInLine.add(new InlineKeyboardButton()
                        .setText("‹" + page)
                        .setCallbackData(filterType + ".page" + (page - 1)));
                continue;
            }
            if (i == 0 && prevPage > 3 && page <= totalPages) {
                if (page == prevPage) {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("•" + page + "•")
                            .setCallbackData(filterType + ".page" + (page - 1)));
                } else {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("«" + 1)
                            .setCallbackData(filterType + ".page0"));
                }
                continue;
            } else if (page <= totalPages) {
                if (page == prevPage) {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("•" + page + "•")
                            .setCallbackData(filterType + ".page" + (page - 1)));
                } else {
                    rowInLine.add(new InlineKeyboardButton()
                            .setText("" + page)
                            .setCallbackData(filterType + ".page" + (page - 1)));
                }
            } else
                continue;
        }
        return rowInLine;
    }

    public String showChannelById(ChannelInfo channelInfo) {
        String answer = "";
        answer = "<i>" + channelInfo.getChannelName() + "</i>\n"
                + "<b>Описание : </b>\n"
                + channelInfo.getChannelDescription() + "\n"
                + channelInfo.getChannelUrl() + "\n";
        return answer;
    }
}
