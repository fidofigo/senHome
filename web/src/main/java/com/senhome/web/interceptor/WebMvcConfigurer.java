package com.senhome.web.interceptor;

import com.senhome.web.interceptor.mvc.CustomRequestMappingHandlerAdapter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcProperties;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
//@EnableConfigurationProperties({SignProperties.class})
@SuppressWarnings("SpringJavaAutowiringInspection")
public class WebMvcConfigurer extends WebMvcAutoConfiguration.EnableWebMvcConfiguration
{
//    @Autowired
//    private AccountInterceptor accountInterceptor;
//
////    @Autowired
////    private SignValidateInterceptor signValidateInterceptor;
////
////    @Autowired
////    private SignProperties signProperties;
////
////    @Autowired
////    private WebTokenInterceptor webTokenInterceptor;
//
//    @Autowired
//    private SystemTrackLogInterceptor systemTrackLogInterceptor;
//
//    @Autowired
//    private ProtocolInterceptor protocolInterceptor;
//
//    @Autowired
//    private WebLoginAuthInterceptor webLoginAuthInterceptor;

//    @Autowired
//    CommonInterceptor commonInterceptor;

    public WebMvcConfigurer(ObjectProvider<WebMvcProperties> mvcPropertiesProvider, ObjectProvider<WebMvcRegistrations> mvcRegistrationsProvider, ListableBeanFactory beanFactory)
    {
        super(mvcPropertiesProvider, mvcRegistrationsProvider, beanFactory);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        super.addInterceptors(registry);
//        String signExcludePath = signProperties.getExcludePath();
//        if(StringUtils.hasText(signExcludePath))
//            registry.addInterceptor(signValidateInterceptor).addPathPatterns("/**").excludePathPatterns(signExcludePath.split(","));
//        else
//            registry.addInterceptor(signValidateInterceptor).addPathPatterns("/**");

//        registry.addInterceptor(accountInterceptor).addPathPatterns("/**").excludePathPatterns("/webNative/wx/**","/webNative/mmd/callback");
//        registry.addInterceptor(systemTrackLogInterceptor).addPathPatterns("/**");
////        registry.addInterceptor(webTokenInterceptor).addPathPatterns("/webNative/**");
////        registry.addInterceptor(webLoginAuthInterceptor).addPathPatterns("/webNative/**");
//        registry.addInterceptor(protocolInterceptor).addPathPatterns("/**");

//        registry.addInterceptor(commonInterceptor).addPathPatterns("/**");
    }

//    @Bean
//    @Override
//    public RequestMappingHandlerMapping requestMappingHandlerMapping()
//    {
//        RequestMappingHandlerMapping handlerMapping = new CommonRequestMappingHandlerMapping();
//        handlerMapping.setOrder(0);
//        handlerMapping.setInterceptors(getInterceptors());
//        return handlerMapping;
//    }

    @Bean
    @Override
    public RequestMappingHandlerAdapter requestMappingHandlerAdapter()
    {
        RequestMappingHandlerAdapter adapter = super.requestMappingHandlerAdapter();
        RequestMappingHandlerAdapter customAdapter = new CustomRequestMappingHandlerAdapter();
        BeanUtils.copyProperties(adapter, customAdapter, "applicationContext", "returnValueHandlers");
        return customAdapter;
    }

//    @Bean(name ="freemarkerConfig")
//    public FreeMarkerConfigurer freemarkerConfig() {
//        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
//        configurer.setTemplateLoaderPath("/WEB-INF/views/");
//        return configurer;
//    }
//    @Override
//    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
//        super.configureContentNegotiation(configurer);
//        configurer.ignoreUnknownPathExtensions(false).defaultContentType(MediaType.TEXT_HTML);
//    }
//    @Override
//    public void configureViewResolvers(ViewResolverRegistry registry) {
//        super.configureViewResolvers(registry);
//        registry.freeMarker();
//    }

}
