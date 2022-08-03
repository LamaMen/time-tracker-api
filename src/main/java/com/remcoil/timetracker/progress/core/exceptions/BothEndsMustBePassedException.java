package com.remcoil.timetracker.progress.core.exceptions;

public class BothEndsMustBePassedException extends RuntimeException {
    public BothEndsMustBePassedException() {
        super("It is necessary to transmit either both ends of the period or neither");
    }
}
