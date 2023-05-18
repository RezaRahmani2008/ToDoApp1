package adaptor

import model.ToDo
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.correntToDosBinding
import com.example.todoapp.dataStore
import kotlinx.collections.immutable.mutate
import kotlinx.coroutines.runBlocking

class ToDoAdaptor(var todoList: MutableList<ToDo>, var context: Context):
    RecyclerView.Adapter<ToDoAdaptor.ViewHolder>() {

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        var card = itemView.findViewById<CardView>(R.id.card)
        var titleTB = itemView.findViewById<TextView>(R.id.titleBox)
        var descriptionTB = itemView.findViewById<TextView>(R.id.descriptinBox)
        var dateTB = itemView.findViewById<TextView>(R.id.dateBox)
        var timeTB = itemView.findViewById<TextView>(R.id.timeBox)
        var isDoneHB = itemView.findViewById<CheckBox>(R.id.checkBox)
        init {
            isDoneHB.setOnCheckedChangeListener { button, isSelected ->
                if (isSelected){
                   runBlocking {
                       context.dataStore.updateData {
                           it.copy(
                               it.todoList.mutate {
                                   it.removeAt(adapterPosition)
                               }
                           )
                       }
                   }
                    correntToDosBinding.recView.adapter!!.notifyDataSetChanged()
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, p1viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_in_rec_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            titleTB.text = todoList[position].title
            descriptionTB.text = todoList[position].description
            dateTB.text = todoList[position].date
            timeTB.text = todoList[position].time
            isDoneHB.isChecked = todoList[position].isDone
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }
}