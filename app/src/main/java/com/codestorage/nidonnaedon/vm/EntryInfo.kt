package com.codestorage.nidonnaedon.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.codestorage.nidonnaedon.BR

class EntryInfo: BaseObservable() {

    var pointsFirst: MutableList<Point> = mutableListOf()
    var pointsSecond: MutableList<Point> = mutableListOf()

    init {
        repeat(9) {
            pointsFirst.add(Point())
            pointsSecond.add(Point())
        }
    }

    fun getAllPoints() = mutableListOf<Point>().apply {
        addAll(pointsFirst)
        addAll(pointsSecond)
    }

    @Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    var handy: Int = 0
        private set

    @Bindable
    var handyStr: String = ""
        set(value) {
            field = value
            handy = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.handyStr)
        }

    @Bindable("name", "handyStr")
    fun isEnable() = name.isNotEmpty() && handyStr.isNotEmpty() && handyStr.toIntOrNull() != null

    class Point: BaseObservable() {
        var point: Int = 0
            private set

        @Bindable
        var pointStr: String = ""
            set(value) {
                field = value
                point = value.toIntOrNull()?:0
                notifyPropertyChanged(BR.pointStr)
            }
    }

}
