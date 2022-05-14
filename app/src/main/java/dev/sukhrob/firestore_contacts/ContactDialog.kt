package dev.sukhrob.firestore_contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.sukhrob.firestore_contacts.databinding.DialogContactBinding
import dev.sukhrob.firestore_contacts.model.Contact
import java.util.*

class ContactDialog() : DialogFragment() {

    private var id: String? = null

    constructor(contact: Contact) : this() {
        this.id = contact.id
        binding.enterFirstName.setText(contact.firstname)
        binding.enterLastName.setText(contact.lastname)
        binding.enterPhone.setText(contact.phone)
    }

    private var _binding: DialogContactBinding? = null
    private val binding get() = _binding!!

    private var listener: ((Contact) -> Unit)? = null

    fun setListener(_listener: (Contact) -> Unit) {
        listener = _listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog!!.window?.setBackgroundDrawableResource(R.drawable.bg_container_out)
        _binding = DialogContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val timeId = if (this.id == null)
                Calendar.getInstance().timeInMillis.toString()
            else this.id
            listener?.invoke(
                Contact(
                    timeId,
                    binding.enterFirstName.text.toString(),
                    binding.enterLastName.text.toString(),
                    binding.enterPhone.text.toString(),

                )
            )
            dismiss()
        }

        binding.buttonCancel.setOnClickListener {
            dismiss()
        }

    }

    // Resize Dialog Size
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        //val height = (resources.displayMetrics.heightPixels * 0.45).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}