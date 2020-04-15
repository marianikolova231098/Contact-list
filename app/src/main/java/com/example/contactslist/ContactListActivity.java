package com.example.contactslist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ContactListActivity extends AppCompatActivity {
    public ListView editResult;


    public SQLiteDatabase openDB(){
        return SQLiteDatabase.openOrCreateDatabase(
                getFilesDir().getPath()+"/kontakti.db",
                null
        );
    }

    public void select(SQLiteDatabase db, ListView result){
        String q="SELECT ID, name, phone, moreInfo, category FROM KONTAKTI2; ";
        Cursor c=db.rawQuery(q, null);

        ArrayList<String> listItems=new ArrayList<String>();
        ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);

        while (c.moveToNext()){
            StringBuilder sb=new StringBuilder();
            Button btn = new Button(this);
            btn.setText("Edit");
            sb.append("\n");
            sb.append("Name: "+c.getString(c.getColumnIndex("name")));
            sb.append("\n");
            sb.append("Phone number: "+c.getString(c.getColumnIndex("phone")));
            sb.append("\n");
            sb.append("More info: "+c.getString(c.getColumnIndex("moreInfo")));
            sb.append("\n");
            sb.append("Category: "+c.getString(c.getColumnIndex("category")));
            sb.append("\n");
            listItems.add(sb.toString());
        }
        result.setAdapter(listAdapter);
    }

    public void addNewContacts(View v){
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
        editResult=findViewById(R.id.result);

        SQLiteDatabase db=openDB();
        select(db, editResult);
        db.close();
    }
}
