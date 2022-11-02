package hr.foi.rampu.memento.ws

data class WsNewsResponse(
    var count: Int,
    var results: ArrayList<WsNewsResult>
)
