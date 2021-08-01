package com.spidertechsoft.safalmemployee;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class LeadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lead_activity);

        LinearLayout lead_ic1 = findViewById(R.id.lead_ic1);
        LinearLayout lead_ic2 = findViewById(R.id.lead_ic2);
        LinearLayout lead_ic3 = findViewById(R.id.lead_ic3);
        LinearLayout lead_ic4 = findViewById(R.id.lead_ic4);
        LinearLayout lead_ic5 = findViewById(R.id.lead_ic5);
        LinearLayout lead_ic6 = findViewById(R.id.lead_ic6);

        lead_ic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LeadActivity.this, SelfLeadListActivity.class);
                startActivity(i);

            }
        });

        lead_ic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LeadActivity.this,CompanyLeadListActivity.class));

            }
        });

        lead_ic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LeadActivity.this,SelfVisitedLeadListActivity.class));

            }
        });

        lead_ic4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LeadActivity.this,CompanyVisitedLeadListActivity.class));

            }
        });

        lead_ic5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LeadActivity.this,SelfSalesLeadList.class));

            }
        });

        lead_ic6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LeadActivity.this,CompanySalesLeadListActivity.class));

            }
        });
    }
}
