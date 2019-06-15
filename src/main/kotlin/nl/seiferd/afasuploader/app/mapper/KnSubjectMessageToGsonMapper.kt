package nl.seiferd.afasuploader.app.mapper

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import nl.seiferd.afasuploader.model.KnSubjectMessage

class KnSubjectMessageToGsonMapper {

    fun toGson(message: KnSubjectMessage): String =
            GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                    .disableHtmlEscaping()
                    .create()
                    .toJson(message)

}