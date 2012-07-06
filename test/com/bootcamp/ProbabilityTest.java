package com.bootcamp;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProbabilityTest {
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowAnErrorForInvalidProbabilityValue() {
        new Probability(2.5);
    }

    @Test
    public void shouldCreateProbabilityObjectForValidValues() {
        assertNotNull(new Probability(0));
        assertNotNull(new Probability(0.5));
        assertNotNull(new Probability(1));
    }

    @Test
    public void shouldReturnTheProbabilityOfTheEventNotOccuring() {
        Probability probability = new Probability(0.4);
        Probability expectedProbability = new Probability(0.6);
        assertEquals(expectedProbability, probability.not());
    }

    @Test
    public void shouldReturnTheProbabilityOfTheEventOccuringAgain() {
        Probability probability = new Probability(0.5);
        Probability expectedProbability = new Probability(0.25);
        assertEquals(expectedProbability, probability.again());
    }

    @Test
    public void shouldReturnTheProbabilityOfTheEventsOccuringTogether() {
        Probability probability = new Probability(0.5);
        Probability another = new Probability(0.5);
        Probability expected = new Probability(0.25);
        assertEquals(expected, probability.and(another));
    }

    @Test
    public void shouldCheckTheEqualityOfTheObjects() {
        assertEquals(new Probability(0.4), new Probability(0.4));
        assertNotSame(new Probability(0.5), new Probability(0.4));
    }

    @Test
    public void shouldReturnTheProbabiltyOfTheEventOccuringAtleastOnce() {
        Probability probability = new Probability(0.5);
        assertEquals(new Probability(0.5), probability.atLeastOnce(1));
        assertEquals(new Probability(0.75), probability.atLeastOnce(2));
        assertEquals(new Probability(0.875), probability.atLeastOnce(3));
    }
}
