package nl.seiferd.afasuploader.app.mapper

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull

object CsvToAfasMapperTest: Spek ({

    given("The CSV to AFAS Mapper") {
        val mapper  = CsvToAfasMapper(emptyList())

        describe("Reading multiple lines") {
            val theLines = listOf(
                    "15;Omschrijving;1-1-2019;admin;260;Bestandsnaam;1437",
                    "16;Omschrijving;2-1-2019;admin;260;Bestandsnaam 2;1437"
            )

            val result = mapper.fromLines(theLines)
            it("Should have created 2 items") {
                assertEquals(2, result.size)
            }
        }

        describe("Reading a line") {
            val theLine = "15;Omschrijving;1-1-2019;admin;260;Bestandsnaam;1437"
            val result = mapper.fromLine(theLine)

            it("Should match the stId") {
                    assertEquals(15, result.stId)
            }

            it("Should match the ds") {
                    assertEquals("Omschrijving", result.ds)
            }

            it("Should match the da") {
                    assertEquals("1-1-2019", result.da)
            }

            it("Should match the usId") {
                    assertEquals("admin", result.usId)
            }

            it("Should match the fvF1") {
                    assertEquals(260, result.fvF1)
            }

            it("Should match the fileName") {
                    assertEquals("Bestandsnaam", result.fileName)
            }

            it("Should match the sfId") {
                    assertEquals("1437", result.sfId)
            }
        }
    }

    given("The CSV to AFAS Mapper with ignorable") {
        val mapper  = CsvToAfasMapper(listOf("-1","-2"))

        describe("Reading a line") {
            val theLine = "-1;Omschrijving;1-1-2019;admin;260;Bestandsnaam;1437"
            val result = mapper.fromLine(theLine)

            it("Should match the stId") {
                    assertEquals(-1, result.stId)
            }

            it("Should match the ds") {
                    assertEquals("Omschrijving", result.ds)
            }

            it("Should match the da") {
                    assertEquals("1-1-2019", result.da)
            }

            it("Should match the usId") {
                    assertEquals("admin", result.usId)
            }

            it("Should be null") {
                    assertNull(result.fvF1)
            }

            it("Should match the fileName") {
                    assertEquals("Bestandsnaam", result.fileName)
            }

            it("Should match the sfId") {
                    assertEquals("1437", result.sfId)
            }
        }
    }



})