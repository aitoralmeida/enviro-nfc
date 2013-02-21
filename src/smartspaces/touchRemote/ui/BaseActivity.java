package smartspaces.touchRemote.ui;

import smartspaces.touchRemote.R;
import smartspaces.touchRemote.TouchActivity;
import smartspaces.touchRemote.ui.generation.InterfaceConfiguration;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration.Properties;
import smartspaces.touchRemote.ui.generation.WidgetConfiguration.Types;
import smartspaces.touchRemote.ui.generation.widgets.AmbientButton;
import smartspaces.touchRemote.ui.generation.widgets.AmbientTextView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public class BaseActivity extends Activity implements OnClickListener {
	
	private LinearLayout baseLayout;
	private InterfaceConfiguration config;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.baseLayout = new LinearLayout(this);
        this.baseLayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));        
        setContentView(this.baseLayout);
        
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
				label.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				label.setText(widget.getProperties().get(Properties.TEXT));
				label.setAction(widget.getProperties().get(Properties.ACTION));
				this.baseLayout.addView(label);
			} else if (widget.getType() == Types.BUTTON) {
				AmbientButton button = new AmbientButton(this);
				button.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				button.setText(widget.getProperties().get(Properties.TEXT));
				button.setAction(widget.getProperties().get(Properties.ACTION));
				button.setOnClickListener(this);
				this.baseLayout.addView(button);
			}
		}  	
    }
       
    private void sendAction(String action){
    	System.out.println(this.config.getEndPoint() + ":" + action);
    }

	public void onClick(View view) {
    	if (view instanceof AmbientButton) {
			AmbientButton button = (AmbientButton) view;
			this.sendAction(button.getAction());
		}    		
	}
}
