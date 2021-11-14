package com.example.cleanweatherapp.ui.error

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.DialogFragment
import com.example.cleanweatherapp.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class PermissionDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.title_permissions_dialog))
            .setMessage(getString(R.string.message_permissions_dialog))
            .setIcon(R.drawable.ic_error)
            .setPositiveButton(getString(R.string.positive_permissions_dialog)) { dialog, _ ->
                val appSettingsIntent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", requireActivity().packageName, null)
                )
                if (requireActivity().packageManager.resolveActivity(appSettingsIntent, PackageManager.MATCH_DEFAULT_ONLY) != null){
                    startActivity(appSettingsIntent)
                    dialog.cancel()
                    requireActivity().finish()
                } else
                    Snackbar.make(requireView(), getString(R.string.error_permissions_dialog), Snackbar.LENGTH_LONG).show()
            }
            .setNegativeButton(getString(R.string.negative_permissions_dialog)) { dialog, _ ->
                dialog.cancel()
            }
            .create()
    }
}