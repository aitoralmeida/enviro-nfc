package smartspaces.touchRemote.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class AmbientTextView extends TextView {
	
	private String action;

	public AmbientTextView(Context context) {
		super(context);
	}

	public AmbientTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AmbientTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
