package com.example.diceroller2

//import android.media.Image //Since I deleted the png file, this isn't needed.
import androidx.appcompat.app.AppCompatActivity  //fun onCreate is already built, but we always want to override as done below.
import android.os.Bundle // this is just required by default.
import android.widget.Button // Anywhere you have a button, gotta have this
import android.widget.ImageView  // Had to set this import due to line 35 -- ImageView
import android.widget.TextView // Anywhere you see TextView, gotta have this
import android.widget.Toast // Had to set this due to lines 28 thru 30


class MainActivity : AppCompatActivity() {

    private lateinit var resultDiceImage1 : ImageView
    private lateinit var resultDiceImage2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        rollButton.setOnClickListener { rollDice() }

        val countButton: Button = findViewById(R.id.countup_button)
        countButton.setOnClickListener { countUp() }

        val resetButton: Button = findViewById(R.id.reset)
        resetButton.setOnClickListener { reset() }

        resultDiceImage1 = findViewById(R.id.dice_image1)
        resultDiceImage2 = findViewById(R.id.dice_image2)
    }

    private fun rollDice() {
        //the Toast is s temporary word bubble that pops up to tell you that yes, you did click a button in the application
        Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()
        resultDiceImage1.setImageResource(getRandomDiceImage())
        resultDiceImage2.setImageResource(getRandomDiceImage())
        // The below val has been commented out here and in reset() because of the late init var declaration of ImageView and further down where it's set = to findViewById(R.id.dice_image
        //val resultDiceImage: ImageView = findViewById(R.id.dice_image)
    }

    private fun getRandomDiceImage(randomInt: Int = (1..6).random()) : Int {
        // val randomInt : Int = (1..6).random()  // This works instead of making it a function parameter in parenthesis above, you just can't use var or val in parameter. Not sure why yet..
        return when (randomInt) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }

    private fun countUp() {
        val countupText = findViewById<TextView>(R.id.count_up)

        //If nothing has been rolled yet
        if (countupText.text == "Count Up") {
            countupText.text = "1"
        } else {
            var resultInt= countupText.text.toString().toInt()

            if (resultInt < 6) {
                resultInt++
                countupText.text = resultInt.toString()
            }
        }
    }

    private fun reset() { //Resets count and resets image to default blank/no dice image
        val countupText = findViewById<TextView>(R.id.count_up)
        countupText.text = 0.toString()
        // The below val has been commented out here and in rollDice() because of the late init var declaration of ImageView and further down where it's set = to findViewById(R.id.dice_image
        //val resultDiceImage: ImageView = findViewById(R.id.dice_image)
        resultDiceImage1.setImageResource(R.drawable.empty_dice)  //If I set (0) instead of r.drawable.empty_dice, it's the same thing as setting tools instead of android in acticity_main.xml
        resultDiceImage2.setImageResource(R.drawable.empty_dice)
        }
}