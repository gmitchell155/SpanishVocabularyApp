 package com.example.assignment2
 /*
    George Mitchell
    CSCI 4020
    Assignment 2
 */

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.databinding.ActivityMainBinding
import java.io.InputStream
import java.util.*
import kotlin.random.Random

 class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //stores all the words in spanish in a mutable list
     private var spanishWords : MutableList<String> = mutableListOf()
     //stores all the words in english in a mutable list
     private var englishWords : MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.myRecyclerview.setLayoutManager(layoutManager)

        binding.myRecyclerview.setHasFixedSize(true)

        //adds divider between each list item
        val divider = DividerItemDecoration(
            applicationContext, layoutManager.orientation
        )
        binding.myRecyclerview.addItemDecoration(divider)

        val adapter = MyAdapter()
        binding.myRecyclerview.setAdapter(adapter)
        wordList()
    }

     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.options_menu, menu)
         return super.onCreateOptionsMenu(menu)
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         //takes user to the first word in the list
         if(item.itemId == R.id.top_menu_option){
             binding.myRecyclerview.scrollToPosition(0)

         }//takes users to the last word in the list
         else if(item.itemId == R.id.bottom_menu_option){
             binding.myRecyclerview.scrollToPosition(spanishWords.size - 1)

         }//takes user to a random word in the list
         else if(item.itemId == R.id.random_menu_option){
             var randomValue = Random.nextInt(0, spanishWords.size - 1)
             binding.myRecyclerview.scrollToPosition(randomValue)
         }
         return super.onOptionsItemSelected(item)
     }


     inner class MyViewHolder(val itemView: View) :
         RecyclerView.ViewHolder(itemView),
        View.OnClickListener{

         init{
             itemView.findViewById<View>(R.id.item_constraintLayout)
                 .setOnClickListener(this)
         }


            fun setText(text : String){
                itemView.findViewById<TextView>(R.id.item_textView)
                    .setText(text)
            }

        override fun onClick(view: View?) {
            //creates intent that opens in Word Activity when list item is clicked
             if(view != null){

                 val intent = Intent(view.context, WordActivity::class.java)

                 val word = spanishWords[adapterPosition]
                 val word2 = englishWords[adapterPosition]


                 intent.putExtra(
                     getString(R.string.word_intent_key),
                     word

                 )
                 intent.putExtra(
                     getString(R.string.word_intent_key2),
                     word2
                 )
                 startActivity(intent)
             }
         }
     }

     inner class MyAdapter() : RecyclerView.Adapter<MyViewHolder>(){
         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
             val view = LayoutInflater.from(parent.context)
                 .inflate(R.layout.item_view, parent, false)
             return MyViewHolder(view)
         }

         override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
             //sets text in the recyclerview
             holder.setText(spanishWords[position])
         }

         override fun getItemCount(): Int {
             return spanishWords.size

         }

     }

     private fun wordList() {
         //gets words from the text file
         val input: InputStream = getAssets().open("spanish_words.txt")

         val scanner = Scanner(input)
         while (scanner.hasNextLine()) {
             spanishWords += scanner.nextLine()
             englishWords += scanner.nextLine()
             //if a blank line is encountered it goes to the next line
             scanner.nextLine()

                 Log.i("All words in spanish", spanishWords.toString())
                 Log.i("All words in english", englishWords.toString())
         }
     }

}