package com.project.drone.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationRequest {
    private String code;
    private String name;
    private Integer weight;
    private String image;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private DroneRequest drone;

}
