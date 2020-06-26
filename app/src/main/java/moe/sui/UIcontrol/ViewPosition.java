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
import java.util.Objects;

import moe.sui.R;
import moe.sui.ViewWindowActivity;
import moe.sui.datastruct.DineTraffic;
import moe.sui.dbcontrol.GetTrafficMonitor;
import moe.sui.ds.Position;
import moe.sui.timecalculate.TimeCalculate;

public class ViewPosition extends Fragment {

    private QMUITipDialog dialog;

    private List<DineTraffic> mPosList;

    // PositionAdapter 内部类
    class PositionAdapter extends RecyclerView.Adapter<PositionAdapter.ViewHolder> {
        private Context mContext;
        private List<DineTraffic> mPosList;

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

        PositionAdapter(List<DineTraffic> posList) {
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
                DineTraffic traffic = mPosList.get(viewHolder.getAdapterPosition());
                ViewWindowActivity.actionStart(getActivity(), traffic.posId);
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //根据List内容依次填入文本到RecyclerView中
            DineTraffic traffic = mPosList.get(position);

            // 设置卡片文本
            holder.posName.setText(traffic.posName);
            holder.posNewNum.setText(String.valueOf(traffic.increaseNum));
            holder.posNowNum.setText(String.valueOf(traffic.currentNum));
            holder.posTime.setText(traffic.updateTime.substring(11,traffic.updateTime.indexOf(".")));
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
        topBar.addLeftBackImageButton().setOnClickListener(v -> getActivity().onBackPressed());

        new Thread(() -> {
            try {
                mPosList = GetTrafficMonitor.getTrafficDining();
                Message msg = new Message(); msg.what = 1;handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // 设置加载对话框
        dialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        dialog.show();
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
                    // 获取完数据，可以加载recyclerView
                    RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.pos_recycler_view);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(new PositionAdapter(mPosList));
                    dialog.hide();
                    break;
            }
        }
    };
}