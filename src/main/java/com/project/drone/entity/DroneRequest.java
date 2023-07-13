package com.project.drone.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DroneRequest {
    private Long serial_no;
    private String Model;
    private Integer weight_limit;
    private Integer battery;
    private DroneState state;
}
