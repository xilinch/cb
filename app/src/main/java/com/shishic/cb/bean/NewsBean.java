package com.shishic.cb.bean;


import com.shishic.cb.util.Constant;

public class NewsBean extends BaseBean {


    /**
     * id : 5
     * title : 李总牛逼分析
     * summary : 李总牛逼分析
     * createTime : 1560878859000
     * updateTime : 1560878859000
     * valid : 1
     * content : 李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析李总牛逼分析
     * cover : http://lucky-bk.oss-cn-shenzhen.aliyuncs.com/image/20190618/20190618132723_975.png
     */

    private int id;
    private String title;
    private String summary;
    private long createTime;
    private long updateTime;
    private int valid;
    private String content;
    private String cover;
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getUrl() {
        url = Constant.URL_NEWS_DETAIL_URL + id;
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", summary='" + summary + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", valid=" + valid +
                ", content='" + content + '\'' +
                ", cover='" + cover + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
