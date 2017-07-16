package com.voiceup.sumantbhandari.social;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.voiceup.sumantbhandari.social.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by sumantbhandari on 12/27/16.
 */

public class Activity1  extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference databaseRef ;
    private DatabaseReference CategoryDB;
    private FirebaseRecyclerAdapter<Topic, RecordViewHolder> firebaseRecyclerAdapter;
    private RecyclerView mTopicList;
    private static int topic_id;
    //private Spinner spinner;
    ArrayList<String> list = new ArrayList<>();
    static final String[] Categories= new String[]{"Entertainment","Social","Serious Singing"};
    private String selected_category;
    private boolean first_time = true;

    public static final String ID_EXTRA = "topic_id";
    public static  final String ID_maxTime ="max_time";
    public static  final String ID_minTime ="min_time";


    //private ArrayAdapter<String> categoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("inside onCreate Activity1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Spinner spinner = (Spinner)findViewById(R.id.spinnerCategory);
        spinner.setSelection(3);

        //Right now I am populating the spinner from a list but later on it can done from the database
        //CategoryDB = FirebaseDatabase.getInstance().getReference().child("Topics");
        //CategoryDB.keepSynced(true);

        mTopicList = (RecyclerView) findViewById(R.id.topic_list);
        mTopicList.setHasFixedSize(true);
        mTopicList.setLayoutManager(new LinearLayoutManager(this));

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, Categories);
        spinner.setAdapter(categoryAdapter);
        spinner.setOnItemSelectedListener(this);



        DataAdapter();

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selected_category = Categories[position];
        DataAdapter();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void DataAdapter(){

        if (first_time) {
            first_time= false;
            Random rand = new Random();
            int n = rand.nextInt(2);
            selected_category = Categories[n];
        }

        databaseRef = FirebaseDatabase.getInstance().getReference().child("Topics").child(selected_category);
        databaseRef.keepSynced(true);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Topic, RecordViewHolder>(
                Topic.class, R.layout.row, RecordViewHolder.class, databaseRef
        ) {


            @Override
            protected void populateViewHolder(RecordViewHolder viewHolder, Topic model, int position) {

                final String post_key = getRef(position).getKey();
                final int topic_id= model.getID();
                final int max_time= model.getMax_time();
                final int min_time = model.getMin_time();
                System.out.println("max_time " + max_time);
                System.out.println("min_time " + min_time);

                viewHolder.post_descitption.setText((model.getDescription()));
                viewHolder.post_rules.setText(model.getRules());
                viewHolder.post_title.setText(model.getText());
                System.out.println("topic_id"+topic_id);

                viewHolder.mView.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(Activity1.this, Activity2.class);
                        i.putExtra(ID_maxTime,max_time);
                        i.putExtra(ID_minTime,min_time);
                        i.putExtra(ID_EXTRA, topic_id);

                        startActivity(i);

                    }
                });

            }
        };

        mTopicList.setAdapter(firebaseRecyclerAdapter);


    }


    public static class RecordViewHolder extends  RecyclerView.ViewHolder {

        View mView;
        public TextView post_title;
        public TextView post_descitption;
        public TextView  post_rules;

        public RecordViewHolder(View itemView) {

            super(itemView);

            mView = itemView;
            post_title = (TextView) mView.findViewById(R.id.display_text);
            post_descitption = (TextView) mView.findViewById(R.id.display_desciption);
            post_rules = (TextView) mView.findViewById(R.id.display_rules);

        }
    }


}
