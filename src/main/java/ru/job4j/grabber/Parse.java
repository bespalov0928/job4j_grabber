package ru.job4j.grabber;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface Parse {
    List<Post> list(String link) throws ParseException, IOException;

    Post detail(String link) throws IOException, ParseException;
}
