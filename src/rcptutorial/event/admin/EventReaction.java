package rcptutorial.event.admin;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

@Component(property = EventConstants.EVENT_TOPIC + "=" + VogellaEventConstants.TOPIC_UPDATE)

public class EventReaction implements EventHandler {

	@Override
	public void handleEvent(Event event) {
		System.out.println("I will now update: " + event.getProperty(VogellaEventConstants.PROPERTY_KEY_TARGET));
	}
}
	