package com.ansengarie.githubuser_sub3.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ansengarie.githubuser_sub3.R
import com.ansengarie.githubuser_sub3.data.local.entity.UserEntity
import com.ansengarie.githubuser_sub3.data.viewmodel.DetailViewModel
import com.ansengarie.githubuser_sub3.data.viewmodel.FavViewModel
import com.ansengarie.githubuser_sub3.databinding.ActivityDetailBinding
import com.ansengarie.githubuser_sub3.ui.adapter.DetailAdapter
import com.ansengarie.githubuser_sub3.ui.fragment.FollowFragment
import com.ansengarie.githubuser_sub3.utils.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavViewModel by viewModels { factory }
    private var username: String? = null
    private var url: String? = null
    private var avatar: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        supportActionBar?.apply {
            title = getString(R.string.title_detail)
            setDisplayHomeAsUpEnabled(true)
        }

        val username = intent.getStringExtra(EXTRA_DATA)
        detailViewModel.getDetailUser(username)

        detailViewModel.userData.observe(this) { userData ->
            detailBinding.apply {
                tvUsername.text = ADD_CHAR + userData.username
                tvName.text = userData?.name ?: "-"
                tvLocation.text = userData?.location ?: "-"
                tvCompany.text = userData?.company ?: "-"
                tvFollowers.text = userData.followers.toString()
                tvFollowing.text = userData.following.toString()
                tvRepository.text = userData.repository.toString()

                Glide.with(this@DetailActivity)
                    .load(userData.avatar)
                    .apply(
                        RequestOptions
                            .circleCropTransform()
                            .placeholder(R.drawable.ic_loading)
                    )
                    .into(ivAvatar)

                val fragment = mutableListOf<Fragment>(
                    FollowFragment.newInstance(FollowFragment.FOLLOWING),
                    FollowFragment.newInstance(FollowFragment.FOLLOWERS)
                )

                val fragmentTitle = mutableListOf(
                    getString(R.string.following),
                    getString(R.string.followers)
                )

                val detailAdapter = DetailAdapter(this@DetailActivity, fragment)
                viewPager.adapter = detailAdapter

                TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    tab.text = fragmentTitle[position]
                }.attach()

                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        if (tab?.position == FollowFragment.FOLLOWERS) {
                            detailViewModel.getFollowers(userData.username)
                        } else {
                            detailViewModel.getFollowing(userData.username)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit
                    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
                })
                detailViewModel.getFollowing(userData.username)
            }
            this.username = userData.username.toString()
            this.url = userData.url.toString()
            this.avatar = userData.avatar.toString()
        }

        detailViewModel.toastText.observe(this) {
            it.getContentIfNotHandled()?.let { toastText ->
                Toast.makeText(
                    this, toastText, Toast.LENGTH_SHORT
                ).show()
            }
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        factory = ViewModelFactory.getInstance(this)

        favoriteViewModel.getFavoritedUser().observe(this) { favList ->
            val isFavorited = favList.any {
                it.username == username
            }
            setIconFavorite(isFavorited)

            detailBinding.btnFav.setOnClickListener {
                val entity = username?.let { UserEntity(it, avatar, false) }

                try {
                    if (entity != null) favoriteViewModel.saveDeleteUser(entity, favList.any {
                        it.username == username
                    })
                } catch (e: Exception) {
                    Toast.makeText(
                        this, e.toString(), Toast.LENGTH_SHORT
                    ).show()
                }

                if (isFavorited) {
                    Toast.makeText(
                        this, "Remove $username to favorite", Toast.LENGTH_SHORT
                    ).show()
                    setIconFavorite(isFavorited)
                } else {
                    Toast.makeText(
                        this, "Add $username to favorite", Toast.LENGTH_SHORT
                    ).show()
                    setIconFavorite(isFavorited)
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        detailBinding.progressBarDetail.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setIconFavorite(isFavorited: Boolean) {
        detailBinding.btnFav.apply {
            if (isFavorited) {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_faved
                    )
                )
            } else {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_fav
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        const val ADD_CHAR = "@"
    }
}