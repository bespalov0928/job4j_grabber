package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Post {

    String avtor = "";
    String description = "";
    String dateCreation = "";

    public Post(String url) throws IOException {
        Map filds = postParser(url);
        avtor = (String) filds.get("avtor");
        description = (String) filds.get("description");
        dateCreation = (String) filds.get("dateCreation");
    }

    public static Map postParser(String url) throws IOException {

        HashMap map = new HashMap();
        map.put("avtor", "");
        map.put("description", "");
        map.put("dateCreation", "");

        String avtor = "";
        String description = "";
        String dateCreation = "";

        //Post post = new Post();

        Document doc = Jsoup.connect(url).get();
        Elements rowMsgTableArr = doc.select(".msgTable");

        Element rowMsgTable = rowMsgTableArr.get(0);
        Elements rowMsgBodyArr = rowMsgTable.select(".msgBody");

        Element rowAvtor = rowMsgBodyArr.get(0);
        Element href = rowAvtor.child(0);
        avtor = href.text();

        Element rowDescription = rowMsgBodyArr.get(1);
        description = rowDescription.text();

        Elements rowMsgFooterArr = rowMsgTable.select(".msgFooter");
        dateCreation = rowMsgFooterArr.get(0).text();
        String[] dateCreationArr = dateCreation.split(",");

//        post.avtor = avtor;
//        post.description = description;
//        post.dateCreation = dateCreation;

        map.put("avtor", avtor);
        map.put("description", description);
        map.put("dateCreation", dateCreationArr[0]);
        return map;
    }
}
