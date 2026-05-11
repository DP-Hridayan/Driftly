package `in`.hridayan.driftly.settings.domain.usecase

import android.content.Context
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.settings.data.local.source.VersionToChangelogs
import `in`.hridayan.driftly.settings.data.local.source.versionToChangelogsMap
import `in`.hridayan.driftly.settings.domain.model.ChangelogItem

class GetAllChangelogsUseCase(
    private val context: Context,
    private val versionToChangelogs: List<VersionToChangelogs> = versionToChangelogsMap
) {
    operator fun invoke(): List<ChangelogItem> {
        return versionToChangelogs.map { item ->

            val text = context.getString(
                if (item.changelogsResId != 0) item.changelogsResId else R.string.no_changelog_found
            )

            ChangelogItem(versionName = item.version, changelog = text)
        }
    }
}