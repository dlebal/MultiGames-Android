package es.dlebal.multigames

import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Class class MainActivity : AppCompatActivity()
 *
 * This class provides MainActivity objects
 */
class MainActivity : AppCompatActivity() {

    /**
     * Variables
     */
    private val className: String = MainActivity::class.java.simpleName // Class name

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
        setContentView(R.layout.activity_main)

        // Initialize the UI
        Log.d(this.className + ": $methodName", "Initialize the UI")
        this.initializeUI()

    }

    /**
     * Method private fun initializeUI()
     *
     * Performs the following tasks:
     *   - Initialize the browser
     */
    private fun initializeUI() {

        // Variables
        val methodName = "initializeUI" // Method name

        // Initialize the browser
        Log.d(this.className + ": $methodName", "Initialize the browser")
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
        browser.loadUrl("file:///android_asset/index.html")

    }

}
