package org.arhor.diploma.config

import org.arhor.diploma.util.Converter
import org.arhor.diploma.web.filter.CustomCsrfFilter
import org.modelmapper.ModelMapper
import org.modelmapper.config.Configuration as MapperConfig
import org.modelmapper.convention.MatchingStrategies
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class BeansConfig {

  @Bean
  fun modelMapper() = ModelMapper().apply {
    configuration.matchingStrategy = MatchingStrategies.LOOSE
    configuration.fieldAccessLevel = MapperConfig.AccessLevel.PRIVATE
    configuration.isFieldMatchingEnabled = true
    configuration.isSkipNullEnabled = true
  }

  @Bean
  fun modelMapperConverter(mapper: ModelMapper) = object : Converter {
    override fun <T, R> convert(source: T, target: Class<R>): R {
      return mapper.map(source, target)
    }
  }

  @Bean
  fun messageSource() = ResourceBundleMessageSource().apply {
    setBasename("messages")
  }

  @Bean
  @Profile("!dev")
  fun csrfFilter(csrfFilter: CustomCsrfFilter) = FilterRegistrationBean<CustomCsrfFilter>().apply {
    order = 1
    filter = csrfFilter
    addUrlPatterns("/api/*")
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder(5)
}
