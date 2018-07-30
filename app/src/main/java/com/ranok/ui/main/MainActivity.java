package com.ranok.ui.main;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.ranok.BR;
import com.ranok.R;
import com.ranok.RanokApp;
import com.ranok.databinding.ActivityMainBinding;
import com.ranok.nfc.factory.NDEFRecordFactory;
import com.ranok.nfc.nfc_models.BaseRecord;
import com.ranok.rx_bus.RxRFIDEvent;
import com.ranok.ui.base.BaseActivity;
import com.ranok.ui.login.LoginActivity;
import com.ranok.ui.main.main_fragment.MainFragment;

import ranok.mvvm.binding.ViewModelBindingConfig;

public class MainActivity  extends BaseActivity<MainActivityIView, MainActivityVM, ActivityMainBinding>
        implements MainActivityIView {

    private IntentFilter[] intentFiltersArray;
    private NfcAdapter nfcAdapter;
    private PendingIntent pendingIntent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        Bundle arguments = getIntent().getExtras();
        if (!RanokApp.getApp().isLoggedIn()) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            if (arguments != null) loginIntent.putExtras(getIntent());
            startActivity(loginIntent);
            finish();
            return;
        }

        setModelView(this);
        if (getSupportFragmentManager().getFragments().size() == 0) {
            showFragment(new MainFragment());
        }
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            ndef.addDataType("*/*");    /* Handles all MIME based dispatches.
                                       You should specify only the ones that you need. */
        }
        catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("fail", e);
        }
        intentFiltersArray = new IntentFilter[] {ndef, };

        if (arguments != null) {
            getTag(getIntent());
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Log.d("BUNDLE",bundle.toString());
            getTag(intent);
        }
        super.onNewIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        nfcAdapter.enableForegroundDispatch(this, pendingIntent , intentFiltersArray, null);
    }

    public void onPause() {
        super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
    }

    private void getTag(Intent i) {
        if (i == null)
            return;

        try {
            Parcelable[] parcs1 = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            for (Parcelable p : parcs1) {
                NdefMessage msg = (NdefMessage) p;
                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record : records) {
                    BaseRecord result = NDEFRecordFactory.createRecord(record);
                    if (result != null) {
                        RxRFIDEvent.getInstance().sendRFIDData(result.payload);
                        //setNfcData(result);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

/*
        String action = i.getAction();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)) {
            Log.d("Nfc", "Action NDEF Found");
            Parcelable[] parcs = i.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            // List record

            for (Parcelable p : parcs) {
                NdefMessage msg = (NdefMessage) p;
                final int numRec = msg.getRecords().length;
                //  recNumberTxt.setText(String.valueOf(numRec));

                NdefRecord[] records = msg.getRecords();
                for (NdefRecord record : records) {
                    BaseRecord result = NDEFRecordFactory.createRecord(record);
                    if (result != null) {
                        setNfcData(result);
                    }
                }
            }
        }
*/

    }

//    void setNfcData(BaseRecord result) {
//        String tag = result.payload;
//        Log.d("nfcRec", tag);
//        if (tag.equals("Посылки")) {
//            Fragment f = getSupportFragmentManager().findFragmentById(R.id.container);
//            if (f instanceof ScanFragment) {
//                Log.d("NFC", "do nothing");
//            } else {
//                addFragment(new ScanFragment());
//            }
//        } else {
//            Snackbar snackbar = Snackbar
//                    .make(getBinding().getRoot(), "Метка неправильная", Snackbar.LENGTH_LONG);
//            snackbar.show();
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Nullable
    @Override
    public ViewModelBindingConfig getViewModelBindingConfig() {
        return new ViewModelBindingConfig(R.layout.activity_main, BR.mainActivityVM, this);
    }
}
