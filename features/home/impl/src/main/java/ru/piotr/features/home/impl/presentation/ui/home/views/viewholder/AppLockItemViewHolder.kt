package ru.piotr.features.home.impl.presentation.ui.home.views.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

//class AppLockItemViewHolder(
//    private val binding: ItemAppLockListBinding,
//    private val appItemClicked: ((AppLockItemItemViewState) -> Unit)?
//) : RecyclerView.ViewHolder(binding.root) {

//    init {
//        binding.imageViewLock.setOnClickListener {
//            appItemClicked?.invoke(binding.viewState!!)
//        }
//    }

//    fun bind(appLockItemViewState: AppLockItemItemViewState) {
//        binding.viewState = appLockItemViewState
//        binding.executePendingBindings()
//    }
//
//    companion object {
//        fun create(
//            parent: ViewGroup,
//            appItemClicked: ((AppLockItemItemViewState) -> Unit)?
//        ): AppLockItemViewHolder {
//            val binding = DataBindingUtil.inflate<ItemAppLockListBinding>(
//                LayoutInflater.from(parent.context),
//                R.layout.item_app_lock_list,
//                parent,
//                false
//            )
//
//            return AppLockItemViewHolder(binding, appItemClicked)
//        }
//    }
//}