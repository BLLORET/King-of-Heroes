package fr.learnandrun.kingofheroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.learnandrun.kingofheroes.business.Board
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoin()
        setContentView(R.layout.activity_main)
    }

    private fun setupKoin() {
        val boardModule = module {
            single {
                Board()
            }
        }
    }
}