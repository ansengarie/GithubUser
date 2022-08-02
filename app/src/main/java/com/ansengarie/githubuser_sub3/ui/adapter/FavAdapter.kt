package com.ansengarie.githubuser_sub3.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ansengarie.githubuser_sub3.R
import com.ansengarie.githubuser_sub3.data.local.entity.UserEntity
import com.ansengarie.githubuser_sub3.databinding.ItemRowUserBinding
import com.ansengarie.githubuser_sub3.ui.activity.DetailActivity
import com.ansengarie.githubuser_sub3.utils.UserDiffCallback
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class FavAdapter :
    RecyclerView.Adapter<FavAdapter.ViewHolder>() {

    private var userList = emptyList<UserEntity>()

    fun updateUserList(newList: List<UserEntity>) {
        val diff = DiffUtil.calculateDiff(UserDiffCallback(userList, newList))
        this.userList = newList

        diff.dispatchUpdatesTo(this)
    }

    inner class ViewHolder(private val binding: ItemRowUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserEntity) {
            binding.apply {
                tvItemUsername.text = user.username
                Glide.with(itemView.context)
                    .load(user.avatar)
                    .apply(
                        RequestOptions
                            .circleCropTransform()
                            .placeholder(R.drawable.ic_loading)
                    ).into(ivItemAvatar)
            }

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, user.username)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val userBinding =
            ItemRowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(userBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size
}