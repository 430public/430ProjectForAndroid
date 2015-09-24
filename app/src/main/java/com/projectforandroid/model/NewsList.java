package com.projectforandroid.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by exkulo on 9/22/2015.
 * 新闻列表
 */
public class NewsList {
    int allNum;
    int allPages;
    int currentPage;
    int maxResult;
    List<NewsDigest> contentlist;

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getAllPages() {
        return allPages;
    }

    public void setAllPages(int allPages) {
        this.allPages = allPages;
    }

    public List<NewsDigest> getContentlist() {
        return contentlist;
    }

    public void setContentlist(List<NewsDigest> contentlist) {
        this.contentlist = contentlist;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResult) {
        this.maxResult = maxResult;
    }

    @Override
    public String toString() {
        return "NewsList{" +
            "allNum=" + allNum +
            ", allPages=" + allPages +
            ", currentPage=" + currentPage +
            ", maxResult=" + maxResult +
            ", contentlist=" + contentlist +
            '}';
    }
}
