package nl.seiferd.afasuploader.app

class CsvFileReader(val location: String) {

    fun readFile() : String {
        return CsvFileReader::class.java.getResource(location).readText()
    }

}