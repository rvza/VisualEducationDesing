package com.tatlicilar.visualeducation;

import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.*;
import android.os.Bundle;
import android.app.*;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.share.widget.ShareDialog;
import com.firebase.client.AuthData;
import com.firebase.client.Config;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.R.attr.data;
import static android.R.attr.scaleType;

public class MainActivity extends AppCompatActivity
 implements GoogleApiClient.OnConnectionFailedListener {
    private EditText inputEmail, inputPassword,logGogSchool,logGogUserType,logGogUserClass,logGogPass;
    private FirebaseAuth mAuth;
    private TextView user_name;
    private ShareDialog shareDialog;
    private  DatabaseReference db;
    ArrayList<Users> usersList=new ArrayList<Users>();
    CallbackManager callbackManager = CallbackManager.Factory.create();
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, forgotPass, logGmail,logGogSaveInfo;
    LoginButton logFace;
    private static final int RC_SIGN_IN = 67;
    private static final int requestCode_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this);
        shareDialog = new ShareDialog(MainActivity.this);
        setContentView(R.layout.activity_main);
        //callbackManager = CallbackManager.Factory.create();
        //Get Firebase auth instance
        mAuth = FirebaseAuth.getInstance();

        logGogPass=(EditText)findViewById(R.id.logGogschool_edtext);
        logGogSchool =(EditText) findViewById(R.id.logGogschool_edtext);
        logGogUserType=(EditText)findViewById(R.id.logGogtype_edtext);
        logGogUserType =(EditText)findViewById(R.id.logGogtype_edtext);
        logGogUserClass=(EditText)findViewById(R.id.logGogyear_edtext) ;
        inputEmail = (EditText) findViewById(R.id.userEmail);
        inputPassword = (EditText) findViewById(R.id.userPass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.registerBtn);
        btnLogin = (Button) findViewById(R.id.loginBtn);
        forgotPass = (Button) findViewById(R.id.forgotPass);
        logGmail = (Button) findViewById(R.id.logGmail);
        logFace=(LoginButton) findViewById(R.id.login_buttonFace) ;
        user_name = (TextView)findViewById(R.id.txt_user_name);
        logFace.setReadPermissions(Arrays.asList("public_profile ","user_friends","email"));
        //Get Firebase auth instance

        forgotPass.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
                try {
                    //Find key hes                                                                      "
                    PackageInfo info = getPackageManager().getPackageInfo("com.tatlicilar.visualeducation", PackageManager.GET_SIGNATURES);
                    for (Signature signature : info.signatures) {
                        MessageDigest md = MessageDigest.getInstance("SHA");
                        md.update(signature.toByteArray());
                        System.out.print( Base64.encodeToString(md.digest(), Base64.DEFAULT));
                        Log.e("MY KEY HASH:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                        startActivity(new Intent(MainActivity.this, findPassword.class));
                    }
                } catch (PackageManager.NameNotFoundException e) {

                } catch (NoSuchAlgorithmException e) {
                }
            }
        });
        //Facebook login transaction
        logFace.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
         //when succesfully
            @Override
            public void onSuccess(LoginResult loginResult) {
               //find users'namess
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject user,
                                    GraphResponse response) {
                                try{

                                    String F_usName = user.getString("name");
                                    FaceLog_showDialog(F_usName);
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        });
                request.executeAsync();
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException e) {

            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.e("Google Login", "Kullanıcı Artık Yetkili. Kullanıcı ID : " + user.getUid());
                } else {
                    Log.e("Google Login", "Kullanıcı Artık Yetkili Değil.");
                }
            }
        };
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
          }
    private void signIn() {

          Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
          startActivityForResult(signInIntent, requestCode_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                Log.e("Google Login", "Oturum Açılıyor..");
                final GoogleSignInAccount account = result.getSignInAccount();
                AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                mAuth.signInWithCredential(credential)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.e("Google Login", "Oturum Google Hesabı ile açıldı.");
                                    String G_usName=(account.getGivenName()+account.getFamilyName());
                                    String G_usEmail=account.getEmail();
                                    GoogLog_showDialog(G_usName,G_usEmail);

                                } else {
                                    Log.e("Google Login", "Oturum Açılamadı.", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                } else {
                Log.e("Google Login", "Google hesabıyla oturum açma isteği yapılamadı.");
               }
            }
         logGmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
          });
      btnLogin.setOnClickListener(new View.OnClickListener()
       {
         @Override
         public void onClick (View v){
          final  String email = inputEmail.getText().toString().trim();
         final String password = inputPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            if (password.length() < 6) {
                                inputPassword.setError("Şifreniz çok kısa, minumum 6 karakter olmalıdır."+getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(MainActivity.this, getString(R.string.auth_failed),Toast.LENGTH_LONG).show();
                            }
                          } else {
                            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            Intent intent = new Intent(MainActivity.this, Home_page.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("userId",userId);
                            intent.putExtras(bundle);
                            startActivity(intent);
                            finish();
                        }
                       }
                     });
                 }});
               }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        //When login with google fails
    }
    public void FaceLog_showDialog(final String F_usName)
    {
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.facebook_aut_user_info, null);
        final EditText edit_F_usSchool = (EditText) textEntryView.findViewById(R.id.logFacschool_edtext);
        final EditText edit_F_usType = (EditText) textEntryView.findViewById(R.id.logFactype_edtext);
        final EditText edit_F_usClassYear = (EditText) textEntryView.findViewById(R.id.logFacyear_edtext);
        final EditText edit_F_usEposta = (EditText) textEntryView.findViewById(R.id.logGFacEmail_edtext);

        edit_F_usSchool.setHint("Okul");
        edit_F_usType.setHint("Kullanıcı Tipi");
        edit_F_usClassYear.setHint("Sınıf");
        edit_F_usEposta.setHint("E-poosta");


        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setIcon(R.drawable.com_facebook_button_icon).setTitle("Bilgilerim").setView(textEntryView).setPositiveButton("Kaydet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                          /* User clicked OK*/
          if(edit_F_usClassYear.getText().toString().trim().length() >0 &&edit_F_usEposta.getText().toString().trim().length()>0&&edit_F_usSchool.getText().toString().trim().length()>0
               &&edit_F_usType.getText().toString().trim().length()>0) {
                 final String edit_usSchool=edit_F_usSchool.getText().toString().trim();
                 final String edit_usType=edit_F_usType.getText().toString().trim();
                 final String edit_usClassYear=edit_F_usClassYear.getText().toString().trim();
                 final String edit_usEposta=edit_F_usEposta.getText().toString().trim();
          //    inputEmail.setText("Hoşgeldin"+""+edit_usSchool);

              mAuth.createUserWithEmailAndPassword(edit_usEposta, edit_usSchool)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //get Id yap
                                    Firebase ref = new Firebase("https://visual-education.firebaseio.com/\n");
                                    ref.authWithPassword(edit_usEposta, edit_usSchool, new Firebase.AuthResultHandler() {
                                        @Override
                                        public void onAuthenticated(AuthData authData) {
                                            System.out.println("User IDDDDDDDDD: " + authData.getUid() + ", Provider: " + authData.getProvider());
                                        }

                                        @Override
                                        public void onAuthenticationError(FirebaseError firebaseError) {
                                            // there was an error
                                        }
                                    });
                                  //  Increment();
                                    Users users = new Users(F_usName,edit_usEposta,"",edit_usType,edit_usSchool,edit_usClassYear);
                                    usersList.add(users);
                                   db = FirebaseDatabase.getInstance().getReference();
                                    db.child("Users").push().setValue(users);
                                    String userId=db.push().getKey();
                                    Intent intent=new Intent(MainActivity.this,Home_page.class);
                                    intent.putExtra("usId",userId);
                                    intent.putExtra("usLogEmail",edit_usEposta);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


            }
          else
             {
              Toast.makeText(getApplicationContext(), "Lütfen bilgilerinizi eksiksiz doldurunuz", Toast.LENGTH_SHORT).show();
                 FaceLog_showDialog(F_usName);
             }
                    }
                }).setNegativeButton("Ana Sayfaya Dön",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                       /*User clicked cancel */
                       dialog.cancel();
                    }
                });
        alert.show();

    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    public void GoogLog_showDialog(final String G_usName,final  String G_usEmail)
    {
        LayoutInflater factory = LayoutInflater.from(this);
       //text_entry is an Layout XML file containing two text field to display in alert dialog
        final View textEntryView = factory.inflate(R.layout.google_aut_user_info, null);
        final EditText edit_G_usSchool = (EditText) textEntryView.findViewById(R.id.logGogschool_edtext);
        final EditText edit_G_usClassYear = (EditText) textEntryView.findViewById(R.id.logGogyear_edtext);
        final EditText edit_G_usType = (EditText) textEntryView.findViewById(R.id.logGogtype_edtext);

        edit_G_usSchool.setHint("Okul");
        edit_G_usType.setHint("Kullanıcı Tipi");
        edit_G_usClassYear.setHint("Sınıf");


        final android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(this);
        alert.setIcon(R.drawable.com_facebook_button_icon).setTitle("Bilgilerim").setView(textEntryView).setPositiveButton("Kaydet",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                          /* User clicked OK*/
                        if(edit_G_usSchool.getText().toString().trim().length() >0 &&edit_G_usType.getText().toString().trim().length()>0&&edit_G_usClassYear.getText().toString().trim().length()>0) {
                            String edit_usSchool=edit_G_usSchool.getText().toString().trim();
                            String edit_usType=edit_G_usType.getText().toString().trim();
                            String edit_usClassYear=edit_G_usClassYear.toString().trim();
                            Users users=new Users(G_usName,G_usEmail,"",edit_usType,edit_usSchool,edit_usClassYear);
                            String userId=db.push().getKey();
                            db = FirebaseDatabase.getInstance().getReference();
                            db.child("Users").push().setValue(users);
                            Intent intent = new Intent(MainActivity.this, Home_page.class);
                            intent.putExtra("G_usEmail",G_usEmail);
                            intent.putExtra("usId",userId);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Lütfen bilgilerinizi eksiksiz doldurunuz", Toast.LENGTH_SHORT).show();
                            FaceLog_showDialog(G_usName);
                        }
                    }
                }).setNegativeButton("Ana Sayfaya Dön",
                      new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                 /*User clicked cancel */
                        dialog.cancel();
                    }
                });
        alert.show();

    }
    }
