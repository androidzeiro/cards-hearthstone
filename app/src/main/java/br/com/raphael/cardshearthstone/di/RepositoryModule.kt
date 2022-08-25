package br.com.raphael.cardshearthstone.di

import br.com.raphael.cardshearthstone.data.repository.CardsRepository
import br.com.raphael.cardshearthstone.data.repository.CardsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCardsRepository(repository: CardsRepositoryImpl): CardsRepository
}