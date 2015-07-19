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
    private static int maxWrongPassword = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File settings = new File(getFilesDir().toString() + "/settings");
        if (!settings.exists())
            firstRunInitialization();

        final EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    try {
                        PPG();
                    } catch (IOException e) {
                        Log.e("PPG_MainActivity", "Can't access file: ", e);
                    }
                }
                return false;
            }
        });
    }

    private void firstRunInitialization() {

        File settingsFile = new File(getFilesDir(), "settings");
        try {
            fileHelper.writeToFile(settingsFile, AES.encrypt(deviceUUID.getUUID(this) + "1234" + salt,
                    "@b4J&Afv0G%2x$SddS1D6h3@yepDo&ja"), false); // Default Master Secret.

            fileHelper.writeToFile(settingsFile, "\n7", true); // Default length of output password(7)
        }
        catch (IOException e) {
            Log.e("PPG_MainActivity", "Can't access file: ", e);
        }
        catch (GeneralSecurityException e) {
            Log.e("PPG_MainActivity", "Security Exception: ", e);
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
            Log.e("PPG_MainActivity","Security Exception: ", e);

            maxWrongPassword--;
            if(maxWrongPassword == 0)
                finish();
            else{
                TextView textViewError = (TextView) findViewById(R.id.textViewError);
                textViewError.setText("Oops!");

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
    protected void onRestart() {
        super.onRestart();

        EditText editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPass.setText("");

        TextView textViewError = (TextView) findViewById(R.id.textViewError);
        textViewError.setText("");
    }
}