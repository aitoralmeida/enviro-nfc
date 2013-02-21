package smartspaces.touchRemote.ui.generation;

import java.io.Serializable;
import java.util.HashMap;

public class WidgetConfiguration implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8369157459610297560L;

	public enum Types {BUTTON, LABEL, SLIDE, CONTROLER, UNKOWN};
	public enum Positions {TOP, CENTER, BOTTOM};
	public enum Properties {TEXT, ACTION, LIMIT_START, LIMIT_END, ORIENTATION, ACTION_UP, ACTION_DOWN, ACTION_LEFT, ACTION_RIGHT};
	
	private Types type;
	private HashMap<Properties, String> properties;
	private Positions position;
	
	public WidgetConfiguration(){
		super();
		properties = new HashMap<Properties, String>();
	}
	
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setType(Types type) {
		this.type = type;
	}

	public void setProperties(HashMap<Properties, String> properties) {
		this.properties = properties;
	}

	public void setPosition(Positions position) {
		this.position = position;
	}
	
	public void putProperty(Properties propType, String value){
		this.properties.put(propType, value);
	}
	
	
	
}
