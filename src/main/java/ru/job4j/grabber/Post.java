package ru.job4j.grabber;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

public class Post {

    private String name = "";
    private String text = "";
    private String link = "";
    private Date created = new Date();

    public Post(String name, String text, String link, Date created) throws IOException, ParseException {
        this.name = name;
        this.text = text;
        this.link = link;
        this.created = created;
    }

    public Post() {
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getLink() {
        return link;
    }

    public Date getCreated() {
        return  created;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "Post{" +
                "name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", link='" + link + '\'' +
                ", created=" + created +
                '}';
    }
}
