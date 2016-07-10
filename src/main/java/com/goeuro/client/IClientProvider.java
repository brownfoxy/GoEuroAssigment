package com.goeuro.client;

import com.sun.jersey.api.client.Client;

/**
 * Created by phanindra on 7/10/16.
 */
public interface IClientProvider {
    Client getApiClient();
}
