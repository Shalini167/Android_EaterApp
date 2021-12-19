package com.example.eater

import android.app.Application
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.lang.reflect.Type


class LoginApplication: Application() {
        lateinit var loginService: ApiInterface
        lateinit var registerService:RegisterApiInterface
        lateinit var dishService:DishApiService
        lateinit var changeemailService:ChangeEmailApiService
        lateinit var accountdeleteService:DeleteApiService
        lateinit var  loginhistoryService:LoginHistoryApiService
        lateinit var otherusersService:OtherUsersApiService
        lateinit var createorderService:CreateOrderApiService
        lateinit var deleteorderService:DeleteorderApiService
        lateinit var myorderService:MyorderApiService
        override fun onCreate() {
            super.onCreate()
            loginService=loginApi()
            registerService=initHttpregisterApiService()
            dishService=initHttpApiService()
            changeemailService=initHttpemailApiService()
            accountdeleteService=initHttpdeleteApiService()
            loginhistoryService=LoginHistoryHttpApiService()
            otherusersService=OtherusersHttpApiService()
            createorderService=CreateOrderHttpApiService()
            myorderService=MyOrderHttpApiService()
            deleteorderService=DeleteOrderHttpApiService()
        }
        fun loginApi(): ApiInterface
        {
            val retrofit= Retrofit.Builder()
                .baseUrl("https://android-kanini-course.cloud/eaterapp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiInterface::class.java)
        }

    fun initHttpdeleteApiService(): DeleteApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/eaterapp/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(DeleteApiService::class.java)
    }


    fun initHttpemailApiService(): ChangeEmailApiService
    {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ChangeEmailApiService::class.java)
    }
    fun initHttpApiService():DishApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/eaterapp/")
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()))
            .build()
        return retrofit.create(DishApiService::class.java)
    }
    private fun OtherusersHttpApiService():OtherUsersApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(OtherUsersApiService::class.java)
    }
    private fun CreateOrderHttpApiService():CreateOrderApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(CreateOrderApiService::class.java)
    }
    private fun MyOrderHttpApiService():MyorderApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(MyorderApiService::class.java)
    }
    private fun DeleteOrderHttpApiService():DeleteorderApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(DeleteorderApiService::class.java)
    }
    private fun LoginHistoryHttpApiService():LoginHistoryApiService{

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/")
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
        return retrofit.create(LoginHistoryApiService::class.java)
    }
    fun initHttpregisterApiService(): RegisterApiInterface {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/eaterapp/")
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RegisterApiInterface::class.java)
    }
    class NullOnEmptyConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type?,
            annotations: Array<Annotation>?,
            retrofit: Retrofit?
        ): Converter<ResponseBody, *>? {
            val delegate = retrofit!!.nextResponseBodyConverter<Any>(this, type!!, annotations!!)
            return Converter<ResponseBody, Any> {
                if (it.contentLength() == 0L) return@Converter
                delegate.convert(it)
            }
        }
    }
    }
