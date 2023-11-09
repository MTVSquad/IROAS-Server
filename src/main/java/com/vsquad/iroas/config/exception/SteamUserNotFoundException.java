package com.vsquad.iroas.config.exception;

public class SteamUserNotFoundException extends RuntimeException {
    public SteamUserNotFoundException() {
        super("Steam User Not Found");
    }

    public SteamUserNotFoundException(String message) {
        super(message);
    }
}
