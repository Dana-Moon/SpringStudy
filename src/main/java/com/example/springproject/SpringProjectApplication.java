package com.example.springproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/* 8/18 @EnableJpaAuditing
Entity의 @createdDate, @LastModifiedDate를 자동으로 Date 값을 주입함.
왜 이걸 쓰는지 알아야 한다.
Date를 주입하거나 설정하는 부분이 3가지
1. 클라이언트에서 사용자가 임의로 날짜를 수정할 수 있는 위험.
이미 클라이언트에서 DATE 정보를 전달받으면 쉽게 Entity에 데이터 입력 가능
2. 서버에서 : 클라이언트가 접속하는 서버의 날짜 기준으로 일관성 있음.
서버에서 날짜 내장 메서드를 실행하는 리소스 문제(당장은 미미하나, 이러한 문제가 있을 수 있다.)
3. DB에서 : DB는 모든 정보를 총괄하는 1개 뿐인 서버 (날짜를 완전히 일관성 있게 만들 수 있다.)
모든 백엔드가 접속하기 때문에 리소스 문제 야기할 가능성 높음.
 */
/*
BaseTimeEntity 생성함.
 */
/*
첫번째로 생성자를 통해 Bean의 의존성 주입
생성자를 통해 Bean의 의존성 주입
: 생성자가 필요한 인자를 먼저 확인 후에 인저에 해당하는 Bean들을 먼저 생성.
Bean들을 모두 정상적으로 만들면 주입하여 생성 진행
*테스트 시 생성자 주입하면 에러가 발생한다.

두번째로 보다 간편하게 필드(Property)에 직접 @Autowired 어노테이션을 추가하여 의존성 주입.
- 순환참조
A가 B를 가지다
B가 A를 가지다

세번째
Setter (생성자로 의존성 주입)
주입받으려는 Bean의 생성자를 호출하고 없을 경우 컨테이너에 등록하고, 객체의 수정자를 호출하여 주입. Final 쓸 수 없음.
생성 시점 이후에 setter를 통해 주입할 수 있으므로 결합도가 낮으나(낮은 결합도, 높은 응집도가 좋다.), 언제나 수정될 수 있다는 문제와 함께 주입하는 Bean의 기능을 쓸 때 주입당하지 않았다면 에러가 생기는 문제가 있다.
 */


@EnableJpaAuditing
@SpringBootApplication
public class SpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringProjectApplication.class, args);

    }


}
