package com.dei.assitancemannagerdei;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listItems;
    private EditText editName;
    private Button btnPublish;
    private List<String> names;
    private ArrayAdapter<String> adapter;
    private Button btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = (ListView) findViewById(R.id.list_items);
        editName = (EditText) findViewById(R.id.edit_name);
        btnPublish = (Button) findViewById(R.id.btn_publish);
        btnClear = (Button) findViewById(R.id.btn_clear);

        btnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString();
                processName(name);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                names.clear();
                adapter.notifyDataSetChanged();
            }
        });

        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listItems.setAdapter(adapter);
    }

    private void processName(String name) {

        if (name.isEmpty()) {
            editName.setError("Pon un nombre");
            return;
        }

        editName.setText("");

        addNameToList(name);

    }

    private void addNameToList(String name) {
        names.add(name);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onPause() {
        super.onPause();

        String todoJunto = "";
        for (String name : names) {
             todoJunto += name + ",";
        }

        SharedPreferences prefs
                = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.edit().putString("lista", todoJunto).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs
                = PreferenceManager.getDefaultSharedPreferences(this);

        String todoJunto = prefs.getString("lista", null);
        if (todoJunto != null) {
            String[] namesArray = todoJunto.split(",");

            for (int i = 0; i < namesArray.length; i++) {
                String name = namesArray[i];
                names.add(name);
            }
        }

        adapter.notifyDataSetChanged();

    }
}
