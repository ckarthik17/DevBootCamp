package com.bootcamp;

public class Probability {
    private double value;

    public Probability(double value) {
        if(value < 0 || value > 1) {
            throw new IllegalArgumentException();
        }
        this.value = value;
    }

    public Probability not() {
        return new Probability(1-this.value);
    }

    public Probability again() {
        return new Probability(this.value * this.value);
    }

    public Probability and(Probability another) {
        return new Probability(this.value * another.value);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;

        Probability that = (Probability) other;

        if (Double.compare(that.value, value) != 0) return false;

        return true;
    }

    public Probability atLeastOnce(int numberOfTries) {
        Probability temp = this.not();
        for(int i=1; i<numberOfTries; i++) {
            temp = temp.and(this.not());
        }

        return temp.not();
    }
}
