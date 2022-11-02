package hr.foi.rampu.memento.ws

import retrofit2.Call
import retrofit2.http.GET

interface WsNewsService {
    @GET("news.php")
    fun getNews(): Call<WsNewsResponse>
}
