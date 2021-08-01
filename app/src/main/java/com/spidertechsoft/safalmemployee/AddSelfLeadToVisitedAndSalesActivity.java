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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AddSelfLeadToVisitedAndSalesActivity extends AppCompatActivity {

    SafalmEmployee se;
    private String TAG = AddSelfLeadToVisitedAndSalesActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private static String url = "";
    HashMap<String, String> contacts;

    ArrayList<HashMap<String, String>> productList;

    private static final String TAG_PID = "pid";
    private static final String TAG_PNAME = "pname";
    private static final String TAG_PPRICE = "pprice";
    private static final String TAG_PDETAIL = "pdetail";

    EditText edtProductName, editASLTVSproductprice, editASLTVSproductquantity, editASLTVStime, editASLTVSdate, editASLTVSdecision, editASLTVSproductamount;
    ImageView imgProductList;

    TextView txtASLTVSemail, txtASLTVScompanyname;

    String pid, pname, pquantity, pmobile, emp_id, lid, lname, laddress, lmobile, pprice, pdetail, lemail, lcname, amount;
    double price, quantity;
    int mDate, mMonth, mYear, mMinute, mHour;
    String date_time, dt, tm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_self_lead_to_visited_and_sales_activity);

        TextView txtASLTVSname = findViewById(R.id.txtASLTVSname);
        TextView txtASLTVSaddress = findViewById(R.id.txtASLTVSaddress);
        TextView txtASLTVSmobile = findViewById(R.id.txtASLTVSmobile);
        imgProductList = findViewById(R.id.imgProductList);
        productList = new ArrayList<>();

        edtProductName = findViewById(R.id.edtProductName);
        editASLTVSproductprice = findViewById(R.id.editASLTVSproductprice);
        editASLTVSproductquantity = findViewById(R.id.editASLTVSproductquantity);
        editASLTVSdate = findViewById(R.id.editASLTVSdate);
        editASLTVStime = findViewById(R.id.editASLTVStime);
        editASLTVSdecision = findViewById(R.id.editASLTVSdecision);
        editASLTVSproductamount = findViewById(R.id.editASLTVSproductamount);
        txtASLTVScompanyname = findViewById(R.id.txtASLTVScompanyname);
        txtASLTVSemail = findViewById(R.id.txtASLTVSemail);

        Button btnASLTVSvisited = findViewById(R.id.btnASLTVSvisited);
       // Button btnASLTVSsales = findViewById(R.id.btnASLTVSsales);

        imgProductList.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (imgProductList.hasFocus()) {
                    Intent i = new Intent(AddSelfLeadToVisitedAndSalesActivity.this, ProductListActivity.class);
                    startActivityForResult(i, 1);
                }
            }
        });

        editASLTVSdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDate = c.get(Calendar.DATE);
                mHour = c.get(Calendar.HOUR);
                mMinute = c.get(Calendar.MINUTE);


                DatePickerDialog datePickerDialog = new DatePickerDialog(AddSelfLeadToVisitedAndSalesActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String mnth, day;
//                        Date date=new Date(year,month,dayOfMonth);
//                        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
//                        String d=sdf.format(date);
//                        editASLTVSdate.setText(d);
//                        if (dayOfMonth<10){
//                             day="0"+dayOfMonth;
//                        }
//                        if (month<10){
//                            mnth="0"+month;
//                        }
                        editASLTVSdate.setText((year + "-" + (((month + 1) < 10) ? "0" + (month + 1) : (month + 1)) + "-" + ((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth)));
//                        timePicker();

                        // editASLTVSdatetime.setText(date_time);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();

            }
        });

        editASLTVStime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c1 = Calendar.getInstance();
                mHour = c1.get(Calendar.HOUR);
                mMinute = c1.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AddSelfLeadToVisitedAndSalesActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // editASLTVSdatetime.setText(date_time + (" "+hourOfDay + ":" + minute));
                        editASLTVStime.setText((((hourOfDay < 10) ? "0" + hourOfDay : hourOfDay) + ":" + ((minute < 10) ? "0" + minute : minute) + ":00"));
                    }
                }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });
        // date_time=dt+" "+tm;

        Intent ii = getIntent();
        Bundle b = ii.getExtras();
        if (b != null) {
            lid = (String) b.get("id");
            lname = (String) b.get("name");
            laddress = (String) b.get("address");
            lmobile = (String) b.get("mobile");
            lemail = (String) b.get("email");
            lcname = (String) b.get("cmpName");
            //txtSLDname.setText(lead_id);
        }

        txtASLTVSname.setText(lname);
        txtASLTVSaddress.setText(laddress);
        txtASLTVSmobile.setText(lmobile);
        txtASLTVSemail.setText(lemail);
        txtASLTVScompanyname.setText(lcname);


        se = (SafalmEmployee) getApplicationContext();
        //emp_id = "1";
        emp_id = se.getempId();

        btnASLTVSvisited.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "date"+date_time, Toast.LENGTH_SHORT).show();

                dt = editASLTVSdate.getText().toString();
                tm = editASLTVStime.getText().toString();
                date_time = dt + " " + tm;
                pquantity = editASLTVSproductquantity.getText().toString();
                pprice = editASLTVSproductprice.getText().toString();
                try {
                    date_time = java.net.URLEncoder.encode(date_time, "UTF-8");
                    pdetail = java.net.URLEncoder.encode(editASLTVSdecision.getText().toString().trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                if ((!date_time.equals("") && !pquantity.equals("") && !pprice.equals("") && !pdetail.equals(""))) {
                    url = "http://10.0.2.2/safalm/add_self_to_visited.php?p_id=" + pid + "&p_quantity=" + quantity + "&p_rate=" + price + "&decision=" + pdetail + "&date_time=" + date_time + "&lead_sid=" + lid + "&emp_id=" + emp_id + "&p_amount=" + amount;
                    new AddSelfLeadToVisitedAndSalesActivity.Employee().execute();

                } else {
                    Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "All fields are required!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        btnASLTVSsales.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                dt = editASLTVSdate.getText().toString();
//                tm = editASLTVStime.getText().toString();
//                date_time = dt + " " + tm;
//                pquantity = editASLTVSproductquantity.getText().toString();
//                pprice = editASLTVSproductprice.getText().toString();
//                try {
//                    date_time = java.net.URLEncoder.encode(date_time, "UTF-8");
//                    pdetail = java.net.URLEncoder.encode(editASLTVSdecision.getText().toString().trim(), "UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//
//                if ((!date_time.equals("") && !pquantity.equals("") && !pprice.equals("") && !pdetail.equals(""))) {
//
//                    url = "http://10.0.2.2/safalm/add_self_to_sales.php?p_id=" + pid + "&p_quantity=" + quantity + "&p_rate=" + price + "&decision=" + pdetail + "&date_time=" + date_time + "&lead_sid=" + lid + "&emp_id=" + emp_id + "&p_amount=" + amount;
//                    //new AddSelfLeadToVisitedAndSalesActivity.Employee().execute();
//
//                    //url = "http://10.0.2.2/safalm/add_self_to_sales.php?p_id=" + pid + "&p_quantity=" + quantity + "&p_rate=" + price + "&date_time=" + date_time + "&lead_vid=" + lid + "&emp_id=" + emp_id + "&p_amount=" + amount;
//                    new AddSelfLeadToVisitedAndSalesActivity.Employee().execute();
//                } else {
//                    Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "All fields are required!!!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });

        imgProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddSelfLeadToVisitedAndSalesActivity.this, ProductListActivity.class);
                startActivityForResult(i, 1);

            }
        });

        editASLTVSproductquantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editASLTVSproductprice.getText().toString().equals("") && !editASLTVSproductquantity.getText().toString().equals("")) {


                    price = Double.parseDouble(editASLTVSproductprice.getText().toString().trim());
                    quantity = Double.parseDouble(editASLTVSproductquantity.getText().toString().trim());

                    amount = String.valueOf(price * quantity);
                    editASLTVSproductamount.setText(amount);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editASLTVSproductprice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!editASLTVSproductprice.getText().toString().equals("") && !editASLTVSproductquantity.getText().toString().equals("")) {

                    price = Double.parseDouble(editASLTVSproductprice.getText().toString().trim());
                    quantity = Double.parseDouble(editASLTVSproductquantity.getText().toString().trim());

                    amount = String.valueOf(price * quantity);
                    editASLTVSproductamount.setText(amount);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//        {
//            price = Integer.parseInt(editASLTVSproductprice.getText().toString().trim());
//            quantity = Integer.parseInt(editASLTVSproductquantity.getText().toString().trim());
//
//            amount = String.valueOf(price * quantity);
//            editASLTVSproductamount.setText(amount);
//        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data.getExtras().containsKey(TAG_PID)) {

            pid = data.getExtras().getString(TAG_PID);
            pname = data.getExtras().getString(TAG_PNAME);
            pprice = data.getExtras().getString(TAG_PPRICE);
            pdetail = data.getExtras().getString(TAG_PDETAIL);

            edtProductName.setText(pname);
            editASLTVSproductprice.setText(pprice);
            //Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "id=" + pid, Toast.LENGTH_SHORT).show();

            //Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "p_id=" + p_id, Toast.LENGTH_SHORT).show();
        }
    }

    private class Employee extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(AddSelfLeadToVisitedAndSalesActivity.this);
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
            //se.setempId(contacts.get("message").toString());
            //   String eid=se.getempId().toString();
            //    Log.e("Safalam 6:",eid);


            if (contacts.get("success").toString().equals("1")) {
//                Intent i = new Intent(AddSelfLeadActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
                edtProductName.setText("");
                editASLTVSproductprice.setText("");
                editASLTVSdecision.setText("");
                editASLTVSproductamount.setText("");
                editASLTVSproductquantity.setText("");
                editASLTVSdate.setText("");
                editASLTVStime.setText("");

                Toast.makeText(AddSelfLeadToVisitedAndSalesActivity.this, "Lead added successfully...", Toast.LENGTH_SHORT).show();

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
