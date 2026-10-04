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

-keep class androidx.datastore.preferences.PreferencesProto$* { *; }
-keepclassmembers class androidx.datastore.preferences.PreferencesProto$* { *; }
-keep class org.slf4j.** { *; }
-dontwarn org.slf4j.**

# Jetpack Navigation Compose Type Safe Routes & Kotlinx Serialization
-keepattributes *Annotation*, InnerClasses
-keep class * implements kotlinx.serialization.KSerializer { *; }
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
}
-keep class in.hridayan.driftly.navigation.** { *; }
-keep class in.hridayan.driftly.core.domain.model.** { *; }
-keepclassmembers enum in.hridayan.driftly.core.domain.model.** { *; }
-keep class in.hridayan.driftly.core.data.model.** { *; }
-keep class in.hridayan.driftly.settings.domain.model.** { *; }


