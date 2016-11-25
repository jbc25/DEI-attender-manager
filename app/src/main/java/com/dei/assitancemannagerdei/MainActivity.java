package com.dei.assitancemannagerdei;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PREF_LIST = "list";
    private ListView listItems;
    private EditText editName;
    private Button btnAdd;
    private List<String> names;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listItems = (ListView) findViewById(R.id.list_items);
        editName = (EditText) findViewById(R.id.edit_name);
        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editName.getText().toString();
                processName(name);
            }
        });

        names = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, names);
        listItems.setAdapter(adapter);


    }

    private void processName(String name) {

        if (name.isEmpty()) {
            editName.setError(getString(R.string.set_name));
            return;
        }

        editName.setText("");

        addNameToList(name);

    }

    private void addNameToList(String name) {
        names.add(name);
        adapter.notifyDataSetChanged();
    }


    // Save and restore data in correct life cycle methods onPause and onResume

    @Override
    protected void onPause() {
        super.onPause();

        String todoJunto = "";
        for (String name : names) {
             todoJunto += name + ",";
        }

        SharedPreferences prefs
                = PreferenceManager.getDefaultSharedPreferences(this);

        prefs.edit().putString(PREF_LIST, todoJunto).commit();

    }


    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs
                = PreferenceManager.getDefaultSharedPreferences(this);

        String todoJunto = prefs.getString(PREF_LIST, null);
        if (todoJunto != null) {
            String[] namesArray = todoJunto.split(",");

            for (int i = 0; i < namesArray.length; i++) {
                String name = namesArray[i];
                names.add(name);
            }
        }

        adapter.notifyDataSetChanged();

    }


    // Action bar menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_clear:
                names.clear();
                adapter.notifyDataSetChanged();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
