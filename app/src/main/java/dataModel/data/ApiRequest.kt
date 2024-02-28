package dataModel.data

import dataModel.model.DataListaFacturas
import retrofit2.Response
import retrofit2.http.GET

fun interface ApiRequest {
    @GET("facturas")
    suspend fun getFacturas(): Response<DataListaFacturas>
}