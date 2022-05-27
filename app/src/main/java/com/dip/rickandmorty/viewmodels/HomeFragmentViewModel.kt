package com.dip.rickandmorty.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dip.rickandmorty.api.ApiResponse
import com.dip.rickandmorty.api.Resource
import com.dip.rickandmorty.api.ResponseHandler
import com.dip.rickandmorty.models.Character
import com.dip.rickandmorty.repositories.NetworkRepository
import com.dip.rickandmorty.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.Response
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    ViewModel() {


    private val _characterList: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> =
        SingleLiveEvent()
    val characterList: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> get() = _characterList

    private val _resultCharacters: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> =
        SingleLiveEvent()
    val resultCharacters: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> get() = _resultCharacters

    private val _newCharacters: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> =
        SingleLiveEvent()
    val newCharacters: SingleLiveEvent<Resource<ApiResponse<MutableList<Character>>>> get() = _newCharacters

    private var _nextPage: String? = null

    fun getCharacterList() = viewModelScope.launch {
        _characterList.postValue(Resource.Loading())
        val response = networkRepository.getCharacters()
        _characterList.postValue(ResponseHandler.handleResponse(response))
        _nextPage = response.body()?.info?.next
    }

    fun searchForCharacter(name: String) = viewModelScope.launch {
        _resultCharacters.postValue(Resource.Loading())
        val response = networkRepository.searchCharacter(name)
        _resultCharacters.postValue(ResponseHandler.handleResponse(response))
        _nextPage = response.body()?.info?.next
    }

    fun loadNextPage() = viewModelScope.launch {
        if (!_nextPage.isNullOrEmpty()) {
            _newCharacters.postValue(Resource.Loading())
            val response = networkRepository.nextPage(_nextPage!!)
            _newCharacters.postValue(ResponseHandler.handleResponse(response))
            _nextPage = response.body()?.info?.next
        }
    }

}