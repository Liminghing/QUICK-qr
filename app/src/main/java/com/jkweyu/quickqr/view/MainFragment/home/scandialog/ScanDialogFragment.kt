package com.jkweyu.quickqr.view.MainFragment.home.scandialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.jkweyu.quickqr.R


class ScanDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(requireContext())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.dialog_scan_type, null)

        dialog.setContentView(view)


        return dialog
    }
}
