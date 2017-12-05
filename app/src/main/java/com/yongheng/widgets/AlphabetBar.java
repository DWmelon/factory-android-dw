package com.yongheng.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.WrapperListAdapter;

import com.yongheng.R;
import com.yongheng.utils.Utils;


public class AlphabetBar extends View {

	private static final int FONT_SIZE_SP = 16;
	private static final int VERTICAL_MARGIN_PX = Utils.dpToPx(5);
	
	private ListView listView;
	private SectionIndexer sectionIndexter;
	private OnSectionChangedListener mSectionListener;
	
	private int mCurIdx;
	private int itemHeight;
	private int yOffset;

	Paint mPaint = new Paint();

	{
		mPaint.setColor(0xFF777777);
		mPaint.setAntiAlias(true);
		mPaint.setTextAlign(Paint.Align.CENTER);
	}

	public AlphabetBar(Context context) {
		super(context);
		init(context);
	}

	public AlphabetBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public AlphabetBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public void init(Context context) {
//		setBackgroundColor(0x00000000);
	}

	public int getCurIndex() {
		return mCurIdx;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		
		if(sectionIndexter == null)
			return false;
		
		String[] mSections = (String[]) sectionIndexter.getSections();
		int pos = (int) event.getY();
		mCurIdx = (pos - yOffset + VERTICAL_MARGIN_PX / 2) / itemHeight;
		
		if (mCurIdx >= mSections.length) {
			mCurIdx = mSections.length - 1;
		} else if (mCurIdx < 0) {
			mCurIdx = 0;
		}

		if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_MOVE) {
			setBackgroundResource(R.color.index_bar_bg);
			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) listView.getAdapter();
			}
			
			int position = sectionIndexter.getPositionForSection(mCurIdx);
			if (position != -1) {  
			    int p = position + shouldJumpCount - 1;
			    if(p==-1)  p= 0;
				listView.setSelection(p); // 额外跳转
			}

			if (mSectionListener != null) {
				mSectionListener.onSectionChanged(mCurIdx);
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
			setBackgroundColor(0x00FFFFFF);
			if (mSectionListener != null) {
				mSectionListener.onSectionCancel();
			}
		}
		return true;
	}

	private int shouldJumpCount;

	public void setShouldJump(int count) {
		shouldJumpCount = count;
	}

	public void setListView(ListView list) {
		listView = list;
		
		Adapter adapter = listView.getAdapter();
		if (adapter instanceof WrapperListAdapter) {
			sectionIndexter = (SectionIndexer) ((WrapperListAdapter) adapter).getWrappedAdapter();
		} else {
			sectionIndexter = (SectionIndexer) adapter;
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(sectionIndexter == null)
			return;
		String[] mSections = (String[]) sectionIndexter.getSections();
		float widthCenter = getMeasuredWidth() / 2;
		for (int i = 0; i < mSections.length; i++) {
			canvas.drawText(mSections[i], widthCenter, yOffset + ((i + 1) * itemHeight), mPaint);
		}

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(width,height);

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		int maxFontSize = (int) Utils.spToPt(FONT_SIZE_SP);
		itemHeight = maxFontSize + VERTICAL_MARGIN_PX;

		if(sectionIndexter == null)
			return;
		
		String[] mSections = (String[]) sectionIndexter.getSections();
		if(mSections == null || mSections.length == 0){
			return;
		}
		int viewHeight = (bottom - top);
		int maxItemHeight = viewHeight / mSections.length;

		if (itemHeight > maxItemHeight) {
			itemHeight = maxItemHeight;
			maxFontSize = itemHeight - VERTICAL_MARGIN_PX;
		}

		yOffset = (viewHeight - itemHeight * mSections.length) / 2;
		mPaint.setTextSize(maxFontSize);
	}
	
	public void setOnSectionChangedListener(OnSectionChangedListener l) {
		mSectionListener = l;
	}

	public static interface OnSectionChangedListener {
		void onSectionChanged(int section);
		void onSectionCancel();
	}

}