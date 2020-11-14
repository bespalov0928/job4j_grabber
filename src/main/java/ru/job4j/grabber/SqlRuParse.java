package ru.job4j.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//import static java.util.Calendar.DAY_OF_MONTH;
//import static java.util.Calendar.MONTH;

public class SqlRuParse {

    public static void main(String[] args) throws Exception {


        String url = "";

        for (int list = 1; list <= 5; list++) {
            url = String.format("https://www.sql.ru/forum/job-offers/%s", list);
            //Document doc = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
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
                    Date dateNew = formatDate(dataString);

                    System.out.println(dataString);
                    System.out.println(dateNew);
                }
            }
        }

        Post post = new Post("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        //post.load("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        post.dateCreation = String.valueOf(formatDate(post.dateCreation));
        System.out.println(post.dateCreation);
    }

    public static Date formatDate(String dataString) throws ParseException {

        HashMap mapMonths = new HashMap();
        mapMonths.put("янв", "01");
        mapMonths.put("фев", "02");
        mapMonths.put("мар", "03");
        mapMonths.put("апр", "04");
        mapMonths.put("май", "05");
        mapMonths.put("июн", "06");
        mapMonths.put("июл", "07");
        mapMonths.put("авг", "08");
        mapMonths.put("сен", "09");
        mapMonths.put("окт", "10");
        mapMonths.put("ноя", "11");
        mapMonths.put("дек", "12");


        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH);
        String[] dataTimeArr = dataString.split(",");
        String[] dataArr = dataTimeArr[0].split(" ");

        Calendar calendar = new GregorianCalendar();
        String day = "";
        String manch = "";
        String year = "";

        if (dataArr.length == 1) {
            if (dataArr[0].equals("вчера")) {
                Date getTime = calendar.getTime();
                day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) - 1);
                manch = String.valueOf(calendar.get(Calendar.MONTH));
                year = String.valueOf(calendar.get(Calendar.YEAR));

            } else if (dataArr[0].equals("сегодня")) {
                day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
                manch = String.valueOf(calendar.get(Calendar.MONTH));
                year = String.valueOf(calendar.get(Calendar.YEAR));
            }
        } else {
            day = dataArr[0];
            manch = (String) mapMonths.get(dataArr[1]);
            year = dataArr[2].replace(",", "");
        }

        String dataInStringNew = String.format("%s-%s-%s", day, manch, year);
        Date dateNew = formatter.parse(dataInStringNew);

        return dateNew;
    }


}
