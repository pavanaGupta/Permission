package com.example.ashwanigupta.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="main";
    public static final int PERM_REQ_CODE=111;

    EditText etToSave;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etToSave=(EditText)findViewById(R.id.etToSave);
        btnSave=(Button)findViewById(R.id.btnSave);

        //taking permission when the activity loads
        int perm= ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(perm== PackageManager.PERMISSION_DENIED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                Toast.makeText(this, "Grant the permission please.Please.",Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this,new String[]
                    {
                            Manifest.permission.WRITE_EXTERNAL_STORAGE       //string of array for all the permissions that need to be asked
                    },PERM_REQ_CODE);
        }



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile("MYFILE", etToSave.getText().toString());
            }
        });
    }

    void writeToFile(String fileName, String data)
    {
        if( ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED)
        {
            Toast.makeText(this, "GO TO SETTINGS AND GRANT PERMISSION", Toast.LENGTH_SHORT).show();
            return;
        }

        File fileToWrite= new File(Environment.getExternalStorageDirectory(),fileName);
        FileOutputStream fOutStr=null;

        try {
            fOutStr=new FileOutputStream(fileName, true);
            fOutStr.write(data.getBytes());
            fOutStr.close();

        } catch (IOException e) {
            Log.d(TAG, "writeToFile: ",e);
            e.printStackTrace();
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode==PERM_REQ_CODE)
//        {
//            for(int i=0;i< permissions.length;i++)
//            {
//                if(permissions[i].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
//                {
//                    grantResults[i]==PackageManager.PERMISSION_GRANTED? writeToFile(): Toast.makeText(this, "PERMISSION NOT GRANTED. CANNOT PROCEED.", Toast.LENGTH_SHORT).show();
//                }
//            }
//        }
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//    }
}
