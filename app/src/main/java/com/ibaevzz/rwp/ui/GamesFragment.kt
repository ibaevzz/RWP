package com.ibaevzz.rwp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ibaevzz.rwp.databinding.FragmentGamesBinding

class GamesFragment: Fragment() {

    private lateinit var binding: FragmentGamesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamesBinding.inflate(inflater)
        return binding.root
    }

}