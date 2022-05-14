package dev.sukhrob.firestore_contacts.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class Contact(
    var id: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var phone: String? = null,
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "firstname" to firstname,
            "lastname" to lastname,
            "phone" to phone,
        )
    }
}