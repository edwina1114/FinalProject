package com.example.test.adapter

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.test.database.Scene
import com.example.test.fragments.ListFragmentDirections
import com.example.test.viewModel.MyViewModel
import com.example.test.databinding.ItemLayoutBinding

//adapter class for the recyclerview (use ListAdapter internally)
//passed arguments: view for display the alertdialog, viewModel for accessing the database
class SceneAdapter(val view: Context, val viewModel: MyViewModel) : ListAdapter<Scene, SceneAdapter.ViewHolder>(BallDiffCallback()), SwipeHandlerInterface {

    class BallDiffCallback : DiffUtil.ItemCallback<Scene>() {
        override fun areItemsTheSame(oldItem: Scene, newItem: Scene): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Scene, newItem: Scene): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater, parent, false)
        val viewHolder = ViewHolder(binding)
        viewHolder.itemView.setOnClickListener {
            //get the selected scene's id in the database
            val rawId = getItem(viewHolder.bindingAdapterPosition).id
            //pass the id to the detailfragment
            it.findNavController()
                .navigate(ListFragmentDirections.actionListFragmentToDetailFragment(rawId))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val scene = getItem(position)
        scene.also { holder.binding.scene = it }
        holder.binding.executePendingBindings()
        holder.binding.type.text = scene.type
    }

    override fun onItemDelete(position: Int) {
        //the view has been removed out of the screen
        val deletedScene = getItem(position)
        AlertDialog.Builder(view).apply {
            setTitle("Delete this scene?")
            setCancelable(false)
            setPositiveButton("Yes") {dialog, which ->
                viewModel.deleteScene(deletedScene) //delete the scene from the database
            }
            setNegativeButton("No") {dialog, which ->
                notifyItemChanged(position) //restore the view
            }
            show()
        }
    }
}

//for resolve app:setImage in the item_layout.xml
@BindingAdapter("setImage")
fun ImageView.setSceneImage(scene: Scene?) {
    scene?.let {
        if (scene.photoFile.isNotEmpty()) {
            Glide.with(this.context)
                .load(Uri.parse(scene.photoFile))
                .apply(RequestOptions().centerCrop())
                .into(this)
        } else {
            setImageResource(scene.photoId)
        }
    }
}