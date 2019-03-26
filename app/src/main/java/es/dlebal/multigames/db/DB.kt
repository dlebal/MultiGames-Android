package es.dlebal.multigames.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import es.dlebal.multigames.models.Category
import es.dlebal.multigames.models.Game
import es.dlebal.multigames.models.GameCategory

/**
 * Class class DB(context: Context) : SQLiteOpenHelper(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION)
 *
 * This class provides DB objects
 *
 * Properties:
 *   - context: Context
 */
class DB(context: Context) : SQLiteOpenHelper(context, DBConfig.DATABASE_NAME, null, DBConfig.DATABASE_VERSION) {

    /**
     * Variables
     */
    private val className: String = DB::class.java.simpleName // Class name

    /**
     * Method override fun onCreate(db: SQLiteDatabase)
     *
     * Performs the following tasks:
     *   - Initialize the database
     *
     * Parameters:
     *   @param db: The database
     */
    override fun onCreate(db: SQLiteDatabase) {

        // Variables
        val methodName = "onCreate" // Method name

        // Initialize the database
        Log.d(this.className + ": $methodName", "Initialize the database")
        db.execSQL(DBConfig.SQL_CREATE_TABLE_CATEGORIES)
        db.execSQL(DBConfig.SQL_CREATE_TABLE_GAMES)
        db.execSQL(DBConfig.SQL_CREATE_TABLE_GAME_CATEGORIES)

    }

    /**
     * Method override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
     *
     * Performs the following tasks:
     *   - Upgrade the database
     *
     * Parameters:
     *   @param db: The database
     *   @param oldVersion: The old database version
     *   @param newVersion: The new database version
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // Variables
        val methodName = "onUpgrade" // Method name

        // Upgrade the database
        Log.d(this.className + ": $methodName", "Upgrade the database")
        db.execSQL(DBConfig.SQL_DROP_TABLE_GAME_CATEGORIES)
        db.execSQL(DBConfig.SQL_DROP_TABLE_GAMES)
        db.execSQL(DBConfig.SQL_DROP_TABLE_CATEGORIES)
        onCreate(db)

    }

    /**
     * Method override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int)
     *
     * Performs the following tasks:
     *   - Downgrade the database
     *
     * Parameters:
     *   @param db: The database
     *   @param oldVersion: The old database version
     *   @param newVersion: The new database version
     */
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        // Variables
        val methodName = "onDowngrade" // Method name

        // Downgrade the database
        Log.d(this.className + ": $methodName", "Downgrade the database")
        onUpgrade(db, oldVersion, newVersion)

    }

    /******************************************************************************************************************/
    /***************************************** Methods for table "categories" *****************************************/
    /******************************************************************************************************************/
    /**
     * Method private fun mappingCategoryContentValues(category: Category): ContentValues
     *
     * Performs the following tasks:
     *   - Mapping category content values
     *
     * Parameters:
     *   @param category: Category
     */
    private fun mappingCategoryContentValues(category: Category): ContentValues {

        // Variables
        val methodName = "mappingCategoryContentValues" // Method name

        // Mapping category content values
        Log.d(this.className + ": $methodName", "Mapping category content values")
        val contentValues = ContentValues()
        contentValues.put(DBConfig.CategoriesEntry.COLUMN_ID, category.id)
        contentValues.put(DBConfig.CategoriesEntry.COLUMN_NAME, category.name)
        return contentValues

    }

    /**
     * Method fun addCategory(category: Category)
     *
     * Performs the following tasks:
     *   - Add a category
     *
     * Parameters:
     *   @param category: Category
     */
    fun addCategory(category: Category) {

        // Variables
        val methodName = "addCategory" // Method name

        // Add a category
        Log.d(this.className + ": $methodName", "Add a category")
        val db = this.writableDatabase
        db.insert(DBConfig.CategoriesEntry.TABLE_NAME, null, mappingCategoryContentValues(category))
        db.close()

    }

    /**
     * Method fun getCategory(categoryId: Long): Category?
     *
     * Performs the following tasks:
     *   - Get a single category
     *
     * Parameters:
     *   @param categoryId: Category identifier
     *   @return category: The category
     */
    fun getCategory(categoryId: Long): Category? {

        // Variables
        val methodName = "getCategory" // Method name
        var category: Category? = null // Category

        // Get a single category
        Log.d(this.className + ": $methodName", "Get a single category")
        val db = this.readableDatabase
        val query = (" SELECT  * FROM " + DBConfig.CategoriesEntry.TABLE_NAME + " WHERE " + DBConfig.CategoriesEntry.COLUMN_ID + " = " + categoryId + " ")
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if ((cursor.count == 1) && (cursor.moveToFirst())) {
                category = Category(
                    cursor.getInt(cursor.getColumnIndex(DBConfig.CategoriesEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.CategoriesEntry.COLUMN_NAME))
                )
            }
            cursor.close()
        }
        db.close()
        return category

    }

    /**
     * Method fun getCategories(): ArrayList<Category>
     *
     * Performs the following tasks:
     *   - Get all categories
     *
     * Parameters:
     *   @return categories: The categories
     */
    fun getCategories(): ArrayList<Category> {

        // Variables
        val methodName = "getCategories" // Method name
        val categories: ArrayList<Category> = ArrayList() // Categories

        // Get all categories
        Log.d(this.className + ": $methodName", "Get all categories")
        val db = this.readableDatabase
        val query = (" SELECT  * FROM " + DBConfig.CategoriesEntry.TABLE_NAME + " ORDER BY " + DBConfig.CategoriesEntry.COLUMN_NAME + " ASC ")
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if ((cursor.count > 0) && (cursor.moveToFirst())) {
                while (cursor.moveToNext()) {
                    val category = Category(
                        cursor.getInt(cursor.getColumnIndex(DBConfig.CategoriesEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.CategoriesEntry.COLUMN_NAME))
                    )
                    categories.add(category)
                }
            }
            cursor.close()
        }
        db.close()
        return categories

    }

    /**
     * Method fun updateCategory(category: Category)
     *
     * Performs the following tasks:
     *   - Update a category
     *
     * Parameters:
     *   @param category: Category
     */
    fun updateCategory(category: Category) {

        // Variables
        val methodName = "updateCategory" // Method name

        // Update a category
        Log.d(this.className + ": $methodName", "Update a category")
        val db = this.writableDatabase
        db.update(DBConfig.CategoriesEntry.TABLE_NAME, mappingCategoryContentValues(category), DBConfig.CategoriesEntry.COLUMN_ID + " = ?", arrayOf(category.id.toString()))
        db.close()

    }

    /**
     * Method fun deleteCategory(category: Category)
     *
     * Performs the following tasks:
     *   - Delete a category
     *
     * Parameters:
     *   @param category: Category
     */
    fun deleteCategory(category: Category) {

        // Variables
        val methodName = "deleteCategory" // Method name

        // Delete a category
        Log.d(this.className + ": $methodName", "Delete a category")
        val db = this.writableDatabase
        db.delete(DBConfig.CategoriesEntry.TABLE_NAME, DBConfig.CategoriesEntry.COLUMN_ID + " = ?", arrayOf(category.id.toString()))
        db.close()

    }

    /**
     * Method fun deleteCategories()
     *
     * Performs the following tasks:
     *   - Delete all categories
     */
    fun deleteCategories() {

        // Variables
        val methodName = "deleteCategories" // Method name

        // Delete all categories
        Log.d(this.className + ": $methodName", "Delete all categories")
        val db = this.writableDatabase
        db.delete(DBConfig.CategoriesEntry.TABLE_NAME, null, null)
        db.close()

    }
    /******************************************************************************************************************/
    /***************************************** Methods for table "categories" *****************************************/
    /******************************************************************************************************************/

    /******************************************************************************************************************/
    /******************************************* Methods for table "games" ********************************************/
    /******************************************************************************************************************/
    /**
     * Method private fun mappingGameContentValues(game: Game): ContentValues
     *
     * Performs the following tasks:
     *   - Mapping game content values
     *
     * Parameters:
     *   @param game: Game
     */
    private fun mappingGameContentValues(game: Game): ContentValues {

        // Variables
        val methodName = "mappingGameContentValues" // Method name

        // Mapping game content values
        Log.d(this.className + ": $methodName", "Mapping game content values")
        val contentValues = ContentValues()
        contentValues.put(DBConfig.GamesEntry.COLUMN_ID, game.id)
        contentValues.put(DBConfig.GamesEntry.COLUMN_NAME, game.name)
        contentValues.put(DBConfig.GamesEntry.COLUMN_DESCRIPTION, game.description)
        contentValues.put(DBConfig.GamesEntry.COLUMN_THUMBNAIL, game.thumbnail)
        contentValues.put(DBConfig.GamesEntry.COLUMN_SCREEN_1, game.screen1)
        contentValues.put(DBConfig.GamesEntry.COLUMN_SCREEN_2, game.screen2)
        contentValues.put(DBConfig.GamesEntry.COLUMN_SCREEN_3, game.screen3)
        contentValues.put(DBConfig.GamesEntry.COLUMN_VIDEO, game.video)
        contentValues.put(DBConfig.GamesEntry.COLUMN_EMBED_CODE, game.embedCode)
        contentValues.put(DBConfig.GamesEntry.COLUMN_ORIENTATION, game.orientation)
        contentValues.put(DBConfig.GamesEntry.COLUMN_PROVIDER, game.provider)
        return contentValues

    }

    /**
     * Method fun addGame(game: Game)
     *
     * Performs the following tasks:
     *   - Add a game
     *
     * Parameters:
     *   @param game: Game
     */
    fun addGame(game: Game) {

        // Variables
        val methodName = "addGamey" // Method name

        // Add a game
        Log.d(this.className + ": $methodName", "Add a game")
        val db = this.writableDatabase
        db.insert(DBConfig.GamesEntry.TABLE_NAME, null, mappingGameContentValues(game))
        db.close()

    }

    /**
     * Method fun getGame(gameId: Long): Game?
     *
     * Performs the following tasks:
     *   - Get a single game
     *
     * Parameters:
     *   @param gameId: Game identifier
     *   @return game: The game
     */
    fun getGame(gameId: Long): Game? {

        // Variables
        val methodName = "getGame" // Method name
        var game: Game? = null // Game

        // Get a single game
        Log.d(this.className + ": $methodName", "Get a single game")
        val db = this.readableDatabase
        val query = (" SELECT  * FROM " + DBConfig.GamesEntry.TABLE_NAME + " WHERE " + DBConfig.GamesEntry.COLUMN_ID + " = " + gameId + " ")
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if ((cursor.count == 1) && (cursor.moveToFirst())) {
                game = Game(
                    cursor.getInt(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_DESCRIPTION)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_THUMBNAIL)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_1)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_2)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_3)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_VIDEO)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_EMBED_CODE)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_ORIENTATION)),
                    cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_PROVIDER))
                )
            }
            cursor.close()
        }
        db.close()
        return game

    }

    /**
     * Method fun getGames(): ArrayList<Game>
     *
     * Performs the following tasks:
     *   - Get all games
     *
     * Parameters:
     *   @return games: The games
     */
    fun getGames(): ArrayList<Game> {

        // Variables
        val methodName = "getGames" // Method name
        val games: ArrayList<Game> = ArrayList() // Games

        // Get all games
        Log.d(this.className + ": $methodName", "Get all games")
        val db = this.readableDatabase
        val query = (" SELECT  * FROM " + DBConfig.GamesEntry.TABLE_NAME + " ORDER BY " + DBConfig.GamesEntry.COLUMN_NAME + " ASC ")
        val cursor = db.rawQuery(query, null)
        if (cursor != null) {
            if ((cursor.count > 0) && (cursor.moveToFirst())) {
                while (cursor.moveToNext()) {
                    val game = Game(
                        cursor.getInt(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_THUMBNAIL)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_1)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_2)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_SCREEN_3)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_VIDEO)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_EMBED_CODE)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_ORIENTATION)),
                        cursor.getString(cursor.getColumnIndex(DBConfig.GamesEntry.COLUMN_PROVIDER))
                    )
                    games.add(game)
                }
            }
            cursor.close()
        }
        db.close()
        return games

    }

    /**
     * Method fun updateGame(game: Game)
     *
     * Performs the following tasks:
     *   - Update a game
     *
     * Parameters:
     *   @param game: Game
     */
    fun updateGame(game: Game) {

        // Variables
        val methodName = "updateGame" // Method name

        // Update a game
        Log.d(this.className + ": $methodName", "Update a game")
        val db = this.writableDatabase
        db.update(DBConfig.GamesEntry.TABLE_NAME, mappingGameContentValues(game), DBConfig.GamesEntry.COLUMN_ID + " = ?", arrayOf(game.id.toString()))
        db.close()

    }

    /**
     * Method fun deleteGame(game: Game)
     *
     * Performs the following tasks:
     *   - Delete a game
     *
     * Parameters:
     *   @param game: Game
     */
    fun deleteGame(game: Game) {

        // Variables
        val methodName = "deleteGame" // Method name

        // Delete a game
        Log.d(this.className + ": $methodName", "Delete a game")
        val db = this.writableDatabase
        db.delete(DBConfig.GamesEntry.TABLE_NAME, DBConfig.GamesEntry.COLUMN_ID + " = ?", arrayOf(game.id.toString()))
        db.close()

    }

    /**
     * Method fun deleteGames()
     *
     * Performs the following tasks:
     *   - Delete all games
     */
    fun deleteGames() {

        // Variables
        val methodName = "deleteGames" // Method name

        // Delete all games
        Log.d(this.className + ": $methodName", "Delete all games")
        val db = this.writableDatabase
        db.delete(DBConfig.GamesEntry.TABLE_NAME, null, null)
        db.close()

    }
    /******************************************************************************************************************/
    /******************************************* Methods for table "games" ********************************************/
    /******************************************************************************************************************/

    /******************************************************************************************************************/
    /************************************** Methods for table "game_categories" ***************************************/
    /******************************************************************************************************************/
    /**
     * Method private fun mappingGameCategoryContentValues(gameCategory: GameCategory): ContentValues
     *
     * Performs the following tasks:
     *   - Mapping game category content values
     *
     * Parameters:
     *   @param gameCategory: Game category
     */
    private fun mappingGameCategoryContentValues(gameCategory: GameCategory): ContentValues {

        // Variables
        val methodName = "mappingGameCategoryContentValues" // Method name

        // Mapping game category content values
        Log.d(this.className + ": $methodName", "Mapping game categroy content values")
        val contentValues = ContentValues()
        contentValues.put(DBConfig.GameCategoriesEntry.COLUMN_GAME_ID, gameCategory.game_id)
        contentValues.put(DBConfig.GameCategoriesEntry.COLUMN_CATEGORY_ID, gameCategory.category_id)
        return contentValues

    }

    /**
     * Method fun addGameCategory(gameCategory: GameCategory)
     *
     * Performs the following tasks:
     *   - Add a game category
     *
     * Parameters:
     *   @param gameCategory: Game category
     */
    fun addGameCategory(gameCategory: GameCategory) {

        // Variables
        val methodName = "addGameyCategory" // Method name

        // Add a game category
        Log.d(this.className + ": $methodName", "Add a game category")
        val db = this.writableDatabase
        db.insert(DBConfig.GameCategoriesEntry.TABLE_NAME, null, mappingGameCategoryContentValues(gameCategory))
        db.close()

    }

    /**
     * Method fun deleteGameCategory(gameCategory: GameCategory)
     *
     * Performs the following tasks:
     *   - Delete a game category
     *
     * Parameters:
     *   @param gameCategory: Game category
     */
    fun deleteGameCategory(gameCategory: GameCategory) {

        // Variables
        val methodName = "deleteGameCategory" // Method name

        // Delete a game category
        Log.d(this.className + ": $methodName", "Delete a game category")
        val db = this.writableDatabase
        db.delete(DBConfig.GameCategoriesEntry.TABLE_NAME, DBConfig.GameCategoriesEntry.COLUMN_GAME_ID + " = ? AND " + DBConfig.GameCategoriesEntry.COLUMN_CATEGORY_ID + " = ?", arrayOf(gameCategory.game_id.toString(), gameCategory.category_id.toString()))
        db.close()

    }

    /**
     * Method fun deleteGameCategories()
     *
     * Performs the following tasks:
     *   - Delete all game categories
     */
    fun deleteGameCategories() {

        // Variables
        val methodName = "deleteGameCategories" // Method name

        // Delete all game categories
        Log.d(this.className + ": $methodName", "Delete all game categories")
        val db = this.writableDatabase
        db.delete(DBConfig.GameCategoriesEntry.TABLE_NAME, null, null)
        db.close()

    }
    /******************************************************************************************************************/
    /************************************** Methods for table "game_categories" ***************************************/
    /******************************************************************************************************************/

}
