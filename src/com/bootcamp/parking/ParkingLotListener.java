package com.bootcamp.parking;

/*
** Listener to notify various parking lot events
*/
public interface ParkingLotListener {
    public void unParked(ParkingLot parkingLot);

    public void full(ParkingLot parkingLot);
    public void eightyPercentFull(ParkingLot parkingLot);
    public void lessThanEightyPercentFull(ParkingLot parkingLot);
}
