package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import ru.job4j.grabber.ClassLoaderDemo;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;

public class AlertRabbit {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Properties cfg;
        cfg = ClassLoaderDemo.getResource();

        Class.forName("org.postgresql.Driver");
        String url = cfg.getProperty("jdbc.url");
        String login = cfg.getProperty("jdbc.username");
        String password = cfg.getProperty("jdbc.password");

        JobDataMap data = new JobDataMap();

        try (Connection connection = DriverManager.getConnection(url, login, password)) {
            List<Long> store = new ArrayList<>();

            data.put("store", store);
            data.put("connect", connection);

            try {

                Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
                scheduler.start();

                JobDetail job = newJob(Rabbit.class)
                        .usingJobData(data)
                        .build();
                SimpleScheduleBuilder times = simpleSchedule()
                        .withIntervalInSeconds(Integer.parseInt(cfg.getProperty("rabbit.interval")))
                        .repeatForever();
                Trigger trigger = newTrigger()
                        .startNow()
                        .withSchedule(times)
                        .build();
                scheduler.scheduleJob(job, trigger);

                Thread.sleep(10000);
                scheduler.shutdown();

            } catch (Exception se) {
                se.printStackTrace();
            }
        }
    }

    public static class Rabbit implements Job {

        public Rabbit() {
            System.out.println(hashCode());
        }

        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("Rabbit runs here ...");

            Connection connect = (Connection) context.getJobDetail().getJobDataMap().get("connect");

            AlertRabbit.add(connect, "Task end");
        }
    }

    private static Properties reads(String filePath) {

        Properties cfg = new Properties();



        try (FileInputStream in = new FileInputStream(filePath)) {
            cfg.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cfg;
    }





    public static void add(Connection connect, String text) {
        try (PreparedStatement ps = connect.prepareStatement("insert into rabbit(comment) values (?)")) {
            ps.setString(1, text);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
