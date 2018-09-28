package com.matthewcasperson.events;

public interface EventNotification {
    void sendNotification(final String id, final boolean success, final String results);
}
