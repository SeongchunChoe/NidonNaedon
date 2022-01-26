package com.codestorage.nidonnaedon.vm

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.codestorage.nidonnaedon.BR

class ResultInfo: BaseObservable() {

    @Bindable
    var name: String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @Bindable
    var totalPoint: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.totalPoint)
        }

    @Bindable
    var winningPoint: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(BR.winningPoint)
        }

}
