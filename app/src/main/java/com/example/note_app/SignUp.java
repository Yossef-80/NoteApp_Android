package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_app.classes.Login;
import com.example.note_app.classes.UserData;
import com.example.note_app.notes.ApiInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUp extends AppCompatActivity {
   private Button signUpBtn;
    private TextInputLayout NameLayout, PasswordLayout, EmailLayout;
    private TextInputEditText NameText,PasswordText,EmailText;
    TextView ihaveAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences=getSharedPreferences("signIn",MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        if (sharedPreferences.getBoolean("signedIn",false))
        {
            Intent intent=new Intent(this,HomePage.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_sign_up);

        //layout
        signUpBtn=findViewById(R.id.sign_Up_button);
        NameLayout =findViewById(R.id.name_signUp_layout);
        PasswordLayout =findViewById(R.id.password_signUp_layout);
        EmailLayout =findViewById(R.id.Email_signUp_layout);
        //editText
        NameText=findViewById(R.id.editText_SignUP_PersonName);
        EmailText=findViewById(R.id.editText_SingUp_EmailAddress);
        PasswordText=findViewById(R.id.editText_SignUP_Password);


        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://noteify-service.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        ihaveAccount=findViewById(R.id.iHaveAccount);
        ihaveAccount.setOnClickListener(v -> {
            Intent intent =new Intent(this,SignIn.class);
            startActivity(intent);
        });
        signUpBtn.setOnClickListener(v -> {
            if(NameText.getText().toString().equals("")|| NameText.getText().toString().length()==0)
            {
              NameLayout.setError("empty Field");
            }
            else if(EmailText.getText().toString().equals("")|| EmailText.getText().toString().length()==0)
            {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setError("empty Field");
            }
            else if(EmailText.getText().toString().length()<5) {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setErrorEnabled(false);
                EmailLayout.setError("email less than 5");
            }
            else if(!EmailText.getText().toString().contains("@")) {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setErrorEnabled(false);
                EmailLayout.setError("Not An Email");
            }
           else  if(PasswordText.getText().toString().equals("")|| PasswordText.getText().toString().length()==0)
            {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setErrorEnabled(false);
                PasswordLayout.setError("Empty Field");
            }
            else if(PasswordText.getText().toString().length()<5) {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setErrorEnabled(false);
                PasswordLayout.setErrorEnabled(false);
                PasswordLayout.setError("email less than 5");
            }
            else
            {
                NameLayout.setErrorEnabled(false);
                EmailLayout.setErrorEnabled(false);
                PasswordLayout.setErrorEnabled(false);
                Login login=new Login(NameText.getText().toString(), EmailText.getText().toString(), PasswordText.getText().toString());
                Call<UserData> call=apiInterface.SignUp(login);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if (response.isSuccessful())
                        {

                            System.out.println("Email: "+response.body().getEmail());
                            System.out.println("The Token: "+response.body().getToken());

                            Toast.makeText(getApplicationContext(),"Successful Sign Up",Toast.LENGTH_SHORT).show();
                             Intent intent =new Intent(getApplicationContext(),SignIn.class);
                            startActivity(intent);
                            finish();
                        }
                        else{

                            Toast.makeText(getApplicationContext(),"Data May Be Wrong",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"the Data May Be Wrong Or Connection Lost",Toast.LENGTH_LONG).show();

                    }
                });

            }

        });
    }
}