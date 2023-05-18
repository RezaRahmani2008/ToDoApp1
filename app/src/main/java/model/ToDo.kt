package model

import kotlinx.serialization.Serializable

@Serializable
data class ToDo(
    var title:String,
    var description:String,
    var time:String,
    var date:String,
    var isDone:Boolean)
