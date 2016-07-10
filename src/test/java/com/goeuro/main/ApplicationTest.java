package com.goeuro.main;

import com.goeuro.entity.City;
import com.goeuro.entity.GeoPosition;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;

/**
 * Created by phanindra on 7/9/16.
 */
public class ApplicationTest {
    private final Application application = new Application();

    @Test
    public void testEmptyInput(){
        String[] input = {""};
        assertFalse(application.validateInput(input));
    }

    @Test
    public void testForgottenInput(){
        String[] input = {};
        assertFalse(application.validateInput(input));
    }
    @Test
    public void testCSVRowPreparedCorrectly(){
        City city = new City();
        city.set_id(1L);
        city.setName("Abu Dhabi");
        city.setType("Capital");
        city.setGeo_position(new GeoPosition(10.12,11.23));

        String[] row = application.prepareRow(city);
        assertEquals(5,row.length);
        assertEquals("1",row[0]);
        assertEquals("Abu Dhabi",row[1]);
        assertEquals("Capital",row[2]);
        assertEquals("10.12",row[3]);
        assertEquals("11.23",row[4]);
    }
}
