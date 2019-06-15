package nl.seiferd.afasuploader.app

import nl.seiferd.afasuploader.model.Config
import java.nio.file.Path
import java.nio.file.Files
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import java.io.IOException


class ConfigExtractor {

    fun extractConfig(path: Path): Config {
        if (!Files.exists(path) || !isJsonFile(path)) {
            throw IllegalArgumentException("Path should be pointing to valid .json file")
        }

        return Gson().fromJson(Files.newBufferedReader(path), Config::class.java)
    }

    private fun isJsonFile(path: Path): Boolean {
        return path.toString().contains(".json")
    }
}