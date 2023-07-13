package com.project.drone;

import com.project.drone.entity.*;
import com.project.drone.repository.DroneRepository;
import com.project.drone.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneService {
    private final DroneRepository droneRepository;
    private final MedicationRepository medicationRepository;
    @Scheduled(fixedDelay = 20000)
    public void battery(){
        var drones = droneRepository.findAllByState(DroneState.IDLE);
        drones.forEach(drone -> {
            drone.setBattery(drone.getBattery() - 1);
        });
        droneRepository.saveAll(drones);
    }
    @Scheduled(fixedDelay = 5000)
    public void batteryLoaded(){
        var drones = droneRepository.findAllByState(DroneState.LOADED);
        drones.forEach(drone -> {
            drone.setBattery(drone.getBattery() - 1);
        });
        droneRepository.saveAll(drones);
    }
    public void chargeDrones(){
        var drones = droneRepository.findAll();
        drones.forEach(drone -> {
            drone.setBattery(100);
        });
        droneRepository.saveAll(drones);
    }
    public DroneRequest mapDroneToDTO(Drone drone){
        if (drone != null) {
            return DroneRequest.builder()
                    .serial_no(drone.getSerial_no())
                    .Model(drone.getModel())
                    .weight_limit(drone.getWeight_limit())
                    .battery(drone.getBattery())
                    .state(drone.getState())
                    .build();
        }
        else
            return null;
    }
    public MedicationRequest mapMedicationToDTO(Medication medication){
        if (medication != null) {
            return  MedicationRequest.builder()
                    .code(medication.getCode())
                    .name(medication.getName())
                    .weight(medication.getWeight())
                    .image(medication.getImage())
                    .drone(mapDroneToDTO(medication.getDrone()))
                    .build();
        }
        else
            return null;
    }
    public List<DroneRequest> getDrones() {
        var drones  = droneRepository.findAll();
        List<DroneRequest> droneRequests = new ArrayList<>();
        drones.forEach(drone -> {
            var droneRequest  =  mapDroneToDTO(drone);
            droneRequests.add(droneRequest);
        });
        return droneRequests;
    }

    public Drone addDrones(DroneRequest request) {
        var drone = Drone.builder()
                .serial_no(null)
                .model(request.getModel())
                .weight_limit(request.getWeight_limit())
                .battery(request.getBattery())
                .state(DroneState.IDLE)
                .build();
        return droneRepository.save(drone);
    }

    public List<MedicationRequest> getMedications() {
        var loads  = medicationRepository.findAll();
        List<MedicationRequest> medicationRequests = new ArrayList<>();
        loads.forEach(load -> {
            var medicationRequest  =  mapMedicationToDTO(load);
            medicationRequests.add(medicationRequest);
        });
        return medicationRequests;
    }
    public MedicationRequest getMedication(String code) {
        var load  = medicationRepository.getReferenceById(code);
        return mapMedicationToDTO(load);
    }
    public Medication addMedication(MedicationRequest request) {
        var load = Medication.builder()
                .code(request.getCode())
                .name(request.getName())
                .weight(request.getWeight())
                .image(request.getImage())
                .drone(null)
                .build();
        return medicationRepository.save(load);
    }
    public Object loadDrone(String code, Long id){
        var drone = droneRepository.getReferenceById(id);
        if (drone.getBattery() > 25) {
            drone.setState(DroneState.LOADING);
            var load = medicationRepository.getReferenceById(code);
            if (load.getDrone() == null && drone.getMedication() == null ) {
                if (drone.getWeight_limit() > load.getWeight()) {
                    load.setDrone(drone);
                    drone.setState(DroneState.LOADED);
                    return mapMedicationToDTO(medicationRepository.save(load));
                } else
                    return "Weight limit exceeded";
            } else
                return "Drone already loaded";
        }
        else
            return "Drone battery too low";
    }
    public Object getDroneFromMedication(String code){
        if (medicationRepository.existsById(code)) {
            var load = medicationRepository.getReferenceById(code);
            if (load.getDrone() != null) {
                Drone drone = load.getDrone();
                return mapDroneToDTO(drone);
            }
            else
                return "No drone found";
        }
        else
            return "No medication found";
    }
    public Object getAvailableDrones(){
        return droneRepository.findAllByState(DroneState.IDLE);
    }
    public Object getDroneBattery(Long id){
        var drone = droneRepository.getReferenceById(id);
        return "The drone battery is "+drone.getBattery()+"%";
    }

}
