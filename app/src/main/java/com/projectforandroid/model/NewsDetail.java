package com.projectforandroid.model;

/**
 * Created by exkulo on 9/24/2015.
 */
public class NewsDetail {
    String laiyuan;
    String neirong;
    String pic;
    String shijian;
    String title;

    public String getLaiyuan() {
        return laiyuan;
    }

    public void setLaiyuan(String laiyuan) {
        this.laiyuan = laiyuan;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getShijian() {
        return shijian;
    }

    public void setShijian(String shijian) {
        this.shijian = shijian;
    }

    /**
     * 标题图片
     * @return
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "NewsDetail{" +
            "laiyuan='" + laiyuan + '\'' +
            ", neirong='" + neirong + '\'' +
            ", pic='" + pic + '\'' +
            ", shijian='" + shijian + '\'' +
            ", title='" + title + '\'' +
            '}';
    }
}
