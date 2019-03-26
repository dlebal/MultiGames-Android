package es.dlebal.multigames.api.multigames.models

import com.google.gson.annotations.SerializedName

/**
 * Class data class MultiGamesData
 *
 * This class provides data MultiGamesData objects
 *
 * Properties:
 *   - categories: Categories
 *   - games: Games
 */
data class MultiGamesData(
    @SerializedName("categories")
    val categories: List<MultiGamesCategory>,
    @SerializedName("games")
    val games: List<MultiGamesGame>
)
