package org.thesheeps.ppg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.thesheeps.ppg.utils.AES;
import org.thesheeps.ppg.utils.deviceUUID;
import org.thesheeps.ppg.utils.fileHelper;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends Activity {

    private static final String salt = "The$HEPPS#1";
    private static final String LOGTAG = "PPG_MainActivity";
    private static int maxWrongPassword = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate settings file with default values.
        File settings = new File(getFilesDir().toString() + "/settings");
        if (!settings.exists())
            firstRunInitialization();

        final EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) { // On "Enter" key pressed.

                    try {
                        PPG();
                    } catch (IOException e) {
                        Log.e(LOGTAG, "Can't access file: ", e);
                    }
                }
                return false;
            }
        });
    }

    private void firstRunInitialization() {

        File settingsFile = new File(getFilesDir(), "settings");
        try {
            // Default login password: 0000.
            // Default Master Secret: @b4J&Afv0G%2x$SddS1D6h3@yepDo&ja.
            fileHelper.writeToFile(settingsFile, AES.encrypt(deviceUUID.getUUID(this) + "0000" +
                            salt,
                    "@b4J&Afv0G%2x$SddS1D6h3@yepDo&ja"), false);

            // Default length of output password: 7.
            fileHelper.writeToFile(settingsFile, "\n7", true);
        }
        catch (IOException e) {
            Log.e(LOGTAG, "Can't access file: ", e);
        }
        catch (GeneralSecurityException e) {
            Log.e(LOGTAG, "Security Exception: ", e);
        }
    }

    private void PPG() throws IOException {

        File settingsFile = new File(getFilesDir(), "settings");

        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        String password = editTextPass.getText().toString();

        String secret = null;
        try {
            secret = AES.decrypt(deviceUUID.getUUID(this) + password + salt, fileHelper.readFromFile
                    (settingsFile).get(0));
        } catch (GeneralSecurityException e) {
            Log.e(LOGTAG, "Security Exception: ", e);

            maxWrongPassword--;
            if(maxWrongPassword == 0)
                finish();
            else{
                TextView textViewError = (TextView) findViewById(R.id.textViewError);
                textViewError.setText(R.string.main_oops);

                return;
            }
        }

        String length = fileHelper.readFromFile(settingsFile).get(1);

        if (secret!=null) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("LENGTH", length);
            intent.putExtra("SECRET", secret);
            intent.putExtra("PASSWORD", password);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass.setText("");

        TextView textViewError = (TextView) findViewById(R.id.textViewError);
        textViewError.setText("");
    }
}