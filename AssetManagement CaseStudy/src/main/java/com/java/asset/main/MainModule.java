package com.java.asset.main;

import com.java.asset.dao.AssetManagementService;
import com.java.asset.dao.AssetManagementServiceImpl;
import java.util.Scanner;

public class MainModule {

    public static void main(String[] args) {
        AssetManagementService service = new AssetManagementServiceImpl();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nAsset Management System Menu:");
            System.out.println("1. Add Asset");
            System.out.println("2. Update Asset");
            System.out.println("3. Delete Asset");
            System.out.println("4. Allocate Asset");
            System.out.println("5. Deallocate Asset");
            System.out.println("6. Perform Maintenance");
            System.out.println("7. Reserve Asset");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    AddAssetHandler.handle(scanner, service);
                    break;
                case 2:
                    UpdateAssetHandler.handle(scanner, service);
                    break;
                case 3:
                    DeleteAssetHandler.handle(scanner, service);
                    break;
                case 4:
                    AllocateAssetHandler.handle(scanner, service);
                    break;
                case 5:
                    DeallocateAssetHandler.handle(scanner, service);
                    break;
                case 6:
                    PerformMaintenanceHandler.handle(scanner, service);
                    break;
                case 7:
                    ReserveAssetHandler.handle(scanner, service);
                    break;
                case 8:
                    System.out.println("Exiting Asset Management System.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}