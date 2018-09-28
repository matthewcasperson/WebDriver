package com.matthewcasperson.events;

import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;

public class CloudWatchEventNotification implements EventNotification {
    @Override
    public void sendNotification(final String id, final boolean success, final String results) {
        try {
            final AmazonCloudWatchEvents cwe =
                    AmazonCloudWatchEventsClientBuilder.defaultClient();

            final String EVENT_DETAILS =
                    "{ \"id\": \"" + id + "\", \"success\": " + success + ", \"results\": {\"text\": \"" + results + "\" }}";

            final PutEventsRequestEntry request_entry = new PutEventsRequestEntry()
                    .withDetail(EVENT_DETAILS)
                    .withDetailType("testResult")
                    .withSource("cucumberLambda");

            final PutEventsRequest request = new PutEventsRequest()
                    .withEntries(request_entry);

            final PutEventsResult response = cwe.putEvents(request);
        } catch (final Exception ex) {
            System.out.println("The cloudwatch event was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
