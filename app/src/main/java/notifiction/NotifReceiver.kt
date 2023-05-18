package notifiction

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.example.todoapp.R
import com.example.todoapp.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class NotifReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val pendingIntent = NavDeepLinkBuilder(context!!).setGraph(R.navigation.navigation)
            .setDestination(R.id.correntToDos).createPendingIntent()
        val currentId = intent!!.extras!!.getString("id")
        val notifManager = context!!.getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager
        runBlocking {
            var courrentToDo = context.dataStore.data.first().todoList.find {
                it.hashCode() == currentId!!.toInt()
            }
            createNotification(
                context,
                currentId.toString(),
                courrentToDo!!.title,
                courrentToDo.description,
                notifManager,
                pendingIntent)
        }
    }
       private fun createNotification(
            context: Context?,
            id: String, title: String,
            description: String,
            notificationManager: NotificationManager,
            pendingIntent: PendingIntent) {

            val notification = NotificationCompat.Builder(context!!, id)
                .setSmallIcon(R.drawable.baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent).build()
            notificationManager.notify(id.toInt(), notification)
        }
    }