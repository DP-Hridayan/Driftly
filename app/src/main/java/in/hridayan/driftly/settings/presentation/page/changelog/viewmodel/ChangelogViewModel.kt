package `in`.hridayan.driftly.settings.presentation.page.changelog.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import `in`.hridayan.driftly.settings.presentation.page.changelog.data.model.ChangelogItem
import `in`.hridayan.driftly.settings.presentation.page.changelog.domain.usecase.GetChangelogsUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ChangelogViewModel @Inject constructor(
    private val getChangelogsUseCase: GetChangelogsUseCase
) : ViewModel() {

    private val _changelogs = mutableStateOf<List<ChangelogItem>>(emptyList())
    val changelogs: State<List<ChangelogItem>> = _changelogs

    init {
        viewModelScope.launch {
            _changelogs.value = getChangelogsUseCase()
        }
    }

    fun splitStringToLines(input: String): List<String> {
        return input.split("\n").filter { it.isNotBlank() }
    }
}