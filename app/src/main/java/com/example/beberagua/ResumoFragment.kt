package com.example.beberagua

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beberagua.databinding.FragmentResumoBinding

class ResumoFragment : Fragment() {

    private lateinit var binding: FragmentResumoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentResumoBinding.inflate(layoutInflater, container, false)



        return binding.root

    }

}