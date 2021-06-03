package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.example.test.R
import com.example.test.databinding.FragmentAboutBinding
import com.example.test.databinding.FragmentWebBinding

class WebFragment : Fragment() {
    private lateinit var binding: FragmentWebBinding
    private lateinit var args: WebFragmentArgs


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_web, container, false)
        args = WebFragmentArgs.fromBundle(requireArguments())

        //Webview設定
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = WebViewClient()
        binding.webView.loadUrl(args.link)
        return binding.root
    }

}