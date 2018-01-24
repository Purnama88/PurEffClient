
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.IOException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RestClient {
    
    public static ClientResponse getNonApi(String url){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + url);
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource webResource = client
               .resource(GlobalFields.SERVER + url);
            
            WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);
            
            response = builder
               .get(ClientResponse.class);
            
            return response;
        }
        catch(ClientHandlerException ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    public static ClientResponse get(String url){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + GlobalFields.API + url);
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource webResource = client
               .resource(GlobalFields.SERVER + GlobalFields.API + url);
            
            WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + GlobalFields.TOKEN);
            
            response = builder
               .get(ClientResponse.class);
            
        }
        catch(ClientHandlerException ex){
            ex.printStackTrace();
        }
        
        return response;
    }
    
    public static ClientResponse post(String url, Object object){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + GlobalFields.API + url);
            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(object);
            
            System.out.println(input);
            
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource resource = client.
                    resource(GlobalFields.SERVER + GlobalFields.API + url);
            
            WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + GlobalFields.TOKEN);

            response =  builder.post(ClientResponse.class, input);
            
            
        }
        catch(RuntimeException | IOException e){
            e.printStackTrace();
        }
        
        return response;
    }
    
    public static ClientResponse postNonApi(String url, Object object){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + url);
            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(object);
            
            System.out.println(input);
            
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource resource = client.
                    resource(GlobalFields.SERVER + url);
            
            WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);

            response =  builder.post(ClientResponse.class, input);
            
            
        }
        catch(RuntimeException | IOException e){
            e.printStackTrace();
        }
        
        return response;
    }
    
    public static ClientResponse put(String url, Object object){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + GlobalFields.API + url);
            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(object);
            
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource resource = client.
                    resource(GlobalFields.SERVER + GlobalFields.API + url);
            
            WebResource.Builder builder = resource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + GlobalFields.TOKEN);

            response =  builder.put(ClientResponse.class, input);
            
            
        }
        catch(RuntimeException | IOException e){
            e.printStackTrace();
        }
        
        return response;
    }
    
    public static ClientResponse delete(String url){
        ClientResponse response = null;
        
        try{
            System.out.println(GlobalFields.SERVER + GlobalFields.API + url);
            Client client = Client.create();
            client.setConnectTimeout(GlobalFields.TIMEOUT);
            client.setReadTimeout(GlobalFields.TIMEOUT);
            
            WebResource webResource = client
               .resource(GlobalFields.SERVER + GlobalFields.API + url);
            
            WebResource.Builder builder = webResource.accept(MediaType.APPLICATION_JSON);
            builder.type(MediaType.APPLICATION_JSON);
            builder.header(HttpHeaders.AUTHORIZATION, "Bearer " + GlobalFields.TOKEN);
            
            response = builder
               .delete(ClientResponse.class);
            
        }
        catch(ClientHandlerException ex){
            ex.printStackTrace();
        }
        
        return response;
    }
}
