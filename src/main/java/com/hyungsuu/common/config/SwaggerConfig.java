package com.hyungsuu.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//@Configuration
//@EnableWebMvc
//@EnableSwagger2
//@Profile(value = "!prod") // profile에 설정
//public class SwaggerConfig {
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.hyungsuu"))
//                .paths(PathSelectors.any())
//                .build();
//
//    }
//    
////    private ApiKey apiKeyHashcd() {
////        return new ApiKey("apiKeyHashcd", "Authorization", "header");
//////        return new ApiKey("Authorization", "apiKeyHashcd", "header");
////    }
////    
////    private ApiKey apiUtlinsttCode() {
////        return new ApiKey("apiUtlinsttCode", "Authorization", "header");
//////        return new ApiKey("Authorization", "apiUtlinsttCode", "header");
////    }
////    
////    private ApiKey deviceID() {
////        return new ApiKey("deviceID", "Authorization", "header");
//////        return new ApiKey("Authorization", "deviceID", "header");
////    }
//}

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().components(new Components()).info(apiInfo());
	}

//    @Bean
//    public GroupedOpenApi userApi() {
//        return GroupedOpenApi.builder()
////        		.group("user")
////        		.
//        		.packagesToScan("com.hyungsuu")
//                .pathsToMatch("/**") // 특정 경로만 포함하려면 수정
//                .build();
//    }
//    

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//        		.group("admin")
//        		.packagesToScan("com.hyungsuu")
//                .pathsToMatch("/**") // 특정 경로만 포함하려면 수정
//                .build();
//    }

	private Info apiInfo() {
		return new Info().title("Springdoc 테스트").description("Springdoc을 사용한 Swagger UI 테스트").version("1.0.0");
	}

}
