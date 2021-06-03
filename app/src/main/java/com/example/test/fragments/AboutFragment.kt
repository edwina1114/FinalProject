package com.example.test.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.test.R
import com.example.test.databinding.FragmentAboutBinding
import com.example.test.databinding.FragmentAddBinding

class AboutFragment : Fragment() {

    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_about, container, false)

        binding.btnGoogleplay.setOnClickListener {
            it.findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToWebFragment("https://play.google.com/store?hl=zh-hk"))
        }

        binding.btnBus.setOnClickListener {
            it.findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToWebFragment("https://www.capitalbusgroup.com.tw/CTBUS/index.html"))
        }

        binding.btnPlain.setOnClickListener {
            it.findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToWebFragment("https://www.hulairport.gov.tw/"))
        }

        binding.btnTrain.setOnClickListener {
            it.findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToWebFragment("https://tip.railway.gov.tw/tra-tip-web/tip"))
        }
        return binding.root
    }
}