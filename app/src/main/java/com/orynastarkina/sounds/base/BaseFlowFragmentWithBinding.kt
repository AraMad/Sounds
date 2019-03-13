package com.orynastarkina.sounds.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Oryna Starkina on 10.01.2019.
 */
abstract class BaseFlowFragmentWithBinding<V: BaseViewModel<IRepository>, B : ViewDataBinding>(fragmentTag: IFlowFragment):
    BaseFlowFragment<V>(fragmentTag){

    protected val NO_DATABINDING_VIEWMODEL_VARIABLE_ID = -1

    protected lateinit var binding : B

    /**
     * Provide a binding model variableId from BR generated class
     * or
     * NO_DATABINDING_VIEWMODEL_VARIABLE_ID if no variable must be set
     */
    abstract fun getBindingViewModelVariableId(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(
            inflater, getContentViewLayoutId(),
            container, false) as B
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.setVariable(getBindingViewModelVariableId(), this.viewModel)
    }

    override fun onViewModelReady() {}
}