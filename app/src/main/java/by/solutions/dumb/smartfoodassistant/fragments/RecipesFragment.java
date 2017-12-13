package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.BaseActivity;
import by.solutions.dumb.smartfoodassistant.activities.RecipeActivity;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.DatabasesManager;
import by.solutions.dumb.smartfoodassistant.util.sql.adapters.ProductsCursorAdapter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class RecipesFragment extends Fragment {

    //region Variables

    private static final String LOG_TAG = "RecipesFragment";

    private ListView recipesView;
    private Disposable disposable;

    //endregion


    //region Fragment lifecycle

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ((BaseActivity) getActivity()).showProgressDialog();
        View fragmentView = inflater.inflate(R.layout.fragment_recipes, container, false);

        recipesView = fragmentView.findViewById(R.id.recipes_list);
        disposable = DatabasesManager.getDatabase().getAllRecipes()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        recipesView.setAdapter(new ProductsCursorAdapter(RecipesFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        recipesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                Cursor recipe = (Cursor) adapterView.getItemAtPosition(i);
                String id = recipe.getString(recipe.getColumnIndex(RecipesTable.ID_COLUMN));

                intent.putExtra("recipeID", id);
                Log.d(LOG_TAG, id);
                startActivity(intent);
            }
        });

        return fragmentView;
    }

    @Override
    public void onDestroy() {
        if (disposable != null) {
            disposable.dispose();
        }
        super.onDestroy();
    }

    //endregion

    public void resetFilter() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = DatabasesManager.getDatabase().getAllRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        recipesView.setAdapter(new ProductsCursorAdapter(RecipesFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void filter(RecipesFilter filter) {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = DatabasesManager.getDatabase().getFilteredRecipes(filter)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Cursor>() {
                    @Override
                    public void onNext(Cursor cursor) {
                        recipesView.setAdapter(new ProductsCursorAdapter(RecipesFragment.this.getActivity(),
                                cursor, R.layout.product));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LOG_TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //region Getters


    //endregion


    //region Setters


    //endregion


    //region Private methods


    //endregion
}
