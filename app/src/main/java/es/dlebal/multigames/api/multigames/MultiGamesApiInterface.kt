package es.dlebal.multigames.api.multigames

import es.dlebal.multigames.api.multigames.models.MultiGamesData
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Interface interface MultiGamesApiInterface
 *
 * This interface provides a MultiGamesApiInterface collection of abstract methods
 */
interface MultiGamesApiInterface {

    // Get data
    @GET("games.json")
    fun getData() : Observable<MultiGamesData>

}
