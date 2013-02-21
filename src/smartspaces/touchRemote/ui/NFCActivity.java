package smartspaces.touchRemote.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;

public class NFCActivity extends Activity
{

   
    NfcAdapter mNfcAdapter;

    public void onTagReadEnd(NdefMessage[] messages){ 
    	for (NdefMessage ndefMessage : messages) {
			System.out.println("msg: " + new String(ndefMessage.getRecords()[0].getPayload()));
		}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);       
        
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
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
    
}