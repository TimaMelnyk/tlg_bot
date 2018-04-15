package com.tgbotv4.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yaoun on 16.03.2018.
 */

@Getter
@Setter
@Entity
@Table(name = "channel_info")
public class ChannelInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column(name = "channel_name", length = 56)
    private String channelName;

    @Column(name = "channel_url", length = 56)
    private String channelUrl;

    @Column(name = "channel_description", length = 256)
    private String channelDescription;

    @Column(name = "channel_category")
    private Integer channelCategory;

    @Column(name = "channel_subscribers", length = 56)
    private Integer channelSubscribers;

    public String getChannelName() {
        return channelName;
    }
}
