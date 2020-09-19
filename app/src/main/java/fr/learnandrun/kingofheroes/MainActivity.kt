package fr.learnandrun.kingofheroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.learnandrun.kingofheroes.ui.home_screen.HomeFragment
import fr.learnandrun.kingofheroes.ui.select_fighter_screen.SelectFighterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}