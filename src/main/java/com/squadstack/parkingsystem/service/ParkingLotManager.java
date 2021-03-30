package com.squadstack.parkingsystem.service;

import com.squadstack.parkingsystem.exception.AbstractParkingLotRuntimeException;
import com.squadstack.parkingsystem.exception.ErrorCode;
import com.squadstack.parkingsystem.model.Car;
import com.squadstack.parkingsystem.model.Driver;
import com.squadstack.parkingsystem.model.ParkingSlot;
import com.squadstack.parkingsystem.model.Vehicle;
import com.squadstack.parkingsystem.parkinglot.ParkingLot;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for assigning tickets and serving other search queries
 */
public class ParkingLotManager {

    private static final String SPACE_DELIMITER = " ";

    private static final String COMMA_DELIMITER = ",";

    private ParkingLot parkingLot;


    public ParkingLotManager(final ParkingLot parkingLot) {

        this.parkingLot = parkingLot;
    }

    public void executeCommand(final String command) {

        String[] commandArray = command.split(SPACE_DELIMITER);

        try {
            switch (commandArray[0]) {

                case "Park":
                    parkVehicle(commandArray);
                    break;
                case "Leave":
                    vacateSlot(commandArray);
                    break;
                case "Slot_numbers_for_driver_of_age":
                    getSlotNumbersForDriverOfParticularAge(commandArray);
                    break;
                case "Vehicle_registration_number_for_driver_of_age":
                    getVehicleRegistrationNumberForDriverOfAge(commandArray);
                    break;
                case "Slot_number_for_car_with_number":
                    getSlotNumberWithParticularNumber(commandArray);
                    break;
                default:
                    System.out.println("Invalid command");

            }
        }
        catch (AbstractParkingLotRuntimeException exception) {

            ErrorCode errorCode = exception.getErrorCode();
            System.out.println(String.format("%d : %s", errorCode.getCode(), errorCode.getDefaultErrorMessage()));
        }
        catch (Exception exception) {
            System.out.println(String.format("Something went wrong while executing command %s", command));
        }

    }

    private void getVehicleRegistrationNumberForDriverOfAge(final String[] commandArray) {

        String result = String.join(COMMA_DELIMITER,
                                    parkingLot.getVehicleRegistrationNumbersBasedOnDriverAge(Integer.parseInt(commandArray[1])));

        if (!result.isEmpty()) {
            System.out.println(result);
        }
    }

    private void getSlotNumberWithParticularNumber(final String[] commandArray) {

        parkingLot.getSlotNumber(commandArray[1]).ifPresent(System.out::println);
    }

    private void getSlotNumbersForDriverOfParticularAge(final String[] commandArray) {

        String result = parkingLot.getSlotNumbersWithDriverOfGivenAge(Integer.parseInt(commandArray[1])).stream().map(String::valueOf)
                                  .collect(Collectors.joining(COMMA_DELIMITER));

        if (!result.isEmpty()) {
            System.out.println(result);
        }
    }

    private void vacateSlot(final String[] vacateSlotArray) {

        int slotNumber = Integer.parseInt(vacateSlotArray[1]);
        Optional<ParkingSlot> vacatedParkingSlot = parkingLot.vacateSlot(slotNumber);
        if (vacatedParkingSlot.isPresent()) {
            System.out.println(String.format(
                    "Slot number %d vacated, the car with vehicle registration number \"%s\" left the space, the driver of the car was of" +
                            " age %d",
                    slotNumber, vacatedParkingSlot.get().getVehicle().getRegistrationNumber(),
                    vacatedParkingSlot.get().getDriver().getAge()));
        }
        else {
            System.out.println("Slot already vacant");
        }
    }

    private void parkVehicle(final String[] parkCommandArray) {

        Vehicle vehicle = new Car(parkCommandArray[1]);
        Driver driver = new Driver(Integer.parseInt(parkCommandArray[3]));
        Optional<Integer> optionalSlotNumber = parkingLot.parkVehicle(vehicle, driver);
        optionalSlotNumber.ifPresent(slotNumber -> printCarParkingDetails(parkCommandArray[1], slotNumber));
    }

    private void printCarParkingDetails(final String registrationNumber,
                                        final int slotNumber) {

        System.out.println(
                String.format("Car with vehicle registration number \"%s\" has been parked at slot number %d", registrationNumber,
                              slotNumber));
    }

}
