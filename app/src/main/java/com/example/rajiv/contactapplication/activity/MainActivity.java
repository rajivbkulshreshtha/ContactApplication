package com.example.rajiv.contactapplication.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.rajiv.contactapplication.R;
import com.example.rajiv.contactapplication.adapter.ContactAdapter;
import com.example.rajiv.contactapplication.pojo.Contact;
import com.example.rajiv.contactapplication.provider.ContactFetcher;
import com.example.rajiv.contactapplication.singleton.SingletonSavesContext;
import com.example.rajiv.contactapplication.utils.Constant;
import com.example.rajiv.contactapplication.utils.MyApplication;
import com.squareup.leakcanary.RefWatcher;

import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    
    private RecyclerView contactRecyclerView;
    private List<Contact> mContactList = Collections.EMPTY_LIST;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        linearLayout = (LinearLayout) findViewById(R.id.activity_main_container);
        SingletonSavesContext.getInstance().setContext(this);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

            mContactList = ContactFetcher.getInstance(this).fetchAll();
            initContactRecyclerView();

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, Constant.PERMISSION_READ_CODE);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, Constant.PERMISSION_READ_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constant.PERMISSION_READ_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    mContactList = ContactFetcher.getInstance(this).fetchAll();
                    initContactRecyclerView();

                } else {

                    Snackbar snackbar = Snackbar
                            .make(linearLayout, "Permission required", Snackbar.LENGTH_LONG)
                            .setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CONTACTS}, Constant.PERMISSION_READ_CODE);
                                }
                            });

                    snackbar.show();
                }
                return;
            }
        }
    }


    private void initContactRecyclerView() {

        ContactAdapter contactAdapter = new ContactAdapter(this, mContactList);
        contactRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contactRecyclerView.setAdapter(contactAdapter);
        contactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = MyApplication.getRefWatcher(this);
        refWatcher.watch(this);
    }
}



