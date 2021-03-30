package com.squadstack.parkingsystem;

import com.squadstack.parkingsystem.parkinglot.IParkingLot;
import com.squadstack.parkingsystem.parkinglot.ParkingLot;
import com.squadstack.parkingsystem.service.ParkingLotManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.IntStream;

public class Simulator {

    public static void main(String[] args) {

        if (args.length < 1) {

            System.out.println("Please provide input file path");
            return;
        }

        List<String> allCommands = getAllCommandsFromFile(args[0]);
        String parkingLotCreationCommand = allCommands.get(0);

        String[] creationCommandArray = parkingLotCreationCommand.split(" ");
        int totalCapacity = Integer.parseInt(creationCommandArray[1]);
        ParkingLot parkingLot = new IParkingLot(totalCapacity);
        System.out.println(String.format("Created parking of %d slots", totalCapacity));

        ParkingLotManager parkingLotManager = new ParkingLotManager(parkingLot);
        IntStream.range(1, allCommands.size())
                 .mapToObj(allCommands::get)
                 .forEach(parkingLotManager::executeCommand);

    }

    private static List<String> getAllCommandsFromFile(final String filePath) {

        try {
            return Files.readAllLines(Paths.get(filePath));
        }
        catch (IOException e) {
            throw new RuntimeException("Invalid file path");
        }
    }
}
