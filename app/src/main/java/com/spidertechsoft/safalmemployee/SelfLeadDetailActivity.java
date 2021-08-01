package com.spidertechsoft.safalmemployee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class SelfLeadDetailActivity extends AppCompatActivity {

    SafalmEmployee se;
    private String TAG = SelfLeadDetailActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private static String url = "";
    int flag = 0;
    String id, name, address, contact, lead_id, email, compname, date_time, date, time;

    TextView txtSLDname, txtSLDaddress, txtSLDmobile, txtSLDemail, txtSLDcompanyname;

    AsyncTask at;
    LinearLayout imgLeadLocator;

    private static final String TAG_SLID = "id";
    private static final String TAG_SLNAME = "name";
    private static final String TAG_SLADDRESS = "address";
    private static final String TAG_SLCONTACT = "mobile";

    Geocoder geoCoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_lead_detail_activity);

        txtSLDname = findViewById(R.id.txtSLDname);
        txtSLDaddress = findViewById(R.id.txtSLDaddress);
        txtSLDmobile = findViewById(R.id.txtSLDmobile);
        txtSLDemail = findViewById(R.id.txtSLDemail);
        txtSLDcompanyname = findViewById(R.id.txtSLDcompanyname);

        imgLeadLocator=findViewById(R.id.imgLeadLocator);

        ImageView icSLDedit = findViewById(R.id.icSLDedit);
        ImageView icSLDaddvisit = findViewById(R.id.icSLDaddvisit);
        ImageView icSLDaddsales = findViewById(R.id.icSLDaddsales);
        ImageView icSLDaddtask = findViewById(R.id.icSLDaddtask);
        ImageView icSLDdelete = findViewById(R.id.icSLDdelete);

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        if (b != null) {
            lead_id = (String) b.get("lead_id");
            //txtSLDname.setText(lead_id);
        }

        url = "http://10.0.2.2/safalm/self_lead_detail.php?id=" + lead_id;
        at = new Employee().execute();

//        if (address != null && !address.isEmpty()) {
//            try {
//                List<Address> addressList = geoCoder.getFromLocationName(address, 1);
//                if (addressList != null && addressList.size() > 0) {
//                    double lat = addressList.get(0).getLatitude();
//                    double lng = addressList.get(0).getLongitude();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            } // end catch
//        } // end if

        imgLeadLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri mapUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);

            }
        });

        icSLDedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(SelfLeadDetailActivity.this, EditSelfLeadActivity.class);
                i.putExtra("id", lead_id);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("mobile", contact);
                i.putExtra("email", email);
                i.putExtra("cmpName", compname);
                i.putExtra("date", date);
                i.putExtra("time", time);
                startActivity(i);


            }
        });

        icSLDaddvisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelfLeadDetailActivity.this, AddSelfLeadToVisitedAndSalesActivity.class);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("mobile", contact);
                i.putExtra("email", email);
                i.putExtra("cmpName", compname);
                startActivity(i);

            }
        });

        icSLDaddsales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelfLeadDetailActivity.this, AddSelfLeadToSalesActivity.class);
                i.putExtra("id", id);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("mobile", contact);
                i.putExtra("email", email);
                i.putExtra("cmpName", compname);
                startActivity(i);

            }
        });

        icSLDaddtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SelfLeadDetailActivity.this, LeadAddToTaskActivity.class);
                i.putExtra("slid", id);
                i.putExtra("name", name);
                i.putExtra("address", address);
                i.putExtra("mobile", contact);
                i.putExtra("email", email);
                i.putExtra("cmpName", compname);
                startActivity(i);


            }
        });

        icSLDdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url = "http://10.0.2.2/safalm/delete_self_lead.php?id=" + lead_id;

                new Employee().execute();

                Intent i = new Intent(SelfLeadDetailActivity.this, SelfLeadListActivity.class);
                startActivity(i);
                finish();

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (flag > 0) {
            url = "http://10.0.2.2/safalm/self_lead_detail.php?id=" + lead_id;
            at.cancel(true);
            at = new Employee().execute();
        }
    }

    private class Employee extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SelfLeadDetailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            //Toast.makeText(SelfLeadDetailActivity.this, "date=" + date_time, Toast.LENGTH_SHORT).show();

            StringTokenizer tokens = new StringTokenizer(date_time, " ");
            date = tokens.nextToken();// this will contain "Fruit"
            time = tokens.nextToken();

            //Log.e("SAFALM5=", contacts.get("success").toString() + contacts.get("message").toString());
            // Toast.makeText(getApplicationContext(), contacts.get("success").toString() + contacts.get("message").toString(), Toast.LENGTH_SHORT).show();
            //String empId=contacts.get("message").toString();
            //se.setempId(contacts.get("message").toString());
            //   String eid=se.getempId().toString();
            //    Log.e("Safalam 6:",eid);
            txtSLDname.setText(name);
            txtSLDaddress.setText(address);
            txtSLDmobile.setText(contact);
            txtSLDemail.setText(email);
            txtSLDcompanyname.setText(compname);

            //Toast.makeText(SelfLeadDetailActivity.this, "Name="+name, Toast.LENGTH_SHORT).show();
            //  if (contacts.get("success").toString().equals("1")) {
//                Intent i = new Intent(AddSelfLeadActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
            //       Toast.makeText(SelfLeadListActivity.this, "Lead added successfully...", Toast.LENGTH_SHORT).show();
            //   }

            pDialog.dismiss();
            if (flag == 0) {
                flag++;
            }
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

                    // Getting JSON Array node
                    JSONArray self_lead = jsonObj.getJSONArray("message");
                    Log.e("SAFALM2=", self_lead.toString());
                    // JSONArray contacts = new JSONArray(jsonStr);

                    // looping through All Contacts
                    // for (int i = 0; i < self_lead.length(); i++) {
                    JSONObject c = self_lead.getJSONObject(0);

                    String success = jsonObj.getString("success");
                    String message = jsonObj.getString("message");
                    // Log.e("SAFALM3=", success);
                    //Log.e("SAFALM4=", message);
                    //Toast.makeText(getApplicationContext(), success + message, Toast.LENGTH_SHORT).show();
                    id = c.getString(TAG_SLID);
                    name = c.getString(TAG_SLNAME);
                    address = c.getString(TAG_SLADDRESS);
                    contact = c.getString(TAG_SLCONTACT);
                    email = c.getString("email");
                    compname = c.getString("cname");
                    date_time = c.getString("date");

                    // HashMap<String, String> map = new HashMap<>();

//                        map.put(TAG_SLID, id);
//                        map.put(TAG_SLNAME, name);
//                        map.put(TAG_SLADDRESS, address);
//                        map.put(TAG_SLCONTACT, contact);
//
//                        leadList.add(map);

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
