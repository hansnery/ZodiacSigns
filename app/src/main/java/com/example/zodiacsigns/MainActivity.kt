package com.example.zodiacsigns

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.zodiacsigns.ui.theme.ZodiacSignsTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ZodiacSignsTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "splash_screen") {
                        composable("splash_screen") { SplashScreen(navController) }
                        composable("zodiacSignList") { ZodiacSignList(navController) }
                        composable("signDetails/{sign}", arguments = listOf(
                            navArgument("sign") { type = NavType.StringType }
                        )) { backStackEntry ->
                            SignDetails(
                                navController = navController,
                                sign = backStackEntry.arguments?.getString("sign") ?: ""
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ZodiacSignList(navController: NavHostController) {
    val zodiacSigns = listOf(
        "Aries", "Taurus", "Gemini", "Cancer",
        "Leo", "Virgo", "Libra", "Scorpio",
        "Sagittarius", "Capricorn", "Aquarius", "Pisces"
    )

    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Horoscope",
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            val itemsPerRow = 3
            val rows = zodiacSigns.chunked(itemsPerRow)

            items(rows) { rowItems ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    for (sign in rowItems) {
                        ZodiacSignImage(sign = sign, context = context, navController = navController)
                    }
                }
            }
        }
    }
}

@SuppressLint("DiscouragedApi")
@Composable
fun ZodiacSignImage(sign: String, context: Context, navController: NavHostController) {
    val imageResource = context.resources.getIdentifier(
        sign.lowercase(),
        "drawable",
        context.packageName
    )

    if (imageResource != 0) {
        Image(
            painter = painterResource(id = imageResource),
            contentDescription = sign,
            modifier = Modifier
                .size(100.dp)
                .clickable {
                    navController.navigate("signDetails/$sign")
                }
        )
    }
}

@Composable
fun SplashScreen(navController: NavHostController) {
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.navigate("zodiacSignList") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Hans' Horoscope", style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun ZodiacSignListPreview() {
    ZodiacSignsTheme {
        zodiacSignList()
    }
}

fun zodiacSignList() {
}
