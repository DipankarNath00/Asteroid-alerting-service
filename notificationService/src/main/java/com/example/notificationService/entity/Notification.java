package com.example.notificationService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asteroidName;
    private String closeApproachDate;
    private String missDistanceKilometers;
    private double estimatedDiameterAvgMeters;
    private boolean emailSent;
}
