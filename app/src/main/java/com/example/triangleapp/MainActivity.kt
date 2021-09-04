package com.example.triangleapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import org.w3c.dom.Text
import android.content.SharedPreferences
import android.util.Log

class MainActivity : AppCompatActivity() {

    var side1Len: Int = 0;
    var side2Len: Int = 0;
    var side3Len: Int = 0;

    // key for current stats
    private val side1NumKey = "side1Length"
    private val side2NumKey = "side2Length"
    private val side3NumKey = "side3Length"

    // Name of shared preferences file
    private val sharedPrefFile = "com.example.triangleapp"
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculateButton: Button = findViewById(R.id.calButton)
        val clearButton: Button = findViewById(R.id.clearButton)


        val side1Length: EditText = findViewById(R.id.inputSide1)
        val side2Length: EditText = findViewById(R.id.inputSide2)
        val side3Length: EditText = findViewById(R.id.inputSide3)


        sharedPref = getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)

        // restore preferences on pause
        side1Len = sharedPref.getInt(side1NumKey, 0)
        side1Length.setText(side1Len.toString())

        side2Len = sharedPref.getInt(side2NumKey, 0)
        side2Length.setText(side2Len.toString())

        side3Len = sharedPref.getInt(side3NumKey, 0)
        side3Length.setText(side3Len.toString())

        calculateButton.setOnClickListener{
            findTriangleType(side1Length.text.toString().toInt(),
                    side2Length.text.toString().toInt(),
                    side3Length.text.toString().toInt())
        }

        clearButton.setOnClickListener{
            side1Length.text.clear()
            side2Length.text.clear()
            side3Length.text.clear()
        }


        side1Length.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                if (s.toString().isNotEmpty()) {
                    side1Len = side1Length.text.toString().toInt();
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }
        })


        side2Length.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    side2Len = side2Length.text.toString().toInt();
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }
        })

        side3Length.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    side3Len = side3Length.text.toString().toInt();
                }
            }
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.toString().trim({ it <= ' ' }).isEmpty())
                {
                    calculateButton.setEnabled(false)
                }
                else
                {
                    calculateButton.setEnabled(true)
                }
            }
        })


    }

    // keep the inputs when the app goes to "pause" mode
    override fun onPause() {
        super.onPause();

        val editor: SharedPreferences.Editor = sharedPref.edit();
        editor.putInt(side1NumKey, side1Len);
        editor.putInt(side2NumKey, side2Len);
        editor.putInt(side3NumKey, side3Len);
        editor.apply();
    }

    // algorithm to find the triangle types
    // credit goes to Isabel
    private fun check_range(value: Int): Boolean {
        if (value in 1..200) {
            return true;
        }
        return false;
    }

    private fun check_c123(side1: Int, side2: Int, side3: Int): Boolean {
        var check_side1: Boolean = check_range(side1)
        var check_side2: Boolean = check_range(side2)
        var check_side3: Boolean = check_range(side3)

        return (check_side1 && check_side2 && check_side3)
    }

    private fun check_g_add(side1: Int, side2: Int, side3: Int): Boolean {
        return (side1 < (side2 + side3))
    }

    private fun check_c456(side1: Int, side2: Int, side3: Int): Boolean {
        return (check_g_add(side1, side2, side3) && check_g_add(side2, side1, side3) && check_g_add(side3, side1, side2))
    }

    private fun findTriangleType(side1: Int, side2: Int, side3: Int ) {

        val resultText: TextView = findViewById(R.id.result)

        val triangleImg: ImageView = findViewById(R.id.triangleImage)
        triangleImg.visibility = View.INVISIBLE

        if (check_c123(side1, side2, side3) && check_c456(side1, side2, side3)) {

            if (side1 == side2 && side2 == side3) {
                resultText.text = "Equilateral"
            }
            else if (side1 == side2 || side2 == side3 || side1 == side3) {
                resultText.text = "Isosceles"
            }
            else {
                resultText.text = "Scalene"
            }
        }
        else
            resultText.text = "Not A Triangle!"

    }

}