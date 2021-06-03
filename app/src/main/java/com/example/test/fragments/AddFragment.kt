package com.example.test.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.test.database.Scene
import com.example.test.database.SceneDatabase
import com.example.test.R
import com.example.test.viewModel.MyViewModel
import com.example.test.viewModel.MyViewModelFactory
import com.example.test.databinding.FragmentAddBinding

//fragment to add a new scene into the database
class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private lateinit var viewModel: MyViewModel
    private var newScene = Scene("", "", "","",0, "")
    val PICKUPIMAGE = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add, container, false)

        //retrieve the database dao
        val application = requireNotNull(this.activity).application
        val dataSource = SceneDatabase.getInstance(application).sceneDatabaseDao

        //shared viewmodel with the activity
        viewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)
        //enable the photo pickup
        binding.selButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, PICKUPIMAGE)
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun saveData() {
        newScene.city = binding.cityEdit.text.toString()
        newScene.name = binding.nameEdit.text.toString()
        newScene.type = binding.typeEdit.text.toString()
        newScene.address = binding.addressEdit.text.toString()
        newScene.description = binding.descriptEdit.text.toString()
        viewModel.insertScene(newScene)
        //simulate the press of the back button
        activity?.onBackPressed()
    }

    // get the photo file path returned from the intent
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICKUPIMAGE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    data.data?.let {
                        newScene.photoFile = it.toString()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.file_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> saveData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStop() {
        super.onStop()
        // Hide the keyboard.
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }
}