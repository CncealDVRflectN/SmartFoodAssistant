package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import by.solutions.dumb.smartfoodassistant.util.filters.ProductsFilter;
import by.solutions.dumb.smartfoodassistant.util.filters.RecipesFilter;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.DatabaseTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.IngredientsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.PricesTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ProductsTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.RecipesTable;
import by.solutions.dumb.smartfoodassistant.util.sql.tables.ShopsTable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class Database {
    private SQLiteDatabase db;

    public final DatabaseOpenHelper HELPER;

    public Database(Context context, String language, int version) {
        HELPER = new DatabaseOpenHelper(context, "SMART_FOOD_ASSISTANT", language, version);
        db = HELPER.getReadableDatabase();
    }

    private Cursor getAllProductsCursor() {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(ProductsTable.ID_COLUMN).append(", ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" ORDER BY ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getAllProducts() {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getAllProductsCursor());
                e.onComplete();
            }
        });
    }

    private Cursor getFilteredProductsCursor(ProductsFilter filter) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(ProductsTable.ID_COLUMN).append(", ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" LIKE '%").append(filter.name).append("%'")
                .append(" ORDER BY ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getFilteredProducts(final ProductsFilter filter) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getFilteredProductsCursor(filter));
                e.onComplete();
            }
        });
    }

    private Cursor getProductByIDCursor(String id) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(ProductsTable.ID_COLUMN).append(", ")
                .append(ProductsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(ProductsTable.ID_COLUMN).append(" = '").append(id).append("'");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getProductByID(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getProductByIDCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getProductPricesCursor(String id) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(DatabaseTable.SQL_ID).append(", ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(ShopsTable.ID_COLUMN).append(", ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(ShopsTable.NAME_COLUMN).append(", ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(ShopsTable.ADDRESS_COLUMN).append(", ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(ShopsTable.CURRENCY_COLUMN).append(", ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.PRICE_COLUMN)
                .append(" FROM ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(", ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME).append(".").append(ShopsTable.ID_COLUMN).append(" = ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.SHOP_ID_COLUMN)
                .append(" AND ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.ID_COLUMN).append(" = '").append(id).append("'")
                .append(" ORDER BY ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.PRICE_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getProductPrices(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getProductPricesCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getAllRecipesCursor() {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(RecipesTable.ID_COLUMN).append(", ")
                .append(RecipesTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.RECIPES_TABLE.TABLE_NAME)
                .append(" ORDER BY ")
                .append(RecipesTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getAllRecipes() {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getAllRecipesCursor());
                e.onComplete();
            }
        });
    }

    private Cursor getFilteredRecipesCursor(RecipesFilter filter) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(RecipesTable.ID_COLUMN).append(", ")
                .append(RecipesTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.RECIPES_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(RecipesTable.NAME_COLUMN)
                .append(" LIKE '%").append(filter.name).append("%'")
                .append(" ORDER BY ")
                .append(RecipesTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getFilteredRecipes(final RecipesFilter filter) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getFilteredRecipesCursor(filter));
                e.onComplete();
            }
        });
    }

    private Cursor getRecipeByIDCursor(String id) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(RecipesTable.ID_COLUMN).append(", ")
                .append(RecipesTable.NAME_COLUMN).append(", ")
                .append(RecipesTable.INSTRUCTION_COLUMN)
                .append(" FROM ")
                .append(HELPER.RECIPES_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(RecipesTable.ID_COLUMN).append(" = '").append(id).append("'");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getRecipeByID(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getRecipeByIDCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getRecipeIngredientsByIDCursor(String id) {
        StringBuilder request = new StringBuilder();
        String ingredientsTable = HELPER.INGREDIENTS_TABLES.get(id).TABLE_NAME;
        Cursor result;

        request.append("SELECT ")
                .append(ingredientsTable).append(".").append(DatabaseTable.SQL_ID).append(", ")
                .append(ingredientsTable).append(".").append(IngredientsTable.AMOUNT_COLUMN).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.MEASURE_COLUMN)
                .append(" FROM ")
                .append(ingredientsTable).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(ingredientsTable).append(".").append(IngredientsTable.ID_COLUMN).append(" = ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.ID_COLUMN)
                .append(" ORDER BY ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getRecipeIngredientsByID(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getRecipeIngredientsByIDCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getShopByIDCursor(String id) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(DatabaseTable.SQL_ID).append(", ")
                .append(ShopsTable.ID_COLUMN).append(", ")
                .append(ShopsTable.ADDRESS_COLUMN).append(", ")
                .append(ShopsTable.CURRENCY_COLUMN).append(", ")
                .append(ShopsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.SHOPS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(ShopsTable.ID_COLUMN).append(" = '").append(id).append("'");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getShopByID(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getShopByIDCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getShopPricesCursor(String id) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(DatabaseTable.SQL_ID).append(", ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.PRICE_COLUMN).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.SHOP_ID_COLUMN)
                .append(" = '").append(id).append("'")
                .append(" AND ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.ID_COLUMN).append(" = ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.ID_COLUMN)
                .append(" ORDER BY ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getShopPrices(final String id) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getShopPricesCursor(id));
                e.onComplete();
            }
        });
    }

    private Cursor getFilteredShopPricesCursor(String id, ProductsFilter filter) {
        StringBuilder request = new StringBuilder();
        Cursor result;

        request.append("SELECT ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(DatabaseTable.SQL_ID).append(", ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.PRICE_COLUMN).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" FROM ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(", ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME)
                .append(" WHERE ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.SHOP_ID_COLUMN)
                .append(" = '").append(id).append("'")
                .append(" AND ")
                .append(HELPER.PRICES_TABLE.TABLE_NAME).append(".").append(PricesTable.ID_COLUMN).append(" = ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.ID_COLUMN)
                .append(" AND ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" LIKE '%").append(filter.name).append("%'")
                .append(" ORDER BY ")
                .append(HELPER.PRODUCTS_TABLE.TABLE_NAME).append(".").append(ProductsTable.NAME_COLUMN)
                .append(" ASC");

        result = db.rawQuery(request.toString(), null);
        result.moveToFirst();
        return result;
    }

    public Observable<Cursor> getFilteredShopPrices(final String id, final ProductsFilter filter) {
        return Observable.create(new ObservableOnSubscribe<Cursor>() {
            @Override
            public void subscribe(ObservableEmitter<Cursor> e) throws Exception {
                e.onNext(getFilteredShopPricesCursor(id, filter));
                e.onComplete();
            }
        });
    }

    public void close() {
        db.close();
        HELPER.close();
    }
}
