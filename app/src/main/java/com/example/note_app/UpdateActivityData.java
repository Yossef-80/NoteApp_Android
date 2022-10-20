package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.note_app.notes.ApiInterface;
import com.example.note_app.notes.Id;
import com.example.note_app.notes.Note;
import com.example.note_app.notes.Notes_frag;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivityData extends AppCompatActivity {
    TextInputLayout newTitle,newContent;
    TextInputEditText newTitleText,newContentText;
    MaterialButton UpdateBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
        UpdateBtn=findViewById(R.id.UpdateButton);
        newTitle=findViewById(R.id.NewTitleLayoutText);
        newContent=findViewById(R.id.NewContentLayoutText);
        newTitleText=findViewById(R.id.NewTitleEditText);
        newContentText=findViewById(R.id.NewContentEditText);
        Bundle bundle = getIntent().getExtras();
        String title=bundle.getString("title");
        String body=bundle.getString("body");
        String id=bundle.getString("id");
        newTitleText.setText(title);
        newContentText.setText(body);
        UpdateBtn.setOnClickListener(v -> {
            if(newTitleText.getText().toString().length()==0||newTitleText.getText().toString().equals(""))
            {
                newTitle.setError("Missing field");
            }
            else if(newContentText.getText().toString().length()==0||newContentText.getText().toString().equals(""))
            {
                newTitle.setErrorEnabled(false);
                newContent.setError("Missing field");
            }
            else {
                newContent.setErrorEnabled(false);
                String newTitle=newTitleText.getText().toString();
                String newContent=newContentText.getText().toString();
                ApiInterface apiInterface=Notes_frag.retrofit.create(ApiInterface.class);
                System.out.println("new Title "+newTitleText.getText().toString()+" new Content "+newContentText.getText().toString());
               NotesForUpdate note=new NotesForUpdate(id,newTitle,newContent);

                Call<Id> call=apiInterface.UpdateNote("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJva2F5YTFAZ21haWwuY29tIiwiaWQiOiI2MzMzNTcxNWRiOWYzMDAwMjFmY2E4YTIiLCJleHBpcmF0aW9uRGF0ZSI6MTY2OTQ5MzAyOSwiaWF0IjoxNjY0MzA5MDI5fQ.-oFPzPRldPO-B8DNMN3XXfLTUp_dgLkCO_qAnYZviZA",note);
                call.clone().enqueue(new Callback<Id>() {
                    @Override
                    public void onResponse(Call<Id> call, Response<Id> response) {
                        if(response.isSuccessful())
                        {
                            System.out.println("id of updated"+response.body().getId());
                            Toast.makeText(getApplicationContext(),"Note Updated Successfully",Toast.LENGTH_SHORT).show();

                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Update Failed",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Id> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"The Connection Lost",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });



    }
}