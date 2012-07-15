package com.bootcamp.parking;

import java.util.Comparator;

public class ParkingLotSortByFreeSpace implements Comparator<ParkingLot> {
    @Override
    public int compare(ParkingLot lot1, ParkingLot lot2) {
        return lot2.freeSpace() - lot1.freeSpace();
    }
}
