package fr.learnandrun.kingofheroes

import android.app.Application
import android.content.Intent
import fr.learnandrun.kingofheroes.view_model.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class KingOfHeroesApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun setupKoin() {
        val viewModelsModule = module {
            viewModel { PartyViewModel() }
            viewModel { (partyViewModel: PartyViewModel) -> BoardViewModel(partyViewModel) }
            viewModel { (partyViewModel: PartyViewModel) -> DiceViewModel(partyViewModel) }
            viewModel { (partyViewModel: PartyViewModel) -> SelectFighterViewModel(partyViewModel) }
            viewModel { (partyViewModel: PartyViewModel) -> ShopViewModel(partyViewModel) }
        }
        startKoin {
            androidContext(this@KingOfHeroesApplication)
            modules(viewModelsModule)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }

}