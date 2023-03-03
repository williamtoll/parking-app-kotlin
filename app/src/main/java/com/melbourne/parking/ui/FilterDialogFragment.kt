package com.melbourne.parking.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.melbourne.parking.R
import com.melbourne.parking.databinding.DialogFilterBinding


class FilterDialogFragment() : DialogFragment() {

    private var _binding: DialogFilterBinding? = null

    private lateinit var listener: FilterDialogListener


    private val TAG = "FilterDialogFragment"

    interface FilterDialogListener {
        fun onFilterSelected(all: Boolean, tapAndGo: Boolean)
    }

    fun setListener(listener: FilterDialogListener) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_filter, null)

        val showAllCheckbox = view.findViewById<CheckBox>(R.id.show_all_checkbox)
        val showTapangoCheckbox = view.findViewById<CheckBox>(R.id.show_tapango_checkbox)


        val builder = AlertDialog.Builder(requireContext())
            .setTitle(R.string.filter_dialog_title)
            .setView(view)
            .setPositiveButton(R.string.filter_dialog_apply) { _, _ ->

                listener.onFilterSelected(showAllCheckbox.isChecked, showTapangoCheckbox.isChecked)
                dismiss()
            }
            .setNegativeButton(R.string.filter_dialog_cancel, null)

        return builder.create()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
