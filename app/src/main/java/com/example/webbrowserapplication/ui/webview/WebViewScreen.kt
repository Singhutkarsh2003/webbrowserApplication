package com.example.webbrowserapplication.ui.webview

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.example.webbrowserapplication.data.local.BrowserDatabase
import com.example.webbrowserapplication.data.local.HistoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    url : String,
    navController: NavController
){

    var isLoading by remember { mutableStateOf(true) }

    var progress by remember {mutableStateOf(0)}

    var pageTitle by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }

    var webView: WebView? by remember{mutableStateOf(null)}

    val context = LocalContext.current
    val dao = BrowserDatabase.getDatabase(context).historyDao()

    BackHandler {
        webView?.let {
            if (it.canGoBack()){
                it.goBack()
            }else{
                navController.popBackStack()
            }
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = pageTitle.ifEmpty { "Browser" }
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        webView?.let{
                            if (it.canGoBack()) it.goBack() else navController.popBackStack()
                        }
                    }
                    ) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")

                    }
                },
                actions = {
                    IconButton(
                        onClick = {navController.popBackStack()}
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                    }
                }
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier.fillMaxSize().padding(paddingValues)
        ) {
            if (isLoading){
                LinearProgressIndicator(
                    progress = {
                        progress/100f
                    }
                )
            }
            errorMessage?.let {
                Text(text = it)
            }
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = {context ->
                    WebView(context).apply {

                        webView = this

                        settings.javaScriptEnabled = true

                        settings.domStorageEnabled = true

                        webChromeClient = object : WebChromeClient(){

                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                progress = newProgress
                                isLoading = newProgress <100
                            }
                        }
                        webViewClient = object  : WebViewClient(){

                            override fun onPageStarted(
                                view: WebView?,
                                url: String?,
                                favicon: Bitmap?
                            ) {
                                isLoading = true
                            }

                            override fun onPageFinished(
                                view: WebView?,
                                url: String?
                            ) {

                                super.onPageFinished(view, url)

                                CoroutineScope(Dispatchers.IO).launch {

                                    dao.insertHistory(
                                        HistoryEntity(
                                            url = url ?: ""
                                        )
                                    )
                                }
                            }

                            override fun onReceivedError(
                                view: WebView?,
                                request: WebResourceRequest?,
                                error: WebResourceError?
                            ) {
                                errorMessage = error?.description?.toString()
                            }
                        }
                        loadUrl(url)
                    }
                }
            )
        }
    }
}