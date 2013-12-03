/*
 * 
 *	Copyright © 2013 Changsha Shishuo Network Technology Co., Ltd. All rights reserved.
 *	长沙市师说网络科技有限公司 版权所有
 *	http://www.shishuo.com
 *
 *	Licensed under the Apache License, Version 2.0 (the "License");
 *	you may not use this file except in compliance with the License.
 *	You may obtain a copy of the License at
 *	 
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 *	Unless required by applicable law or agreed to in writing, software
 *	distributed under the License is distributed on an "AS IS" BASIS,
 *	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *	See the License for the specific language governing permissions and
 *	limitations under the License.
 */
package com.shishuo.cms.action;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.shishuo.cms.entity.vo.JsonVo;
import com.shishuo.cms.service.AdminService;

/**
 * @author Herbert
 * 
 */

@Controller
@RequestMapping("/auth")
public class AuthAction extends BaseAction {

	/**
	 * Kaptcha 验证码
	 */
	@Autowired
	private DefaultKaptcha captchaProducer;

	@Autowired
	private AdminService adminService;

	@RequestMapping(value = "admin/login", method = RequestMethod.GET)
	public String admin(HttpServletRequest request, ModelMap modelMap) {
		return "system/login";
	}

	@ResponseBody
	@RequestMapping(value = "admin/login.json", method = RequestMethod.POST)
	public JsonVo<String> adminLogin(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "captcha") String captcha,
			HttpServletRequest request, ModelMap modelMap) {
		JsonVo<String> json = new JsonVo<String>();

		try {
			String kaptcha = (String) request.getSession().getAttribute(
					com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (!EmailValidator.getInstance().isValid(email)) {
				json.getErrors().put("email", "邮件格式错误");
			}
			if (StringUtils.isBlank(password)) {
				json.getErrors().put("password", "密码不能为空");
			} else if (password.length() < 6 && password.length() > 30) {
				json.getErrors().put("password", "密码最少6个字符，最多30个字符");
			}
			// 校验验证码
			if (StringUtils.isNotBlank(kaptcha)
					&& kaptcha.equalsIgnoreCase(captcha)) {

			} else {
				json.getErrors().put("captcha", "验证码错误");
			}

			this.validate(json);

			adminService.adminLogin(email, password, request);

		} catch (Exception e) {
//			logger.
		}
		return json;
	}

	/**
	 * 生成验证码
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "captcha", method = RequestMethod.GET)
	public void captcha(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control",
				"no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}
}
