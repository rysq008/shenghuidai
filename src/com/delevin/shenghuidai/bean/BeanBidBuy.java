package com.delevin.shenghuidai.bean;

public class BeanBidBuy<T> extends BeanBase<T> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 131255679568215768L;

	public String type;// 优惠券类型（0红包，1加息券）
	public String bank_name;// 银行名称
	public String id_card;// 身份证号
	public String real_name;// 用户姓名
	public String bank_number;// 银行卡号
	public String rate_increase;// 浮动利率
	public String rate;// 利率
	public String product_name;// 产品名称
	public String time_limit;// 时间期限
	public String bank_address;// 银行icon
	public String product_remain;// 产品可投余额
	public String total_mount;// 产品可投总额
	public String day_amt;// 单日
	public String single_amt;// 单笔
	public String balance;// 用户帐户余额
	public String red_id;// 红包Id;
	public String red_money;// 红包金额
	public String red_rate;// 加息金额
	public String is_new_member;// 是否是新手
	public String money;// 未知

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getId_card() {
		return id_card;
	}

	public void setId_card(String id_card) {
		this.id_card = id_card;
	}

	public String getReal_name() {
		return real_name;
	}

	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}

	public String getBank_number() {
		return bank_number;
	}

	public void setBank_number(String bank_number) {
		this.bank_number = bank_number;
	}

	public String getRate_increase() {
		return rate_increase;
	}

	public void setRate_increase(String rate_increase) {
		this.rate_increase = rate_increase;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(String time_limit) {
		this.time_limit = time_limit;
	}

	public String getBank_address() {
		return bank_address;
	}

	public void setBank_address(String bank_address) {
		this.bank_address = bank_address;
	}

	public String getProduct_remain() {
		return product_remain;
	}

	public void setProduct_remain(String product_remain) {
		this.product_remain = product_remain;
	}

	public String getTotal_mount() {
		return total_mount;
	}

	public void setTotal_mount(String total_mount) {
		this.total_mount = total_mount;
	}

	public String getDay_amt() {
		return day_amt;
	}

	public void setDay_amt(String day_amt) {
		this.day_amt = day_amt;
	}

	public String getSingle_amt() {
		return single_amt;
	}

	public void setSingle_amt(String single_amt) {
		this.single_amt = single_amt;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getRed_id() {
		return red_id;
	}

	public void setRed_id(String red_id) {
		this.red_id = red_id;
	}

	public String getRed_money() {
		return red_money;
	}

	public void setRed_money(String red_money) {
		this.red_money = red_money;
	}

	public String getRed_rate() {
		return red_rate;
	}

	public void setRed_rate(String red_rate) {
		this.red_rate = red_rate;
	}

	public String getIs_new_member() {
		return is_new_member;
	}

	public void setIs_new_member(String is_new_member) {
		this.is_new_member = is_new_member;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

}
