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
import android.widget.TextView;

import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import moe.sui.R;
import moe.sui.ViewWindowActivity;

public class ViewPosition extends Fragment {

    private QMUITipDialog dialog;

    // PositionAdapter 内部类
    class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.ViewHolder> {
        private Context mContext;
        private List<Integer> mPosList;

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView posName;
            TextView posTime;
            TextView posNewNum;
            TextView posNowNum;

            ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;
                posName = view.findViewById(R.id.card_win_name);
                posTime = view.findViewById(R.id.card_win_time);
                posNewNum = view.findViewById(R.id.card_queue_num);
                posNowNum = view.findViewById(R.id.card_text_now_num);
            }
        }

        PositionAdapter(List<Integer> posList) {
            mPosList = posList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            if (mContext == null) {
                mContext = parent.getContext();
            }
            View view = LayoutInflater.from(mContext).inflate(R.layout.card_pos_info,parent,false);
            final ViewHolder viewHolder =  new ViewHolder(view);
            view.setOnClickListener(v -> {
                Integer integer = mPosList.get(viewHolder.getAdapterPosition());
                ViewWindowActivity.actionStart(getActivity(), integer);
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //根据List内容依次填入文本到RecyclerView中
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
        View view = inflater.inflate(R.layout.fragment_view_position, container, false);
        // 设置topBar
        QMUITopBar topBar = view.findViewById(R.id.topbar);
        topBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        // 设置RecyclerView
        List<Integer> productList = new ArrayList<>();
        productList.add(1); productList.add(1); productList.add(1);

        RecyclerView recyclerView = view.findViewById(R.id.pos_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new PositionAdapter(productList));

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