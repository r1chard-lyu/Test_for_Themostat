package org.themostat;

import org.junit.jupiter.api.Test;
import org.junit.Assert;


import static org.junit.jupiter.api.Assertions.*;
class ThermostatTest {
    private Thermostat thermostat;

    // Predicate Coverage (PC)
    @Test
    public void testTurnHeaterOn_If_TrueCondition() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();
        thermostat.setThresholdDiff(5);
        thermostat.setMinLag(10);
        thermostat.setCurrentTemp(60);
        thermostat.setOverride(true);
        thermostat.setOverTemp(70);
        thermostat.setTimeSinceLastRun(15);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);

        assertTrue(thermostat.turnHeaterOn(settings));

        // Additional assertions for expected changes in the Thermostat object
    }

    @Test
    public void testTurnHeaterOn_If_FalseCondition(){
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();
        thermostat.setThresholdDiff(5);
        thermostat.setMinLag(10);
        thermostat.setCurrentTemp(70);
        thermostat.setOverride(true);
        thermostat.setOverTemp(70);
        thermostat.setTimeSinceLastRun(5);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);

        assertFalse(thermostat.turnHeaterOn(settings));

    }

    @Test
    public void testTurnHeaterOn_B_TrueCondition() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();
        thermostat.setThresholdDiff(5);
        thermostat.setMinLag(10);
        thermostat.setCurrentTemp(60);
        thermostat.setOverride(true);
        thermostat.setOverTemp(70);
        thermostat.setTimeSinceLastRun(15);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);

        assertTrue(thermostat.turnHeaterOn(settings));
        assertEquals(10, thermostat.getRunTime());
    }

    @Test
    public void testTurnHeaterOn_B_FalseCondition() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();
        thermostat.setThresholdDiff(5);
        thermostat.setMinLag(10);
        thermostat.setCurrentTemp(70);
        thermostat.setOverride(false);
        thermostat.setOverTemp(70);
        thermostat.setTimeSinceLastRun(15);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);

        assertFalse(thermostat.turnHeaterOn(settings));
        assertEquals(0, thermostat.getRunTime());

    }

    // Clause Coverage (CC)
    //  TODO: Add tests for clause coverage


    // Correlated Active Clause Coverage (CACC)
    /*
    * Truth Table:
    *  Row# | a | b | c | d | P | Pa| Pb| Pc| Pd|
    *  ---- |---|---|---|---|---|---|---|---|---|
    *   1	| T | T | T | T | T |   |   |   | T |
    *   2	| T | T | T |   |   |   |   |   | T |
    *   3	| T | T |   | T | T | T |   |   | T |
    *   4	| T | T |   |   |   |   |   |   | T |
    *   5	| T |   | T | T | T | T |   |   | T |
    *   6	| T |   | T |   |   |   |   |   | T |
    *   7	| T |   |   | T | T | T |   |   | T |
    *   8	| T |   |   |   |   |   |   |   | T |
    *   9	|   | T | T | T | T |   | T | T | T |
    *   10	|   | T | T |   |   |   |   |   | T |
    *   11	|   | T |   | T |   | T |   | T |   |
    *   12	|   | T |   |   |   |   |   |   |   |
    *   13	|   |   | T | T |   | T | T |   |   |
    *   14	|   |   | T |   |   |   |   |   |   |
    *   15	|   |   |   | T |   | T |   |   |   |
    *   16	|   |   |   |   |   |   |   |   |   |
    */

    /*
    * The following result for CACC is based on the truth table on the right:
    * Major Clause |  Set of possible tests
    * ------------ | ------------------------------------------------------------------------------------------------------------
    *       a	   |  (3,11), (3,13), (3,15), (5,11), (5,13), (5,15), (7,11), (7,13), (7,15)
    *       b	   |  (9,13)
    *       c	   |  (9,11)
    *       d	   |  (1,2), (1,4), (1,6), (1,8), (1,10), (3,2), (3,4), (3,6), (3,8), (3,10),
    *              |  (5,2), (5,4), (5,6), (5,8), (5,10), (7,2), (7,4), (7,6), (7,8), (7,10), (9,2), (9,4), (9,6), (9,8), (9,10)
    */


    // Major Clause A (3, 11)
    @Test
    public void testTurnHeaterOn_Major_Clause_A() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();

        /* 3 : a = T ; b = T ; c = F ; d = T */
        // a = T (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(50);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = F (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(65);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = T
        assertTrue(thermostat.turnHeaterOn(settings));


        /* 11 : a = F ; b = T ; c = F ; d = T */
        // a = F (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(70);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = F (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(65);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = F
        assertFalse(thermostat.turnHeaterOn(settings));
    }

    // Major Clause B (9, 13)
    // TODO: line 34 needs to test ?
    @Test
    public void testTurnHeaterOn_Major_Clause_B() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();

        /* 9 : a = F ; b = T ; c = T ; d = T */
        // a = F (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(70);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = T (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(85);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = T
        assertTrue(thermostat.turnHeaterOn(settings));


        /* 13 : a = F ; b = F ; c = T ; d = T */
        // a = F (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(70);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = F (override)
        thermostat.setOverride(false);

        // c = T (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(85);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = F
        assertFalse(thermostat.turnHeaterOn(settings));
    }

    // Major Clause C (9, 11)
    // a = F ; b = T ; c = F ; d = T
    @Test
    public void testTurnHeaterOn_Major_Clause_C() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();

        /* 9 : a = F ; b = T ; c = T ; d = T */
        // a = F (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(70);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = T (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(85);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = T
        assertTrue(thermostat.turnHeaterOn(settings));


        /* 11 : a = F ; b = T ; c = F ; d = T */
        // a = F (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(70);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = F (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(65);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = F
        assertFalse(thermostat.turnHeaterOn(settings));
    }

    // Major Clause D (1, 2)
    @Test
    public void testTurnHeaterOn_Major_Clause_D() {
        Thermostat thermostat = new Thermostat();
        ProgrammedSettings settings = new ProgrammedSettings();

        /* 1 : a = T ; b = T ; c = T ; d = T */
        // a = T (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(50);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = T (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(65);

        // d = T (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(15);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = T
        assertTrue(thermostat.turnHeaterOn(settings));


        /* 2 : a = T ; b = T ; c = T ; d = F */
        // a = T (curTemp < dTemp - thresholdDiff)
        thermostat.setCurrentTemp(50);
        thermostat.setDay(DayType.WEEKDAY);
        thermostat.setPeriod(Period.MORNING);
        thermostat.setThresholdDiff(5);

        // b = T (override)
        thermostat.setOverride(true);

        // c = T (curTemp < overTemp - thresholdDiff)
        thermostat.setOverTemp(65);

        // d = F (timeSinceLastRun > minLag)
        thermostat.setTimeSinceLastRun(5);
        thermostat.setMinLag(10);

        // P = (a || (b && c)) && d = F
        assertFalse(thermostat.turnHeaterOn(settings));
    }
}