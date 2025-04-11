package com.java.asset.MyExceptions;

public class AssetNotMaintainException extends Exception {

    private static final long serialVersionUID = 1L;

    public AssetNotMaintainException(String message) {
        super(message);
    }
}