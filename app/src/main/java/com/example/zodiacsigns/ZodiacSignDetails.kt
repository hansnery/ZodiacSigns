package com.example.zodiacsigns

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@SuppressLint("DiscouragedApi")
@Composable
fun SignDetails(navController: NavHostController, sign: String) {
    val context = LocalContext.current
    val imageResource = context.resources.getIdentifier(sign.lowercase(), "drawable", context.packageName)
    val description = ZodiacSignsDescriptions.descriptions[sign] ?: "Description not available."

    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            //Text(text = "Sign: $sign", style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (imageResource != 0) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "$sign image",

                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally) // Center the image horizontally
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = description, style = MaterialTheme.typography.bodyLarge)
    }
}