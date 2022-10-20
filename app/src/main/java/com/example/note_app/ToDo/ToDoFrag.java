package com.example.note_app.ToDo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.note_app.R;
import com.example.note_app.notes.Note;
import com.example.note_app.notes.NotesAdapter;
import com.example.note_app.notes.Notes_frag;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToDoFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToDoFrag extends Fragment {
    public static   NotesAdapter adapter;


    public ToDoFrag() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ToDoFrag newInstance() {

        return new ToDoFrag();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    RecyclerView TasksRecycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_to_do, container, false);
        TasksRecycler=view.findViewById(R.id.Tasks_Recycler);
        TasksRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
         adapter=new NotesAdapter(Notes_frag.noteList,1,getResources().getConfiguration().orientation,getContext());
        TasksRecycler.setAdapter(adapter);
        return view;
    }
    static Boolean allowRefresh=true;
    @Override
    public void onResume() {
        super.onResume();
        if (allowRefresh)
        {
                adapter.notifyDataSetChanged();



            allowRefresh=false;
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        allowRefresh=false;
        adapter.notifyDataSetChanged();
    }
}