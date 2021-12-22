package com.openxsl.studycloud.feign.invoker;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.openxsl.studycloud.feign.invoker.encrypt.FeignInvokerEncryptor;
import com.openxsl.studycloud.feign.invoker.encrypt.HexEncoder;

/**
 * 服务端拦截器：校验token
 * @author xiongsl
 */
@Component
@ConditionalOnBean(SystemTokenDefaultLoader.class)
@WebFilter(filterName="TokenInterceptor", urlPatterns = "/*")
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
		long start = System.nanoTime();
		String[] arrays = authors.split(":");
		arrays[0] = HexEncoder.decode(arrays[0]);
		String orignToken = service.getSystemToken(arrays[0]);
		try {
		return FeignInvokerEncryptor.validateToken(arrays, orignToken);
		}finally {
			System.out.println("spend==============="+(System.nanoTime()-start));
		}
	}

}
