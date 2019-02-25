package ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Recipes;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.TabletActivity;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.DetailActivity;
import retrofit2.Response;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
  private List<Recipes> recipesList;
    Context context;
    public RecipeAdapter(List<Recipes> recipesList, Context context) {
        this.recipesList = recipesList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.repic_item,viewGroup,false);
        RecipeViewHolder holder=new RecipeViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {

        recipeViewHolder.Name.setText(recipesList.get(i).getName().toString());
        if(!recipesList.get(i).getImage().isEmpty())
        {
            Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+recipesList.get(i).getImage()).placeholder(R.drawable.ic_launcher_background).resize(160,200).into(recipeViewHolder.Image);
        }



    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder
   {
       TextView Name;
       ImageView Image;


       public RecipeViewHolder(@NonNull View itemView) {
           super(itemView);
           Image=itemView.findViewById(R.id.iv_recipe_image);
           Name=itemView.findViewById(R.id.tv_recipe_name);
           Name.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   Recipes Selectedrecipes=  recipesList.get(getAdapterPosition());

                   if(isTablet(context))
                   {


//                       Bundle bundle=new Bundle();
//                       bundle.putParcelable("RecipeSelected",Selectedrecipes);
//                       bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) Selectedrecipes.getSteps());
//                       bundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) Selectedrecipes.getIngredients());
//                       stepDetailedFragment mstepDetailedFragment = (stepDetailedFragment) ((TabletActivity)context).getSupportFragmentManager().findFragmentById(R.id.StepDetaildfragment);
//                       if(mstepDetailedFragment != null)
//                       {
//                           Log.i("recipe_adpter", "find_step_detail");
//                           mstepDetailedFragment.setArguments(bundle);
//                       }


                       Intent intent=new Intent(context, TabletActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("RecipeSelected",Selectedrecipes);
                       intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) Selectedrecipes.getSteps());
                       intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) Selectedrecipes.getIngredients());
                       context.startActivity(intent);

                   }
                   else
                   {
                       Intent intent=new Intent(context, DetailActivity.class);
                       intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       intent.putExtra("RecipeSelected",Selectedrecipes);
                       intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) Selectedrecipes.getSteps());
                       intent.putParcelableArrayListExtra("ingredients", (ArrayList<? extends Parcelable>) Selectedrecipes.getIngredients());
                       context.startActivity(intent);

                   }



               }
           });
       }
   }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

}
