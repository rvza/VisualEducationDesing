package com.tatlicilar.visualeducation;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//   compile 'com.android.support:design:25.3.1-alpha1'
import static com.tatlicilar.visualeducation.R.styleable.ActionBar;
import static com.tatlicilar.visualeducation.R.styleable.NavigationView;

/**
 * Created by Asus on 15.08.2017.
 */

public class Home_page extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    ExpandableListAdapter mMenuAdapter;
    ExpandableListView expandableList;
    List<ExpandedMenuModel> listDataHeader;
    HashMap<ExpandedMenuModel, List<String>> listDataChild;
    int lastPosition = -1;
    TextView gelenverideneme;
   //private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
   // final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    FirebaseDatabase db=FirebaseDatabase.getInstance();
    DatabaseReference myRef=db.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        final ActionBar ab = getSupportActionBar();
        //set menu icon
        ab.setHomeAsUpIndicator(android.R.drawable.ic_menu_add);
        ab.setDisplayHomeAsUpEnabled(true);
        gelenverideneme = (TextView) findViewById(R.id.gelenverideneme);
        //  mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        expandableList = (ExpandableListView) findViewById(R.id.navigationmenu);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Bundle dataUserId = getIntent().getExtras();
        String userId = dataUserId.getString("userId");
     //   Toast.makeText(getApplicationContext(), "Comeeeennn userId " + userId, Toast.LENGTH_SHORT).show();
        DatabaseReference databaseReference = db.getInstance().getReference().child("Users").child(userId);
        DatabaseReference refLesson =db.getInstance().getReference().child("lesson").child(userId);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }
        //İçeriğin doldurulma kısmı
        prepareListData();

        mMenuAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild, expandableList);

        // setting list adapter
        expandableList.setAdapter(mMenuAdapter);

        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                //Log.d("DEBUG", "sub menu click");
                return false;
            }
        });
        expandableList.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                //Log.d("DEBUG", "head click");
                return false;
            }
        });
        //bir ıtem ı açtığım da diğer açık olanların kapanmaı için
        expandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                // TODO Auto-generated method stub
                if (lastPosition != -1 && lastPosition != groupPosition) {
                    expandableList.collapseGroup(lastPosition);
                }
                lastPosition = groupPosition;
            }
        });

        refLesson.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //  Lesson lesson = new Lesson();
                //  lesson = dataSnapshot.getValue(Lesson.class);
                //  gelenverideneme.setText(lesson.lessonId + "" + lesson.lessonName);
                    //this is all you need to get a specific user by Uid


                  Lesson lesson = new Lesson();
                        lesson = dataSnapshot.getValue(Lesson.class);
                        Toast.makeText(getApplicationContext(),"Comeeeennnnnnnnnnnnnnnnnnnnnnn serId " +lesson.lessonName+lesson.lessonId, Toast.LENGTH_SHORT).show();



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

   /* databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Users users = new Users();
                users = dataSnapshot.getValue(Users.class);
                gelenverideneme.setText(users.userName + "" + users.userPassword);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/



    }




    private void prepareListData() {
        listDataHeader = new ArrayList<ExpandedMenuModel>();
        listDataChild = new HashMap<ExpandedMenuModel, List<String>>();
        NavigationDrawerItem navItem=new NavigationDrawerItem();

        ExpandedMenuModel item1 = new ExpandedMenuModel();
        item1.setIconName("Fizik");
        item1.setIconImg(R.drawable.pencil_images);
        // Adding data header
        listDataHeader.add(item1);

        ExpandedMenuModel item2 = new ExpandedMenuModel();
        item2.setIconName("Kimya");
        item2.setIconImg(R.drawable.pencil_images);
        listDataHeader.add(item2);

        ExpandedMenuModel item3 = new ExpandedMenuModel();
        item3.setIconName("Biyoloji");
        item3.setIconImg(R.drawable.pencil_images);
        listDataHeader.add(item3);

        // Adding child data
        List<String> heading1 = new ArrayList<String>();
        heading1.add("Madde ve Özellikleri");

        List<String> heading2 = new ArrayList<String>();
        heading2.add("Kimyasal Tepkimeler");
        heading2.add("Asit Baz");
        heading2.add("Periyodik Cetvel");

        listDataChild.put(listDataHeader.get(0), heading1);// Header, Child data
        listDataChild.put(listDataHeader.get(1), heading2);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        //revision: this don't works, use setOnChildClickListener() and setOnGroupClickListener() above instead
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }
}
 /*gelendeneme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                     ValueEventListener dinle = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //veri değiştiginde
                        Bundle dataUserId=getIntent().getExtras();
                        String userId=dataUserId.getString("userId");
                        Toast.makeText(getApplicationContext(),"Comeeeennn serId " +userId, Toast.LENGTH_SHORT).show();
                        Users k = new Users();
                        k = dataSnapshot.getValue(Users.class);
                        gelenverideneme.setText(k.userName + "" + k.userPassword);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //herhnagi bir hata ile karşılaşıldığında
                    }
                };
                read.addValueEventListener(dinle);
            }
        });*/