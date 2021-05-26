package com.example.zcontacts.di

import com.example.zcontacts.MyApplication
import com.example.zcontacts.addcontact.AddContactViewModelFactory
import com.example.zcontacts.database.Repository
import com.example.zcontacts.detailview.DetailViewModelFactory
import com.example.zcontacts.overview.MasterFragmentViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface MainComponent {

    fun getRepo(): Repository

    fun inject(application: MyApplication)

}
