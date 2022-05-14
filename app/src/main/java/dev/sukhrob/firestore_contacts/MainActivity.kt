package dev.sukhrob.firestore_contacts

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sukhrob.firestore_contacts.adapter.ContactAdapter
import dev.sukhrob.firestore_contacts.model.Contact
import dev.sukhrob.firestore_contacts.viewmodel.ContactViewModel
import dev.sukhrob.firestore_contacts.databinding.ActivityMainBinding
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), ContactAdapter.OnItemClickListener {

    private lateinit var contactAdapter: ContactAdapter
    private lateinit var list: ArrayList<Contact>
    private lateinit var checkPermissions: CheckPermissions

    private val contactViewModel: ContactViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initElement()
        initViewModel()
    }

    private fun initElement() {
        list = ArrayList()
        checkPermissions = CheckPermissions(applicationContext)

        // Get list
        contactViewModel.getList()
        binding.fab.setOnClickListener {
            create()
        }

    }

    private fun initViewModel() {
        contactViewModel.getListLiveData.observe(this) {
            onGetList(it)
        }
    }

    private fun onGetList(it: List<Contact>) {
        list = ArrayList()
        list.addAll(it)

        contactAdapter = ContactAdapter(list, this)

        binding.rv.adapter = contactAdapter
        binding.rv.layoutManager = LinearLayoutManager(baseContext)
        binding.rv.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )

        contactAdapter.notifyDataSetChanged()
    }

    private fun create() {

        val dialog = ContactDialog()
        dialog.setListener {
            contactViewModel.create(it)
        }
        dialog.show(supportFragmentManager, "dialog")

    }

    override fun onDelete(item: Contact, position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Yes") { _, _ ->
            contactViewModel.delete(item.id!!)
            Toast.makeText(applicationContext, "Contact deleted!", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ ->

        }
        builder.setTitle("Delete contact")
        builder.setMessage("Are you sure you want to delete ${item.firstname}")
        builder.create().show()

    }

    override fun onClick(item: Contact, position: Int) {
        val dialog = ContactDialog(item)
        dialog.setListener {
            contactViewModel.update(it)
        }
        dialog.show(supportFragmentManager, "update dialog")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCallClicked(item: Contact) {
        val phone = item.phone
        if (checkPermissions.checkPermission(Manifest.permission.CALL_PHONE)) {
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:$phone")
            startActivity(callIntent)
        } else {
            checkPermissions.requestPermission(Manifest.permission.CALL_PHONE)
        }
    }

}
