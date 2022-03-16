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
import android.widget.Toast;

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
    private String url = "https://retoolapi.dev/wJWWAd/people";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.personForm.setVisibility(View.GONE);
        binding.btnUj.setOnClickListener(view -> {
            binding.personForm.setVisibility(View.VISIBLE);
            binding.btnUj.setVisibility(View.GONE);
        });
        binding.btnMegse.setOnClickListener(view -> {
            urlapAlaphelyzetbe();
        });
        binding.btnHozzad.setOnClickListener(view -> {
            emberHozzadasa();
        });
        binding.adatok.setAdapter(new PersonAdapter());
        RequestTask task = new RequestTask(url, "GET");
        task.execute();
    }

    private void urlapAlaphelyzetbe() {
        binding.name.setText("");
        binding.email.setText("");
        binding.age.setText("");
        binding.personForm.setVisibility(View.GONE);
        binding.btnUj.setVisibility(View.VISIBLE);
    }

    private void emberHozzadasa() {
        String name = binding.name.getText().toString().trim();
        String email = binding.email.getText().toString().trim();
        String ageText = binding.age.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Név megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            Toast.makeText(this, "E-mail megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ageText.isEmpty()) {
            Toast.makeText(this, "Életkor megadása kötelező", Toast.LENGTH_SHORT).show();
            return;
        }
        int age = Integer.parseInt(ageText);
        Person person = new Person(0, name, email, age);
        Gson jsonConvert = new Gson();
        RequestTask task = new RequestTask(url,
                "POST", jsonConvert.toJson(person));
        task.execute();
    }

    private class PersonAdapter extends ArrayAdapter<Person> {
        public PersonAdapter() {
            super(MainActivity.this, R.layout.person_list_item, people);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            PersonListItemBinding listItemBinding = PersonListItemBinding.inflate(getLayoutInflater());
            Person actual = people.get(position);
            String name = actual.getName();
            if (name.length() > 20) {
                name = actual.getName().substring(0, 20) + "...";
            }
            listItemBinding.name.setText(name);
            listItemBinding.age.setText(String.format(" (%d)", actual.getAge()));
            listItemBinding.delete.setOnClickListener(view -> {
                RequestTask task = new RequestTask(url, "DELETE", String.valueOf(actual.getId()));
                task.execute();
            });
            return listItemBinding.getRoot().getRootView();
        }
    }

    private class RequestTask extends AsyncTask<Void, Void, Response> {
        String requestUrl;
        String requestType;
        String requestParams;

        public RequestTask(String requestUrl, String requestType) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
        }

        public RequestTask(String requestUrl, String requestType, String requestParams) {
            this.requestUrl = requestUrl;
            this.requestType = requestType;
            this.requestParams = requestParams;
        }

        @Override
        protected Response doInBackground(Void... voids) {
            Response response = null;
            try {
                switch (requestType){
                    case "GET":
                        response = RequestHandler.get(requestUrl);
                        break;
                    case "POST":
                        response = RequestHandler.post(requestUrl, requestParams);
                        break;
                    case "PUT":
                        response = RequestHandler.put(requestUrl, requestParams);
                        break;
                    case "DELETE":
                        response = RequestHandler.delete(requestUrl+"/"+requestParams);
                        break;
                }
            } catch (IOException e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            }
            /*
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Log.d("Hiba", e.toString());
            }
            Response response = new Response(200, "[{\"id\":1,\"name\":\"Nelda Aufderhar\",\"email\":\"reilly.marcelina@example.org\",\"age\":24},{\"id\":2,\"name\":\"Margarita Runolfsson\",\"email\":\"mbeier@example.com\",\"age\":55},{\"id\":3,\"name\":\"Miss Stella Runte Jr.\",\"email\":\"frunolfsdottir@example.net\",\"age\":51},{\"id\":4,\"name\":\"Ms. Jenifer O'Keefe\",\"email\":\"armando.powlowski@example.org\",\"age\":23},{\"id\":5,\"name\":\"Elian Wisozk IV\",\"email\":\"unolan@example.net\",\"age\":80},{\"id\":6,\"name\":\"Dr. Kenton Auer\",\"email\":\"wfeeney@example.com\",\"age\":47},{\"id\":7,\"name\":\"Freddie Rau Sr.\",\"email\":\"uconsidine@example.com\",\"age\":32},{\"id\":8,\"name\":\"Melvina Breitenberg IV\",\"email\":\"qkuvalis@example.net\",\"age\":70},{\"id\":9,\"name\":\"Jayde Hodkiewicz\",\"email\":\"brady.gulgowski@example.net\",\"age\":53},{\"id\":10,\"name\":\"Brandon Aufderhar\",\"email\":\"reanna46@example.com\",\"age\":33},{\"id\":11,\"name\":\"Frieda Greenfelder\",\"email\":\"dfeil@example.org\",\"age\":22},{\"id\":12,\"name\":\"Mr. Ali Toy I\",\"email\":\"hermann.shanahan@example.net\",\"age\":35},{\"id\":13,\"name\":\"Mr. Dusty Osinski DDS\",\"email\":\"serena89@example.org\",\"age\":73},{\"id\":14,\"name\":\"Kiara Reilly\",\"email\":\"freddy.goodwin@example.com\",\"age\":23},{\"id\":15,\"name\":\"Barton Schimmel\",\"email\":\"schmeler.destini@example.com\",\"age\":54},{\"id\":16,\"name\":\"Mr. Garnet Wilkinson MD\",\"email\":\"bayer.kelley@example.org\",\"age\":55},{\"id\":17,\"name\":\"Prof. Davion Runte\",\"email\":\"thora.stroman@example.net\",\"age\":75},{\"id\":18,\"name\":\"Destini Gleason\",\"email\":\"turcotte.piper@example.org\",\"age\":32},{\"id\":19,\"name\":\"Norbert Treutel\",\"email\":\"sipes.kara@example.org\",\"age\":53},{\"id\":20,\"name\":\"Felicita Heidenreich Jr.\",\"email\":\"sean73@example.net\",\"age\":49}]");
             */
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
            if (response.getResponseCode() >= 400){
                Toast.makeText(MainActivity.this, "Hiba történt a kérés feldolgozása során", Toast.LENGTH_SHORT).show();
                Log.d("onPostExecuteError: ", response.getContent());
            }
            switch (requestType){
                case "GET":
                    Person[] peopleArray = converter.fromJson(response.getContent(), Person[].class);
                    people.clear();
                    people.addAll(Arrays.asList(peopleArray));
                    break;
                case "POST":
                    Person person = converter.fromJson(response.getContent(), Person.class);
                    people.add(0, person);
                    urlapAlaphelyzetbe();
                    break;
                case "PUT":
                    break;
                case "DELETE":
                    int id = Integer.parseInt(requestParams);
                    people.removeIf(person1 -> person1.getId() == id);
                    break;
            }
        }
    }
}