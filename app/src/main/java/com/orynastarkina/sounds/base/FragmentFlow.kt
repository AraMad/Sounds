package com.orynastarkina.sounds.base

import android.os.Bundle
import android.support.v4.app.FragmentManager
import java.lang.ref.WeakReference
import java.util.*

/**
 * Created by Oryna Starkina on 04.01.2019.
 */
abstract class FragmentFlow<T : IFlowFragment>(
    private val FLOW_TAG: String,
    private val fragmentManager: FragmentManager,
    var fragmentContainer: Int
) {

    private val FRAGMENTS_ROUT_SIZE_THRESHOLD = 1


    protected var fragmentRoute = LinkedList<T>()

    private var flowLifecycleListener: WeakReference<FlowLifecycleListener>? = null

    lateinit var viewModel: BaseViewModel<*>


    open fun backPressed() {
        if (fragmentRoute.size == FRAGMENTS_ROUT_SIZE_THRESHOLD){
            finishFlow()
            return
        } else if (fragmentManager.backStackEntryCount < fragmentRoute.size
            && fragmentRoute.isNotEmpty()) {
            fragmentRoute.removeLast()
        }
    }

    abstract fun startFlow( bundle: Bundle? = null)

    abstract fun initFragment(fragmentDescriptor: T): BaseFlowFragment<*>

    abstract fun onFragmentFinished(fragmentTag: String, jumpTo: String = "")


    open fun finishFlow(bundle: Bundle? = null) {
        flowLifecycleListener?.get()?.onFlowFinished(FLOW_TAG, bundle)
    }

    fun setFlowLifecycleListener(listener: FlowLifecycleListener) {
        flowLifecycleListener = WeakReference(listener)
    }


    /**
     * Attach Fragment to Activity
     *
     * @param fragmentName: name of fragment to be attached
     * @param isAddToBackStack: bool value is fragment needed to be added to backstack
     * @param isReplace: bool value is fragment replace previous
     * @param tag: tag with what fragment wil be attached
     * @param data: fragments data
     */
    protected fun attachFragment(
        fragmentDescriptor: T,
        isAddToBackStack: Boolean = true,
        isReplace: Boolean = true,
        tag: String? = null,
        data: Bundle? = null
    ) {

        val fragment = initFragment(fragmentDescriptor)

        val transaction = fragmentManager.beginTransaction()
        if (data != null) {
            fragment.arguments = data
        }
        if (isReplace) {
            transaction.replace(fragmentContainer, fragment, tag ?: fragment.fragmentDescriptor.getTag())
        } else {
            transaction.add(fragmentContainer, fragment, tag ?: fragment.fragmentDescriptor.getTag())
        }
        if (isAddToBackStack) {
            transaction.addToBackStack(tag ?: fragment.fragmentDescriptor.getTag())
        }
        transaction.commit()

        fragmentRoute.add(fragmentDescriptor)
    }

    /**
     * Detach Fragment from Activity
     *
     * If there are no fragments in stack - finish this flow
     */
    protected fun detachFragmentOrFinishFlow() {
        if (fragmentRoute.size > FRAGMENTS_ROUT_SIZE_THRESHOLD) {
            fragmentManager.popBackStack()
            fragmentRoute.removeLast()
        } else {
            finishFlow()
        }
    }
}