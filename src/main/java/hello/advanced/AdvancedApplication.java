package hello.advanced;

import hello.advanced.config.AppV1Config;
import hello.advanced.config.AppV2Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

// 클래스를 스프링 빈으로 등록 되게 함.
//@Import(AppV1Config.class)
//@Import(AppV2Config.class)
@Import({AppV2Config.class, AppV1Config.class})
// 기본적으로 전체적으로 컴포넌트 스캔(스프링 컨테이너 안에 Bean 등록)을 하는데
// scanBasePackages 를 설정 하면 특정 패키지만 컴포넌트 스캔을 할 수 있다.
@SpringBootApplication(scanBasePackages = "hello.advanced.app")
public class AdvancedApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvancedApplication.class, args);
	}

}
