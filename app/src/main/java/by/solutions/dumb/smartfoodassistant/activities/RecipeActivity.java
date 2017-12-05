package by.solutions.dumb.smartfoodassistant.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import by.solutions.dumb.smartfoodassistant.R;

public class RecipeActivity extends AppCompatActivity {

    //region Variables

    private final static int INGREDIENT_FONT_SIZE = 18;

    private ActionBar actionBar;

    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        TextView recipeDescriptionView = findViewById(R.id.recipe_description);
        LinearLayout recipeIngredientsContainer = findViewById(R.id.ingredients_container);

        List<String> recipeIngredients = new ArrayList<>();
        TextView tmpTextView;
        actionBar = getSupportActionBar();

        recipeIngredients.add("Картоха");
        recipeIngredients.add("Картошка");
        recipeIngredients.add("Картофель");

        actionBar.setTitle("Жареная картошка");
        actionBar.setDisplayHomeAsUpEnabled(true);

        recipeDescriptionView.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi sit amet lectus tincidunt, eleifend sem at, auctor erat. Quisque ut libero tincidunt, hendrerit felis et, gravida nisi. Curabitur pretium maximus porttitor. Maecenas risus sem, hendrerit eget vestibulum vel, sagittis sit amet justo. Donec lacinia libero vel eros fringilla, in tincidunt neque fringilla. In imperdiet elit sed ex pellentesque, et ullamcorper nisi lacinia. Etiam ut varius eros, facilisis efficitur urna. Morbi eu vestibulum odio, sit amet malesuada lacus. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Sed ex libero, scelerisque sit amet nisl scelerisque, ultricies commodo lectus. Etiam at consequat enim. Morbi tincidunt, leo id molestie aliquet, tellus leo venenatis leo, sit amet convallis dolor elit et purus. Sed luctus ante eu orci maximus, non aliquet arcu congue. Proin nec nisi consectetur dolor euismod blandit id vitae sapien. Proin semper augue ut purus condimentum, ut gravida nisi tristique. Proin aliquam aliquam ante finibus tristique.\nSed facilisis suscipit rutrum. Vivamus sodales feugiat hendrerit. Nam ornare sit amet tellus et pharetra. Aliquam erat volutpat. In et ante quam. Aenean in erat dignissim metus malesuada suscipit. Nulla blandit gravida neque, quis maximus massa ullamcorper vel. Sed cursus efficitur efficitur. Phasellus nec egestas odio, sit amet facilisis turpis. Morbi feugiat turpis eget ipsum fermentum, quis consequat lacus viverra. Curabitur eu imperdiet nibh. Aenean ut convallis massa. Vestibulum eget arcu dui. Aenean porta diam id fringilla blandit. Integer commodo dictum consequat. Etiam pretium leo eget lorem dictum placerat. Vivamus quis maximus neque, et vestibulum leo. Integer in nunc non urna gravida ullamcorper. Sed ultrices augue sed quam imperdiet, vitae aliquam metus tincidunt. Donec pharetra ex vitae nulla tristique, non placerat ipsum egestas. Etiam nibh elit, accumsan ut felis ac, cursus bibendum neque. Nam posuere est blandit risus malesuada, vitae rhoncus nisi pulvinar. Fusce eu efficitur ipsum. Nam porta gravida est a eleifend. Quisque quis rutrum est. In posuere, lacus sed dictum euismod, velit elit cursus ipsum, eget aliquet nibh lectus et neque. Nullam iaculis varius mi eget imperdiet. Sed et hendrerit mi. Aenean consectetur dignissim consequat. In bibendum ullamcorper urna, dictum gravida tellus volutpat a. Donec tempor ultrices nunc, in sollicitudin ipsum imperdiet eget. Fusce vitae accumsan enim. Proin vehicula et dolor ut efficitur. Phasellus placerat id arcu quis blandit. Duis enim nunc, tristique vel luctus eu, consequat et ex. Praesent suscipit tincidunt tellus, a euismod mauris ultrices pulvinar. Duis at venenatis turpis. Curabitur maximus dui sed quam venenatis, porta mollis augue mollis. Nulla facilisi. Ut eget efficitur sapien. Aenean mi nisl, pulvinar id efficitur sed, dapibus vel urna. Sed ut lobortis massa. Cras egestas sed ante sed suscipit. Praesent pulvinar ipsum quis enim convallis porta. Suspendisse justo urna, dapibus eget eleifend sit amet, aliquet vitae ante.");

        for (String ingredient : recipeIngredients) {
            tmpTextView = new TextView(this);
            tmpTextView.setText(ingredient);
            tmpTextView.setTextSize(INGREDIENT_FONT_SIZE);
            recipeIngredientsContainer.addView(tmpTextView);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //endregion
}
