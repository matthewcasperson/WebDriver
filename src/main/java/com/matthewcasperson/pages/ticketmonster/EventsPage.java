package com.matthewcasperson.pages.ticketmonster;

import com.matthewcasperson.AutomatedBrowser;
import com.matthewcasperson.pages.BasePage;

public class EventsPage extends BasePage {
    public EventsPage(final AutomatedBrowser automatedBrowser) {
        super(automatedBrowser);
    }

    public VenuePage selectEvent(final String category, final String event) {
        automatedBrowser.clickElement(category, WAIT_TIME);
        automatedBrowser.clickElement(event, WAIT_TIME);
        return new VenuePage(automatedBrowser);
    }
}