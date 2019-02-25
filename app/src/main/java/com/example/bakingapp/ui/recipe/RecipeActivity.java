package com.example.bakingapp.ui.recipe;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.data.Step;
import com.example.bakingapp.ui.recipe.step.StepDetailsFragment;
import com.example.bakingapp.widget.BakingProviderWidget;

import org.parceler.Parcels;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "recipe";
    private Recipe recipe;
    public static boolean mTwoPane;
    public static boolean stepAdded;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_EXTRA));
        FragmentManager fragmentManager = getSupportFragmentManager();
        RecipeFragment recipeFragment = RecipeFragment.newInstance(recipe);
        fragmentManager.beginTransaction().add(R.id.recipeContainer, recipeFragment).commit();
        if (savedInstanceState == null) {
            refreshWidgetList(recipe);
            saveRecipe(recipe);
        }

        if (findViewById(R.id.twoPane) != null) {
            StepDetailsFragment stepDetailsFragment;
            mTwoPane = true;
            if (!stepAdded) {
                stepAdded = true;
                Step firstStep = recipe.getSteps().get(0);
                stepDetailsFragment = StepDetailsFragment.newInstance(firstStep);
                fragmentManager.beginTransaction().add(R.id.stepDetailsContainer, stepDetailsFragment).commit();
            }

        } else {
            mTwoPane = false;
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        stepAdded = false;
    }

    private void refreshWidgetList(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplication());
        int ids[] = appWidgetManager.getAppWidgetIds(
                new ComponentName(getApplication(), BakingProviderWidget.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(ids, R.id.widget_list_view);
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.baking_provider_widget);
        remoteViews.setTextViewText(R.id.widgetRecipeName, recipe.getName());
        appWidgetManager.partiallyUpdateAppWidget(ids, remoteViews);
    }

    private void saveRecipe(Recipe recipe) {
        SharedPreferences sharedpreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(getString(R.string.widget_recipe), recipe.getName());
        editor.putInt(getString(R.string.recipe_id), recipe.getId());
        editor.apply();

    }
}
