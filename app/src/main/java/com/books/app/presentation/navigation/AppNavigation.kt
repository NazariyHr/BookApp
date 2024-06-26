package com.books.app.presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.books.app.presentation.features.details.DetailsScreenRoot
import com.books.app.presentation.features.library.LibraryScreenRoot
import com.books.app.presentation.features.loading.LoadingScreenRoot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Screen.Loading
    ) {
        composable<Screen.Loading>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            LoadingScreenRoot(navController)
        }
        composable<Screen.Library>(
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            LibraryScreenRoot(navController)
        }
        composable<Screen.Details>(
            typeMap = mapOf(
                typeOf<Screen.Details>() to parcelableType<Screen.Details>()
            ),
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            val bookId = it.toRoute<Screen.Details>().bookId
            DetailsScreenRoot(navController, bookId)
        }
    }
}

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}