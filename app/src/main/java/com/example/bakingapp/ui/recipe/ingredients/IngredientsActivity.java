package com.example.bakingapp.ui.recipe.ingredients;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Ingredient;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.databinding.IngredientsActivityBinding;
import com.example.bakingapp.ui.recipe.RecipeActivity;

import org.parceler.Parcels;

import java.util.List;


public class IngredientsActivity extends AppCompatActivity {

    private IngredientsActivityBinding binding;
    private IngredientsAdapter ingredientsAdapter = new IngredientsAdapter();
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.ingredients_activity);

        recipe = Parcels.unwrap(getIntent().getParcelableExtra(RecipeActivity.RECIPE_EXTRA));

        binding.ingredientsRecyclerView.setAdapter(ingredientsAdapter);
        binding.ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ingredientsAdapter.submitList(recipe.getIngredients());
    }
}
