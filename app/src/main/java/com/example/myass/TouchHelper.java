package com.example.myass;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myass.Adopter.ToDoAdopter;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class TouchHelper extends ItemTouchHelper.SimpleCallback {
    private final ToDoAdopter adaptor;
    public TouchHelper(ToDoAdopter adaptor) {
        super ( 0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.adaptor=adaptor;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        final int position=viewHolder.getAdapterPosition ();
        if (direction==ItemTouchHelper.RIGHT){
            AlertDialog.Builder builder=new AlertDialog.Builder ( adaptor.getContext () );
            builder.setMessage ( "Are You Sure" )
                    .setTitle ( "Delete task" )
                    .setPositiveButton ( "Yes", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                         adaptor.deleteTask ( position );
                        }
                    } ).setNegativeButton ( "No", new DialogInterface.OnClickListener () {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    adaptor.notifyItemChanged ( position );
                }
            } );
            AlertDialog dialog=builder.create ();
            dialog.show ();

        }else {
              adaptor.editTask(position);
        }

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

        new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeRightActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeRightBackgroundColor(Color.RED)
                .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_24)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor ( adaptor.getContext (),R.color.green_blue ))
                .create()
                .decorate();




        super.onChildDraw ( c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive );
    }
}
