package edu.ztone.timenote.util;

import com.example.timenote.R;

import android.R.anim;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class AnimButtons extends RelativeLayout {

	private Context context;
	private int leftMargin = 0, bottomMargin = 0;
	private final int buttonWidth = 140;// 閸ュ墽澧栫�逛粙鐝�
	private final int r = 280;// 閸楀﹤绶�
	private final int maxTimeSpent = 200;// 閺堬拷闂�鍨З閻㈡槒锟芥妞�
	private final int minTimeSpent = 80;// 閺堬拷閻厼濮╅悽鏄忥拷妤佹
	private int intervalTimeSpent;// 濮ｅ繒娴夐柇锟�2娑擃亞娈戦弮鍫曟？闂傛挳娈�
	private Button[] btns;
	private Button btn_menu;
	private RelativeLayout.LayoutParams params;
	private boolean isOpen = false;// 閺勵垰鎯侀懣婊冨礋閹垫挸绱戦悩鑸碉拷锟�
	private float angle;// 濮ｅ繋閲滈幐澶愭尦娑斿妫块惃鍕仚鐟欙拷
	int i = 1;// 闁棙妞傞柦鍫熸鏉烆剚鐖ｈ箛锟�

	public int bottomMargins = this.getMeasuredHeight() - buttonWidth
			- bottomMargin;

	public AnimButtons(Context context) {
		super(context);
		this.context = context;
	}

	public AnimButtons(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		View view = LayoutInflater.from(context).inflate(
				R.layout.mian_layoutanim_buttons, this);

		initButtons(view);

	}

	private void initButtons(View view) {
		// 閸欘垯浜掗弽瑙勫祦閹稿鎸抽惃鍕嚋閺佹媽鍤滃鍗烆杻閸戯拷
		btns = new Button[3];
		btns[0] = (Button) view.findViewById(R.id.main_relayout_btn_video);
		btns[1] = (Button) view.findViewById(R.id.main_relayout_btn_camera);
		btns[2] = (Button) view.findViewById(R.id.main_relayout_btn_chat);
		//btns[3] = (Button) view.findViewById(R.id.main_relayout_btn_send);
		//btns[4] = (Button) view.findViewById(R.id.btn_thought);
		//btns[5] = (Button) view.findViewById(R.id.btn_sleep);
		btn_menu = (Button) view.findViewById(R.id.main_relayout_btn_menu);

		leftMargin = ((RelativeLayout.LayoutParams) (btn_menu.getLayoutParams())).leftMargin;
		bottomMargin = ((RelativeLayout.LayoutParams) (btn_menu
				.getLayoutParams())).bottomMargin;

		for (int i = 0; i < btns.length; i++) {
			// 閸掓繂顫愰崠鏍畱閺冭泛锟芥瑦瀵滈柦顕�鍣搁崥锟�
			btns[i].setLayoutParams(btn_menu.getLayoutParams());
			btns[i].setTag(String.valueOf(i));
			btns[i].setOnClickListener(clickListener);
		}

		intervalTimeSpent = (maxTimeSpent - minTimeSpent) / btns.length;
		angle = (float) Math.PI / (2 * (btns.length - 1));
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		bottomMargins = this.getMeasuredHeight() - buttonWidth - bottomMargin;
		btn_menu.setOnClickListener(new OnClickListener() {
			// btn_menu 閻愮懓鍤禍瀣╂
			@Override
			public void onClick(View v) {
				// 閻愮懓鍤い鎭掞拷渚�锟藉棙妞傞柦鍫熸鏉烇拷
				if (i % 2 != 0) {
					RotateAnimation ra = new RotateAnimation(90, 0,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					ra.setDuration(200);
					btn_menu.startAnimation(ra);
					i++;
				} else {
					RotateAnimation ra = new RotateAnimation(0, 90,
							Animation.RELATIVE_TO_SELF, 0.5f,
							Animation.RELATIVE_TO_SELF, 0.5f);
					ra.setDuration(200);
					btn_menu.startAnimation(ra);
					i++;
				}

				Log.i("rotate", "Rotate");
				if (!isOpen) {
					openMenu();
				} else {
					closeMenu();
				}
			}
		});

	}

	// 閸忔娊妫撮崝銊ф暰
	public void closeMenu() {
		if (isOpen == true) {
			isOpen = false;
			int w = this.getMeasuredWidth();
			int h = this.getMeasuredHeight();
			for (int i = 0; i < btns.length; i++) {
				float xLenth = (float) (r * Math.sin(i * angle));
				float yLenth = (float) (r * Math.cos(i * angle));
				btns[i].startAnimation(animTranslate(leftMargin + w
						- (int) xLenth - buttonWidth, bottomMargins
						- (int) yLenth, w, (int) (h - yLenth), btns[i],
						minTimeSpent + i * intervalTimeSpent));
				// btns[i].startAnimation(animTranslate(-xLenth, yLenth,
				// leftMargin, bottomMargins, btns[i], maxTimeSpent - i
				// * intervalTimeSpent));
				btns[i].setVisibility(View.INVISIBLE);
			}
		}
	}

	// 瀵拷婵濮╅悽锟�
	public void openMenu() {
		isOpen = true;
		int w = this.getMeasuredWidth();
		int h = this.getMeasuredHeight();
		Log.i("leftmargin", leftMargin + "");
		for (int i = 0; i < btns.length; i++) {
			float xLenth = (float) (r * Math.sin(i * angle));
			float yLenth = (float) (r * Math.cos(i * angle));
			Log.i("xlenth", xLenth + "");
			Log.i("ylenth", yLenth + "");
			btns[i].startAnimation(animTranslate(w, h - yLenth, leftMargin + w
					- (int) xLenth - buttonWidth, bottomMargins - (int) yLenth,
					btns[i], minTimeSpent + i * intervalTimeSpent));
			btns[i].setVisibility(View.VISIBLE);
		}

	}

	private Animation animScale(float toX, float toY) {
		Animation animation = new ScaleAnimation(1.0f, toX, 1.0f, toY,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setInterpolator(context,
				anim.accelerate_decelerate_interpolator);
		animation.setDuration(400);
		animation.setFillAfter(false);
		return animation;

	}

	private Animation animTranslate(float toX, float toY, final int lastX,
			final int lastY, final Button button, long durationMillis) {
		Animation animation = new TranslateAnimation(0, toX, 0, toY);
		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				params = new RelativeLayout.LayoutParams(0, 0);
				params.height = buttonWidth;
				params.width = buttonWidth;
				params.setMargins(lastX, lastY, 0, 0);
				button.setLayoutParams(params);
				button.clearAnimation();

			}
		});
		animation.setDuration(durationMillis);
		return animation;
	}

	View.OnClickListener clickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			int selectedItem = Integer.parseInt((String) v.getTag());

			for (int i = 0; i < btns.length; i++) {
				if (i == selectedItem) {

					btns[i].startAnimation(animScale(2.0f, 2.0f));
				} else {
					btns[i].startAnimation(animScale(0.0f, 0.0f));
				}
			}
			if (onButtonClickListener != null) {
				onButtonClickListener.onButtonClick(v, selectedItem);
			}
		}

	};

	public boolean isOpen() {
		return isOpen;
	}

	private OnButtonClickListener onButtonClickListener;

	public interface OnButtonClickListener {
		void onButtonClick(View v, int id);
	}

	public void setOnButtonClickListener(
			OnButtonClickListener onButtonClickListener) {
		this.onButtonClickListener = onButtonClickListener;
	}

}