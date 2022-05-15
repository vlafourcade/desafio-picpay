package com.picpay.desafio.android.user.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.picpay.desafio.android.core.di.factory.ViewModelFactory
import com.picpay.desafio.android.user.databinding.FragmentUserListBinding
import com.picpay.desafio.android.user.presentation.adapter.UserAdapter
import com.picpay.desafio.android.user.presentation.viewmodel.ListUsersViewModel
import com.picpay.desafio.android.user.presentation.viewmodel.ListUsersViewModelImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserListFragment @Inject constructor() : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ListUsersViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentUserListBinding

    @Inject
    internal lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserListBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        configureLayout()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        viewModel.fetchData()
    }

    private fun configureLayout() {
        binding.rvUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            adapter = userAdapter
        }
    }

    private fun setupObservers() {
        viewModel.data.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { data ->
                userAdapter.setDataSource(data)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { error ->
                Snackbar.make(binding.root, error.message ?: "", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}