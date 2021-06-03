package com.example.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.test.database.SceneDatabase
import com.example.test.R
import com.example.test.viewModel.MyViewModel
import com.example.test.viewModel.MyViewModelFactory
import com.example.test.databinding.FragmentDetailBinding

//fragment to display the detailed scene selected from the recyclerview
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        //retrieve the database dao
        val application = requireNotNull(this.activity).application
        val dataSource = SceneDatabase.getInstance(application).sceneDatabaseDao

        //shared viewmodel with the activity
        viewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)
        //retrieve the passed argument (selected scene's id from the recyclerview)
        val args = DetailFragmentArgs.fromBundle(requireArguments())
        viewModel.getScene(args.rawId)

        //set an observer to the liveData and hence update the UI
        viewModel.selectedScene.observe(viewLifecycleOwner, Observer {
            //do data binding in the layout
            it.also { binding.scene = it }
        })

        binding.mapButton.setOnClickListener {
            val passedScene = viewModel.selectedScene.value!!
            it.findNavController()
                .navigate(DetailFragmentDirections.actionDetailFragmentToMapFragment(passedScene.name, passedScene.address))
        }


        return binding.root
    }

}