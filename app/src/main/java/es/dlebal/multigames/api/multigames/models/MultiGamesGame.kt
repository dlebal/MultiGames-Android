package es.dlebal.multigames.api.multigames.models

import com.google.gson.annotations.SerializedName

/**
 * Class data class MultiGamesGame
 *
 * This class provides data MultiGamesGame objects
 *
 * Properties:
 *   - id: Game identifier
 *   - name: Game name
 *   - description: Game description
 *   - thumbnail: Game thumbnail
 *   - screen1: Game screen 1
 *   - screen2: Game screen 2
 *   - screen3: Game screen 3
 *   - video: Game video
 *   - embedCode: Game embed code
 *   - orientation: Game orientation
 *   - provider: Game provider
 *   - categories: Game categories
 */
data class MultiGamesGame(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("screen_1")
    val screen1: String,
    @SerializedName("screen_2")
    val screen2: String,
    @SerializedName("screen_3")
    val screen3: String,
    @SerializedName("video")
    val video: String,
    @SerializedName("embed_code")
    val embedCode: String,
    @SerializedName("orientation")
    val orientation: String,
    @SerializedName("provider")
    val provider: String,
    @SerializedName("categories")
    val categories: List<Int>
)
