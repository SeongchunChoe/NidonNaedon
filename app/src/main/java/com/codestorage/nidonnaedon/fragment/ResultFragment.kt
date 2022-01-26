package com.codestorage.nidonnaedon.fragment

import android.content.DialogInterface
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.ResultCalculator
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.DividerItemDeco
import com.codestorage.nidonnaedon.vm.ResultInfo
import com.codestorage.nidonnaedon.vm.StartInfo
import com.codestorage.nidonnaedon.databinding.*
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.markjmind.uni.mapper.annotiation.AutoBinder
import com.markjmind.uni.mapper.annotiation.Param
import com.muabe.uniboot.extension.recycler.UniRecyclerAdapter
import com.muabe.uniboot.extension.recycler.UniViewHolder


@AutoBinder
class ResultFragment : BaseFragment<FragmentResultBinding>() {

    @Param
    lateinit var startInfo: StartInfo
    @Param
    lateinit var holeInfoList: List<HoleInfo>
    private lateinit var mFirstAdapter: UniRecyclerAdapter

    override fun onPre() {
        val totalWinningPoint = ResultCalculator.execute(startInfo, holeInfoList)
        val totalPointList = startInfo.entryList.map { entryInfo ->
            entryInfo.getAllPoints().map { it.point }.sum()
        }

        val resultList = startInfo.entryList.mapIndexed { index, entryInfo ->
                ResultInfo().apply {
                    name = entryInfo.name
                    totalPoint = totalPointList[index]
                    winningPoint = totalWinningPoint[index]
                }
        }



        mFirstAdapter = UniRecyclerAdapter(binder.recycler).apply {
            addSingleItem("", HeaderHolder::class.java)
            addListItem(resultList, Holder::class.java)
        }
        binder.recycler.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))
    }

    override fun isOnBackPressed(): Boolean {
        showConfirm("게임을 종료하시겠습니까?") { dialog, which ->
            if (which == DialogInterface.BUTTON_POSITIVE) {
                activity?.finish()
            }else{
                onBackPressed()
            }
        }
        return true
    }

    internal class Holder(view: View) : UniViewHolder<ResultInfo, ResultItemBinding>(view) {

        override fun onPre() {
            binder.vm = item
        }

    }

    internal class HeaderHolder(view: View) : UniViewHolder<String, ResultHeaderItemBinding>(view)

}

