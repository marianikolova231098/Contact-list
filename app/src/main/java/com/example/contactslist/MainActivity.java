package com.example.contactslist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    public EditText editName, editPhone, editMoreInfo;
    public ListView editResult;
    public Spinner selector;
    public Button btnInsert;

    public String getText(EditText field){
        return field.getText().toString();
    }
    public String getDropDownValue(Spinner field) { return field.getSelectedItem().toString(); }
    public SQLiteDatabase openDB(){
        return SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath()+"/kontakti.db",
                null
        );
    }
    public void initDatabase(SQLiteDatabase db){
        String q="CREATE TABLE if not exists KONTAKTI2(";
        q+=" ID integer primary key AUTOINCREMENT, ";
        q+=" name text not null, ";
        q+=" phone integer not null, ";
        q+=" moreInfo text, ";
        q+=" category text not null ";
        q+="); ";
        db.execSQL(q);
    }
    public void alert(String s){
        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(s);
        alertDialog.show();
    }

    public void viewContactList(View v){
        Intent myIntent = new Intent(getBaseContext(), ContactListActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editName=findViewById(R.id.name);
        editPhone=findViewById(R.id.phone);
        editMoreInfo=findViewById(R.id.moreInfo);
        selector=findViewById(R.id.dropDownList);
        editResult=findViewById(R.id.result);

        btnInsert=findViewById(R.id.btnInsert);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.categories_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selector.setAdapter(spinnerAdapter);

        SQLiteDatabase db=openDB();
        initDatabase(db);
        db.close();

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String name=getText(editName);
                    String phone=getText(editPhone);
                    String moreInfo=getText(editMoreInfo);
                    String chosenCategory=getDropDownValue(selector);
                    SQLiteDatabase db=openDB();
                    initDatabase(db);
                    String q="INSERT INTO KONTAKTI2(name, phone, moreInfo, category) ";
                    q+=" VALUES(?, ?, ?, ?)";
                    db.execSQL(q, new Object[]{  name, phone, moreInfo, chosenCategory });
                    db.close();
                    alert("New user added!");
                }catch (Exception e){
                    alert("Exception: "+e.getLocalizedMessage());
                }
            }
        });
    }
}
