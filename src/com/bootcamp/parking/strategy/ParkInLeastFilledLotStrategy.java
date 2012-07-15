package com.bootcamp.parking.strategy;

import com.bootcamp.parking.ParkingLot;
import com.bootcamp.parking.ParkingLotSortByFreeSpace;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkInLeastFilledLotStrategy implements LotSelectionStrategy {

    @Override
    public ParkingLot lotToPark(List<ParkingLot> availableParkingLots) {
        List<ParkingLot> parkingLots = new ArrayList<ParkingLot>(availableParkingLots);
        Collections.sort(parkingLots, new ParkingLotSortByFreeSpace());
        return parkingLots.get(0);
    }
}
