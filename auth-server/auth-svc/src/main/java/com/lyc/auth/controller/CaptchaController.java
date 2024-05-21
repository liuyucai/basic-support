package com.lyc.auth.controller;

import cn.hutool.captcha.*;
import cn.hutool.captcha.generator.MathGenerator;
import cn.hutool.captcha.generator.RandomGenerator;
import cn.hutool.core.math.Calculator;
import com.lyc.auth.dto.CaptchaDTO;
import com.lyc.auth.enums.ResponseCodeEnum;
import com.lyc.auth.util.CustomMathGenerator;
import com.lyc.common.vo.ResponseVO;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author: liuyucai
 * @Created: 2023/5/28 21:38
 * @Description: 验证码
 */
@Log4j2
@Api(value = "验证码API", tags = {"验证码CURD"})
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *
     * @param captchaType  验证码类型  MATH：四目运算，  RANDOM_NUMBER:随机数字  RANDOM_CHAR: 随机字符   默认
     * @param width   宽度
     * @param height   高度
     * @param codeCount  字符数量
     * @param lineType  干扰线类型：圆圈、线条   LINE:线条  默认 、SHEAR:扭曲、CIRCLE:圆圈
     * @param lineCount   干扰线数量
     * @param thickness   干扰线宽度
     * @return
     * @throws IOException
     */

    @GetMapping("getCode")
    public ResponseVO<CaptchaDTO> getCode(
            @RequestParam(value="captchaType",required = false,defaultValue = "RANDOM_CHAR") String captchaType,
            @RequestParam(value="width",required = false) Integer width,
            @RequestParam(value="height",required = false) Integer height,
            @RequestParam(value="codeCount",required = false) Integer codeCount,
            @RequestParam(value="lineType",required = false,defaultValue = "LINE") String lineType,
            @RequestParam(value="lineCount",required = false) Integer lineCount,
            @RequestParam(value="thickness",required = false) Integer thickness) throws IOException {

        //验证码类型：四目运算、随机字符、随机数字
        // https://doc.hutool.cn/pages/captcha/#%E8%87%AA%E5%AE%9A%E4%B9%89%E9%AA%8C%E8%AF%81%E7%A0%81
        try{
            CaptchaDTO captchaDTO = new CaptchaDTO();

            AbstractCaptcha captcha = null;

            width = width==null?200:width;

            height = height==null?100:height;

            codeCount = codeCount==null?4:codeCount;

            lineCount = lineCount==null?5:lineCount;

            thickness = thickness==null?4:thickness;


            if("CIRCLE".equals(lineType)){
                captcha = CaptchaUtil.createCircleCaptcha(width, height, codeCount, lineCount);
            }else if("SHEAR".equals(lineType)){
                captcha = CaptchaUtil.createShearCaptcha(width, height, codeCount,thickness);
            }else{
                captcha = CaptchaUtil.createLineCaptcha(width, height, codeCount, lineCount);
            }

            if("MATH".equals(captchaType)){
                // 自定义验证码内容为四则运算方式
                captcha.setGenerator(new CustomMathGenerator(1));
            }else if("RANDOM_NUMBER".equals(captchaType)){
              RandomGenerator randomGenerator = new RandomGenerator("123456789", codeCount);
                captcha.setGenerator(randomGenerator);
            }else{
                RandomGenerator randomGenerator = new RandomGenerator("123456789abcdefghijkmnqrstuyABCDEFGHJKLMNQRSTSY", codeCount);
                captcha.setGenerator(randomGenerator);
            }

            // 重新生成code
            captcha.createCode();

            String code = captcha.getCode();

            String uuid = UUID.randomUUID().toString();

            String base64 = captcha.getImageBase64();

            //如果是四则运算，要把计算的值存到redis
            if("MATH".equals(captchaType)){
                int calculateResult = (int)Calculator.conversion(code);
                String code1 = String.valueOf(calculateResult);
                //把验证码存到redis
                redisTemplate.opsForValue().set("captcha:"+uuid, code1,120, TimeUnit.SECONDS);

            }else{
                //把验证码存到redis
                redisTemplate.opsForValue().set("captcha:"+uuid, code,120, TimeUnit.SECONDS);
            }

//            redisTemplate.opsForValue().set("captcha:"+uuid, code,300, TimeUnit.SECONDS);

            //返回uuid,登录时携带uuid获取验证码校验
            captchaDTO.setUuid(uuid);
            captchaDTO.setBase64(base64);
            return ResponseVO.success(captchaDTO);
        }catch (Exception e){
            return ResponseVO.fail(ResponseCodeEnum.GET_CAPTCHA_ERROR.getCode(),ResponseCodeEnum.GET_CAPTCHA_ERROR.getDesc());
        }
    }
}
