package com.delevin.shenghuidai.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

/**
 *  @author 李红涛  @version 创建时间：2016-12-16 下午4:37:03    类说明
 */
public class BeanUrl {

	public static final String	HOST_SERVICE_PREFIX		= "https://";							// 前缀
	public static final String	HOST_SERVICE_URL		= "api.shenghuidai.com";				// 地址
	public static final String	HOST_SERVICE_PORT		= ":8012";								// 端口
	public static final String	HOST_SERVICE_SUFFIX		= "/v1/";								// 后缀
	public static String		HOST_SERVICE_ORIGINAL	= HOST_SERVICE_PREFIX
																.concat(HOST_SERVICE_URL).concat(HOST_SERVICE_PORT)
																.concat(HOST_SERVICE_SUFFIX);	// 拼接原始地址

	public static final void setHost(Context ct, String url) {
		SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(ct);
		String host = sp.getString("HOST", url);
		if (!TextUtils.isEmpty(url)) {
			sp.edit().putString("HOST", url).commit();
		} else {
			if (TextUtils.isEmpty(host)) {
				host = HOST_SERVICE_ORIGINAL;
				sp.edit().putString("HOST", host).commit();
			}
			url = host;
		}
		// Toast.makeText(ct, url, 0).show();
		HOST_SERVICE_ORIGINAL = url;
		URLZ = URLZB = HostUrl.URL = HOST_SERVICE_ORIGINAL;
	}

	// 测试
	public static String		HOST_TEST				= HOST_SERVICE_ORIGINAL;

	// public static final String URLZ = "https://api.boluolc.com:8012/v1/";
	public static String		URLZ					= HOST_SERVICE_ORIGINAL/* "https://api.shenghuidai.com:8012/v1/" */;
	// 备用
	// public static final String URLZB = "https://api.boluolicai.com:8012/v1/";
	public static String		URLZB					= HOST_SERVICE_ORIGINAL/* "https://api.shenghuidai.com:8012/v1/" */;

	// public static final String URLZ =
	// "http://apitest.boluolicai.com:8012/v1/";
	// // 备用
	// public static final String URLZB =
	// "http://apitest.boluolicai.com:8012/v1/";

	/** 1线上服务器14 **/
	public static final String	URL						= "";
	// public static final String URLZ = "https://api.boluolc.com:8014/v1/";
	/** 1线上服务器 16 **/
	// public static final String URL = "https://api.boluolc.com:8016/v1/";
	/** 1线上服务器 16 **/
	// public static final String URL = "http://www.boluolicai.com:8016/v1/";

	// 自动上标线下
	public static final String	CHANPIN_STRING			= "http://182.92.237.84:8080/jeecg/tjtyyBorrowController.do?getAutoRun&productid=";
	// 自动上标线下
	// public static final String CHANPIN_STRING =
	// "http://192.168.1.79:8080/nongfu/tjtyyBorrowController.do?getAutoRun&productid=";
	// // 自动上标线上
	// public static final String CHANPIN_STRING =
	// "http://api.boluolc.com:8081/jeecg/tjtyyBorrowController.do?getAutoRun&productid=";
	/** 53更新 **/
	/** 公告 **/
	public static final String	GONGGAO_STRING			= "news/media/all";
	/** 2投资首页 **/
	public static final String	TOUZI_STRING			= URL + "mobile/index";
	/** 2.1项目列表 **/
	public static final String	XIANGMU_STRING			= "product/all";
	/** 3安全保障 **/
	public static final String	SAFE_STRING				= URL + "html5/anquan";
	/** 4 新手帮助 **/
	public static final String	XINSHOU_HELP_STRING		= URL + "html5/newhelp";
	/** 5判断手机号是否注册 **/
	public static final String	LOGINORDENGLU_STRING	= URL
																+ "member/verify/%s/exist";
	/** 6获取短信验证码 **/
	public static final String	OBTPHONECODE_STRING		= URL + "verify/code/get";
	/** 7注册 **/
	public static final String	LOGIN_STRING			= URL + "member/register";
	/** 8登陆 **/
	public static final String	DENGLU_STRING			= URL + "member/login";
	/** 9充值 **/
	public static final String	PAY_STRING				= URL + "asset/recharge/%s/params";
	/** 10我的账户 **/
	public static final String	MY_STRING				= URL + "asset/%s/account/asset";
	/** 11产品详情 **/
	public static final String	PRODUCT_DETALS_STRING	= URL + "product/detail/";
	/** 12充值初始化 **/
	public static final String	PAY_INIT_STRING			= URL
																+ "member/%s/real/pay/info";
	/** 13提现初始化 **/
	public static final String	TIXIAN_INIT_STRING		= URL
																+ "asset/%s/account/detail";
	/** 14提现 **/
	public static final String	TIXIAN_STRING			= URL + "asset/fypay/draw/%s"/* "asset/%s/reflect" */;
	/** 15我的投资 **/
	public static final String	TOUZI_MINGXI_STRING		= URL + "product/%s/invest/";
	/** 16我的资金 **/
	public static final String	ZIJINMI_NGXI_STRING		= URL + "asset/%s/0/detail/";
	/** 17我的红包 **/
	public static final String	RED_PACKET_STRING		= URL + "asset/%s/red/pocket";
	/** 18购买界面初始化 **/
	public static final String	BIDBUYINIT_STRING		= URL + "asset/order/detail";
	/** 19购买产品 **/
	public static final String	BUY_BID_STRING			= URL + "asset/%s/order/submit?";
	/** 20提交购买金额时筛选可用红包 **/
	public static final String	SHAIXUAN_RED_STRING		= URL
																+ "asset/%s/account/red/pocket";
	/** 21返利记录 **/
	public static final String	Fanlijilu				= URL + "member/%s/invite/1/10/";
	/** 22邀请记录 **/
	public static final String	YaoqingJilu				= URL + "member/%s/invite/0/10/";
	/** 23发现页面——返利说明 **/
	public static final String	FanliShuoming			= URL + "asset/strategy";
	/** 24发现页面——每日签到 **/
	public static final String	MeiriQiandao			= URL + "activity/sign";
	/** 25发现页面——积分商城 **/
	public static final String	JifenShangcheng			= URL + "activity/store";
	/** 26发现页面——活动中心 **/
	public static final String	HuodongZhongxin			= URL + "activity/new/list";
	/** 27个人中心——判断是否设置密码 **/
	public static final String	SetPossword				= URL + "member/%s/pay/password";
	/** 28修改交易密码 */
	public static final String	XiugaiPayPassword		= URL
																+ "member/%s/modify/pay/passwd";
	/** 29 重置交易身份密码验证 **/
	public static final String	ShenfenIDCard			= URL
																+ "member/%s/verify/member/id";
	/** 30 获取验证码 **/
	public static final String	Yanzhengma				= URL + "verify/code/get";
	/** 31获取更多优质项目 **/
	public static final String	MOREOBJECT_STRING		= URL
																+ "product/type/page/all/";
	/** 32媒体报道更多 **/
	public static final String	MEITIBAODAO_STRING		= URL + "news/media/all";
	/** 33替换手机号 **/
	public static final String	newPhonePost			= URL + "member/%s/reset/phone";
	/** 35充值查看限额 **/
	public static final String	addPayChakanXiane		= URL + "asset/bank/xiane/";
	/** 36充值纪录 **/
	public static final String	ChongzhiJilu			= URL + "asset/%s/1/list/";
	/** 37提现规则 **/
	public static final String	TixianGuize				= URL + "asset/fetch";
	/** 38提现记录 **/
	public static final String	TixianJilu				= URL + "asset/%s/2/list/";
	/** 39委托协议 **/
	public static final String	WeituoXieyi				= URL
																+ "html5/letter_of_authorization/";
	/** 40银行卡信息 **/
	public static final String	BankCardInformation		= URL
																+ "member/%s/real/pay/info";
	// /**41 总收益**/
	// public static final String ZongShouyi = URL + "asset/%s/account/asset";
	/** 42 验证码提交 **/
	public static final String	YANZHENGMAPOST_STRING	= URL
																+ "verify/code/match";
	// /** 43重置交易密码 **/
	// public static final String BANKCHONGZHIMIMA_STRING = URL
	// + "member/%s/reset/pay/passwd";
	/** 43設置交易密码 **/
	public static final String	BANKCHONGZHIMIMA_STRING	= URL
																+ "asset/info/fybin/confirm/%s";

	/** 富有密碼驗證 **/
	public static final String	PAY_CONFIRM				= "asset/fyparams/confirm/%s";
	/** 44返利记录 **/
	// public static final String PDFSTRING_STRING =
	// "http://apitest.boluolc.com:8019/upload/pdf/test.pdf";
	/** 45是否绑定身份证 **/
	public static final String	ISIDBAND_STRING			= URL
																+ "member/bind/%s/bind/info";
	/** 46找回登录密码 **/
	public static final String	LOGINPASSWROD_STRING	= URL
																+ "member/reset/passwd";
	/** 47 签到规则 **/
	public static final String	QiandaoGuize			= URL + "activity/sign_rule";
	/** 48 发现- 邀请码 **/
	public static final String	yaoqingMa				= URL + "member/%s/info";
	/** 49 三八节 **/
	public static final String	THREEBALOVE_STRING		= URL
																+ "activity/women/index";
	/** 50 大富翁 **/
	public static final String	DAFUWEN_STRING			= URL + "activity/zill/index";
	/** 51分享**分享大富翁 */
	public static final String	SHARE_STRING			= URL + "activity/share/friend/";
	/** 52项目详情-合同 **/
	public static final String	PROJECTHETONG_STRING	= URL
																+ "product/borrow/demo";
	/** 54我的-更多-风险评估 **/
	public static final String	FENGXIANPINGGU_STRING	= URL
																+ "html5/risk_evaluation?phone=";
	/** 55更换银行卡说明 **/
	public static final String	ChangeBankcradExplain	= URL
																+ "activity/bank/jiebang/rules";
	/** 56邀请人 */
	public static final String	YAOQINGLIANJIE_STRING	= URL + "html5/invate/";

	/** 561邀请页 */
	public static final String	YAOQINHAOYOU_STRING		= URL
																+ "html5/invite?phone=%s&token=%s";

	/** 57委托书 */
	public static final String	WEITUOXIEYISHU_STRING	= URL
																+ "html5/letter_of_authorization/";
	/** 58支持银行列表 */
	public static final String	ZHICHIBANK_STRING		= URL + "asset/bank_support/";
	/** 59项目概况*1**投资信息*2**相关文件 *3 */
	public static final String	XIANGMUGAIKUANG_STRING	= URL
																+ "html5/investmentInfo/%s/";
	/** 60信息纰漏 */
	public static final String	MESSAGEPILOU_STRING		= URL + "html5/expose/info";
	/** 61排行榜 */
	public static final String	PAIHANGBANG_STRING		= URL + "html5/ranking";
	// 更新//http://www.boluolicai.com:8016/v1/verify/action/update/android
	public static final String	GENGXINS_STRING			= "verify/action/update/android";
	// http://www.boluolicai.com:8016/v1/verify/action/update/android
	// 签到规则
	public static final String	QIANDAOGUIZE_STRING		= URL + "activity/sign_rule";
	// 五一活动
	public static final String	WUYIACTION_STRING		= URL + "activity/labour";
	/** 17我的加息券 **/
	public static final String	JIAXIQUAN_STRING		= URL + "asset/%s/rate/pocket";
	/** 筛选加息券 **/
	public static final String	SHAIXUAN_STRING			= URL
																+ "asset/%s/account/rate/pocket";
	// 分享成功标记
	public static final String	SHARESUCCESS_STRING		= URL
																+ "activity/labour/invate?phone=";
	// 服务器维护中
	public static final String	SERIVCE_STRING			= URL + "html5/maintenance";
	// 公告列表
	public static final String	NOTICELIST_STRING		= URL + "news/notice/all";
	// 收货地址
	public static final String	ADRESS_STRING			= "message/address/upload/%s";
	// 发现页面图片
	public static final String	FAXIAN_BANNERS			= "mobile/discovery";

	public static final class HostUrl {
		// 测试服务器
		public static final String	TEST_URL					= "http://121.196.203.132";
		public static final String	HOST_URL					= "https://api.shenghuidai.com:8012/";
		public static String		URL							= HOST_SERVICE_ORIGINAL;

		// // 安全保障 /v1/safety
		// public static final String ANQUANBAOZHANG_HOST = URL + "/v1/safety";
		// 会员等级 /v1/huiyuandengji?phone=18519291259 phone:用户注册手机号码

		public static final String	HUIYUANDENGJI_HOST_FMT_1	= URL
																		+ "huiyuandengji?phone=%s";
		// 特权详情 /v1/tequanxiangqing
		public static final String	TEQUANXIANGQING_HOST		= URL
																		+ "tequanxiangqing";

		// 邀请好友 /v1/invite
		public static final String	YAOQINGHAOYOU_HOST			= URL + "invite";

		// 新手必读 /v1/xinshoubidu
		public static final String	XINSHOUBIDU_HOST			= URL + "xinshoubidu";
		// 派红包 /v1/new_hand_red
		public static final String	PAIHONGBAO_HOST				= URL + "new_hand_red";
		// 关于我们 /v1/about_our
		public static final String	GUANYUWOMENG_HOST			= URL + "about_our";
		// 投资攻略 /v1/html5/raiders
		public static final String	TOUZIGONGLUE_HOST			= URL + "html5/raiders";

		// // 活动列表 /v1/activity/lists?phone=18519291259 phone:用户注册手机号码
		// public static final String HUODONGLIEBIAO_HOST_FMT_1 = URL
		// + "/v1/activity/lists?phone=%s";

		// 大转盘 /v1/activity/dzp?phone=18519291259 phone:用户注册手机号码
		public static final String	DAZHUANPAN_HOST_FMT_1		= URL + "activity/dzp?phone=%s";

		public static final String	DAZHUANPAN_FAXIAN_HOST		= URL + "activity/zill/index";

		// // 积分商城 /v1/shop?phone=18519291259 phone:用户注册手机号码
		// public static final String JIFENSHANGCHENG_FMT_1 = URL
		// + "/v1/shop?phone=%s";

		// 积分领取兑换记录 /v1/get_record?phone=18519291259 phone:用户注册手机号码
		public static final String	JIFENDUIHUANJILU_FMT_1		= URL
																		+ "get_record?phone=%s";
		// 无积分兑换记录 /v1/no_record?phone=18519291259 phone:用户注册手机号码
		public static final String	WUJIFENDUIHUANJILI_FMT_1	= URL
																		+ "no_record?phone=%s";

	}
}
