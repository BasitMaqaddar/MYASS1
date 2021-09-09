package com.example.myass.Adopter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myass.AddNewTask;
import com.example.myass.MainActivity;
import com.example.myass.Model.ToDoModel;
import com.example.myass.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ToDoAdopter extends RecyclerView.Adapter<ToDoAdopter.MyViewHolder> {
private List<ToDoModel> todoList;
private MainActivity activity;
private FirebaseFirestore firestore;

public ToDoAdopter(MainActivity mainActivity , List<ToDoModel>todoList){
    this.todoList=todoList;
    activity = mainActivity;
}
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(activity).inflate ( R.layout.each_task , parent , false );
  firestore =FirebaseFirestore.getInstance ();
   return new MyViewHolder (view);

    }
      public void deleteTask(int position){
    ToDoModel toDoModel=todoList.get ( position );
    firestore.collection ( "task" ).document (toDoModel.TaskId).delete ();
    todoList.remove ( position );
    notifyItemRemoved ( position );
      }

      public Context getContext(){
       return activity;
      }

      public void updateTask(int position){
    ToDoModel toDoModel=todoList.get ( position );
          Bundle bundle=new Bundle ();
          bundle.putString ("task" , toDoModel.getTask ());
          bundle.putString ( "Due",toDoModel.getDue () );
          bundle.putString ( "id" , toDoModel.TaskId  );


          AddNewTask addNewTask=new AddNewTask ();
          addNewTask.setArguments ( bundle );
          addNewTask.show ( activity.getSupportFragmentManager (),addNewTask.getTag () );


      }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ToDoModel toDoModel = todoList.get ( position );
        holder.mCheckBox.setText ( toDoModel.getTask () );
        holder.mDueDateTv.setText ( "due on" + toDoModel.getDue () );
        holder.mCheckBox.setChecked ( toBoolen ( toDoModel.getStatus () ) );


        holder.mCheckBox.setOnCheckedChangeListener ( new CompoundButton.OnCheckedChangeListener () {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked ()) {
                    firestore.collection ( "task" ).document ( toDoModel.TaskId ).update ( "status", 1 );
                }
                else {
                    firestore.collection ( "task" ).document ( toDoModel.TaskId ).update ( "status", 0 );
                }
            }
        } );

        }
    private boolean toBoolen(int status) {
        return status != 0;
    }


    @Override
    public int getItemCount() {
    return todoList.size ();
    }

    public void editTask(int position) {
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
           TextView mDueDateTv;
           CheckBox mCheckBox;
        public MyViewHolder(@NonNull View itemView) {
            super ( itemView );
            mDueDateTv=itemView.findViewById ( R.id.due_date_tv );
            mCheckBox=itemView.findViewById ( R.id.checkbox );
        }
    }
}
