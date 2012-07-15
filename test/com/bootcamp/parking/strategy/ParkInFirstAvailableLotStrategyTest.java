package com.bootcamp.parking.strategy;

import com.bootcamp.parking.Car;
import com.bootcamp.parking.ParkingLot;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParkInFirstAvailableLotStrategyTest {
    @Test
    public void shouldReturnTheFirstSlot() {
        LotSelectionStrategy lotSelectionStrategy = new ParkInFirstAvailableLotStrategy();

        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(3);

        parkingLot1.park(new Car() {});
        parkingLot2.park(new Car() {});

        List<ParkingLot> parkingLots = Arrays.asList(parkingLot1, parkingLot2);

        ParkingLot parkingLot = lotSelectionStrategy.lotToPark(parkingLots);
        assertEquals(parkingLot1, parkingLot);
    }
}
