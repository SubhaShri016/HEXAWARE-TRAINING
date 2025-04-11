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
        // First, create an asset in a state suitable for maintenance (e.g., 'in use')
        String uniqueSerialNumber = "JUNIT-SERIAL-" + UUID.randomUUID().toString(); // Generate unique serial number
        Asset assetForMaintenance = new Asset(0, "Maintenance Test Asset", "Monitor", uniqueSerialNumber, "2024-01-01", "Test Location", "in use", 1); // Use unique serial number
        assetService.addAsset(assetForMaintenance);
        Asset retrievedAsset = findAssetBySerialNumber(uniqueSerialNumber); // Retrieve using helper with unique serial
        assertNotNull("Retrieved asset should not be null", retrievedAsset);
        Asset retrievedAssetBeforeMaintenance;
        try {
            retrievedAssetBeforeMaintenance = assetService.getAssetById(retrievedAsset.getAssetId());
        } catch (AssetNotFoundException e) {
            fail("AssetNotFoundException: Asset for maintenance not found after adding: " + e.getMessage());
            return; // Exit test if asset not found
        }
        assertEquals("Initial status should be 'in use'", "in use", retrievedAssetBeforeMaintenance.getStatus());

        boolean maintenancePerformed = assetService.performMaintenance(retrievedAsset.getAssetId(), "2024-05-15", "Test Maintenance", 100.00);
        assertTrue("Maintenance should be performed successfully", maintenancePerformed);
    }

    // Helper method to find Asset by Serial Number (for use with original Impl)
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