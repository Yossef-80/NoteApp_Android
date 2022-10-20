package com.example.note_app.notes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_app.R;
import com.example.note_app.ToDo.ToDoFrag;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNotes extends AppCompatActivity {
    TextInputEditText title ,content,year,month,day;
    String tag;
    TextInputLayout titleLayout,contentLayout,YearLayout,MonthLayout,DayLayout;
    Note note;
    Date currentDate;
    ConstraintLayout datePick;
    ImageButton cancel;
    SwitchMaterial switchMaterial;
    TextView add,note_OR_Task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        titleLayout=findViewById(R.id.TitleTextField);
        contentLayout=findViewById(R.id.DetailsTextField);
        YearLayout=findViewById(R.id.AlarmYearDataField);
        MonthLayout=findViewById(R.id.AlarmDataMonthField);
        DayLayout=findViewById(R.id.AlarmDayDataField);

        year=findViewById(R.id.AlarmYearEditText);
        month=findViewById(R.id.AlarmMonthEditText);
        day=findViewById(R.id.AlarmDayEditText);


        title=findViewById(R.id.TitleEditText);
        content=findViewById(R.id.DetailsEditText);
        datePick=findViewById(R.id.ConstraintDateView);
        switchMaterial=findViewById(R.id.NoteSwitcher);
        cancel=findViewById(R.id.CancelAdditionButton);
        add=findViewById(R.id.ADDButtonText);
        cancel.setOnClickListener(
                v -> {
                    finish();
                }
        );
        add.setOnClickListener(v -> {
            System.out.println("add clicked");
            ClearData();

        });

        note_OR_Task=findViewById(R.id.NoteOrToDoText);

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
            {
              note_OR_Task.setText(R.string.to_do);
              tag="task";
              datePick.setVisibility(View.VISIBLE);
            }
            else if(!isChecked){
                tag="note";
                note_OR_Task.setText(R.string.note);
                datePick.setVisibility(View.GONE);
            }
        });



    }

    private void ClearData() {
        int thisYear=Year.now().getValue();
        if(title.getText().toString().length()==0||title.getText().toString().equals(""))
        {
            titleLayout.setError("Missing field");
        }
        else if(content.getText().toString().length()==0||content.getText().toString().equals(""))
        {
            titleLayout.setErrorEnabled(false);
            contentLayout.setError("Missing field");
        }
        if(switchMaterial.isChecked())
        {
            if(day.getText().toString().length()==0||day.getText().toString().equals(""))
            {
                contentLayout.setErrorEnabled(false);
                DayLayout.setError("Missing values");
            }
            else if( month.getText().toString().length()==0||month.getText().toString().equals(""))
            {
                DayLayout.setErrorEnabled(false);
                MonthLayout.setError("Missing values");
            }else if(year.getText().toString().length()==0||year.getText().toString().equals(""))
            {
                MonthLayout.setErrorEnabled(false);
                YearLayout.setError("Missing values");
            }
            else if(Integer.parseInt(day.getText().toString())>31)
            {
                YearLayout.setErrorEnabled(false);
                DayLayout.setError("Invalid Range");
            }else if(Integer.parseInt(month.getText().toString())>12)
            {
                DayLayout.setErrorEnabled(false);
                MonthLayout.setError("Invalid Range");
            }else if(Integer.parseInt(year.getText().toString()) < thisYear)
            {
                MonthLayout.setErrorEnabled(false);
                YearLayout.setError("Invalid Range");
            }
            else{
                DayLayout.setErrorEnabled(false);
                MonthLayout.setErrorEnabled(false);
                YearLayout.setErrorEnabled(false);
                addTheNote();
            }

        }
        else{
            addTheNote();
        }



    }
    public void addTheNote()
    {


        currentDate = new Date();
        String AlarmDate;
        if (switchMaterial.isChecked()) {
            AlarmDate = day.getText().toString() + "." + month.getText().toString() + "." + year.getText().toString();
        } else {
            AlarmDate = null;
        }
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://noteify-service.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        note=new Note(title.getText().toString(),content.getText().toString(),AlarmDate,tag);
        Call<Note> call=apiInterface.CreateNote(note,"Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJva2F5YTFAZ21haWwuY29tIiwiaWQiOiI2MzMzNTcxNWRiOWYzMDAwMjFmY2E4YTIiLCJleHBpcmF0aW9uRGF0ZSI6MTY2OTQ5MzAyOSwiaWF0IjoxNjY0MzA5MDI5fQ.-oFPzPRldPO-B8DNMN3XXfLTUp_dgLkCO_qAnYZviZA");

        call.enqueue(new Callback<Note>() {
            @Override
            public void onResponse(Call<Note> call, Response<Note> response) {
                if (response.isSuccessful())
                {
                    Notes_frag.noteList.add(note);
                    Notes_frag.adapter.notifyDataSetChanged();
                    System.out.println("Details: "+response.body().getTitle());
                    Toast.makeText(getApplicationContext(),"Note Added Successfully",Toast.LENGTH_SHORT).show();
                    finish();

                    // System.out.println("The Token: "+response.body().get(0).getTitle());

                }
                else{

                    Toast.makeText(getApplicationContext(),"The List is Empty",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Note> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"the Data May Be Wrong Or Connection Lost",Toast.LENGTH_LONG).show();

            }
        });



        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        String currentDateandTime = sdf1.format(currentDate);
        note=new Note(title.getText().toString(),content.getText().toString(),AlarmDate,switchMaterial.isChecked()?"Task":"Note");
        System.out.println(note.getTitle()+" "+note.getDetails()+" "+note.getTag()+" "+note.getDateCreated()+note.getAlarmDate());


    }
}