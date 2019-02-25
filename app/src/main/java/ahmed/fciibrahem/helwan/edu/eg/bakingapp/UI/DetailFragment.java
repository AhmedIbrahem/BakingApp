package ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters.DetailRepicAdapter;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters.IngredientsAdapter;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Ingredients;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Recipes;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Steps;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.data.RecipeContract;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.data.RecipeContract.*;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.widget.BakingWidgetProvider;

import static  ahmed.fciibrahem.helwan.edu.eg.bakingapp.data.RecipeContract.RECIPE_CONTENT_URI;



public class DetailFragment extends Fragment {
    Recipes recipe;
    List<Steps> steps;
    List<Ingredients> ingredients;
    RecyclerView DetailReciperecyclerView,IngredientsRecyclarView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_detail,container,false);
        DetailReciperecyclerView=v.findViewById(R.id.DetailRecipeRecyclarView);
        IngredientsRecyclarView=v.findViewById(R.id.IngRecipeRecyclarView);
        if (savedInstanceState !=null)
        {
            Log.d("onCreateView: ", "savedinstance");
            recipe=savedInstanceState.getParcelable("recipe");
            steps=savedInstanceState.getParcelableArrayList("steps");
            ingredients=savedInstanceState.getParcelableArrayList("ingredients");



        }
        else
        {
            Log.d("onCreateView: ", "intent");
            Intent GetData=getActivity().getIntent();
            recipe=GetData.getParcelableExtra("RecipeSelected");
            steps=GetData.getParcelableArrayListExtra("steps");
            ingredients=GetData.getParcelableArrayListExtra("ingredients");
        }

        DetailRepicAdapter detailRepicAdapter=new DetailRepicAdapter(steps,ingredients,getActivity());
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getActivity());
        DetailReciperecyclerView.setLayoutManager(layoutManager);
        DetailReciperecyclerView.setAdapter(detailRepicAdapter);
        IngredientsAdapter ingredientsAdapter=new IngredientsAdapter(ingredients,getActivity());
        RecyclerView.LayoutManager layoutManager1=new GridLayoutManager(getActivity(),1,GridLayoutManager.HORIZONTAL,false);
        IngredientsRecyclarView.setLayoutManager(layoutManager1);
        IngredientsRecyclarView.setAdapter(ingredientsAdapter);
        return  v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       getActivity().getMenuInflater().inflate(R.menu.menu_ingredient_step, menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            case R.id.action_favorite:
                Log.d("DetailFragment", "menu: ");
                if (isFavorite()) {
                    removeRecipeFromFavorites();
                    item.setIcon(R.drawable.ic_favorite_normal);
                    Toast.makeText(getActivity().getApplicationContext(),"Remove"+ recipe.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("DetailFragment", "add: ");

                    addRecipeToFavorites();
                    item.setIcon(R.drawable.ic_favorite_added);
                    Toast.makeText(getActivity().getApplicationContext(), "Add"+ recipe.getName(), Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu)
    {
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        if (isFavorite())
        {
            //change the icon to indicate it's a favorite recipe
            menuItem.setIcon(R.drawable.ic_favorite_added);
        }
        else //it's not favorite
        {
            //change the icon to indicate it's not favorite
            menuItem.setIcon(R.drawable.ic_favorite_normal);
        }
    }


    private boolean isFavorite()
    {

        //get all the recipes where its "id" equal the current recipe if the return cursor is null then it's not fav
        //if the cursor is not null then it's favorite
        String[] projection = {RecipeContract.RecipeEntry.COLUMN_RECIPE_ID};
        String selection = RecipeContract.RecipeEntry.COLUMN_RECIPE_ID + " = " + recipe.getId();
        Cursor cursor =getActivity().getContentResolver().query(RECIPE_CONTENT_URI,
                projection,
                selection,
                null,
                null,
                null);

        return (cursor != null ? cursor.getCount() : 0) > 0;
    }



    synchronized private void removeRecipeFromFavorites() {
        getActivity().getContentResolver().delete(RECIPE_CONTENT_URI, null, null);
    }

    synchronized private void addRecipeToFavorites()
    {
        Log.d("DetailFragment", "start: ");

        //delete the old recipes (it can only save one recipe )
        getActivity().getContentResolver().delete(RECIPE_CONTENT_URI, null, null);
//save every ingredient in the database with the recipe name and id
        for (Ingredients ingredient : ingredients) {
            ContentValues values = new ContentValues();
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_ID, recipe.getId());
            values.put(RecipeContract.RecipeEntry.COLUMN_RECIPE_NAME, recipe.getName());
            values.put(RecipeEntry.COLUMN_INGREDIENT_NAME, ingredient.getIngredient());
            values.put(RecipeEntry.COLUMN_INGREDIENT_MEASURE, ingredient.getMeasure());
            values.put(RecipeEntry.COLUMN_INGREDIENT_QUANTITY, ingredient.getQuantity());
            getActivity().getContentResolver().insert(RECIPE_CONTENT_URI, values);
        }

        Log.d("DetailFragment", "addRecipeToFavorites: ");
        Intent intent = new Intent(getActivity(), BakingWidgetProvider.class);
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] ids = AppWidgetManager.getInstance(getContext()).getAppWidgetIds(new ComponentName(getContext(),BakingWidgetProvider.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        getActivity().sendBroadcast(intent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("recipe",recipe);
        outState.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
        outState.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) ingredients);
    }
}


