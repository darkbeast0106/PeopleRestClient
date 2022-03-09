package com.darkbeast0106.peoplerestclient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.darkbeast0106.peoplerestclient.databinding.ActivityMainBinding;
import com.darkbeast0106.peoplerestclient.databinding.PersonListItemBinding;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<Person> people = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.adatok.setAdapter(new PersonAdapter());
        RequestTask task = new RequestTask();
        task.execute();
    }
    private class PersonAdapter extends ArrayAdapter<Person>{
        public PersonAdapter() {
            super(MainActivity.this, R.layout.person_list_item, people);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            PersonListItemBinding listItemBinding = PersonListItemBinding.inflate(getLayoutInflater());
            Person actual = people.get(position);
            String name = actual.getName();
            if (name.length() > 20){
                name = actual.getName().substring(0,20) + "...";
            }
            listItemBinding.name.setText(name);
            listItemBinding.age.setText(String.format(" (%d)",actual.getAge()));
            return listItemBinding.getRoot().getRootView();
        }
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {

        @Override
        protected Response doInBackground(Void... voids) {
        /*
        Response response = null;
        try {
           response = RequestHandler.get("https://retoolapi.dev/wJWWAd/people");
        } catch (IOException e) {
            Log.d("Hiba", e.toString());
        }
        */
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.d("Hiba", e.toString());
            }
            Response response = new Response(200, "[{\"id\":1,\"name\":\"Nelda Aufderhar\",\"email\":\"reilly.marcelina@example.org\",\"age\":24},{\"id\":2,\"name\":\"Margarita Runolfsson\",\"email\":\"mbeier@example.com\",\"age\":55},{\"id\":3,\"name\":\"Miss Stella Runte Jr.\",\"email\":\"frunolfsdottir@example.net\",\"age\":51},{\"id\":4,\"name\":\"Ms. Jenifer O'Keefe\",\"email\":\"armando.powlowski@example.org\",\"age\":23},{\"id\":5,\"name\":\"Elian Wisozk IV\",\"email\":\"unolan@example.net\",\"age\":80},{\"id\":6,\"name\":\"Dr. Kenton Auer\",\"email\":\"wfeeney@example.com\",\"age\":47},{\"id\":7,\"name\":\"Freddie Rau Sr.\",\"email\":\"uconsidine@example.com\",\"age\":32},{\"id\":8,\"name\":\"Melvina Breitenberg IV\",\"email\":\"qkuvalis@example.net\",\"age\":70},{\"id\":9,\"name\":\"Jayde Hodkiewicz\",\"email\":\"brady.gulgowski@example.net\",\"age\":53},{\"id\":10,\"name\":\"Brandon Aufderhar\",\"email\":\"reanna46@example.com\",\"age\":33},{\"id\":11,\"name\":\"Frieda Greenfelder\",\"email\":\"dfeil@example.org\",\"age\":22},{\"id\":12,\"name\":\"Mr. Ali Toy I\",\"email\":\"hermann.shanahan@example.net\",\"age\":35},{\"id\":13,\"name\":\"Mr. Dusty Osinski DDS\",\"email\":\"serena89@example.org\",\"age\":73},{\"id\":14,\"name\":\"Kiara Reilly\",\"email\":\"freddy.goodwin@example.com\",\"age\":23},{\"id\":15,\"name\":\"Barton Schimmel\",\"email\":\"schmeler.destini@example.com\",\"age\":54},{\"id\":16,\"name\":\"Mr. Garnet Wilkinson MD\",\"email\":\"bayer.kelley@example.org\",\"age\":55},{\"id\":17,\"name\":\"Prof. Davion Runte\",\"email\":\"thora.stroman@example.net\",\"age\":75},{\"id\":18,\"name\":\"Destini Gleason\",\"email\":\"turcotte.piper@example.org\",\"age\":32},{\"id\":19,\"name\":\"Norbert Treutel\",\"email\":\"sipes.kara@example.org\",\"age\":53},{\"id\":20,\"name\":\"Felicita Heidenreich Jr.\",\"email\":\"sean73@example.net\",\"age\":49}]");
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            binding.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Response response) {
            super.onPostExecute(response);
            binding.progressBar.setVisibility(View.GONE);
            Gson converter = new Gson();
            Person[] peopleArray = converter.fromJson(response.getContent(), Person[].class);
            people.clear();
            people.addAll(Arrays.asList(peopleArray));
        }
    }
}