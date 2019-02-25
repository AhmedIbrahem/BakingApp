package ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Ingredients;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Steps;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.TabletActivity;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.StepDetails;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.stepDetailedFragment;


public class DetailRepicAdapter extends RecyclerView.Adapter<DetailRepicAdapter.RecipeDetailViewHolder> {
List<Steps> steps;
List<Ingredients> ingredients;
Context context;


    public DetailRepicAdapter(List<Steps> steps, List<Ingredients> ingredients, Context context) {
        this.steps = steps;
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.detail_repic_item,viewGroup,false);
        RecipeDetailViewHolder holder=new RecipeDetailViewHolder(row);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder recipeDetailViewHolder, int i) {

            recipeDetailViewHolder.DetailName.setText(steps.get(i).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder
    {
        TextView DetailName;



        public RecipeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            DetailName=itemView.findViewById(R.id.repicName);
            DetailName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Steps step = steps.get(getAdapterPosition());

                    if(isTablet(context)) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("OneStep", step);
                        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) steps);
                        bundle.putInt("AdapterPosation", getAdapterPosition());
                        stepDetailedFragment mstepDetailedFragment = (stepDetailedFragment) ((TabletActivity) context).getSupportFragmentManager().findFragmentById(R.id.StepDetaildfragment);
                        if (mstepDetailedFragment != null) {
                            Log.i("recipe_adpter", "find_step_detail");
                            mstepDetailedFragment.setArguments(bundle);
                            mstepDetailedFragment.getArs();

                        }
                    }

                   else {
                        Intent intent = new Intent(context, StepDetails.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("OneStep", step);
                        intent.putParcelableArrayListExtra("steps", (ArrayList<? extends Parcelable>) steps);
                        intent.putExtra("AdapterPosation", getAdapterPosition());
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
