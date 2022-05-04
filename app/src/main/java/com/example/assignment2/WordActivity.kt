package com.example.assignment2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.assignment2.databinding.ActivityWordBinding

class WordActivity : AppCompatActivity(), View.OnClickListener {
    //stores the english word that corresopnds to the word in spanish
    var englishWord = ""
    private lateinit var binding: ActivityWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //gets values from intent in Main Activity
        val intent = getIntent()

        //gets words in spanish
        val word = intent.getSerializableExtra(
            getString(R.string.word_intent_key)
        )
        //gets words in english
        val word2 = intent.getSerializableExtra(
            getString(R.string.word_intent_key2)
        )
        binding.button.setOnClickListener(this)
        //adds the corresponding spanish word into a textview
        binding.spanishTextView.setText(word.toString())


        englishWord = word2.toString()
        //sets the imageview icon
        binding.answerCheckImageView.setImageResource(R.drawable.ic_baseline_help_24)

    }

    override fun onClick(v: View?) {
        //changes imageview icon to a red X if what the user enters is not the correct word
        if(binding.translationEditTextText.getText().toString() != englishWord) {
            binding.answerCheckImageView.setImageResource(R.drawable.ic_baseline_close_24)


        }//changes imageview icon to a green check if what the user enters is the correct word
        else if(binding.translationEditTextText.getText().toString() == englishWord){
            binding.answerCheckImageView.setImageResource(R.drawable.ic_baseline_check_24)
        }
    }
}