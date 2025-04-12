package com.java.asset.dao;

import com.java.asset.MyExceptions.AssetNotFoundException;
import com.java.asset.model.Asset;
import com.java.asset.model.Reservation;
import com.java.asset.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AssetManagementServiceImpl implements AssetManagementService {

    @Override
    public boolean addAsset(Asset asset) {
        String query = "INSERT INTO assets (name, type, serial_number, purchase_date, location, status, owner_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, asset.getName());
            pstmt.setString(2, asset.getType());
            pstmt.setString(3, asset.getSerialNumber());
            pstmt.setString(4, asset.getPurchaseDate());
            pstmt.setString(5, asset.getLocation());
            pstmt.setString(6, asset.getStatus());
            pstmt.setInt(7, asset.getOwnerId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateAsset(Asset asset) {
        String query = "UPDATE assets SET name = ?, type = ?, serial_number = ?, purchase_date = ?, location = ?, status = ?, owner_id = ? WHERE asset_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, asset.getName());
            pstmt.setString(2, asset.getType());
            pstmt.setString(3, asset.getSerialNumber());
            pstmt.setString(4, asset.getPurchaseDate());
            pstmt.setString(5, asset.getLocation());
            pstmt.setString(6, asset.getStatus());
            pstmt.setInt(7, asset.getOwnerId());
            pstmt.setInt(8, asset.getAssetId());

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteAsset(int assetId) {
        String query = "DELETE FROM assets WHERE asset_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Asset getAssetById(int assetId) throws AssetNotFoundException {
        String query = "SELECT * FROM assets WHERE asset_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToAsset(rs);
            } else {
                throw new AssetNotFoundException("Asset not found with ID: " + assetId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null; 
        }
    }

    @Override
    public List<Asset> getAllAssets() {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws AssetNotFoundException {
        if (getAssetById(assetId) == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId + ". Allocation failed.");
        }
        String query = "INSERT INTO asset_allocations (asset_id, employee_id, allocation_date) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            pstmt.setInt(2, employeeId);
            pstmt.setString(3, allocationDate);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws AssetNotFoundException {
        if (getAssetById(assetId) == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId + ". Deallocation failed.");
        }
        String query = "UPDATE asset_allocations SET return_date = ? WHERE asset_id = ? AND employee_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, returnDate);
            pstmt.setInt(2, assetId);
            pstmt.setInt(3, employeeId);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) throws AssetNotFoundException {
        if (getAssetById(assetId) == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId + ". Maintenance record creation failed.");
        }
        String query = "INSERT INTO maintenance_records (asset_id, maintenance_date, description, cost) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            pstmt.setString(2, maintenanceDate);
            pstmt.setString(3, description);
            pstmt.setDouble(4, cost);

            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws AssetNotFoundException {
        if (getAssetById(assetId) == null) {
            throw new AssetNotFoundException("Asset not found with ID: " + assetId + ". Reservation failed.");
        }
        String query = "INSERT INTO reservations (asset_id, employee_id, reservation_date, start_date, end_date, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            pstmt.setInt(2, employeeId);
            pstmt.setString(3, reservationDate);
            pstmt.setString(4, startDate); 
            System.out.println("DEBUG reserveAsset: Inserting startDate: " + startDate); 
            pstmt.setString(5, endDate);
            pstmt.setString(6, "pending"); 

            int rows = pstmt.executeUpdate();
            System.out.println("DEBUG reserveAsset: executeUpdate() returned rows: " + rows); 
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean withdrawReservation(int reservationId) throws AssetNotFoundException { 
        String query = "DELETE FROM reservations WHERE reservation_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, reservationId);
            int rows = pstmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Asset> getAssetsByType(String assetType) {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets WHERE type = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, assetType);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public List<Asset> getAssetsByStatus(String assetStatus) {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets WHERE status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, assetStatus);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

     @Override
    public List<Asset> getAssetsAvailableForAllocation() {
        List<Asset> assets = new ArrayList<>();
        String query = "SELECT * FROM assets WHERE status = 'available'"; 
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                assets.add(mapResultSetToAsset(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assets;
    }

    @Override
    public List<Reservation> getAssetReservations(int assetId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE asset_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, assetId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                reservations.add(mapResultSetToReservation(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    private Asset mapResultSetToAsset(ResultSet rs) throws SQLException {
        Asset asset = new Asset();
        asset.setAssetId(rs.getInt("asset_id"));
        asset.setName(rs.getString("name"));
        asset.setType(rs.getString("type"));
        asset.setSerialNumber(rs.getString("serial_number"));
        asset.setPurchaseDate(rs.getString("purchase_date"));
        asset.setLocation(rs.getString("location"));
        asset.setStatus(rs.getString("status"));
        asset.setOwnerId(rs.getInt("owner_id"));
        return asset;
    }

    private Reservation mapResultSetToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationId(rs.getInt("reservation_id"));
        reservation.setAssetId(rs.getInt("asset_id"));
        reservation.setEmployeeId(rs.getInt("employee_id"));
        reservation.setReservationDate(rs.getString("reservation_date"));
        reservation.setStartDate(rs.getString("start_date"));
        reservation.setEndDate(rs.getString("end_date"));
        reservation.setStatus(rs.getString("status"));
        return reservation;
    }
}
