package com.spidertechsoft.safalmemployee;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddSelfVisitedLeadToSalesActivity extends AppCompatActivity {

    SafalmEmployee se;
    private String TAG = AddSelfVisitedLeadToSalesActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private static String url = "";
    HashMap<String, String> contacts;

    ArrayList<HashMap<String, String>> productList;

    EditText editASVTSproductname,editASVTSproductquantity,editASVTSproductprice,editASVTSdate,editASVTStime,editASVTSdetail,editASVTSproductamount;

    TextView txtASVTSname,txtASVTSaddress,txtASVTSmobile,txtASVTSemail,txtASVTScompanyname;

    ImageView imgProductList;

    Double rate,quantity;

    String pid,pname, vlid, name, address, mobile,email,cmpName,date,time,prate,pamount,pquantity="0",decision,emp_id,pprice,pdetail;

    private static final String TAG_PID = "pid";
    private static final String TAG_PNAME = "pname";
    private static final String TAG_PPRICE = "pprice";
    private static final String TAG_PDETAIL = "pdetail";

    int mDate, mMonth, mYear, mMinute, mHour;

    String date_time, dt, tm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_self_visited_lead_to_sales_activity);

         txtASVTSname = findViewById(R.id.txtASVTSname);
         txtASVTSaddress = findViewById(R.id.txtASVTSaddress);
         txtASVTSmobile = findViewById(R.id.txtASVTSmobile);
         txtASVTSemail = findViewById(R.id.txtASVTSemail);
         txtASVTScompanyname = findViewById(R.id.txtASVTScompanyname);

         editASVTSproductname = findViewById(R.id.editASVTSproductname);
         editASVTSproductquantity = findViewById(R.id.editASVTSproductquantity);
         editASVTSproductprice = findViewById(R.id.editASVTSproductprice);
         editASVTSdate = findViewById(R.id.editASVTSdate);
         editASVTStime = findViewById(R.id.editASVTStime);
         editASVTSdetail = findViewById(R.id.editASVTSdetail);
         editASVTSproductamount = findViewById(R.id.editASVTSproductamount);
         imgProductList=findViewById(R.id.imgProductList);

        Button btnASVTSaddtosales = findViewById(R.id.btnASVTSaddtosales);

        se = (SafalmEmployee) getApplicationContext();
        //emp_id = "1";
        emp_id = se.getempId();
       // Toast.makeText(AddSelfVisitedLeadToSalesActivity.this, "id="+emp_id, Toast.LENGTH_SHORT).show();

        Intent ii = getIntent();
        Bundle b = ii.getExtras();

        if (b != null) {
            vlid = (String) b.get("vlid");
            pid = (String) b.get("pid");
            name = (String) b.get("name");
            address = (String) b.get("address");
            mobile = (String) b.get("mobile");
            email=(String) b.get("email");
            cmpName=(String)b.get("cname");
            date=(String)b.get("date");
            time=(String)b.get("time");
            pname=(String)b.get("pname");
            prate=(String)b.get("prate");
            pamount=(String)b.get("pamount");
           // pquantity=(String)b.get("pquantity");
            decision=(String)b.get("decision");
            //txtSLDname.setText(lead_id);
        }

        txtASVTSname.setText(name);
        txtASVTSaddress.setText(address);
        txtASVTSmobile.setText(mobile);
        txtASVTSemail.setText(email);
        txtASVTScompanyname.setText(cmpName);
        editASVTSproductname.setText(pname);
        editASVTSdate.setText(date);
        editASVTStime.setText(time);
        editASVTSproductprice.setText(prate);
        editASVTSproductamount.setText(pamount);
        editASVTSproductquantity.setText(pquantity);
        editASVTSdetail.setText(decision);



        btnASVTSaddtosales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(AddSelfVisitedLeadToSalesActivity.this, "id"+vlid, Toast.LENGTH_SHORT).show();
                dt = editASVTSdate.getText().toString();
                tm = editASVTStime.getText().toString();
                date_time = dt + " " + tm;
               // pquantity = editASVTSproductquantity.getText().toString();
                pprice = editASVTSproductprice.getText().toString();
                pamount = editASVTSproductamount.getText().toString();
                pquantity=editASVTSproductquantity.getText().toString();
                try {
                    date_time = java.net.URLEncoder.encode(date_time, "UTF-8");
                    pdetail = java.net.URLEncoder.encode(editASVTSdetail.getText().toString().trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if ((!date_time.equals("") && !pquantity.equals("") && !pprice.equals("") && !pdetail.equals(""))) {

                    url = "http://10.0.2.2/safalm/add_self_visited_to_sales.php?p_id=" + pid + "&p_quantity=" + quantity + "&p_rate=" + pprice + "&decision=" + pdetail + "&date_time=" + date_time + "&v_lead_id=" + vlid + "&emp_id=" + emp_id + "&p_amount=" + pamount;
                    //new AddSelfLeadToVisitedAndSalesActivity.Employee().execute();

                    //url = "http://10.0.2.2/safalm/add_self_to_sales.php?p_id=" + pid + "&p_quantity=" + quantity + "&p_rate=" + price + "&date_time=" + date_time + "&lead_vid=" + lid + "&emp_id=" + emp_id + "&p_amount=" + amount;
                    new AddSelfVisitedLeadToSalesActivity.Employee().execute();
                } else {
                    Toast.makeText(AddSelfVisitedLeadToSalesActivity.this, "All fields are required!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        editASVTSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDate = c.get(Calendar.DATE);
                mHour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSelfVisitedLeadToSalesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        editASVTSdate.setText((year + "-" + (((month + 1) < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth)));

                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
        editASVTStime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR);
                mMinute = c1.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSelfVisitedLeadToSalesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // editASLTVSdatetime.setText(date_time + (" "+hourOfDay + ":" + minute));
                        editASVTStime.setText((((hourOfDay < 10) ? "0" + hourOfDay : hourOfDay) + ":" + ((minute < 10) ? "0" + minute : minute) + ":00"));
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });


        editASVTSproductquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editASVTSproductprice.getText().toString().equals("") && !editASVTSproductquantity.getText().toString().equals("")) {


                    rate = Double.parseDouble(editASVTSproductprice.getText().toString().trim());
                    quantity = Double.parseDouble(editASVTSproductquantity.getText().toString().trim());

                    pamount = String.valueOf(rate * quantity);
                    editASVTSproductamount.setText(pamount);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editASVTSproductprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editASVTSproductprice.getText().toString().equals("") && !editASVTSproductquantity.getText().toString().equals("")) {


                    rate = Double.parseDouble(editASVTSproductprice.getText().toString().trim());
                    quantity = Double.parseDouble(editASVTSproductquantity.getText().toString().trim());

                    pamount = String.valueOf(rate * quantity);
                    editASVTSproductamount.setText(pamount);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        imgProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddSelfVisitedLeadToSalesActivity.this, ProductListActivity.class);
                startActivityForResult(i, 1);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data.getExtras().containsKey(TAG_PID)) {

            pid = data.getExtras().getString(TAG_PID);
            pname = data.getExtras().getString(TAG_PNAME);
            prate = data.getExtras().getString(TAG_PPRICE);

            editASVTSproductname.setText(pname);
            editASVTSproductprice.setText(prate);

        }
    }

    private class Employee extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddSelfVisitedLeadToSalesActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);


            if (contacts.get("success").toString().equals("1")) {

                editASVTSdate.setText("");
                editASVTSdetail.setText("");
                editASVTSproductamount.setText("");
                editASVTSproductname.setText("");
                editASVTSproductprice.setText("");
                editASVTSproductquantity.setText("");
                editASVTStime.setText("");

                Toast.makeText(AddSelfVisitedLeadToSalesActivity.this, "Lead added successfully...", Toast.LENGTH_SHORT).show();
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

                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray();
//                    Log.e("SAFALM2=", contacts.toString());
                    //JSONArray contacts = new JSONArray(jsonStr);

                    // looping through All Contacts
                    //for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);

                    String success = jsonObj.getString("success");
                    String message = jsonObj.getString("message");
                    Log.e("SAFALM3=", success);
                    Log.e("SAFALM4=", message);
                    //Toast.makeText(getApplicationContext(), success + message, Toast.LENGTH_SHORT).show();
//                        String email = c.getString("email");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");

                    // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");

                    // tmp hash map for single contact
                    contacts = new HashMap<>();

                    // adding each child node to HashMap key => value
                    contacts.put("success", success);
                    contacts.put("message", message);
//                        contact.put("email", email);
//                        contact.put("mobile", mobile);

                    // adding contact to contact list
                    //contactList.add(contact);
                    // }
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
