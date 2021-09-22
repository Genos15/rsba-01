package  com.rsba.monitor_ms.configuration.cors

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter

@Configuration
class CorsConfigurationSet {

    @Bean
    fun corsFilter(): FilterRegistrationBean<*> {
        val source = UrlBasedCorsConfigurationSource()
        val config = CorsConfiguration()
//        config.allowCredentials = false
        config.addAllowedOrigin("*")
        config.addAllowedOriginPattern("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        source.registerCorsConfiguration("/**", config)

        val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
        bean.order = 0
        return bean
    }
}