package com.example.note_app.notes;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.note_app.R;
import com.google.android.material.card.MaterialCardView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDetails_landscape#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDetails_landscape extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "title";
    private static final String ARG_PARAM2 = "content";
    private static final String ARG_PARAM3 = "Date Created";
    private static final String ARG_PARAM4 = "Color";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String mParam3;
    private int mParam4;

    public FragmentDetails_landscape() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetails_landscape newInstance() {
        FragmentDetails_landscape fragment = new FragmentDetails_landscape();/*
        Bundle args = new Bundle();
       // System.out.println("new instance "+param1+" "+param2+" "+param3+" "+param4);
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        //args.putString(ARG_PARAM3, param3);
        //args.putInt(ARG_PARAM4, param4);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4=getArguments().getInt(ARG_PARAM4);
        }
    }
    private TextView Title;
    private TextView Content;
   private MaterialCardView card;
   private TextView date;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_details_landscape, container, false);
         Title =view.findViewById(R.id.title_landscape);
         Content =view.findViewById(R.id.content_text_landscape);
         card=view.findViewById(R.id.LandScapeCard);
         date=view.findViewById(R.id.DateLandscape);

        Bundle data=getArguments();
        if (data!=null)
        {
            Title.setText(data.getString("title"));
            Content.setText(data.getString("Details"));
            date.setText(data.getString("DateCreated"));
            card.setCardBackgroundColor(data.getInt("color"));

        }
        else{
                Title.setText("title 1");
                Content.setText("content 1");
                date.setText("date 1");

        }


      //  Title.setText(mParam1);
        //content.setText(mParam2);
        //date.setText(mParam3);
        //card.setBackgroundColor(Color.RED);

        return view;
    }
    public void UpdateData(String title,String content,int color)
    {
        String date="";
        System.out.println("Update Data "+title+" "+content+" "+date+" "+color);
       // Title.setText(title);
        //Content.setText(content);
       /* if (date.equals("")||date.equals(null))
        {
            this.date.setText("No date");
        }
        else {
            this.date.setText(date);
        }
*/
        //this.card.setBackgroundColor(color);
    }
}