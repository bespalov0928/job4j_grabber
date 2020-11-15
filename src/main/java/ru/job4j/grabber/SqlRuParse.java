package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;



public class SqlRuParse {


    public static void main(String[] args) throws Exception {
        String url = "";

        for (int list = 1; list <= 5; list++) {
            url = String.format("https://www.sql.ru/forum/job-offers/%s", list);
            Document doc = Jsoup.connect(url).get();
            Elements row = doc.select(".postslisttopic");
            Elements rowAltCol = doc.select(".altCol");

            for (int index = 0; index < row.size(); index++) {
                Element href = row.get(index).child(0);
                //System.out.println(href.attr("href"));
                //System.out.println(href.attr(href.text()));

                if (index % 2 != 0) {
                    //System.out.println(rowAltCol.get(index * 2 - 1).attr("altCol"));
                    String dataString = rowAltCol.get(index * 2 - 1).text();
                    Date dateNew = FormatDate.formatDate(dataString);

                    //System.out.println(dataString);
                    //System.out.println(dateNew);
                }
            }
        }
        Post post = new Post("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
//        post.dateCreation = String.valueOf(FormatDate.formatDate(post.dateCreation));
        System.out.println(post.getName());
        System.out.println(post.getText());
        System.out.println(post.getLink());
        System.out.println(post.getCreated());
    }
}
