package com.codestorage.nidonnaedon.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.codestorage.nidonnaedon.BR

class StartInfo: BaseObservable() {
    val entryList = mutableListOf<EntryInfo>()
    var buddy: Int = 1
        private set
    var eagle: Int = 3
        private set
    var albatross: Int = 7
        private set
    var holeInOne: Int = 6
        private set
    var near: Int = 1
        private set
    var double: Int = 4
        private set

    @Bindable
    var isDoubleStart = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.isDoubleStart)
        }

    @Bindable
    var buddyStr: String = "1"
        set(value) {
            field = value
            buddy = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.buddyStr)
        }

    @Bindable
    var eagleStr: String = "3"
        set(value) {
            field = value
            eagle = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.eagleStr)
        }

    @Bindable
    var albatrossStr: String = "7"
        set(value) {
            field = value
            albatross = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.albatrossStr)
        }

    @Bindable
    var holeInOneStr: String = "6"
        set(value) {
            field = value
            holeInOne = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.holeInOneStr)
        }

    @Bindable
    var nearStr: String = "1"
        set(value) {
            field = value
            near = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.nearStr)
        }

    @Bindable
    var doubleStr: String = "4"
        set(value) {
            field = value
            double = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.doubleStr)
        }
}
