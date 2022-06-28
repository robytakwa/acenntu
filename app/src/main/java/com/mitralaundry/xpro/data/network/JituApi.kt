package com.mitralaundry.xpro.data.network

import com.mitralaundry.xpro.data.model.*
import com.mitralaundry.xpro.data.model.response.*
import retrofit2.http.*

interface JituApi {

    @GET("users")
    suspend fun getUser(): List <UserResponse>


    @POST("login")
    suspend fun login(@Body user: User): Response<Login>

    @POST("change-password")
    suspend fun resetPassword(@Body resetPasswordRequest: ResetPasswordRequest): Response<Login>

    @GET("admin/merchant")
    suspend fun <T> merchant(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("perpage") perPage: Int = 10,
        @Query("sort_by") sortBy: String?,
        @Query("order") order: String? = "ASC",
        @Query("name") name: String?
    ): BaseResponse<T>

    @POST("admin/merchant/detail")
    suspend fun <T> merchantDetail(
        @Header("Authorization") token: String,
        @Query("merchant_id") merchantId: String
    ): BaseResponse<T>

    @POST("admin/merchant/onboarding")
    suspend fun <T> addMerchant(
        @Header("Authorization") token: String,
        @Query("user_name") userName: String,
        @Query("user_email") userEmail: String,
        @Query("user_phone") userPhone: String,
        @Query("merchant_name") merchantName: String,
        @Query("merchant_phone") merchantPhone: String,
        @Query("merchant_address") merchantAddress: String,
        @Query("merchant_description") merchantDescription: String,
        @Query("outlet_name") outletName: String,
        @Query("outlet_phone") outletPhone: String,
        @Query("outlet_address") outletAddress: String,
        @Query("outlet_description") outletDescription: String,
    ): BaseResponse<T>

    @POST("admin/outlet/create")
    suspend fun <T> addOutlet(
        @Header("Authorization") token: String,
        @Query("merchant_id") merchantId: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("address") address: String,
        @Query("description") description: String,
    ): BaseResponse<ResultOutlet>

    @POST("admin/outlet/detail")
    suspend fun <T> getDetailOutlet(
        @Header("Authorization") token: String,
        @Query("outlet_id") outletId: String
    ): BaseResponse<ResultOutlet>

    @POST("admin/outlet/update")
    suspend fun <T> updateOutlet(
        @Header("Authorization") token: String,
        @Query("outlet_id") outletId: String,
        @Query("name") name: String,
        @Query("phone") phone: String,
        @Query("address") address: String,
        @Query("description") description: String,
    ): BaseResponse<T>

    @GET("admin/devices")
    suspend fun <T> getListDevices(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("perpage") perPage: Int = 10,
        @Query("sort_by") sortBy: String?,
        @Query("order") order: String? = "ASC",
    ): BaseResponse<T>
    @POST("admin/devices/create")
    suspend fun <T> addDevice(
        @Header("Authorization") token: String,
        @Body createDeviceRequest: CreateDeviceRequest
    ): BaseResponse<T>

    @POST("admin/devices/update")
    suspend fun <T> updateDevice(
        @Header("Authorization") token: String,
        @Body createDeviceRequest: CreateDeviceRequest
    ): BaseResponse<T>

    @POST("admin/devices/detail")
    suspend fun <T> detailDevice(
        @Header("Authorization") token: String,
        @Query("device id") deviceId: String
    ): BaseResponse<T>

    @GET("admin/mapping")
    suspend fun getListMapping(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("perpage") perPage: Int = 20,
        @Query("sort_by") sortBy: String,
        @Query("outlet_id") outletId: String,
    ): ResponseMapping



    @GET("admin/mapping/get-device")
    suspend fun getListDevicesMapping(
        @Header("Authorization") token: String,
    ): ResponseDeviceMapping


    @POST("admin/mapping/update")
    suspend fun <T> updateMapping(
        @Header("Authorization") token: String,
        @Body updateMappingRequest: UpdateMappingRequest
    ): BaseResponse<T>

    @POST("admin/mapping/create")
    suspend fun <T> createMapping(
        @Header("Authorization") token: String,
        @Body createMappingRequest: CreateMappingRequest
    ): BaseResponse<T>


    @POST("merchant")
    suspend fun <T> getListOutlet(
        @Header("Authorization") token: String
    ): BaseResponse<T>

    @GET("merchant/outlet/member")
    suspend fun <T> getListMember(
        @Header("Authorization") token: String,
        @Query("outlet_id") outletId: String,
        @Query("perpage") perPage: Int = 50,
    ): BaseResponse<T>

    @GET("merchant/accounts/list")
    suspend fun <T> getListUser(
        @Header("Authorization") token: String,
    ): BaseResponse<T>

    @GET("merchant/accounts/list")
    suspend fun <T> getListUserSearch(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): BaseResponse<T>

    @GET("admin/accounts")
    suspend fun <T> getListAccountAdmin(
        @Header("Authorization") token: String,
        @Query("perpage") perPage: Int = 2,

    ): BaseResponse<T>

    @GET("admin/accounts")
    suspend fun <T> getListAccountAdminSearch(
        @Header("Authorization") token: String,
        @Query("name") name: String,
    ): BaseResponse<T>

    @POST("merchant/outlet/member/create")
    suspend fun <T> addMember(
        @Header("Authorization") token: String,
        @Body member: Member
    ): BaseResponse<T>

    @POST("merchant/accounts/create")
    suspend fun <T> addUser(
        @Header("Authorization") token: String,
        @Body userAccountRequest: UserAccountRequest
    ): BaseResponse<T>

    @POST("admin/accounts/create")
    suspend fun <T> createAccountAdmin(
        @Header("Authorization") token: String,
        @Body accountAdminRequest: AccountAdminRequest
    ): BaseResponse<T>

    @POST("merchant/prices/update")
    suspend fun <T> updatePrice(
        @Header("Authorization") token: String,
        @Body updatePriceRequest: UpdatePriceRequest
    ): BaseResponse<T>

    @POST("admin/pricing/update")
    suspend fun <T> updatePriceAdmin(
        @Header("Authorization") token: String,
        @Body updatePriceRequest: UpdatePriceRequest
    ): BaseResponse<T>

    @POST("merchant/accounts/reset")
    suspend fun <T> resetUser(
        @Header("Authorization") token: String,
        @Body resetUserRequest: ResetUserRequest
    ): BaseResponse<T>

    @POST("admin/accounts/reset")
    suspend fun <T> resetUserAdmin(
        @Header("Authorization") token: String,
        @Body resetUserAdminRequest: ResetUserAdminRequest
    ): BaseResponse<T>

    @GET("merchant/outlet/member/detail")
    suspend fun <T> getDetailMember(
        @Header("Authorization") token: String,
        @Query("member_id") memberId: String
    ): BaseResponse<T>

    @GET("merchant/prices")
    suspend fun <T> getPriceList(
        @Header("Authorization") token: String,
        @Query("outlet_id") outletId: String
    ): BaseResponse<T>

    @GET("admin/pricing")
    suspend fun <T> getPriceAdminSearch(
        @Header("Authorization") token: String,
        @Query("name") name: String
    ): BaseResponse<T>

    @GET("admin/pricing")
    suspend fun <T> getPriceAdmin(
        @Header("Authorization") token: String,
    ): BaseResponse<T>

    @GET("merchant/user/profile")
    suspend fun <T> getProfileMerchant(
        @Header("Authorization") token: String,
    ): BaseResponse<T>

    @GET("admin/user/profile")
    suspend fun <T> getProfileAdmin(
        @Header("Authorization") token: String,
    ): BaseResponse<T>


    @POST("merchant/user/profile/update")
    suspend fun <T> updateProfileMerchant(
        @Header("Authorization") token: String,
        @Body updateProfileMerchantRequest: UpdateProfileRequest
    ): BaseResponse<T>


    @POST("admin/user/profile/update")
    suspend fun <T> updateProfileAdmin(
        @Header("Authorization") token: String,
        @Body updateProfileAdminRequest: UpdateProfileRequest
    ): BaseResponse<T>

    @POST("admin/pricing/detail")
    suspend fun <T> getDetailPriceAdmin(
        @Header("Authorization") token: String,
        @Body detailAdminRequest: DetailAdminRequest
    ): BaseResponse<T>

    @POST("merchant/outlet/member/update")
    suspend fun <T> updateMember(
        @Header("Authorization") token: String,
        @Body member: Member
    ): BaseResponse<T>

    @GET("merchant/titip-laundry")
    suspend fun <T> postTitipLaundry(
        @Header("Authorization") token: String,
        @Query("member_id") memberId: String,
        @Query("curr_saldo") currentSaldo: String,
        @Query("price") price: String
    ): BaseResponse<T>

    @GET("merchant/laporan-topup")
    suspend fun <T> getReportLaporanTopup(
        @Header("Authorization") token: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("outlet_id") outletId: String,
    ): BaseResponse<T>

    @GET("merchant/laporan-mesin")
    suspend fun <T> getReportLaporanMesinFilter(
        @Header("Authorization") token: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("outlet_id") outletId: String,
    ): BaseResponse<T>


    @POST("merchant/upload_counter")
    suspend fun <T> uploadLaporanMesin(
        @Header("Authorization") token: String,
        @Body uploadLaporanMesinRequest: UploadLaporanMesinRequest
    ): BaseResponse<T>
}