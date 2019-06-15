package nl.seiferd.afasuploader.app.mapper

import nl.seiferd.afasuploader.model.AfasFile
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.*

object AfasFileToKnSubjectMapperTest : Spek({
    given("The CSV to AFAS file to subject Mapper") {
        val mapper  = AfasFileToKnSubjectMapper()

        describe("Mapping a file to subject") {
            val theFile = AfasFile(
                15,
                    "Bestand dat geupload gaat worden",
                    "2019-01-01T00:00:00",
                    "admin",
                    260,
                    "example_csv.csv",
                    "1437"
            )

            val result = mapper.mapToKnSubject(theFile)

            it("Should match the stId") {
                assertEquals(15, result.element.fields.stId)
            }

            it("Should match the ds") {
                assertEquals("Bestand dat geupload gaat worden", result.element.fields.ds)
            }

            it("Should match the da") {
                assertEquals("2019-01-01T00:00:00", result.element.fields.da)
            }

            it("Should match the usId") {
                assertEquals("admin", result.element.fields.usId)
            }

            it("Should match the fvF1") {
                assertEquals(260, result.element.fields.fvF1)
            }

            it("Should match the fileName") {
                val element = result.element.objects[0].knSubjectAttachment.element
                assertEquals("example_csv.csv",  element.fields.fileName)
            }

            it("Should match the file stream") {
                val element = result.element.objects[0].knSubjectAttachment.element
                assertNotNull(element.fields.fileStream)
            }

            it("Should match the sfTp") {
                val element = result.element.objects[0].knSubjectLink.element
                assertEquals(2,  element.fields.sfTp)
            }

            it("Should match the sfId") {
                val element = result.element.objects[0].knSubjectLink.element
                assertEquals("1437",  element.fields.sfId)
            }
        }
    }

})