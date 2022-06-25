package com.dip.rickandmorty.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dip.rickandmorty.R

import com.dip.rickandmorty.databinding.HomeCharacterListItemBinding
import com.dip.rickandmorty.databinding.LoadingItemBinding
import com.dip.rickandmorty.models.Character
import com.dip.rickandmorty.utils.Utils

class CharacterListAdapter(private val mList: MutableList<Character>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var context: Context
    private val loadingList: MutableList<Int> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return if (viewType != -1) {
            val binding = HomeCharacterListItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            ItemViewHolder(binding)
        } else {
            val binding = LoadingItemBinding
                .inflate(LayoutInflater.from(parent.context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ItemViewHolder) {
            with(holder) {
                val item = mList[position]
                Glide.with(context).load(item.image).into(binding.ivHomeCharacterAvatar)
                binding.tvHomeCharacterName.text = item.name
                binding.tvHomeCharacterStatus.text = item.status
                when (item.status.lowercase()) {
                    Utils.ALIVE -> {
                        binding.ivHomeCharacterStatus.setBackgroundColor(
                            context.resources.getColor(
                                R.color.green
                            )
                        )
                    }
                    Utils.DEAD -> {
                        binding.ivHomeCharacterStatus.setBackgroundColor(
                            context.resources.getColor(
                                R.color.red
                            )
                        )
                    }
                    else -> {
                        binding.ivHomeCharacterStatus.setBackgroundColor(
                            context.resources.getColor(
                                R.color.unknown
                            )
                        )
                    }
                }
                binding.tvHomeCharacterGender.text = item.gender
                binding.tvHomeCharacterSpecies.text = item.species
            }
        }
    }

    fun addItems(list: List<Character>) {
        val insertPosition = mList.size
        mList.addAll(list)
        notifyItemRangeInserted(insertPosition, mList.size)
    }

    fun addResultItems(list: List<Character>) {
        val removedRange = mList.size
        notifyItemRangeRemoved(0, removedRange)
        mList.clear()
        mList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun showLoading() {
        loadingList.add(0, 0)
        notifyItemRangeInserted(mList.size, mList.size + 1)
    }

    fun hideLoading() {
        loadingList.clear()
        notifyItemRangeRemoved(mList.size + 1, 1)
    }

    fun isLoading(): Boolean {
        return loadingList.isNotEmpty()
    }

    override fun getItemCount(): Int {
        return mList.size + loadingList.size
    }

    override fun getItemViewType(position: Int): Int {
        if (position == mList.size) {
            return -1
        }
        return super.getItemViewType(position)
    }

    class ItemViewHolder(val binding: HomeCharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    class LoadingViewHolder(val binding: LoadingItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

}