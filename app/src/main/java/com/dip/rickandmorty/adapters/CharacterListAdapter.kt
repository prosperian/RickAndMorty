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
import com.dip.rickandmorty.models.Character
import com.dip.rickandmorty.utils.Utils

class CharacterListAdapter(private val mList: MutableList<Character>) :
    RecyclerView.Adapter<CharacterListAdapter.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HomeCharacterListItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        with(holder) {
            Glide.with(context).load(item.image).into(binding.ivHomeCharacterAvatar)
            binding.tvHomeCharacterName.text = item.name
            binding.tvHomeCharacterStatus.text = item.status
            when (item.status.lowercase()) {
                Utils.ALIVE -> {
                    binding.ivHomeCharacterStatus.setBackgroundColor(context.resources.getColor(R.color.green))
                }
                Utils.DEAD -> {
                    binding.ivHomeCharacterStatus.setBackgroundColor(context.resources.getColor(R.color.red))
                }
                else -> {
                    binding.ivHomeCharacterStatus.setBackgroundColor(context.resources.getColor(R.color.unknown))
                }
            }
            binding.tvHomeCharacterGender.text = item.gender
            binding.tvHomeCharacterSpecies.text = item.species
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

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(val binding: HomeCharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}