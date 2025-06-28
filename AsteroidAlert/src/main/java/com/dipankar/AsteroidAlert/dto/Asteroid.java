package com.dipankar.AsteroidAlert.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Asteroid {
    private  String id;
    private String name;
    @JsonProperty("estimated_diameter")
    private EstimatedDiameter estimatedDiameter;

    @JsonProperty("is_potentially_hazardous_asteroid")
    private boolean is_potentially_hazardous;
    @JsonProperty("close_approach_data")
    private List<CloseApproachData> closeApproachData;
}
