package com.java.asset.main;

import com.java.asset.dao.AssetManagementService;
import com.java.asset.model.Asset;
import java.util.Scanner;

public class AddAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.println("Enter asset details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Type: ");
        String type = scanner.nextLine();
        System.out.print("Serial Number: ");
        String serialNumber = scanner.nextLine();
        System.out.print("Purchase Date (YYYY-MM-DD): ");
        String purchaseDate = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        System.out.print("Status (in use, decommissioned, under maintenance): ");
        String status = scanner.nextLine();
        System.out.print("Owner ID (Employee ID): ");
        int ownerId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Asset asset = new Asset(0, name, type, serialNumber, purchaseDate, location, status, ownerId); 
        if (service.addAsset(asset)) {
            System.out.println("Asset added successfully.");
        } else {
            System.out.println("Failed to add asset.");
        }
    }
}
