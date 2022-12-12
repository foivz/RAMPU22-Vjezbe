package hr.foi.rampu.memento.ws

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WsNewsService {
    @GET("news.php")
    fun getNews(): Call<WsNewsResponse>

    @GET("news.php")
    fun getNews(@Query("title") newsTitle: String): Call<WsNewsResult>
}
