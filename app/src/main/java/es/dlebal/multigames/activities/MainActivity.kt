package es.dlebal.multigames.activities

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.transition.Explode
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import es.dlebal.multigames.R
import es.dlebal.multigames.adapters.GameAdapter
import es.dlebal.multigames.db.DB
import es.dlebal.multigames.models.Game
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Class class MainActivity : AppCompatActivity(), GameAdapter.Listener
 *
 * This class provides MainActivity objects
 */
class MainActivity : AppCompatActivity(), GameAdapter.Listener {

    /**
     * Variables
     */
    private val className: String = MainActivity::class.java.simpleName // Class name
    private var gameAdapter: GameAdapter? = null // GameAdapter
    private var games: ArrayList<Game>? = null // Games
    private lateinit var interstitialAd: InterstitialAd // InterstitialAd

    /**
     * Method override fun onCreate(savedInstanceState: Bundle?)
     *
     * Performs the following tasks:
     *   - Initialize the activity
     *   - Initialize the UI
     *   - Initialize AdMob
     *
     * Parameters:
     *   @param savedInstanceState: If the activity is being re-initialized after previously being shut down then this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle). Note: Otherwise it is null
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Variables
        val methodName = "onCreate" // Method name

        // Initialize the activity
        Log.d(this.className + ": $methodName", "Initialize the activity")
        setContentView(R.layout.activity_main)

        // Initialize the UI
        Log.d(this.className + ": $methodName", "Initialize the UI")
        this.initializeUI()

        // Initialize AdMob
        Log.d(this.className + ": $methodName", "Initialize AdMob")
        this.initializeAdMob()

    }

    /**
     * Method override fun onBackPressed()
     *
     * Performs the following tasks:
     *   - Move the task containing this activity to the back of the activity stack
     */
    override fun onBackPressed() {

        // Variables
        val methodName = "onBackPressed" // Method name

        // Move the task containing this activity to the back of the activity stack
        Log.d(this.className + ": $methodName", "Move the task containing this activity to the back of the activity stack")
        moveTaskToBack(true)

    }

    /**
     * Method private fun initializeAdMob()
     *
     * Performs the following tasks:
     *   - Initialize AdMob
     *   - Load the banner ad
     *   - Load the intersticial ad
     */
    private fun initializeAdMob() {

        // Variables
        val methodName = "initializeAdMob" // Method name

        // Initialize AdMob
        Log.d(this.className + ": $methodName", "Initialize AdMob")
        MobileAds.initialize(this, getString(R.string.ad_mob_app_id))

        // Load the banner ad
        Log.d(this.className + ": $methodName", "Load the banner ad")
        val adRequest = AdRequest.Builder().build()
        bannerAd.loadAd(adRequest)

        // Load the intersticial ad
        Log.d(this.className + ": $methodName", "Load the intersticial ad")
        this.interstitialAd = InterstitialAd(this)
        this.interstitialAd.adUnitId = getString(R.string.ad_mob_app_activity_game_intersticial_id)
        this.interstitialAd.loadAd(AdRequest.Builder().build())

    }

    /**
     * Method private fun initializeUI()
     *
     * Performs the following tasks:
     *   - Set the activity transitions
     *   - Initialize the game list
     *   - Get games
     */
    private fun initializeUI() {

        // Variables
        val methodName = "initializeUI" // Method name

        // Set the activity transitions
        Log.d(this.className + ": $methodName", "Set the activity transitions")
        window.enterTransition = Explode()
        window.exitTransition = Explode()

        // Initialize the game list
        Log.d(this.className + ": $methodName", "Initialize the game list")
        val gridLayoutManager = GridLayoutManager(this, 2)
        gameList.layoutManager = gridLayoutManager

        // Get games
        Log.d(this.className + ": $methodName", "Get games")
        this.getGames()

    }

    /**
     * Method override fun onItemClick(game: Game)
     *
     * Performs the following tasks:
     *   - Load the game
     *
     * Parameters:
     *   @param game: Game
     */
    override fun onItemClick(game: Game) {

        // Variables
        val methodName = "onItemClick" // Method name

        // Load the game
        Log.d(this.className + ": $methodName", "Load the game")
        if (this.interstitialAd.isLoaded) {
            this.interstitialAd.show()
        }
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra(GameActivity.GAME_EMBED_CODE_KEY, game.embedCode)
        intent.putExtra(GameActivity.GAME_ORIENTATION_KEY, game.orientation)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())

    }

    /**
     * Method private fun getGames()
     *
     * Performs the following tasks:
     *   - Get the games
     */
    private fun getGames() {

        // Variables
        val methodName = "getGames" // Method name
        val db = DB(this) // Database

        // Get the games
        Log.d(this.className + ": $methodName", "Get the games")
        this.games = db.getGames()
        this.gameAdapter = GameAdapter(this.games!!, this)
        gameList.adapter = this.gameAdapter

    }

}
