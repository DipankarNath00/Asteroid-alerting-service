package com.example.notificationService.service;

import com.example.asteroidalerting.event.AsteroidCollisionEvent;
import com.example.notificationService.Repository.NotificationRepository;
import com.example.notificationService.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationService {
    final NotificationRepository notificationRepository;
    final EmailService emailService;
    @Autowired
    public NotificationService(NotificationRepository notificationRepository,EmailService emailService){
        this.notificationRepository=notificationRepository;
        this.emailService= emailService;
    }
    @KafkaListener(topics = "asteroid-alert",groupId ="notification-service" )
    public void  alertEvent(AsteroidCollisionEvent event){
        log.info("Received ordered event :{}",event);
        //create entity for notification
        final Notification notification = Notification.builder()
                .asteroidName(event.getAsteroidName()).closeApproachDate(event.getCloseApproachDate())
                .estimatedDiameterAvgMeters(event.getEstimatedDiameterAvgMeters())
                .missDistanceKilometers(event.getMissDistanceKilometers())
                .emailSent(false).build();
        //save notification
        final Notification savedNotification=notificationRepository.saveAndFlush(notification);
        log.info("Saved notification {}",savedNotification);


    }
    @Scheduled(fixedRate = 10000)
    public void sendAlertEmail(){
        log.info("Triggering scheduled job  to send email");
        emailService.sendAsteroidAlertEmail();
    }
}
