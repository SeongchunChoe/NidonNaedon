package com.codestorage.nidonnaedon.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.codestorage.nidonnaedon.BR

class HoleInfo: BaseObservable() {
    @Bindable
    var hole: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.hole)
        }

    @Bindable
    var par: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.par)
        }

    @Bindable
    var near: Int = -1
        set(value) {
            field = value
            notifyPropertyChanged(BR.near)
        }

    @Bindable
    var x2: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.x2)
        }
}
