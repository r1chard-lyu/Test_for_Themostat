package org.themostat;

import org.junit.jupiter.api.Test;
import org.junit.Assert;


import static org.junit.jupiter.api.Assertions.*;
class ThermostatTest {
    private Thermostat thermostat;

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

}