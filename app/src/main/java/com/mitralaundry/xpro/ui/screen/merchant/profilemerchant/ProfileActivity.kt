package com.mitralaundry.xpro.ui.screen.merchant.profilemerchant

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View.GONE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.ActivityProfileBinding
import com.mitralaundry.xpro.databinding.ReportMachineActivityBinding
import com.mitralaundry.xpro.helper.CustomLoading
import com.mitralaundry.xpro.ui.screen.admin.profileadmin.ProfileAdminViewModel
import com.mitralaundry.xpro.ui.screen.fragment.SuccesDialogFragment
import com.mitralaundry.xpro.ui.screen.fragment.SuccesSaveDialogFragment
import com.spe.spectrum.helper.getExtraString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileMerchantViewModel by viewModels()
    private val viewModel2: ProfileAdminViewModel by viewModels()
    private var succesDialogFragment = SuccesSaveDialogFragment()


    var role = ""
    var userid = 0
    var userIdAdmin = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        role = getExtraString("role")
        binding.toolbar.ivAdd.visibility = GONE
        binding.toolbar.btnBack.setOnClickListener {
            onBackPressed()
        }
        binding.toolbar.tvToolbar.text = "Edit Profile"

        if (role == "merchant" || role == "kasir") {
            viewModel.getDataProfileMerchant()
        } else {
            viewModel2.getDataProfileAdmin()
        }

        initDataResponse()
        initListener()
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initListener() {

        binding.btnEdit.setOnClickListener() {
            if (binding.etName.text.toString().isEmpty() || binding.etEmail.text.toString()
                    .isEmpty() || binding.etTelepon.text.toString()
                    .isEmpty() || binding.etConfirmPassword.text.toString().isEmpty() ||
                binding.etOldPassword.text.toString()
                    .isEmpty() || binding.etNewPassword.text.toString().isEmpty()
            ) {
                toast("Data tidak boleh kosong")
            } else if (binding.etNewPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
                toast("Password baru dan password lama tidak sama")

            } else if (binding.etOldPassword.text.toString() == binding.etNewPassword.text.toString()) {
                toast("Password baru tidak boleh sama dengan password lama")
            } else if (binding.etNewPassword.text.toString().length < 6) {
                toast("Password baru minimal 6 karakter")
            } else if (binding.etOldPassword.text.toString().length < 6) {
                toast("Password lama minimal 6 karakter")
            } else if (binding.etConfirmPassword.text.toString().length < 6) {
                toast("Password konfirmasi minimal 6 karakter")
            } else if (binding.etTelepon.text.toString().length < 13) {
                toast("Nomor telepon minimal 12 karakter")
            } else if (!binding.etEmail.text.toString()
                    .contains("@") || !binding.etEmail.text.toString().contains(".")
            ) {
                toast("Email tidak valid")
            } else {
                if (role == "merchant" || role == "kasir") {
                    viewModel.updateProfileMerchant(
                        userid,
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etTelepon.text.toString(),
                        binding.etOldPassword.text.toString(),
                        binding.etNewPassword.text.toString(),

                        binding.etConfirmPassword.text.toString()
                    )
                } else {
                    viewModel2.updateProfileAdmin(
                        userIdAdmin,
                        binding.etName.text.toString(),
                        binding.etEmail.text.toString(),
                        binding.etTelepon.text.toString(),
                        binding.etOldPassword.text.toString(),
                        binding.etNewPassword.text.toString(),

                        binding.etConfirmPassword.text.toString()
                    )
                }

            }
        }
    }

    private fun initDataResponse() {

        if (role == "merchant" || role == "kasir") {

            viewModel.dataProfile.observe(this) {
                userid = it!!.userId!!
                if (it != null) {
                    binding.etName.setText(it.name)
                    binding.etEmail.setText(it.email)
                    binding.etTelepon.setText(it.phone)
                }
            }

            viewModel.statusProfile.observe(this) {
                if (it == Status.LOADING) {
                    CustomLoading.showLoading(this)

                } else {
                    CustomLoading.hideLoading()

                }
            }

            viewModel.statusUpdateMerchant.observe(this) {
                if (it == Status.LOADING) {
                    CustomLoading.showLoading(this)

                } else if (it == Status.SUCCESS) {
                    succesDialogFragment.show(supportFragmentManager, "")
                    CustomLoading.hideLoading()

                } else {
                    toast("Password lama anda salah")
                    CustomLoading.hideLoading()
                }
            }

        } else {
            viewModel2.dataProfile.observe(this) {
                userIdAdmin = it!!.userId!!

                if (it != null) {
                    binding.etName.setText(it.name)
                    binding.etEmail.setText(it.email)
                    binding.etTelepon.setText(it.phone)
                }
            }

            viewModel2.statusProfile.observe(this) {
                if (it == Status.LOADING) {
                    CustomLoading.showLoading(this)

                } else {
                    CustomLoading.hideLoading()

                }
            }

            viewModel2.statusUpdateAdmin.observe(this) {
                if (it == Status.LOADING) {
                    CustomLoading.showLoading(this)

                } else if (it == Status.SUCCESS) {
                    succesDialogFragment.show(supportFragmentManager, "")
                    CustomLoading.hideLoading()

                } else {
                    toast("Password lama anda salah")
                    CustomLoading.hideLoading()


                }
            }
        }
    }
}
