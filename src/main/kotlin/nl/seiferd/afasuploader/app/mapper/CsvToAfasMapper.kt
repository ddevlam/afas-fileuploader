package nl.seiferd.afasuploader.app.mapper

import nl.seiferd.afasuploader.model.AfasFile

class CsvToAfasMapper(val toIgnore: List<String>) {

    fun fromLines(string: List<String>): List<AfasFile> {
        return string.map(this::fromLine)
    }

    fun fromLine(string: String): AfasFile {
        val split = string.split(";")
        var index = 0

        val stId = split[index++]

        return AfasFile(
                stId.toInt(),
                split[index++],
                split[index++],
                split[index++],
                property(stId, split[index++]),
                split[index++],
                split[index]
        )
    }

    fun property(stId: String, possible: String) :Int? {
        return if (toIgnore.contains(stId)) null else possible.toInt()
    }

}