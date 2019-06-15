package nl.seiferd.afasuploader.app

import nl.seiferd.afasuploader.app.mapper.AfasFileToKnSubjectMapper
import nl.seiferd.afasuploader.app.mapper.CsvToAfasMapper
import nl.seiferd.afasuploader.app.mapper.KnSubjectMessageToGsonMapper
import nl.seiferd.afasuploader.model.AfasFile
import nl.seiferd.afasuploader.model.KnSubjectMessage
import java.nio.file.Paths

fun main(args: Array<String>) {

    fun loadCsv(file: String): List<AfasFile> {
        return CsvToAfasMapper().fromLines(
                CsvFileReader(file).readFile()
                        .lines()
                        .drop(1)
        )
    }

    val gsonMapper = KnSubjectMessageToGsonMapper()
    val config = ConfigExtractor().extractConfig(Paths.get(args[0]))
    val afasConnector = AfasConnector(config.abboId, config.tokenBase64)

    loadCsv(config.inputFile)
            .map(AfasFileToKnSubjectMapper()::mapToKnSubject)
            .map(::KnSubjectMessage)
            .map(gsonMapper::toGson)
            .onEach(::println)
            .map(afasConnector::sendToAfas)
            .forEach({res -> println("I am done and the result is ${res?.rawStatusCode()}") })

}