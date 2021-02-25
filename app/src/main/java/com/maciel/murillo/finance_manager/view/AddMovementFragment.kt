package com.maciel.murillo.finance_manager.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.maciel.murillo.finance_manager.R
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maciel.murillo.finance_manager.databinding.FragmentAddMovementBinding
import com.maciel.murillo.finance_manager.databinding.FragmentFinancesBinding
import com.maciel.murillo.finance_manager.extensions.toStringValue
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.MovementType
import com.maciel.murillo.finance_manager.utils.EventObserver
import com.maciel.murillo.finance_manager.viewmodel.AddMovementViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddMovementFragment : Fragment() {

    @Inject
    lateinit var addMovementViewModel: AddMovementViewModel

    private val navController by lazy { findNavController() }

    private var _binding: FragmentAddMovementBinding? = null
    private val binding get() = _binding!!

    private val arguments: AddMovementFragmentArgs by navArgs()
    private val movementType: MovementType by lazy { arguments.movementType }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentAddMovementBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
        setUpListeners()
        setThemeByMovementType()
    }

    private fun setUpObservers() {
        addMovementViewModel.saveSuccessfull.observe(viewLifecycleOwner, EventObserver {
            navController.popBackStack()
        })

        addMovementViewModel.formError.observe(viewLifecycleOwner, EventObserver { error ->
            Toast.makeText(context, error.resource, Toast.LENGTH_SHORT).show()
        })
    }

    private fun setUpListeners() {
        binding.fabDone.setOnClickListener {
            val value = binding.etValue.text.toString()
            addMovementViewModel.onClickDone(
                FinancialMovement(
                    value = value.toDouble(),
                    type = movementType.toStringValue(),
                    description = binding.etDescription.text.toString(),
                    category = binding.etCategory.text.toString(),
                    date = binding.etDate.text.toString(),
                )
            )
        }
    }

    private fun setThemeByMovementType() {
        val color = if (movementType == MovementType.RECIPE) {
            R.color.colorPrimaryReceita
        } else {
            R.color.colorPrimaryDespesa
        }
        binding.clExpense.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
    }
}