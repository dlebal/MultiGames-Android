package es.dlebal.multigames.api.multigames.models

import com.google.gson.annotations.SerializedName

/**
 * Class data class MultiGamesCategory
 *
 * This class provides data MultiGamesCategory objects
 *
 * Properties:
 *   - id: Category identifier
 *   - name: Category name
 */
data class MultiGamesCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
