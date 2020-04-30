package com.example.trendinggit

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.android.synthetic.main.activity_repo_detail.*

class RepoDetail : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_detail)

        val url : String? = intent.getStringExtra("HTML_URL");
        val toolbar : MaterialToolbar? = toolbar_repodetail
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Repository Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true);

        setupWebView()
        webview_repo_detail.loadUrl(url);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle back arrow button click
        if(item.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setupWebView() {
        webview_repo_detail.setInitialScale(1)
        val webSettings = webview_repo_detail.settings
        webSettings.setAppCacheEnabled(false)
        webSettings.builtInZoomControls = true
        webSettings.displayZoomControls = false
        webSettings.javaScriptEnabled = true
        webSettings.useWideViewPort = true
        webSettings.domStorageEnabled = true

        webview_repo_detail.webViewClient = object : WebViewClient(){

        }
    }
}
