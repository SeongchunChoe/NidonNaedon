package com.codestorage.nidonnaedon.fragment

import android.content.Context
import android.content.DialogInterface
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
    private lateinit var mFirstAdapter: UniRecyclerAdapter
    private lateinit var mSecondAdapter: UniRecyclerAdapter
    private var mFirstHoleInfoList = makeHeaderInfo(true)
    private var mSecondHoleInfoList = makeHeaderInfo(false)

    override fun onPre() {

        mFirstAdapter = UniRecyclerAdapter(binder.rvFirst).apply {
            addSingleItem(mFirstHoleInfoList, HeaderHolder::class.java).addParam("fragment", this@InputPointFragment)
            addListItem(startInfo.entryList, Holder::class.java).addParam("isFirstHalf", true).addParam("holeInfos", mFirstHoleInfoList)
        }
        binder.rvFirst.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))

        mSecondAdapter = UniRecyclerAdapter(binder.rvSecond).apply {
            addSingleItem(mSecondHoleInfoList, HeaderHolder::class.java).addParam("fragment", this@InputPointFragment)
            addListItem(startInfo.entryList, Holder::class.java).addParam("isFirstHalf", false).addParam("holeInfos", mSecondHoleInfoList)
        }
        binder.rvSecond.addItemDecoration(DividerItemDeco(context, LinearLayoutManager.VERTICAL))

    }

    private fun makeHeaderInfo(isFirstHalf: Boolean) : List<HoleInfo>{
        val holeList = mutableListOf<HoleInfo>()
        repeat(9) {
            holeList.add(HoleInfo().apply { hole = if(isFirstHalf) (it + 1).toString() else (it + 1 + 9).toString() })
        }
        return holeList
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
    fun showResult(view: View){
        builder.addParam("startInfo", startInfo)
            .addParam("holeInfoList", mutableListOf<HoleInfo>().apply {
                addAll(mFirstHoleInfoList)
                addAll(mSecondHoleInfoList)
            })
            .replace(ResultFragment())
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
            val holeIndex = ((view as TextView).text.toString().toInt() % 9) - 1
            val holeInfo = item[holeIndex]
            if(holeInfo.par == "3"){
                showNearCheckPopup(view.context, holeInfo, holeIndex)
            }
        }

        private fun showNearCheckPopup(context: Context, holeInfo: HoleInfo, holeIndex: Int){
            MaterialAlertDialogBuilder(context)
                .setTitle("Hole ${holeInfo.hole} - Near player 선택하기")
                .setSingleChoiceItems(fragment.startInfo.entryList.map { it.name }.toTypedArray(), -1) { dialog, which ->
                    item[holeIndex].near = which
                    dialog.dismiss()
                }
                .show()
        }
    }
}

