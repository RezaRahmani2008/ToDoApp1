package notifiction

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.getSystemService
import model.ToDo
import java.util.Calendar

@RequiresApi(Build.VERSION_CODES.O)
class Notifiction(var name: String,
                  var description: String,
                  var context: Context,
                  var id: String,
                  var todo: ToDo
                  ) {
    init {
        createChannel(name, description)
        startNotifiction()
    }
    private fun startNotifiction(){
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, (todo.time).split(":")[0].toInt())
        calender.set(Calendar.MINUTE, (todo.time).split(":")[1].toInt())
        calender.set(Calendar.SECOND, 0)
        calender.set(Calendar.MILLISECOND, 0)
        calender.set(Calendar.YEAR, (todo.date).split("/")[2].toInt())
        calender.set(Calendar.MONTH, (todo.date).split("/")[1].toInt()-1)
        calender.set(Calendar.DAY_OF_MONTH, (todo.date).split("/")[0].toInt())

        val notifIntent = Intent(context, NotifReceiver::class.java)
        notifIntent.putExtra("id", id)
        var alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calender.timeInMillis, PendingIntent.getBroadcast(
            context, id.toInt(), notifIntent, PendingIntent.FLAG_IMMUTABLE
        ))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(name: String, description: String){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id,name,importance)
        channel.description = description
        val notifManager = context.getSystemService(NotificationManager::class.java)
        notifManager.createNotificationChannel(channel)
    }
}