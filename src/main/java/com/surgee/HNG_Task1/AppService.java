package com.surgee.HNG_Task1;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.surgee.HNG_Task1.Data.HttpResponseData;
import com.surgee.HNG_Task1.Data.IpLocatorResponse;
import com.surgee.HNG_Task1.response.HttpResponse;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.github.cdimascio.dotenv.Dotenv;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class AppService {
    private final Dotenv dotenv;

    public ResponseEntity<?> getUserDetails(String visitor_name, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        try {
            String ipAddress = request.getRemoteAddr();

            if(ipAddress == null) {
                throw new Exception("Something went wrong.");
            }
        
            IpLocatorResponse ipAddressResponse = WebClient.create().get()
                                    .uri(dotenv.get("ipAddressBaseUrl") + ipAddress + "?token="+dotenv.get("ipAddressApiToken"))
                                    .retrieve()
                                    .bodyToMono(IpLocatorResponse.class)
                                    .block();
            
            if(ipAddressResponse == null) {
                throw new Exception("Something went wrong.");
            }

            HttpResponseData weatherApiResponse = WebClient.create().get()
                                    .uri("https://api.tomorrow.io/v4/weather/forecast?location=40,40&apikey=8aIvSGpdvuOF1hFBuiHz4Ca1HZtrJ8hG")
                                    .retrieve()
                                    .bodyToMono(HttpResponseData.class)
                                    .block();    
            if(weatherApiResponse == null) {
                throw new Exception("Something went wrong.");
            }

            String city = ipAddressResponse.getCity();
            city = city == null ? "Island": city;
            int temperature = weatherApiResponse.getTimelines()
                                                .get().getMinutely()
                                                .get(0).getValues()
                                                .getTemperature();

            String message = "Hello, " + visitor_name + "!, the temperature is " + temperature + " degrees Celcius in " + city;
            
            HttpResponse response = HttpResponse.builder()
                                    .client_ip(ipAddress)
                                    .Location(city)
                                    .greeting(message)
                                    .build();
            status = HttpStatus.OK;

            return new ResponseEntity<HttpResponse>(response, status);

            
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), status);
        }
    } 
    
}
