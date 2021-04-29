package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btnAdd;
    RecyclerView rec;
    TaskAdapter adp;
    ImageView imgDelAll;
    Dialog delall;
    Button btnYes,btnNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd=findViewById(R.id.btnAdd);
        rec=findViewById(R.id.rec);
        imgDelAll=findViewById(R.id.imgDelAll);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AddActivity.class));
                finish();

            }
        });



        delall=new Dialog(MainActivity.this);
        delall.setContentView(R.layout.deleteall_dailog);
        delall.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btnYes=delall.findViewById(R.id.btnYes);
        btnNo=delall.findViewById(R.id.btnNo);



        imgDelAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delall.show();
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteall();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        delall.dismiss();
                    }
                });
            }
        });

        display();
        rec.setLayoutManager(new StaggeredGridLayoutManager(2 , StaggeredGridLayoutManager.VERTICAL));

    }

    private void deleteall() {


        class DeleteAll extends AsyncTask<Void,Void,Void>
        {


            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(getApplicationContext()).getDab().noteDao().DeleteAll();

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(MainActivity.this,"All Notes Deleted", Toast.LENGTH_SHORT).show();
                delall.dismiss();
                display();
            }
        }
        DeleteAll deleteAll=new DeleteAll();
        deleteAll.execute();

    }

    private void display() {

        class dispAll extends AsyncTask<Void,Void, List<NoteEntity>>
        {

            @Override
            protected List<NoteEntity> doInBackground(Void... voids) {

                return DatabaseClient.getInstance(getApplicationContext()).getDab().noteDao().Display();
            }

            @Override
            protected void onPostExecute(List<NoteEntity> noteEntities) {
                super.onPostExecute(noteEntities);
                if(!noteEntities.isEmpty())
                {

                }
                adp=new TaskAdapter(MainActivity.this,noteEntities);
                rec.setAdapter(adp);

            }
        }

        dispAll dis=new dispAll();
        dis.execute();

    }
}