package com.codestorage.nidonnaedon.common

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import com.markjmind.uni.UniBindFragment
import com.markjmind.uni.mapper.annotiation.AutoBinder


@AutoBinder
open class BaseFragment<T : ViewDataBinding> : UniBindFragment<T>(){
    fun showAlert(message: String, onClick: DialogInterface.OnClickListener? = null){
        val dlg: AlertDialog.Builder = AlertDialog.Builder(requireContext(),  0)
        dlg.setMessage(message)
        dlg.setPositiveButton("확인", onClick)
        dlg.show()
    }

    fun showConfirm(message: String, onClick: DialogInterface.OnClickListener? = null){
        val dlg: AlertDialog.Builder = AlertDialog.Builder(requireContext(),  0)
        dlg.setMessage(message)
        dlg.setPositiveButton("확인", onClick)
        dlg.setNegativeButton("취소", onClick)
        dlg.show()
    }
}

