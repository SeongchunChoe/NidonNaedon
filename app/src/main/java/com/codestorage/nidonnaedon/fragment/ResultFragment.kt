package com.codestorage.nidonnaedon.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.ResultCalculator
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.DividerItemDeco
import com.codestorage.nidonnaedon.databinding.FragmentResultBinding
import com.codestorage.nidonnaedon.databinding.ResultHeaderItemBinding
import com.codestorage.nidonnaedon.databinding.ResultItemBinding
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.codestorage.nidonnaedon.vm.ResultInfo
import com.codestorage.nidonnaedon.vm.StartInfo
import com.markjmind.uni.mapper.annotiation.AutoBinder
import com.markjmind.uni.mapper.annotiation.OnClick
import com.markjmind.uni.mapper.annotiation.Param
import com.muabe.uniboot.extension.recycler.UniRecyclerAdapter
import com.muabe.uniboot.extension.recycler.UniViewHolder


@AutoBinder
class ResultFragment : BaseFragment<FragmentResultBinding>() {

    @Param
    lateinit var startInfo: StartInfo
    @Param
    lateinit var holeInfoList: List<HoleInfo>
    private lateinit var mAdapter: UniRecyclerAdapter

    override fun onPre() {
        val totalWinningPoint = ResultCalculator.execute(startInfo, holeInfoList)
        val totalPointList = startInfo.entryList.map { entryInfo ->
            entryInfo.getAllPoints().map { it.point }.sum()
        }

        val resultList = startInfo.entryList.mapIndexed { index, entryInfo ->
                ResultInfo().apply {
                    name = entryInfo.name
                    handy = entryInfo.handy
                    totalPoint = totalPointList[index]
                    winningPoint = totalWinningPoint[index]
                }
        }



        mAdapter = UniRecyclerAdapter(binder.recycler).apply {
            addSingleItem("", HeaderHolder::class.java)
            addListItem(resultList, Holder::class.java)
        }
        binder.recycler.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))
    }

    @OnClick
    fun finishGame(view: View){
        activity?.finish()
    }

    internal class Holder(view: View) : UniViewHolder<ResultInfo, ResultItemBinding>(view) {

        override fun onPre() {
            binder.vm = item
        }

    }

    internal class HeaderHolder(view: View) : UniViewHolder<String, ResultHeaderItemBinding>(view)

}

