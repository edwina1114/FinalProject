package com.example.test.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
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
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://play.google.com/store?hl=zh-hk")
            startActivity(intent)
        }

        binding.btnBus.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.capitalbusgroup.com.tw/CTBUS/index.html")
            startActivity(intent)
        }

        binding.btnPlain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.hulairport.gov.tw/")
            startActivity(intent)
        }

        binding.btnTrain.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://tip.railway.gov.tw/tra-tip-web/tip")
            startActivity(intent)
        }
        return binding.root
    }
}