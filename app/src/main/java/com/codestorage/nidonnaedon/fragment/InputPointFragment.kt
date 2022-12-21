package com.codestorage.nidonnaedon.fragment

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.*
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.DividerItemDeco
import com.codestorage.nidonnaedon.databinding.*
import com.codestorage.nidonnaedon.vm.EntryInfo
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.codestorage.nidonnaedon.vm.StartInfo
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.markjmind.uni.mapper.annotiation.AutoBinder
import com.markjmind.uni.mapper.annotiation.OnClick
import com.markjmind.uni.mapper.annotiation.Param
import com.muabe.uniboot.extension.recycler.UniRecyclerAdapter
import com.muabe.uniboot.extension.recycler.UniViewHolder


@AutoBinder
class InputPointFragment : BaseFragment<FragmentInputPointBinding>() {

    @Param
    lateinit var startInfo: StartInfo
    @Param
    lateinit var firstHoleInfoList: List<HoleInfo>
    @Param
    lateinit var secondHoleInfoList: List<HoleInfo>
    private lateinit var mFirstAdapter: UniRecyclerAdapter
    private lateinit var mSecondAdapter: UniRecyclerAdapter


    override fun onPre() {

        //test
        firstHoleInfoList[0].par = "4"
        firstHoleInfoList[1].par = "3"
        firstHoleInfoList[2].par = "4"
        startInfo.entryList[0].pointsFirst[0].pointStr="0"
        startInfo.entryList[0].pointsFirst[1].pointStr="0"
        startInfo.entryList[0].pointsFirst[2].pointStr="2"
        startInfo.entryList[1].pointsFirst[0].pointStr="-1"
        startInfo.entryList[1].pointsFirst[1].pointStr="0"
        startInfo.entryList[1].pointsFirst[2].pointStr="1"
        startInfo.entryList[2].pointsFirst[0].pointStr="3"
        startInfo.entryList[2].pointsFirst[1].pointStr="0"
        startInfo.entryList[2].pointsFirst[2].pointStr="0"

        mFirstAdapter = UniRecyclerAdapter(binder.rvFirst).apply {
            addSingleItem(firstHoleInfoList, HeaderHolder::class.java).addParam("fragment", this@InputPointFragment)
            addListItem(startInfo.entryList, Holder::class.java).addParam("isFirstHalf", true).addParam("holeInfos", firstHoleInfoList)
        }
        binder.rvFirst.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))

        mSecondAdapter = UniRecyclerAdapter(binder.rvSecond).apply {
            addSingleItem(secondHoleInfoList, HeaderHolder::class.java).addParam("fragment", this@InputPointFragment)
            addListItem(startInfo.entryList, Holder::class.java).addParam("isFirstHalf", false).addParam("holeInfos", secondHoleInfoList)
        }
        binder.rvSecond.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))

    }

    @OnClick
    fun showResult(view: View){
        builder.addParam("startInfo", startInfo)
            .addParam("holeInfoList", mutableListOf<HoleInfo>().apply {
                addAll(firstHoleInfoList)
                addAll(secondHoleInfoList)
            })
            .replace(ResultFragment())
    }

    @OnClick
    fun modifyStartInfo(view: View){
        super.onBackPressed()
    }

    internal class Holder(view: View) : UniViewHolder<EntryInfo, PointRowItemBinding>(view) {

        @Param
        var isFirstHalf: Boolean = true
        @Param
        lateinit var holeInfos: List<HoleInfo>

        override fun onPre() {
            binder.points = if(isFirstHalf) item.pointsFirst else item.pointsSecond
            binder.vm = item
            binder.holeInfos = holeInfos
            binder.myIdx = itemPosition

        }

    }

    internal class HeaderHolder(view: View) : UniViewHolder<List<HoleInfo>, PointHeaderItemBinding>(view) {

        @Param
        lateinit var fragment: InputPointFragment

        override fun onPre() {
            binder.vm = item
        }

        @OnClick(ids = [R.id.hole1, R.id.hole2, R.id.hole3, R.id.hole4, R.id.hole5, R.id.hole6, R.id.hole7, R.id.hole8, R.id.hole9])
        fun onClickHole(view: View){
            val idx = (view as TextView).text.toString().toInt()
            val holeIndex = if(idx > 9) idx - 9 - 1 else idx - 1
            val holeInfo = item[holeIndex]
            if(holeInfo.par == "3"){
                showNearCheckPopup(view.context, holeInfo)
            }
        }

        private fun showNearCheckPopup(context: Context, holeInfo: HoleInfo){
            MaterialAlertDialogBuilder(context)
                .setTitle("Hole ${holeInfo.hole} - Near player 선택하기")
                .setNegativeButton("닫기", null)
                .setPositiveButton("선택취소") { _, _ -> holeInfo.near = -1 }
                .setSingleChoiceItems(fragment.startInfo.entryList.map { it.name }.toTypedArray(), holeInfo.near) { dialog, which ->
                    holeInfo.near = which
                    dialog.dismiss()
                }
                .show()
        }
    }
}

