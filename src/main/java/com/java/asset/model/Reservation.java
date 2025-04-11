package com.java.asset.model;

public class Reservation {
    private int reservationId;
    private int assetId; // Foreign Key to assets table
    private int employeeId; // Foreign Key to employees table
    private String reservationDate;
    private String startDate;
    private String endDate;
    private String status;

    public Reservation() {
    }

    public Reservation(int reservationId, int assetId, int employeeId, String reservationDate, String startDate, String endDate, String status) {
        this.reservationId = reservationId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.reservationDate = reservationDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    // Getters and Setters
    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
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

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", assetId=" + assetId +
                ", employeeId=" + employeeId +
                ", reservationDate='" + reservationDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}