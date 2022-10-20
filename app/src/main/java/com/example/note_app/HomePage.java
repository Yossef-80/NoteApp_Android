package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note_app.classes.Login;
import com.example.note_app.classes.UserData;
import com.example.note_app.notes.AddNotes;
import com.example.note_app.notes.ApiInterface;
import com.example.note_app.notes.FragmentDetails_landscape;
import com.example.note_app.notes.Id;
import com.example.note_app.notes.Note;
import com.example.note_app.notes.Notes_frag;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomePage extends AppCompatActivity implements LandScapeInterface,DeletingInterface,UpdatingInterface{
   TabLayout tabLayout;
    ViewPager2 viewPager;
    TextView username;
    ImageView DeleteAllItems;
    Fragment DetailsFragment;
    FloatingActionButton FAB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int Orientation=getResources().getConfiguration().orientation;

        SharedPreferences sharedPreferences=getSharedPreferences("signIn",MODE_PRIVATE);
        String theName=sharedPreferences.getString("email","User");
        String newName="";
        for (int i=0;i<theName.indexOf('@');i++)
        {
            newName=newName.concat(String.valueOf(theName.charAt(i)));
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        tabLayout=findViewById(R.id.MainTabLayout);
        viewPager=findViewById(R.id.MainPager);
        username=findViewById(R.id.UserNameText);
        Calendar timeNow= Calendar.getInstance();
        if(Configuration.ORIENTATION_PORTRAIT==Orientation)
        {
            DeleteAllItems=findViewById(R.id.DeleteAllNotes);
            DeleteAllItems.setOnClickListener(v -> {
                DeletionAlert();

            });

            if (timeNow.get(Calendar.HOUR_OF_DAY)>=12)
            {
                username.setText("Hello "+newName+", good Evening");
            }
        else {
            username.setText("Hello "+newName+", good Morning");
        }
            String[] tabsName=new String[]{"Notes","To Do"};
            PagerAdapter pagerAdapter=new PagerAdapter(getSupportFragmentManager(),getLifecycle());
            viewPager.setAdapter(pagerAdapter);
            FAB=findViewById(R.id.floating_action_button);
            FAB.setOnClickListener(v -> {
                Intent intent=new Intent(this, AddNotes.class);
                startActivity(intent);

            });
            new TabLayoutMediator(tabLayout,viewPager,(tab, position) -> {
                tab.setText(tabsName[position]);

            }).attach();
        }

            if(savedInstanceState==null)
            {
                if(Configuration.ORIENTATION_LANDSCAPE==Orientation)
                {
                    DetailsFragment=FragmentDetails_landscape.newInstance();
                    FragmentManager manager=this.getSupportFragmentManager();
                    FragmentTransaction transaction=manager.beginTransaction();
                    transaction.setReorderingAllowed(true)
                            .add(R.id.fragment,DetailsFragment).commit();
                }


            }










    }

    private void DeletionAlert() {
        ApiInterface apiInterface=Notes_frag.retrofit.create(ApiInterface.class);

        Call<DeleteMessage> call=apiInterface.DeleteAll("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJva2F5YTFAZ21haWwuY29tIiwiaWQiOiI2MzMzNTcxNWRiOWYzMDAwMjFmY2E4YTIiLCJleHBpcmF0aW9uRGF0ZSI6MTY2OTQ5MzU1MCwiaWF0IjoxNjY0MzA5NTUwfQ.C_44_7tiZ1-Vp1nDB_TAYCorO-iUruihMuQfelW5nuw");


        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage("Are You Sure Want To Delete All Items? ");
        builder1.setIcon(R.drawable.ic_round_add_24);
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "No",
                (dialog, Id) -> dialog.cancel()
        );
        builder1.setPositiveButton(
                "Yes",
                (dialog, Id) -> {
                call.enqueue(new Callback<DeleteMessage>() {
                    @Override
                    public void onResponse(Call<DeleteMessage> call, Response<DeleteMessage> response) {
                           System.out.println( response.body().Message);
                        Toast.makeText(getApplicationContext(),"Deleting Items Success",Toast.LENGTH_SHORT).show();
                            Notes_frag.noteList.clear();
                            Notes_frag.adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<DeleteMessage> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Connection Failed",Toast.LENGTH_SHORT).show();
                    }
                });



                }
        );
        builder1.setIcon(R.drawable.ic_round_delete_24);
        AlertDialog alert11 = builder1.create();

        alert11.show();
    }



    @Override
    public void SendData(String title, String Details,String DateCreated, int color) {
        FragmentDetails_landscape DetailsFrag=FragmentDetails_landscape.newInstance();
        int Orientation=getResources().getConfiguration().orientation;
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if (DateCreated==null)
        {
            DateCreated="p";
        }
            System.out.println("Send Data "+title+" "+Details+" "+DateCreated+" "+color);
        Bundle bundle=new Bundle();
        bundle.putString("title",title);
        bundle.putString("Details",Details);

        bundle.putString("DateCreated",DateCreated);
        bundle.putInt("color",color);
        DetailsFrag.setArguments(bundle);
        if(Configuration.ORIENTATION_LANDSCAPE==Orientation)
        {
            fragmentTransaction.replace(R.id.fragment, DetailsFrag).show(DetailsFrag).commit();
        }


    }

    @Override
    public void UpdateDataAfterDeletion(String deletedItemId) {
       Notes_frag.noteList.removeIf(note -> {return note.getId().equals(deletedItemId);});
       Notes_frag.adapter.notifyDataSetChanged();
    }

    @Override
    public void UpdateScreen(Note note) {
        Intent intent=new Intent(this, UpdateActivityData.class);
        intent.putExtra("id",note.getId());
        intent.putExtra("title",note.getTitle());
        intent.putExtra("body",note.getDetails());
        startActivity(intent);
    }
}
