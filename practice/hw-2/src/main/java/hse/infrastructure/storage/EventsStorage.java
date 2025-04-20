package hse.infrastructure.storage;

import hse.domain.events.AnimalMovedEvent;
import hse.domain.events.FeedingTimeEvent;
import java.util.ArrayList;
import java.util.List;

public class EventsStorage {
    private static final List<AnimalMovedEvent> moveEvents = new ArrayList<>(0);
    private static final List<FeedingTimeEvent> feedEvents = new ArrayList<>(0);

    public static void addMoveEvent(final AnimalMovedEvent moveEvent) {
        moveEvents.add(moveEvent);
    }

    public static void addFeedEvent(final FeedingTimeEvent feedEvent) {
        feedEvents.add(feedEvent);
    }
}
