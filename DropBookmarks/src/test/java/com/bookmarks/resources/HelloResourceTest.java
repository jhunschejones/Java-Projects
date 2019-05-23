package com.bookmarks.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import static org.junit.Assert.*;

public class HelloResourceTest {

    @ClassRule
    public static final ResourceTestRule RULE = ResourceTestRule.builder().addResource(new HelloResource()).build();

    @Test
    //@Ignore
    public void serializesToJSON() throws Exception {
        String expected = "Hello World";
        String actual = RULE.getJerseyTest().target("/hello").request(MediaType.TEXT_PLAIN).get(String.class);
        assertEquals(expected, actual);
    }
}