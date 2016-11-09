package co.experimentality.magicmuseum.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import co.experimentality.magicmuseum.R;

public class ObjectFragment extends Fragment {

    private TextView tvObjectName;
    private String objectId;
    private String objectDistance;
    private String objectNextId;
    private String objectDistance2;

    private ImageView ivPhoto;
    private TextView tvDistance;
    private TextView tvAuthor;

    private ImageView ivPhotoNext;
    private TextView tvObjectNameNext;
    private TextView tvDistanceNext;

    public ObjectFragment() {
        // Required empty public constructor
    }

    public ObjectFragment(String id, String distance) {
        this.objectId = id;
        this.objectDistance = distance;
    }

    public ObjectFragment(String id, String distance, String id2, String distance2) {
        this.objectId = id;
        this.objectDistance = distance;
        this.objectNextId = id2;
        this.objectDistance2 = distance2;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_object, container, false);
        // Inflate the layout for this fragment
        tvObjectName = (TextView) rootView.findViewById(R.id.tvName);
        ivPhoto = (ImageView) rootView.findViewById(R.id.ivPhoto);
        tvDistance = (TextView) rootView.findViewById(R.id.tvDistance);
        tvAuthor = (TextView) rootView.findViewById(R.id.tvAuthor);

        ivPhotoNext = (ImageView) rootView.findViewById(R.id.ivPhotoNext);
        tvObjectNameNext = (TextView) rootView.findViewById(R.id.tvNameNext);
        tvDistanceNext = (TextView) rootView.findViewById(R.id.tvDistanceNext);

        if (objectId != null && tvObjectName != null) {
            //tvObjectName.setText(objectId);
            tvDistance.setText(objectDistance);
            switch (objectId.toUpperCase()) {
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:4579:30200":
                    tvObjectName.setText("Acuadactus");
                    ivPhoto.setImageResource(R.drawable.uno);
                    tvAuthor.setText("Autor: Javier Roman");
                    break;
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:48827:22504":
                    tvObjectName.setText("Esculturacter");
                    ivPhoto.setImageResource(R.drawable.dos);
                    tvAuthor.setText("Autor: Gil Facman");
                    break;
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:648:12":
                    tvObjectName.setText("Chorractinuc");
                    ivPhoto.setImageResource(R.drawable.tres);
                    tvAuthor.setText("Autor: Rest Lomer");
                    break;
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:34234:23384":
                    tvObjectName.setText("Saminidos");
                    ivPhoto.setImageResource(R.drawable.cuatro);
                    tvAuthor.setText("Autor: Sam Hord");
                    break;
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:40899:23197":
                    tvObjectName.setText("Giuranda");
                    ivPhoto.setImageResource(R.drawable.cinco);
                    tvAuthor.setText("Autor: Halton Heign");
                    break;
                case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:19116:19151":
                    tvObjectName.setText("Farmato");
                    ivPhoto.setImageResource(R.drawable.seis);
                    tvAuthor.setText("Autor: Lucas Castrillon");
                    break;

                default:
                    ivPhoto.setImageResource(R.drawable.beacon);
                    break;
            }
            if (objectNextId != null) {
                tvDistanceNext.setText(objectDistance2);
                switch (objectNextId.toUpperCase()) {
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:4579:30200":
                        tvObjectNameNext.setText("Acuadactus");
                        ivPhotoNext.setImageResource(R.drawable.uno);
                        break;
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:48827:22504":
                        tvObjectNameNext.setText("Esculturacter");
                        ivPhotoNext.setImageResource(R.drawable.dos);
                        break;
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:648:12":
                        tvObjectNameNext.setText("Chorractinuc");
                        ivPhotoNext.setImageResource(R.drawable.tres);
                        break;
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:34234:23384":
                        tvObjectNameNext.setText("Saminidos");
                        ivPhotoNext.setImageResource(R.drawable.cuatro);
                        break;
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:40899:23197":
                        tvObjectNameNext.setText("Giuranda");
                        ivPhotoNext.setImageResource(R.drawable.cinco);
                        break;
                    case "B9407F30-F5F8-466E-AFF9-25556B57FE6D:19116:19151":
                        tvObjectNameNext.setText("Farmato");
                        ivPhotoNext.setImageResource(R.drawable.seis);
                        break;
                }
            }
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
