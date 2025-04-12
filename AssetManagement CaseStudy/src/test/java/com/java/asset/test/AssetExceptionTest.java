package com.java.asset.test;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.MyExceptions.AssetNotMaintainException;
import com.java.asset.dao.AssetManagementService;
import com.java.asset.dao.AssetManagementServiceImpl;
import com.java.asset.model.Asset;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.fail;

public class AssetExceptionTest {

    private AssetManagementService assetService;
    private static int assetCounter = 0; 

    private static String generateUniqueSerialNumber(String baseName) {
        return baseName + "-" + UUID.randomUUID().toString() + "-" + assetCounter++;
    }

    private String generateUniqueSerialNumberForTest(String baseName) {
        return generateUniqueSerialNumber(baseName);
    }


    @Before
    public void setUp() {
        assetService = new AssetManagementServiceImpl();
    }

    @Test(expected = AssetNotFoundException.class)
    public void testGetAssetByIdNotFoundException() throws AssetNotFoundException {
        assetService.getAssetById(9999); 
    }

    @Test(expected = AssetNotFoundException.class)
    public void testPerformMaintenanceAssetNotFoundException() throws AssetNotFoundException, AssetNotMaintainException {
        assetService.performMaintenance(9999, "2024-05-15", "Test Maintenance", 100.00); 
    }

    @Test(expected = AssetNotFoundException.class)
    public void testReserveAssetAssetNotFoundException() throws AssetNotFoundException {
        assetService.reserveAsset(9999, 1, "2024-05-15", "2024-05-20", "2024-05-25"); 
    }

    @Test
    public void testPerformMaintenanceAssetNotMaintainException() throws AssetNotFoundException, AssetNotMaintainException {
       
        String uniqueSerialNumber = generateUniqueSerialNumberForTest("JUNIT-EXCEPTION");
        Asset assetNotMaintainable = new Asset(0, "Not Maintainable Asset", "Printer", uniqueSerialNumber, "2024-01-01", "Test Location", "decommisioned", 1);
        System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: Before assetService.addAsset(assetNotMaintainable) with serial: " + uniqueSerialNumber); 
        boolean addAssetResult = assetService.addAsset(assetNotMaintainable);
        System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: assetService.addAsset(assetNotMaintainable) returned: " + addAssetResult); 
        System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: assetNotMaintainable.getAssetId() AFTER addAsset: " + assetNotMaintainable.getAssetId());

       
        Asset retrievedAsset = null;
        try {
            retrievedAsset = findAssetBySerialNumber(uniqueSerialNumber); 
            System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: findAssetBySerialNumber FOUND asset with ID: " + retrievedAsset.getAssetId()); 
            System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: Retrieved assetId BEFORE performMaintenance call: " + retrievedAsset.getAssetId()); 
        } catch (AssetNotFoundException e) {
            System.out.println("DEBUG testPerformMaintenanceAssetNotMaintainException: findAssetBySerialNumber threw AssetNotFoundException: " + e.getMessage()); 
            fail("findAssetBySerialNumber threw AssetNotFoundException: " + e.getMessage()); 
            return; 
        }


       
        assetService.performMaintenance(retrievedAsset.getAssetId(), "2024-05-15", "Attempted Maintenance", 50.00);
        
    }

    
    private Asset findAssetBySerialNumber(String serialNumber) throws AssetNotFoundException {
        System.out.println("DEBUG findAssetBySerialNumber: Searching for asset with serial number: " + serialNumber); 
        List<Asset> allAssets = assetService.getAllAssets();
        System.out.println("DEBUG findAssetBySerialNumber: Retrieved " + allAssets.size() + " assets from getAllAssets()"); 
        for (Asset asset : allAssets) {
            if (asset.getSerialNumber().equals(serialNumber)) {
                System.out.println("DEBUG findAssetBySerialNumber: Checking asset with ID: " + asset.getAssetId() + ", Serial Number: " + asset.getSerialNumber()); 
            }
            if (asset.getSerialNumber().equals(serialNumber)) {
                System.out.println("DEBUG findAssetBySerialNumber: Found matching asset with ID: " + asset.getAssetId()); 
                return asset;
            }
        }
        System.out.println("DEBUG findAssetBySerialNumber: Asset with serial number " + serialNumber + " NOT found."); 
        throw new AssetNotFoundException("Asset with serial number " + serialNumber + " not found after adding.");
    }
}
