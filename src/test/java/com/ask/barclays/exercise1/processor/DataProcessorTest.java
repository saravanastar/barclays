package com.ask.barclays.exercise1.processor;

import com.ask.barclays.exercise1.ExerciseOneTest;
import com.ask.barclays.exercise1.po.Gate;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class DataProcessorTest extends ExerciseOneTest {

    DataProcessor dataProcessor = new DataProcessor();
    DataReceiver dataReceiver = new DataReceiver();

    @Test
    public void testRandomInputs() throws Exception {
        Scanner scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_conveyorSys.txt"));
        Map<String,Gate> out =  dataReceiver.buildConveyorGateInput(scanner);
        scanner = new Scanner(loadInputData(RESOURCE_PATH, "section_departures.txt"));
        dataReceiver.buildFlightDetails(scanner, out);
        scanner = new Scanner(new ByteArrayInputStream("0001 Concourse_A_Ticketing UA12".getBytes()));
        List<Gate> path = dataProcessor.findShortestPath(out, "0001", "Concourse_A_Ticketing", "UA12", 0);
        Assert.assertNotNull(path);
        Assert.assertEquals(3,path.size());
        Assert.assertEquals(Boolean.TRUE, path.stream().filter(gate -> gate.getName().equalsIgnoreCase("A1")).findFirst().isPresent());
    }
}
