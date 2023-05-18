package db

import androidx.datastore.core.Serializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

class TodoListSerializer() : Serializer<ToDoListHolder> {
    override val defaultValue = ToDoListHolder()

    override suspend fun readFrom(input: InputStream): ToDoListHolder {
        return Json.decodeFromString(
            ToDoListHolder.serializer(),
            input.readBytes().decodeToString()
        )
    }

    override suspend fun writeTo(holder: ToDoListHolder, output: OutputStream) {
        output.write(
            Json.encodeToString(
                ToDoListHolder.serializer(), holder
            ).encodeToByteArray()
        )
    }
}
