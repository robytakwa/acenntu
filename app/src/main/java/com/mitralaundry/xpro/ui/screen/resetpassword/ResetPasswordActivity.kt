package com.mitralaundry.xpro.ui.screen.resetpassword

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.mitralaundry.xpro.R
import com.mitralaundry.xpro.base.BaseActivity
import com.mitralaundry.xpro.data.model.response.Status
import com.mitralaundry.xpro.databinding.ResetPasswordActivityBinding
import com.mitralaundry.xpro.databinding.UploadLaporanActivityBinding
import com.mitralaundry.xpro.ui.screen.login.LoginActivity
import com.spe.spectrum.helper.getExtraString

class ResetPasswordActivity : BaseActivity() {

    private var email = ""
    private val viewModel: ResetPasswordViewModel by viewModels()
    private lateinit var binding: ResetPasswordActivityBinding
    var status = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ResetPasswordActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        email = getExtraString("email")

        viewModel.status.observe(this) {
            if (it == Status.SUCCESS) {
                Toast.makeText(
                    this, "Ganti Password Berhasil, Silahkan Login dengan Password Baru!",
                    Toast.LENGTH_LONG
                ).show()
                val intent = Intent(this, LoginActivity::class.java)
                this.startActivity(intent)

            }
        }

        initClick()

    }

    private fun initClick() {
        binding.btnLogin.setOnClickListener {

            if (binding.etPassword.text.toString() != binding.etConfirmPassword.text.toString()) {
                Toast.makeText(this, "Password tidak sama!", Toast.LENGTH_LONG).show()
            } else if (binding.etPassword.text.toString().length < 8) {
                Toast.makeText(this, "Password minimal 8 karakter!", Toast.LENGTH_LONG).show()
            } else if (binding.etPassword.text.toString().length > 20) {
                Toast.makeText(this, "Password maksimal 20 karakter!", Toast.LENGTH_LONG).show()
            } else if (binding.etPassword.text.isEmpty() || binding.etConfirmPassword.text.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua data!", Toast.LENGTH_LONG).show()
            } else {
                viewModel.resetPass(
                    email = email,
                    password = binding.etPassword.text.toString(),
                    confirm = binding.etConfirmPassword.text.toString()
                )
            }


        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, LoginActivity::class.java)
        this.startActivity(intent)
    }
}