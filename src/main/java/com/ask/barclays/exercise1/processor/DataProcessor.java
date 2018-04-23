package com.ask.barclays.exercise1.processor;

import com.ask.barclays.exercise1.po.Flight;
import com.ask.barclays.exercise1.po.Gate;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Process the Bag information to find the shortest path.
 */
public class DataProcessor {

    /**
     * Reads Bags and destination flight information and find the shortest path.
     * @param userInput
     * @param gateMap
     */
    public void processBags(Scanner userInput, Map<String, Gate> gateMap) {
        System.out.println("Enter the input values : Baggage_Id (space) Gate (space) Flight");
        System.out.println("If you want to exit, press y");
        try {
            String input = userInput.nextLine();
            while (input != null && !input.equalsIgnoreCase("y")) {
                String[] baggageInfo = input.split(" ");
                if (baggageInfo.length == 3) {
                    Gate gate = gateMap.get(baggageInfo[1]);
                    if (gate != null) {
                        List<Gate> gates = findShortestPath(gateMap, baggageInfo[0], baggageInfo[1], baggageInfo[2], 0);
                        if (gates != null && gates.size() > 0) {
                            Collections.reverse(gates);
                            System.out.print(baggageInfo[0] + " ");
                            int sum = 0;
                            for (Gate path : gates) {
                                sum += path.getCostFromOrigin();
                                System.out.print(path.getName() + " ");
                            }
                            System.out.print(" : " + sum);
                            System.out.println();
                        }
                        cleanDataVisitedNodes(gateMap);
                    }
                } else {
                    System.out.println("Invalid input");
                }
                input = userInput.nextLine();

            }
        } catch (Exception exception) {
            System.out.println("Exception in finding the shortest path" + exception.getMessage());
        }
    }

    /**
     * clear the reset the map and gates visited flag information.
     * @param gateMap
     */
    private void cleanDataVisitedNodes(Map<String, Gate> gateMap) {
        gateMap.entrySet().forEach( map -> {
            Gate gate = map.getValue();
            gate.setVisited(Boolean.FALSE);
            gateMap.put(map.getKey(), gate);
        });
    }

    /**
     * Find the shortest path for the given source(gate) and destination(flight id).
     * @param map
     * @param baggageId
     * @param source
     * @param destination
     * @param weight
     * @return
     */
    public List<Gate> findShortestPath(Map<String, Gate> map, String baggageId, String source, String destination, int weight) {

        if (map == null || map.isEmpty()) {
            return null;
        }
        Gate gate = map.get(source);
        if (gate.isVisited()) {
            return null;
        }
        gate.setVisited(Boolean.TRUE);
        map.put(gate.getName(), gate);

        /**
         *  Checking whether its a destination gate.
         */
        Optional<Flight> destGate = gate.getFlights().stream().filter(gateFlightCheck -> gateFlightCheck.getId().equalsIgnoreCase(destination)).findFirst();
        if (destGate.isPresent() || destination.equalsIgnoreCase(source)) {
            List<Gate> foundGate = new ArrayList<>();
            gate.setCostFromOrigin(weight);
            foundGate.add(gate);
            return foundGate;
        }

        /**
         * Get the connected Vertices(Gates), if its not already visited.
         */
        List<Gate> possibleGates = gate.getConnectedGates().stream().filter(connectedGate -> !map.get(connectedGate.getName()).isVisited()).collect(Collectors.toList());
        if (possibleGates != null && possibleGates.size() > 0) {

            /**
             * Recursive call with all the possible connected nodes and collecting the result in List<List<Gate>>.
             */
            List<List<Gate>> gateTotal = possibleGates.stream().map(connectedGate -> {
                List<Gate> tmpList = findShortestPath(map, baggageId, connectedGate.getName(), destination, connectedGate.getCostFromOrigin());
                if (tmpList != null) {
                    gate.setCostFromOrigin(weight);
                    tmpList.add(gate);
                }
                return tmpList;
            }).collect(Collectors.toList());

            /**
             * filtering and finding the shortest path.
             */
            return gateTotal.stream().filter(nullList -> nullList!=null && nullList.size() > 0).min(Comparator.comparingInt(elem -> elem.stream().mapToInt(gateVal -> gateVal.getCostFromOrigin()).sum())).orElse(null);
        } else {
            return null;
        }
    }


}
