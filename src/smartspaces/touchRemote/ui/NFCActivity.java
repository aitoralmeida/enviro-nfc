package smartspaces.touchRemote.ui;

import smartspaces.touchRemote.R;
import smartspaces.touchRemote.R.layout;
import smartspaces.touchRemote.R.menu;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class NFCActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nfc, menu);
        return true;
    }
}
