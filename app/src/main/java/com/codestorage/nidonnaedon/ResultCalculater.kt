package com.codestorage.nidonnaedon

import com.codestorage.nidonnaedon.vm.EntryInfo
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.codestorage.nidonnaedon.vm.StartInfo

object ResultCalculator {

    fun execute(startInfo: StartInfo, holeInfo: List<HoleInfo>): IntArray{
        val earnList = IntArray(startInfo.entryList.size)
        startInfo.entryList.map { it.handy }.apply {
            this.forEachIndexed{ index, value ->
                for(x in this.indices){
                    if(index != x) {
                        earnList[index] += (value - this[x])
                    }
                }
            }
        }

        calculate(startInfo, convertData(startInfo.entryList, holeInfo), earnList, startInfo.isDoubleStart)
        return earnList.map { it.times(startInfo.danwi) }.toIntArray()
    }

    private fun convertData(entryList: List<EntryInfo>, holeInfoList: List<HoleInfo>): List<Pair<Pair<String, Int>, IntArray>> {
        val pointList = entryList.map { it.getAllPoints() }
        return holeInfoList.mapIndexed { index, holeInfo ->
            Pair(Pair(holeInfo.par, holeInfo.near), pointList.map { it[index].point }.toIntArray())
        }
    }

    private fun calculate(startInfo: StartInfo, parPoints: List<Pair<Pair<String, Int>, IntArray>>, earnList: IntArray, isDouble: Boolean = false): IntArray {
        var isDbl = isDouble
        parPoints.forEach {
            calc(startInfo, it, isDbl).forEachIndexed { index, i ->
                earnList[index] += i
            }
            isDbl = isNextDouble(startInfo, it.first.first, it.second)
        }
        return earnList
    }

    private fun isNextDouble(startInfo: StartInfo, par: String, pointList: IntArray) =
        par.isNotEmpty() && (pointList.maxOrNull()!! > par.toInt()-1 || pointList.map { point -> pointList.count { it == point } }.maxOrNull()!! > startInfo.double-1)

    private fun calc(startInfo: StartInfo, parPoint: Pair<Pair<String, Int>, IntArray>, isDouble: Boolean): IntArray{
        val earnList = IntArray(parPoint.second.size)

        //보너스 포인트 계산
        parPoint.second.forEachIndexed { index, value ->
            parPoint.second[index] -= getBonusPoint(startInfo, parPoint.first.first, value)
        }

        //Par3이고 near가 있는 경우
        if(parPoint.first.first == "3" && parPoint.first.second > -1){
            parPoint.second[parPoint.first.second] += if(parPoint.second[parPoint.first.second] > 0) startInfo.near else -startInfo.near
        }

        parPoint.second.forEachIndexed { index, value ->
            for(x in parPoint.second.indices){
                if(index != x) {
                    earnList[index] += (parPoint.second[x] - value) * if(isDouble) 2 else 1
                }
            }
        }

        return earnList
    }


    //buddy 이상일 경우 보너스 포인트
    private fun getBonusPoint(startInfo: StartInfo, par: String, point: Int): Int =
        when(point){
            -1 -> startInfo.buddy
            -2 -> if(par == "3") startInfo.holeInOne else startInfo.eagle
            -3 -> startInfo.albatross
            else -> 0
        }
}