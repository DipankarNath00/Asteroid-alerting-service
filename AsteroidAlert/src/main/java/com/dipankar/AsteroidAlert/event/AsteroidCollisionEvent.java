package com.dipankar.AsteroidAlert.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsteroidCollisionEvent {
        private String asteroidName;
        private String closeApproachDate;
        private String missDistanceKilometers;
        private double estimatedDiameterAvgMeters;

}
