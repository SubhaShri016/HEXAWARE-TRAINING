package com.java.asset.test;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.dao.AssetManagementService;
import com.java.asset.dao.AssetManagementServiceImpl;
import com.java.asset.model.Asset;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID; 

public class AssetCreationTest {

    private AssetManagementService assetService;

    @Before
    public void setUp() {
        assetService = new AssetManagementServiceImpl();
    }

    @Test
    public void testAddAssetSuccessfully() {
        String uniqueSerialNumber = "JUNIT-SERIAL-" + UUID.randomUUID().toString(); 
        Asset newAsset = new Asset(0, "Test Asset", "Laptop", uniqueSerialNumber, "2024-01-01", "Test Location", "in use", 1); // Use unique serial
        System.out.println("DEBUG testAddAssetSuccessfully: Before assetService.addAsset(newAsset) with serial: " + uniqueSerialNumber); // Debug log with serial
        boolean added = assetService.addAsset(newAsset);
        System.out.println("DEBUG testAddAssetSuccessfully: assetService.addAsset(newAsset) returned: " + added); 
        assertTrue("Asset should be added successfully", added);


      
        try {
            Asset retrievedAsset = findAssetBySerialNumber(uniqueSerialNumber); 
            assertNotNull("Retrieved asset should not be null", retrievedAsset);
            assertEquals("Retrieved asset name should match", "Test Asset", retrievedAsset.getName());
            assertEquals("Retrieved asset status should match", "in use", retrievedAsset.getStatus());
            assertEquals("Retrieved asset serial number should match", uniqueSerialNumber, retrievedAsset.getSerialNumber()); // Verify unique serial
        } catch (AssetNotFoundException e) {
            fail("AssetNotFoundException should not be thrown after adding asset: " + e.getMessage());
        }
    }

    
    private Asset findAssetBySerialNumber(String serialNumber) throws AssetNotFoundException {
        List<Asset> allAssets = assetService.getAllAssets();
        for (Asset asset : allAssets) {
            if (asset.getSerialNumber().equals(serialNumber)) {
                return asset;
            }
        }
        throw new AssetNotFoundException("Asset with serial number " + serialNumber + " not found after adding.");
    }
}
