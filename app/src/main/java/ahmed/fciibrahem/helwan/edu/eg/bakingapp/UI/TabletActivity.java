package ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcelable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Recipes;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Steps;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.R;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.DetailFragment;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.stepDetailedFragment;
import ahmed.fciibrahem.helwan.edu.eg.bakingapp.UI.StepDetails;

public class TabletActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isTablet(this)) {
            Log.i("tablet_activity", "tablet");
            setContentView(R.layout.activity_tablet);


            Intent intent = getIntent();
            if (intent.hasExtra("steps")) {
                Recipes recipes = (Recipes) intent.getParcelableExtra("RecipeSelected");
                Log.i("tablet_activity", recipes.getName());

                //intent.getIntExtra("adapter",0);
                stepDetailedFragment mstepDetailedFragment = (stepDetailedFragment) getSupportFragmentManager().findFragmentById(R.id.StepDetaildfragment);
                if (mstepDetailedFragment != null) {
                    Log.i("recipe_adpter", "find_step_detail");
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("steps", intent.getParcelableArrayListExtra("steps"));
                    bundle.putInt("AdapterPosation", 0);
                    mstepDetailedFragment.setArguments(bundle);

                }
            }
        }

    }
    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

//    public void getDetail(Bundle bundle)
//    {
//
//        Intent intent=getIntent();
//        intent.getParcelableArrayListExtra("steps");
//        intent.getIntExtra("adapter",0);
//
//
//    }
}
