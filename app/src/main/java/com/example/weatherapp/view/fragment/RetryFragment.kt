package com.example.weatherapp.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentRetryBinding
import com.example.weatherapp.view.MainActivity

import android.content.Intent


class RetryFragment : Fragment() {

    private lateinit var  binding:FragmentRetryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_retry, container, false)
        binding.retry.setOnClickListener{
            val intent =Intent(context,MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }


        return binding.root
    }

}