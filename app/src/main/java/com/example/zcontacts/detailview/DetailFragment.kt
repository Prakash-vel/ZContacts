package com.example.zcontacts.detailview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.zcontacts.R
import com.example.zcontacts.databinding.FragmentDetailBinding


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_detail, container, false)
        val binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }


}