package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;


public class SqlRuParse implements Parse {

    @Override
    public List<Post> list(String url) throws ParseException, IOException {

        List<Post> listPost = new LinkedList<>();

        String name = "";
        String text = "";
        String link = "";
        Date created = new Date();

        Document doc = Jsoup.connect(url).get();

        Elements row = doc.select(".postslisttopic");
        Elements rowAltCol = doc.select(".altCol");

        for (int index = 0; index < row.size(); index++) {
            Post post = new Post();
            Element href = row.get(index).child(0);
            text = href.attr(href.text());
            link = href.attr("href");
            if (index % 2 != 0) {
                String dataString = rowAltCol.get(index * 2 - 1).text();
                created = FormatDate.formatDate(dataString);

            } else {
                name = rowAltCol.get(index).text();
            }
            post.setName(name);
            post.setText(text);
            post.setLink(link);
            post.setCreated(created);
            listPost.add(post);
        }
        return listPost;
    }


    @Override
    public Post detail(String link) throws IOException, ParseException {
        Post post = new Post("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        return post;
    }

    public static void main(String[] args) throws Exception {
        SqlRuParse sqlRuParse = new SqlRuParse();

        for (int list = 1; list <= 5; list++) {
            List<Post> listTemp = sqlRuParse.list(String.format("https://www.sql.ru/forum/job-offers/%s", list));
            for (Post post : listTemp) {
                System.out.println(post.getName());
                System.out.println(post.getText());
                System.out.println(post.getLink());
                System.out.println(post.getCreated());
            }

        }
        Post post = sqlRuParse.detail("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        System.out.println(post.getName());
        System.out.println(post.getText());
        System.out.println(post.getLink());
        System.out.println(post.getCreated());
    }
}


