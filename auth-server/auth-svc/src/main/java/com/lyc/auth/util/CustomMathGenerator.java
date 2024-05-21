package com.lyc.auth.util;

import cn.hutool.captcha.generator.CodeGenerator;
import cn.hutool.core.math.Calculator;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author: liuyucai
 * @Created: 2023/11/12 17:33
 * @Description:
 */
public class CustomMathGenerator implements CodeGenerator {

    private static final String operators = "+-*";
    private final int numberLength;

    public CustomMathGenerator() {
        this(2);
    }

    public CustomMathGenerator(int numberLength) {
        this.numberLength = numberLength;
    }

    @Override
    public String generate() {
        int limit = this.getLimit();

        int int1 = RandomUtil.randomInt(limit);

        int int2 = RandomUtil.randomInt(limit);

        char c = RandomUtil.randomChar("+-*");

        if(c == '-'){
            if(int1<int2){
               int temp = int1;
               int1 = int2;
               int2 = temp;
            }
        }
        String number1 = Integer.toString(int1);
        String number2 = Integer.toString(int2);
        number1 = StrUtil.padAfter(number1, this.numberLength, ' ');
        number2 = StrUtil.padAfter(number2, this.numberLength, ' ');
        return StrUtil.builder().append(number1).append(RandomUtil.randomChar("+-*")).append(number2).append('=').toString();
    }

    @Override
    public boolean verify(String code, String userInputCode) {
        int result;
        try {
            result = Integer.parseInt(userInputCode);
        } catch (NumberFormatException var5) {
            return false;
        }

        int calculateResult = (int) Calculator.conversion(code);
        return result == calculateResult;
    }

    public int getLength() {
        return this.numberLength * 2 + 2;
    }

    private int getLimit() {
        return Integer.parseInt("1" + StrUtil.repeat('0', this.numberLength));
    }
}
