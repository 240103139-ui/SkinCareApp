
package com.example.skincareapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.skincareapp.navigation.AppNavigation
import com.example.skincareapp.ui.theme.SkinCareAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SkinCareAppTheme {
                AppNavigation()
            }
        }
    }
}
