package com.github.cascal.reverb.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyAppWebViewClient extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (Uri.parse(url).getHost().endsWith("srmbands.com")) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);
        return true;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url){
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-header-wrap')[0]) != 'undefined' && document.getElementsByClassName('td-header-wrap')[0] != null){document.getElementsByClassName('td-header-wrap')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-sub-footer-container')[0]) != 'undefined' && document.getElementsByClassName('td-sub-footer-container')[0] != null){document.getElementsByClassName('td-sub-footer-container')[0].style.display = 'none';} void 0");
        view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-main-sidebar')[0]) != 'undefined' && document.getElementsByClassName('td-main-sidebar')[0] != null){document.getElementsByClassName('td-main-sidebar')[0].style.display = 'none';} void 0");

    }

}