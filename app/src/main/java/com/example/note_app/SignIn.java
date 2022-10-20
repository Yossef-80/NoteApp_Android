package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note_app.classes.Login;
import com.example.note_app.classes.UserData;
import com.example.note_app.notes.ApiInterface;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignIn extends AppCompatActivity {
    public static String Token;
    private EditText EmailField,PasswordField;
    int  redColor=Color.argb(250,255,3,3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        SharedPreferences sharedPreferences=getSharedPreferences("signIn",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://noteify-service.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);




        //password
        TextInputLayout password_layout=findViewById(R.id.passwordTextField);
        TextInputEditText passwordEditText=findViewById(R.id.passwordTextEdit);
        //email
        TextInputLayout Email_layout=findViewById(R.id.emailTextField);
        TextInputEditText EmailEditText=findViewById(R.id.emailEditText);

        MaterialButton signInBtn=findViewById(R.id.signIn_button);
        signInBtn.setOnClickListener(v -> {
            if (EmailEditText.getText().toString().equals(" ")||EmailEditText.getText().toString().length()==0)
            {
                Email_layout.setError("email empty");
            }
            else if(EmailEditText.getText().toString().length()<5) {
                Email_layout.setError("email less than 5");
            }

             else if (passwordEditText.getText().toString().equals("")||passwordEditText.getText().toString().length()==0)
            {
                Email_layout.setErrorEnabled(false);

                password_layout.setError("password empty");
            }
             else if(passwordEditText.getText().toString().length()<5)
            {
                password_layout.setErrorEnabled(false);
                password_layout.setError("password less than 5");

            }
             else {
                password_layout.setErrorEnabled(false);

                Login login=new Login(EmailEditText.getText().toString(),passwordEditText.getText().toString());
                Call<UserData> call=apiInterface.login(login);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        if (response.isSuccessful())
                        {
                            editor.putString("id",response.body().getId());
                            editor.putString("email",response.body().getEmail());
                            editor.putBoolean("signedIn",true);
                            editor.apply();

                            System.out.println("Email: "+response.body().getEmail());
                            System.out.println("The Token: "+response.body().getToken());
                            Token=response.body().getToken();
                            Intent intent =new Intent(getApplicationContext(),HomePage.class);
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

       // Button singInBtn = findViewById(R.id.sign_in_button);
        //EmailField=findViewById(R.id.editText_SingIn_EmailAddress);
        //PasswordField=findViewById(R.id.editText_SignIn_Password);
     /*   singInBtn.setOnClickListener(v -> {
            if(EmailField.getText().toString().equals(""))
            {
                EmailField.setHintTextColor(redColor);
                Toast.makeText(this,"Email Field is missing",Toast.LENGTH_SHORT).show();
            }
           else if(PasswordField.getText().toString().equals(""))
            {
                PasswordField.setHintTextColor(redColor);
                Toast.makeText(this,"Password Field is missing",Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(this,HomePage.class);
                startActivity(intent);
            }
        });*/
    }
}