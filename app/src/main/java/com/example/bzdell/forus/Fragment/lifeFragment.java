package com.example.bzdell.forus.Fragment;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bzdell.forus.R;
import com.example.bzdell.forus.Utils.ACache;
import com.example.bzdell.forus.Utils.PullToRefreshLayoutforAutoMoreSwipe;
import com.example.bzdell.forus.Utils.PullableAndAutomoreSwipListView;
import com.example.bzdell.forus.Utils.Util0;
import com.example.bzdell.forus.life.MyAdapter;
import com.example.bzdell.forus.life.Notice;
import com.example.bzdell.forus.life.Noticedetail;
import com.example.bzdell.forus.life.newnotice;
import com.example.bzdell.forus.life.search;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Jack on 2017/8/22.
 */

//, AbsListView.OnScrollListener


public class lifeFragment extends ListFragment implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{

    private SharedPreferences sp;
    private Notice mnotice = new Notice();



    private TextView a, t;
    private FloatingActionButton b;
    private int width, height,i,x;

    private boolean isFirstIn = true;
    private PullToRefreshLayoutforAutoMoreSwipe ptrl;
    private PullableAndAutomoreSwipListView listview;
    private int PAGESIZE=20;

    private ACache aCache;

    BmobRealTimeData rtd = new BmobRealTimeData();  //数据实时更新

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_life, container, false);
        b = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);//button类的控件 需要先在onCreateView 中声明，不然会失效。Textview 例外  这是因为要先获取一个view，这样才能知道是那个布局中的控件。
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe2);
        ptrl = ((PullToRefreshLayoutforAutoMoreSwipe) view.findViewById(R.id.refresh_view));
        sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        listview = (PullableAndAutomoreSwipListView) view.findViewById(android.R.id.list);//看这里的findViewById！！！，他是Android.R.id.list。
        aCache=ACache.get(getActivity());

        ptrl.setListView(listview);
        ptrl.setOnRefreshListener(new MyListener());
//        swipeRefreshLayout.setEnabled(false);

//        a = (TextView) view.findViewById(R.id.network);



//        listview();
//        initData();

//        if (!checkNetwork()) {
//            a.setVisibility(View.VISIBLE);
//        } else {
//            a.setVisibility(View.GONE);
//        }

        DisplayMetrics dm = getResources().getDisplayMetrics();
        width = dm.widthPixels;
        height = dm.heightPixels;
        t = (TextView) view.findViewById(R.id.editText);
        t.setWidth(width - 70);

        return view;

    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        swipeRefreshLayout = (SwipeRefreshLayout) getActivity().findViewById(R.id.swipe2);

//        inits();  暂时关闭


        click();
        bendishuaxin();
//        initView();
        initview();

        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), search.class));
            }
        });

    }

    public static lifeFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("ARGS", content);
        lifeFragment fragment = new lifeFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    public void initData() {
//        swipeRefreshLayout.setOnRefreshListener(this);
//        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
//    }   //下拉刷新监视器

    @Override
    public void onRefresh() {
        BmobQuery<Notice> query = new BmobQuery<>();
        query.order("-createdAt");
        query.include("author[username]");
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> list, BmobException e) {
                if (e == null) {

                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();


                    for (Notice notice : list) {


                        HashMap<String, Object> tempHashMap = new HashMap<>();

                        tempHashMap.put("title", notice.getTitle());
                        tempHashMap.put("time", notice.getCreatedAt());
                        tempHashMap.put("content", notice.getContent());
                        tempHashMap.put("author", notice.author.getUsername());
                        tempHashMap.put("id",notice.getObjectId());
                        arrayList.add(tempHashMap);

                    }

                    MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                    setListAdapter(adapter);

                }
            }

        });



//        swipeRefreshLayout.setEnabled(true);
//        swipeRefreshLayout.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                swipeRefreshLayout.setRefreshing(false);
//                swipeRefreshLayout.setEnabled(false);
//
//            }
//        }, 2000);
    }    //下拉刷新功能

    boolean checkNetwork() {
        // 实例化ConnectivityManager
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        // 获得当前网络信息
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 判断是否连接
        if (info == null || !info.isConnected()) {
            return false;
        }
        return true;
    }   //检查网络

    public void initview() {
        b.setOnClickListener(this);
    }   //悬浮按钮

    @Override
    public void onClick(View v) {
        startActivity(new Intent(getActivity(), newnotice.class));
    }    //悬浮按钮

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if(checkNetwork()){

        l=listview;
        HashMap<String,String> map=(HashMap<String,String>)listview.getItemAtPosition(position);
        String ID =map.get("id");
        Intent intent =new Intent(getActivity(), Noticedetail.class);
        intent.putExtra("id",ID);
        startActivity(intent);}

        else{

           Util0.showToast(getActivity(),"当前无网络连接无法查看详情");
        }
    }   //Listview 点击事件

//    public void listview() {
//        listview.setOnScrollListener(this);
//    }       //防止listview 和 下拉刷新的冲突
//
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//    }     //防止listview 和 下拉刷新的冲突
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//        boolean enable = false;
//
//        if (listview != null && listview.getChildCount() > 0) {
//            boolean firstItemVisible = listview.getFirstVisiblePosition() == 0;
//            // check if the top of the first item is visible
//            boolean topOfFirstItemVisible = listview.getChildAt(0).getTop() == 0;
//            // enabling or disabling the refresh layout
//            enable = firstItemVisible && topOfFirstItemVisible;
//        }
//        swipeRefreshLayout.setEnabled(enable);
//
//
//    }     //防止listview 和 下拉刷新的冲突

    public void  bendishuaxin(){

//        BmobQuery<Notice> query = new BmobQuery<>();
//        query.order("-createdAt");
//        query.include("author[username]");
//        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));//
//        boolean isCache = query.hasCachedResult(Notice.class);
//        Log.d("bmob", " "+isCache);
//        query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ONLY);
//        query.findObjects(new FindListener<Notice>() {
//            @Override
//            public void done(List<Notice> list, BmobException e) {
//                if (e == null) {
//
//                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
//
//                    for (Notice notice : list) {
//
//                        HashMap<String, Object> tempHashMap = new HashMap<>();
//
//                        tempHashMap.put("title", notice.getTitle());
//                        tempHashMap.put("time", notice.getCreatedAt());
//                        tempHashMap.put("content", notice.getContent());
//                        tempHashMap.put("author", notice.author.getUsername());
//                        tempHashMap.put("id",notice.getObjectId());
//                        arrayList.add(tempHashMap);
//
//                    }

                    ArrayList<HashMap<String, Object>> arrayList = ( ArrayList<HashMap<String, Object>>)aCache.getAsObject("test");
                    if(arrayList!=null){
                    MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                    setListAdapter(adapter);
                    }


//                }
//            }
//
//        });


    }  //加载通知

    public void shuaxin()

    {
        BmobQuery<Notice> query = new BmobQuery<>();
        query.order("-createdAt");
        query.include("author[username]");
        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
        boolean isCache = query.hasCachedResult(Notice.class);
        Log.d("bmob", " "+isCache);
        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
        query.findObjects(new FindListener<Notice>() {
            @Override
            public void done(List<Notice> list, BmobException e) {
                if (e == null) {

                    ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();

                    for (Notice notice : list) {

                        HashMap<String, Object> tempHashMap = new HashMap<>();

                        tempHashMap.put("title", notice.getTitle());
                        tempHashMap.put("time", notice.getCreatedAt());
                        tempHashMap.put("content", notice.getContent());
                        tempHashMap.put("author", notice.author.getUsername());
                        tempHashMap.put("id",notice.getObjectId());
                        arrayList.add(tempHashMap);

                    }

                    MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                    setListAdapter(adapter);

                }
            }

        });


    }

    public void inits(){

        rtd.start(new ValueEventListener(){
            @Override
            public void onConnectCompleted(Exception e) {
                Log.d("bmob", "连接成功:"+rtd.isConnected());
//                rtd.subTableUpdate("Notice");       //  暂时因为功能冲突关闭 推送
//                rtd.unsubTableDelete("Notice");
            }

            @Override
            public void onDataChange(JSONObject jsonObject) {

                NotificationManager mNotificationManager =(NotificationManager)getActivity().getSystemService(NOTIFICATION_SERVICE);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());

                mBuilder.setContentTitle("测试标题")//设置通知栏标题
                        .setContentText("测试内容")
//	                    .setContentIntent(getActivity().getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图
//	                    .setNumber(number) //设置通知集合的数量
                        .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的
                        .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                        .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级
	                    .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消
                        .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                        .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合 Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
                        .setSmallIcon(R.drawable.appinfo);//设置通知小ICON
                mNotificationManager.notify(1, mBuilder.build());
                Log.d("bmob", "收到信息");
                shuaxin();
            }
        });


    }   //实时同步（暂时关闭）

//    private void initView() {
//        ArrayList<HashMap<String, Object>> arrayList = ( ArrayList<HashMap<String, Object>>)aCache.getAsObject("test");
//        MyAdapter adapter =new MyAdapter(getActivity(),arrayList);
//        adapter.setOnRightItemClickListener(new MyAdapter.onRightItemClickListener() {
//            @Override
//            public void onRightItemClick(View v, int position) {
//                Toast.makeText(getActivity(), "删除第  " + (position+1)+" 对话记录",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//    }

    class MyListener implements PullToRefreshLayoutforAutoMoreSwipe.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayoutforAutoMoreSwipe pullToRefreshLayout) {

            if (Util0.isNetUseable(getActivity()) ) {

//                // 下拉刷新操作
//                new AsyncTask<Integer, Void, Void>() {
//
//
//                    protected Void doInBackground(Integer... params) {
//
//                        return null;
//                    }
//
//                    @Override
//                    protected void onPostExecute(Void result) {
//
//                        Util0.showToast(getActivity(), "数据更新完成");
//                        // 完成后更新UI
//                        pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.SUCCEED);
//
//                    }
//
//                }.execute(200)

                if (sp.getInt("count", 0) == 0) {

                    BmobQuery<Notice> query = new BmobQuery<>();
                    query.order("-createdAt");
                    query.include("author[username]");
                    query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                    boolean isCache = query.hasCachedResult(Notice.class);
                    Log.d("bmob", " " + isCache);
                    query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                    query.findObjects(new FindListener<Notice>() {
                        @Override
                        public void done(List<Notice> list, BmobException e) {
                            if (e == null) {


                                ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
                                for (Notice notice : list) {
                                    HashMap<String, Object> tempHashMap = new HashMap<>();
                                    tempHashMap.put("title", notice.getTitle());
                                    tempHashMap.put("time", notice.getCreatedAt());
                                    tempHashMap.put("content", notice.getContent());
                                    tempHashMap.put("author", notice.author.getUsername());
                                    tempHashMap.put("id", notice.getObjectId());
                                    arrayList.add(tempHashMap);
                                }
                                aCache.put("test", arrayList);
                                MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                                setListAdapter(adapter);
                                sp.edit().putInt("count", arrayList.size()).commit();
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.SUCCEED);
                            } else
                                pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.FAIL);
                        }

                    });


                    }
                    else
                        {

                        BmobQuery<Notice> query0 = new BmobQuery<>();
                        query0.count(Notice.class, new CountListener() {
                            @Override
                            public void done(Integer count, BmobException e) {
                                if(e==null){
                                   i=count;

                                    if(i-sp.getInt("count", 0)-sp.getInt("count0",0)>0){
                                        BmobQuery<Notice> query = new BmobQuery<>();
                                        query.order("-createdAt");
                                        query.include("author[username]");
                                        query.setLimit(i-sp.getInt("count", 0)-sp.getInt("count0",0));
                                        query.setMaxCacheAge(TimeUnit.DAYS.toMillis(30));
                                        boolean isCache = query.hasCachedResult(Notice.class);
                                        Log.d("bmob", " " + isCache);
                                        query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);
                                        query.findObjects(new FindListener<Notice>() {
                                            @Override
                                            public void done(List<Notice> list, BmobException e) {
                                                if (e == null) {


                                                    ArrayList<HashMap<String, Object>> arrayList = (ArrayList<HashMap<String, Object>>) aCache.getAsObject("test");

                                                    for (Notice notice : list) {
                                                        HashMap<String, Object> tempHashMap = new HashMap<>();
                                                        tempHashMap.put("title", notice.getTitle());
                                                        tempHashMap.put("time", notice.getCreatedAt());
                                                        tempHashMap.put("content", notice.getContent());
                                                        tempHashMap.put("author", notice.author.getUsername());
                                                        tempHashMap.put("id", notice.getObjectId());
                                                        arrayList.add(0,tempHashMap);
                                                    }
                                                    aCache.put("test", arrayList);
                                                    MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                                                    setListAdapter(adapter);
                                                    sp.edit().putInt("count", arrayList.size()).commit();
                                                    pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.SUCCEED);
                                                }
                                                else   pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.FAIL);
                                            }

                                        });

                                    }

                                  else   pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.FAIL);


                                }else  pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.FAIL);
                            }
                        });



                        }
            }
            else
                pullToRefreshLayout.refreshFinish(PullToRefreshLayoutforAutoMoreSwipe.FAIL);


        }


        @Override
        public void onLoadMore(final PullToRefreshLayoutforAutoMoreSwipe pullToRefreshLayout) {

            if (Util0.isNetUseable(getActivity())) {

                // 加载操作
//                new AsyncTask<Integer, Void, Boolean>() {
//                    protected Boolean doInBackground(Integer... params) {
//                        // 网络同步读取数据（加载更多）
//
//
//
//                        return true;
//
//                    }
//
//                    @Override
//                    protected void onPostExecute(Boolean result) {
//
//                        if (result) {//如果数据不为空
//                            // 完成后更新UI
//
//                            listview.setNomore(1);//设置可以自动加载
//                        } else {
//                            listview.setNomore(2);//显示没有更多
//                        }
//                    }
//
//                }.execute(200);





         listview.setNomore(3);



            } else {
                listview.setNomore(0);

            }

        }
    }

    private void click() {

        ArrayList<HashMap<String, Object>> arrayList = (ArrayList<HashMap<String, Object>>) aCache.getAsObject("test");
        MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
        adapter.setOnRightItemClickListener(new MyAdapter.onRightItemClickListener() {
            @Override
            public void onRightItemClick(View v, int position) {
                sp = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                x = sp.getInt("count0", 0);
                ArrayList<HashMap<String, Object>> arrayList = (ArrayList<HashMap<String, Object>>) aCache.getAsObject("test");
                arrayList.remove(position - 1);
                aCache.put("test", arrayList);
                MyAdapter adapter = new MyAdapter(getActivity(), arrayList);
                setListAdapter(adapter);
                x++;
                sp.edit().putInt("count0", x).commit();

            }
        });

    }

}