package com.java.asset.main;

import com.java.asset.dao.AssetManagementService;
import java.util.Scanner;

public class DeleteAssetHandler {

    public static void handle(Scanner scanner, AssetManagementService service) {
        System.out.print("Enter Asset ID to delete: ");
        int assetId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        if (service.deleteAsset(assetId)) {
            System.out.println("Asset deleted successfully.");
        } else {
            System.out.println("Failed to delete asset.");
        }
    }
}