package com.matthewcasperson.events;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class EmailNotification implements EventNotification {
    private final String to;

    public EmailNotification(final String to) {
        this.to = to;
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
                                    .withCharset("UTF-8").withData("WebDriver Test Results")))
                    .withSource("admin@matthewcasperson.com");
            client.sendEmail(request);
        } catch (final Exception ex) {
            System.out.println("The email was not sent. Error message: "
                    + ex.getMessage());
        }
    }
}
