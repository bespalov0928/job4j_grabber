package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {

        Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();

        Elements row = doc.select(".postslisttopic");
//        for (Element td : row) {
//            Element href = td.child(0);
//            System.out.println(href.attr("href"));
//            System.out.println(href.attr(href.text()));
//         }
        Elements rowAltCol = doc.select(".altCol");

        for (int index = 0; index < row.size(); index++) {
            Element href = row.get(index).child(0);
            System.out.println(href.attr("href"));
            System.out.println(href.attr(href.text()));

            if (index % 2 != 0) {
                //System.out.println(rowAltCol.get(index * 2 - 1).attr("altCol"));
                System.out.println(rowAltCol.get(index * 2 - 1).text());
            }
        }
    }
}
