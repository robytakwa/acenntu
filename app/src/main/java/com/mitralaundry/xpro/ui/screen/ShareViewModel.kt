package com.mitralaundry.xpro.ui.screen

import android.nfc.TagLostException
import android.nfc.tech.MifareClassic
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.mitralaundry.xpro.data.database.model.CustomerCard
import com.mitralaundry.xpro.data.model.Member
import com.mitralaundry.xpro.data.model.response.Merchant
import com.mitralaundry.xpro.data.model.response.ResultData
import com.mitralaundry.xpro.data.model.response.ResultMember
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.data.model.TitipLaundry
import com.mitralaundry.xpro.data.repository.CustomerCardRepository
import com.mitralaundry.xpro.data.repository.MemberRepository
import com.mitralaundry.xpro.data.repository.MerchantRepository
import com.mitralaundry.xpro.data.repository.TitipRepository
import com.mitralaundry.xpro.ui.screen.admin.card.MasterType
import com.mitralaundry.xpro.ui.screen.merchant.member.detail.DetailMemberRepository
import com.mitralaundry.xpro.ui.screen.merchant.topup.Harga
import com.mitralaundry.xpro.util.Constant.BLOCK_BRAND
import com.mitralaundry.xpro.util.Constant.BLOCK_CODE
import com.mitralaundry.xpro.util.Constant.BLOCK_COUNTER
import com.mitralaundry.xpro.util.Constant.BLOCK_COUNTER_PRICE
import com.mitralaundry.xpro.util.Constant.BLOCK_DURATION
import com.mitralaundry.xpro.util.Constant.BLOCK_MAC_ADDRESS
import com.mitralaundry.xpro.util.Constant.BLOCK_MEMBER_ID
import com.mitralaundry.xpro.util.Constant.BLOCK_OUTLET_ID
import com.mitralaundry.xpro.util.Constant.BLOCK_MERCHANT_ID
import com.mitralaundry.xpro.util.Constant.BLOCK_PULSE
import com.mitralaundry.xpro.util.Constant.BLOCK_SALDO
import com.mitralaundry.xpro.util.Constant.BLOCK_TITLE
import com.mitralaundry.xpro.util.Constant.BLOCK_TYPE_MACHINE
import com.mitralaundry.xpro.util.Constant.BLOCK_UPDATE_PRICE
import com.mitralaundry.xpro.util.Constant.BLOCK_USER
import com.mitralaundry.xpro.util.Constant.CODE_COUNTER
import com.mitralaundry.xpro.util.Constant.CODE_MAC
import com.mitralaundry.xpro.util.Constant.CODE_USER
import com.mitralaundry.xpro.util.Constant.CODE_MASTER
import com.mitralaundry.xpro.util.Constant.CODE_PRICE
import com.mitralaundry.xpro.util.Constant.CODE_RESET
import com.mitralaundry.xpro.util.GsonUtil
import com.mitralaundry.xpro.util.MifareUtils.closeCard
import com.mitralaundry.xpro.util.MifareUtils.readBlock
import com.mitralaundry.xpro.util.MifareUtils.writeBlock
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    private val repositoryMember: MemberRepository,
    private val repository: DetailMemberRepository,
    private val repositoryMerchant: MerchantRepository,
    private val repositoryTitip: TitipRepository,
    private val customerRepository: CustomerCardRepository
) :
    ViewModel() {

    private lateinit var mifare: MifareClassic
    private var verify: Boolean = false
    private var verifySaldo: Boolean = false
    private var verifyTopup: Boolean = false

    private val _status = MutableLiveData<Status>()
    val status: LiveData<Status> = _status

    private val _memberIdOnCard = MutableLiveData<String>()
    val memberIdOnCard: LiveData<String> = _memberIdOnCard

    private val _idMember = MutableLiveData<String>()
    val idMember: LiveData<String> = _idMember

    private val _masterKeyType = MutableLiveData<MasterType>()
    val masterKeyType: LiveData<MasterType> = _masterKeyType

    private val _duration = MutableLiveData<String>()
    val duration: LiveData<String> = _duration

    private val _price = MutableLiveData<String>()
    val price: LiveData<String> = _price

    private val _pulse = MutableLiveData<Boolean>()
    val pulse: LiveData<Boolean> = _pulse

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _brand = MutableLiveData<String>()
    val brand: LiveData<String> = _brand

    private val _username = MutableLiveData<String>()
    val username: LiveData<String> = _username

    private val _saldo = MutableLiveData<Int>()
    val saldo: LiveData<Int> = _saldo

    private val _textInfo = MutableLiveData<String>()
    val textInfo: LiveData<String> = _textInfo

    private val _disableButton = MutableLiveData<Boolean>()
    val disableButton: LiveData<Boolean> = _disableButton

    private val _backButton = MutableLiveData<Boolean>()
    val backButton: LiveData<Boolean> = _backButton

    private val _harga = MutableLiveData<Harga>()
    val topup: LiveData<Harga> = _harga

    private val _totalPotong = MutableLiveData<String>()
    val totalPotong: LiveData<String> = _totalPotong

    private val _finish = MutableLiveData<Boolean>()
    val finish: LiveData<Boolean> = _finish

    private val _newCard = MutableLiveData<Boolean>()
    val newCard: LiveData<Boolean> = _newCard

    private val _openDialog = MutableLiveData<Boolean>()
    val openDialog: LiveData<Boolean> = _openDialog

    private val _titleDialog = MutableLiveData<String>()
    val titleDialog: LiveData<String> = _titleDialog

    private val _counter = MutableLiveData<String>()
    val counter: LiveData<String> = _counter

    private val _phoneNumber = MutableLiveData<String>()
    val phoneNumber: LiveData<String> = _phoneNumber

    private val _outletId = MutableLiveData<String>()
    val outletId: LiveData<String> = _outletId

    private val _idCard = MutableLiveData<String>()
    val idCard: LiveData<String> = _idCard

    private val _isMember = MutableLiveData<Boolean>(false)
    val isMember = _isMember

    private val _typeMachine = MutableLiveData<String>()
    val typeMachine = _typeMachine

    private val _totalCounter = MutableLiveData<Int>()
    val totalCounter = _totalCounter

    private val _isNewCard = MutableLiveData<Boolean>()
    private val isNewCard: LiveData<Boolean> = _isNewCard

    private val _merchantId = MutableLiveData<String>()
    private val merchantId: LiveData<String> = _merchantId

    private val _memberId = MutableLiveData<String>()
    private val memberId: LiveData<String> = _memberId

    private val _priceCounter = MutableLiveData<String>()
    private val priceCounter: LiveData<String> = _priceCounter

    private val _macAddress = MutableLiveData<String>()
    private val macAddress: LiveData<String> = _macAddress

    private val _data = MutableLiveData<List<Merchant>>()
    val listMerchant: LiveData<List<Merchant>> = _data


    init {
        _newCard.value = false
        _username.value = "Name Card"
        _saldo.value = 0
        _textInfo.value = "Tempelkan Kartu"
        _harga.value = Harga(title = 0, topup = 0, harga = 0)
        _totalPotong.value = ""
        _disableButton.value = false

        viewModelScope.launch {
            // try to remove all customer card on local
            customerRepository.deleteAll()
        }
    }

    fun setFinish(finish : Boolean) {
        _finish.value = finish
    }

    fun setMemberId(newMember: String) {
        _memberId.value = newMember
    }

    fun setMerchantId(merchantId: String) {
        _merchantId.value = merchantId
    }

    fun setIsMember(isMember: Boolean) {
        _isMember.value = isMember
    }

    fun setIdMember(idMember: String) {
        _idMember.value = idMember
    }

    fun setIdCard(id: String) {
        _idCard.value = id
    }

    fun setOutletId(outletId: String) {
        _outletId.value = outletId
    }

    fun setIsNewCard(isNewCard: Boolean) {
        _isNewCard.value = isNewCard
    }

    fun setPhoneNumber(phoneNumber: String) {
        _phoneNumber.value = phoneNumber
    }

    fun setOpenDialog(open: Boolean) {
        _openDialog.value = open
    }

    fun setMasterKey(key: MasterType) {
        _masterKeyType.value = key
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setBrand(brand: String) {
        _brand.value = brand
    }

    fun setPrice(price: String) {
        _price.value = price
    }

    fun setPulse(pulse: Boolean) {
        _pulse.value = pulse
    }

    fun setDuration(duration: String) {
        _duration.value = duration
    }

    fun setNewCard(newCard: Boolean) {
        _newCard.value = newCard
    }

    fun setTotalPotong(total: String) {
        _totalPotong.value = total
    }

    fun selectHarga(harga: Harga) {
        _harga.value = harga
    }

    fun backButton() {
        _backButton.value = true
    }

    fun backButtonDone() {
        _backButton.value = false
    }

    fun setTypeMachine(type: String) {
        _typeMachine.value = type
    }

    fun addMifare(mifareClassic: MifareClassic) {
        mifare = mifareClassic
        if (_newCard.value == false) {
            readCard()
        } else {
            connectCard()
        }
    }

    fun finishWriteData() {
        _finish.value = true
    }

    fun finishNavWriteData() {
        _finish.value = false
    }

    fun setUserName(username: String) {
        _username.value = username
    }

    fun setSaldo(saldo: Int) {
        _saldo.value = saldo
    }

    fun readCounter() {
        try {
            connectCard()
            val counter = readBlock(mifare, BLOCK_COUNTER)
            val price = readBlock(mifare, BLOCK_COUNTER_PRICE)
            val type = readBlock(mifare, BLOCK_TYPE_MACHINE)
            val macAddress = readBlock(mifare, BLOCK_MAC_ADDRESS)

            _macAddress.value = macAddress?.filter { it.isLetterOrDigit() }
            val aCounter = counter?.filter { it.isDigit() }?.toInt()
            val aPrice = price?.filter { it.isDigit() }?.toInt()
            var total = 0
            aCounter?.let { bCounter ->
                aPrice?.let { bPrice ->
                    _priceCounter.value = bPrice.toString()
                    total = bCounter * bPrice
                }
            }

            type?.let {
                _typeMachine.value = it
            }
            _totalCounter.value = total
            _counter.value = counter?.filter { it.isDigit() }
            closeCard(mifare)
        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
    }

    fun writeMasterCard() {
        try {
            connectCard()
            when (masterKeyType.value) {
                MasterType.MASTER -> {
                    val onOff = if (pulse.value == true) "ON" else "OFF"
                    writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_MASTER)
                    writeBlock(mifare, blockIndex = BLOCK_PULSE, onOff)
                    writeBlock(mifare, blockIndex = BLOCK_TITLE, title.value.toString())
                    writeBlock(
                        mifare,
                        blockIndex = BLOCK_UPDATE_PRICE,
                        price.value.toString().filter { it.isDigit() })
                    writeBlock(mifare, blockIndex = BLOCK_BRAND, brand.value.toString())
                    writeBlock(mifare, blockIndex = BLOCK_DURATION, duration.value.toString())
                    writeBlock(
                        mifare,
                        blockIndex = BLOCK_TYPE_MACHINE,
                        typeMachine.value.toString()
                    )
                    writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
                }
                MasterType.PRICE -> {
                    writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_PRICE)
                    writeBlock(
                        mifare,
                        blockIndex = BLOCK_UPDATE_PRICE,
                        price.value.toString().filter { it.isDigit() })
                    writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
                }
                MasterType.RESET -> {
                    writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_RESET);
                    writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
                }
                MasterType.COUNTER -> {
                    writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_COUNTER);
                    writeBlock(mifare, blockIndex = BLOCK_COUNTER, "0");
                    writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
                }
                else -> {
                    // write to get mac address
                    writeBlock(mifare, blockIndex = BLOCK_MAC_ADDRESS, CODE_MAC);
                    writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
                }
            }
            _titleDialog.value = "Success"
            _openDialog.value = true
            _disableButton.value = false
            _textInfo.value = "Tulis data di kartu berhasil, silakan kembali ke home menu"
            closeCard(mifare)
        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
    }

    /**
     * Write data to card
     */
    private fun writeNewCardUser() {
        try {
            connectCard()
            writeBlock(mifare, blockIndex = BLOCK_CODE, CODE_USER)
            writeBlock(mifare, blockIndex = BLOCK_USER, username.value.toString())
            writeBlock(mifare, blockIndex = BLOCK_SALDO, saldo.value.toString())
            writeBlock(mifare, blockIndex = BLOCK_MERCHANT_ID, merchantId.value.toString())
            writeBlock(mifare, blockIndex = BLOCK_MEMBER_ID, memberId.value.toString())
            writeBlock(mifare, blockIndex = BLOCK_OUTLET_ID, outletId.value.toString())
            _disableButton.value = false
            _textInfo.value = "Verifikasi data"
            closeCard(mifare)

            // save to local database for verify then
            viewModelScope.launch {
                if (verify) {
                    val newCard = CustomerCard(
                        id = null,
                        saldo.value.toString(),
                        username.value.toString(),
                        merchantId.value.toString(),
                        memberId.value.toString(),
                        outletId.value.toString()
                    )
                    customerRepository.save(newCard)
                    verifyNewCard()
                }
            }
        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
    }

    private fun verifyNewCard() {
        readCard()
    }

    fun saveMemberToCloud() {
        // todo add loading on screen
        _status.value = Status.LOADING
        viewModelScope.launch {
            try {
                val member = Member(
                    outletId = outletId.value.toString(),
                    name = username.value.toString(),
                    phone = phoneNumber.value.toString(),
                    keyCard = idCard.value.toString(),
                    saldo = saldo.value!!,
                    memberId = ""
                )
                val result = repositoryMember.addMember(member)
                if (result.code == 201) {
                    _status.value = Status.SUCCESS
                    val newResult =
                        GsonUtil.fromJson<ResultMember>(Gson().toJson(result.results))
                    repositoryMember.saveSession(newResult.accessToken)
                    _memberId.value = newResult.memberId
                    _memberIdOnCard.value = newResult.memberId
                    // todo write to card

                    verify = true
                    writeNewCardUser()
                }
                println(result)
            } catch (throwable: Throwable) {
                _status.value = Status.ERROR
                println(throwable.message)
            }
        }
    }

    fun topUpSaldo() {
        try {
            connectCard()
            val valueSaldo = readBlock(mifare, blockIndex = BLOCK_SALDO)
            valueSaldo?.let {
                val saldoValue = valueSaldo.filter { it.isDigit() }
                val total = saldoValue.toInt() + topup.value?.harga!!
                setSaldo(total)
                verifyTopup = true
                writeBlock(mifare, blockIndex = BLOCK_SALDO, total.toString())
                closeCard(mifare)
                // read card again for verify data
                readCard()
            }
        } catch (e: TagLostException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        } catch (e: UninitializedPropertyAccessException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
    }

    fun cutSaldo() {
        _status.value = Status.LOADING
        if (_totalPotong.value.toString().isBlank()) {
            _textInfo.value = "Isi total saldo terlebih dahulu"
            closeCard(mifare)
        } else {
            try {
                _disableButton.value = false
                connectCard()
                val valueSaldo = readBlock(mifare, BLOCK_SALDO)
                valueSaldo?.let {
                    val saldoValue = valueSaldo.filter { it.isDigit() }
                    val totalCut = _totalPotong.value?.filter { it.isDigit() }?.toInt()
                    if (saldoValue.toInt() <= totalCut!!) {
                        _textInfo.value = "Saldo tidak cukup, silakan topup terlebih dahulu"
                        _disableButton.value = false
                    } else {
                        val total =
                            saldoValue.toInt() - (_totalPotong.value?.filter { it.isDigit() }
                                ?.toInt()
                                ?: 0)
                        setSaldo(total)
                        writeBlock(mifare, BLOCK_SALDO, total.toString())
                        _textInfo.value = "Verify data"
                        verifySaldo = true
                        readCard()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
                _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
                closeCard(mifare)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
                _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
                closeCard(mifare)
            }
        }
    }

    // TODO, tambakan block outletID
    /**
     * ReadCard
     * Fungsi membaca block username, saldo, member Id, block counter
     * Ketika block berisikan CODE_USER maka fungsi ini akan me-return-kan data
     * a. Username
     * b. Saldo
     * c. MemberID
     * Ketika block berisikan CODE_COUNTER, maka funsi ini akan me-return-kan data
     * a. value count
     * b. type mesin yang digunakan
     */
    private fun readCard() {
        try {
            connectCard()
            when (readBlock(mifare, blockIndex = BLOCK_CODE)?.filter { it.isLetterOrDigit() }) {
                CODE_USER -> {
                    val valueUsername = readBlock(mifare, blockIndex = BLOCK_USER)
                    if (isMember.value == false) {
                        valueUsername?.let {
                            setUserName(valueUsername)
                        }
                    }
                    val valueSaldo = readBlock(mifare, blockIndex = BLOCK_SALDO)
                    valueSaldo?.let {
                        val saldoValue = valueSaldo.filter { it.isDigit() }
                        if (saldoValue.isNotEmpty()) {
                            val total = saldoValue.toInt()
                            setSaldo(total)
                        }
                    }
                    val valueMemberId = readBlock(mifare, blockIndex = BLOCK_MEMBER_ID)
                    valueMemberId?.let {
                        val value = it.filter { filter -> filter.isLetterOrDigit() }
                        setMemberId(value)
                    }
                    val mMerchantId = readBlock(mifare, blockIndex = BLOCK_MERCHANT_ID)
                    mMerchantId?.let {
                        val value = it.filter { filter -> filter.isLetterOrDigit() }
                        setMerchantId(value)
                    }
                    val readOutletId = readBlock(mifare, blockIndex = BLOCK_OUTLET_ID)
                    readOutletId?.let {
                        val value = it.filter { filter -> filter.isLetterOrDigit() }
                        setOutletId(value)
                    }

                    viewModelScope.launch {


                        if (verify) {
                            verify = false
                            val customer =
                                customerRepository.getCustomerById(memberId.value.toString())
                            val saldoBefore = customer.saldo.toInt()
                            val saldoAfter = saldo.value
                            // baca data dari kartu
                            val onLocal =
                                customerRepository.getCustomerById(memberIdOnCard.value.toString())
                            // baca data kalo database
                            // compare
                            if (onLocal.saldo == saldo.value.toString() &&
                                onLocal.outletId == outletId.value.toString() &&
                                onLocal.merchantId == merchantId.value.toString() &&
                                onLocal.memberId == memberId.value.toString() &&
                                onLocal.username == username.value.toString()
                            ) {
                                // notif user kalo data terverifikasi
                                // hapus data di table

                                _textInfo.value = "Verification completed"
                                _disableButton.value = false

                                // remove data on local
                                customerRepository.delete(onLocal)
                            } else {
                                setSaldo(onLocal.saldo.toInt())
                                setOutletId(onLocal.outletId)
                                setMerchantId(onLocal.merchantId)
                                setMemberId(onLocal.memberId)
                                setUserName(onLocal.username)

                                writeNewCardUser()
                            }
                        } else if (verifySaldo) {
                            val customer =
                                customerRepository.getCustomerById(memberId.value.toString())
                            val saldoBefore = customer.saldo.toInt()
                            val saldoAfter = saldo.value

                            val totalPotong = totalPotong.value?.filter { it.isDigit() }?.toInt()!!
                            val total = saldoBefore - totalPotong
                            if (saldoAfter == total) {
                                verifySaldo = false
                                _disableButton.value = true
                                _textInfo.value =
                                    "Berhasil potong saldo, silakan tab lagi untuk proses selanjutnya"
                                _totalPotong.value = ""

                                // update data ke server
                                val titppLaundry =
                                    TitipLaundry(
                                        memberId.value.toString(),
                                        saldoAfter.toString(),
                                        totalPotong.toString()
                                    )
                                repositoryTitip.updateTitipLaundry(titppLaundry)
                            } else {
                                // tidak sama, tulis ulang ke kartu
                                setSaldo(total)
                                writeBlock(mifare, BLOCK_SALDO, total.toString())
                                _textInfo.value = "Verify data"
                                verifySaldo = true
                                readCard()
                            }
                        } else if (verifyTopup) {
                            verifyTopup = false
                            val customer =
                                customerRepository.getCustomerById(memberId.value.toString())
                            val saldoBefore = customer.saldo.toInt()
                            val saldoAfter = saldo.value

                            val topupValue = topup.value?.harga!!
                            val total = saldoBefore + topupValue

                            if (total == saldoAfter) {
                                println("verify")
                                _disableButton.value = false
                                _finish.value = true
                            } else {
                                println("not verify")
                                println("write again")
                                setSaldo(total)
                                connectCard()
                                writeBlock(mifare, blockIndex = BLOCK_SALDO, saldo.value.toString())
                                verifyTopup = true
                            }
                        } else {
                            /**
                             * We should save data from card to local database for
                             * comparison  for next jobs like top up or cut saldo
                             */
                            val customerCard = CustomerCard(
                                saldo = saldo.value.toString(),
                                username = username.value.toString(),
                                merchantId = merchantId.value.toString(),
                                memberId = memberId.value.toString(),
                                id = null,
                                outletId = outletId.value.toString()

                            )
                            customerRepository.save(customerCard)
                        }
                    }
                }

                CODE_COUNTER -> {
                    val counter = readBlock(mifare, BLOCK_COUNTER)
                    val typeMachine = readBlock(mifare, BLOCK_TYPE_MACHINE)
                    _counter.value = counter?.filter { it.isDigit() }
                    _typeMachine.value = typeMachine.toString()
                }
                // TODO
                // BLOCK_MAC
                // baca mac address kemudian arahkan ke halaman mapping
                else -> {
                    _textInfo.value = "Tidak bisa membaca kartu, silakan tab kembali"
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
            _textInfo.value = "Tidak ada kartu yang terdeteksi, silakan tab kembali"
        }
        closeCard(mifare)
    }

    /**
     * Connect Card
     * Fungsi ini menghubungkan NFC android ke kartu yang tempelkan ke handphone
     */
    private fun connectCard() {
        try {
            if (!mifare.isConnected) {
                mifare.connect()
                _disableButton.value = true
                _textInfo.value = "Kartu terdeteksi, silakan di proses"
            }
        } catch (e: IOException) {
            _openDialog.value = true
            _titleDialog.value = "Failed"
            _textInfo.value = "Tidak ada kartu terdeteksi"
            _disableButton.value = false
        }
    }

    fun getDetail() {
        viewModelScope.launch {
            _status.value = Status.LOADING
            val result = idMember.value?.let { repository.getDetailMember(idMember = it) }
            if (result?.code == 200) {
                _status.value = Status.SUCCESS
                val newResult =
                    GsonUtil.fromJson<Member>(Gson().toJson(result.results))
                _idCard.value = newResult.keyCard
                _username.value = newResult.name
                _phoneNumber.value = newResult.phone
            }
            println(result)
        }
    }

    fun updateMember() {
        _status.value = Status.LOADING
        viewModelScope.launch {
            val member = Member(
                outletId = outletId.value.toString(),
                name = username.value.toString(),
                phone = phoneNumber.value.toString(),
                keyCard = idCard.value.toString(),
                saldo = 0,
                memberId = idMember.value.toString()
            )
            val result = repositoryMember.updateMember(member)
            print(result)
            if (result.code == 201) {
                val newResult =
                    GsonUtil.fromJson<ResultMember>(Gson().toJson(result.results))
                repositoryMember.saveSession(newResult.accessToken)
            }
            if (result.code == 400) {
                println(result.description)
                // validasi gagal
                // todo info user
            }
            _status.value = Status.SUCCESS
        }
    }

    fun getAllMerchant() {
        viewModelScope.launch {
            val result = repositoryMerchant.getAllMerchant(1, 50, null, null, null)

            val resultt =
                GsonUtil.fromJson<ResultData<Merchant>>(Gson().toJson(result.results))
            _data.value = resultt.data
        }
    }

    /*
    * read from machine and upload to server
    * */
    fun uploadCounter() {
        val type = typeMachine.value
        val totalCount = totalCounter.value
        val price = priceCounter.value
        val outletId = outletId.value
        val mac = macAddress.value

    }

}