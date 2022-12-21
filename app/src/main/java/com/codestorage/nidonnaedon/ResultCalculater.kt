package com.codestorage.nidonnaedon

import com.codestorage.nidonnaedon.vm.EntryInfo
import com.codestorage.nidonnaedon.vm.HoleInfo
import com.codestorage.nidonnaedon.vm.StartInfo

object ResultCalculator {

    fun execute(startInfo: StartInfo, holeInfo: List<HoleInfo>): IntArray{
        val earnList = startInfo.entryList.map { it.handy }.toIntArray()

        calculate(startInfo, convertData(startInfo.danwi, startInfo.entryList, holeInfo), earnList)
        return earnList
    }

    private fun convertData(danwi: Int, entryList: List<EntryInfo>, holeInfoList: List<HoleInfo>): List<Pair<HoleInfo, IntArray>> {
        val pointList = entryList.map { it.getAllPoints() }
        return holeInfoList.mapIndexed { index, holeInfo ->
            Pair(holeInfo, pointList.map { it[index].point * danwi }.toIntArray())
//            Pair(Pair(holeInfo.par, holeInfo.near), pointList.map { it[index].point }.toIntArray())
        }
    }

    private fun calculate(startInfo: StartInfo, parPoints: List<Pair<HoleInfo, IntArray>>, earnList: IntArray): IntArray {
        parPoints.forEach {
            calc(startInfo, it, it.first.x2).forEachIndexed { index, i ->
                earnList[index] += i
            }
        }
        return earnList
    }

    private fun isNextDouble(startInfo: StartInfo, par: String, pointList: IntArray) =
        par.isNotEmpty() && (
                pointList.maxOrNull()!! > par.toInt()-1//양파 이상
                || pointList.count { it == pointList[0] } == startInfo.entryList.size//전원이 동타
                || pointList.map { point -> pointList.count { it == point } }.maxOrNull()!! > startInfo.double-1)//지정된 동타인원 이상

    private fun calc(startInfo: StartInfo, parPoint: Pair<HoleInfo, IntArray>, isDouble: Boolean): IntArray{
        val earnList = IntArray(parPoint.second.size)

        //보너스 포인트 계산
        parPoint.second.forEachIndexed { index, value ->
            parPoint.second[index] -= getBonusPoint(startInfo, parPoint.first.par, value)
        }

        //Par3이고 near가 있는 경우
        if(parPoint.first.par == "3" && parPoint.first.near > -1){
            parPoint.second[parPoint.first.near] += if(parPoint.second[parPoint.first.near] > 0) startInfo.near else -startInfo.near
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
            -1000 -> startInfo.buddy
            -2000 -> if(par == "3") startInfo.holeInOne else startInfo.eagle
            -3000 -> startInfo.albatross
            else -> 0
        }
}