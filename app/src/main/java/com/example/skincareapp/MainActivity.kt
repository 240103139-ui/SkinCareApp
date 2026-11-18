
package com.example.skincareapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.skincareapp.ui.theme.SkinCareAppTheme
import com.example.skincareapp.view.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkinCareAppTheme {
                HomeScreen()
            }
        }
    }
}
