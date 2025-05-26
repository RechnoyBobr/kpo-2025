package hse.infrastructure.storage;

import hse.domain.events.AnimalMovedEvent;
import hse.domain.events.FeedingTimeEvent;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 * Event storage.
 */
public class EventsStorage {
    @Getter
    private static final List<AnimalMovedEvent> moveEvents = new ArrayList<>(0);
    @Getter
    private static final List<FeedingTimeEvent> feedEvents = new ArrayList<>(0);

    /**
     * Add move event.
     *
     * @param moveEvent Move event
     */
    public static void addMoveEvent(final AnimalMovedEvent moveEvent) {
        moveEvents.add(moveEvent);
    }

    /**
     * Add feed event.
     *
     * @param feedEvent Feed event
     */
    public static void addFeedEvent(final FeedingTimeEvent feedEvent) {
        feedEvents.add(feedEvent);
    }
}
