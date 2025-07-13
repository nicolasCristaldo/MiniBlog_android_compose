# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Mantener anotaciones y firmas para reflexión y serialización
-keepattributes *Annotation*
-keepattributes Signature

# Mantener clases y métodos de bibliotecas comunes de Google y Android
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-keep class androidx.** { *; }

# Mantener clases de tu aplicación (ajusta "your.package" a tu paquete real)
-keep class com.nicolascristaldo.miniblog.data.** { *; }
-keep class com.nicolascristaldo.miniblog.ui.** { *; }
-keep class com.nicolascristaldo.miniblog.di.** { *; }

# Reglas para Hilt (si usas inyección de dependencias con Hilt)
-keep class dagger.hilt.** { *; }
-keep class dagger.** { *; }

# Reglas para Jetpack Compose (si usas Compose)
-keep class androidx.compose.** { *; }

# Reglas para serialización (por ejemplo, Gson)
-keep class com.google.gson.** { *; }
-keep class com.nicolascristaldo.miniblog.domain.** { *; }  # Ajusta a tu paquete de modelos

# Reglas para WorkManager (si usas tareas en segundo plano)
-keep class androidx.work.** { *; }

# Reglas para Navigation Component (si usas navegación)
-keep class androidx.navigation.** { *; }

# Reglas para Lifecycle (si usas componentes de Lifecycle)
-keep class androidx.lifecycle.** { *; }

# Reglas para Coroutines (si usas operaciones asincrónicas)
-keep class kotlinx.coroutines.** { *; }
-keep class kotlinx.coroutines.flow.** { *; }

# Reglas específicas de Firebase
-keep class com.google.firebase.auth.** { *; }           # Authentication
-keep class com.google.firebase.firestore.** { *; }      # Firestore
-keep class com.google.firebase.database.** { *; }       # Realtime Database
-keep class com.google.firebase.storage.** { *; }        # Storage
-keep class com.google.firebase.crashlytics.** { *; }    # Crashlytics
-keep class com.google.firebase.analytics.** { *; }      # Analytics
-keep class com.google.firebase.remoteconfig.** { *; }   # Remote Config
-keep class com.google.firebase.perf.** { *; }           # Performance Monitoring
-keep class com.google.firebase.appdistribution.** { *; } # App Distribution
-keep class com.google.firebase.inappmessaging.** { *; } # In-App Messaging
-keep class com.google.firebase.dynamiclinks.** { *; }   # Dynamic Links
-keep class com.google.firebase.ml.** { *; }             # ML Kit

# Reglas para componentes de AndroidX y Material Design
-keep class com.google.android.material.** { *; }        # Material Components
-keep class androidx.constraintlayout.** { *; }          # ConstraintLayout
-keep class androidx.recyclerview.** { *; }              # RecyclerView
-keep class androidx.viewpager.** { *; }                 # ViewPager
-keep class androidx.fragment.** { *; }                  # Fragments
-keep class androidx.activity.** { *; }                  # Activities
-keep class androidx.service.** { *; }                   # Services
-keep class androidx.broadcast.** { *; }                 # BroadcastReceivers
-keep class androidx.provider.** { *; }                  # ContentProviders
-keep class androidx.preference.** { *; }                # Preferences
-keep class androidx.paging.** { *; }                    # Paging
-keep class androidx.databinding.** { *; }               # Data Binding
-keep class androidx.biometric.** { *; }                 # Biometric