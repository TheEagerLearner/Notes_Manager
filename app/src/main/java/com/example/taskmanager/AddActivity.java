package com.example.taskmanager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {

    ImageView imgBack;
    TextView Done;
    EditText etHeading,etDescription;

    String heading,description;
    Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imgBack=findViewById(R.id.imgBack);
        Done=findViewById(R.id.Done);
        etHeading=findViewById(R.id.etHeading);
        etDescription=findViewById(R.id.etDescription);
        toolbar=findViewById(R.id.toolbar);


        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddActivity.this,MainActivity.class));
                finish();
            }
        });

        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


    insert();

            }
        });


    }

    private void insert() {


        heading = etHeading.getText().toString();
        description = etDescription.getText().toString();
        if (heading.isEmpty() && description.isEmpty()) {
            Toast.makeText(AddActivity.this, "All the above fields are mandatory ", Toast.LENGTH_SHORT).show();
        }
        else
            {

            class InsertTask extends AsyncTask<Void, Void, Void> {


                @Override
                protected Void doInBackground(Void... voids) {

                    Date c = Calendar.getInstance().getTime();

                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String formattedDate = df.format(c);
                    NoteEntity note = new NoteEntity();
                    note.setHeading(heading);
                    note.setDescription(description);
                    note.setCreated(formattedDate);
                    DatabaseClient.getInstance(getApplicationContext()).getDab().noteDao().Insert(note);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    startActivity(new Intent(AddActivity.this,MainActivity.class));
                    finish();
                }
            }
            InsertTask insertTask = new InsertTask();
            insertTask.execute();
        }
    }
}