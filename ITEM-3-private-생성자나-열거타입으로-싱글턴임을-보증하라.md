# ITEM 3 - private 생성자나 열거타입으로 싱글턴임을 보증하라

ITEM 3 에서는 싱글턴을 만드는 3가지 방식에 대해 설명하고 있다.<br>

싱글턴의 구체적인 예는 아래의 두가지를 예로 들 수 있다.

- 무상태(stateless) 객체
- 설계상 유일해야 하는 시스템 컴포넌트

<br>

**싱글턴 클래스의 단점**<br>

싱글턴 클래스는 객체를 Mocking 해서 테스트 할 수 없다는 단점이 있기에 테스트가 어려워진 다는 단점 역시도 존재한다. 이런 경우 래퍼 메서드를 작성해서 Mocking 이 불가한 문제를 해결할 수 있다. 예를 들면, Singleton 클래스를 사용하는 객체 내에 메서드를 하나 추가해 싱글턴 객체를 얻어오는 래퍼 메서드를 하나 추가해서 사용하는 방식으로 어느 정도 우회하는 방식을 사용하는 것이다.<br>

**싱글턴 클래스를 멤버 메서드로 래핑해서, Mocking 가능하도록 만들기**<br>

Ex) <br>

아래와 같이 getInstance 메서드를 호출하는 로직을 멤버 함수내에서 호출하도록 래핑해서, 객체를 Mocking하는 것이 가능하다.

```java
@Service
public class StockPrice{
  // ...
  
  public List<ServerTime> getServerTimeList(String countryCode){
    ServerStockTime.getInstance().getStockTimeList(countryCode);
  }
}
```



## 싱글턴을 만드는 여러가지 방식들

싱글턴을 만드는 방식은 여러가지가 있다. 아래에 정리한 내용 중 4 번째 방식은 Bill Pugh 라는 사람이 고안한 방식인데, Effective Java 에서는 언급하고 있지 않은 내용이다.

- 1 ) private 생성자를 가진 클래스 내의 인스턴스를 즉시 생성해서 초기화 하는 방식이다.
  - 좋은 방식은 아니다.
  - 병렬프로그래밍 환경에서 객체가 두개 이상 생길 수 있다는 단점이 있다.
- 2 ) 정적 팩터리 메서드를 public static 멤버로 제공한다.
  - 흔히 getInstance() 라는 이름의 메서드로 제공하는 방식을 이야기한다.
  - 이 방식 역시 병렬 프로그래밍 환경에서 객체가 두개 이상 생길 수 있다는 단점이 있다.
- 3 ) 열거 타입으로 선언하는 것이다.
  - 열거타입으로 싱글턴을 구현할 경우 리필렉션 공격이나 복잡한 직렬화 상황에 대해서 제 2의 인스턴스가 생성되는 것을 막아준다고 책에서는 설명하고 있다.
  - 하지만, 특수한 경우가 아니라면 사용하지 않는 것이 좋다.
- 4 ) inner class 에서 멤버 필드의 선언과 동시에 객체를 생성해 초기화하는 방식
  - 추천되는 방식이다.
  - Bill Pugh Singleton 방식이다. 
  - 참고
    - [dzone.com - Singletons: Bill Pugh Solution or Enum](https://dzone.com/articles/singleton-bill-pugh-solution-or-enum)
    - [Yaboong.github.io](https://yaboong.github.io/design-pattern/2018/09/28/thread-safe-singleton-patterns/)

