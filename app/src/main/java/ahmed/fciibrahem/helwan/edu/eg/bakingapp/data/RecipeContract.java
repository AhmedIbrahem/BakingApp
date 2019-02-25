package ahmed.fciibrahem.helwan.edu.eg.bakingapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract
{
    static final String CONTENT_AUTHORITY = "ahmed.fciibrahem.helwan.edu.eg.bakingapp.data";
    static final String PATH_RECIPE = "recipe";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final Uri RECIPE_CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

// the first table
    public static final class RecipeEntry implements BaseColumns
    {
        public static final String COLUMN_RECIPE_NAME = "recipe_name";
        public static final String COLUMN_RECIPE_ID = "recipe_id";
        public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
        public static final String COLUMN_INGREDIENT_MEASURE = "ingredient_measure";
        public static final String COLUMN_INGREDIENT_QUANTITY = "ingredient_quantity";
        static final String RECIPE_TABLE = "recipe";
    }
}
