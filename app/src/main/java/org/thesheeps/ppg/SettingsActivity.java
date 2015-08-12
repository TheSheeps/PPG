package org.thesheeps.ppg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.thesheeps.ppg.utils.AES;
import org.thesheeps.ppg.utils.deviceUUID;
import org.thesheeps.ppg.utils.fileHelper;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class SettingsActivity extends Activity {

    private static final String salt = "The$HEPPS#1";
    private static final String LOGTAG = "PPG_SettingsActivity";
    private static int maxWrongPassword = 3;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        assert getActionBar() != null;
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle bundle = getIntent().getExtras();
        password = bundle.getString("PASSWORD");

        File settingsFile = new File(getFilesDir(), "settings");

        String length = "7";
        try {
            length = fileHelper.readFromFile(settingsFile).get(1);
        } catch (IOException e) {
            Log.e(LOGTAG, "Can't access file: ", e);
        }

        EditText editTextLength = (EditText) findViewById(R.id.editTextLength);
        editTextLength.setText(length);
    }

    // Respond to the action bar's Up/Home button
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    public void saveSettings(View view) {

        TextView textViewError = (TextView) findViewById(R.id.textViewError);
        textViewError.setText("");

        if (!checkPasswordMatch())
            return;

        if (saveNewSettings()){
            Toast.makeText(this, R.string.settings_class_saved_successfully, Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private Boolean checkPasswordMatch() {

        EditText editTextNewPass = (EditText) findViewById(R.id.editTextNewPassword);
        String newPass = editTextNewPass.getText().toString();

        EditText editTextConfirmPass = (EditText) findViewById(R.id.editTextConfirmPassword);
        String confirmPass = editTextConfirmPass.getText().toString();

        if (!newPass.equals(confirmPass)){
            TextView textViewError = (TextView) findViewById(R.id.textViewError);
            textViewError.setText(R.string.settings_class_password_match);

            return false;
        }
        return true;
    }

    private Boolean saveNewSettings() {

        File settingsFile = new File(getFilesDir(), "settings");

        String secret = readOldSecret();
        if (secret==null)
            return false;

        EditText editTextNewPass = (EditText) findViewById(R.id.editTextNewPassword);
        String newPass = editTextNewPass.getText().toString();
        if (newPass.isEmpty())
            newPass=password;

        EditText editTextSecret = (EditText) findViewById(R.id.editTextMasterKey);
        String newSecret = editTextSecret.getText().toString();
        if (newSecret.isEmpty())
            newSecret=secret;

        EditText editTextLength = (EditText) findViewById(R.id.editTextLength);
        String length = editTextLength.getText().toString();
        if(length.isEmpty())
            length = "7";

        try {
            fileHelper.writeToFile(settingsFile, AES.encrypt(deviceUUID.getUUID(this) + newPass
                    + salt, newSecret), false);

            fileHelper.writeToFile(settingsFile, "\n" + length, true);
        }
        catch (IOException e) {
            Log.e(LOGTAG, "Can't access file: ", e);
            return false;
        }
        catch (GeneralSecurityException e) {
            Log.e(LOGTAG, "Security Exception: ", e);
            return false;
        }
        return true;
    }

    private String readOldSecret() {

        File settingsFile = new File(getFilesDir(), "settings");

        String secret;
        try {
            secret = AES.decrypt(deviceUUID.getUUID(this) + password + salt, fileHelper.readFromFile
                    (settingsFile).get(0));
        }
        catch (GeneralSecurityException e) {
            Log.e(LOGTAG, "Security Exception: ", e);
            return null;
        }
        catch (IOException e) {
            Log.e(LOGTAG, "Can't access file: ", e);
            return null;
        }
        return secret;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
