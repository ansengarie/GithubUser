package com.ansengarie.githubuser_sub3.utils

import androidx.recyclerview.widget.DiffUtil
import com.ansengarie.githubuser_sub3.data.local.entity.UserEntity

class UserDiffCallback(private val oldList: List<UserEntity>, private val newList: List<UserEntity>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }


    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition].username
        val latest = newList[newItemPosition].username
        return old == latest
    }
}