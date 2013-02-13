package smartspaces.touchRemote.ui;

import java.io.Serializable;
import java.util.Vector;

public class InterfaceConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2964316163338894398L;

	//REST endpoint for the actions
	private String endPoint;
	
	//title of the interface
	private String title;
	
	private Vector<WidgetConfiguration> widgets;
	
	public InterfaceConfiguration(){
		super();
		widgets = new Vector<WidgetConfiguration>();
	}

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setWidgets(Vector<WidgetConfiguration> widgets) {
		this.widgets = widgets;
	}
	
	public void addWidget(WidgetConfiguration widget){
		this.widgets.add(widget);
	}
}
