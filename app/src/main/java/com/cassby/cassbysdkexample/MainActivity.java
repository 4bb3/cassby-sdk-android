package com.cassby.cassbysdkexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cassby.cassbysdk.CassbySDK;
import com.cassby.cassbysdk.Entities.Check;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText name;
    EditText price;
    EditText qty;
    EditText branch;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.main_name);
        price = findViewById(R.id.main_price);
        qty = findViewById(R.id.main_qty);
        branch = findViewById(R.id.main_branch);

        submit = findViewById(R.id.main_submit_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCheck();
            }
        });
    }

    private void saveCheck() {
        CassbySDK.getInstance().initCheck(Integer.parseInt(branch.getText().toString()));
        CassbySDK.getInstance().addToCheck(name.getText().toString(),Integer.parseInt(price.getText().toString()),Double.valueOf(qty.getText().toString()));
        CassbySDK.getInstance().commit();

        name.setText("");
        price.setText("");
        qty.setText("");
    }
}
