package ru.job4j.grabber;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormatDate {

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

    public static Date formatDate(String dataString) throws ParseException {

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
            manch = (String) MAPMONTHS.get(dataArr[1]);
            year = dataArr[2].replace(",", "");
        }

        String dataInStringNew = String.format("%s-%s-%s", day, manch, year);
        Date dateNew = formatter.parse(dataInStringNew);

        return dateNew;
    }

}
