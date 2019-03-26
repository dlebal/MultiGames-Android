package es.dlebal.multigames.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import es.dlebal.multigames.R
import es.dlebal.multigames.models.Game
import java.util.*

/**
 * Class class GameAdapter(games: ArrayList<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>()
 *
 * This class provides GameAdapter objects
 *
 * Properties:
 *   - games: Games
 */
class GameAdapter(private val games: ArrayList<Game>, private val listener : Listener) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    /**
     * interface
     */
    interface Listener {
        fun onItemClick(game: Game)
    }

    /**
     * Variables
     */
    private val className: String = GameAdapter::class.java.simpleName // Class name

    /**
     * Method override fun getItemCount(): Int
     *
     * Performs the following tasks:
     *   - Returns the total number of game items in the data set held by the adapter
     *
     * Parameters:
     *   @return this.games.size: The total number of game items in this adapter
     */
    override fun getItemCount(): Int {

        // Variables
        val methodName = "getItemCount" // Method name

        // Returns the total number of game items in the data set held by the adapter
        Log.d(this.className + ": $methodName", "Returns the total number of game items in the data set held by the adapter")
        return this.games.size

    }

    /**
     * Method override fun getItemId(position: Int): Long
     *
     * Performs the following tasks:
     *   - Return the stable identifier for the game item at position
     *
     * Parameters:
     *   @param position: Adapter position to query
     *   @return position.toLong(): The stable identifier of the game item at position
     */
    override fun getItemId(position: Int): Long {

        // Variables
        val methodName = "getItemId" // Method name

        // Return the stable identifier for the game item at position
        Log.d(this.className + ": $methodName", "Return the stable identifier for the game item at position")
        return position.toLong()

    }

    /**
     * Method override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder
     *
     * Performs the following tasks:
     *   - Return a new GameViewHolder that holds a view of the given view type
     *
     * Parameters:
     *   @param parent: The ViewGroup into which the new view will be added after it is bound to an adapter position
     *   @param viewType: The view type of the new view
     *   @return GameViewHolder(inflater, parent): A new GameViewHolder that holds a view of the given view type
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {

        // Variables
        val methodName = "onCreateViewHolder" // Method name

        // Return a new GameViewHolder that holds a view of the given view type
        Log.d(this.className + ": $methodName", "Return a new GameViewHolder that holds a view of the given view type")
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent)

    }

    /**
     * Method override fun onBindViewHolder(holder: GameViewHolder, position: Int)
     *
     * Performs the following tasks:
     *   - Display the data at the specified position
     *
     * Parameters:
     *   @param holder: The GameViewHolder which should be updated to represent the contents of the item at the given position in the data set
     *   @param position: The position of the game item within the adapter's data set
     */
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {

        // Variables
        val methodName = "onBindViewHolder" // Method name

        // Display the data at the specified position
        Log.d(this.className + ": $methodName", "Display the data at the specified position")
        val game: Game = this.games[position]
        holder.bind(game, listener)

    }

    /**
     * Class class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_game, parent, false))
     *
     * This class provides GameViewHolder objects
     *
     * Properties:
     *   - inflater: The view inflate from a specified xml resource
     *   - parent: The parent that this view will eventually be attached to
     */
    class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_game, parent, false)) {

        /**
         * Variables
         */
        private val className: String = GameAdapter::class.java.simpleName // Class name
        private var thumbnail: ImageView? = null // Game thumbnail
        private var title: TextView? = null // Game title

        /**
         * init
         */
        init {
            thumbnail = itemView.findViewById(R.id.thumbnail)
            title = itemView.findViewById(R.id.title)
        }

        /**
         * Method fun bind(game: Game)
         *
         * Performs the following tasks:
         *   - Bind a game
         *
         * Parameters:
         *   @param game: Game
         *   @param listener: Listener
         */
        fun bind(game: Game, listener: Listener) {

            // Variables
            val methodName = "bind" // Method name

            // Bind a game
            Log.d(this.className + ": $methodName", "Bind a game")
            Picasso.get().load(game.thumbnail).into(thumbnail)
            title?.text = game.name
            itemView.setOnClickListener {
                listener.onItemClick(game)
            }

        }

    }

}
