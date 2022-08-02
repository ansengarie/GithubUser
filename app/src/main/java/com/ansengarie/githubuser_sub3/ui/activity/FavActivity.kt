package com.ansengarie.githubuser_sub3.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ansengarie.githubuser_sub3.R
import com.ansengarie.githubuser_sub3.data.viewmodel.FavViewModel
import com.ansengarie.githubuser_sub3.databinding.ActivityFavBinding
import com.ansengarie.githubuser_sub3.ui.adapter.FavAdapter
import com.ansengarie.githubuser_sub3.utils.ViewModelFactory

class FavActivity : AppCompatActivity() {

    private lateinit var favoriteUserBinding: ActivityFavBinding
    private lateinit var factory: ViewModelFactory
    private lateinit var favoriteAdapter: FavAdapter
    private val favoriteViewModel: FavViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteUserBinding = ActivityFavBinding.inflate(layoutInflater)
        setContentView(favoriteUserBinding.root)

        supportActionBar?.apply {
            title = getString(R.string.title_favorite)
            setDisplayHomeAsUpEnabled(true)
        }

        factory = ViewModelFactory.getInstance(this)

        favoriteAdapter = FavAdapter()
        showRecyclerList()
        favoriteViewModel.getFavoritedUser().observe(this) { favoriteUser ->
            favoriteUserBinding.progressBarFav.visibility = View.GONE
            favoriteAdapter.updateUserList(favoriteUser)
        }
    }

    private fun showRecyclerList() {
        favoriteUserBinding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(this@FavActivity)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}