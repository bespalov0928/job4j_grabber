package ru.job4j.grabber;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TestTest {

    @Test
    public void testBol() {
    Testing test = new Testing();
        Boolean rsl = ((Testing) test).testBol();
        assertThat(rsl, is(true));
    }
}