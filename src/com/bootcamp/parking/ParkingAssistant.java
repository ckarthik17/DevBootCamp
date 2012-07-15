package com.bootcamp.parking;

import com.bootcamp.parking.strategy.LotSelectionStrategy;
import com.bootcamp.parking.strategy.ParkInFirstAvailableLotStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 ** Help park and unpark car among one of multiple lots
 */
public class ParkingAssistant implements ParkingLotListener {

    private List<ParkingLot> parkingLots;
    private List<ParkingLot> availableParkingLots;
    private LotSelectionStrategy lotSelectionStrategy;
    private List<ParkingLot> almostFullParkingLots;

    private List<ParkingAssistant> assistants;

    public ParkingAssistant(List<ParkingLot> parkingLots) {
        this.parkingLots = parkingLots;
        this.almostFullParkingLots = new ArrayList<ParkingLot>();
        this.availableParkingLots = new ArrayList<ParkingLot>();
        this.lotSelectionStrategy = new ParkInFirstAvailableLotStrategy();
        this.assistants = new ArrayList<ParkingAssistant>();

        for (ParkingLot parkingLot : parkingLots) {
            this.availableParkingLots.add(parkingLot);
            parkingLot.addListener(this);
        }
    }

    public ParkingAssistant(List<ParkingLot> parkingLots, LotSelectionStrategy lotSelectionStrategy) {
        this(parkingLots);
        this.lotSelectionStrategy = lotSelectionStrategy;
    }

    public ParkingTicket park(Car car) {
        for(ParkingAssistant assistant : assistants) {
            if(assistant.isLotAvailable()) {
                return assistant.park(car);
            }
        }
        ParkingLot parkingLot = lotSelectionStrategy.lotToPark(availableParkingLots);
        return parkingLot.park(car);
    }

    private boolean isLotAvailable() {
        return availableParkingLots.size() != 0;
    }

    public Car unPark(ParkingTicket parkingTicket) {
        ParkingLot parkedLot = parkingTicket.getParkingLot();
        return parkedLot.unPark(parkingTicket);
    }

    public boolean isAlmostFull(ParkingLot parkingLot) {
        return almostFullParkingLots.contains(parkingLot);
    }

    public boolean isAvailable(ParkingLot parkingLot) {
        return availableParkingLots.contains(parkingLot);
    }

    public int availableParkingLots() {
        return availableParkingLots.size();
    }

    @Override
    public void unParked(ParkingLot parkingLot) {
        if(parkingLot.isFree() && !availableParkingLots.contains(parkingLot)) {
            availableParkingLots.add(parkingLot);
        }
    }

    @Override
    public void full(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);
    }

    @Override
    public void eightyPercentFull(ParkingLot parkingLot) {
        almostFullParkingLots.add(parkingLot);
    }

    @Override
    public void lessThanEightyPercentFull(ParkingLot parkingLot) {
        almostFullParkingLots.remove(parkingLot);
    }

    public boolean isCoordinator() {
        return assistants.size() != 0;
    }

    public void coordinate(List<ParkingAssistant> parkingAssistants) {
        this.assistants = parkingAssistants;
    }

    public void revokeCoordinatorRole() {
        this.assistants = new ArrayList<ParkingAssistant>();
    }
}
