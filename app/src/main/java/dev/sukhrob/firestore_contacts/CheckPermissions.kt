package dev.sukhrob.firestore_contacts

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener

class CheckPermissions(private val context: Context) {

    fun requestPermission(permission: String) {
        val dialogPermissionListener: PermissionListener = DialogOnDeniedPermissionListener.Builder
            .withContext(context)
            .withTitle("Permission")
            .withMessage("Permission is needed to apk successfuly")
            .withButtonText(android.R.string.ok)
            .withIcon(R.drawable.ic_call)
            .build()

        Dexter.withContext(context).withPermission(permission)
            .withListener(dialogPermissionListener).check()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun checkPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

}