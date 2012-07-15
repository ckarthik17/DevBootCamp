package com.bootcamp.parking.strategy;

import com.bootcamp.parking.ParkingLot;

import java.util.List;

public interface LotSelectionStrategy {
    public ParkingLot lotToPark(List<ParkingLot> availableParkingLots);
}
