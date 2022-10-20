package com.example.note_app;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.note_app.ToDo.ToDoFrag;
import com.example.note_app.notes.Notes_frag;

class  PagerAdapter extends FragmentStateAdapter{
    Fragment fragment;

    public PagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        fragment=new Notes_frag();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment newFragment;
        if (position==0)
       {
            newFragment=fragment;

       }
       else
       {
           newFragment=new  ToDoFrag();
       }

        return newFragment;

    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
