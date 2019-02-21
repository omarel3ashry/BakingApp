package com.example.bakingapp.ui.recipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;

import org.parceler.Parcels;

public class RecipeActivity extends AppCompatActivity {

    public static final String RECIPE_EXTRA = "recipe";
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(RECIPE_EXTRA));

    }
}
