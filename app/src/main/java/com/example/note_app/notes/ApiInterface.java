package com.example.note_app.notes;

import android.os.Message;

import com.example.note_app.DeleteMessage;
import com.example.note_app.NotesForUpdate;
import com.example.note_app.classes.Login;
import com.example.note_app.classes.UserData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

        @GET("note/getAll")
        public Call<List<Note>> getAllNotes(@Header("Authorization") String authHeader);
        @GET("note{id}")
        public  Call<Note> getNote(@Query("id")String id,@Header("Authorization") String authHeader);
        @POST("user/login")
        Call<UserData> login(@Body Login login);
        @POST("user/register")
        Call<UserData> SignUp(@Body Login singUp);
        @POST("note/add")
        Call<Note> CreateNote(@Body Note note,@Header("Authorization") String authHeader);
        @DELETE("note/delete")
        Call<Id> DeleteNote(@Header("Authorization") String authHeader,@Query("id") String id);
        @PUT("note/update")
        Call<Id>UpdateNote(@Header("Authorization") String authHeader,@Body NotesForUpdate note);
        @DELETE("note/deleteAll")
        Call<DeleteMessage> DeleteAll(@Header("Authorization") String authHeader);

}
