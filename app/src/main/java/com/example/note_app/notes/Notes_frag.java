package com.example.note_app.notes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.note_app.R;
import com.example.note_app.SignIn;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Notes_frag extends Fragment {
    public static ArrayList<Note> noteList=new ArrayList<>();
   public static  NotesAdapter adapter;
   public static Retrofit retrofit=new Retrofit.Builder().baseUrl("https://noteify-service.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
    ApiInterface apiInterface=retrofit.create(ApiInterface.class);
    Call<List<Note>> call=apiInterface.getAllNotes("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJva2F5YTFAZ21haWwuY29tIiwiaWQiOiI2MzMzNTcxNWRiOWYzMDAwMjFmY2E4YTIiLCJleHBpcmF0aW9uRGF0ZSI6MTY2OTQ5MzAyOSwiaWF0IjoxNjY0MzA5MDI5fQ.-oFPzPRldPO-B8DNMN3XXfLTUp_dgLkCO_qAnYZviZA");

    public Notes_frag() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Notes_frag newInstance() {
        Notes_frag fragment = new Notes_frag();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter=new NotesAdapter(noteList,0,getResources().getConfiguration().orientation,getContext());




    }
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_notes_frag, container, false);
        recyclerView=view.findViewById(R.id.notesRecycler);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        }
        else if (getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }

        sendResponse(call);
        recyclerView.setAdapter(adapter);

        System.out.println("fragment created");







        return view;
    }
     static Boolean allowRefresh=true;

    @Override
    public void onResume() {

        super.onResume();

        if (allowRefresh&&noteList!=null)
        {
            Thread thread=new Thread(()->{
                System.out.println("is resumed");
                 sendResponse(call);
                for (int i=0;i<noteList.size();i++)
                {
                    System.out.println(noteList.get(i).getId()+" "+noteList.get(i).getTitle()+" "+noteList.get(i).getDetails()+" "+noteList.get(i).getAlarmDate());
                }
                allowRefresh = false;
            //     getParentFragmentManager().beginTransaction().detach(this).attach(this).commit();
                System.out.println("allow refresh "+allowRefresh);
            });
            thread.start();

        }

      /*  Fragment frg = this.requireParentFragment();
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        ft.commit();*/
    }



    @Override
    public void onPause() {
        super.onPause();
        allowRefresh=true;
       // FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        //ft.detach().attach(this).commit();
        System.out.println("fragment paused");
    }
    public void sendResponse(Call<List<Note>> call)
    {

        call.clone().enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful())
                {

                    System.out.println("Details: "+response.body().size());
                    if(noteList==null)
                    {
                        for (int i=0;i<response.body().size();i++)
                        {
                            noteList.add(new Note(response.body().get(i).getId(),response.body().get(i).getTitle(),
                                    response.body().get(i).getDetails(),
                                    (String)response.body().get(i).getTag(),
                                    response.body().get(i).getDateCreated(),
                                    (String)response.body().get(i).getAlarmDate())) ;
                        }
                        adapter.notifyDataSetChanged();




                    }
                    else {
                        noteList.clear();
                        for (int i=0;i<response.body().size();i++)
                        {
                            noteList.add(new Note(response.body().get(i).getId(),response.body().get(i).getTitle(),
                                    response.body().get(i).getDetails(),
                                    (String)response.body().get(i).getTag(),
                                    response.body().get(i).getDateCreated(),
                                    (String)response.body().get(i).getAlarmDate())) ;
                        }
                        adapter.notifyDataSetChanged();



                    }

                    allowRefresh=false;
                    // System.out.println("The Token: "+response.body().get(0).getTitle());

                }
                else{

                    Toast.makeText(getContext(),"The List is Empty",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {

                Toast.makeText(getContext(),"the Data May Be Wrong Or Connection Lost",Toast.LENGTH_LONG).show();

            }
        });

    }
}