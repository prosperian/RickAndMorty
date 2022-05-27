package com.dip.rickandmorty.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dip.rickandmorty.R
import com.dip.rickandmorty.adapters.CharacterListAdapter
import com.dip.rickandmorty.api.Resource
import com.dip.rickandmorty.databinding.FragmentHomeBinding
import com.dip.rickandmorty.models.Character
import com.dip.rickandmorty.viewmodels.HomeFragmentViewModel


class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeFragmentViewModel
    private lateinit var adapter: CharacterListAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)

        viewModel.characterList.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it?.data?.results.isNullOrEmpty()) {
                        setUpCharacterList(it?.data?.results!!)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("HomeFragment", "error loading")
                }
            }
        }

        viewModel.newCharacters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it?.data?.results.isNullOrEmpty()) {
                        addNextPage(it?.data?.results!!)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("HomeFragment", "error loading")
                }
            }

        }

        viewModel.resultCharacters.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (!it?.data?.results.isNullOrEmpty()) {
                        addNewItems(it?.data?.results!!)
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("HomeFragment", "error loading")
                }
            }

        }

        viewModel.getCharacterList()
        setUpSearchBar()
    }

    private fun setUpSearchBar() {
        binding.etHomeSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val inputMethodManager =
                    v.context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                viewModel.searchForCharacter(v.text.toString())
            }
            false
        }

        binding.etHomeSearch.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                viewModel.getCharacterList()
            }
        }
    }

    private fun setUpCharacterList(list: MutableList<Character>) {
        val rvHomeCharacters = binding.rvHomeCharacters

        rvHomeCharacters.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = CharacterListAdapter(list)
        rvHomeCharacters.adapter = adapter

        rvHomeCharacters.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    viewModel.loadNextPage()
                }

            }
        })

    }

    private fun addNextPage(list: MutableList<Character>) {
        adapter.addItems(list)
    }

    private fun addNewItems(list: MutableList<Character>) {
        adapter.addResultItems(list)
    }

}