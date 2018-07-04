package com.ljd.hackajob.phonebook.model.exceptions;

public class Messages {
    private Messages() {
        // not meant to be instantiated
    }

    public static final String BUNDLE_NAME = Messages.class.getName();

    // PBE0001: No Contact found with id '%s'
    public static final String PBE0001 = "PBE0001";
}
