package com.tatlicilar.visualeducation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Asus on 12.08.2017.
 */

public class textDeneme extends Activity {
    TextView lessonInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lesson_information);
        LayoutInflater factory = LayoutInflater.from(this);
//text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.google_aut_user_info, null);
        final EditText edit_usSchool = (EditText) textEntryView.findViewById(R.id.logGogschool_edtext);
        final EditText edit_usType = (EditText) textEntryView.findViewById(R.id.logGogtype_edtext);
        final EditText edit_usClassYear = (EditText) textEntryView.findViewById(R.id.logGogyear_edtext);


        edit_usSchool.setText("Okul", TextView.BufferType.EDITABLE);
        edit_usType.setText("Kullanıcı Tipi", TextView.BufferType.EDITABLE);
        edit_usClassYear.setText("Sınıf", TextView.BufferType.EDITABLE);

        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setIcon(R.drawable.com_facebook_button_icon).setTitle("Bilgilerim").setView(textEntryView).setPositiveButton("Kaydet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        Intent intent=new Intent(textDeneme.this,Home_page.class);
                        startActivity(intent);


                        Log.i("AlertDialog", "TextEntry 2 Entered " + edit_usClassYear.getText().toString());
    /* User clicked OK so do some stuff */
                    }
                }).setNegativeButton("Ana Sayfaya Dön",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
     /*
     * User clicked cancel so do some stuff
     */
                    }
                });
        alert.show();

    }
    }



