package smartspaces.touchRemote.ui;

import java.io.Serializable;
import java.util.HashMap;

public class WidgetConfiguration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8369157459610297560L;

	public enum Types {BUTTON, LABEL, SLIDE, CONTROLER};
	public enum Positions {TOP, CENTER, BOTTOM};
	public enum Properties {TEXT, ACTION, SCALE_START, SCALE_END, ORIENTATION, ACTION_UP, ACTION_DOWN, ACTION_LEFT, ACTION_RIGHT};
	
	private final Types type;
	private final HashMap<Properties, String> properties;
	private final Positions position;
	
	public WidgetConfiguration(Types type, HashMap<Properties, String> properties,
			Positions position) {
		super();
		this.type = type;
		this.properties = properties;
		this.position = position;
	}

	public Types getType() {
		return type;
	}

	public HashMap<Properties, String> getProperties() {
		return properties;
	}

	public Positions getPosition() {
		return position;
	}
	
}
