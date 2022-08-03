package com.remcoil.timetracker.progress.core.exceptions;

public class StartDateAfterEndException extends RuntimeException {
    public StartDateAfterEndException() {
        super("Attempt to create a period whose start date is after the end");
    }
}
