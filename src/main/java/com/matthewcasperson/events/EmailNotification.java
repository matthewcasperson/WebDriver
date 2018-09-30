package com.matthewcasperson.events;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class EmailNotification implements EventNotification {
    private final String to;
    private final String from;

    public EmailNotification(final String to, final String from) {
        this.to = to;
        this.from = from;
    }

    @Override
    public void sendNotification(final String id, final boolean success, final String results) {
        try {
            final AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_EAST_1).build();
            final SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination().withToAddresses(to))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withText(new Content()
                                            .withCharset("UTF-8").withData(results)))
                            .withSubject(new Content()
                                    .withCharset("UTF-8")
                                    .withData((success ? "SUCCESS" : "FAILURE") + " WebDriver Test Results")))
                    .withSource(from);
            client.sendEmail(request);
        } catch (final Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
