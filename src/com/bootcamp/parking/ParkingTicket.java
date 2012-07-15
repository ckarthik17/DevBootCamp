package com.bootcamp.parking;

/**
 * Issued once a Car has been parked in a lot
 */
public class ParkingTicket {
    private final ParkingLot parkingLot;

    public ParkingTicket(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }
}
