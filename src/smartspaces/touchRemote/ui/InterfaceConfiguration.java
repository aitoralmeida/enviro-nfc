package smartspaces.touchRemote.ui;

import java.io.Serializable;
import java.util.Vector;

public class InterfaceConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2964316163338894398L;

	//REST endpoint for the actions
	private final String endPoint;
	
	//title of the interface
	private final String title;
	
	private final Vector<WidgetConfiguration> widgets;

	public InterfaceConfiguration(String endPoint, String title,
			Vector<WidgetConfiguration> widgets) {
		super();
		this.endPoint = endPoint;
		this.title = title;
		this.widgets = widgets;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public String getTitle() {
		return title;
	}

	public Vector<WidgetConfiguration> getWidgets() {
		return widgets;
	}
}
