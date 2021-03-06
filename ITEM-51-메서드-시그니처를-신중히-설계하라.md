# ITEM 51. 메서드 시그니처를 신중히 설계하라

> 개별 아이템에서 설명하기에 애매한 api 설계요령들<br>

<br>

## 메서드 이름을 신중히 짓자

- 표준 명명규칙(아이템 68)을 따르자.
- 자바 라이브러리 API 가이드 참조
  - ex) Collections, List, String, ArrayList, ... 

<br>

## 편의 메서드를 너무 많이 만들지 말자

모든 메서드는 각각 자신의 소임을 다해야 한다.

- 확신이 서지 않으면 만들지 마라
- 클래스, 인터페이스는 각 기능을 완벽히 수행하는 메서드로 제공해야 한다.
- 메서드가 너무 많으면 관리하기 쉽지 않다.

<br>

## 매개변수의 타입으로는 클래스보다는 인터페이스가 더 낫다

> 참고 : [아이템 64 - 객체는 인터페이스를 사용해 참조하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-64-객체는-인터페이스를-사용해-참조하라.md)
>
> - 아이템 64 에서도 언급하고 있는 내용이고, 여러 java 기본서에서도 자주 언급하는 내용이다. 기본서를 보면서 대체 이런 내용은 매번 누가 먼저 이야기하는걸까? 하는 의구심이 매번 가끔씩 들었는데, Effective Java가 아닐까? 하는 생각이 잠깐 들었다.<br>

<br>

**매개변수의 타입으로는 클래스보다는 인터페이스가 더 낫다.**<br>

>  [아이템 64 - 객체는 인터페이스를 사용해 참조하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-64-객체는-인터페이스를-사용해-참조하라.md)<br>

<br>

매개 변수로 적합한 인터페이스가 있다면, 이것을 구현한 클래스보다는 그 인터페이스를 직접 사용하자.<br>

예를 들면, 어떤 메서드에 `HashMap` 을 곧바로 넘길 일은 전혀 없다. 그 대신 `Map` 을 사용해야 한다. 이렇게 하면 `HashMap`, `TreeMap` , `ConcurrentHashMap`, `TreeMap` 의 부분 맵 등 어떤 `Map` 구현체도 인수로 건네는 것이 가능해진다.<br>

자바 라이브러리에는 존재하지 않는 커스텀으로 재정의한 Map 구현체도 사용가능하다.(실제로 커스텀으로 재정의한 사례를 실무에서 본 경험이 있다!!!)<br>

<br>

**인터페이스 대신 구체 클래스를 매개변수로 사용하는 경우의 단점**<br>

- 클라이언트에게 특정 구현체만 사용하게끔 제한하는 꼴이다.
- 혹시라도 입력 데이터가 다른 형태로 존재한다면 명시한 특정 구현체의 객체로 옮겨담느라 비싼 복사 비용을 치러야 한다.<br>

<br>

## boolean 보다는 원소 2개 짜리 열거타입을 고려하자

책에서는 `boolean` 보다는 원소 2개짜리 열거타입이 더 낫다. 라고 언급하고 있다.<br>

열거 타입을 사용하면 코드를 읽고 쓰기가 더욱 쉬워진다. 예를 들면 아래는 화씨온도(Fahrenheit), 섭씨온도(Celsius)를 원소로 정의한 열거타입이다.<br>

```java
public enum TemperatureScale {FAHRENHEIT, CELSIUS}
```

<br>

온도계 클래스가 있다고 해보자. 온도계에서는 인스턴스를 생성하는 정적 팩터리 메서드 `newInstance(...)` 를 사용하기로 했지만, 매개변수로 뭘로 할 지는 아직 정하지 않았다.

- 이때 boolean 타입을 사용하면 이런 모양이 된다.
  -  `Thermometer.newInstance(true)` 
- 반면, enum 을 사용하면 아래와 같은 모양이 된다.
  - `Thermometer.newInstance(TemperatureScale.CELSIUS)`

<br>

이렇게 코드를 작성하면, 구문이 수행하는 동작을 명확하게 설명할 수 있다는 장점이 있다. 물론 모든 boolean 타입에 이렇게 사용하기보다는 true/false 로 리턴하는게 더 명확한 경우 역시 있다는 점도 염두에 두고 enum 을 사용하는 것이 더 좋을수도 있을 것 같다.<br>

> 물론 true/false 역시도 모두 enum 으로 정의해서 `TRUE/FALSE`, `true/false`, `1/0` , `YES/NO`, `Y/N` 등으로 paraphrase 하는 경우에 대한 예제 역시도 본 경험이 있다. <br>
>
> 배달의 민족 기술블로그에서 이런 경우들을 설명하는 글을 몇년 전에 봤던 것으로 기억하는데, 현재 그 글이 어디있는지 찾지를 못해서, 조만간 찾게 되면 해당 글의 링크를 여기에 추가해둘 예정이다...

<br>

이렇게 enum 을 구현해두면, 각 온도를 다른 온도 단위로 변환해주는 메서드를 열거 타입에 정의해 둘 수 있어서 훨씬 더 명확한 코드를 만들 수 있다는 점이 장점이 될 수 있다.<br>

<br>

## 매개변수 목록은 짧게 유지하라

- 4개 이하가 기억하기 어렵지 않다.
- 같은 타입의 매개변수 여러개가 연달아 나오는 경우가 특히 해롭다.

<br>

## 매개변수 짧게 하는 TIP

- 1 ) 여러 메서드로 쪼갠다
- 2 ) 매개변수 여러개를 묶어주는 도우미 클래스를 만들어 활용
- 여러 메서드로 쪼개기 + 매개변수 여러개를 묶어주는 도우미 클래스 사용
  -  `1)` 과 `2)` 를 혼합한 방식
  - 객체 생성에 사용한 빌더패턴을 메서드 호출에 응용한다.

<br>

## 첫번째, 여러 메서드로 쪼갠다.

필요 메서드 단위로 따로 분리한 후에 여러 메서드로 쪼갠다.<br>

각 메서드 단위로 분리해서 매개변수를 분리하면, 잘못하면 메서드가 너무 많아질 수 있지만, 서로 의미/기능이 완전하게 분리된 각각의 메서드로 분리가 가능하다.<br>

<br>

**직교성을 높여준다.**<br>

`책에서는 직교성을 높여 메서드 수를 줄여주는 효과가 있다.` 라고 설명하고 있다.<br>

(다소 학문적인 용어라 받아들이는 사람에 따라 다르게 느낄수 있겠지만, 다른 프로그래밍 언어 및 해외 전공서적 등 에서 사용할 수도 있는 컴퓨터 공학에서 사용되는 용어인 것으로 보임.)<br>

> 저자가 이야기하는 직교성이라는 것은 공통점이 없는 기능들이 잘 분리되어 있다는 의미다. <br>
>
> (수학에서의 직교는 어떤 두 벡터가 서로 직각을 이룰때, 두 벡터의 내적은 0인데, 이것은 두 벡터가 서로 영향을 전혀 주지 않는 다는 의미다.)<br>

<br>

**`java.util.List` 인터페이스**<br>

ex) 리스트에서 주어진 원소의 인덱스를 찾아야하는데, 지정된 범위의 부분리스트 에서의 인덱스를 찾는 경우<br>

이 기능을 하나의 메서드로 구현하려 할때 필요한 매개변수는 아래의 3가지다.

- 부분리스트의 시작
- 부분리스트의 끝
- 찾을 원소

<br>

List 인터페이스는 아래의 메서드들을 통해 기능을 두개로 분리해 제공하고 있는데, 깔끔하게 서로의 기능이 분리되어 독립적이다.

- subList 메서드
  - 부분 리스트를 반환한다.
- indexOf 메서드
  - 주어진 원소의 인덱스 위치를 알려주는 메서드

<br>

`subList` 메서드가 반환하는 부분 리스트 역시 `List` 타입이기에, `indexOf` 메서드로 처리를 수행하는 것이 가능하다.<br>

<br>

## 두 번째, 매개변수 여러 개를 묶어주는 도우미 클래스를 만들어 활용

일반적으로 도우미 클래스는 정적 멤버 클래스(아이템 24)로 둔다.<br>

도우미 클래스를 사용한다는 의미는 매개변수 여러 개를 필요한 기능 단위로 나누고, 나누어놓은 단위별로 클래스를 하나 만들어두는 것을 의미한다.<br>

이렇게 도우미 클래스를 사용하게 될때는, 되도록 정적 멤버 클래스로 두어 사용하는 것이 좋다.<br>

> 참고 : 아이템 24 - 멤버 클래스는 되도록 static 으로 만들라
>
> - 이 챕터를 아직 정리하지 않았는데, 차주 내로 정리를 시작하게 될 것 같다.<br>

<br>

**ex) 카드게임**<br>

예를 들어 카드게임을 클래스로 만든다고 해보자.<br>

메서드를 호출할 때 카드의 숫자(rank), 무늬(suit)를 뜻하는 두 매개변수를 항상 같은 순서로 전달하게 된다.<br>

이런 경우는 이 둘을 묶는 도우미 클래스를 하나 만들고, 도우미 클래스 하나로 매개변수를 주고 받으면 API는 물론 클래스 내부 구현 역시 깔끔해진다.<br>

<br>

## 세 번째, 여러 메서드로 쪼개기 + 도우미 클래스 활용

- 첫 번째로 언급한 여러 메서드로 쪼개기

- 두 번째로 언급한 매개변수 여러개를 묶어주는 도우미 클래스를 활용하기 

이 두 방식을 혼합한 방식이다.<br>

매개 변수가 많고, 그 중 일부는 생략해도 괜찮을 경우 도움이 되는 방식이다.<br>

<br>

대략적으로 설명해보면 아래와 같은 방식이다.<br>

- 모든 매개변수를 하나로 추상화한 객체를 정의
- 클라이언트 측에서 이 객체의 필드들은 빌더패턴을 통해 개별 주입
  - 또는 setter 를 통해 필요한 값을 설정
- 클라이언트 측에서는 `execute` 메서드를 호출해 매개변수들의 유효성을 검증

<br>

책에서는 이렇게 언급하고 있지만, 실무에서는 아마도 빌더 패턴을 적극적으로 활용하게 되지 않을까 싶다. 빌더 패턴을 사용하게 될 경우 크게 아래의 두가지 방식으로 사용하게 될 것 같다.

- 직접 구현한 빌더 패턴을 사용하고, `build()` 메서드 내에서 매개변수가 올바른지 검사
- `lombok` 의 빌더 패턴을 사용하고, `build()` 메서드를 커스텀으로 정의해서 validation 구문을 정의
  - 이 부분에 대한 내용은 [Add Custom Validations with Lombok Builders](https://trguduru.github.io/2019/06/add-custom-validations-with-lombok-builders/) 을 참고하자.

[Add Custom Validations with Lombok Builders](https://trguduru.github.io/2019/06/add-custom-validations-with-lombok-builders/) 의 예제중 일부를 발췌해보면 아래와 같다.

```java
@Getter
@EqualsAndHashCode
@ToString
@Builder(builderClassName = "Builder",buildMethodName = "build")
public class Customer {
    private long id;
    private String name;

    static class Builder {
        Customer build() {
            if (id < 0) {
                throw new RuntimeException("Invaid id");
            }
            if (Objects.isNull(name)) {
                throw new RuntimeException("name is null");
            }
            return new Customer(id, name);
        }
    }
}
```

<br>





















