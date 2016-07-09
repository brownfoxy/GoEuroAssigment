package com.goeuro.main;

import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.entity.City;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by phanindra on 7/9/16.
 */
public class Application {

    private static final String GET_CITY_URL = "http://api.goeuro.com/api/v2/position/suggest/en/";
    private static final ClientConfig clientConfig = new DefaultClientConfig();

    public static void main(String[] args) {

        if(!validateInput(args)){
            System.err.println("Input string is NOT valid! Please enter a valid CITY_NAME");
            System.exit(1);
        }

        String inputCity = args[0];

        //Enable auto conversion from JSON to Java object
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        Client client = Client.create(clientConfig);
        WebResource webResource = client.resource(GET_CITY_URL).path(inputCity.trim());
        ClientResponse response = webResource.get(ClientResponse.class);

        List<City> cities = webResource.get(new GenericType<List<City>>(){});

        if (response.getStatus() != 200) {
            System.err.println("Invalid HTTP status:"+response.getStatus());
            System.exit(1);
        }

        exportDataIntoCSV(cities);
    }

    private static void exportDataIntoCSV(List<City> cities) {
        CSVWriter csvWriter=null;
        try {
            csvWriter = createCSVInCurrentDirectory();
        } catch (IOException e) {
            System.out.println("Error creating CSV file!");
            System.exit(1);
        }

        String[] header = {"_id","name","type","latitude","longitude"};
        csvWriter.writeNext(header);

        long rowsBuffered =0L;
        for(City city:cities){
            csvWriter.writeNext(prepareRow(city));
            rowsBuffered++;
            long FLUSH_THRESHOLD = 1000L;
            if(rowsBuffered >= FLUSH_THRESHOLD){
                try {
                    csvWriter.flush();
                } catch (IOException e) {
                    System.out.println("Error flushing data to CSV file!");
                }
            }
        }
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            System.out.println("Error flushing data to CSV file!");
            System.exit(1);
        }
    }

     static boolean validateInput(String[] args) {
        if(args.length==0 || args[0].length()==0){
            return false;
        }
        return true;
    }

    private static CSVWriter createCSVInCurrentDirectory() throws IOException {
        String workingDir = System.getProperty("user.dir");
        System.out.println("Final data will be stored under : " + workingDir);
        return new CSVWriter(new FileWriter(new File(workingDir,"Cities.csv")));
    }

    static String[] prepareRow(City city) {
        String[] row = { String.valueOf(city.get_id()),city.getName(),city.getType(),
                            String.valueOf(city.getGeo_position().getLatitude()),
                            String.valueOf(city.getGeo_position().getLongitude()) };
        return row;
    }
}
