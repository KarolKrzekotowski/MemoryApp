package com.example.memoryapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.fragment.app.Fragment
import java.util.*

class LanguageManager(private val ctx: Context, private val sharedPreferences: SharedPreferences = ctx.getSharedPreferences("LANG",
    Context.MODE_PRIVATE
)) {

    fun UpdateResource(code:String){
        val locale = Locale(code)
        Locale.setDefault(locale)
        val resources = ctx.resources
        val config = resources.configuration
        config.locale = locale
        resources.updateConfiguration(config,resources.displayMetrics)
        setLanguge(code)
    }
    fun setLanguge(string: String){
        val editor = sharedPreferences.edit()
        editor.putString("LANG",string)
        editor.commit()

    }
    fun getLanguage():String{
        return sharedPreferences.getString("LANG","en")!!

    }


}
fun Fragment.translate(){
    val languageManager = LanguageManager(requireContext())
    val currentLang = languageManager.getLanguage()
    when(currentLang){
        "en" -> languageManager.UpdateResource("pl")
        "pl" -> languageManager.UpdateResource("en")
        else -> languageManager.UpdateResource("en")
    }

}
