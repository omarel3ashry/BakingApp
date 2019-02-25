package com.example.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.rest.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class IngredientsRemoteViewsService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientsRemoteViewsFactory(getApplicationContext());
    }
}

class IngredientsRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Ingredient> mIngredientsList;
    private List<Recipe> mRecipeList;
    private Recipe recipe;


    IngredientsRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mRecipeList = new ArrayList<>();
        SharedPreferences prefs = mContext.getSharedPreferences("pref", MODE_PRIVATE);
        final int recipeId = prefs.getInt(mContext.getString(R.string.recipe_id), 1);
        RetrofitClient.getRecipesService().getRecipes().enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                if (response.isSuccessful()) {
                    mRecipeList = response.body();
                    List<Ingredient> ingredients = mRecipeList.get(recipeId).getIngredients();
                    mIngredientsList.addAll(ingredients);
                }
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.e("JSONFAIL", t.getMessage(), t);
            }
        });

//        List<Ingredient> ingredients = mRecipeList.get(recipeId).getIngredients();
//        mIngredientsList.addAll(ingredients);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if (mIngredientsList == null || mIngredientsList.isEmpty())
            return 0;
        else
            return mIngredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        Ingredient ingredient = mIngredientsList.get(position);
        remoteViews.setTextViewText(R.id.list_view_text_item,
                ingredient.getQuantity() + " " + ingredient.getMeasure() + " " + ingredient.getIngredient());
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

