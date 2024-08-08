// 다른 소스코드가 이 파일보다 상위 디렉토리에 있어서는 안된다.

package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//	@SpringBootApplication 에는 @ComponentScan 어노테이션이 있다.
//	상위 디렉터리는 Scan 하지 못하기 때문에 root 디렉터리에 위치해야한다.
@ServletComponentScan
//	주로 web 서블릿 기반의 구성요소를 스캔하고,  자동으로 등록하는 기능
//	예를 들어, @WebServlet, @WebFilter, @WebListener 등

@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
