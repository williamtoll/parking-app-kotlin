package com.melbourne.parking.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.melbourne.parking.MainActivity
import com.melbourne.parking.R
import com.melbourne.parking.databinding.DialogFilterBinding


class FilterDialogFragment : DialogFragment() {

    private var _binding: DialogFilterBinding? = null
    private val binding get() = _binding!!

    private var creditCardChecked = false
    private var tapAndGoChecked = false

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogFilterBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.filter_dialog_title)
            .setView(binding.root)
            .setPositiveButton(R.string.filter_dialog_apply) { _, _ ->
//                ParkingFragment().filterBy("","","NO")
                ParkingViewModel().fetchParkingList("","NO","")
                dismiss()
            }
            .setNegativeButton(R.string.filter_dialog_cancel, null)

//        binding.checkboxCreditCard.setOnCheckedChangeListener { _, isChecked ->
//            creditCardChecked = isChecked
//        }
//
//        binding.checkboxTapAndGo.setOnCheckedChangeListener { _, isChecked ->
//            tapAndGoChecked = isChecked
//        }

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
