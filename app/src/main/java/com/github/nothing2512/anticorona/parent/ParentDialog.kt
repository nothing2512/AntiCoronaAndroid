package com.github.nothing2512.anticorona.parent

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.ViewDataBinding
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.utils.getBinding

@Suppress("LeakingThis")
abstract class ParentDialog<VDB : ViewDataBinding>(
    activity: Activity?,
    parent: ViewGroup,
    layout: Int
) : AlertDialog.Builder(ContextThemeWrapper(activity, R.style.Dialog)) {

    protected val binding: VDB = getBinding(layout, parent)
    protected var alertDialog: AlertDialog
    protected var isCancelable = false

    init {

        setView(binding.root)

        alertDialog = this.create()

        subscribeUI()

        alertDialog.apply {

            requestWindowFeature(Window.FEATURE_NO_TITLE)

            window?.apply {

                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

                attributes?.apply {

                    windowAnimations = R.style.Dialog
                    gravity = Gravity.BOTTOM
                    flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
                }
            }

            setCancelable(isCancelable)
        }
    }

    abstract fun subscribeUI()

    override fun show(): AlertDialog {
        alertDialog.show()
        return alertDialog
    }
}