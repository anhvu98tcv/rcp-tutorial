package rcptutorial.event.admin;

public final class VogellaEventConstants {

	private VogellaEventConstants() {
		// private default constructor for constants class
		// to avoid someone extends the class
	}

	public static final String TOPIC_BASE = "com/vogella/events/";
	public static final String TOPIC_HELP = TOPIC_BASE + "HELP";
	public static final String TOPIC_UPDATE = TOPIC_BASE + "UPDATE";
	public static final String TOPIC_SWITCH = TOPIC_BASE + "SWITCH";
	public static final String TOPIC_ALL = TOPIC_BASE + "*";

	public static final String PROPERTY_KEY_TARGET = "target";

}
