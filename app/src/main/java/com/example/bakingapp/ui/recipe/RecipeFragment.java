package com.example.bakingapp.ui.recipe;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Parcel;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.R;
import com.example.bakingapp.data.Recipe;
import com.example.bakingapp.databinding.FragmentRecipeBinding;
import com.example.bakingapp.ui.recipe.ingredients.IngredientsActivity;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeFragment extends Fragment {

    private Recipe recipe;
    private FragmentRecipeBinding binding;
    private StepsAdapter stepsAdapter;


    public RecipeFragment() {
        // Required empty public constructor
    }

    public static RecipeFragment newInstance(Recipe recipe) {

        Bundle args = new Bundle();

        args.putParcelable("recipe", Parcels.wrap(recipe));
        RecipeFragment fragment = new RecipeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipe = Parcels.unwrap(getArguments().getParcelable("recipe"));
        stepsAdapter = new StepsAdapter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRecipeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.stepsRecyclerView.setAdapter(stepsAdapter);
        binding.stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        binding.ingredientsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), IngredientsActivity.class);
                intent.putExtra(RecipeActivity.RECIPE_EXTRA, Parcels.wrap(recipe));
                startActivity(intent);
            }
        });

//        stepsAdapter.submitList(recipe.getSteps());
    }
}
