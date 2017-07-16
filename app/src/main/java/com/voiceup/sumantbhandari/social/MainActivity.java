/*
package com.example.sumantbhandari.myfirstapplication;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;

import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.appcompat.*;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    // for getting the data to display the list of topics avialable
    //private IngredientHelper dbIngredientHelper = null;
    private Cursor ourCursor = null;
    //private IngredientAdapter adapter = null;
    private TestAdapter mDbHelper = null;


    //for preloading a database for an activity
    DatabaseHelper mydb; //  is never used till now

    // for making the list items selectable and adding intent
    public static final String ID_EXTRA = "com.example.sumantbhandari.myfirstapplication._ID";

    // getting database reference
    private DatabaseReference databaseRef ;
    private FirebaseRecyclerAdapter<Topic, MainActivity.RecordViewHolder> firebaseRecyclerAdapter;
    private RecyclerView mTopicList;
    private static int topic_id;





    @Override
    protected void onCreate(Bundle savedInstanceState){
        System.out.println("Lets see if we are in Oncreate of SignInActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



            // The first step is to display the list of topics avialable
            //dbIngredientHelper = new IngredientHelper(this);
            //dbIngredientHelper.createDatabase();
            //dbIngredientHelper.openDataBase();
            //ourCursor = dbIngredientHelper.getCursor();
            //String[] fromFieldNames= new String[]{dbIngredientHelper.COLUMN_ID, dbIngredientHelper.COULMN_TITLE};
            //ListView myListView = (ListView) findViewById(R.id.myListView);
            //startManagingCursor(ourCursor);
            //adapter = new IngredientAdapter(ourCursor);
            //myListView.setAdapter(adapter);


           */
/**
            mDbHelper = new TestAdapter(this);
            mDbHelper.createDatabase();
            mDbHelper.open();

            ourCursor = mDbHelper.getTestData();
            mDbHelper.close();
            ListView myListView = (ListView) findViewById(R.id.myListView);
            System.out.println("bhai dekhe to sahi : "  + ourCursor);
            adapter = new IngredientAdapter(ourCursor);
            myListView.setAdapter(adapter);
            myListView.setOnItemClickListener(onListClick); *//*



            databaseRef = FirebaseDatabase.getInstance().getReference().child("Topics").child("Social");
            databaseRef.keepSynced(true);

            mTopicList = (RecyclerView) findViewById(R.id.topic_list);
            mTopicList.setHasFixedSize(true);
            mTopicList.setLayoutManager(new LinearLayoutManager(this));

            //mydb = new DatabaseHelper(this);
            //viewAll();


    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);


    }

    */
/**

    private void viewAll(){
        Cursor res = mydb.getAllData();
        if (res.getCount() == 0){
            showMessage("Error", "Nothing found");
            return ;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()){
            buffer.append("ID : " + res.getString(0) + "\n");
            buffer.append("Name : " + res.getString(1) + "\n");
            buffer.append("SurName : " + res.getString(2) + "\n");
            buffer.append("Recording : " + res.getString(3) + "\n\n");
        }

        // show all data
        showMessage("Data", buffer.toString());
    }

    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public class IngredientAdapter extends CursorAdapter {


        public IngredientAdapter(Cursor c) {
            super(MainActivity.this, c);

        }

        public void bindView(View row, Context ctxt, Cursor c){
            IngredientHolder holder = (IngredientHolder)row.getTag();
            holder.populateFrom(c, mDbHelper);

        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View row =  inflater.inflate(R.layout.row ,parent, false);
            IngredientHolder holder = new IngredientHolder(row);
            row.setTag(holder);
            return(row);
        }


    }
    static class IngredientHolder {

        private TextView name = null;

        IngredientHolder(View row) {
            name = (TextView) row.findViewById(R.id.ingredientText);
        }

        void populateFrom(Cursor c, TestAdapter r) {
            try{
            name.setText(r.getName(c));
        }catch (Exception e){
            e.printStackTrace();
            }
        }
    }


 **//*



    @Override
    protected void onStart(){
        super.onStart();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Topic, MainActivity.RecordViewHolder>(
                Topic.class, R.layout.row, MainActivity.RecordViewHolder.class, databaseRef
        ) {


            @Override*/
/**//*

            protected void populateViewHolder(MainActivity.RecordViewHolder viewHolder, Topic model, int position) {

                final String post_key = getRef(position).getKey();

                viewHolder.setTopicDesc(model.getDescription());
                viewHolder.setTopicRules(model.getRules());
                viewHolder.setTopicText(model.getText());

                topic_id = model.getID();
            }
        };

        mTopicList.setAdapter(firebaseRecyclerAdapter);

    }


    public static class RecordViewHolder extends  RecyclerView.ViewHolder {

        View mView;

        //DatabaseReference databaseRef;



        public RecordViewHolder(View itemView) {

            super(itemView);

            mView = itemView;

        }



        public void setTopicText(String topicText) {
            TextView post_title = (TextView) mView.findViewById(R.id.display_text);
            post_title.setText(topicText);
        }
        public void setTopicDesc(String topicDesc) {
            TextView post_descitption = (TextView) mView.findViewById(R.id.display_desciption);
            post_descitption.setText(topicDesc);
        }
        public void setTopicRules(String topicRules) {
            TextView post_rules = (TextView) mView.findViewById(R.id.display_rules);
            post_rules.setText(topicRules);
        }
    }


}
*/
