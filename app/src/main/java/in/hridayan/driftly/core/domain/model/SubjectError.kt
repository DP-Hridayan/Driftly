package `in`.hridayan.driftly.core.domain.model

sealed class SubjectError {
    object Empty : SubjectError()
    object AlreadyExists : SubjectError()
    data class Unknown(val message: String) : SubjectError()
    object None : SubjectError()
}