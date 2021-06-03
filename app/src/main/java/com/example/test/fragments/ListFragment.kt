package com.example.test.fragments

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test.adapter.SceneAdapter
import com.example.test.adapter.SwipeHandler
import com.example.test.database.SceneDatabase
import com.example.test.R
import com.example.test.viewModel.MyViewModel
import com.example.test.viewModel.MyViewModelFactory
import com.example.test.databinding.ListFragmentBinding

//fragment with a recyclerview to show a list of scenes
class ListFragment   : Fragment() {

    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModel: MyViewModel
    private lateinit var searchView: SearchView
    private lateinit var gridLayoutManager: GridLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)

        //retrieve the database dao
        val application = requireNotNull(this.activity).application
        val dataSource = SceneDatabase.getInstance(application).sceneDatabaseDao

        //get the shared viewModel associated with the activity
        viewModel =
            ViewModelProvider(this).get(MyViewModel::class.java)
//        //setup RecyclerView
//        val layoutManager = LinearLayoutManager(this.activity)
//        binding.recyclerView.layoutManager = layoutManager


        ///// design the gridlayout & set recyclerview /////
        gridLayoutManager = GridLayoutManager(
            requireContext(), 2,
            LinearLayoutManager.VERTICAL, false
        )
        binding.recyclerView.layoutManager = gridLayoutManager
        binding.recyclerView.setHasFixedSize(true)
        ///// design the gridlayout & set recyclerview /////


        val adapter = SceneAdapter(requireActivity(), viewModel) //based on ListAdapter
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this.activity, DividerItemDecoration.VERTICAL))

        //setup swipe handler
        val swipeHandler = ItemTouchHelper(SwipeHandler(adapter,0,(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)))
        swipeHandler.attachToRecyclerView(binding.recyclerView)

        //observe any changes on the data source of the recylerview
        //sceneList is a livedata return by the database query
        viewModel.sceneList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)  //submit the up-to-date sceneList to the recyclerView
            }
        })

        //enable options menu
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.action_menu, menu)

        // Initialize Search View
        searchView = menu?.findItem(R.id.searchView)?.actionView as SearchView
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchScene(query!!)
                hideKeyboard()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener(object: SearchView.OnCloseListener {
            override fun onClose(): Boolean {
                viewModel.getAllScenes()
                searchView.onActionViewCollapsed()
                hideKeyboard()
                return true
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.
        onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    fun hideKeyboard() {
        // Hide the keyboard.
        val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }


}