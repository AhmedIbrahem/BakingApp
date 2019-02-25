package ahmed.fciibrahem.helwan.edu.eg.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Ingredients;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    List<Ingredients> ingredients ;
    Context context;

    public IngredientsAdapter(List<Ingredients> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View row= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ingrecipe_item,viewGroup,false);
        IngredientsViewHolder holder=new IngredientsViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder ingredientsViewHolder, int i) {
        ingredientsViewHolder.ingredient.setText(ingredients.get(i).getIngredient());
        ingredientsViewHolder.measure.setText(ingredients.get(i).getMeasure());
        ingredientsViewHolder.Quantity.setText(ingredients.get(i).getQuantity());


    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class IngredientsViewHolder extends RecyclerView.ViewHolder
    {
        TextView Quantity,measure,ingredient;



        public IngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            Quantity=itemView.findViewById(R.id.quantity);
            measure=itemView.findViewById(R.id.measure);
            ingredient=itemView.findViewById(R.id.ingredient);


        }
    }
}
