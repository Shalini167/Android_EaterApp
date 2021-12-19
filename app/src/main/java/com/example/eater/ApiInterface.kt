package com.example.eater

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun postData(@Body users:UserData): Call<LoginData>
}
interface RegisterApiInterface {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun postRegData(@Body reg:UserData): Call<LoginData>
}
interface DishApiService {

    @GET("dishes")
    suspend fun GetDishes(@Header("Authorization") token: String): Response<AllDishes>
}
interface ChangeEmailApiService{
    @Headers("Content-Type: application/json")

    @POST("/eaterapp/users/me/email")
    fun ChangeEmail(@Header("Authorization") token: String, @Body user:EmailUpdate ):Call<Void>

}
interface DeleteApiService{
    @DELETE("users/me")
    fun deleteUser(@Header("Authorization") token: String):Call<Void>
}
interface LoginHistoryApiService {
    @GET("/eaterapp/users/me/loginHistory")
    suspend fun logDetails(@Header("Authorization") token: String): Response<LoginhistoryListData>
}


interface OtherUsersApiService {
    @GET("/eaterapp/users")
    suspend fun otherUserDetails(@Header("Authorization") token: String): Response<OtherusersDataList>
}
interface CreateOrderApiService {
    @POST("/eaterapp/users/me/orders")
    suspend fun CreateOrders(
        @Header("Authorization") token: String,
        @Body request: CreateOrderData
    ): Response<Unit>
}
interface MyorderApiService{
    @GET("eaterapp/users/me/orders")
    suspend fun fetchOrders(@Header("Authorization") token: String):Response<MyorderList>
}
interface DeleteorderApiService {
    @DELETE("eaterapp/users/me/orders/{orderId}")
    fun DeleteOrders(@Header("Authorization") token: String,@Path("orderId")orderId:Int):Call<Void>
}
