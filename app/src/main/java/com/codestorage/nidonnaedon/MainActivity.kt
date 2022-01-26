package com.codestorage.nidonnaedon

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codestorage.nidonnaedon.fragment.StartFragment
import com.muabe.uniboot.boot.wing.MenuBoot

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MenuBoot.putContentView(this).initHomeFragment(StartFragment())
    }

    override fun onBackPressed() {
        if(!MenuBoot.onBackPressed(this)){
            super.onBackPressed()
        }
    }
}