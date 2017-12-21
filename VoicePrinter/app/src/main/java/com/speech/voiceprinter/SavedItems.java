package com.speech.voiceprinter;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import static com.speech.voiceprinter.SQLiteHelper.KEY_Name;

public class SavedItems extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ListView lv;
    ArrayList<String> myList = new ArrayList<String>();
    ArrayList<String> ID_ArrayList = new ArrayList<String>();

    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter;
    TextToSpeech tts;

    public static final String KEY_ID = "id";
    public static final String TABLE_NAME = "demoTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);



        lv = (ListView) findViewById(R.id.lv);
        SQLITEHELPER = new SQLiteHelper(this);
        registerForContextMenu(lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), TextShow.class);
                i.putExtra("one", id);
                i.putExtra("two", myList.get(position));
                startActivity(i);
            }
        });
    }


    @Override
    protected void onResume() {
        ShowSQLiteDBdata();
        super.onResume();
    }

    private void ShowSQLiteDBdata() {
        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM demoTable ORDER BY id DESC", null);

        ID_ArrayList.clear();
        myList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add("File000" + cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)) + ".txt");
                myList.add(cursor.getString(cursor.getColumnIndex(KEY_Name)));
            } while (cursor.moveToNext());
        }
        ListAdapter = new SQLiteListAdapter(SavedItems.this, ID_ArrayList, myList);
        lv.setAdapter(ListAdapter);
        cursor.close();
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(SavedItems.this, MainActivity.class);
        startActivity(i);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_record) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_saved) {
            Intent i = new Intent(getApplicationContext(), SavedItems.class);
            startActivity(i);

        } else if (id == R.id.nav_offline) {
            Intent i = new Intent(getApplicationContext(), offline.class);
            startActivity(i);

        } else if (id == R.id.nav_about) {
            Intent i = new Intent(getApplicationContext(), about.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}