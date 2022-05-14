package dev.sukhrob.firestore_contacts.adapter.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.firestore_contacts.R
import dev.sukhrob.firestore_contacts.databinding.ItemContactRowBinding
import dev.sukhrob.firestore_contacts.model.Contact

class ContactViewHolder(private val binding: ItemContactRowBinding): RecyclerView.ViewHolder(binding.root) {

    fun bindItem(contact: Contact) {
        itemView.apply {
            binding.name.text = "${contact.firstname}"
            binding.phone.text = "${contact.phone}"
        }
    }
}