package smartspaces.touchRemote;

import java.util.HashMap;
import java.util.Vector;

import smartspaces.touchRemote.ui.BaseActivity;
import smartspaces.touchRemote.ui.InterfaceConfiguration;
import smartspaces.touchRemote.ui.WidgetConfiguration;
import smartspaces.touchRemote.ui.WidgetConfiguration.Positions;
import smartspaces.touchRemote.ui.WidgetConfiguration.Properties;
import smartspaces.touchRemote.ui.WidgetConfiguration.Types;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class TouchActivity extends Activity {
	
	private InterfaceConfiguration config;
	public final static String INTERFACE_CONFIG = "smartspaces.touchRemote.ui.InterfaceConfiguration";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_touch, menu);
        return true;
    }
    
    public void showInterface(View view) {
    	
    	//JUST TESTING
    	final Vector<WidgetConfiguration> widgets = new Vector<WidgetConfiguration>();
    	
    	//label
    	final HashMap<Properties, String> propertiesLabel = new HashMap<Properties, String>();
    	propertiesLabel.put(Properties.TEXT, "Test label");
    	final WidgetConfiguration labelTop = new WidgetConfiguration(Types.LABEL, propertiesLabel, Positions.TOP);
    	widgets.add(labelTop);
    	
    	//button
    	final HashMap<Properties, String> propertiesButton = new HashMap<Properties, String>();
    	propertiesButton.put(Properties.TEXT, "Test button");
    	propertiesButton.put(Properties.ACTION, "action button");
    	final WidgetConfiguration buttonMiddle = new WidgetConfiguration(Types.BUTTON, propertiesButton, Positions.CENTER);
    	widgets.add(buttonMiddle);
    	
		this.config = new InterfaceConfiguration("test", "test view", widgets);
		//END TESTING

		Intent intent = new Intent(this, BaseActivity.class);
		intent.putExtra(INTERFACE_CONFIG, this.config);
		startActivity(intent);

    }
    
}
