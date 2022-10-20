package com.example.note_app.notes;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note_app.HomePage;
import com.example.note_app.R;
import com.example.note_app.SignIn;
import com.example.note_app.UpdateActivityData;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolders> {
    private final int orientation;
    private final Context context;
    private int numType;
        private ArrayList<Note> NotesList=new ArrayList<>();
    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        if (numType==0) {
            if (orientation==Configuration.ORIENTATION_PORTRAIT)
            {
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_card,parent,false);
            }
            else if(orientation==Configuration.ORIENTATION_LANDSCAPE){
                view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_card,parent,false);
            }

        }
        else {
            view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tasks_card,parent,false);
        }

           return new ViewHolders(view,numType,orientation);
    }

    public NotesAdapter(ArrayList<Note> notesList, int numType, int orientation, Context context) {
        NotesList = notesList;
        this.numType=numType;
        this.orientation=orientation;
        this.context=context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {
        TextView title=holder.title;
        TextView details=holder.details;
        Random rand=new Random();
        int randRed,randBlue,randGreen;
        randRed=rand.nextInt(255);
        randBlue=rand.nextInt(255);
        randGreen=rand.nextInt(255);
        TextView AlarmDate=holder.AlarmDate;

        title.setText(NotesList.get(position).getTitle());
        if(numType==0)
       {
           holder.itemView.setOnClickListener(v -> {
               ((HomePage)context).SendData(NotesList.get(position).getTitle(),NotesList.get(position).getDetails(),NotesList.get(position).getDateCreated(),Color.argb(40,randRed,randGreen,randBlue));

           });

           if (orientation==Configuration.ORIENTATION_PORTRAIT)
           {
               holder.card1.setCardBackgroundColor(Color.argb(60,randRed,randGreen,randBlue));
             
               details.setText(NotesList.get(position).getDetails());

               holder.optionsButton.setOnClickListener(v -> {
                   System.out.println(NotesList.get(position).getId());

                   popupMenu( holder.optionsButton,NotesList.get(position));


               });
           }
           else {


               holder.card.setCardBackgroundColor(Color.argb(35,randRed,randGreen,randBlue));
               details.setText("");

               AlarmDate.setText("");
           }




       }
       else if(numType==1){
            details.setText(NotesList.get(position).getDetails());

           AlarmDate.setText((String) NotesList.get(position).getAlarmDate());
       }



    }


    @Override
    public int getItemCount() {
        return NotesList.size();
    }
    public  static  class ViewHolders extends RecyclerView.ViewHolder
    {

        TextView title;
        TextView details;
        CardView card;
        MaterialCardView card1;
        TextView AlarmDate;
        ImageView optionsButton;
        public ViewHolders(@NonNull View itemView, int numType,int orientation) {
            super(itemView);
            if (numType==0)
            {

                if(orientation==Configuration.ORIENTATION_LANDSCAPE)
                {
                    title=itemView.findViewById(R.id.task_Title);

                    details=itemView.findViewById(R.id.task_details);
                    details.setVisibility(View.GONE);
                    AlarmDate=itemView.findViewById(R.id.task_AlertDate);
                    AlarmDate.setVisibility(View.GONE);
                    card=itemView.findViewById(R.id.tasks_Card);
                }
                else {
                    title=itemView.findViewById(R.id.titleText);
                    details=itemView.findViewById(R.id.detailsText);
                    card1=itemView.findViewById(R.id.CardItem);
                    optionsButton=(ImageView) itemView.findViewById(R.id.optionsBtn);
                }
            }
            else {
                AlarmDate=itemView.findViewById(R.id.task_AlertDate);
                    title=itemView.findViewById(R.id.task_Title);

                details=itemView.findViewById(R.id.task_details);

            }



        }
    }
    public void popupMenu(ImageView optionsButton, Note note)
    {
        System.out.println("note in pop menu"+note);
        PopupMenu popupMenu= new PopupMenu(context,optionsButton);
        popupMenu.inflate(R.menu.item_menu);
        popupMenu.setOnMenuItemClickListener(item -> {
            if (item.getItemId()==R.id.EditNote)
            {
                ((HomePage)context).UpdateScreen(note);
                return true;
            }
            else if (item.getItemId()==R.id.deleteNote)
            {

                theAlertDialog(note.getId());


                return true;
            }


            return false;
        });
        popupMenu.show();
    }



    private void theAlertDialog(String  noteId) {

           Retrofit retrofit=new Retrofit.Builder().baseUrl("https://noteify-service.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        Id id=new Id(noteId);

        Call<Id> call=apiInterface.DeleteNote("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJlbWFpbCI6InJva2F5YTFAZ21haWwuY29tIiwiaWQiOiI2MzMzNTcxNWRiOWYzMDAwMjFmY2E4YTIiLCJleHBpcmF0aW9uRGF0ZSI6MTY2OTQ5MzU1MCwiaWF0IjoxNjY0MzA5NTUwfQ.C_44_7tiZ1-Vp1nDB_TAYCorO-iUruihMuQfelW5nuw",id.getId());

        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage("Are You Sure Want To Delete? ");
        builder1.setCancelable(true);
        builder1.setNegativeButton(
                "No",
                (dialog, Id) -> dialog.cancel()
        );
        builder1.setPositiveButton(
                "Yes",
                (dialog, Id) -> {
                    System.out.println("dialog yes pressed");
                    System.out.println("note id "+id);

                    call.enqueue(new Callback<Id>() {
                        @Override
                        public void onResponse(Call<Id> call, Response<Id> response) {
                            if (response.isSuccessful())
                            {
                                System.out.println(response.body().id+"the response is successful");
                                Toast.makeText(context,"The item deleted Successfully",Toast.LENGTH_SHORT).show();
                                notifyDataSetChanged();
                                ((HomePage)context).UpdateDataAfterDeletion(id.getId());
                            }
                            else {
                                Toast.makeText(context,"may the connection lost",Toast.LENGTH_SHORT).show();
                                System.out.println("the response is failed "+response.code());
                            }
                        }

                        @Override
                        public void onFailure(Call<Id> call, Throwable t) {
                                System.out.println("Adapter  failure");
                        }
                    });

                }
        );
        builder1.setIcon(R.drawable.ic_round_delete_24);
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
