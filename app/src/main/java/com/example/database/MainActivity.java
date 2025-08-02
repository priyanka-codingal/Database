package com.example.database;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName, editEmail, editContact;
    Button btnAddData, btnShowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editName);
        editEmail = findViewById(R.id.editEmail);
        editContact = findViewById(R.id.editContact);
        btnAddData = findViewById(R.id.btnAddData);
        btnShowData = findViewById(R.id.btnShowData);

        btnAddData.setOnClickListener(v -> {
            boolean isInserted = myDb.insertData(
                    editName.getText().toString(),
                    editEmail.getText().toString(),
                    editContact.getText().toString()
            );

            if (isInserted) {
                Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                editName.setText("");
                editEmail.setText("");
                editContact.setText("");
            } else {
                Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_SHORT).show();
            }
        });

        btnShowData.setOnClickListener(v -> showDataPopup());
    }

    private void showDataPopup() {
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuilder data = new StringBuilder();
        while (res.moveToNext()) {
            data.append("Name: ").append(res.getString(1)).append("\n")
                    .append("Email: ").append(res.getString(2)).append("\n")
                    .append("Contact: ").append(res.getString(3)).append("\n\n");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Stored Data");
        builder.setMessage(data.toString());
        builder.setPositiveButton("OK", null);
        builder.show();
    }
}
