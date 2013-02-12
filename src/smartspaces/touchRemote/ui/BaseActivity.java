package smartspaces.touchRemote.ui;

import smartspaces.touchRemote.R;
import smartspaces.touchRemote.TouchActivity;
import smartspaces.touchRemote.ui.WidgetConfiguration.Properties;
import smartspaces.touchRemote.ui.WidgetConfiguration.Types;
import smartspaces.touchRemote.ui.widgets.AmbientButton;
import smartspaces.touchRemote.ui.widgets.AmbientTextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;

public class BaseActivity extends Activity {
	
	private ListView baseView;
	private InterfaceConfiguration config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        this.baseView = (ListView)this.findViewById(R.id.baseView);
        this.config = (InterfaceConfiguration) getIntent().getSerializableExtra(TouchActivity.INTERFACE_CONFIG);
        this.createInterface();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_base, menu);
        return true;
    }
    
    
    public void createInterface(){
    	    	
    	for (WidgetConfiguration widget : this.config.getWidgets()) {
			if (widget.getType() == Types.LABEL) {
				AmbientTextView label = new AmbientTextView(this);
				label.setText(widget.getProperties().get(Properties.TEXT));
				label.setAction(widget.getProperties().get(Properties.ACTION));
				this.baseView.addView(label);
			} else if (widget.getType() == Types.BUTTON) {
				AmbientButton button = new AmbientButton(this);
				button.setText(widget.getProperties().get(Properties.TEXT));
				button.setAction(widget.getProperties().get(Properties.ACTION));
				this.baseView.addView(button);
			}
		}    	
    }
    
    public void performAction(View view) {
    	if (view instanceof AmbientButton) {
			AmbientButton button = (AmbientButton) view;
			this.sendAction(button.getAction());
		}    	
    }
    
    private void sendAction(String action){
    	System.out.println(this.config.getEndPoint() + ":" + action);
    }
}
