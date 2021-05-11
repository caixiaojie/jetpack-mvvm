package com.cxj.av.day2

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.cxj.jetpackmvvm.App
import com.cxj.jetpackmvvm.R
import com.cxj.jetpackmvvm.databinding.ActivityAudioRecordBinding
import com.cxj.reacthttp.base.BaseVMActivity
import java.io.File

class AudioRecordActivity : BaseVMActivity() {
    private val binding by binding<ActivityAudioRecordBinding>(R.layout.activity_audio_record)
    @RequiresApi(Build.VERSION_CODES.M)
    override fun initView() {
        binding.run {
            mBtnStart.setOnClickListener {
                val checkSelfPermission = ContextCompat.checkSelfPermission(
                    this@AudioRecordActivity,
                    "android.permission.RECORD_AUDIO"
                )
                if (checkSelfPermission != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), 100)
                }else {
                    AudioRecordUtil.getInstance(App.CONTEXT).startRecord()
                }
            }

            mBtnStop.setOnClickListener {
                AudioRecordUtil.getInstance(App.CONTEXT).stopRecord()
            }

            mBtnPcm2wav.setOnClickListener {
                AudioRecordUtil.getInstance(App.CONTEXT).convertPcmToWav()
            }
        }
    }

    override fun initData() {
    }

    override fun startObserve() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            AudioRecordUtil.getInstance(App.CONTEXT).startRecord()
        }else {
            showToast("权限不足")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AudioRecordUtil.getInstance(App.CONTEXT).release()
    }
}