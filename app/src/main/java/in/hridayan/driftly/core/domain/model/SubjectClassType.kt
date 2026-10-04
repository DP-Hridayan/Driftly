package `in`.hridayan.driftly.core.domain.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
enum class SubjectClassType {
    NONE, THEORETICAL, PRACTICAL
}