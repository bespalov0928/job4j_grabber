package ru.job4j.grabber;

import java.io.*;
import java.util.Properties;

public class ClassLoaderDemo {
    public static Properties getResource() {

        Properties cfg = new Properties();
        //String path = "src\\main\\resource\\rabbit.properties";
        String rsc = "rabbit.properties";
        String val = "";
        InputStream i = null;
        try {
            Class cls = Class.forName("ru.job4j.grabber.ClassLoaderDemo");
            ClassLoader cLoader = cls.getClassLoader();

            i = cLoader.getResourceAsStream(rsc);
            String a = "";
            //BufferedReader r = new BufferedReader(new InputStreamReader(i));
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (InputStream defaultsStream = ClassLoaderDemo.class.getResourceAsStream(rsc)) {

            cfg.load(i);
        } catch (IOException e) {
            e.printStackTrace();
        }


//        try (FileInputStream in = new FileInputStream(filePath)) {
//            cfg.load(in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return cfg;
//
//        String val = "";
//        try {
//            Class cls = Class.forName("ClassLoaderDemo");
//            ClassLoader cLoader = cls.getClassLoader();
//
//            InputStream i = cLoader.getResourceAsStream(rsc);
//            BufferedReader r = new BufferedReader(new InputStreamReader(i));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        return cfg;
    }
}
