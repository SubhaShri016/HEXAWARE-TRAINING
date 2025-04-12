package com.java.asset.main;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import java.util.Scanner;

public class PerformMaintenanceHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID for maintenance: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Maintenance Date (YYYY-MM-DD): ");
        String maintenanceDate = scanner.nextLine();
        System.out.print("Enter Maintenance Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Maintenance Cost: ");
        double cost = scanner.nextDouble();
        scanner.nextLine(); 
        try {
            if (service.performMaintenance(assetId, maintenanceDate, description, cost)) {
                System.out.println("Maintenance record added successfully.");
            } else {
                System.out.println("Failed to add maintenance record.");
            }
        } catch (AssetNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
