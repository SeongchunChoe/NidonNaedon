package com.codestorage.nidonnaedon.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.codestorage.nidonnaedon.BR

class StartInfo: BaseObservable() {
    val entryList = mutableListOf<EntryInfo>()
    var buddy: Int = 1000
        private set
    var eagle: Int = 3000
        private set
    var albatross: Int = 7000
        private set
    var holeInOne: Int = 6000
        private set
    var near: Int = 1000
        private set
    var double: Int = 4
        private set
    var danwi: Int = 1000
        private set

    @Bindable
    var buddyStr: String = "1000"
        set(value) {
            field = value
            buddy = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.buddyStr)
        }

    @Bindable
    var eagleStr: String = "3000"
        set(value) {
            field = value
            eagle = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.eagleStr)
        }

    @Bindable
    var albatrossStr: String = "7000"
        set(value) {
            field = value
            albatross = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.albatrossStr)
        }

    @Bindable
    var holeInOneStr: String = "6000"
        set(value) {
            field = value
            holeInOne = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.holeInOneStr)
        }

    @Bindable
    var nearStr: String = "1000"
        set(value) {
            field = value
            near = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.nearStr)
        }

    @Bindable
    var danwiStr: String = "1000"
        set(value) {
            field = value
            danwi = value.toIntOrNull()?:0
            notifyPropertyChanged(BR.danwiStr)
        }
}
