package com.ask.barclays.exercise1;

import com.ask.barclays.exercise1.po.ConveyorSystem;
import com.ask.barclays.exercise1.po.Flight;
import com.ask.barclays.exercise1.po.Gate;
import com.ask.barclays.exercise1.processor.DataProcessor;
import com.ask.barclays.exercise1.processor.DataReceiver;
import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Automated baggage system.
 *
 */
public class ExerciseOne {

    public static void main(String[] args) {
        DataReceiver receiver = new DataReceiver();
        DataProcessor processor = new DataProcessor();
        try(Scanner userInput = new Scanner(System.in)) {
            Map<String,Gate> gateMap = receiver.buildConveyorGateInput(userInput);
            receiver.buildFlightDetails(userInput, gateMap);
            processor.processBags(userInput,gateMap);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }


}
