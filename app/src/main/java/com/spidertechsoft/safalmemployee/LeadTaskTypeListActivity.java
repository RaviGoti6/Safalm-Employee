package com.spidertechsoft.safalmemployee;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LeadTaskTypeListActivity extends AppCompatActivity {

    TextView taskTypeWhatsapp,taskTypeSMS,taskTypeMail,taskTypeCall;

    LinearLayout llWhatsapp,llSMS,llCall,llMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_task_type_list_activity);

        setTitle("Select Task Type");

        taskTypeWhatsapp=findViewById(R.id.taskTypeWhatsapp);
        taskTypeCall=findViewById(R.id.taskTypeCall);
        taskTypeMail=findViewById(R.id.taskTypeMail);
        taskTypeSMS=findViewById(R.id.taskTypeSMS);

        llCall=findViewById(R.id.llCall);
        llMail=findViewById(R.id.llMail);
        llSMS=findViewById(R.id.llSMS);
        llWhatsapp=findViewById(R.id.llWhatsapp);

        llWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("task_type", "WhatsApp to Lead");
                setResult(RESULT_OK, i);
                finish();

            }
        });

        llSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("task_type", "SMS to Lead");
                setResult(RESULT_OK, i);
                finish();

            }
        });

        llCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("task_type", "Call to Lead");
                setResult(RESULT_OK, i);
                finish();

            }
        });

        llMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = getIntent();
                i.putExtra("task_type", "Mail to Lead");
                setResult(RESULT_OK, i);
                finish();

            }
        });


    }
}
