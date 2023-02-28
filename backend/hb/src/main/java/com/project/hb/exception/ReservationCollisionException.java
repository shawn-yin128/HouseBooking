package com.project.hb.exception;

public class ReservationCollisionException extends RuntimeException {
    public ReservationCollisionException(String message) {
        super(message);
    }
}