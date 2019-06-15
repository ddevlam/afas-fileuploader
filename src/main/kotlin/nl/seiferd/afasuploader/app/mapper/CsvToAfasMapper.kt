package nl.seiferd.afasuploader.app.mapper

import nl.seiferd.afasuploader.model.AfasFile

class CsvToAfasMapper {

    fun fromLines(string: List<String>): List<AfasFile> {
        return string.map(this::fromLine)
    }

    fun fromLine(string: String): AfasFile {
        val split = string.split(";")
        var index = 0

        return AfasFile(
                split[index++].toInt(),
                split[index++],
                split[index++],
                split[index++],
                split[index++].toInt(),
                split[index++],
                split[index]
        )
    }

}