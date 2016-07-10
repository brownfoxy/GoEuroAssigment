package com.goeuro.integration;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.entity.City;
import com.goeuro.entity.GeoPosition;
import com.goeuro.main.Application;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by phanindra on 7/10/16.
 */
public class IntegrationTest {

    private final Application application = new Application();
    private static CSVWriter csvWriter;
    private static File tempFile;

    @BeforeClass
    public static void setUp() throws IOException {
        tempFile = File.createTempFile("test","csv");
        csvWriter = new CSVWriter(new FileWriter(tempFile));
        tempFile.deleteOnExit();
    }

    @Test
    public void testExportContainsAllCities() throws IOException {
        City city1 = new City();
        city1.set_id(1L);
        city1.setName("Abu Dhabi");
        city1.setType("Capital");
        city1.setGeo_position(new GeoPosition(10.12,11.23));

        City city2 = new City();
        city2.set_id(2L);
        city2.setName("Dubai");
        city2.setType("Touristy");
        city2.setGeo_position(new GeoPosition(10.11,11.22));

        List<City> cityList = new ArrayList<City>();
        cityList.add(city1);
        cityList.add(city2);

        application.exportDataIntoCSV(csvWriter,cityList);

        CSVReader csvReader = new CSVReader(new FileReader(tempFile));
        List<String[]> lines = csvReader.readAll();

        //Set Expectation
        String[] expectedCity1 = {"1","Abu Dhabi","Capital","10.12","11.23"};
        String[] expectedCity2 = {"2","Dubai","Touristy","10.11","11.22"};

        //Verify
        assertEquals(3,lines.size());
        assertArrayEquals("First city NOT exported correctly!", expectedCity1, lines.get(1));
        assertArrayEquals("Second city NOT exported correctly!", expectedCity2, lines.get(2));

    }

}
