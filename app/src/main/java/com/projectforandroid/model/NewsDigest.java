package com.projectforandroid.model;

import java.util.Arrays;

/**
 * Created by exkulo on 9/22/2015.
 * 新闻列表里面的列表项目
 */
public class NewsDigest {
    /**
     * 标题
     */
    String title;
    /**
     * 超链接
     */
    String link;
    /**
     * 发布日期
     */
    String pubDate;
    /**
     * 新闻来源
     */
    String source;
    /**
     * 新闻简要描述
     */
    String desc;
    /**
     * 频道id
     */
    String channelId;
    /**
     * 频道名称
     */
    String channelName;
    /**
     * 新闻对应的外网id
     */
    String nid;
    /**
     * 图片列表
     */
    NewsImage[] imageurls;

    /**
     * 频道id
     */
    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * 频道名称
     */
    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    /**
     * 新闻简要描述
     */
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    /**
     * 图片列表
     */
    public NewsImage[] getImageUrls() {
        return imageurls;
    }

    public void setImageUrls(NewsImage[] imageUrls) {
        this.imageurls = imageUrls;
    }

    /**
     * 超链接
     */
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return 新闻对应的外网id
     */
    public String getnId() {
        return nid;
    }

    public void setnId(String nId) {
        this.nid = nId;
    }

    /**
     * @return 发布时间
     */
    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * 来源网站
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    /**
     * 标题
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsDigest{" +
            "channelId='" + channelId + '\'' +
            ", title='" + title + '\'' +
            ", link='" + link + '\'' +
            ", pubDate='" + pubDate + '\'' +
            ", source='" + source + '\'' +
            ", desc='" + desc + '\'' +
            ", channelName='" + channelName + '\'' +
            ", nid='" + nid + '\'' +
            ", imageurls=" + Arrays.toString(imageurls) +
            '}';
    }
}
