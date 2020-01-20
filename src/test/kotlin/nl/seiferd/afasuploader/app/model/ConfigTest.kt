package nl.seiferd.afasuploader.app.model

import com.google.gson.Gson
import nl.seiferd.afasuploader.model.Config
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertIterableEquals

object ConfigTest: Spek({

    given("a String") {

        describe("Config can read from file") {
            val config = Gson().fromJson("{ignorable : [\"-1\", \"-2\"]}", Config::class.java)

            it("Should match the collection") {

                assertIterableEquals(listOf("-1", "-2"), config.ignorable)
            }
        }
    }
})