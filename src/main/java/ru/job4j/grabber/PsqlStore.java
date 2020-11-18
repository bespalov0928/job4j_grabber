package ru.job4j.grabber;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store, AutoCloseable {

    private Connection cnn;

    public static void main(String[] args) throws IOException, ParseException {
        Properties cfg = ClassLoaderDemo.getResource();
        PsqlStore psqlStore = new PsqlStore(cfg);

        SqlRuParse sqlRuParse = new SqlRuParse();
        Post post = sqlRuParse.detail("https://www.sql.ru/forum/1325330/lidy-be-fe-senior-cistemnye-analitiki-qa-i-devops-moskva-do-200t");
        //psqlStore.save(post);
        //psqlStore.getAll();
        psqlStore.findById("3");

    }

    public PsqlStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            cnn = DriverManager.getConnection(
                    cfg.getProperty("jdbc.url"),
                    cfg.getProperty("jdbc.username"),
                    cfg.getProperty("jdbc.password"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private static java.sql.Date getDateFromDateSql(java.util.Date date) {
        //java.util.Date today = new java.util.Date();
        return new java.sql.Date(date.getTime());
    }

    private static java.util.Date getDateSqlFromDate(java.sql.Date date) {
        //java.util.Date today = new java.util.Date();
        //date.valueOf(date.toLocalDate());
        return date.valueOf(date.toLocalDate());
    }

    @Override
    public void close() throws Exception {
        if (cnn != null) {
            cnn.close();
        }
    }

    @Override
    public void save(Post post) {
        try (PreparedStatement ps = cnn.prepareStatement("INSERT INTO rabbit(name, text, link, created) VALUES (?,?,?,?)")) {
            ps.setString(1, post.getName());
            ps.setString(2, post.getText());
            ps.setString(3, post.getLink());
            ps.setDate(4, getDateFromDateSql(post.getCreated()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> list = new LinkedList<>();
        try (PreparedStatement ps = cnn.prepareStatement("select * from rabbit")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Post post = new Post(rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5));
                list.add(post);
                System.out.println(post);
            }
        } catch (SQLException | IOException | ParseException e) {
            e.printStackTrace();
        }


        return list;
    }

    @Override
    public Post findById(String id) {
        Post post = new Post();
        try (PreparedStatement ps = cnn.prepareStatement("select * from rabbit where id = ?")) {
            ps.setInt(1, Integer.valueOf(id));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                post.setName(rs.getString(2));
                post.setText(rs.getString(3));
                post.setLink(rs.getString(4));
                post.setCreated(getDateSqlFromDate(rs.getDate(5)));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(post);
        return post;
    }
}
