package `in`.hridayan.driftly.core.domain.provider

import android.content.Context
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.domain.model.SubjectClassType

fun classTypeToString(
    context: Context,
    subjectClassType: SubjectClassType
): String {
    return when (subjectClassType) {
        SubjectClassType.NONE -> context.getString(R.string.none)

        SubjectClassType.THEORETICAL -> context.getString(R.string.theoretical)

        SubjectClassType.PRACTICAL -> context.getString(R.string.practical)
    }
}