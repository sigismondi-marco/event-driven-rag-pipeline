package com.github.sigismondi.marco.domain.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Location {

    public String type;
    public Double longitude;
    public Double latitude;
    public Double altitude;

}
