package com.codestorage.nidonnaedon.fragment

import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.DividerSpaceItemDeco
import com.codestorage.nidonnaedon.vm.EntryInfo
import com.codestorage.nidonnaedon.vm.StartInfo
import com.codestorage.nidonnaedon.databinding.EntryItemBinding
import com.codestorage.nidonnaedon.databinding.FragmentStartBinding
import com.markjmind.uni.mapper.annotiation.AutoBinder
import com.markjmind.uni.mapper.annotiation.OnClick
import com.muabe.uniboot.extension.recycler.UniRecyclerAdapter
import com.muabe.uniboot.extension.recycler.UniViewHolder


@AutoBinder
class StartFragment : BaseFragment<FragmentStartBinding>() {

    lateinit var mAdapter: UniRecyclerAdapter
    private val startInfo = StartInfo()

    override fun onPre() {
        binder.startInfoVm = startInfo
        binder.entryInfoVm = EntryInfo()

        mAdapter = UniRecyclerAdapter(binder.recycler).apply {
            addListItem(startInfo.entryList, Holder::class.java)
        }
        binder.recycler.addItemDecoration(DividerSpaceItemDeco(context, LinearLayoutManager.VERTICAL, 5f))

        binder.etHandy.setOnEditorActionListener { textView, actionId, keyEvent ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> if(binder.addPlayer.isEnabled) addPlayer(textView)
            }
            false
        }
    }

    @OnClick
    fun addPlayer(v: View){
        startInfo.entryList.add(binder.entryInfoVm!!)
        mAdapter.notifyItemInserted(mAdapter.itemSize)
        binder.entryInfoVm = EntryInfo()
        binder.etName.requestFocus()
    }

    @OnClick
    fun startGame(v: View){
        if(startInfo.entryList.size < 2){
            showAlert("참가자는 2명 이상만 가능합니다.")
            return
        }
        builder.historyAllClear().setHistory(false).addParam("startInfo", binder.startInfoVm).replace(
            InputPointFragment()
        )
    }

    internal class Holder(view: View) : UniViewHolder<EntryInfo, EntryItemBinding>(view) {
        override fun onPre() {
            binder.vm = item
        }

        @OnClick
        fun btnDelete(v: View){
            adapter.listItem.removeAt(adapterPosition)
            adapter.notifyItemRemoved(adapterPosition)
        }
    }
}

