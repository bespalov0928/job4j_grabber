package ru.job4j.grabber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormatDate {

    public static Date formatDate(String dataString, HashMap mapMonths) throws ParseException {

        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy", Locale.ENGLISH);
        String[] dataTimeArr = dataString.split(",");
        String[] dataArr = dataTimeArr[0].split(" ");

        Calendar calendar = new GregorianCalendar();
        String day = "";
        String manch = "";
        String year = "";

        if (dataArr.length == 1) {
            manch = String.valueOf(calendar.get(Calendar.MONTH));
            year = String.valueOf(calendar.get(Calendar.YEAR));
            if (dataArr[0].equals("вчера")) {
                Date getTime = calendar.getTime();
                day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) - 1);
            } else if (dataArr[0].equals("сегодня")) {
                day = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
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
