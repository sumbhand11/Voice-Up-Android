package com.voiceup.sumantbhandari.social;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Handler;
import android.content.Intent;
import android.widget.Button;
import com.facebook.share.model.ShareMedia;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StreamDownloadTask;
import com.voiceup.sumantbhandari.social.BuildConfig;
import com.voiceup.sumantbhandari.social.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import android.net.Uri;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;


import com.firebase.client.Firebase;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;


//this is all for the mediaplayer//

import com.squareup.picasso.Picasso;

import android.media.AudioManager;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;


public class Activity2 extends AppCompatActivity{

    String passedVar = null;
    private int passed_topicID=101;
    private int passedMaxTime= 120;
    private int passedMinTime= 60;
    private Long startTime;
    private Long stopTime;
    private TextView passedView = null;

    /** Called when activity is first called*/
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaPlayer_recorder;
    private MediaRecorder recorder;
    private String OUTPUT_FILE;
    private String SHARE_FILE;

    //using the firebase constant
    final String FIREBASE_URL= BuildConfig.UNIQUE_FIREBASE_ROOT_URL;
    private StorageReference storageRef;
    private DatabaseReference databaseRef;
    private DatabaseReference userDataBase;
    private UploadTask uploadTask;
    private FirebaseAuth mAuth;
    protected ProgressDialog mProgress;
    private static FirebaseUser mCurrentUser;


    private  RecyclerView mRecordList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseLike;
    private StorageReference audioRecordRef;
    private FirebaseRecyclerAdapter<Records, RecordViewHolder> firebaseRecyclerAdapter;
    private Boolean mProcessLike = false;

    private Boolean isShare= false;




    private static Long LikesCount;

    private int MAX_LENGTH = 35;

    /**Variables for seekbar processing
     String sntSeekPos;
     int intSeekPos;
     int mediaPosition;
     int mediaMax;
     private final Handler handler = new Handler();
     private static int songEnded;
     public static final String BROADCAST_ACTION = "com.example.sumantbhandari.myfirstapplication.seekprogress";

     public static final String BROADCAST_BUFFER =  "com.example.sumantbhandari.myfirstapplication.broadcastbuffer";
     Intent bufferIntent;
     Intent seekIntent;

     private SeekBar seekBar;
     private  int seekMax;
     private static int SongEnded =0;
     boolean mBroadcastIsRegistered;
     private Button buttonPlayStop;
     private SeekBar seekBar;

     **/

    private Button record_button;
    private  Button play_button;
    private Button postView;
    private TextView time1;
    private CountDownTimer countDownTimer;

    private  Long durationSeconds = Long.valueOf(0);

    boolean shouldRunThread;






    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act2);


        //passedVar = getIntent().getStringExtra(MainActivity.ID_EXTRA);
        passed_topicID = getIntent().getExtras().getInt(Activity1.ID_EXTRA);
        passedMaxTime  = getIntent().getExtras().getInt(Activity1.ID_maxTime);
        passedMinTime = getIntent().getExtras().getInt(Activity1.ID_minTime);

        System.out.println(passed_topicID);

        //passedView = (TextView)findViewById(R.id.passed);
        //passedView.setText("Ab tu yahan aa gaya " + passedVar);

        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        play_button = (Button)findViewById(R.id.button3);
        record_button= (Button)findViewById(R.id.button1);
        time1 = (TextView)findViewById(R.id.time1);





   /*     countDownTimer = new CountDownTimer(passedMaxTime*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time.setText("Seconds Remaining " + millisUntilFinished/1000);

            }

            @Override
            public void onFinish() {
                time.setText("Max time Limit reached !");
                stopRecording();

            }
        };*/



        mediaPlayer = new MediaPlayer();

        OUTPUT_FILE = Environment.getExternalStorageDirectory() + "/audiorecorder.3gpp";

        SHARE_FILE = Environment.getExternalStorageDirectory() + "/sharethis.3gpp";

        //test firebase
        Firebase.setAndroidContext(this);
        storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://recordinghouse-2c286.appspot.com");
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Records");
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        userDataBase = FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth= FirebaseAuth.getInstance();
        mProgress = new ProgressDialog(this);

        mDatabaseLike.keepSynced(true);
        databaseRef.keepSynced(true);

        //addtoFirebasedb();



        populateListfromFireBase();


        //latest addition for pulling from fire base
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Records");

        mRecordList = (RecyclerView) findViewById(R.id.record_list);
        mRecordList.setHasFixedSize(true);
        mRecordList.setLayoutManager(new LinearLayoutManager(this));

        BhaiKaMethod();

        //seekbar
        /**
         seekIntent = new Intent(BROADCAST_ACTION);
         mediaPlayer.setOnSeekCompleteListener(this);
         **/



    }

    private void populateListfromFireBase() {
        StorageReference dbRef = storageRef.child("audio1/");


    }

 /*   private void addtoFirebasedb() {
        //Firebase ref = new Firebase(FIREBASE_URL);
        Firebase ref = new Firebase(FIREBASE_URL);
        ref.child("listname").setValue('1');

    }*/

    public void  ButtonTap(View view) {
        System.out.println("Abhi to party shuru hui hai");

        switch (view.getId()) {
            case R.id.button1:
                try {
                    //record_button= (Button)findViewById(R.id.button1);
                    //time = (TextView)findViewById(R.id.time1);
                    beginRecording();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button2:
                try {
                    stopRecording();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button3:
                try {
                    //play_button = (Button)findViewById(R.id.button3);
                    playRecording();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button4:
                try {
                    stopPlayBack();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.postRecordBtn:
                try {
                    insertFirebaseBS(OUTPUT_FILE);

                }catch (Exception e){
                    e.printStackTrace();
                }

        }
    }

    private void beginRecording() throws Exception{
        cancelTimer();
        ditchMediaRecorder();
        //ditchMediaPlayer();
        stopPlayBack();
        File outFile = new File(OUTPUT_FILE);
        /**System.out.println(OUTPUT_FILE); **/

        if (outFile.exists()) {
            outFile.delete();
        }

        recorder = new MediaRecorder();
        System.out.println(" in Begin Recording ");
        try{
            recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            recorder.setOutputFile(OUTPUT_FILE);
            recorder.prepare();
        }catch (Exception e){
            Toast.makeText(this, "Go to Phone Settings -> Apps -> Voice Up ->Permissions -> enable microphone", Toast.LENGTH_LONG).show();
        }


        startTime = System.currentTimeMillis();
        recorder.start();

        record_button.setText("Stop Recording");
        record_button.setId(R.id.button2);


        time1.setVisibility(TextView.VISIBLE);
        time1.setText(""+passedMaxTime);

        countDownTimer = new CountDownTimer(passedMaxTime*1000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //timecountdown =String.format("%02d:%02d", Integer.valueOf((int) ((millisUntilFinished % 3600) / 60)), Integer.valueOf((int)(millisUntilFinished % 60)));
                //time1.setText("Seconds Remaining " + millisUntilFinished/1000);

                time1.setText("Time Check : " + formatSeconds((int) (passedMaxTime - (millisUntilFinished /1000))));

            }
            public  String formatSeconds(int timeInSeconds)
            {
                int hours = timeInSeconds / 3600;
                int secondsLeft = timeInSeconds - hours * 3600;
                int minutes = secondsLeft / 60;
                int seconds = secondsLeft - minutes * 60;

                String formattedTime = "";

                if (minutes < 10)
                    formattedTime += "0";
                formattedTime += minutes + ":";

                if (seconds < 10)
                    formattedTime += "0";
                formattedTime += seconds ;

                return formattedTime;
            }

            @Override
            public void onFinish() {
                time1.setText("Max time Limit reached !");
                stopRecording();

            }
        };
        countDownTimer.start();

    }


    private void playRecording() throws  Exception{
        //ditchMediaRecorder();

        System.out.println(" record_button.getText() " + record_button.getText().toString().toLowerCase());
        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
            stopRecording();
        }
        System.out.println(" in playRecording  ");
        ditchMediaPlayer();
        System.out.println(" in playRecording after ditch media  ");
        mediaPlayer_recorder = new MediaPlayer();
        try{
        mediaPlayer_recorder.setDataSource(OUTPUT_FILE);
        mediaPlayer_recorder.prepare();
        mediaPlayer_recorder.start();
        }catch (Exception e){
            Toast.makeText(this, "Aa aa not fair!! Record before you play", Toast.LENGTH_SHORT).show();
        }

        play_button.setId(R.id.button4);
        play_button.setText("Stop");

    }
    private void stopPlayBack(){
        if( mediaPlayer_recorder != null){
            mediaPlayer_recorder.stop();
        }
        play_button.setId(R.id.button3);
        play_button.setText("Play");
    }

    private void ditchMediaRecorder() {
        if (recorder != null) {
            recorder.release();
        }
    }
    private void ditchMediaPlayer(){
        if(mediaPlayer_recorder != null) {
            try {
                mediaPlayer_recorder.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    private void stopRecording(){
        if (recorder != null){
            recorder.stop();
        }
        cancelTimer();
    }

    private void cancelTimer(){

        if(countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer = null;
            //time1.setVisibility(TextView.INVISIBLE);
            stopTime = System.currentTimeMillis();
            record_button.setText("Start Recording");
            record_button.setId(R.id.button1);
        }
    }


    public String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        //int randomLength = generator.nextInt(MAX_LENGTH);
        int length = 20;
        char tempChar;
        for (int i = 0; i < length; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private void insertFirebaseBS(String output_file){
        System.out.println("Lets see if we are in insertFirebaseBS");


        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
            stopRecording();
        }

        if (play_button.getText().toString().toLowerCase().equals("stop")) {
            stopPlayBack();
        }

        ditchMediaPlayer();
        ditchMediaRecorder();


        if (mAuth.getCurrentUser() == null ){
            startActivity(new Intent(Activity2.this, SignInActivity.class));
            return;
        }

        durationSeconds =  (stopTime - startTime)/1000;
        System.out.println("durationSeconds " + durationSeconds);

        if (durationSeconds < passedMinTime || durationSeconds > passedMaxTime){
            Toast.makeText(this, "Your Recording should be between " + passedMinTime +" and " + passedMaxTime +" seconds.", Toast.LENGTH_LONG).show();
            return;
        }

        mProgress.setMessage(" Posting your record ");
        mProgress.show();
        System.out.println("inside Buffer System");
        InputStream stream = null;
        audioRecordRef = storageRef.child("user_Records").child(random());
        try {
            stream = new FileInputStream(new File(output_file));
            uploadTask = audioRecordRef.putStream(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //StorageReference recordRef = storageRef.child(file.getLastPathSegment());

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();

                DatabaseReference newRecord = databaseRef.push();
                newRecord.child("Topic").setValue(passed_topicID);
                newRecord.child("Recording").setValue(downloadUrl.toString());
                newRecord.child("duration").setValue(durationSeconds);
                newRecord.child("Number_of_Likes").setValue(0);
                if( mAuth != null && mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getUid() != null) {
                    newRecord.child("Uid").setValue(mCurrentUser.getUid());
                    //newRecord.child("Username").setValue(mCurrentUser.getDisplayName());
                }

                //newRecord.child("uid").setValue(mAuth.getCurrentUser().getDisplayName());

                mProgress.dismiss();

            }
        });
    }




    protected void onStart(){
        super.onStart();
        Query ref = FirebaseDatabase.getInstance().getReference().child("Records").orderByChild("Topic").equalTo(passed_topicID);

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Records, RecordViewHolder>(
                Records.class, R.layout.record_row, RecordViewHolder.class, ref
        ) {


            @Override
            protected void populateViewHolder(RecordViewHolder viewHolder, Records model, final int position) {
                final Records model_copy= model;
                final String post_key = getRef(position).getKey();

                //viewHolder.setUserName(model.getUsername());
                System.out.println("model.getUid() :"+ model.getRecording() +  "  : " +model.getUid());
                if(userDataBase.child(model.getUid()) != null) {
                    viewHolder.setUserName(userDataBase.child(model.getUid()), getApplicationContext());
                }
                //viewHolder.setAudio(model.getRecording());
                //viewHolder.setNumberOfLikes();
                viewHolder.setLikeButton(post_key);

                //viewHolder.setUserImage(getApplicationContext());
                //viewHolder.setUserName(getApplicationContext());
                //songList.clear();
                songList.add( model); // this is going to cause some serious issues
                viewHolder.mLikeButton.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {

                        mProcessLike = true;

                        mDatabaseLike.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (mProcessLike) {
                                    if( mAuth != null && mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getUid() != null ) {
                                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {

                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                            mProcessLike = false;

                                        } else {
                                            mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("Random");
                                            mProcessLike = false;
                                        }
                                    }
                                    else{
                                        Toast.makeText(Activity2.this, "To like , you must be signed in!  click on POST Button.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        System.out.println("Inside  pushPlay from Adapter");

                        firstLaunch = false;
                        if(mediaPlayer != null){
                            System.out.println("currentIndex : "+  position );

                            if(position  < songList.size()){
                                Records next = songList.get(position);
                                changeSelectedSong(position);
                                prepareSong(next);
                            }else{
                                changeSelectedSong(0);
                                prepareSong(songList.get(0));
                            }

                        }




                    }
                });

                viewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
                            stopRecording();
                        }

                        if (play_button.getText().toString().toLowerCase().equals("stop")) {
                            stopPlayBack();
                        }
                        //ditchMediaPlayer();
                        //ditchMediaRecorder();

                        final Intent share= new Intent(Intent.ACTION_SEND);
                        share.setType("audio/*");
                        StorageReference httpsReference = storageRef.child("user_Records").getStorage().getReferenceFromUrl(model_copy.getRecording());
                        File shareFile = new File(SHARE_FILE);


                        httpsReference.getFile(shareFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                                Uri uri =Uri.parse(SHARE_FILE);
                                System.out.println(" SHARE_FILE : "+ SHARE_FILE);
                                System.out.println("taskSnapshot : "+ taskSnapshot.getStorage().getName());
                                System.out.println("uri : "+ uri);


                                share.putExtra(Intent.EXTRA_STREAM, uri);
                                try{
                                    isShare = true;
                                startActivity(Intent.createChooser(share, "Voice Up"));}
                                catch (Exception e){
                                    e.printStackTrace();
                                }

                            }
                        });

                        httpsReference.getFile(shareFile).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                    Toast.makeText( Activity2.this, "Go to Phone Settings -> Apps -> Voice Up ->Permissions -> enable Storage", Toast.LENGTH_LONG).show();
                            }
                        });


                    }
                });

            }
        };


        mRecordList.setAdapter(firebaseRecyclerAdapter);
    }


    public static class RecordViewHolder extends  RecyclerView.ViewHolder {

        View mView;

        ImageButton mLikeButton;
        Button shareButton;

        DatabaseReference mDatabaseLike;
        FirebaseAuth mAuth;
        DatabaseReference databaseRef;
        DatabaseReference userDatabase;

        public RecordViewHolder(View itemView) {

            super(itemView);

            mView = itemView;
            mLikeButton = (ImageButton) mView.findViewById(R.id.likeBtn);
            shareButton = (Button)mView.findViewById(R.id.share_button);
            mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
            databaseRef = FirebaseDatabase.getInstance().getReference().child("Records");
            userDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            mAuth = FirebaseAuth.getInstance();
            mDatabaseLike.keepSynced(true);

        }

        public void setUserName(DatabaseReference UserName, final Context ctx) {
            final ImageView user_image = (ImageView) mView.findViewById(R.id.userImage);
            final TextView user_name = (TextView) mView.findViewById(R.id.post_username);

            UserName.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.getValue() !=  null) {
                        System.out.println("dataSnapshot.getValue(UserTable.class) :" + dataSnapshot.getValue());
                        UserTable usertable= dataSnapshot.getValue(UserTable.class);

                        Uri image_uri = Uri.parse(usertable.getImage());
                        Picasso.with(ctx).load(image_uri).resize(100,100).centerInside().into(user_image);


                        user_name.setText(usertable.getName());
                    }
                    else {
                        user_image.setImageResource(R.drawable.com_facebook_profile_picture_blank_square);
                        user_name.setText("Anonymous");
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }





        public  void setLikeButton(final String postkey){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    //System.out.println("post_key : "  + dataSnapshot.child("Likes").getChildrenCount() +  "  mAuth.getCurrentUser().getUid())  : " + mAuth.getCurrentUser().getUid());
                    if( mAuth != null && mAuth.getCurrentUser() != null && mAuth.getCurrentUser().getUid() != null) {

                        // System.out.println(dataSnapshot.child("2322323").hasChild("Topic"));


                        if (dataSnapshot.child(postkey).hasChild(mAuth.getCurrentUser().getUid())) {
                            mLikeButton.setImageResource(R.drawable.like_red);
                        } else {
                            mLikeButton.setImageResource(R.drawable.like_grey);
                        }
                    }
                    LikesCount = dataSnapshot.child(postkey).getChildrenCount();
                    TextView post_title = (TextView) mView.findViewById(R.id.post_numberLikes);
                    System.out.println("LikesCount" + LikesCount);
                    if (LikesCount != null){
                        post_title.setText("Likes : " + LikesCount);
                        databaseRef.child(postkey).child("Number_of_Likes").setValue(LikesCount);

                    }else{
                        post_title.setText("Likes : 0");
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }




    private static final String TAG = "APP";
    private RecyclerView recycler;
    private SongAdapter mAdapter;
    private ArrayList<Records> songList = new ArrayList<>();
    private int currentIndex;
    private TextView tb_title, tb_duration, tv_time;
    private ImageView iv_play, iv_next, iv_previous;
    private ProgressBar pb_loader, pb_main_loader;
    //private MediaPlayer mediaPlayer;
    private long currentSongLength;
    private SeekBar seekBar;
    private boolean firstLaunch = true;
    private boolean shouldEndPreviousThread;

    //private FloatingActionButton fab_search;
    Runnable run = null;
    final Handler mHandler = new Handler();

    public void  BhaiKaMethod(){
        mAdapter = new SongAdapter(0);
        initializeViews();
        songList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //Lancer la chanson
                //shouldRunThread = true;
                togglePlay(mp);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                shouldRunThread = false;
                seekBar.setProgress(mediaPlayer.getCurrentPosition() / 1000);
                tv_time.setText(Utility.convertDuration((long) mediaPlayer.getCurrentPosition()));

                if (run != null) {
                    mHandler.removeCallbacks(run);
                }
                System.out.println("songList.size()  :"  +songList.size());
                System.out.println("currentIndex   :  " +currentIndex);
                if( mediaPlayer != null){
                    mediaPlayer.stop();
                }

                //Good logic and can be implemented if required
                /*if(currentIndex + 1 < songList.size()){
                    Records next = songList.get(currentIndex + 1);
                    changeSelectedSong(currentbIndex+1);
                    prepareSong(next);
                }else{
                    Records next = songList.get(0);
                    changeSelectedSong(0);
                    prepareSong(next);
                }*/
            }
        });


        handleSeekbar();

        //Controle de la chanson
        pushPlay();
        pushPrevious();
        pushNext();


    }

    private void handleSeekbar(){
        System.out.println("handleSeekbar");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void prepareSong(Records song){
        System.out.println("Inside prepareSong");
        if(run != null){
            System.out.println("run is not nulll");
            mHandler.removeCallbacks(run);
        }
        currentSongLength = song.getDuration();
        pb_loader.setVisibility(View.VISIBLE);
        tb_title.setVisibility(View.GONE);
        iv_play.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selector_play));
        tb_title.setText(song.getUid());
        tv_time.setText(Utility.convertDuration(song.getDuration()));
        String stream = song.getRecording();
        //mediaPlayer.release();
        if (mediaPlayer!=null){
            try{
        mediaPlayer.reset();}
            catch (Exception e){
              /*  mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                System.out.println("Media player Exception");*/
                e.printStackTrace();
            }}



        try {
            System.out.println("stream is :  " +stream);
            System.out.println("user id is :" +  song.getUid());
            //mediaPlayer = MediaPlayer.create(this, Uri.parse(stream));
            mediaPlayer.setDataSource(stream);
            mediaPlayer.prepareAsync();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void togglePlay(MediaPlayer mp){
        System.out.println("Inside togglePlay");


        if(mp.isPlaying()){
            mp.stop();
            mp.reset();
        }else{
            pb_loader.setVisibility(View.GONE);
            tb_title.setVisibility(View.VISIBLE);
            mp.start();
            iv_play.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.selector_pause));


            this.runOnUiThread(run = new Runnable() {
                @Override
                public void run() {

                        System.out.println("Seekbar functionality" + (int) currentSongLength + "    :  " + mediaPlayer.getCurrentPosition() / 1000);
                        seekBar.setMax((int) currentSongLength);
                        int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(mCurrentPosition);
                        tv_time.setText(Utility.convertDuration((long) mediaPlayer.getCurrentPosition()));
                        mHandler.postDelayed(this, 1000);
                       /* if((int) currentSongLength == mediaPlayer.getCurrentPosition() / 1000) {
                            mHandler.removeCallbacks(this);
                        }*/
                }


            });
        }

    }

    private void initializeViews(){

        tb_title = (TextView) findViewById(R.id.tb_title);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        iv_previous = (ImageView) findViewById(R.id.iv_previous);
        pb_loader = (ProgressBar) findViewById(R.id.pb_loader);
        //pb_main_loader = (ProgressBar) findViewById(R.id.pb_main_loader);
        //recycler = (RecyclerView) findViewById(R.id.recycler);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        tv_time = (TextView) findViewById(R.id.tv_time);
        //fab_search = (FloatingActionButton) findViewById(R.id.fab_search);

    }

    private void changeSelectedSong(int index){
        //firebaseRecyclerAdapter.notifyItemChanged(mAdapter.getSelectedPosition());
        System.out.println("Inside changeSelectedSong");
        currentIndex = index;
        mAdapter.setSelectedPosition(currentIndex);
        firebaseRecyclerAdapter.notifyItemChanged(currentIndex);
    }

    private void pushPlay(){

        iv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Inside  pushPlay");
                if(mediaPlayer != null && mediaPlayer.isPlaying() ){
                    iv_play.setImageDrawable(ContextCompat.getDrawable(Activity2.this, R.drawable.selector_play));
                    mediaPlayer.pause();
                }else{
                    System.out.println("firstLaunch  : " +firstLaunch);
                    if(firstLaunch){
                        Records song = songList.get(0);
                        changeSelectedSong(0);
                        prepareSong(song);
                    }else{
                        mediaPlayer.start();
                        firstLaunch = false;
                    }
                    iv_play.setImageDrawable(ContextCompat.getDrawable(Activity2.this, R.drawable.selector_pause));
                }

            }
        });
    }

    private void pushPrevious(){

        iv_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLaunch = false;
                if(mediaPlayer != null){

                    if(currentIndex - 1 >= 0){
                        Records previous = songList.get(currentIndex - 1);
                        changeSelectedSong(currentIndex - 1);
                        prepareSong(previous);
                    }else{
                        changeSelectedSong(songList.size() - 1);
                        prepareSong(songList.get(songList.size() - 1));
                    }

                }
            }
        });

    }


    private void pushNext(){

        System.out.println("Inside  pushNext");

        iv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLaunch = false;
                if(mediaPlayer != null){
                    System.out.println("currentIndex : "+  currentIndex );

                    if(currentIndex + 1 < songList.size()){
                        Records next = songList.get(currentIndex + 1);
                        changeSelectedSong(currentIndex + 1);
                        prepareSong(next);
                    }else{
                        changeSelectedSong(0);
                        prepareSong(songList.get(0));
                    }

                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isShare){
            isShare = false;
            //startActivity(Intent.makeMainActivity(Activity2.this));
            //Intent acrivity2= new Intent(Activity2);
            //startActivity(Intent.createChooser(Activity2.this, "Voice Up"));
            return;
        }
        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
            stopRecording();
        }
        ditchMediaPlayer();
        if(mediaPlayer != null) {
            try {
                mediaPlayer.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(run != null){
            System.out.println("run is not nulll");
            mHandler.removeCallbacks(run);
        }


        startActivity(new Intent(Activity2.this, Activity1.class));
    }
















    @Override
    public void onStop() {
        super.onStop();
        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
            stopRecording();
        }
        ditchMediaPlayer();
        if(mediaPlayer != null) {
            try {
                mediaPlayer.release();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        if(run != null){
            System.out.println("run is not nulll");
            mHandler.removeCallbacks(run);
        }
        //startActivity(new Intent(Activity2.this, Activity1.class));
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (record_button.getText().toString().toLowerCase().equals("stop recording")) {
            stopRecording();
        }
        ditchMediaPlayer();

    }





}
