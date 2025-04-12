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
       
        assetService = new AssetManagementServiceImpl();
        
       
        Asset testAsset = new Asset();
        testAsset.setName("Test Laptop");
        testAsset.setType("Laptop");
        testAsset.setSerialNumber("SN-TEST-12345");
        testAsset.setPurchaseDate("2023-01-01");
        testAsset.setLocation("Test Location");
      
        testAsset.setStatus("available");
        testAsset.setOwnerId(1); 
        
       
        assetService.addAsset(testAsset);
        
       
        List<Asset> assets = assetService.getAllAssets();
        for (Asset asset : assets) {
            if ("SN-TEST-12345".equals(asset.getSerialNumber())) {
                testAssetId = asset.getAssetId();
                break;
            }
        }
        
        
        testEmployeeId = 1;
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        
        try {
            assetService.deleteAsset(testAssetId);
        } catch (Exception e) {
           
        }
    }

    @Before
    public void setUp() throws Exception {
      
    }

    @After
    public void tearDown() throws Exception {
        
        try {
            if (testReservationId > 0) {
                assetService.withdrawReservation(testReservationId);
                testReservationId = 0;
            }
        } catch (Exception e) {
           
        }
    }

    @Test
    public void testReserveAssetSuccessfully() throws AssetNotFoundException {
       
        String reservationDate = "2024-04-11";
        String startDate = "2024-04-15";
        String endDate = "2024-04-20";
        
        boolean result = assetService.reserveAsset(testAssetId, testEmployeeId, reservationDate, startDate, endDate);
        
       
        assertTrue("Reservation should be successful", result);
        
        
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        assertFalse("Reservations list should not be empty", reservations.isEmpty());
        
        
        if (!reservations.isEmpty()) {
            testReservationId = reservations.get(0).getReservationId();
            
           
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
        
        int invalidAssetId = 99999; 
        assetService.reserveAsset(invalidAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
    }

    @Test
    public void testWithdrawReservationSuccessfully() throws AssetNotFoundException {
        
        assetService.reserveAsset(testAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
        
        
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        
        
        if (!reservations.isEmpty()) {
            int reservationId = reservations.get(0).getReservationId();
            
           
            boolean result = assetService.withdrawReservation(reservationId);
            
           
            assertTrue("Withdrawing reservation should be successful", result);
            
           
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
       
        int nonExistentReservationId = 99999; 
        boolean result = assetService.withdrawReservation(nonExistentReservationId);
        
        
        assertFalse("Withdrawing non-existent reservation should fail", result);
    }
    
    @Test
    public void testGetAssetReservations() throws AssetNotFoundException {
       
        assetService.reserveAsset(testAssetId, testEmployeeId, "2024-04-11", "2024-04-15", "2024-04-20");
        
       
        List<Reservation> reservations = assetService.getAssetReservations(testAssetId);
        
       
        assertNotNull("Reservations list should not be null", reservations);
        assertFalse("Reservations list should not be empty", reservations.isEmpty());
        
        
        if (!reservations.isEmpty()) {
            testReservationId = reservations.get(0).getReservationId();
            
           
            Reservation reservation = reservations.get(0);
            assertEquals("Asset ID should match", testAssetId, reservation.getAssetId());
            assertEquals("Employee ID should match", testEmployeeId, reservation.getEmployeeId());
        }
    }
}
