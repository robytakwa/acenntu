package com.mitralaundry.xpro.ui.screen.merchant.member.detail

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.MifareClassic
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.lifecycle.Observer
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.util.MifareUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMemberActivity : ComponentActivity() {

    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mPendingIntent: PendingIntent
    private var mIntentFilters: Array<IntentFilter> = arrayOf()
    private var mTechList: Array<Array<String>> = arrayOf(arrayOf())

    private val viewModel: ShareViewModel by viewModels()
    private var availableNfc = false

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (MifareUtils.availableNfc(this)) {
            availableNfc = true
            mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
            val intent = Intent(this, javaClass).apply {
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            }
            mPendingIntent = PendingIntent.getActivity(
                this, 0, intent,
                PendingIntent.FLAG_MUTABLE
            )
            val filter = IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
            try {
                filter.addDataType("*/*")
            } catch (e: IntentFilter.MalformedMimeTypeException) {
                e.printStackTrace()
            }
            mIntentFilters = arrayOf(filter)
            mTechList = arrayOf(arrayOf(MifareClassic::class.java.name))
        }

        val idMember = intent?.extras?.get("idMember")
       viewModel.setIdMember(idMember.toString())

        viewModel.finish.observe(this, Observer {
            if (it) {
                onBackPressed()
                viewModel.finishNavWriteData()
            }
        })

        setContent {
            DetailMemberCompose(viewModel = viewModel)
        }
    }

    override fun onResume() {
        super.onResume()
        if (availableNfc) {
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mTechList)
        }
    }

    override fun onPause() {
        super.onPause()
        if (availableNfc) {
            mNfcAdapter.disableForegroundDispatch(this)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        if (NfcAdapter.ACTION_TECH_DISCOVERED != intent?.action) {
            return
        }
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {
        val tag = intent!!.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)

        val mifare = MifareClassic.get(intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG))
        viewModel.setNewCard(false)
        viewModel.setIsMember(true)
        viewModel.addMifare(mifare)
        if (tag != null) {
            byteArrayToHexString(tag.id)?.let { viewModel.setIdCard(it) }
        }
    }

    private fun byteArrayToHexString(inarray: ByteArray): String? {
        var i: Int
        var `in`: Int
        val hex =
            arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F")
        var out = ""
        var j = 0
        while (j < inarray.size) {
            `in` = inarray[j].toInt() and 0xff
            i = `in` shr 4 and 0x0f
            out += hex[i]
            i = `in` and 0x0f
            out += hex[i]
            ++j
        }
        return out
    }
}