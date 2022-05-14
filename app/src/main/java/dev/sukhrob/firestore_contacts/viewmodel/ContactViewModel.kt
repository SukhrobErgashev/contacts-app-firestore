package dev.sukhrob.firestore_contacts.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.sukhrob.firestore_contacts.model.Contact
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ContactViewModel: ViewModel() {

    private var db = Firebase.firestore
    private val contacts = "contacts"

    val getListLiveData: MutableLiveData<List<Contact>> by lazy {
        MutableLiveData<List<Contact>>()
    }

    fun create(contact: Contact) {
        val docRef = db.collection(contacts)
        docRef.add(contact.toMap()).addOnSuccessListener {
            getList()
        }.addOnFailureListener {
            Log.d("create", it.localizedMessage!!)
        }
    }

    fun update(contact: Contact) {
        val docRef = db.collection(contacts)
        docRef.document(contact.id!!).update(contact.toMap()).addOnSuccessListener {
            getList()
        }.addOnFailureListener {
            Log.d("update", it.localizedMessage!!)
        }
    }

    fun delete(id: String) {
        val docRef = db.collection(contacts)
        docRef.document(id).delete().addOnSuccessListener {
            getList()
        }.addOnFailureListener {
            Log.d("delete", it.localizedMessage!!)
        }
    }

    fun getList() {
        val docRef = db.collection(contacts)
        docRef.get().addOnSuccessListener {
            val contacts = ArrayList<Contact>()
            for (item in it.documents) {
                val contact = Contact()

                contact.id = item.id
                contact.firstname = item.data!!["firstname"] as String?
                contact.lastname = item.data!!["lastname"] as String?
                contact.phone = item.data!!["phone"] as String?

                contacts.add(contact)
            }

            getListLiveData.postValue(contacts)
        }.addOnFailureListener {
            Log.d("get", it.localizedMessage!!)
            getListLiveData.postValue(null)
        }
    }
}