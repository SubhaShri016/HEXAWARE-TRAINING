package com.java.asset.test;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.MyExceptions.AssetNotMaintainException;
import com.java.asset.dao.AssetManagementService;
import com.java.asset.dao.AssetManagementServiceImpl;
import com.java.asset.model.Asset;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.UUID; // Import UUID for unique serial numbers

public class AssetMaintenanceTest {

    private AssetManagementService assetService;

    @Before
    public void setUp() {
        assetService = new AssetManagementServiceImpl();
    }

    @Test
    public void testPerformMaintenanceSuccessfully() throws AssetNotFoundException, AssetNotMaintainException {
       
        String uniqueSerialNumber = "JUNIT-SERIAL-" + UUID.randomUUID().toString();
        Asset assetForMaintenance = new Asset(0, "Maintenance Test Asset", "Monitor", uniqueSerialNumber, "2024-01-01", "Test Location", "in use", 1); 
        assetService.addAsset(assetForMaintenance);
        Asset retrievedAsset = findAssetBySerialNumber(uniqueSerialNumber); 
        assertNotNull("Retrieved asset should not be null", retrievedAsset);
        Asset retrievedAssetBeforeMaintenance;
        try {
            retrievedAssetBeforeMaintenance = assetService.getAssetById(retrievedAsset.getAssetId());
        } catch (AssetNotFoundException e) {
            fail("AssetNotFoundException: Asset for maintenance not found after adding: " + e.getMessage());
            return; 
        }
        assertEquals("Initial status should be 'in use'", "in use", retrievedAssetBeforeMaintenance.getStatus());

        boolean maintenancePerformed = assetService.performMaintenance(retrievedAsset.getAssetId(), "2024-05-15", "Test Maintenance", 100.00);
        assertTrue("Maintenance should be performed successfully", maintenancePerformed);
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
