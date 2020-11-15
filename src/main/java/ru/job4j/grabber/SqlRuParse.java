package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;



public class SqlRuParse {

    public static final HashMap<String, String> MAPMONTHS = new HashMap<>();
    static {
        MAPMONTHS.put("янв", "01");
        MAPMONTHS.put("фев", "02");
        MAPMONTHS.put("мар", "03");
        MAPMONTHS.put("апр", "04");
        MAPMONTHS.put("май", "05");
        MAPMONTHS.put("июн", "06");
        MAPMONTHS.put("июл", "07");
        MAPMONTHS.put("авг", "08");
        MAPMONTHS.put("сен", "09");
        MAPMONTHS.put("окт", "10");
        MAPMONTHS.put("ноя", "11");
        MAPMONTHS.put("дек", "12");
    }

    public static void main(String[] args) throws Exception {
        String url = "";

        for (int list = 1; list <= 5; list++) {
            url = String.format("https://www.sql.ru/forum/job-offers/%s", list);
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements rowAltCol = doc.select(".altCol");

            for (int index = 0; index < row.size(); index++) {
                Element href = row.get(index).child(0);
                System.out.println(href.attr("href"));
                System.out.println(href.attr(href.text()));

                if (index % 2 != 0) {
                    //System.out.println(rowAltCol.get(index * 2 - 1).attr("altCol"));
                    String dataString = rowAltCol.get(index * 2 - 1).text();
                    Date dateNew = FormatDate.formatDate(dataString, MAPMONTHS);

                    System.out.println(dataString);
                    System.out.println(dateNew);
                }
            }
        }
        Post post = new Post("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        post.dateCreation = String.valueOf(FormatDate.formatDate(post.dateCreation, MAPMONTHS));
        System.out.println(post.dateCreation);
    }
}
