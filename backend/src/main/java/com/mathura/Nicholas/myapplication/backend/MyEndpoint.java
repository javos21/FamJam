package com.mathura.Nicholas.myapplication.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import java.io.IOException;

import javax.inject.Named;

/**
 * Created by Nicholas on 5/14/2015.
 */
@Api(name = "myApi", version = "v1", namespace = @ApiNamespace(ownerDomain = "backend.myapplication.Nicholas.mathura.com", ownerName = "backend.myapplication.Nicholas.mathura.com", packagePath = ""))
public class MyEndpoint {
 MessagingEndpoint m= new MessagingEndpoint();
    /**
     * A simple endpoint method that takes a name and says Hi back
     */
    @ApiMethod(name = "sayHi")
    public MyBean sayHi(@Named("name") String name) throws IOException {
        MyBean response = new MyBean();
        response.setData("Requestresponse" + name);
        m.sendMessage(name);
        return response;
    }

}
