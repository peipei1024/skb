package com.peixuze.ui;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 
 * https://git.oschina.net/steve/HoveringScroll.git
 * @author steve
 *
 */
public class HoveringScrollview extends ScrollView {

	private OnScrollListener onScrollListener;
	/**
	 * ��Ҫ�������û���ָ�뿪��view����view���ڼ���������������������Y�ľ��룬Ȼ�����Ƚ�
	 */
	private int lastScrollY;

	public HoveringScrollview(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	/**
	 * ���ù����ӿ�
	 * 
	 * @param onScrollListener
	 */
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}

	/**
	 * �����û���ָ�뿪MyScrollView��ʱ���ȡMyScrollView������Y���룬Ȼ��ص���onScroll������
	 */
	private Handler handler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			int scrollY = HoveringScrollview.this.getScrollY();

			// ��ʱ�ľ���ͼ�¼�µľ��벻��ȣ��ڸ�6�����handler������Ϣ?
			if (lastScrollY != scrollY) {
				lastScrollY = scrollY;
				handler.sendMessageDelayed(handler.obtainMessage(), 6);
			}
			if (onScrollListener != null) {
				onScrollListener.onScroll(scrollY);
			}

		};

	};

	/**
	 * ��дonTouchEvent�� ���û�������HoveringScrollview�����ʱ��
	 * ֱ�ӽ�HoveringScrollview������Y�������ص���onScroll�����У����û�̧���ֵ�ʱ��
	 * HoveringScrollview���ܻ��ڻ��������Ե��û�̧�������Ǹ�6�����handler������Ϣ����handler����
	 * HoveringScrollview�����ľ���
	 */
	public boolean onTouchEvent(MotionEvent ev) {
		if (onScrollListener != null) {
			onScrollListener.onScroll(lastScrollY = this.getScrollY());
		}
		switch (ev.getAction()) {
		case MotionEvent.ACTION_UP:
			handler.sendMessageDelayed(handler.obtainMessage(), 20);
			break;
		}
		return super.onTouchEvent(ev);
	};

	/**
	 * �����Ļص��ӿ�
	 */
	public interface OnScrollListener {
		/**
		 * �ص������� ���ر�view������Y�������
		 */
		public void onScroll(int scrollY);
	}

}