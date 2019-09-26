package akashsarkar188.proxynotes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<String> data = new ArrayList<>();
    Context context;
    SimpleExoPlayer player;
    boolean flag = true;

    public RecyclerAdapter(ArrayList<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        String url = data.get(position);

        initializePlayer(holder,url);
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
        player.setPlayWhenReady(true);
        holder.playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.playerView.setOnTouchListener(new OnSwipeTouchListener(context){
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                Log.e("SwipeDetected", "onSwipeLeft: ");
                context.startActivity(new Intent(context, Profile.class).putExtra("id",position));
                //Activity activity = (Activity) context;
                //activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                Log.e("SwipeDetected", "onSwipeRight: " );
                Toast.makeText(context, "Subscribed to video creator " + position, Toast.LENGTH_SHORT).show();
            }

            public void onClick(){
                Log.d("OnCLickPlayer", "onClick() returned: " + flag);
                if (flag) {
                    player.setPlayWhenReady(true);
                    flag = false;
                } else {
                    player.setPlayWhenReady(false);
                    flag = true;
                }
            }


            @Override
            public boolean onTouch(View v, MotionEvent event) {

                return super.onTouch(v, event);
            }

        });
    }

    private void initializePlayer(ViewHolder holder, String url) {
        player = ExoPlayerFactory.newSimpleInstance(context);
        holder.playerView.setPlayer(player);

        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        PlayerView playerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            playerView = itemView.findViewById(R.id.exoPlayer);
        }
    }
}
