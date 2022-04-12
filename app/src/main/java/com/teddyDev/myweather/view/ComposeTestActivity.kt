package com.teddyDev.myweather.view

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class ComposeTestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NomeFunTestComposable("Davide")
        }
    }
    
}

@Composable
fun NomeFunTestComposable(name: String){
    Text(text = "Ciao $name")
}

@Preview
@Composable
fun PreviewNomeFunTestComposable(){
    NomeFunTestComposable("Davide")
}