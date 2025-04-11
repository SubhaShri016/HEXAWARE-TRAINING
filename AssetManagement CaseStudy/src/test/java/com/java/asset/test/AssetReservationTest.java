package com.java.asset.test;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.java.asset.dao.*;
import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.model.Asset;
import com.java.asset.model.Reservation;

public class AssetReservationTest {

    private static AssetManagementServiceImpl assetService;
    private static int testAssetId;
    private static int testEmployeeId;
    private static int testReservationId;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // Initialize the service
        assetService = new AssetManagementServiceImpl();
        
        // Create a test asset for reservation tests
        Asset testAsset = new Asset();
        testAsset.setName("Test Laptop");
        testAsset.setType("Laptop");
        testAsset.setSerialNumber("SN-TEST-12345");
        testAsset.setPurchaseDate("2023-01-01");
        testAsset.setLocation("Test Location");
        // Fix for status column issue - check your database to see valid status values
        // Common values might be 'A' for available, 'I' for in-use, etc.
        testAsset.setStatus("available"); // Using a single character which should fit any status column
        testAsset.setOwnerId(1); // Assuming owner ID 1 exists
        
        // Add the asset to the database
        assetService.addAsset(testAsset);
        
        // Get all assets and find our test asset
        List<Asset> assets = assetService.getAllAssets();
        for (Asset asset : assets) {
            if ("SN-TEST-12345".equals(asset.getSerialNumber())) {
                testAssetId = asset.getAssetId();
                break;
            }
        }
        
        // Test employee ID (assuming this ID exists in your system)
        testEmployeeId = 1;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // Clean up - delete the test asset
        try {
            assetService.deleteAsset(testAssetId);
        } catch (Exception e) {
            // Ignore if already deleted
        }
    }

    @Before
    public void setUp() throws Exception {
        // Any setup needed before each test
    }

    @After
    public void tearDown() throws Exception {
        // Clean up after each test
        // Try to withdraw any reservations made during tests
        try {
            if (testReservationId > 0) {
                assetService.withdrawReservation(testReservationId);
                testReservationId = 0;
            }
        } catch (Exception e) {
            // Ignore cleanup errors
        }
    }

    @Test
    public void testReserveAssetSuccessfully() throws AssetNotFoundException {
        // Test reserving the asset
        String reservationDate = "2024-04-11";
        String startDate = "2024-04-15";
        String endDate = "2024-04-20";
        
        boolean result = assetService.reserveAsset(testAssetId, testEmployeeId, reservationDate, startDate, endDate);
        
        // Assert that reservation was successful
        assertTrue("Reservation should be successful", result);
        
        // Verify the reservation was created by checking the reservations for the asset
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        assertFalse("Reservations list should not be empty", reservations.isEmpty());
        
        // Save reservation ID for cleanup
        if (!reservations.isEmpty()) {
            testReservationId = reservations.get(0).getReservationId();
            
            // Verify reservation details
            Reservation reservation = reservations.get(0);
            assertEquals("Asset ID should match", testAssetId, reservation.getAssetId());
            assertEquals("Employee ID should match", testEmployeeId, reservation.getEmployeeId());
            assertEquals("Reservation date should match", reservationDate, reservation.getReservationDate());
            assertEquals("Start date should match", startDate, reservation.getStartDate());
            assertEquals("End date should match", endDate, reservation.getEndDate());
            assertEquals("Status should be pending", "pending", reservation.getStatus());
        }
    }

    @Test(expected = AssetNotFoundException.class)
    public void testReserveAssetWithInvalidAssetId() throws AssetNotFoundException {
        // Test reserving with invalid asset ID (should throw exception)
        int invalidAssetId = 99999; // Assuming this ID doesn't exist
        assetService.reserveAsset(invalidAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
    }

    @Test
    public void testWithdrawReservationSuccessfully() throws AssetNotFoundException {
        // First create a reservation
        assetService.reserveAsset(testAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
        
        // Get the reservation ID
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        
        // Only proceed if we got reservations
        if (!reservations.isEmpty()) {
            int reservationId = reservations.get(0).getReservationId();
            
            // Test withdrawing the reservation
            boolean result = assetService.withdrawReservation(reservationId);
            
            // Assert that withdrawal was successful
            assertTrue("Withdrawing reservation should be successful", result);
            
            // Verify the reservation was removed
            reservations = assetService.getAssetReservations(testAssetId);
            boolean reservationFound = false;
            for (Reservation r : reservations) {
                if (r.getReservationId() == reservationId) {
                    reservationFound = true;
                    break;
                }
            }
            assertFalse("Reservation should no longer exist", reservationFound);
        } else {
            fail("Could not create reservation for withdrawal test");
        }
    }

    @Test
    public void testWithdrawNonExistentReservation() throws AssetNotFoundException {
        // Test withdrawing a non-existent reservation
        int nonExistentReservationId = 99999; // Assuming this ID doesn't exist
        boolean result = assetService.withdrawReservation(nonExistentReservationId);
        
        // Assert that withdrawal fails
        assertFalse("Withdrawing non-existent reservation should fail", result);
    }
    
    @Test
    public void testGetAssetReservations() throws AssetNotFoundException {
        // First create a reservation
        assetService.reserveAsset(testAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
        
        // Test getting reservations for the asset
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        
        // Assert that we got reservations
        assertNotNull("Reservations list should not be null", reservations);
        assertFalse("Reservations list should not be empty", reservations.isEmpty());
        
        // Save reservation ID for cleanup
        if (!reservations.isEmpty()) {
            testReservationId = reservations.get(0).getReservationId();
            
            // Verify reservation details
            Reservation reservation = reservations.get(0);
            assertEquals("Asset ID should match", testAssetId, reservation.getAssetId());
            assertEquals("Employee ID should match", testEmployeeId, reservation.getEmployeeId());
        }
    }
}