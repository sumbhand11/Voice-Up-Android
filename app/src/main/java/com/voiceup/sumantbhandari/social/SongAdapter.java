package com.voiceup.sumantbhandari.social;

/**
 * Created by sumantbhandari on 12/24/16.
 */

//import com.squareup.picasso.Picasso;


public class SongAdapter {


    private int selectedPosition;

    public SongAdapter(int selectedPosition){

        this.selectedPosition =selectedPosition;
    }


    public interface RecyclerItemClickListener{
        void onClickListener(Song song, int position);
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }


}

