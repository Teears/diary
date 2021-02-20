package qifang.li.menu;

/**
 * Author: Qifang Li
 * NewDate: 2019-10-20
 * Version: 1.0
 * Description: 生成四则运算验证码。
 * History:none
 */


import java.util.Random;

public class ArithmeticProducer {
	private String arithmeticStr;
	private int result;
	
	public ArithmeticProducer() {
		produce();
	}
	
	public String getArithmeticStr() {
		return arithmeticStr;
	}
	public int getResult() {
		return result;
	}
	
	public void produce() {
		Random random = new Random(System.currentTimeMillis());
		int a = Math.abs(random.nextInt()%10);
		int b = Math.abs(random.nextInt()%10);
		int c = Math.abs(random.nextInt()%10);
		int operator1 =  Math.abs(random.nextInt()%3)+1;
		int operator2 =  Math.abs(random.nextInt()%3)+1;
		String operatorStr1 = null;
		String operatorStr2 = null;
		int result = 0;
		if(operator2 == 3) {
				operatorStr2 = "*";
				result += b * c;
				switch(operator1) {
				case 1:
					operatorStr1 = "+";
					result += a;
					break;
				case 2:
					operatorStr1 = "-";
					result = a-result;
					break;
				case 3:
					operatorStr1 = "*";
					result *= a;
					break;	
				}
				
			}else {
			switch(operator1) {
			case 1:
				operatorStr1 = "+";
				result += a + b;
				break;
			case 2:
				operatorStr1 = "-";
				result += a - b;
				break;
			case 3:
				operatorStr1 = "*";
				result += a * b;
				break;	
			}
			switch(operator2) {
			case 1:
				operatorStr2 = "+";
				result += c;
				break;
			case 2:
				operatorStr2 = "-";
				result -= c;
				break;
			case 3:
				operatorStr2 = "*";
				result *= c;
				break;
			}
		}
		arithmeticStr = a + operatorStr1 + b + operatorStr2 + c + "=";
		this.result = result;
	}	
}
