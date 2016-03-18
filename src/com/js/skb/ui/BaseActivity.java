package com.js.skb.ui;

import java.util.ArrayList;
import java.util.List;

import com.js.skb.R;
import com.js.skb.ui.fragment.FenleiFragment;
import com.js.skb.ui.fragment.IndexFragment;
import com.js.skb.ui.fragment.MyFragment;
import com.js.skb.ui.fragment.StrollFragment;
import com.js.skb.ui.fragment.WorkFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BaseActivity extends FragmentActivity implements OnClickListener{
	private ViewPager viewPager;
	private List<Fragment> list;
	private LinearLayout llindex,llcircle,llstroll,llfenlei,llmy;
	private TextView tindex,tcircle,tstroll,tfenlei,tmy;
	private ImageView iindex,icircle,istroll,ifenlei,imy;
	
	static final int ORANGE = Color.parseColor("#fc8e35");
	private int currentPage = 0;// 初始化当前页为0（第一页）
	private int tabLineLength;// 1/5屏幕宽
	private ImageView tabline;
	private Animation left_in, left_out;
	private Animation right_in, right_out;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_base);
        initTabLine();
        initAnim();
        // 初始化界面
      initView();
   }
	 private void initTabLine() {
         // 获取显示屏信息
         Display display = getWindow().getWindowManager().getDefaultDisplay();
         // 得到显示屏宽度
         DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
         // 1/3屏幕宽度
         tabLineLength = metrics.widthPixels / 3;
         // 获取控件实例
        tabline = (ImageView) findViewById(R.id.tabline);
         // 控件参数
         LayoutParams lp = tabline.getLayoutParams();
        lp.width = tabLineLength;
         tabline.setLayoutParams(lp);
    }
	private void initAnim() {
		left_in = AnimationUtils.loadAnimation(this, R.anim.left_in);
		left_out = AnimationUtils.loadAnimation(this, R.anim.left_out);
		right_in = AnimationUtils.loadAnimation(this, R.anim.right_in);
		right_out = AnimationUtils.loadAnimation(this, R.anim.right_out);
	}
	private void initView() {
        // 实例化对象
       viewPager = (ViewPager) findViewById(R.id.viewpager);
   llindex = (LinearLayout) findViewById(R.id.index);
   llcircle = (LinearLayout) findViewById(R.id.circle);
   llstroll = (LinearLayout) findViewById(R.id.stroll);
   llfenlei = (LinearLayout) findViewById(R.id.fenlei);
   llmy = (LinearLayout) findViewById(R.id.my);
   llindex.setOnClickListener(this);
   llcircle.setOnClickListener(this);
   llstroll.setOnClickListener(this);
   llfenlei.setOnClickListener(this);
   llmy.setOnClickListener(this);
  
      tindex = (TextView) findViewById(R.id.text_index);
      tcircle = (TextView) findViewById(R.id.text_circle);
      tstroll = (TextView) findViewById(R.id.text_stroll);
      tfenlei = (TextView) findViewById(R.id.text_fenlei);
      tmy = (TextView) findViewById(R.id.text_my);
        
      iindex=(ImageView) findViewById(R.id.image_index);
      icircle=(ImageView) findViewById(R.id.image_circle);
      istroll=(ImageView) findViewById(R.id.image_stroll);
      ifenlei=(ImageView) findViewById(R.id.image_fenlei);
      imy=(ImageView) findViewById(R.id.image_my);
      

        // 设置数据源
        IndexFragment indexfragment = new IndexFragment();
        WorkFragment circlefragment = new WorkFragment();
        StrollFragment strollfragment = new StrollFragment();
        FenleiFragment fenleifragment = new FenleiFragment();
        MyFragment myfragment=new MyFragment();
        list = new ArrayList<Fragment>();

        
        list.add(indexfragment);
        list.add(circlefragment);
        list.add(strollfragment);
        list.add(fenleifragment);
        list.add(myfragment);

       // 设置适配器
        FragmentPagerAdapter adapter = new FragmentPagerAdapter(
                getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int arg0) {
                return list.get(arg0);
            }
        };

        // 绑定适配器
        viewPager.setAdapter(adapter);

        // 设置滑动监听
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

           @Override
           public void onPageSelected(int position) {
               // 当页面被选择时，先讲3个textview的字体颜色初始化成黑
               tindex.setTextColor(Color.WHITE);
               tcircle.setTextColor(Color.WHITE);
               tstroll.setTextColor(Color.WHITE);
               tfenlei.setTextColor(Color.WHITE);
               tmy.setTextColor(Color.WHITE);
               
               iindex.setImageResource(R.drawable.index_uncheck);
       		icircle.setImageResource(R.drawable.circle_uncheck);
       		ifenlei.setImageResource(R.drawable.fenlei_uncheck);
       		istroll.setImageResource(R.drawable.stroll_uncheck);
       		imy.setImageResource(R.drawable.my_uncheck);

               // 再改变当前选择页（position）对应的textview颜色
       		
               switch (position) {
              case 0:
                   tindex.setTextColor(ORANGE);
                   iindex.setImageResource(R.drawable.index_check);
                   break;
               case 1:
            	   tcircle.setTextColor(ORANGE);
                   icircle.setImageResource(R.drawable.circle_check);
                   break;
             case 2:
            	 tstroll.setTextColor(ORANGE);
                 istroll.setImageResource(R.drawable.stroll_check);
               break;
             case 3:
            	 tfenlei.setTextColor(ORANGE);
                 ifenlei.setImageResource(R.drawable.fenlei_check);
               break;
             case 4:
            	 tmy.setTextColor(ORANGE);
                 imy.setImageResource(R.drawable.my_check);
               break;
           }
            
              currentPage = position;

           }

       @Override
           public void onPageScrolled(int arg0, float arg1, int arg2) {
               Log.i("tuzi", arg0 + "," + arg1 + "," + arg2);
            // 取得该控件的实例
               LinearLayout.LayoutParams ll = (android.widget.LinearLayout.LayoutParams) tabline
                       .getLayoutParams();

               if (currentPage == 0 && arg0 == 0) { // 0->1移动(第一页到第二页)
                   ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                           * tabLineLength);
               } else if (currentPage == 1 && arg0 == 1) { // 1->2移动（第二页到第三页）
                  ll.leftMargin = (int) (currentPage * tabLineLength + arg1
                           * tabLineLength);
               } else if (currentPage == 1 && arg0 == 0) { // 1->0移动（第二页到第一页）
                  ll.leftMargin = (int) (currentPage * tabLineLength - ((1 - arg1) * tabLineLength));
              } else if (currentPage == 2 && arg0 == 1) { // 2->1移动（第三页到第二页）
                   ll.leftMargin = (int) (currentPage * tabLineLength - (1 - arg1)
                           * tabLineLength);
          }

           tabline.setLayoutParams(ll);
              
          

           

       }

           @Override
          public void onPageScrollStateChanged(int arg0) {
               // TODO Auto-generated method stub

         }
      });

  }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		 tindex.setTextColor(Color.WHITE);
         tcircle.setTextColor(Color.WHITE);
         tstroll.setTextColor(Color.WHITE);
         tfenlei.setTextColor(Color.WHITE);
         tmy.setTextColor(Color.WHITE);
         
         iindex.setImageResource(R.drawable.index_uncheck);
 		icircle.setImageResource(R.drawable.circle_uncheck);
 		ifenlei.setImageResource(R.drawable.fenlei_uncheck);
 		istroll.setImageResource(R.drawable.stroll_uncheck);
 		imy.setImageResource(R.drawable.my_uncheck);
 		 switch (v.getId()) {
         case R.id.index:
        	 
              tindex.setTextColor(ORANGE);
              iindex.setImageResource(R.drawable.index_check);
              viewPager.setCurrentItem(0);
              break;
          case R.id.circle:
       	   tcircle.setTextColor(ORANGE);
              icircle.setImageResource(R.drawable.circle_check);
              viewPager.setCurrentItem(1);
              break;
        case R.id.stroll:
       	 tstroll.setTextColor(ORANGE);
            istroll.setImageResource(R.drawable.stroll_check);
            viewPager.setCurrentItem(2);
          break;
        case R.id.fenlei:
       	 tfenlei.setTextColor(ORANGE);
            ifenlei.setImageResource(R.drawable.fenlei_check);
            viewPager.setCurrentItem(3);
          break;
        case R.id.my:
       	 tmy.setTextColor(ORANGE);
            imy.setImageResource(R.drawable.my_check);
            viewPager.setCurrentItem(4);
          break;
      }
	}
}

