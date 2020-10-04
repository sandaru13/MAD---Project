package com.example.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.registration.adapter.UserListAdapter;
import com.example.registration.model.User;
import com.example.registration.preference.Consts;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UserHandlingActivity extends AppCompatActivity implements UserListAdapter.OnUserClickListener {

    ProgressDialog pd;
    private UserListAdapter adapter;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userhandling);

        pd = new ProgressDialog(this);
        pd.setMessage("Loading Users");

        userList = new ArrayList<>();
        adapter = new UserListAdapter(userList, this);

        RecyclerView rlUserList = findViewById(R.id.rl_user_list);
        rlUserList.setLayoutManager(new LinearLayoutManager(this));
        rlUserList.setAdapter(adapter);

        pd.show();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users");

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists()) {
                    pd.hide();
                    User user = dataSnapshot.getValue(User.class);
                    user.setId(dataSnapshot.getKey());
                    userList.add(user);
                    updateList();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    user.setId(dataSnapshot.getKey());

                    for (User u : userList) {
                        if (u.getId().equals(user.getId())) {
                            userList.remove(u);
                            break;
                        }
                    }
                    userList.add(user);
                    updateList();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mDatabase.addChildEventListener(childEventListener);
    }

    private void updateList() {
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return user.getName().toLowerCase().compareTo(t1.getName().toLowerCase());
            }

            @Override
            public boolean equals(Object o) {
                return false;
            }
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        intent.putExtra(Consts.USER_EXTRA, user);
        startActivity(intent);
    }
}