package com.maciel.murillo.finance_manager.view.finances

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.maciel.murillo.finance_manager.databinding.FragmentFinancesBinding
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.viewmodel.FinancesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FinancesFragment : Fragment() {

    @Inject
    lateinit var financesViewModel: FinancesViewModel

    private val navController by lazy { findNavController() }

    private var _binding: FragmentFinancesBinding? = null
    private val binding get() = _binding!!

    private val content by lazy { binding.contentFinances }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinancesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpListeners()
    }

    private fun setUpObservers() {
        financesViewModel.logout.observe(viewLifecycleOwner, EventObserver {
            navController.navigate(FinancesFragmentDirections.financesToLoginFrag())
        })
    }

    private fun setUpListeners() {
        content.ivLogout.setOnClickListener {
            financesViewModel.logout()
        }
    }
}