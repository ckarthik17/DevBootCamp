package com.bootcamp.parking;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ParkingLotSortByFreeSpaceTest {
    @Test
    public void shouldSortTheParkingLotsBasedOnFreeSpace() {
        List<ParkingLot> sortedParkingLots = new ArrayList<ParkingLot>();
        ParkingLot parkingLot1 = new ParkingLot(5);
        ParkingLot parkingLot2 = new ParkingLot(5);
        ParkingLot parkingLot3 = new ParkingLot(5);
        sortedParkingLots.add(parkingLot1);
        sortedParkingLots.add(parkingLot2);
        sortedParkingLots.add(parkingLot3);

        parkingLot1.park(new Car() {});
        parkingLot1.park(new Car() {});

        parkingLot2.park(new Car() {});

        parkingLot3.park(new Car() {});
        parkingLot3.park(new Car() {});
        parkingLot3.park(new Car() {});

        Collections.sort(sortedParkingLots, new ParkingLotSortByFreeSpace());

        assertEquals(parkingLot2,sortedParkingLots.get(0));
        assertEquals(parkingLot1,sortedParkingLots.get(1));
        assertEquals(parkingLot3,sortedParkingLots.get(2));
    }
}
