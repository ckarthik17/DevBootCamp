package com.bootcamp.parking.strategy;

import com.bootcamp.parking.Car;
import com.bootcamp.parking.ParkingLot;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ParkInLeastFilledLotStrategyTest {
    @Test
    public void shouldReturnTheLeastFilledSlot() {
        LotSelectionStrategy lotSelectionStrategy = new ParkInLeastFilledLotStrategy();

        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(3);

        parkingLot1.park(new Car() {});
        parkingLot2.park(new Car() {});

        ParkingLot parkingLot = lotSelectionStrategy.lotToPark(Arrays.asList(parkingLot1, parkingLot2));
        assertEquals(parkingLot2, parkingLot);
    }
}
