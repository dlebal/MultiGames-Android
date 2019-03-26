package es.dlebal.multigames.db

import android.provider.BaseColumns

/**
 * Object object DBConfig
 *
 * This object provides DBConfig objects
 */
object DBConfig {

    // Database
    const val DATABASE_VERSION = 1 // Database version
    const val DATABASE_NAME = "multigames.db" // Database name

    // Inner class that defines the table "categories"
    object CategoriesEntry : BaseColumns {
        const val TABLE_NAME = "categories"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
    }

    // Inner class that defines the table "games"
    object GamesEntry : BaseColumns {
        const val TABLE_NAME = "games"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_THUMBNAIL = "thumbnail"
        const val COLUMN_SCREEN_1 = "screen_1"
        const val COLUMN_SCREEN_2 = "screen_2"
        const val COLUMN_SCREEN_3 = "screen_3"
        const val COLUMN_VIDEO = "video"
        const val COLUMN_EMBED_CODE = "embed_code"
        const val COLUMN_ORIENTATION = "orientation"
        const val COLUMN_PROVIDER = "provider"
    }

    // Inner class that defines the table "game_categories"
    object GameCategoriesEntry : BaseColumns {
        const val TABLE_NAME = "game_categories"
        const val COLUMN_GAME_ID = "game_id"
        const val COLUMN_CATEGORY_ID = "category_id"
    }

    // SQL sentences for table "categories"
    const val SQL_CREATE_TABLE_CATEGORIES =
        " CREATE TABLE " + CategoriesEntry.TABLE_NAME + " ( " +
                CategoriesEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CategoriesEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE " +
        " ) " // SQL create table "categories"
    const val SQL_DROP_TABLE_CATEGORIES = " DROP TABLE IF EXISTS " + CategoriesEntry.TABLE_NAME + " " // SQL drop table "categories"

    // SQL sentences for table "games"
    const val SQL_CREATE_TABLE_GAMES =
        " CREATE TABLE " + GamesEntry.TABLE_NAME + " ( " +
                GamesEntry.COLUMN_ID + " INTEGER PRIMARY KEY, " +
                GamesEntry.COLUMN_NAME + " TEXT NOT NULL UNIQUE, " +
                GamesEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                GamesEntry.COLUMN_THUMBNAIL + " TEXT, " +
                GamesEntry.COLUMN_SCREEN_1 + " TEXT, " +
                GamesEntry.COLUMN_SCREEN_2 + " TEXT, " +
                GamesEntry.COLUMN_SCREEN_3 + " TEXT, " +
                GamesEntry.COLUMN_VIDEO + " TEXT, " +
                GamesEntry.COLUMN_EMBED_CODE + " TEXT NOT NULL, " +
                GamesEntry.COLUMN_ORIENTATION + " TEXT, " +
                GamesEntry.COLUMN_PROVIDER + " TEXT NOT NULL " +
        " ) " // SQL create table "games"
    const val SQL_DROP_TABLE_GAMES = " DROP TABLE IF EXISTS " + GamesEntry.TABLE_NAME + " " // SQL drop table "games"

    // SQL sentences for table "game_categories"
    const val SQL_CREATE_TABLE_GAME_CATEGORIES =
        " CREATE TABLE " + GameCategoriesEntry.TABLE_NAME + " ( " +
                GameCategoriesEntry.COLUMN_GAME_ID + " INTEGER NOT NULL, " +
                GameCategoriesEntry.COLUMN_CATEGORY_ID + " INTEGER NOT NULL, " +
                " PRIMARY KEY (" + GameCategoriesEntry.COLUMN_GAME_ID + ", " + GameCategoriesEntry.COLUMN_CATEGORY_ID + "), " +
                " FOREIGN KEY (" + GameCategoriesEntry.COLUMN_GAME_ID + ") REFERENCES " + GamesEntry.TABLE_NAME + "(" + GamesEntry.COLUMN_ID + "), " +
                " FOREIGN KEY (" + GameCategoriesEntry.COLUMN_CATEGORY_ID + ") REFERENCES " + CategoriesEntry.TABLE_NAME + "(" + CategoriesEntry.COLUMN_ID + ") " +
        " ) " // SQL create table "game_categories"
    const val SQL_DROP_TABLE_GAME_CATEGORIES = " DROP TABLE IF EXISTS " + GameCategoriesEntry.TABLE_NAME + " " // SQL drop table "game_categories"

}
