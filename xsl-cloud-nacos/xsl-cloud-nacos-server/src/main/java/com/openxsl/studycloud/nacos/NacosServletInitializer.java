package com.openxsl.studycloud.nacos;

import java.lang.management.ManagementFactory;
import java.util.Set;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.alibaba.cloud.nacos.registry.NacosAutoServiceRegistration;

@SpringBootApplication
public class NacosServletInitializer extends SpringBootServletInitializer
			/*implements ApplicationRunner*/{
	
	@Autowired(required = false)
    private NacosAutoServiceRegistration registration;
	@Value("${server.port:8080}")
    private Integer port;
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(NacosServletInitializer.class);
    }

	/**
	 * war包部署时，没有register:
	 *    AbstractAutoServiceRegistration继承了ApplicationListener<WebServerInitializedEvent>
	 */
	@SuppressWarnings("deprecation")
//	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (registration != null) {
            Integer tomcatPort = port;
            try {
                tomcatPort = new Integer(this.getTomcatPort());
            } catch (Exception e) {
                e.printStackTrace();
            }

            registration.setPort(tomcatPort);
            registration.start();
        }
	}
	
	/**
     * 获取外部tomcat端口
     */
    private String getTomcatPort() throws Exception {
        MBeanServer beanServer = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> objectNames = beanServer.queryNames(new ObjectName("*:type=Connector,*"), Query.match(Query.attr("protocol"), Query.value("HTTP/1.1")));
        return objectNames.iterator().next().getKeyProperty("port");
    }

}
