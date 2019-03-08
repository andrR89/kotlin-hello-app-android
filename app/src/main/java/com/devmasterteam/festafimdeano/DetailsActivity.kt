package com.devmasterteam.festafimdeano

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.devmasterteam.festafimdeano.util.FimDeAnoConstants
import com.devmasterteam.festafimdeano.util.SecurityPreferences

class DetailsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var securityPreferences:SecurityPreferences

    override fun onClick(v: View?) {
        val id = v?.id
        if(id == R.id.check_participate){
            if(viewHolder.checkConfirm.isChecked){
                securityPreferences.storeString(FimDeAnoConstants.PRESENCE, FimDeAnoConstants.CONFIRM_WILL_GO)
            }else{
                securityPreferences.storeString(FimDeAnoConstants.PRESENCE, FimDeAnoConstants.CONFIRM_WONT_GO)
            }
        }
    }

    private val viewHolder = ViewHolder()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setIcon(R.mipmap.ic_launcher)

        securityPreferences = SecurityPreferences(this)
        viewHolder.checkConfirm = findViewById(R.id.check_participate) as CheckBox
        viewHolder.checkConfirm.setOnClickListener(this)

        loadDataFromActivity()

    }

    private fun loadDataFromActivity(){
        val extras = intent.extras
        if(extras != null){
            val presence = securityPreferences.getString(FimDeAnoConstants.PRESENCE)
            viewHolder.checkConfirm.isChecked = presence == FimDeAnoConstants.CONFIRM_WILL_GO
        }
    }

    private class ViewHolder {
        lateinit var checkConfirm: CheckBox
    }
}
