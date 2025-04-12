package com.java.asset.main;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import com.java.asset.model.Asset;
import java.util.Scanner;

public class UpdateAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID to update: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); 

        try {
            Asset existingAsset = service.getAssetById(assetId);
            if (existingAsset == null) {
                System.out.println("Asset not found.");
                return;
            }

            System.out.println("Enter updated asset details (leave blank to keep current):");
            System.out.print("Name (" + existingAsset.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Type (" + existingAsset.getType() + "): ");
            String type = scanner.nextLine();
            System.out.print("Serial Number (" + existingAsset.getSerialNumber() + "): ");
            String serialNumber = scanner.nextLine();
            System.out.print("Purchase Date (" + existingAsset.getPurchaseDate() + ") (YYYY-MM-DD): ");
            String purchaseDate = scanner.nextLine();
            System.out.print("Location (" + existingAsset.getLocation() + "): ");
            String location = scanner.nextLine();
            System.out.print("Status (" + existingAsset.getStatus() + ") (in use, decommissioned, under maintenance): ");
            String status = scanner.nextLine();
            System.out.print("Owner ID (" + existingAsset.getOwnerId() + "): ");
            String ownerIdStr = scanner.nextLine();
            int ownerId = ownerIdStr.isEmpty() ? existingAsset.getOwnerId() : Integer.parseInt(ownerIdStr);


            Asset updatedAsset = new Asset(assetId,
                    name.isEmpty() ? existingAsset.getName() : name,
                    type.isEmpty() ? existingAsset.getType() : type,
                    serialNumber.isEmpty() ? existingAsset.getSerialNumber() : serialNumber,
                    purchaseDate.isEmpty() ? existingAsset.getPurchaseDate() : purchaseDate,
                    location.isEmpty() ? existingAsset.getLocation() : location,
                    status.isEmpty() ? existingAsset.getStatus() : status,
                    ownerId);


            if (service.updateAsset(updatedAsset)) {
                System.out.println("Asset updated successfully.");
            } else {
                System.out.println("Failed to update asset.");
            }

        } catch (AssetNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
