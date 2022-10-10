package com.github.etwasanderes2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import java.security.SecureRandom

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
        val size = sizeView.text.toString().toIntOrNull() ?: return
        //TODO: error toast
        //TODO: max size
        val data = ByteArray(size)
        rng.nextBytes(data)

        //TODO: other encodings
        val encoded = Base64.encodeToString(data, Base64.NO_WRAP or Base64.NO_PADDING)

        val resultView = findViewById<EditText>(R.id.editResult)
        resultView.setText(encoded)
    }
}