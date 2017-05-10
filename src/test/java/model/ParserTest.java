package model;

import model.ircevent.IRCEvent;
import model.ircevent.RAWEvent;
import org.junit.Assert;
import org.junit.Test;

public class ParserTest {
    @Test
    public void parseRawEvent() throws Exception {
        String raw = "Raw string from server";
        IRCEvent ircEvent = Parser.parse(raw);
        Assert.assertTrue(ircEvent instanceof RAWEvent);
    }
}
