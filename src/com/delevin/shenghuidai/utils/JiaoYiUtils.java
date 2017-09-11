package com.delevin.shenghuidai.utils;


import java.util.Timer;
import java.util.TimerTask;

import com.delevin.shenghuidai.activity.YiSetPayPasswordNextActivity;
import com.delevin.shenghuidai.interfaces.JianPanCallback;
import com.delevin.shenghuidai.view.CustomDialog;
import com.delevin.shenghuidai.view.PasswordInputView;
import com.yourenkeji.shenghuidai.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class JiaoYiUtils {
	
	private static Button btnGetCode;
	private static CountTimer countTimer;
	//设置交易密码
	public static void setPassword(final Context mContext,final JianPanCallback callBack){
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.item_jianpan, null);
		final PasswordInputView passwordInputView = (PasswordInputView) layout.findViewById(R.id.item_get_jiaoyipassword_passwordInputView);
		
		passwordInputView.setFocusable(true);  
		passwordInputView.setFocusableInTouchMode(true);  
		 passwordInputView.requestFocus(); 
		 Timer timer = new Timer();  
	        timer.schedule(new TimerTask() {  
	  
	            @Override  
	            public void run() {  
	                InputMethodManager imm = (InputMethodManager) mContext  
	                        .getSystemService(Context.INPUT_METHOD_SERVICE);  
	                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);  
	            }  
	  
	        }, 200);//延时自动弹起键盘
		
		final TextView tvWangji = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_wangjipassword);
		final TextView tvMoney = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_money);
		final RelativeLayout rlMoney = (RelativeLayout) layout.findViewById(R.id.item_get_jiaoyipassword_rl_money);
		final TextView tvFinish = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_finish);
		final TextView tvTitle = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_title);
		tvTitle.setText("设置交易密码");
		tvWangji.setVisibility(View.GONE);
		rlMoney.setVisibility(View.GONE);
		tvFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		 Window window = dialog.getWindow();  
//		 window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置  
		   passwordInputView.addTextChangedListener(new TextWatcher() {
	            @Override
	            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }

	            @Override
	            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }

	            @Override
	            public void afterTextChanged(Editable editable) {
	                final  String numString = editable.toString();
	                SharedPreferences sharedPreferences = mContext.getSharedPreferences("ljq", Context.MODE_PRIVATE);
	                String is = sharedPreferences.getString("is", null);//如果取不到值就取后面的""



	                if (is==null){
	                    if (numString.length() == 6) {
	                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
	                        editor.putString("password", passwordInputView.getText().toString());
	                        editor.putString("is","1");
	                        editor.commit();//提交修改
	                        passwordInputView.setText("");
	                        tvTitle.setText("请再次输入交易密码");
	                    }

	                }else if (is!=null&&is.equals("1")){
	                    if (numString.length() == 6) {
	                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
	                        editor.putString("password2", passwordInputView.getText().toString());
	                        editor.commit();//提交修改
	                        editor.remove("is");
	                        editor.commit();
	                        passwordInputView.setText("");
	                        
	                        String numbers = sharedPreferences.getString("password", "");//如果取不到值就取后面的""
		                     String  numbers2 = sharedPreferences.getString("password2", "");//如果取不到值就取后面的""
		                     if (numbers.equals(numbers2)){
//		                         Toast.makeText(mContext, "交易密码设置成功", Toast.LENGTH_SHORT).show();
		                         callBack.passWord(numbers,countTimer,dialog);
		                     }else{
		                         Toast.makeText(mContext, "两次输入不一致", Toast.LENGTH_SHORT).show();
		                         tvTitle.setText("设置交易密码");
		                     }
	                    }
	                }

	            }
	        });
//		btDis.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//			       SharedPreferences sp = mContext.getSharedPreferences("ljq", Context.MODE_PRIVATE);
//			     //取得user_id和手机号
//			                     String numbers = sp.getString("password", "");//如果取不到值就取后面的""
//			                     String  numbers2 = sp.getString("password2", "");//如果取不到值就取后面的""
//			                     if (numbers.equals(numbers2)){
//			                         Toast.makeText(mContext, "一致", Toast.LENGTH_SHORT).show();
//			                         callBack.getPassword(numbers2);
//			                     }else{
//			                         Toast.makeText(mContext, "两次输入不一致", Toast.LENGTH_SHORT).show();
//			                     }
//			                     
//				dialog.dismiss();
//				
//			}
//		});
//		dialog.setCancelable(false);
		dialog.show();
	}
	//输入交易密码
	public static void getPassword(final Context mContext,final JianPanCallback callBack){
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.item_jianpan, null);
		 final PasswordInputView passwordInputView = (PasswordInputView) layout.findViewById(R.id.item_get_jiaoyipassword_passwordInputView);
		 
		 passwordInputView.setFocusable(true);  
		 passwordInputView.setFocusableInTouchMode(true);  
		 passwordInputView.requestFocus(); 
		 Timer timer = new Timer();  
	        timer.schedule(new TimerTask() {  
	  
	            @Override  
	            public void run() {  
	                InputMethodManager imm = (InputMethodManager) mContext  
	                        .getSystemService(Context.INPUT_METHOD_SERVICE);  
	                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);  
	            }  
	  
	        }, 200);//延时自动弹起键盘
		 
		 final TextView tvMoney = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_money);
		 final TextView tvWangji = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_wangjipassword);
		 final TextView tvFinish = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_finish);
		 final TextView tvTitle = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_title);
		 final TextView tvStates = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_states);
		 tvTitle.setText("请输入交易密码");
		 dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
//		 Window window = dialog.getWindow();  
//		 window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
		 callBack.setText(tvStates, tvMoney);
		 tvWangji.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mContext.startActivity(new Intent(mContext, YiSetPayPasswordNextActivity.class));
			}
		});
		 tvFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		   passwordInputView.addTextChangedListener(new TextWatcher() {
	            @Override
	            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }
	            @Override
	            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }
	            @Override
	            public void afterTextChanged(Editable editable) {
	                final  String numString = editable.toString();
	                if(numString.length()==6){
	                	
	                	callBack.passWord(numString,countTimer,dialog);
	                	dialog.dismiss();
	                	
	                }else {
//						Toast.makeText(mContext, "密码为六位数字", Toast.LENGTH_SHORT).show();
					}
	             

	            }
	        });
//		dialog.setCancelable(false);
		dialog.show();
	}
	//通联验证码
public static void getPhoneCode(final Context mContext,final JianPanCallback callBack){
		
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// 实例化自定义主题的对话框
		final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
		View layout = inflater.inflate(R.layout.dialog_jiaoyi_phone_code, null);
		 final PasswordInputView passwordInputView = (PasswordInputView) layout.findViewById(R.id.dialog_jiaoyi_phone_code_passwordInputView);
		 ImageView imgFinish=(ImageView) layout.findViewById(R.id.item_get_jiaoyipassword_img_finish);
		 final TextView tvTitle = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_title);
			tvTitle.setText("请输入验证码");
		 passwordInputView.setFocusable(true);  
		 passwordInputView.setFocusableInTouchMode(true);  
		 passwordInputView.requestFocus(); 
		 Timer timer = new Timer();  
	        timer.schedule(new TimerTask() {  
	  
	            @Override  
	            public void run() {  
	                InputMethodManager imm = (InputMethodManager) mContext  
	                        .getSystemService(Context.INPUT_METHOD_SERVICE);  
	                imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);  
	            }  
	  
	        }, 200);//延时自动弹起键盘
		 
		 
		 final TextView tvPhone = (TextView) layout.findViewById(R.id.dialog_jiaoyi_phone_code_tv_phone);
		 btnGetCode = (Button) layout.findViewById(R.id.dialog_jiaoyi_phone_code_btn_getcode);
		 dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		 countTimer = new CountTimer(60000, 1000);
		 callBack.setPhone(btnGetCode, tvPhone,countTimer);
//		 countTimer.start();
		 imgFinish.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				countTimer.cancel();
			}
		});
		   passwordInputView.addTextChangedListener(new TextWatcher() {
	            @Override
	            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }
	            @Override
	            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

	            }
	            @Override
	            public void afterTextChanged(Editable editable) {
	                final  String numString = editable.toString();
	                if(numString.length()==4){
	                	
	                	callBack.passWord(numString,countTimer,dialog);
//	                	dialog.dismiss();
	                	
	                }else {
//						Toast.makeText(mContext, "密码为六位数字", Toast.LENGTH_SHORT).show();
					}
	             

	            }
	        });
		dialog.setCancelable(false);
		dialog.show();
	}
//富友验证码
public static CustomDialog getPhoneCode2(final Context mContext,final JianPanCallback callBack){
	
	LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// 实例化自定义主题的对话框
	final CustomDialog dialog = new CustomDialog(mContext,R.style.gesturesPassword_dialog);
	View layout = inflater.inflate(R.layout.dialog_jiaoyi_phone_code2, null);
	final PasswordInputView passwordInputView = (PasswordInputView) layout.findViewById(R.id.dialog_jiaoyi_phone_code_passwordInputView2);
	ImageView imgFinish=(ImageView) layout.findViewById(R.id.item_get_jiaoyipassword_img_finish2);
	final TextView tvTitle = (TextView) layout.findViewById(R.id.item_get_jiaoyipassword_tv_title2);
	tvTitle.setText("请输入验证码");
	passwordInputView.setFocusable(true);  
	passwordInputView.setFocusableInTouchMode(true);  
	passwordInputView.requestFocus(); 
	Timer timer = new Timer();  
	timer.schedule(new TimerTask() {  
		
		@Override  
		public void run() {  
			InputMethodManager imm = (InputMethodManager) mContext  
					.getSystemService(Context.INPUT_METHOD_SERVICE);  
			imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);  
		}  
		
	}, 200);//延时自动弹起键盘
	
	
	final TextView tvPhone = (TextView) layout.findViewById(R.id.dialog_jiaoyi_phone_code_tv_phone2);
	btnGetCode = (Button) layout.findViewById(R.id.dialog_jiaoyi_phone_code_btn_getcode2);
	dialog.addContentView(layout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
	countTimer = new CountTimer(60000, 1000);
	callBack.setPhone(btnGetCode, tvPhone,countTimer);
//		 countTimer.start();
	imgFinish.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			countTimer.cancel();
		}
	});
	passwordInputView.addTextChangedListener(new TextWatcher() {
		@Override
		public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			
		}
		@Override
		public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
			
		}
		@Override
		public void afterTextChanged(Editable editable) {
			final  String numString = editable.toString();
			if(numString.length()==6){
				
				callBack.passWord(numString,countTimer,dialog);
//				dialog.dismiss();
				
			}else {
//						Toast.makeText(mContext, "密码为六位数字", Toast.LENGTH_SHORT).show();
			}
			
			
		}
	});
	dialog.setCancelable(false);
	dialog.show();
	return dialog;
}
public static class CountTimer extends CountDownTimer {
    private SpannableStringBuilder sb;
    private ForegroundColorSpan colorSpan;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public CountTimer(long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        sb = new SpannableStringBuilder();
//        colorSpan = new ForegroundColorSpan(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
    }

    /**
     * 倒计时过程中调用
     * @param millisUntilFinished
     */
    @Override
    public void onTick(long millisUntilFinished) {
        Log.e("Tag", "millisUntilFinished=" + millisUntilFinished);
//        Log.e("Tag", "倒计时=" + (millisUntilFinished/1000));
        //处理后的倒计时数值
        int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
        //拼接要显示的字符串
        sb.clear(); //先把之前的字符串清除
        Log.e("Tag", "字符长度=" + sb.length());
        sb.append(String.valueOf(time));
        sb.append("s后重发");
        int index = String.valueOf(sb).indexOf("后");
        //给秒数和单位设置蓝色前景色
        sb.setSpan(colorSpan, 0, index, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        
				btnGetCode.setText(sb);
				//设置倒计时中的按钮外观
				btnGetCode.setClickable(false);//倒计时过程中将按钮设置为不可点击
				btnGetCode.setBackgroundColor(Color.parseColor("#c7c7c7"));
//        		btnCaptcha.setTextColor();
//				btnGetCode.setTextSize(16);
			
       
    }

    /**
     * 倒计时完成后调用
     */
    @Override
    public void onFinish() {
        Log.e("Tag", "倒计时完成");
       

				 //设置倒计时结束之后的按钮样式
//        		Color.parseColor("#808080")
        		btnGetCode.setBackgroundColor(Color.parseColor("#1b7dec"));
//		        btnCaptcha.setTextColor(ContextCompat.getColor(context, android.R.color.white));
//				btnGetCode.setTextSize(18);
				btnGetCode.setText("重新发送");
				btnGetCode.setClickable(true);
    }
}

	
}
