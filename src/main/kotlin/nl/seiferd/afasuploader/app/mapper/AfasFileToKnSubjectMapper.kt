package nl.seiferd.afasuploader.app.mapper

import nl.seiferd.afasuploader.model.AfasFile
import nl.seiferd.afasuploader.model.KnSubjectMessage
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject.Element.Objects
import java.nio.file.Files
import java.nio.file.Paths
import java.util.*
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject as KnSubect
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject.Element as KnSubjectElement
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject.Element.Fields as KnSubjectFields
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject.Element.Objects.KnSubjectAttachment.Element as AttachmentElement
import nl.seiferd.afasuploader.model.KnSubjectMessage.KnSubject.Element.Objects.KnSubjectLink.Element as LinkElement

class AfasFileToKnSubjectMapper {

    fun mapToKnSubject(afasFile: AfasFile): KnSubjectMessage.KnSubject {
        return KnSubjectMessage.KnSubject(
                KnSubjectElement(toKnSubjectFields(afasFile),
                        listOf(Objects(
                                Objects.KnSubjectLink(
                                        toLinkElement(afasFile)
                                ),
                                Objects.KnSubjectAttachment(
                                        toAttachElement(afasFile)
                                )
                        ))
                )
        )
    }

    private fun toAttachElement(afasFile: AfasFile) = AttachmentElement(
            AttachmentElement.Fields(
                    afasFile.fileName,
                    fileAsBase64(afasFile.fileName) ?: ""
            )
    )

    private fun fileAsBase64(fileName: String): String? {
        val pathToFile = Paths.get(fileName)
        if (Files.exists(pathToFile)) {
            val bytes = Files.newBufferedReader(pathToFile).readLines().joinToString().toByteArray()
            return String(Base64.getEncoder().encode(bytes))
        } else {
            return null
        }
    }

    private fun toLinkElement(afasFile: AfasFile) = LinkElement(
            LinkElement.Fields(sfId = afasFile.sfId)
    )

    private fun toKnSubjectFields(afasFile: AfasFile) = KnSubjectFields(
            afasFile.stId, afasFile.ds, afasFile.da, afasFile.usId, afasFile.fvF1
    )

}