package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Post {

    private String name = "";
    private String text = "";
    private String link = "";
    private Date created = new Date();

    public Post(String url) throws IOException, ParseException {
        Map filds = Post.postParser(url);
        name = (String) filds.get("name");
        text = (String) filds.get("text");
        link = (String) filds.get("link");
        created = (Date) filds.get("created");
    }

    public Post() {
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Date getCreated() {
        return created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public static Map<String, String> postParser(String url) throws IOException, ParseException {

        HashMap map = new HashMap<String, String>();
        map.put("name ", "");
        map.put("text ", "");
        map.put("link ", "");
        map.put("created ", new Date());

        String name = "";
        String text = "";
        String link = "";
        Date created = new Date();

        Document doc = Jsoup.connect(url).get();
        Elements rowMsgTableArr = doc.select(".msgTable");

        Element rowMsgTable = rowMsgTableArr.get(0);
        Elements rowMsgBodyArr = rowMsgTable.select(".msgBody");

        Element rowAvtor = rowMsgBodyArr.get(0);
        Element href = rowAvtor.child(0);
        name = href.text();

        Element rowDescription = rowMsgBodyArr.get(1);
        text = rowDescription.text();

        Elements rowMsgFooterArr = rowMsgTable.select(".msgFooter");
        String createdString = rowMsgFooterArr.get(0).text();
        String[] dateCreationArr = createdString.split(",");
        created = FormatDate.formatDate(dateCreationArr[0]);

        map.put("name", name);
        map.put("text", text);
        map.put("link", link);
        map.put("created", created);
        return map;
    }
}
