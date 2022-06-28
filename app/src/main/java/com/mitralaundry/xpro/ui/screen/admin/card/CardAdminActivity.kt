package com.mitralaundry.xpro.ui.screen.admin.card

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.MifareClassic
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.Observer
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.ui.screen.ShareViewModel
import com.mitralaundry.xpro.util.MifareUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardAdminActivity : ComponentActivity() {

    private lateinit var mNfcAdapter: NfcAdapter
    private lateinit var mPendingIntent: PendingIntent
    private var mIntentFilters: Array<IntentFilter> = arrayOf()
    private var mTechList: Array<Array<String>> = arrayOf(arrayOf())

    private val viewModel: ShareViewModel by viewModels()
    private var availableNfc = false

    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topup_activity)

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
        viewModel.backButton.observe(this, Observer {
            if (it) {
                onBackPressed()
                viewModel.backButtonDone()
            }
        })

        val viewCompose = findViewById<ComposeView>(R.id.view_topup)
        viewCompose.setContent {
            CardAdminCompose(viewModel = viewModel)
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
        if (!NfcAdapter.ACTION_TECH_DISCOVERED.equals(intent?.action)) {
            return
        }
        resolveIntent(intent)
    }

    private fun resolveIntent(intent: Intent?) {
        val mifare = MifareClassic.get(intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG))
        viewModel.setNewCard(true)
        viewModel.addMifare(mifare)
    }
}