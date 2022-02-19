package com.example.sampleapplication1.activities

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.sampleapplication1.R
import com.example.sampleapplication1.base.ViewBindingActivity
import com.example.sampleapplication1.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : ViewBindingActivity<ActivityMainBinding>() {

    companion object {
        private const val DATE_FORMAT = "dd/MM/yyyy"
    }

    private var gender: String = "Female"

    override fun onCreateViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDatePickerDialog()
        setupForGender()
        binding.cvCreateAccount.setOnClickListener { checkValidations() }
    }

    private fun setupDatePickerDialog() {
        val cal = Calendar.getInstance()
        val dateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(DATE_FORMAT, Locale.US)
                binding.edtDob.setText(sdf.format(cal.time))
            }
        binding.edtDob.setOnClickListener {
            DatePickerDialog(
                this@MainActivity, dateSetListener,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    private fun setupForGender() {
        binding.cvFemale.setOnClickListener {
            binding.cvFemale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.cvMale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            binding.cvFemale.strokeColor = ContextCompat.getColor(this, R.color.light_blue)
            binding.cvMale.strokeColor = ContextCompat.getColor(this, R.color.gray)
            gender = resources.getString(R.string.female)
        }
        binding.cvMale.setOnClickListener {
            binding.cvMale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.white))
            binding.cvFemale.setCardBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            binding.cvMale.strokeColor = ContextCompat.getColor(this, R.color.light_blue)
            binding.cvFemale.strokeColor = ContextCompat.getColor(this, R.color.gray)
            gender = resources.getString(R.string.male)
        }
    }

    private fun checkValidations() {
        val editTexts: HashMap<EditText, String> = HashMap<EditText, String>()
        val validationMessages: MutableList<String> = mutableListOf()

        // validate for empty editTexts
        if (binding.edtFirstName.text!!.isEmpty() || binding.edtLastName.text!!.isEmpty()
            || binding.edtEmail.text!!.isEmpty() || binding.edtNationality.text!!.isEmpty()
            || binding.edtCountry.text!!.isEmpty()
        ) {
            editTexts[binding.edtFirstName] = resources.getString(R.string.enter_first_name)
            editTexts[binding.edtLastName] = resources.getString(R.string.enter_last_name)
            editTexts[binding.edtEmail] = resources.getString(R.string.enter_email)
            editTexts[binding.edtNationality] = resources.getString(R.string.enter_nationality)
            editTexts[binding.edtCountry] = resources.getString(R.string.enter_country)
            validationMessages.addAll(validationForEditTexts(editTexts))
        }

        // validate for valid email address
        if (binding.edtEmail.text?.isNotEmpty()!!) {
            if (!isValidEmail(binding.edtEmail.text)) {
                validationMessages.add(resources.getString(R.string.enter_valid_email))
            }
        }

        // validate for date of birth
        if (binding.edtDob.text.toString()
                .equals(DATE_FORMAT, ignoreCase = true)
        ) {
            validationMessages.add(resources.getString(R.string.enter_dob))
        }

        if (validationMessages.size > 0) {
            showAlerts(validationMessages)
        } else {
            Toast.makeText(applicationContext, "Account created successfully!", Toast.LENGTH_LONG)
                .show()
        }
    }


    private fun validationForEditTexts(inputs: Map<EditText, String?>): Collection<String> {
        val strMsg: MutableList<String> = ArrayList()
        for (et in inputs.keys) {
            if (TextUtils.isEmpty(et.text.toString())) {
                strMsg.add(inputs[et]!!)
            }
        }
        return strMsg
    }

    private fun showAlerts(validationMsgs: List<String?>) {
        var error = StringBuilder()
        for (msg in validationMsgs.asReversed()) {
            if (error.toString() == "") {
                error = StringBuilder(msg)
            } else {
                error.append("\n").append(msg)
            }
        }
        val builder = AlertDialog.Builder(this)
        builder.setMessage(error)
        builder.setPositiveButton("OK") { _, _ ->
            Toast.makeText(applicationContext, "Please submit a valid form!", Toast.LENGTH_LONG)
                .show()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(true)
        alertDialog.show()
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}