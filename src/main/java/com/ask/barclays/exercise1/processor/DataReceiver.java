package com.ask.barclays.exercise1.processor;

import com.ask.barclays.exercise1.po.Flight;
import com.ask.barclays.exercise1.po.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 * Reads the user data and build the object.
 */
public class DataReceiver {

    /**
     *  Read the flight information from the user and build the Flight object.
     * @param userInput
     * @param gateMap
     */
    public void buildFlightDetails(Scanner userInput, Map<String, Gate> gateMap) throws Exception {

        System.out.println("Enter the Flight, Gate, destination  and corresponding time delimiter by space");
        System.out.println("Do you want to exit, press y");
        try {
            if (gateMap != null && gateMap.isEmpty()) {
                throw new Exception("Conveyor System is not prepard properly");
            }
            String input = userInput.nextLine();
            while (input != null && !input.equalsIgnoreCase("y")) {
                String[] flightInfo = input.split(" ");
                if (flightInfo.length == 4) {
                    /**
                     * check for the gate id in map and the flight information into it.
                     */
                    gateMap.computeIfPresent(flightInfo[1], (key, value) -> {
                        Optional.ofNullable(value.getFlights()).ifPresent(flights -> {
                            Flight flight = new Flight(flightInfo[0], flightInfo[2], flightInfo[3]);
                            flights.add(flight);
                            value.setFlights(flights);
                        });
                        return value;
                    });
                }
                input = userInput.nextLine();
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Invalid data for the input");
            throw new Exception("Null pointer Exception in building flight details" + nullPointerException.getMessage());
        } catch (Exception exception) {
            throw new Exception("Exception in building flight details" + exception.getMessage());
        }
    }

    /**
     * Building Gate object from the user input.
     * @param userInput
     * @return
     */
    public Map<String,Gate> buildConveyorGateInput(Scanner userInput) throws Exception {
        System.out.println("Enter the Gate1, Gate 2  and corresponding weight delimiter by space");
        System.out.println("Do you want to exit, press y");
        String input = userInput.nextLine();
        Map<String, Gate> availableGates = new HashMap<>();
        try {
            while (input != null && !input.equalsIgnoreCase("y")) {
                /**
                 * Creating the conveyor system object.
                 */
                String[] gateInfo = input.split(" ");
                if (gateInfo.length == 3) {
                    /**
                     * creating map of Gate object
                     * K,V : K - Gate name , V - All the available Gates.
                     */
                    BiFunction<String, String, Gate> buildObject = (source, destination) -> {
                        final Gate gate = Optional.ofNullable(availableGates.get(source)).orElseGet(() -> Optional.ofNullable(new Gate(source)).get());

                        Optional.ofNullable(gate.getConnectedGates()).ifPresent(gateList -> {
                            /**
                             * Building connected Gates objects with cost for the corresponding gates.
                             * eg: a5-> a1,a2,a3
                             */
                            Gate connectedGate = new Gate(destination, Integer.parseInt(gateInfo[2]));
                            gateList.add(connectedGate);
                            gate.setConnectedGates(gateList);
                        });
                        return gate;
                    };
                    Gate gate = availableGates.put(gateInfo[0], buildObject.apply(gateInfo[0], gateInfo[1]));
                    gate = availableGates.put(gateInfo[1], buildObject.apply(gateInfo[1], gateInfo[0]));
                } else {
                    System.out.println("Its a valid input, enter it again or y to exit");
                }
                input = userInput.nextLine();
            }
        } catch (NullPointerException nullPointerException) {
            System.out.println("Invalid data for the input");
            throw new Exception("Null pointer Exception in building gate details" + nullPointerException.getMessage());
        } catch (Exception exception) {
            throw new Exception("Exception in building gate details" + exception.getMessage());
        }

        return availableGates;
    }
}
