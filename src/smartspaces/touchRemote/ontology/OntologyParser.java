//Quick and dirty ad-hoc turtle parser, not proud of it :-/
//Mend it as soon as posible

package smartspaces.touchRemote.ontology;

import smartspaces.touchRemote.ui.generation.InterfaceConfiguration;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration.Positions;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration.Properties;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration.Types;

public class OntologyParser {

	public InterfaceConfiguration parseOntology(String ont){
		String[] lines = ont.split("\n");
		InterfaceConfiguration config = new InterfaceConfiguration();
		WidgetConfiguration widget = null;
	
		for (int i = 0; i < lines.length; i++) {
			String line = lines[i].trim();
			if (!line.startsWith("@") || (!line.startsWith("<")) || (!line.startsWith("default"))){
				if (line.startsWith("rdf:type") && !line.contains("owl:Ontology")) {
					if ((widget != null) && (widget.getType() != Types.UNKOWN)) {
						config.addWidget(widget);
					}
					
					Types type = this.parseType(line);
					widget = new WidgetConfiguration();
					widget.setType(type);					
				} else if(line.startsWith("ambInt")){
					this.parseProperty(config, widget, line);
				} 
			}
		}
		
		return config;
	}
	
	public Types parseType(String line){
		if (line.contains("Label")) {
			return Types.LABEL;
		} else if (line.contains("Button")) {
			return Types.BUTTON;
		} else if (line.contains("Slide")) {
			return Types.SLIDE;
		} else if (line.contains("Controler")) {
			return Types.CONTROLER;
		} else{
			return Types.UNKOWN;
		}
	}
	
	
	public void parseProperty(InterfaceConfiguration config, WidgetConfiguration widget, String line){
		String value = "";
		if (line.contains("\"")) {
			value = this.parsePropertyValue(line);
		}
		
		if (line.contains("ambInt:action")) {
			widget.putProperty(Properties.ACTION, value);
		} else if (line.contains("ambInt:position")) {
			widget.setPosition(Positions.valueOf(value));
		} else if (line.contains("ambInt:text")) {
			widget.putProperty(Properties.TEXT, value);
		} else if (line.contains("ambInt:limitEnd")) {
			widget.putProperty(Properties.LIMIT_END, value);
		} else if (line.contains("ambInt:limitStart")) {
			widget.putProperty(Properties.LIMIT_START, value);
		} else if (line.contains("ambInt:actionUp")) {
			widget.putProperty(Properties.ACTION_UP, value);
		} else if (line.contains("ambInt:actionDown")) {
			widget.putProperty(Properties.ACTION_DOWN, value);
		} else if (line.contains("ambInt:actionLeft")) {
			widget.putProperty(Properties.ACTION_LEFT, value);
		} else if (line.contains("ambInt:actionRight")) {
			widget.putProperty(Properties.ACTION_RIGHT, value);
		} else if (line.contains("ambInt:endpoint")) {
			config.setEndPoint(value);
		} else if (line.contains("ambInt:title")) {
			config.setTitle(value);
		}			
	}
	
	private String parsePropertyValue(String line){
		String[] tokens = line.split("\"");
		return tokens[1];
	}
	
		
}
