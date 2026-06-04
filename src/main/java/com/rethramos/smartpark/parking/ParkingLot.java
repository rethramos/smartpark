package com.rethramos.smartpark.parking;

import com.rethramos.smartpark.foundation.Address;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(indexes = @Index(name = "lot_id_uniq", columnList = "lot_id", unique = true))
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 50)
    private String lotId;
    @OneToOne(optional = false)
    Address location;
    private Integer capacity;
    private Integer occupiedSpaces;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLotId() {
        return lotId;
    }

    public void setLotId(String lotId) {
        this.lotId = lotId;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getOccupiedSpaces() {
        return occupiedSpaces;
    }

    public void setOccupiedSpaces(Integer occupiedSpaces) {
        this.occupiedSpaces = occupiedSpaces;
    }

    public Address getLocation() {
        return location;
    }

    public void setLocation(Address location) {
        this.location = location;
    }

}
