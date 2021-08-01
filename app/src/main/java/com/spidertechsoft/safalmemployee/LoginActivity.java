package com.spidertechsoft.safalmemployee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    EditText editLoginUsername, editLoginPassword;
    SafalmEmployee se;
    Button btnLogin;
    private String TAG = LoginActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private static String url = "";
    HashMap<String, String> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editLoginUsername = findViewById(R.id.editLoginUsername);
        editLoginPassword = findViewById(R.id.editLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        se= (SafalmEmployee) getApplicationContext();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String contact=editLoginUsername.getText().toString().trim();
                String password=editLoginPassword.getText().toString().trim();

                if (!contact.equals("") && !password.equals("")) {

                    url = "http://10.0.2.2/safalm/emp_login.php?contact=" + contact + "&password=" + password;

                    new Employee().execute();

                }else {
                    Toast.makeText(LoginActivity.this, "All field are required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

   private class Employee extends AsyncTask<Void, Void, Void>{

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           pDialog = new ProgressDialog(LoginActivity.this);
           pDialog.setMessage("Please wait...");
           pDialog.setCancelable(false);
           pDialog.show();
       }

       @Override
       protected void onPostExecute(Void s) {
           super.onPostExecute(s);
           //Log.e("SAFALM5=", contacts.get("success").toString() + contacts.get("message").toString());
          // Toast.makeText(getApplicationContext(), contacts.get("success").toString() + contacts.get("message").toString(), Toast.LENGTH_SHORT).show();
           //String empId=contacts.get("message").toString();
           se.setempId(contacts.get("message").toString());
        //   String eid=se.getempId().toString();
       //    Log.e("Safalam 6:",eid);



            if (contacts.get("success").toString().equals("1")){
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
           pDialog.dismiss();
       }

        @Override
        protected Void doInBackground(Void... voids) {

            Log.e("hardik", url);
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    Log.e("SAFALM1=", jsonObj.toString());

                    String success = jsonObj.getString("success");
                    String message = jsonObj.getString("message");
                    Log.e("SAFALM3=", success);
                    Log.e("SAFALM4=", message);
                    //Toast.makeText(getApplicationContext(), success + message, Toast.LENGTH_SHORT).show();

                    // tmp hash map for single contact
                    contacts = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contacts.put("success", success);
                    contacts.put("message", message);

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }
            return null;
        }
    }
}
