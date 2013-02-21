package smartspaces.touchRemote.ui;

import smartspaces.touchRemote.R;
import smartspaces.touchRemote.R.layout;
import smartspaces.touchRemote.R.menu;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.view.Menu;
import java.io.IOException;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;




//<uses-permission android:name="android.permission.NFC"/>
//<activity android:name=".gui.NFCActivity">
//    <intent-filter>
//        <action android:name="android.nfc.action.NDEF_DISCOVERED" />
//        <category android:name="android.intent.category.DEFAULT" />
//        <data android:mimeType="*/*" />
//    </intent-filter>
//</activity>
@TargetApi(10)
public class NFCActivity extends Activity
{


	private static final String NFCACTIVITY_DATATYPE = "text/plain";
    
    NfcAdapter mNfcAdapter;

    PendingIntent mNfcPendingIntent;
    IntentFilter[] mNdefExchangeFilters;


    public void onTagReadEnd(NdefMessage[] messages){ 
    	for (NdefMessage ndefMessage : messages) {
			System.out.println("msg: " + new String(ndefMessage.getRecords()[0].getPayload()));
		}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        // Handle all of our received NFC intents in this activity.
        mNfcPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        // Intent filters for reading a note from a tag or exchanging over p2p.
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndefDetected.addDataType(NFCACTIVITY_DATATYPE);
        } catch (MalformedMimeTypeException e) { }
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };
        
    }
    
    @Override
    protected void onResume() {
    	System.out.println("Entering onResume");
        super.onResume();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            NdefMessage[] messages = getNdefMessagesFromIntent(getIntent());
            onTagReadEnd(messages);
            setIntent(new Intent()); // Consume this intent.
        }
        //enableNdefExchangeMode();
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// NDEF exchange mode
    	if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
    		NdefMessage[] msgs = getNdefMessagesFromIntent(intent);
    		onTagReadEnd(msgs);
    	}
    }

	NdefMessage[] getNdefMessagesFromIntent(Intent intent) {
        // Parse the intent
        NdefMessage[] msgs = null;
        String action = intent.getAction();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)
                || NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMsgs != null) {
                msgs = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    msgs[i] = (NdefMessage) rawMsgs[i];
                }
            } else {
                // Unknown tag type
                byte[] empty = new byte[] {};
                NdefRecord record = new NdefRecord(NdefRecord.TNF_UNKNOWN, empty, empty, empty);
                NdefMessage msg = new NdefMessage(new NdefRecord[] {
                    record
                });
                msgs = new NdefMessage[] {
                    msg
                };
            }
        } else {
            finish();
        }
        return msgs;
    }

	/*private void enableNdefExchangeMode() {
       mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mNdefExchangeFilters, null);
    }*/
   
    
}