package com.orynastarkina.sounds.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
abstract class BaseFragment<V: BaseViewModel<*>> : Fragment() {

    protected var TAG = BaseFlowFragment::class.java.simpleName

    lateinit var viewModel: V

    /**
     * Must return an instance of ViewModel : V for current Fragment
     */
    abstract fun obtainViewModel(): V

    abstract fun getContentViewLayoutId(): Int

    open fun getTagName(): String = TAG

    /**
     * Called immediately after getting instance of viewModel and after onActivityCreated()
     * by Android lifecycle
     */
    protected abstract fun onViewModelReady()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initClassTag()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?)
            = inflater.inflate(getContentViewLayoutId(), container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel = obtainViewModel()
        onViewModelReady()
    }

    private fun initClassTag() {
        TAG = getTagName()
    }
}