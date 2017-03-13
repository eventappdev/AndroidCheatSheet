package com.androidprojects.androidcheatsheet.Firebase;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.androidprojects.androidcheatsheet.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Nikhitha Tarala on 2/21/2017.
 */

public class FirebaseActivity extends AppCompatActivity {



    private static final String TAG = "FirebaseActivity";
    private static final int RC_SIGN_IN=1;
    private static final int RC_PHOTO_PICKER =  2;
    public static final String ANONYMOUS = "anonymous";
    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mfirebaseAuth;
    private FirebaseAuth.AuthStateListener mFireAuthStateListener;
    private ChildEventListener childEventListener;/*
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;*/
    FriendlyMessage friendlyMessage;
    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);

        mUsername = ANONYMOUS;
        //firebase database implementation
        firebaseDatabase= FirebaseDatabase.getInstance();
        mfirebaseAuth=FirebaseAuth.getInstance();
        databaseReference=firebaseDatabase.getReference().child("messages");
       // storageReference=firebaseStorage.getReference().child("chat_photos");

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);

        // Initialize message ListView and its adapter
        List<FriendlyMessage> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.activity_firebase_item_message, friendlyMessages);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Send messages on click
                friendlyMessage=new FriendlyMessage(mMessageEditText.getText().toString(),mUsername,null);
                databaseReference.push().setValue(friendlyMessage);
                Log.e("message sent","db");
                // Clear input box
                mMessageEditText.setText("");
            }
        });

        // Send button sends a message and clears the EditText
        mFireAuthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mUser=firebaseAuth.getCurrentUser();
                if(mUser!=null){

                    signInIntitialise(mUser.getDisplayName());
                }
                else{
                    onSignedOut();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build(),
                                            new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()
                                           ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
     
   };



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
    if (requestCode == RC_SIGN_IN) {

        // Successfully signed in
        if (resultCode == ResultCodes.OK) {

        } else if (resultCode == RESULT_CANCELED) {
            finish();
        }
        else if(resultCode == RC_PHOTO_PICKER && resultCode == RESULT_OK){
           /* Uri selectedImage=data.getData();
            StorageReference storageRef=storageReference.child(selectedImage.getLastPathSegment());
            storageRef.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Uri downloadUrl=taskSnapshot.getDownloadUrl();
                    FriendlyMessage friendlyMsg = new FriendlyMessage(null,mUsername,downloadUrl.toString());
                    databaseReference.push().setValue(friendlyMsg);
                }
            });*/
        }

        
        }

    }


    private void onSignedOut() {

        mMessageAdapter.clear();
        mUsername=ANONYMOUS;
        detachDataBaseListener();
    }

    private void detachDataBaseListener() {
        if(childEventListener!=null) {

            databaseReference.removeEventListener(childEventListener);
            childEventListener=null;        }
    }

    private void signInIntitialise(String userName) {
        mUsername=userName;
        attachDataFromDatabase();
    }

    private void attachDataFromDatabase() {

        if (childEventListener==null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    FriendlyMessage friendlyMessage = dataSnapshot.getValue(FriendlyMessage.class);
                    mMessageAdapter.add(friendlyMessage);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };
        }
        databaseReference.addChildEventListener(childEventListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        mfirebaseAuth.addAuthStateListener(mFireAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mfirebaseAuth.removeAuthStateListener(mFireAuthStateListener);
        detachDataBaseListener();
        mMessageAdapter=null;
    }
}
