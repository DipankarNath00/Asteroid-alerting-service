package com.dipankar.AsteroidAlert.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloseApproachData {
    @JsonProperty("close_approach_date")
    private LocalDate closeApproachDate;
    @JsonProperty("miss_distance")
    private MissDistance missDistance;
}
