package es.dlebal.multigames.activities

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.TransitionDrawable
import android.os.Bundle
import android.os.Handler
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.transition.Fade
import android.util.Log
import android.view.View
import com.crashlytics.android.Crashlytics
import es.dlebal.multigames.R
import es.dlebal.multigames.api.multigames.MultiGamesApiInterface
import es.dlebal.multigames.api.multigames.MultiGamesConfig
import es.dlebal.multigames.api.multigames.models.MultiGamesData
import es.dlebal.multigames.db.DB
import es.dlebal.multigames.models.Category
import es.dlebal.multigames.models.Game
import es.dlebal.multigames.models.GameCategory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_splash_screen.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class class SplashScreenActivity : AppCompatActivity()
 *
 * This class provides SplashScreenActivity objects
 */
class SplashScreenActivity : AppCompatActivity() {

    /**
     * Variables
     */
    private val className: String = SplashScreenActivity::class.java.simpleName // Class name
    private var compositeDisposable: CompositeDisposable? = null // CompositeDisposable

    /**
     * Method override fun onCreate(savedInstanceState: Bundle?)
     *
     * Performs the following tasks:
     *   - Initialize the activity and prevent relaunch from launcher icon (only in root activity)
     *   - Initialize the UI
     *
     * Parameters:
     *   @param savedInstanceState: If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Variables
        val methodName = "onCreate" // Method name

        // Initialize the activity and prevent relaunch from launcher icon (only in root activity)
        Log.d(this.className + ": $methodName", "Initialize the activity and prevent relaunch from launcher icon (only in root activity)")
        if (!isTaskRoot) { // Android launched another instance of the root activity into an existing task so just quietly finish and go away, dropping the user back into the activity at the top of the stack
            finish()
            return
        }
        setContentView(R.layout.activity_splash_screen)

        // Initialize the UI
        Log.d(this.className + ": $methodName", "Initialize the UI")
        this.initializeUI()

    }

    /**
     * Method override fun onDestroy()
     *
     * Performs the following tasks:
     *   - Clear all sources
     */
    override fun onDestroy() {

        super.onDestroy()

        // Variables
        val methodName = "onDestroy" // Method name

        // Clear all sources
        Log.d(this.className + ": $methodName", "Clear all sources")
        this.compositeDisposable?.clear()
        loadingIndicator?.visibility = View.GONE
        loading?.visibility = View.GONE

    }

    /**
     * Method private fun getMultiGamesData()
     *
     * Performs the following tasks:
     *   - Get the MultiGames data
     */
    private fun getMultiGamesData() {

        // Variables
        val methodName = "getMultiGamesData" // Method name

        // Get the MultiGames data
        Log.d(this.className + ": $methodName", "Get the MultiGames data")
        val requestInterface = Retrofit.Builder()
            .baseUrl(MultiGamesConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(MultiGamesApiInterface::class.java)
        this.compositeDisposable?.add(requestInterface.getData()
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleMultiGamesDataResponse))

    }

    /**
     * Method private fun initializeUI()
     *
     * Performs the following tasks:
     *   - Set the activity transitions
     *   - Hide the action bar
     *   - Initialize the background
     *   - Initialize the app name
     *   - Get the MultiGames data
     */
    private fun initializeUI() {

        // Variables
        val methodName = "initializeUI" // Method name
        val transitionDuration = 2000 // Transition duration

        // Set the activity transitions
        Log.d(this.className + ": $methodName", "Set the activity transitions")
        window.enterTransition = Fade()
        window.exitTransition = Fade()

        // Hide the action bar
        Log.d(this.className + ": $methodName", "Hide the action bar")
        supportActionBar?.hide()

        // Initialize the background
        Log.d(this.className + ": $methodName", "Initialize the background")
        val color = arrayOf(ColorDrawable(Color.GRAY), ColorDrawable(Color.BLACK))
        val transitionDrawable = TransitionDrawable(color)
        container.background = transitionDrawable
        transitionDrawable.startTransition(transitionDuration)

        // Initialize the app name
        Log.d(this.className + ": $methodName", "Initialize the app name")
        appName.typeface = ResourcesCompat.getFont(this.applicationContext, R.font.arcade)

        // Get the MultiGames data
        Log.d(this.className + ": $methodName", "Get the MultiGames data")
        this.compositeDisposable = CompositeDisposable()
        Handler().postDelayed({
            loadingIndicator.visibility = View.VISIBLE
            loading.visibility = View.VISIBLE
            this.getMultiGamesData()
        }, transitionDuration.toLong())

    }

    /**
     * Method private fun handleMultiGamesDataResponse(multigamesData: MultiGamesData)
     *
     * Performs the following tasks:
     *   - Check if the MultiGames data are correct
     *       # The MultiGames data are correct:
     *           · Delete all data
     *           · Load the categories
     *           · Load the games
     *       # The MultiGames data are not correct:
     *           · Report the issue to Crashlytics
     *
     * Parameters:
     *   @param multigamesData: MultiGames data
     */
    private fun handleMultiGamesDataResponse(multigamesData: MultiGamesData) {

        // Variables
        val methodName = "handleMultiGamesDataResponse" // Method name
        val areTheMultiGamesDataCorrect: Boolean = (multigamesData.categories.isNotEmpty()) && (multigamesData.games.isNotEmpty()) // Are the MultiGames data correct?
        val db = DB(this) // Database

        // Check if the MultiGames data are correct
        Log.d(this.className + ": $methodName", "Check if the MultiGames data are correct. Are the MultiGames data correct? -> $areTheMultiGamesDataCorrect")
        if (areTheMultiGamesDataCorrect) { // The MultiGames data are correct

            // Delete all data
            Log.d(this.className + ": $methodName", "Delete all data")
            db.deleteGameCategories()
            db.deleteGames()
            db.deleteCategories()

            // Load the categories
            Log.d(this.className + ": $methodName", "Load the categories")
            for (category in multigamesData.categories) {
                db.addCategory(Category(category.id, category.name))
            }

            // Load the games
            Log.d(this.className + ": $methodName", "Load the games")
            for (game in multigamesData.games) {
                db.addGame(Game(game.id, game.name, game.description, game.thumbnail, game.screen1, game.screen2, game.screen3, game.video, game.embedCode, game.orientation, game.provider))
                for (categoryId in game.categories) {
                    db.addGameCategory(GameCategory(game.id, categoryId))
                }
            }

        } else { // The MultiGames data are not correct

            // Report the issue to Crashlytics
            Log.d(this.className + ": $methodName", "Report the issue to Crashlytics")
            Crashlytics.logException(Throwable("The MultiGames data are not correct"))

        }
        this@SplashScreenActivity.runOnUiThread {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            finishAfterTransition()
            finish()
        }

    }

}
