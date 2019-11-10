import com.github.gatoke.offers.domain.shared.Event
import com.github.gatoke.offers.domain.shared.EventPublisher

class TestEventPublisher implements EventPublisher {

    @Override
    void publishEvent(Event event) {
        // do nothing
    }
}
