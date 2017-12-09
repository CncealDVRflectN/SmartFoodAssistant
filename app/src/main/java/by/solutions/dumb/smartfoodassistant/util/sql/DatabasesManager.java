package by.solutions.dumb.smartfoodassistant.util.sql;


import android.content.Context;

public class DatabasesManager {
    public static final String LOCALE_RU = "ru";
    public static final String LOCALE_EN = "en";

    private static Database db;
    private static String currentLanguage;
    private static int currentVersion;

    private DatabasesManager() {
    }

    public static void changeLanguage(Context context, String language) {
        if (currentLanguage == null || !currentLanguage.equalsIgnoreCase(language.trim())) {
            close();
            if (currentVersion == 0) {
                currentVersion = 1;
            }
            db = new Database(context, language, 1);
            currentLanguage = language.trim();
        }
    }

    public static void changeVersion(Context context, int version) {
        if (currentVersion != version || currentVersion == 0) {
            close();
            if (currentLanguage == null) {
                currentLanguage = "en";
            }
            db = new Database(context, currentLanguage, version);
            currentVersion = version;
        }
    }

    public static void changeLanguageWithVersion(Context context, String language, int version) {
        if (currentLanguage == null || !currentLanguage.equalsIgnoreCase(language.trim()) ||
                currentVersion != version || currentVersion == 0) {
            close();
            db = new Database(context, language, version);
            currentLanguage = language.trim();
            currentVersion = version;
        }
    }

    public static Database getDatabase() {
        return db;
    }

    public static String getLanguage() {
        return currentLanguage;
    }

    public static int getVersion() {
        return currentVersion;
    }

    public static void close() {
        if (db != null) {
            db.close();
        }
    }
}
