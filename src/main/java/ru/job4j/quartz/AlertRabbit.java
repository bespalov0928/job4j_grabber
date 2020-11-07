package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) {
        Properties cfg;
        String filePath = "src\\main\\java\\ru\\job4j\\quartz\\rabbit.properties";
        cfg = Reads(filePath);

//        try {
//            FileInputStream in = new FileInputStream("src\\main\\java\\ru\\job4j\\quartz\\rabbit.properties");
//            //FileInputStream in = new FileInputStream("app.properties");
//            cfg.load(in);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDetail job = newJob(Rabbit.class).build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("rabbit.interval")))
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    public static class Rabbit implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");

//            try (BufferedReader in = new BufferedReader(new FileReader("src\\main\\java\\ru\\job4j\\quartz\\rabbit.properties"))) {
//                List<String> lines = new ArrayList<>();
//                in.lines().forEach(lines::add);
//                for (String line: lines) {
//                    System.out.println(line);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }

    private static Properties Reads(String filePath) {

        Properties cfg = new Properties();

        try (FileInputStream in = new FileInputStream(filePath)) {
            cfg.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cfg;
    }

}
