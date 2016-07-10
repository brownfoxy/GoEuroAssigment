package com.goeuro.client;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

/**
 * Created by phanindra on 7/10/16.
 */
public class ClientProvider implements IClientProvider {
    private static final ClientConfig clientConfig = new DefaultClientConfig();

    public Client getApiClient() {
        //Enable auto conversion from JSON to Java object
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        clientConfig.getClasses().add(JacksonJsonProvider.class);
        return Client.create(clientConfig);
    }
}
