package com.github.sigismondi.marco.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Payload {

    @Column(name = "acc_x")
    public Double accX;
    @Column(name = "acc_y")
    public Double accY;
    @Column(name = "acc_z")
    public Double accZ;
    
    @Column(name = "speed_kmh")
    public Double speedKmh;

    @Column(name = "acc_unit")
    public String accUnit;
    
    @Column(name = "engine_rpm")
    public Integer engineRpm;
    
    @Column(name = "fuel_level_percent")
    public Integer fuelLevelPercent;
    
    public String status;


    
}
