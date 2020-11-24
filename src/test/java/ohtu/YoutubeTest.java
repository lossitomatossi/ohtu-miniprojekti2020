/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ohtu;

import java.sql.Date;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author julinden
 */
public class YoutubeTest {
    Youtube youtube;
    

    @Before
    public void setUp() {
        youtube = new Youtube("www.com", "title", "description");
        
    }

    @Test
    public void constructorCreatesYoutube() {
        assertEquals("www.com", youtube.getUrl());
    }
    
    @Test
    public void dateIsCorrect() {
        assertEquals(new Date(System.currentTimeMillis()), youtube.getDate());
    }
}
