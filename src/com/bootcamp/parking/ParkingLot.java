package com.bootcamp.parking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Parks cars and issues tickets
 */
public class ParkingLot {

    private final Map<ParkingTicket, Car> slots = new HashMap<ParkingTicket, Car>();
    private List<ParkingLotListener> listeners = new ArrayList<ParkingLotListener>();

    private final int size;

    public ParkingLot(int size) {
        this.size = size;
    }

    public ParkingTicket park(Car car) {
        if (!isFree()) {
            throw new IllegalStateException("Parking lot Full");
        }

        ParkingTicket parkingTicket = new ParkingTicket(this);
        slots.put(parkingTicket, car);
        triggerParkingListeners();
        return parkingTicket;
    }

    public Car unPark(ParkingTicket parkingTicket) {
        Car unParkedCar = slots.remove(parkingTicket);
        if(unParkedCar == null) {
            throw new IllegalStateException("Car not found for the ticket");
        }

        triggerUnParkListeners();
        return unParkedCar;
    }

    private void triggerParkingListeners() {
        for (ParkingLotListener parkingLotListener : listeners) {
            if(!isFree()) {
                parkingLotListener.full(this);
            }
            if(isEightyPercentFull()) {
                parkingLotListener.eightyPercentFull(this);
            }
        }
    }

    private void triggerUnParkListeners() {
        for (ParkingLotListener parkingLotListener : listeners) {
            parkingLotListener.unParked(this);
            if(!isEightyPercentFull()) {
                parkingLotListener.lessThanEightyPercentFull(this);
            }
        }
    }

    private boolean isEightyPercentFull() {
        return (slots.size() >= size *0.8);
    }

    public boolean isFree() {
        return this.slots.size() < this.size;
    }

    public int freeSpace() {
        return size - slots.size();
    }

    public void addListener(ParkingLotListener parkingLotListener) {
        listeners.add(parkingLotListener);
    }
}
