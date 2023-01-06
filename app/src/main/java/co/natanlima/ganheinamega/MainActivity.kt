package co.natanlima.ganheinamega

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
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

        val editText: EditText = findViewById(R.id.edit_number)
        val txtResult: TextView = findViewById(R.id.txt_result)
        val btnGenerate: Button = findViewById(R.id.btn_generate)

        // shared preferences eh um arquivo armazenado dentro do seu smartphone com informaços no formato xml, e essas infos  tambem teramo suas props e seus valores
        //database preferences
        preferences = getSharedPreferences("database", Context.MODE_PRIVATE)
        val result = preferences.getString("result", null)

        if(result != null){
            txtResult.text = "Última aposta: $result"
        }

        btnGenerate.setOnClickListener {
            val text = editText.text.toString()
            numberGenerator(text, txtResult)
            editText.setText("")
        }

    }

    private fun numberGenerator(text: String, txtResult: TextView) {

        if(text.isEmpty()) {
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val qtd = text.toInt()

        if(qtd < 6 || qtd > 15){
            Toast.makeText(this, "Informe um número entre 6 e 15", Toast.LENGTH_SHORT).show()
            return
        }

        val numbers = mutableSetOf<Int>()

        val random = Random()

        while (numbers.size != qtd) {
            val number = random.nextInt(60) // 0..59
            numbers.add(number + 1)
        }

        txtResult.text = numbers.joinToString(" - ")

        val editor = preferences.edit()
        editor.putString("result", txtResult.text.toString())
        editor.apply()

        /*
        * commit -> salvar de forma síncrona (bloquear a interface gráfica) e informar se teve sucesso ou não (usar quando é um dado muito muito simples)
        *
        * apply -> salvar de forma assíncrona (não vai bloquear a interface gráfica) e não vai informar se teve sucesso ou não (ESSE EH O QUE O GOOGLE RECOMENDA USAR)
        * */
    }

}