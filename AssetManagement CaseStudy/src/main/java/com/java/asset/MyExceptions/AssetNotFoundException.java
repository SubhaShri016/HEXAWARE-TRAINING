package com.java.asset.MyExceptions;

public class AssetNotFoundException extends Exception {

    private static final long serialVersionUID = 1L; // Added serialVersionUID

    public AssetNotFoundException(String message) {
        super(message);
    }
}