package com.hooapps.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.google.android.gms.internal.zzs.TAG;

public class MainActivity extends Activity {

    private LinearLayout ll;
    private DatabaseReference mPostReference;
    private DatabaseReference mDatabase;

    private Button newtask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ll = (LinearLayout) findViewById(R.id.activity_main);
        newtask = (Button) findViewById(R.id.newtask);
        newtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                builder1.setMessage("New Task");
                builder1.setCancelable(true);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View filterLL = inflater.inflate(R.layout.dialog, null);

                final EditText payloadtv = (EditText) filterLL.findViewById(R.id.payloadtv);
                final EditText prioritytv = (EditText) filterLL.findViewById(R.id.prioritytv);
                prioritytv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            prioritytv.setText("");
                        }
                    }
                });


                builder1.setView(filterLL);

                builder1.setPositiveButton(
                        "Create",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Calendar c = Calendar.getInstance();
                                writeNewPost(payloadtv.getText().toString(), prioritytv.getText().toString());
                                dialog.cancel();

                            }
                        });

                builder1.setNegativeButton(
                        "Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mPostReference = FirebaseDatabase.getInstance().getReference().child("tasks");
        mPostReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ll.removeAllViews();
                for (final DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Task t = postSnapshot.getValue(Task.class);
                    View v = getLayoutInflater().inflate(R.layout.task, null);

                    ll.addView(v);
                    TextView tv = (TextView) v.findViewById(R.id.tv);
                    tv.setText(t.payload);
                    Button b = (Button) v.findViewById(R.id.button);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            RelativeLayout r = (RelativeLayout) view.getParent();
                            ll.removeView(r);
                            mDatabase.child("tasks").child(postSnapshot.getKey()).removeValue();

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void writeNewPost(String payload, String priority) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("tasks").push().getKey();
        Task task = new Task(payload, priority);
        Map<String, Object> postValues = task.toMap();

        Log.d(payload, priority);
        Map<String, Object> childUpdates = new HashMap<>();
        //childUpdates.put(payload, priority);
        childUpdates.put("/tasks/" + key, postValues);
        //childUpdates.put("/user-posts/" + useId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}

