package com.teddyDev.myweather.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import com.teddyDev.myweather.R


class ComposeTestFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_compose_test, container, false).apply {
            findViewById<ComposeView>(R.id.compose_view).setContent {
                ComposableFunTest("Davide")
            }
        }
    }

}

@Composable
fun ComposableFunTest(nome: String){
    Text(text = "ciao $nome")
}