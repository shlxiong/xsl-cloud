package com.openxsl.studycloud.nacos.invoker;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openxsl.studycloud.nacos.invoker.encrypt.FeignInvokerEncryptor;
import com.openxsl.studycloud.nacos.invoker.encrypt.HexEncoder;

/**
 * @author xiongsl
 */
@Component
@WebFilter(filterName="TokenInterceptor", urlPatterns = "/config/*")
public class RpcTokenInterceptor implements Filter{
	@Autowired
	private SystemTokenDefaultLoader service;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {
		HttpServletRequest httpReq = ((HttpServletRequest)request);
		if ("feign".equals(httpReq.getHeader("accept-origin"))){
			String authors = httpReq.getHeader("Authorization");
			if (authors==null || !this.validate(authors)) {
				throw new ServletException("token error!");
			}
		}
		chain.doFilter(request, response);
	}
	
	private boolean validate(String authors) {
		String[] arrays = authors.split(":");
		arrays[0] = HexEncoder.decode(arrays[0]);
		String orignToken = service.getSystemToken(arrays[0]);
		return FeignInvokerEncryptor.validateToken(arrays, orignToken);
	}

}
