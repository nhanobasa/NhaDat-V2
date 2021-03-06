package com.example.nhadat_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseUser;

import java.util.Calendar;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private Button btn, btnLogup, btnDate;
    private EditText txtTen, txtUs, txtPass, txtSDT, txtEmail, txtDC, txtDate;
    private RadioGroup rd;
    private ParseUser user;
    private RelativeLayout reLoad, re;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setID();
        setListener();
        back4App();
    }

    private void setID(){
        btn=findViewById(R.id.btnSigIn);
        reLoad=findViewById( R.id.reLoadSignUp);
        btnLogup=findViewById(R.id.logup_btnsigup);
        txtUs=findViewById( R.id.txtSigUp_Username );
        txtPass=findViewById( R.id.txtSigUp_Password );
        txtTen=findViewById( R.id.txtSigUp_Fullname );
        txtSDT=findViewById( R.id.txtSigUp_Phone );
        txtEmail=findViewById( R.id.txtSigUp_Email );
        txtDC=findViewById( R.id.txtSigUp_Addr );
        rd=findViewById( R.id.rdGioiTinh );
        txtDate=findViewById(R.id.txtSigUp_Date);
        btnDate=findViewById(R.id.btnSigUp_Date);
        re=findViewById(R.id.re42);

        try {
            Intent a=getIntent();
            String s=a.getStringExtra("activity");
            if(s.equalsIgnoreCase("profile")==true){
                re.setVisibility(View.GONE);
            }
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }


    }

    private void back4App(){
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_app_id))
                .clientKey(getString(R.string.back4app_client_key))
                .server(getString(R.string.back4app_server_url))
                .build());
    }

    private void setListener(){
        btnLogup.setOnClickListener( this );
        btnDate.setOnClickListener(this);
        btn.setOnClickListener( this );
    }

    boolean checkEmail(CharSequence  email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            //button ????ng nh???p
            case R.id.btnSigIn:{
                finish();
                break;
            }

            case R.id.btnSigUp_Date:{
                showDate();
                break;
            }
            //button ????ng k??
            case R.id.logup_btnsigup:{
                dangKy();
                break;
            }
        }
    }

    //dang ky
    private void dangKy(){
        if(txtUs.getText().toString().isEmpty()){
            txtUs.setError("T??n ????ng nh???p tr???ng");
        }
        else if(txtPass.getText().toString().isEmpty()){
            txtPass.setError("M???t kh???u tr???ng");
        }
        else if(txtEmail.getText().toString().isEmpty()){
            txtEmail.setError("Email tr???ng");
        }
        else if(txtTen.getText().toString().isEmpty()){
            txtTen.setError("H??? v?? t??n tr???ng");
        }
        else if(txtSDT.getText().toString().isEmpty()){
            txtSDT.setError("S??? ??i???n tho???i tr???ng");
        }
        else if(txtDC.getText().toString().isEmpty()){
            txtDC.setError("?????a ch??? tr???ng");
        }
        else if (txtUs.getText().toString().isEmpty() | txtPass.getText().toString().isEmpty() |
                txtEmail.getText().toString().isEmpty() | txtTen.getText().toString().isEmpty() |
                txtSDT.getText().toString().isEmpty() | txtDC.getText().toString().isEmpty()){
            Toast.makeText(this, "B???n c???n ??i???n ?????y ????? th??ng tin tr?????c khi ????ng k??", Toast.LENGTH_LONG).show();
        }
        else{
            if(txtPass.getText().toString().length()>16){
                txtPass.setError("Password kh??ng ???????c qu?? 16 k?? t???");
            }
            else if(!checkEmail(txtEmail.getText().toString())){
                txtEmail.setError("Email kh??ng h???p l???");
            }
            else{
                String u=((RadioButton)findViewById( rd.getCheckedRadioButtonId() )).getText().toString();
                user=new ParseUser();
                user.setUsername(txtUs.getText().toString());
                user.setPassword( txtPass.getText().toString() );
                user.setEmail( txtEmail.getText().toString() );
                user.put( "fullname" , txtTen.getText().toString());
                user.put( "sex" , u);
                user.put( "date" , txtDate.getText().toString());
                user.put( "phone", txtSDT.getText().toString() );
                user.put( "address", txtDC.getText().toString() );

                reLoad.setVisibility( View.VISIBLE );
                user.signUpInBackground(e -> {
                    if(e==null){
                        ParseUser.logOut();
                        Animation ani= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                        ani.setStartOffset(1200);
                        ani.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                Intent b=getIntent();
                                String ss=b.getStringExtra("activity");
                                if(ss.equalsIgnoreCase("dangtin")==true){
                                    showMessage("T??i kho???n ???? ???????c t???o th??nh c??ng!",
                                            "Vui l??ng x??c nh???n email tr?????c khi th???c hi???n ????ng nh???p",ss, false);
                                }
                                if(ss.equalsIgnoreCase("manager")==true){
                                    showMessage("T??i kho???n ???? ???????c t???o th??nh c??ng!",
                                            "Vui l??ng x??c nh???n email tr?????c khi th???c hi???n ????ng nh???p",ss, false);
                                }
                                else{
                                    showMessage1("T??i kho???n ???? ???????c t???o th??nh c??ng!",
                                            "Vui l??ng x??c nh???n email tr?????c khi th???c hi???n ????ng nh???p", false);;
                                }
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        reLoad.startAnimation(ani);
                    }
                    else{
                        ParseUser.logOut();
                        Animation ani= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.animation);
                        ani.setStartOffset(1200);
                        ani.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                showMessage1("T??i kho???n ch??a ???????c t???o th??nh c??ng!", "L???i :"+e.getMessage(), true);                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                        reLoad.startAnimation(ani);

                    }
                });
            }
        }
    }

    private void showMessage(String tittle, String message, String ss, boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this)
                .setTitle(tittle)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    if (!error) {
                        Intent a=new Intent(SignUp.this, SignIn.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        String px=txtUs.getText().toString()+"noi"+txtPass.getText().toString();
                        a.putExtra( "dangky", px );
                        a.putExtra("activity", ss);
                        startActivity( a );
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    private void showMessage1(String tittle, String message, boolean error){
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUp.this)
                .setTitle(tittle)
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> {
                    dialog.cancel();
                    if (!error) {
                        Intent a=new Intent(SignUp.this, Profile.class);
                        a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity( a );
                    }
                });
        AlertDialog ok = builder.create();
        ok.show();
    }

    //date piker dialog

    private void showDate(){
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog a=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txtDate.setText(year+"/"+(month+1)+"/"+dayOfMonth);
            }
        }, year, month, day);
        a.show();
    }
}