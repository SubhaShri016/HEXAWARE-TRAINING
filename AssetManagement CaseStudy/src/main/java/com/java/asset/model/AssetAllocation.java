package com.java.asset.model;

public class AssetAllocation {
    private int allocationId;
    private int assetId; 
    private int employeeId; 
    private String allocationDate;
    private String returnDate;

    public AssetAllocation() {
    }

    public AssetAllocation(int allocationId, int assetId, int employeeId, String allocationDate, String returnDate) {
        this.allocationId = allocationId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.allocationDate = allocationDate;
        this.returnDate = returnDate;
    }

    // Getters and Setters
    public int getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(int allocationId) {
        this.allocationId = allocationId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAllocationDate() {
        return allocationDate;
    }

    public void setAllocationDate(String allocationDate) {
        this.allocationDate = allocationDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "AssetAllocation{" +
                "allocationId=" + allocationId +
                ", assetId=" + assetId +
                ", employeeId=" + employeeId +
                ", allocationDate='" + allocationDate + '\'' +
                ", returnDate='" + returnDate + '\'' +
                '}';
    }
}
