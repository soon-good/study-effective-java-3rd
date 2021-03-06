# ITEM 5. 자원을 직접 명시하지 말고 의존객체 주입을 사용하라

## 핵심정리

**사용하는 자원에 따라서 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.**<br>

클래스가 내부적으로 하나 이상의 자원에 의존하고, 그 자원이 클래스의 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안된다.<br>

대신 필요한 자원 또는 자원을 만들어주는 팩토리 객체를 생성자(또는 정적 팩터리,빌더 등)에 넘겨주자.<br>

이렇게 의존 객체 주입을 하는 이런 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 기막히게 개선해준다.<br>

<br>

## 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않은 경우

**사용하는 자원에 따라서 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.**<br>

대부분의 많은 클래스가 하나 이상의 자원에 의존한다. 의존성을 분리하기 위해 static 을 이용한 정적 유틸리티 클래스를 만들기도 하고, 싱글턴을 사용하는 경우도 있다. 하지만, 유연하지 않고 테스트하기 어려우며, 의존 객체 주입을 어렵게 만드는 경우가 있다. 예를 들면 아래의 두 경우다.

- Private 생성자나 열거타입으로 싱글턴임을 보증한 정적 유틸리티 클래스 (ITEM 3)
- 싱글턴을 잘못 사용한 경우

> 책에서는 용도에 맞게 잘 사용하지 않은 경우에 대한 단점에 대해 설명한다. 싱글턴, private 생성자를 사용하는 경우, 용도에 맞게 잘 사용한다면 여기에 해당하지는 않는다. 예를 들면 시스템의 전역 공유 변수라든가 하는 것들은 싱글턴을 쓰는 것이 나쁘지는 않다. 스프링의 빈 역시도 싱글턴으로 관리된다. <br>

<br>

1 ) private 생성자나 열거타입으로 싱글턴임을 보증하는 정적 유틸리티 클래스

```java
public class SpellChecker{
  private static final Lexicon dictionary = ...;
  private SpellChecker(){} // 객체 생성 방지
  
  public static boolean isValid(String word){ ... }
  public static List<String> suggestions(String typo) { ... }
}
```

<br>

2 ) 싱글턴을 잘못 사용한 경우

```java
public class SpellChecker{
  private final Lexicon dictionary = ... ;
  
  private SpellChecker(...) {}
  public static SpellChecker INSTANCE = new SpellChecker(...);
  
  public boolean isValid(String word){...}
  public List<String> suggestions(String typo){...}
}
```

<br>

## 의존 객체 주입방식

**사용하는 자원에 따라서 동작이 달라지는 클래스에는 정적 유틸리티 클래스나 싱글턴 방식이 적합하지 않다.**<br>

클래스가 여러가지 자원 인스턴스를 사용해야 하고, 클라이언트가 원하는 자원을 사용해야 하는 경우 **인스턴스를 생성할 때 생성자에 필요한 자원을 넘겨주는 방식을 사용하는 방식**이 권장된다. 의존 객체 주입을 해주는 방법 중 하나인데, 의존 객체를 주입하는 방식은 아래와 같이 여러가지 방식들이 있다.<br>

- 생성자에 객체를 넘겨주는 방식
- 정적 팩터리 (ITEM1) 에 객체를 넘겨주는 방식
- 빌더 (ITEM 2) 에 객체를 넘겨주는 방식
- 자원 팩터리를 넘겨주는 방식
  - 1 ) 팩터리 메서드 패턴을 구현한 객체를 넘겨주는 방식
  - 2 ) `Supplier<T>` 를 이용해 팩터리를 표현한 람다식을 전달받아 의존성을 주입받는 방식



**장점**<br>

- 불변성이 확보된다.
- 스레드 안전하다.
- 테스트에 용이하다.
  - Mockito 등을 이용한 테스트에 용이하다.

<br>

**단점**<br>

의존성이 수천 개 이상이 되는 큰 프로젝트에서는 코드를 어지럽게 만들기도 한다.<br>

대거(Dagger), 주스(Guice), 스프링(Spring) 과 같은 프레임워크를 사용하면 의존객체 주입을 수월하게 할 수 있도록 편의성을 제공하기에 이러한 복잡합을 해결할 수 있다. 

