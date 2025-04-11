package com.java.asset.dao;

import com.java.asset.model.Asset;
import com.java.asset.model.Reservation;

import java.util.List;

public interface AssetManagementService {

    boolean addAsset(Asset asset);
    boolean updateAsset(Asset asset);
    boolean deleteAsset(int assetId);
    Asset getAssetById(int assetId) throws com.java.asset.MyExceptions.AssetNotFoundException;
    List<Asset> getAllAssets();
    boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws com.java.asset.MyExceptions.AssetNotFoundException;
    boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws com.java.asset.MyExceptions.AssetNotFoundException;
    boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) throws com.java.asset.MyExceptions.AssetNotFoundException;
    boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws com.java.asset.MyExceptions.AssetNotFoundException;
    boolean withdrawReservation(int reservationId) throws com.java.asset.MyExceptions.AssetNotFoundException;
    List<Asset> getAssetsByType(String assetType);
    List<Asset> getAssetsByStatus(String assetStatus);
    List<Asset> getAssetsAvailableForAllocation();
    List<Reservation> getAssetReservations(int assetId);
}