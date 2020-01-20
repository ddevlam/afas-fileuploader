package nl.seiferd.afasuploader.app

import nl.seiferd.afasuploader.app.mapper.AfasFileToKnSubjectMapper
import nl.seiferd.afasuploader.app.mapper.CsvToAfasMapper
import nl.seiferd.afasuploader.app.mapper.KnSubjectMessageToGsonMapper
import nl.seiferd.afasuploader.model.AfasFile
import nl.seiferd.afasuploader.model.KnSubjectMessage
import java.nio.file.Paths

fun main(args: Array<String>) {
    val config = ConfigExtractor().extractConfig(Paths.get(args[0]))

    fun loadCsv(file: String): List<AfasFile> {
        return CsvToAfasMapper(config.ignorable).fromLines(
                CsvFileReader(file).readFile()
                        .drop(1)
        )
    }

    val gsonMapper = KnSubjectMessageToGsonMapper()
    val afasConnector = AfasConnector(config.abboId, config.tokenBase64)

    val loadedCsv = loadCsv(config.inputFile)
    println("Going to parse ${loadedCsv.size} lines")

    loadedCsv
            .map(AfasFileToKnSubjectMapper()::mapToKnSubject)
            .map(::KnSubjectMessage)
            .map(gsonMapper::toGson)
            .mapIndexed{id, it -> afasConnector.sendToAfas(1 + id, it)} // Human readable lines nrs ...
            .onEach { println("I am done with batch item ${it.request.headers["batch"]} and the result is ${it.statusCode}") }

    Thread.sleep(5000)
}