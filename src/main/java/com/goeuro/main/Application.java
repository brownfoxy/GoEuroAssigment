package com.goeuro.main;

import au.com.bytecode.opencsv.CSVWriter;
import com.goeuro.client.CSVWriterProvider;
import com.goeuro.client.CSVWriterProviderImpl;
import com.goeuro.client.ClientProvider;
import com.goeuro.client.IClientProvider;
import com.goeuro.config.Config;
import com.goeuro.entity.City;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by phanindra on 7/9/16.
 */
public class Application {

    private final IClientProvider clientProvider  = new ClientProvider();
    private final CSVWriterProvider csvWriterProvider = new CSVWriterProviderImpl();

    public static void main(String[] args) {

        Application application = new Application();
        CSVWriter csvWriter = null;

        if(!application.validateInput(args)){
            System.err.println("Input string is NOT valid! Please enter a valid CITY_NAME");
            System.exit(1);
        }

        String inputCity = args[0];

        WebResource webResource = application.getWebResource(inputCity);
        ClientResponse clientResponse = webResource.get(ClientResponse.class);

        if (clientResponse.getStatus() != 200) {
            System.err.println("Invalid HTTP status:"+clientResponse.getStatus());
            System.exit(1);
        }
        List<City> cities = application.getAllObjects(webResource, City.class);

        String workingDir = System.getProperty("user.dir");
        System.out.println("Final data(Cities.csv) will be stored under : " + workingDir);

        try {
            csvWriter = application.getCsvWriter(new File(workingDir));
        } catch (IOException e) {
            System.err.println("Error getting Csvwriter for the path: "+workingDir);
            System.exit(1);
        }

        try {
            application.exportDataIntoCSV(csvWriter, cities);
        } catch (IOException e) {
            System.out.println("Error flushing data to CSV file or Closing CSV!");
        }
    }

    private CSVWriter getCsvWriter(File file) throws IOException {
        return csvWriterProvider.getCsvWriter(file);
    }


    public <T> List<T> getAllObjects(WebResource webResource, final Class<T> clazz) {

        ParameterizedType parameterizeGenericType = new ParameterizedType() {
            public Type[] getActualTypeArguments() {
                return new Type[] { clazz };
            }

            public Type getRawType() {
                return List.class;
            }

            public Type getOwnerType() {
                return List.class;
            }
        };

        GenericType<List<T>> genericType = new GenericType<List<T>>(
                parameterizeGenericType) {
        };

        return webResource.get(genericType);
    }



    private WebResource getWebResource(String input) {
        Client apiClient = clientProvider.getApiClient();
        return apiClient.resource(Config.GET_CITY_URL).path(input.trim());
    }

    public void exportDataIntoCSV(CSVWriter csvWriter, List<City> cities) throws IOException {
        String[] header = {"_id","name","type","latitude","longitude"};
        csvWriter.writeNext(header);
        long rowsBuffered =0L;
        for(City city:cities){
            csvWriter.writeNext(prepareRow(city));
            rowsBuffered++;
            long FLUSH_THRESHOLD = 1000L;
            if(rowsBuffered >= FLUSH_THRESHOLD){
                    csvWriter.flush();
            }
        }
        csvWriter.flush();
        csvWriter.close();
    }

    boolean validateInput(String[] args) {
        return !(args.length == 0 || args[0].length() == 0);
    }

    String[] prepareRow(City city) {
        String[] row = { String.valueOf(city.get_id()),city.getName(),city.getType(),
                            String.valueOf(city.getGeo_position().getLatitude()),
                            String.valueOf(city.getGeo_position().getLongitude()) };
        return row;
    }
}
