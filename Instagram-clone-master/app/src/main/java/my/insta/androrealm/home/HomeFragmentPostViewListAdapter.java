package my.insta.androrealm.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import my.insta.androrealm.R;
import my.insta.androrealm.Utils.UniversalImageLoader;
import my.insta.androrealm.models.Photo;

public class HomeFragmentPostViewListAdapter extends RecyclerView.Adapter<HomeFragmentPostViewListAdapter.ViewHolder> {

    private Context mContext;
    private List<Photo> mPhotos;

    public HomeFragmentPostViewListAdapter(Context context, List<Photo> photos) {
        mContext = context;
        mPhotos = photos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_home_post_viewer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Photo photo = mPhotos.get(position);

        holder.caption.setText(photo.getCaption());

        ImageLoader imageLoader = ImageLoader.getInstance();
        UniversalImageLoader.setImage(photo.getImage_Path(), holder.image, null, "");
    }

    @Override
    public int getItemCount() {
        return mPhotos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView caption;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            caption = itemView.findViewById(R.id.fragment_home_post_viewer_txt_caption);
            image = itemView.findViewById(R.id.fragment_home_post_viewer_post_image);
        }
    }
}
