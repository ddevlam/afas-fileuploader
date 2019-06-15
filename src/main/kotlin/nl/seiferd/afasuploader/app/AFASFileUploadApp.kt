package nl.seiferd.afasuploader.app

import nl.seiferd.afasuploader.app.mapper.AfasFileToKnSubjectMapper
import nl.seiferd.afasuploader.app.mapper.CsvToAfasMapper
import nl.seiferd.afasuploader.app.mapper.KnSubjectMessageToGsonMapper
import nl.seiferd.afasuploader.model.AfasFile
import nl.seiferd.afasuploader.model.KnSubjectMessage
import reactor.core.publisher.Flux
import java.nio.file.Paths
import java.time.Duration

fun main(args: Array<String>) {
    fun loadCsv(file: String): List<AfasFile> {
        return CsvToAfasMapper().fromLines(
                CsvFileReader(file).readFile()
                        .drop(1)
        )
    }

    val gsonMapper = KnSubjectMessageToGsonMapper()
    val config = ConfigExtractor().extractConfig(Paths.get(args[0]))
    val afasConnector = AfasConnector(config.abboId, config.tokenBase64)

    val loadedCsv = loadCsv(config.inputFile)
    println("Going to parse ${loadedCsv.size} lines")

    Flux.fromIterable(loadedCsv)
            .delayElements(Duration.ofSeconds(10))
            .map(AfasFileToKnSubjectMapper()::mapToKnSubject)
            .map(::KnSubjectMessage)
            .map(gsonMapper::toGson)
            .flatMap(afasConnector::sendToAfas)
            .doOnNext({res -> println("I am done and the result is ${res?.statusCode()} ") })
            .blockLast(Duration.ofMinutes(5))


}