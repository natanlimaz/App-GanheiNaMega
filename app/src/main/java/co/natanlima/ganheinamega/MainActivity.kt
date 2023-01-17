package co.natanlima.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle("Gerador Mega Sena")

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        preferences = getSharedPreferences("database", Context.MODE_PRIVATE)
        val result = preferences.getString("result", null)

        if(result != null){
            txtResult.text = "Última aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
            editText.setText("")

            val service = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            service.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if(text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val qtd = text.toInt()

        if(qtd < 6 || qtd > 15){
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_LONG).show()
            return
        }

        val numbers = mutableSetOf<Int>()

        val random = Random()
        //[3, 4, 8, 4, 2, 6]
        while (numbers.size != qtd) {
            val number = random.nextInt(60) // 0..59
            numbers.add(number + 1)
        }

        val listSorted = numbers.sorted()

        txtResult.text = listSorted.joinToString(" - ")

        val editor = preferences.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

    }

}