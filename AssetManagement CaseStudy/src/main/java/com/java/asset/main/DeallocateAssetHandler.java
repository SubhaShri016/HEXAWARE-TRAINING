package com.java.asset.main;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import java.util.Scanner;

public class DeallocateAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID to deallocate: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Employee ID to deallocate from: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Return Date (YYYY-MM-DD): ");
        String returnDate = scanner.nextLine();

        try {
            if (service.deallocateAsset(assetId, employeeId, returnDate)) {
                System.out.println("Asset deallocated successfully.");
            } else {
                System.out.println("Failed to deallocate asset.");
            }
        } catch (AssetNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}