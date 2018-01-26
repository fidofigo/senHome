package com.senhome.web.interceptor;

import com.senhome.web.interceptor.mvc.CustomRequestMappingHandlerAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 * web的全局配合
 *
 * @author <a href="mailto:zhongchao@gegejia.com">zhong</a>
 * @version 1.0 2016/11/25
 * @since 1.0
 */
@Configuration
@SuppressWarnings("SpringJavaAutowiringInspection")
public class WebMvcConfigurer extends WebMvcAutoConfiguration.EnableWebMvcConfiguration
{
    @Autowired
    private AccountInterceptor accountInterceptor;

    public WebMvcConfigurer(ObjectProvider<WebMvcProperties> mvcPropertiesProvider, ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ListableBeanFactory beanFactory)
    {
        super(mvcPropertiesProvider, mvcRegistrationsProvider, beanFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);

        registry.addInterceptor(accountInterceptor).addPathPatterns("/**");
    }

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter()
    {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        RequestMappingHandlerAdapter customAdapter = new CustomRequestMappingHandlerAdapter();
        BeanUtils.copyProperties(adapter, customAdapter, "applicationContext", "returnValueHandlers");
        return customAdapter;
    }
}
