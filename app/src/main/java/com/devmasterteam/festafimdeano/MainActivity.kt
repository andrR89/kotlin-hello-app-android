package com.devmasterteam.festafimdeano

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.devmasterteam.festafimdeano.util.FimDeAnoConstants
import com.devmasterteam.festafimdeano.util.SecurityPreferences
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var securityPreferences: SecurityPreferences

    override fun onClick(v: View?) {
       val id = v?.id
        if(id == R.id.button_confirm){

            val intent = Intent(this, DetailsActivity::class.java)
            intent.putExtra(FimDeAnoConstants.PRESENCE, securityPreferences.getString(FimDeAnoConstants.PRESENCE))
            startActivity(intent)
        }
    }

    private val viewHolder = ViewHolder()

    private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        securityPreferences = SecurityPreferences(this)

        viewHolder.textToday = findViewById(R.id.text_today) as TextView
        viewHolder.textDayLeft = findViewById(R.id.text_days_left) as TextView
        viewHolder.buttonConfirm = findViewById(R.id.button_confirm) as Button

        viewHolder.buttonConfirm.setOnClickListener(this)
        viewHolder.textToday.text = simpleDateFormat.format(Calendar.getInstance().time)
        viewHolder.textDayLeft.text = "${getDaysLeft()} dias"
    }

    override fun onResume() {
        super.onResume()
        verifyPresence()
    }

    private fun getDaysLeft():String {
        val calendarToday = Calendar.getInstance()
        val today = calendarToday.get(Calendar.DAY_OF_YEAR)
        val december31 = calendarToday.getActualMaximum(Calendar.DAY_OF_YEAR)

        return (december31 - today).toString()
    }

    private fun verifyPresence(){
        val presence = securityPreferences.getString(FimDeAnoConstants.PRESENCE)
        when (presence) {
            "" -> viewHolder.buttonConfirm.setText(R.string.nao_confirmado)
            FimDeAnoConstants.CONFIRM_WILL_GO -> viewHolder.buttonConfirm.setText(R.string.sim)
            else -> viewHolder.buttonConfirm.setText(R.string.nao)
        }
    }

    private class ViewHolder {
        lateinit var textToday:TextView
        lateinit var textDayLeft:TextView
        lateinit var buttonConfirm:Button
    }

}
