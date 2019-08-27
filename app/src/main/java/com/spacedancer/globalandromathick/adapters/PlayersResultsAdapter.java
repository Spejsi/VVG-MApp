package com.spacedancer.globalandromathick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.spacedancer.globalandromathick.R;
import com.spacedancer.globalandromathick.components.Player;
import com.spacedancer.globalandromathick.components.RecyclerPlayerItem;

import java.util.List;

public class PlayersResultsAdapter extends RecyclerView.Adapter<PlayersResultsAdapter.PlayersViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private List<Player> playersList;

    //getting the context and product list with constructor
    public PlayersResultsAdapter(Context mCtx, List<Player> playersList) {
        this.mCtx = mCtx;
        this.playersList = playersList;
    }

    @Override
    public PlayersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_player_result_items, null);
        return new PlayersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PlayersViewHolder holder, int position) {

        //getting the player of the specified position and create recycler item
        Player player = playersList.get(position);

        RecyclerPlayerItem rpi = new RecyclerPlayerItem(player);

        //binding the data with the viewholder views

        holder.playersRanking.setText(rpi.getPlayerRanking());
        holder.playersName.setText(rpi.getPlayerName());
        holder.playersScore.setText(rpi.getPlayerScore());
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    class PlayersViewHolder extends RecyclerView.ViewHolder {

        TextView playersRanking, playersName, playersScore;

        public PlayersViewHolder(View itemView) {
            super(itemView);

            playersRanking = itemView.findViewById(R.id.leaderboard_rank_TextView);
            playersName = itemView.findViewById(R.id.leaderboard_name_TextView);
            playersScore = itemView.findViewById(R.id.leaderboard_score_TextView);
        }
    }
}
