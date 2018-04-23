package com.ask.barclays.exercise1.processor;

import com.ask.barclays.exercise1.ExerciseOneTest;
import com.ask.barclays.exercise1.po.Gate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;
import java.util.Scanner;

@RunWith(JUnit4.class)
public class DataReceiverTest extends ExerciseOneTest {

    DataReceiver dataReceiver = new DataReceiver();

    @Test
    public void testDataReceiver() throws Exception {
        Scanner scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_conveyorSys.txt"));
        Map<String,Gate> out =  dataReceiver.buildConveyorGateInput(scanner);
        Assert.assertNotNull(out);
        Assert.assertEquals(12, out.size());
    }

    @Test(expected = Exception.class)
    public void testInvalidInputParameter() throws Exception {
        Scanner scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_conveyorSys_invalid_input"));
        Map<String,Gate> out =  dataReceiver.buildConveyorGateInput(scanner);
    }

    @Test(expected = Exception.class)
    public void testNullScannerObject() throws Exception {
        Map<String,Gate> out =  dataReceiver.buildConveyorGateInput(null);
    }

    @Test
    public void testBuildFlightInfo() throws Exception {
        Scanner scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_conveyorSys.txt"));
        Map<String,Gate> out =  dataReceiver.buildConveyorGateInput(scanner);
        scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_departures.txt"));
        dataReceiver.buildFlightDetails(scanner, out);
        Assert.assertNotNull(out.get("A5").getFlights());
        Assert.assertEquals(1, out.get("A5").getFlights().size());
        Assert.assertEquals(3, out.get("A2").getFlights().size());
    }
}
