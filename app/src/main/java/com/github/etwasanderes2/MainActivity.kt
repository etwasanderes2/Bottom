package com.github.etwasanderes2

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import java.security.SecureRandom

const val MAX_SIZE = 10000

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sizeView = findViewById<EditText>(R.id.editSize)
        sizeView.setOnEditorActionListener { v, actionId, event ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_GO -> {
                    generate(sizeView)
                    true
                }
                else -> false
            }
        }
    }




    fun generate(view: View) {
        val rng = SecureRandom()
        val sizeView = findViewById<EditText>(R.id.editSize)
        val size = sizeView.text.toString().toIntOrNull() ?: run {
            Toast.makeText(applicationContext, "How the fuck did you manage to enter a non-number (${sizeView.text}) into a number field?", Toast.LENGTH_LONG).show()
            return@generate
        }
        if (size > MAX_SIZE) {
            Toast.makeText(applicationContext, "Size too great! Maximum is $MAX_SIZE", Toast.LENGTH_SHORT).show()
            return
        }
        if (size < 0) {
            Toast.makeText(applicationContext, "How the fuck did you manage to enter a negative size ($size)?", Toast.LENGTH_LONG).show()
            return
        }
        val data = ByteArray(size)
        rng.nextBytes(data)

        val encoded = when (findViewById<RadioGroup>(R.id.radioGroupSelectEncoding).checkedRadioButtonId) {
            R.id.radioBase64NoPad -> Base64.encodeToString(data, Base64.NO_WRAP or Base64.NO_PADDING)
            R.id.radioBase64 -> Base64.encodeToString(data, Base64.NO_WRAP)
            R.id.radioHex -> data.joinToString(separator = "") { "%02x".format(it) }
            else -> {
                Toast.makeText(applicationContext, "How the fuck did you manage to check a radio button that doesn't exist?", Toast.LENGTH_LONG).show()
                return
            }
        }

        val resultView = findViewById<EditText>(R.id.editResult)
        resultView.setText(encoded)
    }

    fun clear(view: View) {
        val resultView = findViewById<EditText>(R.id.editResult)
        resultView.setText("")
    }

    fun copy(view: View) {
        val resultView = findViewById<EditText>(R.id.editResult)
        val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        clipboardManager.setPrimaryClip(ClipData.newPlainText(resultView.text, resultView.text))
    }
}