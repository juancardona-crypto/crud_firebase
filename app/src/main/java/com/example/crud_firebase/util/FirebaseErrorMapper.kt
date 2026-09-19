package com.example.crud_firebase.util

import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestoreException

fun Throwable.toUserFriendlyMessage(): String {
    return when (this) {
        is FirebaseAuthException -> {
            when (errorCode) {
                "ERROR_INVALID_EMAIL" -> "El formato del correo electrónico no es válido."
                "ERROR_WRONG_PASSWORD" -> "La contraseña es incorrecta."
                "ERROR_USER_NOT_FOUND" -> "No existe ninguna cuenta con este correo."
                "ERROR_USER_DISABLED" -> "Esta cuenta ha sido deshabilitada."
                "ERROR_TOO_MANY_REQUESTS" -> "Demasiados intentos. Por favor, intenta más tarde."
                "ERROR_EMAIL_ALREADY_IN_USE" -> "Este correo electrónico ya está registrado por otro usuario."
                "ERROR_WEAK_PASSWORD" -> "La contraseña es demasiado corta o débil."
                "ERROR_NETWORK_REQUEST_FAILED" -> "Error de red. Verifica tu conexión a internet."
                else -> "Error de autenticación: ${message ?: "desconocido"}"
            }
        }
        is FirebaseFirestoreException -> {
            when (code) {
                FirebaseFirestoreException.Code.PERMISSION_DENIED -> "No tienes permiso para realizar esta acción."
                FirebaseFirestoreException.Code.UNAVAILABLE -> "El servidor no está disponible. Revisa tu conexión."
                FirebaseFirestoreException.Code.FAILED_PRECONDITION -> {
                    if (message?.contains("index") == true) {
                        "Sincronizando base de datos... El servidor está preparando tus datos, por favor espera un par de minutos."
                    } else {
                        "Error de condición: ${message ?: "desconocido"}"
                    }
                }
                else -> "Error de base de datos: ${message ?: "desconocido"}"
            }
        }
        else -> this.localizedMessage ?: "Ha ocurrido un error inesperado."
    }
}