package com.dipankar.AsteroidAlert.Service;

import com.dipankar.AsteroidAlert.dto.Asteroid;
import com.dipankar.AsteroidAlert.event.AsteroidCollisionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class AsteroidAlertingService {
    private final NasaClient nasaClient;
    private final KafkaTemplate<String,AsteroidCollisionEvent> kafkaTemplate;
    @Autowired
    public AsteroidAlertingService(NasaClient nasaClient,KafkaTemplate kafkaTemplate){
        this.nasaClient=nasaClient;
        this.kafkaTemplate=kafkaTemplate;
    }


    public void alert(){
         log.info("Alerting service called");
         //From and to date
        final LocalDate fromDate = LocalDate.now();
        final LocalDate toDate = LocalDate.now().plusDays(7);

        //Call nasa api to get the data
        log.info("Getting asteroid data from date:{} to:{}",fromDate,toDate);
        final List<Asteroid> asteroidList= nasaClient.getNeoAsteroids(fromDate,toDate);
        log.info("Retrieved asteroid list of size: {}",asteroidList.size());
        //if there are any hazardous asteroid send an alert
        final List<Asteroid> dangerousAsteroid = asteroidList.stream().filter(Asteroid::is_potentially_hazardous).toList();
        log.info("Found hazardous asteroid :{}",dangerousAsteroid);
        //create an alert and put it on kafka
        final List<AsteroidCollisionEvent> asteroidCollisionEvents =createAsteroidCollisionEvent(dangerousAsteroid);
        log.info("Sending {} asteroid alerts to kafka",asteroidCollisionEvents);
        asteroidCollisionEvents.forEach(asteroidCollisionEvent -> {
            kafkaTemplate.send("asteroid-alert",asteroidCollisionEvent);
            log.info("Asteroid alert  sent to kafka topic:{}",asteroidCollisionEvent);
        });

    }

    private List<AsteroidCollisionEvent> createAsteroidCollisionEvent(List<Asteroid> dangerousAsteroid) {
        return dangerousAsteroid.stream().map(
                asteroid -> AsteroidCollisionEvent.builder().asteroidName(
                        asteroid.getName()
                ).closeApproachDate(asteroid.getCloseApproachData().getFirst().getCloseApproachDate().toString()).
                        missDistanceKilometers(asteroid.getCloseApproachData().getFirst().getMissDistance().getKilometers()).
                        estimatedDiameterAvgMeters((double) (asteroid.getEstimatedDiameter().getMeter().getMaxDiameter() + asteroid.getEstimatedDiameter().getMeter().getMinDiameter()) /2).build()
        ).toList();
    }
}
