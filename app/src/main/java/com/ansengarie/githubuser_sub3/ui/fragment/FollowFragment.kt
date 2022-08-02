package com.ansengarie.githubuser_sub3.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ansengarie.githubuser_sub3.data.viewmodel.DetailViewModel
import com.ansengarie.githubuser_sub3.databinding.FragmentFollowBinding
import com.ansengarie.githubuser_sub3.ui.adapter.ViewPagerAdapter

class FollowFragment : Fragment() {

    private var position = 0
    private val bindingFragment: FragmentFollowBinding by viewBinding()
    private val detailViewModel by activityViewModels<DetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFollowBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel.listGithubUser.observe(viewLifecycleOwner) { listGithubUser ->
            bindingFragment.rvFollow.apply {
                adapter = ViewPagerAdapter(listGithubUser)
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireActivity())
            }
        }
    }

    companion object {
        const val FOLLOWING = 0
        const val FOLLOWERS = 1

        fun newInstance(position: Int): FollowFragment =
            FollowFragment().apply {
                this.position = position
            }
    }
}