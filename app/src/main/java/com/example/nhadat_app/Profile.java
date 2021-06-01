package com.example.nhadat_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nhadat_app.DB.SQLiteDatabase;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity implements View.OnClickListener {
    private ImageButton back;
    private Button btnUpdate, btnLogOut, btnSignIn, btnSignUp;
    private String loin;
    private TextView fullname, email, phone, address, date;
    private RelativeLayout re_1, re_2;
    private SQLiteDatabase db;
    private String category="";
    private CircleImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setID();
        setListener();
        db=new SQLiteDatabase(this);
        category=db.getTK();
        if(category.equalsIgnoreCase("No")==true){
            re_1.setVisibility(View.GONE);
            re_2.setVisibility(View.VISIBLE);
        }
        else{
            re_1.setVisibility(View.VISIBLE);
            re_2.setVisibility(View.GONE);
            try {
                fullname.setText(ParseUser.getCurrentUser().getString("fullname"));
                String[] arr = ParseUser.getCurrentUser().getCreatedAt().toString().split(" ");
                date.setText(arr[2] + " " + arr[1] + " " + arr[5]);
                email.setText(ParseUser.getCurrentUser().getString("email"));
                phone.setText(ParseUser.getCurrentUser().getString("phone"));
                address.setText(ParseUser.getCurrentUser().getString("address"));
                Picasso.get().load(Uri.parse(ParseUser.getCurrentUser().
                                    getString("imgurl"))).into(img);
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
            }
        }
    }

    private void setID(){
        back=findViewById(R.id.profile_back);
        fullname=findViewById(R.id.profile_fullname);
        email=findViewById(R.id.profile_email);
        phone=findViewById(R.id.profile_phone);
        address=findViewById(R.id.profile_address);
        date=findViewById(R.id.profile_date);
        btnUpdate=findViewById(R.id.chage_profile1);
        btnLogOut=findViewById(R.id.logout);
        btnSignIn=findViewById(R.id.chage_signin);
        btnSignUp=findViewById(R.id.profile_signup);
        re_1=findViewById(R.id.re_btnchange);
        img=findViewById(R.id.image_profilee);
        re_2=findViewById(R.id.re_buttonsign);

    }

    private void setListener(){
        back.setOnClickListener(this);
        btnLogOut.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onClick(View v) {
        switch ((v.getId())){
            case R.id.profile_back:{
                Intent a=new Intent(this, MainActivity.class);
                a.putExtra("type","yes");
                startActivity(a);
                break;
            }
            case R.id.chage_profile1:{
                Intent a=new Intent(this, ChangeProfile.class);
                startActivity(a);
                break;
            }
            case R.id.chage_signin:{
                Intent a=new Intent(this, SignIn.class);
                a.putExtra("activity", "profile");
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                break;
            }
            case R.id.profile_signup:{
                Intent a=new Intent(this, SignUp.class);
                a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                a.putExtra("activity", "profile");
                startActivity(a);
                break;
            }
            case R.id.logout:{
                ParseUser.logOutInBackground(e -> {
                    if(e==null){
                        db.xoaTK("Yes");
                        db.themTK("No");
                        re_1.setVisibility(View.GONE);
                        re_2.setVisibility(View.VISIBLE);
                        fullname.setText("");
                        date.setText("");
                        email.setText("");
                        phone.setText("");
                        address.setText("");
                        img.setImageResource(R.drawable.circle);
                    } });
                break;
            }
        }
    }
}