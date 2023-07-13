package com.project.drone;

import com.project.drone.entity.Drone;
import com.project.drone.entity.DroneRequest;
import com.project.drone.entity.Medication;
import com.project.drone.entity.MedicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("drone")
public class Controller {
    private final DroneService droneService;
    @GetMapping
    public ResponseEntity<Object> getDrones(){
        return ResponseEntity.ok(droneService.getDrones());
    }
    @PostMapping
    public ResponseEntity<Drone> addDrone(@RequestBody DroneRequest request){
        return ResponseEntity.ok(droneService.addDrones(request));
    }
    @GetMapping("/medications/{code}")
    public ResponseEntity<MedicationRequest> getMedication(@PathVariable String code){
        return ResponseEntity.ok(droneService.getMedication(code));
    }
    @GetMapping("/medications")
    public ResponseEntity<List<MedicationRequest>> getMedications(){
        return ResponseEntity.ok(droneService.getMedications());
    }
    @PostMapping("/medications")
    public ResponseEntity<Medication> addMedications(@RequestBody MedicationRequest request){
        return ResponseEntity.ok(droneService.addMedication(request));
    }
    @PostMapping("/load/{code}")
    public ResponseEntity<Object> loadDrone(@RequestBody Long id, @PathVariable String code){
        return ResponseEntity.ok(droneService.loadDrone(code,id));
    }
    @PostMapping("/getdrone/{code}")
    public ResponseEntity<Object> getDroneFromMedication(@PathVariable String code){
        return ResponseEntity.ok(droneService.getDroneFromMedication(code));
    }
    @GetMapping("/freedrones")
    public ResponseEntity<Object> getAvailableDrones(){
        return ResponseEntity.ok(droneService.getAvailableDrones());
    }
    @GetMapping("/getbattery/{id}")
    public ResponseEntity<Object> getDroneBattery(@PathVariable Long id){
        return ResponseEntity.ok(droneService.getDroneBattery(id));
    }
    @GetMapping("/charge")
    public ResponseEntity<String> chargeDrones(){
        droneService.chargeDrones();
        return ResponseEntity.ok("Drones charged to full");
    }
}
