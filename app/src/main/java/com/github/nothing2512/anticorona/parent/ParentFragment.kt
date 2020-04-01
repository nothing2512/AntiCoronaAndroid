package com.github.nothing2512.anticorona.parent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MainThread
import androidx.appcompat.view.ContextThemeWrapper
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.github.nothing2512.anticorona.R
import com.github.nothing2512.anticorona.utils.Preference
import com.github.nothing2512.anticorona.utils.getBinding
import com.github.nothing2512.anticorona.utils.launchMain
import com.github.nothing2512.anticorona.vo.Theme
import kotlinx.android.synthetic.*
import org.koin.android.ext.android.inject

abstract class ParentFragment<VDB : ViewDataBinding>(private val layout: Int) : Fragment() {

    protected lateinit var binding: VDB
    protected val preference: Preference by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val wrapper = when (preference.theme) {
            Theme.DEFAULT.toString() -> ContextThemeWrapper(activity, R.style.AppTheme)
            Theme.OCEAN.toString() -> ContextThemeWrapper(activity, R.style.Ocean)
            else -> ContextThemeWrapper(activity, R.style.AppTheme)
        }
        binding = getBinding(inflater.cloneInContext(wrapper), layout, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchMain { subscribeUI(savedInstanceState) }
    }

    override fun onDestroy() {
        super.onDestroy()
        clearFindViewByIdCache()
    }

    @MainThread
    abstract fun subscribeUI(bundle: Bundle?)
}