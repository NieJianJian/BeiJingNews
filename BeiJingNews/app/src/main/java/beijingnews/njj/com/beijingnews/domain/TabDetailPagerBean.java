package beijingnews.njj.com.beijingnews.domain;

import java.util.List;

/**
 * Created by jian on 2016/10/24.
 */
public class TabDetailPagerBean {

    private TabDetailPagerData data;
    private int retcode;

    public TabDetailPagerData getData() {
        return data;
    }

    public void setData(TabDetailPagerData data) {
        this.data = data;
    }

    public int getRetcode() {
        return retcode;
    }

    public void setRetcode(int retcode) {
        this.retcode = retcode;
    }

    public static class TabDetailPagerData { // static更好的释放
        private String countcommenturl;
        private String more;
        private List<News> news;
        private String title;
        private List topic; // 不考虑
        private List<Topnews> topnews;

        public List<Topnews> getTopnews() {
            return topnews;
        }

        public void setTopnews(List<Topnews> topnews) {
            this.topnews = topnews;
        }

        public String getCountcommenturl() {
            return countcommenturl;
        }

        public void setCountcommenturl(String countcommenturl) {
            this.countcommenturl = countcommenturl;
        }

        public String getMore() {
            return more;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public List<News> getNews() {
            return news;
        }

        public void setNews(List<News> news) {
            this.news = news;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List getTopic() {
            return topic;
        }

        public void setTopic(List topic) {
            this.topic = topic;
        }

        @Override
        public String toString() {
            return "TabDetailPagerData{" +
                    "countcommenturl='" + countcommenturl + '\'' +
                    ", more='" + more + '\'' +
                    ", news=" + news +
                    ", title='" + title + '\'' +
                    ", topic=" + topic +
                    ", topnews=" + topnews +
                    '}';
        }
    }

    public static class Topnews {
        private boolean comment;
        private String commentlist;
        private String commenturl;
        private int id;
        private String pubdate;
        private String title;
        private String topimage;
        private String type;
        private String url;

        public String getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(String commentlist) {
            this.commentlist = commentlist;
        }

        public boolean isComment() {
            return comment;
        }

        public void setComment(boolean comment) {
            this.comment = comment;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

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

        public String getTopimage() {
            return topimage;
        }

        public void setTopimage(String topimage) {
            this.topimage = topimage;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "Topnews{" +
                    "comment=" + comment +
                    ", commentlist='" + commentlist + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    ", id=" + id +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", topimage='" + topimage + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    public static class News {
        private boolean comment;
        private String commentlist;
        private String commenturl;
        private int id;
        private String listimage;
        private String pubdate;
        private String title;
        private String type;
        private String url;

        public boolean isComment() {
            return comment;
        }

        public void setComment(boolean comment) {
            this.comment = comment;
        }

        public String getCommentlist() {
            return commentlist;
        }

        public void setCommentlist(String commentlist) {
            this.commentlist = commentlist;
        }

        public String getCommenturl() {
            return commenturl;
        }

        public void setCommenturl(String commenturl) {
            this.commenturl = commenturl;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPubdate() {
            return pubdate;
        }

        public void setPubdate(String pubdate) {
            this.pubdate = pubdate;
        }

        public String getListimage() {
            return listimage;
        }

        public void setListimage(String listimage) {
            this.listimage = listimage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "News{" +
                    "comment=" + comment +
                    ", commentlist='" + commentlist + '\'' +
                    ", commenturl='" + commenturl + '\'' +
                    ", id=" + id +
                    ", listimage='" + listimage + '\'' +
                    ", pubdate='" + pubdate + '\'' +
                    ", title='" + title + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TabDetailPagerBean{" +
                "data=" + data +
                ", retcode=" + retcode +
                '}';
    }
}
