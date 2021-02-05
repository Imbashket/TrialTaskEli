package by.kravchenko.elinexttest;

/**
 * @author Pavel Kravchenko
 */
public class EventServiceImpl implements EventService {
    private EventDao eventDao;

    @Inject
    public EventServiceImpl(EventDao eventDao) {
        this.eventDao = eventDao;
    }
}

