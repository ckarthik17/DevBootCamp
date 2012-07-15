package com.bootcamp.parking.strategy;

import com.bootcamp.parking.ParkingLot;

import java.util.List;

public class ParkInFirstAvailableLotStrategy implements LotSelectionStrategy {
    @Override
    public ParkingLot lotToPark(List<ParkingLot> availableParkingLots) {
        return availableParkingLots.get(0);
    }
}
