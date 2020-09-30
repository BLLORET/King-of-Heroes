package fr.learnandrun.kingofheroes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.learnandrun.kingofheroes.view_model.PartyViewModel
import fr.learnandrun.kingofheroes.view_model.BoardViewModel
import fr.learnandrun.kingofheroes.view_model.DiceViewModel
import fr.learnandrun.kingofheroes.view_model.SelectFighterViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupKoin()
        setContentView(R.layout.activity_main)
    }

    private fun setupKoin() {
        val viewModelsModule = module {
            viewModel { PartyViewModel() }
            viewModel { (partyViewModel: PartyViewModel) -> BoardViewModel(partyViewModel) }
            viewModel { (partyViewModel: PartyViewModel) -> DiceViewModel(partyViewModel) }
            viewModel { (partyViewModel: PartyViewModel) -> SelectFighterViewModel(partyViewModel) }
        }
        startKoin {
            androidContext(this@MainActivity)
            modules(viewModelsModule)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopKoin()
    }

}