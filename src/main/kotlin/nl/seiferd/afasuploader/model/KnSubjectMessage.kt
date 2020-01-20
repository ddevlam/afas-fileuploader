package nl.seiferd.afasuploader.model

class KnSubjectMessage(val knSubject: KnSubject) {
    class KnSubject(val element: Element) {
        class Element(var fields: Fields, var objects: List<Objects>) {
            class Fields(val stId: Int, val ds: String, val da: String, val usId: String, val fvF1: Int?)
            class Objects(var knSubjectLink: KnSubjectLink, var knSubjectAttachment: KnSubjectAttachment) {
                class KnSubjectLink(val element: Element) {
                    class Element(val fields: Fields) {
                        class Fields(val sfTp: Int = 2, val sfId: String, val toEm: Boolean = true)
                    }
                }

                class KnSubjectAttachment(val element: Element) {
                    class Element(val fields: Fields) {
                        class Fields(val fileName: String, val fileStream: String)
                    }
                }
            }
        }
    }
}