package com.java.asset.main;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import java.util.Scanner;

public class ReserveAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID to reserve: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Employee ID for reservation: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Reservation Date (YYYY-MM-DD): ");
        String reservationDate = scanner.nextLine();
        System.out.print("Enter Start Date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine();
        System.out.print("Enter End Date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine();

        try {
            if (service.reserveAsset(assetId, employeeId, reservationDate, startDate, endDate)) {
                System.out.println("Asset reserved successfully.");
            } else {
                System.out.println("Failed to reserve asset.");
            }
        } catch (AssetNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}