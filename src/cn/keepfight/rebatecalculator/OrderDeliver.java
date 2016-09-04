package cn.keepfight.rebatecalculator;

public interface OrderDeliver {

	void regitsterDelivee(OrderDelivee delivee);

	/**
	 * 获得提成总额
	 * 
	 * @return 返回提成总额
	 */
	double getRebateTotal();

	/**
	 * 申请数量
	 * 
	 * @param subNum
	 *            申请数量
	 * @param price
	 *            申请的单价
	 * @return 成功返回-1，失败返回最大可申请的数
	 */
	int applyRebateSub(OrderDelivee delivee, int subNum,
			double price);

	/**
	 * 获得提成剩余
	 * 
	 * @return 提成剩余
	 */
	double getRebateRemainder();

	/**
	 * 更新余额计算
	 */
	void updateRebate();
}
