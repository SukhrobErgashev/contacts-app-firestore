package dev.sukhrob.firestore_contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import dev.sukhrob.firestore_contacts.R
import dev.sukhrob.firestore_contacts.adapter.viewholder.ContactViewHolder
import dev.sukhrob.firestore_contacts.databinding.ItemContactRowBinding
import dev.sukhrob.firestore_contacts.model.Contact

class ContactAdapter(
    private var list: List<Contact>,
    private var onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val binding =
            ItemContactRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val item = list[position]
        holder.bindItem(item)
        holder.itemView.setOnClickListener {
            onItemClickListener.onClick(item, position)
        }
        holder.itemView.findViewById<ImageView>(R.id.delete).setOnClickListener {
            onItemClickListener.onDelete(item, position)
        }
        holder.itemView.findViewById<ImageButton>(R.id.call).setOnClickListener {
            onItemClickListener.onCallClicked(item)
        }
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener {
        fun onCallClicked(item: Contact)
        fun onClick(item: Contact, position: Int)
        fun onDelete(item: Contact, position: Int)
    }
}