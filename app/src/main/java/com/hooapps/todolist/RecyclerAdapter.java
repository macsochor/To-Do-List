package com.hooapps.todolist;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mac on 12/29/16.
 */


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.TaskHolder> {

    private ArrayList<RelativeLayout> al;

    public RecyclerAdapter(ArrayList<RelativeLayout> arraylist){
        al = arraylist;
    }
    @Override
    public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task, parent, false);
        return new TaskHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(TaskHolder holder, int position) {
        RelativeLayout task = al.get(position);
        holder.bindTask(task);
    }

    @Override
    public int getItemCount() {
        return al.size();
    }

    public static class TaskHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private Button button;
        private TextView tv;

        public TaskHolder(View v){
            super(v);
            button = (Button) v.findViewById(R.id.button);
            tv = (TextView) v.findViewById(R.id.tv);
            v.setOnClickListener(this);
        }

        public void bindTask(RelativeLayout rl){

        }

        @Override
        public void onClick(View v) {
            Log.d("clicked", "Success!");

        }
    }
}
