package com.zhuravlov.repairagency.model.exception;

public class RepairFormNotFoundException extends RuntimeException {
    public RepairFormNotFoundException() {
        super("Repair form not found");
    }

    public RepairFormNotFoundException(String message) {
        super(message);
    }
}
