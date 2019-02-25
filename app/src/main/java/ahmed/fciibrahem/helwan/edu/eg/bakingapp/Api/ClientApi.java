package ahmed.fciibrahem.helwan.edu.eg.bakingapp.Api;

import java.util.List;

import ahmed.fciibrahem.helwan.edu.eg.bakingapp.Model.Recipes;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ClientApi {
    @GET("baking.json")
    Call<List<Recipes>> GetData();
}
