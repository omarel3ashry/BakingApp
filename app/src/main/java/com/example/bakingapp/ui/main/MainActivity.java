package com.example.bakingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.databinding.ActivityMainBinding;
import com.example.bakingapp.ui.recipe.RecipeActivity;

import org.parceler.Parcels;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;
    private RecipesAdapter recipesAdapter = new RecipesAdapter();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        getLifecycle().addObserver(viewModel);

        setupRecyclerView();

        viewModel.getRecipesLive().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesAdapter.submitList(recipes);
            }
        });
    }

    private void setupRecyclerView() {
        binding.recipesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recipesRecyclerView.setAdapter(recipesAdapter);

        recipesAdapter.setRecipeClickListener(new RecipesAdapter.RecipeClickListener() {
            @Override
            public void onClick(Recipe recipe) {
                Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
                intent.putExtra(RecipeActivity.RECIPE_EXTRA, Parcels.wrap(recipe));
                startActivity(intent);
            }
        });
    }
}
