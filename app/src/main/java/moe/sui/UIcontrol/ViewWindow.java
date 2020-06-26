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
import moe.sui.datastruct.LineTraffic;
import moe.sui.dbcontrol.GetTrafficMonitor;

public class ViewWindow extends Fragment {

    private QMUITipDialog dialog;
    private List<LineTraffic> mWinList;

    // PositionAdapter 内部类
    class WindowAdapter extends RecyclerView.Adapter<WindowAdapter.ViewHolder> {
        private Context mContext;
        private List<LineTraffic> mWinList;

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView cardView;
            TextView winName;
            TextView winTime;
            TextView queueNum;

            ViewHolder(View view) {
                super(view);
                cardView = (CardView) view;

                winName = view.findViewById(R.id.card_win_name);
                winTime = view.findViewById(R.id.card_win_time);
                queueNum = view.findViewById(R.id.card_queue_num);
            }
        }

        WindowAdapter(List<LineTraffic> winList) {
            mWinList = winList;
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
            LineTraffic traffic = mWinList.get(position);

            // 设置卡片文本
            holder.winName.setText(traffic.winName);
            holder.winTime.setText(traffic.updateTime.substring(11,traffic.updateTime.indexOf(".")));
            holder.queueNum.setText(String.valueOf(traffic.currentNum));
        }

        @Override
        public int getItemCount() {
            return mWinList.size();
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_window, container, false);
        // 设置topBar
        QMUITopBar topBar = view.findViewById(R.id.topbar_queue);
        topBar.addLeftBackImageButton().setOnClickListener(v -> getActivity().onBackPressed());
        dialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        dialog.show();
        return view;
    }


    public void refresh(int posId) {
        new Thread(() -> {
            try {
                List<LineTraffic> trafficList = GetTrafficMonitor.getTrafficLining(posId);
                mWinList = GetTrafficMonitor.getTrafficLining(posId);
                Message msg = new Message(); msg.what = 1;handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        // 设置加载对话框
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
                    RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.queue_recycler_view);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(new ViewWindow.WindowAdapter(mWinList));
                    dialog.hide();
                    break;
            }
        }
    };
}