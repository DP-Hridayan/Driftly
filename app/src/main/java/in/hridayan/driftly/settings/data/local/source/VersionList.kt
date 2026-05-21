package `in`.hridayan.driftly.settings.data.local.source

import `in`.hridayan.driftly.R

data class VersionToChangelogs(
    val version: String,
    val changelogsResId: Int
)

val versionToChangelogsMap = listOf(
    VersionToChangelogs("v1.9.0", R.string.changelogs_v1_9_0),
    VersionToChangelogs("v1.8.3", R.string.changelogs_v1_8_3),
    VersionToChangelogs("v1.8.2", R.string.changelogs_v1_8_2),
    VersionToChangelogs("v1.8.1", R.string.changelogs_v1_8_1),
    VersionToChangelogs("v1.8.0", R.string.changelogs_v1_8_0),
    VersionToChangelogs("v1.7.0", R.string.changelogs_v1_7_0),
    VersionToChangelogs("v1.6.0", R.string.changelogs_v1_6_0),
    VersionToChangelogs("v1.5.1", R.string.changelogs_v1_5_1),
    VersionToChangelogs("v1.5.0", R.string.changelogs_v1_5_0),
    VersionToChangelogs("v1.4.0", R.string.changelogs_v1_4_0),
    VersionToChangelogs("v1.3.0", R.string.changelogs_v1_3_0),
    VersionToChangelogs("v1.2.0", R.string.changelogs_v1_2_0),
    VersionToChangelogs("v1.1.0", R.string.changelogs_v1_1_0),
    VersionToChangelogs("v1.0.0", R.string.changelogs_v1_0_0),
)
