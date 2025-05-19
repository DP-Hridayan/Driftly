package `in`.hridayan.driftly.core.common.constants

object SeedColors {
    const val BLUE = 0xFF0061A4.toInt()
    const val INDIGO = 0xFF535A92.toInt()
    const val PURPLE = 0xFF794A99.toInt()
    const val PINK = 0xFF94416F.toInt()
    const val RED = 0xFFFF3B30.toInt()
    const val BROWN = 0xFF904B3F.toInt()
    const val ORANGE = 0xFF855318.toInt()
    const val YELLOW = 0xFF785A0B.toInt()
    const val TEAL = 0xFF666014.toInt()
    const val GREEN = 0xFF1E6B4F.toInt()
}

object SeedColorProvider {
    var seedColor: Int = SeedColors.TEAL
}
