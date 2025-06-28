package com.dipankar.AsteroidAlert.Service;

import com.dipankar.AsteroidAlert.dto.Asteroid;
import com.dipankar.AsteroidAlert.dto.NasaNeoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class NasaClient {
    @Value("${nasa.neo.api.url}")
    private String nasaNeoApiUrl;
    @Value("${nasa.api.key}")
    private String apiKey;
    public List<Asteroid> getNeoAsteroids (LocalDate fromDate, LocalDate toDate){
        final RestTemplate restTemplate = new RestTemplate();
        final NasaNeoResponse nasaNeoResponse = restTemplate.getForObject(getUrl(fromDate,toDate),NasaNeoResponse.class);
        List<Asteroid> asteroidList = new ArrayList<>();
        if(nasaNeoResponse!=null){
            asteroidList.addAll(nasaNeoResponse.getNearEarthObjects().values().stream().flatMap(List::stream).toList());

        }
        return asteroidList;
    }
    private String getUrl(LocalDate fromDate,LocalDate toDate){
        String apiUri = UriComponentsBuilder.fromUriString(nasaNeoApiUrl).
                queryParam("start_date",fromDate).
                queryParam("end_date",toDate).
                queryParam("api_key",apiKey).
                toUriString();
        return apiUri;
    }
}
