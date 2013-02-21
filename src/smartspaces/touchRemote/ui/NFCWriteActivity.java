package smartspaces.touchRemote.ui;

import java.io.IOException;

import smartspaces.touchRemote.R;
import smartspaces.touchRemote.R.layout;
import smartspaces.touchRemote.R.menu;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentFilter.MalformedMimeTypeException;
import android.view.Menu;

public class NFCWriteActivity extends Activity {

	public static final int WRITE_STATE_WRITTEN							= 0;
	public static final int WRITE_STATE_FORMATTED_AND_WRITTEN			= 1;
	public static final int WRITE_STATE_ERROR_UNKNOWN					= 2;
	public static final int WRITE_STATE_ERROR_NDEF_NOT_SUPPORTED		= 3;
	public static final int WRITE_STATE_ERROR_FORMAT_NOT_POSSIBLE		= 4;
	public static final int WRITE_STATE_ERROR_READ_ONLY					= 5;
	public static final int WRITE_STATE_ERROR_CONTENT_TOO_BIG			= 6;
	
	public static String TEST =  "TestTestTest";


	public static String writeStateToString(int writeState){
		switch (writeState) {
			case WRITE_STATE_WRITTEN:					return "OK";
			case WRITE_STATE_FORMATTED_AND_WRITTEN:		return "Formatted & Written";
			case WRITE_STATE_ERROR_NDEF_NOT_SUPPORTED:	return "ERROR: NDEF not supported";
			case WRITE_STATE_ERROR_FORMAT_NOT_POSSIBLE:	return "ERROR: Format not possible";
			case WRITE_STATE_ERROR_READ_ONLY:			return "ERROR: Read-only tag";
			case WRITE_STATE_ERROR_CONTENT_TOO_BIG:		return "ERROR: Content too big";
		}
		return "ERROR: Unknown";
	}
		
	private static final String NFCACTIVITY_DATATYPE = "text/plain";
	
	
    private boolean mResumed = false;
    private boolean mWriteMode = true;
    
    NfcAdapter mNfcAdapter;
    String message = NFCWriteActivity.TEST;

    PendingIntent mNfcPendingIntent;
    IntentFilter[] mWriteTagFilters;
    IntentFilter[] mNdefExchangeFilters;


    public void onTagWriteEnd(String message, int writeState){
    	System.out.println("Status: " + NFCWriteActivity.writeStateToString(writeState));
    	System.out.println("MSG: " + message);
    }
    
	@SuppressWarnings("deprecation")
	public void writeNextDetectedTag(String message) {
		if (mResumed) {
			this.message = message;
            mNfcAdapter.enableForegroundNdefPush(NFCWriteActivity.this, getMessageAsNdef());
			disableNdefExchangeMode();
			enableTagWriteMode(); 
        }
	}

	private NdefMessage getMessageAsNdef() {
		byte[] textBytes = message.getBytes();
		NdefRecord textRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
				NFCACTIVITY_DATATYPE.getBytes(), new byte[] {}, textBytes);
		return new NdefMessage(new NdefRecord[] { textRecord });
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
        
        // Intent filters for writing to a tag
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] { tagDetected };
    }
    
    @Override
    protected void onResume() {
    	System.out.println("Entering onResume");
        
    	super.onResume();
    	this.mResumed = true;
    	if (mWriteMode)
    	{
    		writeNextDetectedTag(message);
    	}
    }
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onPause() {
        super.onPause();
        mResumed = false;
        mNfcAdapter.disableForegroundNdefPush(this);
    }
    
    @Override
	protected void onNewIntent(Intent intent) {
     
        // Tag writing mode
        if (mWriteMode && NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag detectedTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            onTagWriteEnd(message, writeTag(getMessageAsNdef(), detectedTag));
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

    

    @SuppressWarnings("deprecation")
	private void disableNdefExchangeMode() {
        mNfcAdapter.disableForegroundNdefPush(this);
        mNfcAdapter.disableForegroundDispatch(this);
    }

    private void enableTagWriteMode() {
        mWriteMode = true;
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mWriteTagFilters = new IntentFilter[] {
            tagDetected
        };
        mNfcAdapter.enableForegroundDispatch(this, mNfcPendingIntent, mWriteTagFilters, null);
    }

    private int writeTag(NdefMessage message, Tag tag) {
    	System.out.println("writing tag");
        int size = message.toByteArray().length;

        try {
            Ndef ndef = Ndef.get(tag);
            if (ndef != null) {
                ndef.connect();

                if (!ndef.isWritable()) {
                    return WRITE_STATE_ERROR_READ_ONLY;
                }
                if (ndef.getMaxSize() < size) {
                    return WRITE_STATE_ERROR_CONTENT_TOO_BIG;
                }

                ndef.writeNdefMessage(message);
                return WRITE_STATE_WRITTEN;
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if (format != null) {
                    try {
                        format.connect();
                        format.format(message);
                        return WRITE_STATE_FORMATTED_AND_WRITTEN;
                    } catch (IOException e) {
                        return WRITE_STATE_ERROR_FORMAT_NOT_POSSIBLE;
                    }
                } else {
                    return WRITE_STATE_ERROR_NDEF_NOT_SUPPORTED;
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to write tag");
            System.out.println(e.getMessage());
        }

        return WRITE_STATE_ERROR_UNKNOWN;
    }
}
    
