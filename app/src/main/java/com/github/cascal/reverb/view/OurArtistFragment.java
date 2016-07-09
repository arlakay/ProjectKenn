package com.github.cascal.reverb.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.cascal.reverb.OnFragmentInteractionListener;
import com.github.cascal.reverb.R;
import com.github.cascal.reverb.util.AVLoadingIndicatorDialog;


public class OurArtistFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private WebView mWebView;

    public OurArtistFragment() {
        // Required empty public constructor
    }

    public static OurArtistFragment newInstance(String param1, String param2) {
        OurArtistFragment fragment = new OurArtistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_our_artist, container, false);

        final AVLoadingIndicatorDialog dialog=new AVLoadingIndicatorDialog(getActivity());
        dialog.setMessage("Loading");
        dialog.show();

        mWebView = (WebView) rootView.findViewById(R.id.webview_our_artist);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getHost().endsWith("srmbands.com")) {
                    return false;
                }

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                view.getContext().startActivity(intent);

                dialog.show();

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-header-wrap')[0]) != 'undefined' && document.getElementsByClassName('td-header-wrap')[0] != null){document.getElementsByClassName('td-header-wrap')[0].style.display = 'none';} void 0");
                view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-sub-footer-container')[0]) != 'undefined' && document.getElementsByClassName('td-sub-footer-container')[0] != null){document.getElementsByClassName('td-sub-footer-container')[0].style.display = 'none';} void 0");
                view.loadUrl("javascript:if (typeof(document.getElementsByClassName('td-main-sidebar')[0]) != 'undefined' && document.getElementsByClassName('td-main-sidebar')[0] != null){document.getElementsByClassName('td-main-sidebar')[0].style.display = 'none';} void 0");

                dialog.dismiss();
            }
        });

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Use remote resource
        mWebView.loadUrl("http://www.srmbands.com/our-artist/");

        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
