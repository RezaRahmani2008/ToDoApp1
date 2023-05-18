package db

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.serialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import model.ToDo

class TodoListSerializer2(val dataKSerializer: KSerializer<ToDo>): KSerializer<PersistentList<ToDo>> {
    class PersistentListDescriptor: SerialDescriptor by serialDescriptor<List<ToDo>>()
    override val descriptor = PersistentListDescriptor()


    override fun deserialize(decoder: Decoder): PersistentList<ToDo> {
        return ListSerializer(dataKSerializer).deserialize(decoder).toPersistentList()

    }


    override fun serialize(encoder: Encoder, value: PersistentList<ToDo>) {
        return ListSerializer(dataKSerializer).serialize(encoder, value.toList())
    }
}