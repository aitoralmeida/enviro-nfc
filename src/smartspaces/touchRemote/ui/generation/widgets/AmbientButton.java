package smartspaces.touchRemote.ui.generation.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class AmbientButton extends Button {

	private String action;
	
	public AmbientButton(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public AmbientButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public AmbientButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
