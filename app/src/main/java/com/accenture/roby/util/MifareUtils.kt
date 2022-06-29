package com.accenture.roby.util

import android.content.Context
import android.nfc.NfcManager
import android.nfc.tech.MifareClassic

object MifareUtils {

    fun closeCard(mifare: MifareClassic) {
        if (mifare.isConnected) {
            mifare.close()
        }
    }

    fun readBlock(mifare: MifareClassic, blockIndex: Int): String? {
        val sectorIndex = mifare.blockToSector(blockIndex)
        val auth = mifare.authenticateSectorWithKeyB(sectorIndex, Constant.KEY_DEFAULT)
        if (auth) {
            val data = mifare.readBlock(blockIndex)
            return convertBytes2String(data)
        }
        return null
    }

    fun writeBlock(mifare: MifareClassic, blockIndex: Int, content: String) {
        val sectorIndex = mifare.blockToSector(blockIndex)
        val auth = mifare.authenticateSectorWithKeyA(sectorIndex, Constant.KEY_DEFAULT)
        if (auth) {
            mifare.writeBlock(blockIndex, convertString2Bytes(content))
        }
    }

    fun convertString2Bytes(content: String): ByteArray {
        val ret = ByteArray(16)
        val buf = content.toByteArray(Charsets.UTF_8)
        val retLen = ret.size
        val bufLen = buf.size
        val b = retLen > bufLen

        for (i in 0 until retLen) {
            if (b && i >= bufLen) {
                ret[i] = 0
                continue
            }
            ret[i] = buf[i]
        }
        return ret
    }

    fun convertBytes2String(data: ByteArray): String {
        return data.toString(Charsets.UTF_8)
    }

    fun availableNfc(context: Context): Boolean {
        val manager = context.getSystemService(Context.NFC_SERVICE) as NfcManager
        val adapter = manager.defaultAdapter
        return adapter != null && adapter.isEnabled
    }
}