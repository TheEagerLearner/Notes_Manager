package com.example.taskmanager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Dialog dialog;
    Context context;
    List<NoteEntity> notes;

    public TaskAdapter(Context context,List<NoteEntity> notes)
    {
        this.context=context;
        this.notes=notes;
    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.task_row,parent,false);

        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        holder.txtTitle.setText(notes.get(position).getHeading());
        holder.txtDate.setText(notes.get(position).getCreated());

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txtDheading,txtDDes;
        Button btnDelete,btnUpdate,btnDone;
        TextView txtTitle,txtDate;
        LinearLayout linear;
        EditText etUpdateHead,etUpdateDes;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle=itemView.findViewById(R.id.txtTitle);
            txtDate=itemView.findViewById(R.id.txtDate);
            linear=itemView.findViewById(R.id.linear);
            dialog=new Dialog(context);
            dialog.setContentView(R.layout.dialog);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            itemView.setOnClickListener(this);



        }


        @Override
        public void onClick(View view) {
            txtDheading=dialog.findViewById(R.id.txtDHeading);
            txtDDes=dialog.findViewById(R.id.txtDDes);
            btnDelete=dialog.findViewById(R.id.btnDelete);
            btnUpdate=dialog.findViewById(R.id.btnUpdate);
            NoteEntity n= notes.get(getAdapterPosition());
            txtDheading.setText(n.getHeading());
            txtDDes.setText(n.getDescription());
            dialog.show();
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    delete(n);
                    dialog.dismiss();
                }
            });

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    Dialog updateDialog=new Dialog(context);
                    updateDialog.setContentView(R.layout.update_dialog);
                    updateDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    etUpdateDes=updateDialog.findViewById(R.id.etUpdateDes);
                    etUpdateHead=updateDialog.findViewById(R.id.etUpdateHead);
                    btnDone=updateDialog.findViewById(R.id.btnUp);
                    etUpdateHead.setText(n.getHeading());
                    etUpdateDes.setText(n.getDescription());
                    updateDialog.show();
                    btnDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Date c = Calendar.getInstance().getTime();

                            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                            String formattedDate;
                            formattedDate = df.format(c);
                            NoteEntity no = new NoteEntity();
                            no.setId(n.getId());
                            no.setDescription(etUpdateDes.getText().toString());
                            no.setHeading(etUpdateHead.getText().toString());
                            no.setCreated(formattedDate);
                            update(no);
                            updateDialog.dismiss();

                        }
                    });


                }
            });

        }

        private void update(NoteEntity n) {

            class UpdateNote extends AsyncTask<Void,Void,Void>
            {


                @Override
                protected Void doInBackground(Void... voids) {

                    DatabaseClient.getInstance(context).getDab().noteDao().Update(n);

                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(context,"Updated",Toast.LENGTH_SHORT).show();
                    ((Activity)context).finish();
                    ((Activity)context).overridePendingTransition(0, 0);
                    context.startActivity(((Activity)context).getIntent());
                    ((Activity)context).overridePendingTransition(0,0);



                }
            }

            UpdateNote updateNote=new UpdateNote();
            updateNote.execute();

        }


    }

    private void delete(NoteEntity n) {

        class DeleteNote extends AsyncTask<Void,Void,Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {

                DatabaseClient.getInstance(context).getDab().noteDao().Delete(n);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(context,"Deleted",Toast.LENGTH_SHORT).show();
                ((Activity)context).finish();
                ((Activity)context).overridePendingTransition(0, 0);
                context.startActivity(((Activity)context).getIntent());
                ((Activity)context).overridePendingTransition(0,0);


            }
        }

        DeleteNote deleteNote=new DeleteNote();
        deleteNote.execute();
    }
}

