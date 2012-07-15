package com.bootcamp.parking;

import com.bootcamp.parking.strategy.LotSelectionStrategy;
import com.bootcamp.parking.strategy.ParkInLeastFilledLotStrategy;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ParkingAssistantTest {
    private Car expectedCar;
    private LotSelectionStrategy lotSelectionStrategy;
    private List<ParkingLot> parkingLots;

    @Before
    public void setUp() {
        this.expectedCar = new Car() { };
        this.lotSelectionStrategy = new ParkInLeastFilledLotStrategy();
        this.parkingLots = Arrays.asList(new ParkingLot(5));
    }

    @Test
    public void shouldParkTheCarInParkingLot() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(parkingLots, lotSelectionStrategy);

        ParkingTicket parkingTicket = parkingAssistant.park(expectedCar);

        assertNotNull(parkingTicket);
    }

    @Test
    public void shouldUnparkCarFromParkingLot() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(parkingLots, lotSelectionStrategy);

        ParkingTicket parkingTicket = parkingAssistant.park(expectedCar);
        Car actualCar = parkingAssistant.unPark(parkingTicket);

        assertEquals(expectedCar, actualCar);
    }

    @Test
    public void shouldParkInTheNextAvailableParkingLotIfFirstParkingLotIsFull() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(new ParkingLot(1), new ParkingLot(2)), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        ParkingTicket parkingTicket = parkingAssistant.park(expectedCar);

        assertNotNull(parkingTicket);
    }

    @Test
    public void shouldUnParkTheCarCorrectlyIfThereIsMoreThanOneParkingLot() {
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(new ParkingLot(1), new ParkingLot(2)), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        ParkingTicket parkingTicket = parkingAssistant.park(expectedCar);

        Car actualCar = parkingAssistant.unPark(parkingTicket);
        assertEquals(expectedCar, actualCar);
    }

    @Test
    public void shouldParkTheCarInTheLeastFilledParkingLot() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(3);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot1, parkingLot2), lotSelectionStrategy);

        parkingLot1.park(new Car() {});
        parkingAssistant.park(new Car() {});

        assertEquals(1, parkingLot1.freeSpace());
        assertEquals(2, parkingLot2.freeSpace());
    }

    @Test
    public void shouldBeNotifiedWhenParkingLotIsEightyPercentFull() {
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});

        assertTrue(parkingAssistant.isAlmostFull(parkingLot));
    }

    @Test
    public void shouldBeNotifiedWhenParkingGoesLessThanEightyPercentFull() {
        ParkingLot parkingLot = new ParkingLot(5);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});
        ParkingTicket parkingTicket = parkingAssistant.park(new Car() {});

        parkingAssistant.unPark(parkingTicket);

        assertFalse(parkingAssistant.isAlmostFull(parkingLot));
    }

    @Test
    public void shouldBeNotifiedWhenAParkingLotIsFull() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        parkingAssistant.park(new Car() {});

        assertFalse(parkingAssistant.isAvailable(parkingLot));
    }

    @Test
    public void shouldAddParkingLotBackToAvailableWhenACarIsUnParkedFromAFullLot() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot), lotSelectionStrategy);

        parkingAssistant.park(new Car() {});
        ParkingTicket parkingTicket = parkingAssistant.park(new Car() {});

        assertFalse(parkingAssistant.isAvailable(parkingLot));
        parkingAssistant.unPark(parkingTicket);
        assertTrue(parkingAssistant.isAvailable(parkingLot));
    }

    @Test
    public void shouldReturnTheAvailableParkingLots() {
        ParkingLot parkingLot1 = new ParkingLot(2);
        ParkingLot parkingLot2 = new ParkingLot(3);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot1, parkingLot2), lotSelectionStrategy);

        assertEquals(2, parkingAssistant.availableParkingLots());

        parkingLot1.park(new Car() {
        });
        parkingLot1.park(new Car() {
        });

        assertEquals(1, parkingAssistant.availableParkingLots());
    }

    @Test
    public void shouldAddParkingLotBackToAvailableOnlyOnce() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot), lotSelectionStrategy);

        assertEquals(1, parkingAssistant.availableParkingLots());

        ParkingTicket parkingTicket1 = parkingAssistant.park(new Car() {});
        ParkingTicket parkingTicket2 = parkingAssistant.park(new Car() {});

        assertEquals(0, parkingAssistant.availableParkingLots());
        parkingAssistant.unPark(parkingTicket1);
        assertEquals(1, parkingAssistant.availableParkingLots());
        parkingAssistant.unPark(parkingTicket2);
        assertEquals(1, parkingAssistant.availableParkingLots());
    }

    @Test
    public void shouldCreateAParkingCoordinator() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot));

        ParkingAssistant parkingAssistant1 = new ParkingAssistant(Arrays.asList(parkingLot));
        ParkingAssistant parkingAssistant2 = new ParkingAssistant(Arrays.asList(parkingLot));

        assertFalse(parkingAssistant.isCoordinator());
        parkingAssistant.coordinate(Arrays.asList(parkingAssistant1, parkingAssistant2));
        assertTrue(parkingAssistant.isCoordinator());
    }

    @Test
    public void shouldRevokeTheCoordinatorRoleForAnAssistant() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingAssistant = new ParkingAssistant(Arrays.asList(parkingLot));

        ParkingAssistant parkingAssistant1 = new ParkingAssistant(Arrays.asList(parkingLot));
        ParkingAssistant parkingAssistant2 = new ParkingAssistant(Arrays.asList(parkingLot));

        parkingAssistant.coordinate(Arrays.asList(parkingAssistant1, parkingAssistant2));
        assertTrue(parkingAssistant.isCoordinator());

        parkingAssistant.revokeCoordinatorRole();
        assertFalse(parkingAssistant.isCoordinator());
    }

    @Test
    @Ignore
    public void parkingCoordinatorShouldDelegateTheParkingJobToFirstAvailableAssistant() {
        ParkingAssistant parkingCoordinator = new ParkingAssistant(Arrays.asList(new ParkingLot(2)));
        Car expectedCar = new Car() {};

        ParkingAssistant parkingAssistant1 = new ParkingAssistant(Arrays.asList(new ParkingLot(1)));
        ParkingAssistant parkingAssistant2 = mock(ParkingAssistant.class);
        parkingCoordinator.coordinate(Arrays.asList(parkingAssistant1, parkingAssistant2));

        parkingAssistant1.park(new Car() {});
        parkingCoordinator.park(expectedCar);

        verify(parkingAssistant2).park(expectedCar);
    }

    @Test
    @Ignore
    public void parkingAssistantShouldNotDelegateTheParkingJobToAssistantsAfterRevokingCoordinatorRole() {
        ParkingLot parkingLot = new ParkingLot(2);
        ParkingAssistant parkingCoordinator = new ParkingAssistant(Arrays.asList(parkingLot));
        Car expectedCar = new Car() {};

        ParkingAssistant parkingAssistant1 = mock(ParkingAssistant.class);
        ParkingAssistant parkingAssistant2 = new ParkingAssistant(Arrays.asList(parkingLot));

        parkingCoordinator.coordinate(Arrays.asList(parkingAssistant1, parkingAssistant2));
        parkingCoordinator.park(expectedCar);
        verify(parkingAssistant1).park(expectedCar);

        parkingCoordinator.revokeCoordinatorRole();
        parkingCoordinator.park(expectedCar);
        verifyZeroInteractions(parkingAssistant1);
    }
}
