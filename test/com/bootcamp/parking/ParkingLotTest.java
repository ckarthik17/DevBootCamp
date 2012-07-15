package com.bootcamp.parking;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ParkingLotTest {

    @Test
    public void shouldParkInFirstAvailableLot() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car = createDummyCar();

        ParkingTicket parkingTicket = parkingLot.park(car);
        assertNotNull(parkingTicket);
    }

    @Test
    public void shouldReturnTheFreeSpaceInALot() {
        ParkingLot parkingLot = new ParkingLot(5);
        parkingLot.park(new Car() {});
        parkingLot.park(new Car() {});

        assertEquals(3, parkingLot.freeSpace());
    }

    @Test(expected = IllegalStateException.class)
    public void shouldNotParkCarIfFull() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car car1 = createDummyCar();
        parkingLot.park(car1);

        Car car2 = createDummyCar();
        parkingLot.park(car2);
    }

    @Test
    public void shouldUnparkCar() {
        ParkingLot parkingLot = new ParkingLot(1);
        Car expectedCar = createDummyCar();
        ParkingTicket parkingTicket = parkingLot.park(expectedCar);

        Car actualCar = parkingLot.unPark(parkingTicket);
        assertEquals(expectedCar, actualCar);
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowAnErrorIfUnParkedCarIsNotFound() {
        ParkingLot parkingLot = new ParkingLot(1);
        parkingLot.unPark(new ParkingTicket(parkingLot));
    }

    @Test
    public void shouldNotifyWhenParkingLotIsFull() {
        ParkingLot parkingLot = new ParkingLot(1);

        ParkingLotListener parkingLotManager = mock(ParkingLotListener.class);
        parkingLot.addListener(parkingLotManager);

        Car expectedCar = new Car() {};
        parkingLot.park(expectedCar);

        verify(parkingLotManager).full(parkingLot);
    }

    @Test
    public void shouldNotifyTheListenersWhenUnParking() {
        ParkingLot parkingLot = new ParkingLot(1);

        ParkingLotListener parkingLotManager = mock(ParkingLotListener.class);
        parkingLot.addListener(parkingLotManager);

        Car expectedCar = new Car() {};
        ParkingTicket parkingTicket = parkingLot.park(expectedCar);
        parkingLot.unPark(parkingTicket);

        verify(parkingLotManager).unParked(parkingLot);
    }

    @Test
    public void shouldNotifyWhenParkingLotIsEightyPercentFull() {
        ParkingLot parkingLot = new ParkingLot(5);

        ParkingLotListener mockParkingLotListener = mock(ParkingLotListener.class);
        parkingLot.addListener(mockParkingLotListener);

        parkingLot.park(new Car() {});
        parkingLot.park(new Car() {});
        parkingLot.park(new Car() {});
        parkingLot.park(new Car() {});

        verify(mockParkingLotListener).eightyPercentFull(parkingLot);
    }

    private Car createDummyCar() {
        return new Car() { };
    }

}
