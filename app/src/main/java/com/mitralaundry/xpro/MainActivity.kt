package com.mitralaundry.xpro

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mitralaundry.xpro.ui.screen.admin.devices.DeviceAddScreen
import com.mitralaundry.xpro.ui.screen.admin.devices.DeviceScreen
import com.mitralaundry.xpro.ui.screen.admin.devices.ViewModelDevice
import com.mitralaundry.xpro.ui.screen.admin.merchant.add.MerchantAddScreen
import com.mitralaundry.xpro.ui.screen.admin.merchant.detail.MerchantDetail
import com.mitralaundry.xpro.ui.screen.admin.merchant.MerchantScreen
import com.mitralaundry.xpro.ui.screen.admin.merchant.ViewModelMerchant
import com.mitralaundry.xpro.ui.screen.admin.merchant.add.ViewModelAddMerchant
import com.mitralaundry.xpro.ui.screen.admin.merchant.detail.ViewModelMerchantDetail
import com.mitralaundry.xpro.ui.screen.admin.merchant.outlet.OutletScreen
import com.mitralaundry.xpro.ui.screen.admin.merchant.outlet.ViewModelOutlet
import com.mitralaundry.xpro.ui.screen.home.HomeScreen
import com.mitralaundry.xpro.ui.screen.home.ProfileScreen
import com.mitralaundry.xpro.ui.screen.home.ViewModelHome
import com.mitralaundry.xpro.ui.screen.login.LoginScreen
import com.mitralaundry.xpro.ui.screen.login.LoginViewModel
import com.mitralaundry.xpro.ui.screen.merchant.laporanmesin.LaporanMesinCompose
import com.mitralaundry.xpro.ui.screen.merchant.laporanmesin.ViewModelReportMesin
import com.mitralaundry.xpro.ui.screen.merchant.laporantopup.LaporanTopupCompose
import com.mitralaundry.xpro.ui.screen.merchant.member.MemberScreen
import com.mitralaundry.xpro.ui.screen.merchant.member.ViewModelMember
import com.mitralaundry.xpro.ui.screen.merchant.member.add.SetupNewCard
import com.mitralaundry.xpro.ui.screen.merchant.member.add.ViewModelCardSetup
import com.mitralaundry.xpro.ui.screen.merchant.reportnitip.ReportNitipCompose
import com.mitralaundry.xpro.ui.screen.merchant.reportnitip.ViewModelReportNitip
import com.mitralaundry.xpro.ui.screen.profile.ProfileCompose
import com.mitralaundry.xpro.ui.screen.splashscreen.SplashScreenCompose
import com.mitralaundry.xpro.ui.screen.splashscreen.ViewModelSplash
import com.mitralaundry.xpro.ui.theme.LaundryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaundryTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        val navController = rememberNavController()
                        Navigation(navController = navController, this)
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Navigation(navController: NavHostController, thisActivity: Context) {
    var mappingDevice = ""
    NavHost(navController = navController, startDestination = "splash") {
        composable("member/{outletId}/{outletName}") {
            val viewModelMember = hiltViewModel<ViewModelMember>()
            MemberScreen(
                navHostController = navController,
                viewModel = viewModelMember,
                it.arguments?.getString("outletId"),
                it.arguments?.getString("outletName")
            )
        }
        composable("home") {
            val viewModel = hiltViewModel<ViewModelHome>();
            HomeScreen(navController = navController, viewModelHome = viewModel)
        }
        composable("merchant") {
            val viewModel = hiltViewModel<ViewModelMerchant>()
            MerchantScreen(navController, viewModel = viewModel, "merchant")
        }
        composable("merchant-detail/{merchantId}") {
            val viewModel = hiltViewModel<ViewModelMerchantDetail>()
            MerchantDetail(navController, viewModel, it.arguments?.getString("merchantId"), "")
        }
        composable("mapping-detail/{merchantId}") {
            val viewModel = hiltViewModel<ViewModelMerchantDetail>()
            MerchantDetail(navController, viewModel, it.arguments?.getString("merchantId"), "map")
        }
        composable("profile") {
            ProfileScreen(navController)
        }
        composable("merchant-add") {
            val viewModel = hiltViewModel<ViewModelAddMerchant>()
            MerchantAddScreen(navController, viewModel = viewModel)
        }
        composable("outlet-update/{outletId}") {
            val viewModel = hiltViewModel<ViewModelOutlet>()
            OutletScreen(
                navController,
                viewModel,
                it.arguments?.getString("merchantId"),
                it.arguments?.getString("outletId")
            )
        }
        composable("outlet-add/{merchantId}") {
            val viewModel = hiltViewModel<ViewModelOutlet>()
            OutletScreen(
                navController,
                viewModel,
                it.arguments?.getString("merchantId"),
                it.arguments?.getString("outletId")
            )
        }
//        composable("mapping-list/{outletId}") {
//            val intent = Intent(thisActivity, MappingActivity::class.java)
//            intent.putExtra("outletId", it.arguments?.getString("outletId"))
//            intent.putExtra("outletName",    it.arguments?.getString("outletName"))
//            thisActivity.startActivity(intent)
//        }
        composable("device") {
            val viewModelDevice = hiltViewModel<ViewModelDevice>()
            DeviceScreen(navController, viewModel = viewModelDevice)
        }
        composable("device-update/{deviceId}") {
            val viewModelDevice = hiltViewModel<ViewModelDevice>()
            DeviceAddScreen(
                navController,
                viewModel = viewModelDevice,
                it.arguments?.getString("deviceId")
            )
        }
        composable("device-add") {
            val viewModelDevice = hiltViewModel<ViewModelDevice>()
            DeviceAddScreen(navController, viewModel = viewModelDevice, null)
        }
        composable("login") {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            LoginScreen(
                onSuccessLogin = {
                    navController.navigate("home") {
                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },
                viewModel = loginViewModel
            )
        }
        composable("new-card/{outletId}/{merchantId}") {
            val viewModel = hiltViewModel<ViewModelCardSetup>()
            SetupNewCard(
                navController,
                viewModel,
                it.arguments?.getString("outletId"),
                it.arguments?.getString("merchantId")
            )
        }
        composable("splash") {
            val viewModel = hiltViewModel<ViewModelSplash>()
            SplashScreenCompose(viewModel = viewModel,
                onLogin = {
                    navController.navigate("home") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                },
                needLogin = {
                    navController.navigate("login") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable("mapping") {
            val viewModel = hiltViewModel<ViewModelMerchant>()
            MerchantScreen(navController, viewModel = viewModel, "map")
        }
        composable("profile") {
            ProfileCompose(navHost = navController)
        }
        composable("report-nitip/{outletId}/{outletName}") {
            val viewModel = hiltViewModel<ViewModelReportNitip>()
            ReportNitipCompose(
                navHostController = navController,
                viewModel,
                it.arguments?.getString("outletId")!!,
                it.arguments?.getString("outletName")!!
            )
        }
        composable("laporan-topup/{outletId}/{outletName}") {
            val viewModel = hiltViewModel<ViewModelReportNitip>()
            LaporanTopupCompose(
                onClickBackArrow = {
                    navController.popBackStack()
                },
                it.arguments?.getString("outletId")!!,
                it.arguments?.getString("outletName")!!,
                viewModel
            )
        }
        composable("laporan-mesin/{outletId}/{outletName}") {
            val viewModel = hiltViewModel<ViewModelReportMesin>()
            LaporanMesinCompose(
                onClickBackArrow = {
                    navController.popBackStack()
                },
                it.arguments?.getString("outletId")!!,
                it.arguments?.getString("outletName")!!,
                viewModel
            )
        }
    }
}