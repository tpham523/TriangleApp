package com.example.triangleapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var side1Length: EditText
    private lateinit var side2Length: EditText
    private lateinit var side3Length: EditText

    //  create a textWatcher member
    private val mTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}
        override fun afterTextChanged(editable: Editable) {
            // check Fields For Empty Values
            checkFieldsForEmptyValues()
        }
    }

    fun checkFieldsForEmptyValues() {
        val b: Button = findViewById(R.id.calButton)

        val s1 = side1Length.text.toString()
        val s2 = side2Length.text.toString()
        val s3 = side3Length.text.toString()

        b.isEnabled = !(s1 == "" || s2 == "" || s3 == "")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculateButton: Button = findViewById(R.id.calButton)
        val clearButton: Button = findViewById(R.id.clearButton)

        side1Length = findViewById(R.id.inputSide1)
        side2Length = findViewById(R.id.inputSide2)
        side3Length = findViewById(R.id.inputSide3)

        calculateButton.setOnClickListener{

            findTriangleType(side1Length.text.toString().toFloat(),
                    side2Length.text.toString().toFloat(),
                    side3Length.text.toString().toFloat())
        }

        clearButton.setOnClickListener{
            side1Length.text.clear()
            side2Length.text.clear()
            side3Length.text.clear()
        }

        side1Length.addTextChangedListener(mTextWatcher)
        side2Length.addTextChangedListener(mTextWatcher)
        side3Length.addTextChangedListener(mTextWatcher)

    }

    // algorithm to find the triangle types
    private fun check_range(value: Float): Boolean {
        if (value >= 1 && value <= 200) {
            return true
        }
        return false
    }

    private fun check_c123(side1: Float, side2: Float, side3: Float): Boolean {
        val check_side1: Boolean = check_range(side1)
        val check_side2: Boolean = check_range(side2)
        val check_side3: Boolean = check_range(side3)

        return (check_side1 && check_side2 && check_side3)
    }

    private fun check_g_add(side1: Float, side2: Float, side3: Float): Boolean {
        return (side1 < (side2 + side3))
    }

    private fun check_c456(side1: Float, side2: Float, side3: Float): Boolean {
        return (check_g_add(side1, side2, side3) && check_g_add(side2, side1, side3) && check_g_add(side3, side1, side2))
    }

    private fun findTriangleType(side1: Float, side2: Float, side3: Float) {

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

