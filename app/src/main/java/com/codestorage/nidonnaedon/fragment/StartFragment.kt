package com.codestorage.nidonnaedon.fragment

import android.content.DialogInterface
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.DividerSpaceItemDeco
import com.codestorage.nidonnaedon.vm.EntryInfo
import com.codestorage.nidonnaedon.vm.StartInfo
import com.codestorage.nidonnaedon.databinding.EntryItemBinding
import com.codestorage.nidonnaedon.databinding.FragmentStartBinding
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.markjmind.uni.mapper.annotiation.AutoBinder
import com.markjmind.uni.mapper.annotiation.OnClick
import com.markjmind.uni.mapper.annotiation.Param
import com.muabe.uniboot.extension.recycler.UniRecyclerAdapter
import com.muabe.uniboot.extension.recycler.UniViewHolder


@AutoBinder
class StartFragment : BaseFragment<FragmentStartBinding>() {

    lateinit var mAdapter: UniRecyclerAdapter
    private val startInfo = StartInfo()
    private var mFirstHoleInfoList = makeHeaderInfo(true)
    private var mSecondHoleInfoList = makeHeaderInfo(false)

    override fun onPre() {
        binder.startInfoVm = startInfo
        binder.entryInfoVm = EntryInfo()

        //test
        startInfo.entryList.apply {
            add(EntryInfo().apply {
                name = "ㄱ"
                handyStr = "5000"
            })
            add(EntryInfo().apply {
                name = "ㄴ"
                handyStr = "-5000"
            })
            add(EntryInfo().apply {
                name = "ㄷ"
                handyStr = "0"
            })
        }

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

    private fun makeHeaderInfo(isFirstHalf: Boolean) : List<HoleInfo>{
        val holeList = mutableListOf<HoleInfo>()
        repeat(9) {
            holeList.add(HoleInfo().apply { hole = if(isFirstHalf) (it + 1).toString() else (it + 1 + 9).toString() })
        }
        return holeList
    }

    @OnClick
    fun addPlayer(v: View){
        startInfo.entryList.add(binder.entryInfoVm!!)
        mAdapter.notifyItemInserted(mAdapter.itemSize)
        binder.entryInfoVm = EntryInfo()
        binder.etName.requestFocus()
    }

    override fun isOnBackPressed(): Boolean {
        showConfirm("게임을 종료하시겠습니까?") { _, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                activity?.finish()
            }
        }
        return false
    }

    @OnClick
    fun startGame(v: View){
        if(startInfo.entryList.size < 2){
            showAlert("참가자는 2명 이상만 가능합니다.")
            return
        }
        builder
            .addParam("startInfo", binder.startInfoVm)
            .addParam("firstHoleInfoList", mFirstHoleInfoList)
            .addParam("secondHoleInfoList", mSecondHoleInfoList)
            .replace(InputPointFragment())
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

