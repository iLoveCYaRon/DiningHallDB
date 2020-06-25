package moe.sui.UIcontrol;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.qmuiteam.qmui.widget.popup.QMUIPopups;
import com.qmuiteam.qmui.widget.popup.QMUIQuickAction;
import com.raizlabs.android.dbflow.annotation.NotNull;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import moe.sui.R;

public class ViewWindow extends Fragment {

    private QMUITipDialog dialog;

    // PositionAdapter 内部类
    class WindowAdapter extends RecyclerView.Adapter<WindowAdapter.ViewHolder> {
        private Context mContext;
        private List<Integer> mPosList;

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView winName;
            TextView winTime;
            TextView queueNum;

            ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
//                productPhoto = view.findViewById(R.id.product_view);
//                title = view.findViewById(R.id.textViewTitle);
//                shortDescription = view.findViewById(R.id.textViewDesci);
//                price = view.findViewById(R.id.textViewPrice);
//                option = view.findViewById(R.id.buttonOption);
            }
        }

        WindowAdapter(List<Integer> posList) {
            mPosList = posList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.card_window_info,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//            Product product = mProductList.get(position);
//
//            // 设置卡片文本
//            holder.title.setText(product.getName());
//            holder.price.setText(product.getPrice());
//            String description =  product.getShort_description();
//            description = description.substring(3, description.length()-5);
//            holder.shortDescription.setText(description);

        }

        @Override
        public int getItemCount() {
            return mPosList.size();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_window, container, false);
        // 设置topBar
        QMUITopBar topBar = view.findViewById(R.id.topbar_queue);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // 设置RecyclerView
        List<Integer> productList = new ArrayList<>();
        productList.add(1); productList.add(1); productList.add(1);

        RecyclerView recyclerView = view.findViewById(R.id.queue_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new WindowAdapter(productList));

        // 设置加载对话框
        dialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();

        return view;
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 1:
//                    // 获取完数据，可以加载recyclerView
//                    RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.product_title_recycler_view);
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//                    recyclerView.setLayoutManager(linearLayoutManager);
//                    recyclerView.setAdapter(new ProductAdapter(SQLite.select().from(Product.class).queryList()));
//                    dialog.hide();
//                    break;
            }
        }
    };
}