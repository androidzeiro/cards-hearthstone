package br.com.raphael.cardshearthstone.ui

import br.com.raphael.cardshearthstone.databinding.ActivityMainBinding
import br.com.raphael.cardshearthstone.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)