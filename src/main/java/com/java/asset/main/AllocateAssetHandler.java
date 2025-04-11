package com.java.asset.main;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import java.util.Scanner;

public class AllocateAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID to allocate: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Employee ID to allocate to: ");
        int employeeId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("Enter Allocation Date (YYYY-MM-DD): ");
        String allocationDate = scanner.nextLine();

        try {
            if (service.allocateAsset(assetId, employeeId, allocationDate)) {
                System.out.println("Asset allocated successfully.");
            } else {
                System.out.println("Failed to allocate asset.");
            }
        } catch (AssetNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}