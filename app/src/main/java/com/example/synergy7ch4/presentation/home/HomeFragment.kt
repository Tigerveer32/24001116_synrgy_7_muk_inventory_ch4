package com.example.synergy7ch4.presentation.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.search.SearchView
import com.example.synergy7ch4.common.calculateSpanCount
import com.example.synergy7ch4.common.createDialog
import com.example.synergy7ch4.data.local.entities.Product
import com.example.synergy7ch4.databinding.FragmentHomeBinding
import com.example.synergy7ch4.presentation.main.MainActivity

class HomeFragment : Fragment(), HomeInteraction, View.OnClickListener {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: HomeAdapter by lazy { HomeAdapter() }
    private val adapterSearch: HomeSearchAdapter by lazy { HomeSearchAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(onBackPressedCallback())
        bindObserver()
        bindView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun bindView() {
        binding.toolbar.isTitleCentered = true

        binding.searchBar.isDefaultScrollFlagsEnabled = false

        binding.searchView.editText.addTextChangedListener(onTextWatcher())
        binding.searchView.addTransitionListener(onSearchViewShow())

        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvHome.itemAnimator = DefaultItemAnimator()
        binding.rvHome.isNestedScrollingEnabled = true
        binding.rvHome.adapter = adapter

        binding.rvSearch.setHasFixedSize(true)
        binding.rvSearch.layoutManager = StaggeredGridLayoutManager(requireActivity().windowManager.calculateSpanCount(), RecyclerView.VERTICAL)
        binding.rvSearch.itemAnimator = DefaultItemAnimator()
        binding.rvSearch.isNestedScrollingEnabled = true
        binding.rvSearch.adapter = adapterSearch

        binding.fabAdd.setOnClickListener(this)

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onInteraction(this)

        adapterSearch.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapterSearch.onInteraction(this)
    }

    private fun onBackPressedCallback() = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                requireActivity().finish()
            }
        }
    }

    private fun onTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.getQuery(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) { }
    }

    private fun onSearchViewShow() = SearchView.TransitionListener { _, _, newState ->
        if (newState == SearchView.TransitionState.SHOWN) {
            (requireActivity() as MainActivity).bottomNav(false)
        } else {
            (requireActivity() as MainActivity).bottomNav(true)
        }
    }

    private fun bindObserver() {
        viewModel.getAll().observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                binding.imgNotFound.isVisible = true
                adapter.submitList(emptyList())
            } else {
                binding.imgNotFound.isVisible = false
                adapter.submitList(it)
            }
        }

        viewModel.getQuery.observe(viewLifecycleOwner) {
            adapterSearch.submitList(it)
        }

        viewModel.checkUser.observe(viewLifecycleOwner) {
            binding.toolbar.title = "Welcome back, ${it.name}"
        }

        viewModel.checkUser()
    }

    override fun onClick(position: Int, item: Product) {
        val destination = HomeFragmentDirections.actionHomeFragmentToAddProductFragment()
        findNavController().navigate(destination)
    }

    override fun onLongClick(position: Int, item: Product) {
        MaterialAlertDialogBuilder(requireContext()).createDialog(
            title = item.name.toString(),
            message = "Are you seriously, delete? " + item.description.toString(),
            negativeAction = { dialog ->
                dialog.cancel()
            },
            positiveAction = { dialog ->
                viewModel.deleteProduct(item.id)
                dialog.dismiss()
            }
        )
    }

    override fun onClick(p0: View?) {
        val destination = HomeFragmentDirections.actionHomeFragmentToAddProductFragment()
        findNavController().navigate(destination)
    }
}