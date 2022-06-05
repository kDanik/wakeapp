package de.wakeapp.service.properties;

public class PropertiesNotFoundException extends RuntimeException {
    public PropertiesNotFoundException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public PropertiesNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
