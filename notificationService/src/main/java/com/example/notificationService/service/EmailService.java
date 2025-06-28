package com.example.notificationService.service;

import com.example.notificationService.Repository.NotificationRepository;
import com.example.notificationService.Repository.UserRepository;
import com.example.notificationService.entity.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class EmailService {
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;;
    @Value("${email.service.from.email}")
    private String fromEmail;
    @Autowired
    public EmailService(NotificationRepository notificationRepository,UserRepository userRepository,JavaMailSender mailSender){
        this.notificationRepository=notificationRepository;
        this.userRepository=userRepository;
        this.mailSender=mailSender;
    }

    @Async
    public void sendAsteroidAlertEmail(){
        final String email = createEmailText();
        if (email==null){
            log.info("No asteroids alert to send {}", LocalDateTime.now());
            return;
        }
        final List<String> toEmails = userRepository.findAllEmailsAndNotificationEnabled();
        if(toEmails.isEmpty()){
            log.info("No users to send email to");
            return;
        }
        toEmails.forEach(toEmail->sendEmail(toEmail,email));
        log.info("Number of people send email to {}",toEmails.size());

    }

    private void sendEmail(String toEmail, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setFrom(fromEmail);
        message.setSubject("NASA collision event");
        message.setText(email);
        mailSender.send(message);
    }

    private String createEmailText() {
        List<Notification> notificationList = notificationRepository.findByEmailSent(false);
        if (notificationList.isEmpty()){
            return null;
        }
        StringBuilder email = new StringBuilder();

        // Header
        email.append("Asteroid Alert!\n");
        email.append("**********************************************************************************\n\n");

        // Asteroid Details
        notificationList.forEach(notification -> {
            email.append("A potentially hazardous asteroid has been detected!\n\n");
            email.append("Asteroid Name: ").append(notification.getAsteroidName()).append("\n");
            email.append("Close Approach Date: ").append(notification.getCloseApproachDate()).append("\n");
            email.append("Miss Distance (Kilometers): ").append(notification.getMissDistanceKilometers()).append("\n");
            email.append(String.format("Estimated Diameter (Avg Meters): %.2f\n\n", notification.getEstimatedDiameterAvgMeters()));
            notification.setEmailSent(true);
            notificationRepository.save(notification);

        });
        // Additional Information/Call to Action (Optional)
        email.append("Please stay informed and follow updates from official space agencies like NASA.\n");
        email.append("For more detailed information, please visit the NASA NeoWs website.\n\n");

        // Footer
        email.append("Stay safe!\n");
        email.append("The Asteroid Alert Team\n");
        email.append("**********************************************************************************\n");

        return email.toString();

    }
}
