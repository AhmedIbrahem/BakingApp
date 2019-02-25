package ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters.RecipeAdapter;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Api.ClientApi;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Recipes;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityFragment extends Fragment {
    RecyclerView reciperecyclerView;
   Response<List<Recipes>> recipes ;
   private static List<Recipes> mRecipesList=new ArrayList<>();
    RecipeAdapter recipeAdapter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        }


        private void getRepics()
    {

        String BaseUrl = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ClientApi clientApi = retrofit.create(ClientApi.class);
        Call<List<Recipes>> connection = clientApi.GetData();
        connection.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                mRecipesList=response.body();
                Log.d("onResponse", ": "+mRecipesList.size());
              recipeAdapter  = new RecipeAdapter(mRecipesList, getActivity());
                RecyclerView.LayoutManager layoutManager;
                if (isTablet(getActivity().getApplication())) {
                    layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 4);

                } else {
                    layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);

                }
                Log.d("soooo", "onCreateView: ");

                reciperecyclerView.setLayoutManager(layoutManager);
                reciperecyclerView.setAdapter(recipeAdapter);


                recipes = response;
                for (Recipes r : response.body()) {

                    Log.d("size", "size: " + r.getIngredients().size());
                    Log.d("steps", "size: " + r.getSteps().size());

                }

            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
            }
        });
    }


        public boolean isTablet (Context context){
            boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
            boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
            return (xlarge || large);
        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_main_activity, container, false);
            reciperecyclerView = v.findViewById(R.id.RecipeRecyclarView);
            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            } else
                connected = false;

            if (!connected) {
                Toast.makeText(getActivity().getApplicationContext(), "No Internet Access please Open the Data or Wifi", Toast.LENGTH_LONG).show();
            }
            if (savedInstanceState !=null)
            {
                Log.d("oncreate", "onsaveinsatance: ");
                mRecipesList=savedInstanceState.getParcelableArrayList("repics");
                recipeAdapter  = new RecipeAdapter(mRecipesList, getActivity());
                RecyclerView.LayoutManager layoutManager;
                if (isTablet(getActivity().getApplication())) {
                    layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 4);

                } else {
                    layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);

                }
                Log.d("soooo", "onCreateView: ");

                reciperecyclerView.setLayoutManager(layoutManager);
                reciperecyclerView.setAdapter(recipeAdapter);


                Log.d("oncreate", "onsaveinsatance: "+mRecipesList.size());

            }
            else {

                getRepics();
            }
            return v;
        }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("repics", (ArrayList<? extends Parcelable>) mRecipesList);

    }
}
