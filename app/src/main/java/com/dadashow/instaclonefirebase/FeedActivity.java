package com.dadashow.instaclonefirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> emails;
    ArrayList<String> comments;
    ArrayList<String> urls;
    RecyclerView recyclerView;
    FeedRecycleAdapter feedRecycleAdapter;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.upload_menu_options,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (R.id.sign_out==item.getItemId()){
            firebaseAuth.signOut();
            Intent intentToSignUp= new Intent(FeedActivity.this,SignUpActivity.class);
            startActivity(intentToSignUp);
            finish();
        }else  if(R.id.add_post==item.getItemId()){
            Intent intentToUpload= new Intent(FeedActivity.this,UploadActivity.class);
            startActivity(intentToUpload);
        }else if (R.id.refresh==item.getItemId()){
            Intent intent=new Intent(FeedActivity.this,FeedActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        comments=new ArrayList<>();
        urls=new ArrayList<>();
        emails=new ArrayList<>();
        getData();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         feedRecycleAdapter=new FeedRecycleAdapter(emails,comments,urls);
        recyclerView.setAdapter(feedRecycleAdapter);

    }
    public void getData(){
        CollectionReference collectionReference=firebaseFirestore.collection("Posts");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
            if (error!=null){
                Toast.makeText(FeedActivity.this,error.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            }
            if (value!=null){

                for (DocumentSnapshot snapshot:value.getDocuments()){
                    Map<String ,Object> map=snapshot.getData();
                    String comment=(String)map.get("comment");
                    String downloaduri=(String)map.get("downloaduri");
                    String email=(String)map.get("email");
                    System.out.println(email);
                    comments.add(comment);
                    urls.add(downloaduri);
                    emails.add(email);
                }
                feedRecycleAdapter.notifyDataSetChanged();

                comments=new ArrayList<>();
                urls=new ArrayList<>();
                emails=new ArrayList<>();

            }
            }
        });
    }

}