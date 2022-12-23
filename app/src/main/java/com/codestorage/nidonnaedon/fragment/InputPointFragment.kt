package com.codestorage.nidonnaedon.fragment

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.codestorage.nidonnaedon.*
import com.codestorage.nidonnaedon.common.BaseFragment
import com.codestorage.nidonnaedon.common.BindAdapters
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

    fun setPointBackground(fl: FrameLayout, hole: String, point: String) {
        if(hole.isNullOrEmpty() || point.isNullOrEmpty()){
            fl.setBackgroundColor(fl.context.getColor(R.color.white))
        }else{
            val holeInt = hole.toInt()
            val pointInt = point.toInt()
            if(pointInt == holeInt){
                fl.setBackgroundResource(R.drawable.bg_double_par)
            }else if(pointInt == 1){
                fl.setBackgroundResource(R.drawable.bg_bogey)
            }else if(pointInt > 1){
                fl.setBackgroundResource(R.drawable.bg_double_bogey)
            }else if(pointInt == -1){
                fl.setBackgroundResource(R.drawable.bg_buddy)
            }else if(pointInt < -1){
                fl.setBackgroundResource(R.drawable.bg_eagle)
            }else {
                fl.setBackgroundColor(fl.context.getColor(R.color.white))
            }
        }
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
            binder.par1.addTextChangedListener(PointInputTextWatcher(binder.par1.parent as FrameLayout, holeInfos[0]))
            binder.par2.addTextChangedListener(PointInputTextWatcher(binder.par2.parent as FrameLayout, holeInfos[1]))
            binder.par3.addTextChangedListener(PointInputTextWatcher(binder.par3.parent as FrameLayout, holeInfos[2]))
            binder.par4.addTextChangedListener(PointInputTextWatcher(binder.par4.parent as FrameLayout, holeInfos[3]))
            binder.par5.addTextChangedListener(PointInputTextWatcher(binder.par5.parent as FrameLayout, holeInfos[4]))
            binder.par6.addTextChangedListener(PointInputTextWatcher(binder.par6.parent as FrameLayout, holeInfos[5]))
            binder.par7.addTextChangedListener(PointInputTextWatcher(binder.par7.parent as FrameLayout, holeInfos[6]))
            binder.par8.addTextChangedListener(PointInputTextWatcher(binder.par8.parent as FrameLayout, holeInfos[7]))
            binder.par9.addTextChangedListener(PointInputTextWatcher(binder.par9.parent as FrameLayout, holeInfos[8]))
        }

    }

    internal class PointInputTextWatcher(private val parent: FrameLayout, val hole: HoleInfo): TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun afterTextChanged(editable: Editable?) {
            val point = editable.toString()
            BindAdapters.bindingPointBackground(parent, hole.par, point)
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

