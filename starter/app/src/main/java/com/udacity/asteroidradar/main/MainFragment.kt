package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        val adapter = AstroidAdapter(AsteroidClickListener {
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        });
        viewModel.asteroidArray.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)

        })
        binding.asteroidRecycler.adapter=adapter;

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_all_menu -> viewModel.toggleWeekFilter()
            R.id.show_rent_menu -> {
                viewModel.toggleFilterToday()


            }
            R.id.show_buy_menu -> view?.let { Snackbar.make(it,"All Asteroid are saved periodically,so there would be no unsaved Asteroid", Snackbar.LENGTH_SHORT).show() }
        }
        return true
    }
}
