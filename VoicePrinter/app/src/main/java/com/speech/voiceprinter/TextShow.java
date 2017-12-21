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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class TextShow extends SavedItems implements NavigationView.OnNavigationItemSelectedListener {

    TextView texthead, textshow;
    ImageButton btnSpeak, btnShare;
    TextToSpeech tts;
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;
    SQLiteListAdapter ListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_show);


        //texthead = (TextView) findViewById(R.id.texthead);
        textshow = (TextView) findViewById(R.id.textshow);
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
        btnShare = (ImageButton) findViewById(R.id.btnShare);

        Intent intent = getIntent();
        String id1 = intent.getStringExtra("one");
        String name = intent.getStringExtra("two");

        //texthead.setText("File000" + id1 + ".txt");
        textshow.setText(name);

        tts = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.UK);
                }
            }
        });

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = textshow.getText().toString();
                tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                String yo = textshow.getText().toString();
                i.putExtra(Intent.EXTRA_TEXT, yo);
                startActivity(i);
            }
        });

    }

    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.conextmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.delete:
//                Intent intent = getIntent();
//                String name = intent.getStringExtra("two");
//
//                //SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();
//                SQLITEDATABASE.rawQuery("DELETE from demoTable WHERE " + name + "=name", null).moveToFirst();
//
//                Intent i = new Intent(TextShow.this, SavedItems.class);
//                startActivity(i);
//                break;
//
//            case R.id.about:
//                Toast.makeText(getApplicationContext(), "Developed by RIYA & Group", Toast.LENGTH_SHORT).show();
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TextShow.this, SavedItems.class);
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
