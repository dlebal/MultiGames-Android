package es.dlebal.multigames.activities

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.transition.Fade
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import es.dlebal.multigames.R
import kotlinx.android.synthetic.main.activity_game.*

/**
 * Class class GameActivity : AppCompatActivity()
 *
 * This class provides GameActivity objects
 */
class GameActivity : AppCompatActivity() {

    /**
     * Constants
     */
     companion object {
        const val GAME_EMBED_CODE_KEY = "embed_code" // Game embed code key
        const val GAME_ORIENTATION_KEY = "orientation" // Game orientation key
        private const val GAME_ORIENTATION_LANDSCAPE_VALUE = "landscape" // Game orientation landscape value
        private const val GAME_ORIENTATION_PORTRAIT_VALUE = "portrait" // Game orientation portrait value
     }

    /**
     * Variables
     */
    private val className: String = GameActivity::class.java.simpleName // Class name
    private var html: String = "" +
            "<!DOCTYPE html>\n" +
            "\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <title>MultiGames</title>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        ####embed_code####" +
            "    </body>\n" +
            "</html>" // HTML
    private val mimeType: String = "text/html" // MIME type
    private val utfType: String = "UTF-8" // UTF type

    /**
     * Method override fun onCreate(savedInstanceState: Bundle?)
     *
     * Performs the following tasks:
     *   - Initialize the activity
     *   - Initialize the UI
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
        setContentView(R.layout.activity_game)

        // Initialize the UI
        Log.d(this.className + ": $methodName", "Initialize the UI")
        this.initializeUI()

    }

    /**
     * Method override fun onResume()
     *
     * Performs the following tasks:
     *   - Resume the browser
     */
    override fun onResume() {

        super.onResume()

        // Variables
        val methodName = "onResume" // Method name

        // Resume the browser
        Log.d(this.className + ": $methodName", "Resume the browser")
        if (browser != null) {
            browser.onResume()
        }

    }

    /**
     * Method override fun onPause()
     *
     * Performs the following tasks:
     *   - Pause the browser
     */
    override fun onPause() {

        super.onPause()

        // Variables
        val methodName = "onPause" // Method name

        // Pause the browser
        Log.d(this.className + ": $methodName", "Pause the browser")
        if (browser != null) {
            browser.onPause()
        }

    }

    /**
     * Method override fun onBackPressed()
     *
     * Performs the following tasks:
     *   - Check if the browser can go back
     *       # The browser can go back:
     *           · Browser go back
     *       # The browser cannot go back:
     *           # Finish the activity
     */
    override fun onBackPressed() {

        // Variables
        val methodName = "onBackPressed" // Method name
        val canTheBrowserGoBack: Boolean = browser.canGoBack() // Can the browser go back?

        // Check if the browser can go back
        Log.d(this.className + ": $methodName", "Check if the browser can go back. Can the browser go back? -> $canTheBrowserGoBack")
        if (browser.canGoBack()) { // The browser can go back

            // Browser go back
            Log.d(this.className + ": $methodName", "Browser go back")
            browser.goBack()

        } else { // The browser cannot go back

            // Finish the activity
            Log.d(this.className + ": $methodName", "Finish the activity")
            this.finish()

        }

    }

    /**
     * Method private fun initializeUI()
     *
     * Performs the following tasks:
     *   - Set the activity transitions
     *   - Hide the action bar
     *   - Check if the game can be loaded
     *       # The game can be loaded:
     *           · Set the screen orientation
     *           · Initialize the browser
     *       # The game cannot be loaded:
     *           · Show the error and finish activity
     */
    private fun initializeUI() {

        // Variables
        val methodName = "initializeUI" // Method name
        val extras: Bundle? = intent.extras // Extras
        val canTheGameBeLoaded: Boolean = (extras != null) && (!TextUtils.isEmpty(extras.getString(GAME_EMBED_CODE_KEY))) // Can the game be loaded?

        // Set the activity transitions
        Log.d(this.className + ": $methodName", "Set the activity transitions")
        window.enterTransition = Fade()
        window.exitTransition = Fade()

        // Hide the action bar
        Log.d(this.className + ": $methodName", "Hide the action bar")
        supportActionBar?.hide()

        // Check if the game can be loaded
        Log.d(this.className + ": $methodName", "Check if the game can be loaded. Can the game be loaded? -> $canTheGameBeLoaded")
        if ((extras != null) && (canTheGameBeLoaded)) { // The game can be loaded

            // Set the screen orientation
            Log.d(this.className + ": $methodName", "Set the screen orientationr")
            val orientation: String = extras.getString(GAME_ORIENTATION_KEY)!!.toLowerCase()
            if (!TextUtils.isEmpty(orientation)) {
                when (orientation.toLowerCase()) {
                    GAME_ORIENTATION_LANDSCAPE_VALUE -> this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    GAME_ORIENTATION_PORTRAIT_VALUE -> this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                }
            }

            // Initialize the browser
            Log.d(this.className + ": $methodName", "Initialize the browser")
            this.html = html.replace("####embed_code####", extras.getString(GAME_EMBED_CODE_KEY)!!).replace("data-mode=\"auto\"", "data-mode=\"fullscreen\"")
            val settings = browser.settings
            settings.javaScriptEnabled = true
            settings.setAppCacheEnabled(true)
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            settings.setAppCachePath(cacheDir.path)
            settings.setSupportZoom(true)
            settings.builtInZoomControls = true
            settings.displayZoomControls = true
            settings.blockNetworkImage = false
            settings.loadsImagesAutomatically = true
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                settings.safeBrowsingEnabled = true
            }
            settings.useWideViewPort = true
            settings.loadWithOverviewMode = true
            settings.javaScriptCanOpenWindowsAutomatically = true
            settings.mediaPlaybackRequiresUserGesture = false
            settings.domStorageEnabled = true
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.allowContentAccess = true
            settings.setGeolocationEnabled(true)
            settings.allowUniversalAccessFromFileURLs = true
            settings.allowFileAccess = true
            browser.fitsSystemWindows = true
            browser.setLayerType(View.LAYER_TYPE_HARDWARE, null)
            browser.loadData(this.html, this.mimeType, this.utfType)

        } else { // The game cannot be loaded

            // Show the error and finish activity
            Log.d(this.className + ": $methodName", "Show the error and finish activity")
            this.finish()

        }

    }

}
