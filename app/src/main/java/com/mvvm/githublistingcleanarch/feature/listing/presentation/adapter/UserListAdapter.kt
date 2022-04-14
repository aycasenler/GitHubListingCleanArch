package com.mvvm.githublistingcleanarch.feature.listing.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mvvm.data.local.model.UserModel
import com.mvvm.githublistingcleanarch.databinding.ItemUserDesignBinding

class UserListAdapter(
    private val users: List<UserModel>, private val listener: ClickListener
) : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    interface ClickListener {
        fun onItemClick(user: UserModel)
        fun onUpdateFavoriteClick(username : String,position: Int)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserDesignBinding.inflate(inflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(users[position])

    override fun getItemCount(): Int = users.size

    inner class ViewHolder(private val binding: ItemUserDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserModel) {
            binding.apply {
                this.user = user
                executePendingBindings()
                ivUserAvatar.setOnClickListener { listener.onItemClick(user) }
                ivFavorite.setOnClickListener { listener.onUpdateFavoriteClick(user.username, position) }
            }
        }
    }
}