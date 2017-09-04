package com.tatlicilar.visualeducation;

import android.content.Intent;
import android.os.Bundle;
import android.os.DeadSystemException;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by Asus on 11.08.2017.
 */

public class Register extends AppCompatActivity {
    private EditText usName,usSchool,usEmail,usSchoolYear,usPass,usConfigPass,usType;
    private Button btnRegister;
    private ProgressBar progressBar;
    private DatabaseReference db;
    private FirebaseAuth auth;
    private Firebase firebaseRef;
  //  public  String usId;
    ArrayList<Users> usersList=new ArrayList<Users>();
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Firebase.setAndroidContext(this);
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        usName = (EditText) findViewById(R.id.username_edtext);
        usSchool = (EditText) findViewById(R.id.school_edtext);
        usEmail = (EditText) findViewById(R.id.email_edtext);
        usSchoolYear = (EditText) findViewById(R.id.year_edtext);
        usPass = (EditText) findViewById(R.id.passwd_edtext);
        usConfigPass = (EditText) findViewById(R.id.cnfpasswd_edtext);
        usType = (EditText) findViewById(R.id.type_edtext);
        btnRegister = (Button) findViewById(R.id.register_button);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


       /* usConfigPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Register.this, ResetPasswordActivity.class));
            }
        });*/

      /*  btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final String name = usName.getText().toString().trim();
                final String type = usType.getText().toString().trim();
                final String school = usSchool.getText().toString().trim();
                final String email = usEmail.getText().toString().trim();
                final String schoolYear = usSchoolYear.getText().toString().trim();
                final String password = usPass.getText().toString().trim();
                final String configPass = usConfigPass.getText().toString().trim();


                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "E-posta adresinizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Şifrenizi giriniz", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Girilen şifre minimum 6 karakter olmalı", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Register.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //get Id yap
                                    Firebase ref = new Firebase("https://visual-education.firebaseio.com/\n");
                                    ref.authWithPassword(email, password, new Firebase.AuthResultHandler() {
                                        @Override
                                        public void onAuthenticated(AuthData authData) {
                                            System.out.println("User IDDDDDDDDD: " + authData.getUid() + ", Provider: " + authData.getProvider());
                                       //     usId=authData.getUid();
                                        }
                                        @Override
                                        public void onAuthenticationError(FirebaseError firebaseError) {
                                            // there was an error
                                        }
                                    });
                                  //  Increment();
                                    Users users = new Users(name, email,password, type, school, schoolYear);
                                 //   usersList.add(users);
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    db = FirebaseDatabase.getInstance().getReference();
                                    db.child("Users").child(userId).setValue(users);
                                  //  String usId=db.push().getKey();
                                    saveClassGiven(userId);
                                    subject(userId);
                                   // Bundle bundle = new Bundle();
                                   // bundle.putString("userId",userId);
                                    Intent intent=new Intent(getApplicationContext(), MainActivity.class);
                                    //intent.putExtras(bundle);
                                    startActivity(intent);
                                   // startActivity(new Intent(Register.this, MainActivity.class));
                                    finish();
                                }
                            }
                        });
                      }
        });
             }
   /*      public  int Increment()
          {
            // Firebase myRef=new Firebase("https://visual-education.firebaseio.com/\n");
            db=FirebaseDatabase.getInstance().getReference();
            final  DatabaseReference myUsersRef=db.child("Users");
            myUsersRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
              @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                  value =(int) dataSnapshot.child("userId").getValue();
                  System.out.print("userID"+value);
                  myUsersRef.child("userId").setValue(++value);
                  System.out.print("ıncrement "+value);
              }

              @Override
              public void onCancelled(DatabaseError databaseError) {
                  throw databaseError.toException();
              }
             } );
              return value;
         }*/
   public  void saveClassGiven(String userId)
   {
       databaseReference = FirebaseDatabase.getInstance().getReference("classesGiven");
      // String classGivenId=databaseReference.push().getKey();
       String lessonId="2";
       ClassesGiven classesGiven=new ClassesGiven(lessonId);
       databaseReference.child(userId).setValue(classesGiven);
       Toast.makeText(this, "ClassesGiven succesfully", Toast.LENGTH_SHORT).show();
       lesson(lessonId,userId);
       lesson_subject(userId,lessonId);


   }
    public  void lesson(String lessonId,String userId){
        //databaseReference = FirebaseDatabase.getInstance().getReference("lesson");
        String lessonName="Kimya";
        Lesson lesson=new Lesson(lessonId,lessonName);
        db.child("lesson").child(userId).setValue(lesson);
        Toast.makeText(this, "Lesson succesfully", Toast.LENGTH_SHORT).show();
        classLesson(lessonId,userId);
    }
    public  void subject(String subjectId) {

        databaseReference = FirebaseDatabase.getInstance().getReference("subject");
       // String subId=databaseClassGiv.push().getKey();
        String subName="Kimyasal Tepkimeler",subContext="",subVideo="";
        Subject subject=new Subject(subjectId,subName,subContext,subVideo);
        databaseReference.child(subjectId).setValue(subject);
        Toast.makeText(this, "ClassesGiven succesfully", Toast.LENGTH_SHORT).show();
    }
    public  void lesson_subject(String userId,String lessonId){
        databaseReference = FirebaseDatabase.getInstance().getReference("lessonSubject");
        String subjectId="2";
        String subjectOrder="1";
        LessonSubject lessonSubject=new LessonSubject(userId,lessonId,subjectId,subjectOrder);
        databaseReference.child(userId).setValue(lessonSubject);
        Toast.makeText(this, "ClassesGiven succesfully", Toast.LENGTH_SHORT).show();
    }
    public  void classLesson(String lessonId,String userId){
        databaseReference = FirebaseDatabase.getInstance().getReference("classLesson");
        String id="456";
        String lessonOrder="1";
        ClassLesson classLesson=new ClassLesson(id,lessonId,lessonOrder);
        databaseReference.child(userId).setValue(classLesson);
        Toast.makeText(this, "ClassesGiven succesfully", Toast.LENGTH_SHORT).show();
    }
      @Override
       protected void onResume() {
         super.onResume();
         progressBar.setVisibility(View.GONE);
      }
  }
//classGiven dan lesson ve diğerleri çağırılmalı çünkü lessonId ye göre işlem yapılıyor, gerekl, olan parametreler gönderilir..