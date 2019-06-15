package nl.seiferd.afasuploader.app

import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

class CsvFileReader(val location: String) {

    fun readFile() : List<String> {
        return Files.newBufferedReader(Paths.get(location)).lines().collect(Collectors.toList())
    }

}