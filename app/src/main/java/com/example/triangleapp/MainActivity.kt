package com.example.triangleapp

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var side1Length: EditText
    private lateinit var side2Length: EditText
    private lateinit var side3Length: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calculateButton: Button = findViewById(R.id.calButton)
        val clearButton: Button = findViewById(R.id.clearButton)

        side1Length = findViewById(R.id.inputSide1)
        side2Length = findViewById(R.id.inputSide2)
        side3Length = findViewById(R.id.inputSide3)

        // listen to events from Calculate button
        calculateButton.setOnClickListener {
            if (side1Length.text.toString() != "" && side2Length.text.toString() != ""
                    && side3Length.text.toString() != "") {
                val side1Len: Float = side1Length.text.toString().toFloat()
                val side2Len: Float = side2Length.text.toString().toFloat()
                val side3Len: Float = side3Length.text.toString().toFloat()

                val s1: Boolean = check_range(side1Len)
                val s2: Boolean = check_range(side2Len)
                val s3: Boolean = check_range(side3Len)

                // find the type of the triangle after validating all 3 sides
                if (s1 && s2 && s3)
                    findTriangleType(side1Len, side2Len, side3Len)
                else {
                    // alert when one of the values is out of range
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error")
                    builder.setMessage("Input values out of bound! Only 1 to 100 accepted.")
                    builder.setPositiveButton("OK") { _, _ -> }
                    builder.show()
                }
            }
            else {
                // show error dialog for empty values
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error")
                builder.setMessage("Empty value(s)!")
                builder.setPositiveButton("OK") { _, _ -> }
                builder.show()
            }
        }

        // listen for events from Clear button
        clearButton.setOnClickListener {
            side1Length.text.clear()
            side2Length.text.clear()
            side3Length.text.clear()

            val triangleImg: ImageView = findViewById(R.id.triangleImage)
            triangleImg.visibility = View.VISIBLE

            val resultText: TextView = findViewById(R.id.result)
            resultText.text = ""

        }
    }
    // algorithm to find the triangle types
    private fun check_range(value: Float): Boolean {
        if (value >= 1 && value <= 100) {
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



