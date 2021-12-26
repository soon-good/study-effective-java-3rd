# ITEM 10. equals 는 일반 규약을 지켜 재정의하라

보통 일반적인 Object의 equals() 메서드는, 인스턴스 자체의 hashCode를 비교한다. 이렇게 하면, 객체가 같은지 아닌지를 비교하게 된다. 만약 논리적인 값으로 동치성을 비교하고 싶다면, equals() 메서드를 오버라이딩하면 된다. 이번 장에서 다루는 주요 내용들을 요약해서 리스트업 해보면 아래와 같다.

- equals() 메서드를 재정의할 필요가 없는 경우들
- equals() 메서드를 재정의해야 하는 경우들 

<br>

equals() 관련 챕터인 이번 장은 너무 엄청나게 매우 길어서, 3일 ~ 4일은 걸려야 정리가 잘 될 것 같다. 잠시동안 해야하는 다른 일이 있어서 잠시 이펙티브 자바 정리를 미뤄두고 있었는데, 한번 정리할 때 하루에 30~1시간 정도는 투자를 해서 정리를 ㅐㅎ야 할것 같다. 어차피 야간 모니터링을 거의 매의 해야 되니... 멍때리기보다는 스터디를 ... 해야 겠다. 모니터링을 하다보면, 실제 공부를 할 수 있는 시간보다는 모니터링을 하는데에 시간을 많이 뺏기긴 하지만... 그래도 남는 시간 잘 활용해서 지금 상황을 벗어나야 할 것 같다!!!

<br>

## 다른 챕터 참고

- 아이템 11- equals 를 재정의할 때는 hashCode 도 반드시 재정의하라
- 아이템 17 - 변경가능성을 최소화하라
  - equals 의 일관성 조건을 설명하면서 클래스를 불변 클래스로 만드는 것에 대해 언급하면서 불변 클래스로 만드는 것에 대해 아이템 17을 참고하게끔 주석으로 정리되어 있다.
- 아이템 52 - 다중정의는 신중히 사용하라
  - Object의 equals(Object o) 를 메서드 재정의하는 것이 아닌 다중정의하는 경우가 있다. 
  - 예를 들면, `public boolean equals(MyClass o){ ... }` 를 정의하는 경우다.
  - 다중 정의에 대한 내용은 아이템 52를 찾아보자.

<br>

## Java 라이브러리 내에 equals 규약이 제대로 지켜지지 않은 라이브러리들

저자인 조슈아블로크는 Java 언어 내에서 equals 규약이 제대로 지켜지지 않아서 특정 상황에서는 해당 라이브러리를 가급적 지키지 않도록 경고를 해주고 있다. 책을 읽으면서는 가끔씩 그냥 지나쳤는데, 여기에 해당 라이브러리들에 대해 요약을 해두었다. (계속 추가 예정. 조금 늦게 정리를 시작했다.)

- 
- **`java.net.URL`**
  - equals 규약의 일관성 조건을 위배
  - java.net.URL 의 equals 는 주어진 URL 과 호스트의 IP 주소를 이용해 비교한다.
  - 호스트 이름을 IP 주소로 바꾸려면 네트워크를 통해야 하는데, 네트워크 사정상 항상 같은 결과를 보장하기 어렵다.
  - 이런 문제는 URL클래스의 equals 가 일반 규약을 어기게 되어 실무에서도 종종 문제를 일으키게 된다.
  - URL의 equals를 이렇게 구현한 것은 큰 실수였다. 따라하면 안된다.

<br>

## equals 재정의시 주의점 (요약)

이 글은 이번 챕터의 가장 마지막에 언급하는 내용이다. 책의 저자인 조슈아 블로크는 한국인으로 귀화한 게 아닐까 하는 착각이 들정도로 이상하게도 결론을 맨 뒤에 둔다. 한국말은 끝까지 들어봐야 한다는 이야기가 있는데, 이 분은 항상 글의 맨 뒤에 요점정리를 해둔다. 그래서 이번 챕터의 가장 마지막 부분을 읽고 해당 주의 점에 대한 언급을 여기에 정리해두려 한다.<br>

<br>

equals 를 다 구현했다면 아래의 세 가지를 자문해봐야 한다.

- 대칭적인가?
- 추이성이 있는가?
- 일관적인가?

<br>

위의 자문에서 끝내지 말고 반드시 단위테스트를 작성해 돌려봐야 한다. 단, equals 메서드를 AutoValue 를 이용해 작성했다면 테스트를 생략해도 안심할 수 있다. 위의 세 요건 중 하나라도 실패한다면, 원인을 찾아서 고쳐야 한다. 물론 반사성, null-아님 요건도 만족해야 하는데, 반사성, null-아님에 위배되는 경우는 거의 없다.<br>

<br>

그리고 아래는 equals 메서드를 구현시에 주의할 점이다.<br>

- equals 를 재정의할 때는 hashCode 도 반드시 재정의하자. (아이템 11)

- 너무 복잡하게 해결하려 들지 말자.

  - 필드들의 동치성만 검사해도 equals 규약을 어렵지 않게 지킬 수 있다. 오히려 너무 공격적으로 파고들다가 문제를 일으킨다.
  - 일반적으로 별칭(alias)은 비교하지 않는게 좋다.
    - 예를 들어 File 클래스의 경우 심볼릭 링크를 이용해 같은파일을 가리키는지 확인하려 하면 안된다.
    - 다행히 File 클래스는 이런 시도를 하지 않는다.

- Object 외의 타입을 매개변수로 받는 equals 메서드는 선언하지 말아야 한다.

  - 많은 경우 equals 를 `public boolean equals(MyClass o){ ... }` 와 같은 형태로 작성해놓고 문제의 원인을 찾으려 헤매는 경우가 많다.

  - 이런 경우의 equals 는 Object.equals 를 재정의한 것이 아니다. 입력타입이 Object가 아니기 때문에 메서드 재정의되지 않은 것이다. 오히려 다른 입력타입을 가진 또다른 하나의 메서드를 선언한 것 이다.

  - 이렇게 타입을 구체적으로 명시항 equals 메서드는 오히려 해가 된다.

  - @Override 애너테이션을 잘 사용하면 긍정오류(false positive)를 내게 하고 보안 측면에서도 잘못된 정보임을 명시해 컴파일 타임에 오류를 찾아낼 수 있다.

  - 이렇게 부주의하게 다중정의를 했을 때를 예방하려면 명시적으로 Object의 equals 메서드를 재정의했음을 컴파일타임에 알수 있도록 해주면 된다. 예를 들면 아래와 같은 방식을 예로 들수 있다.

  - ```java
    @Override
    public boolean equals(MyClass o){
      // ...
    }
    ```

  - 위의 클래스는 @Override를 했지만, Object의 equals (Object o) 를 오버라이딩한 것이 아니다. 따라서 컴파일 타임에 에러를 낼 수 있기 때문에 안전하다.

  - 즉, @Override 를 명시해서, 이것이 Object의 equals(Object) 를 오버라이딩 했음을 명시적으로 정의하는 습관을 들이자.

<br>

아래는 equals 규약을 나름 잘 지켜서 작성된 PhoneNumber 클래스다. (소스코드 [링크](https://github.com/jbloch/effective-java-3e-source-code/blob/master/src/effectivejava/chapter3/item10/PhoneNumber.java))

```java
package effectivejava.chapter3.item10;

// Class with a typical equals method (Page 48)
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix   = rangeCheck(prefix,   999, "prefix");
        this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.lineNum == lineNum && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }

    // Remainder omitted - note that hashCode is REQUIRED (Item 11)!
}
```

<br>

## equals 를 재정의하지 않는 것이 오히려 더 나을 수 있는 경우들

equals 메서드는 재정의하기 쉬워보이지만, 곳곳에 함정이 도사리고 있어서 자칫하면 끔찍한 결과를 초래한다.<br>

Equals() 메서드를 잘못 정의해서 생기는 문제를 회피하는 방법은 아예 재정의하지 않는 것이다. 아래에 정의한 내용 들 중 하나에 해당된다면 equals() 메서드를 재정의 하지 않는 것이 최선이다.<br>

<br>

- 각 인스턴스가 본질적으로 고유하다.
- 인스턴스의 논리적 동치성(logical equality) 을 검사할 일이 없다.
- 상위클래스에서 재정의한 equals가 하위 클래스에도 딱 들어 맞는경우
- 클래스가 private 이거나 package-private 이고, equals 메서드를 호출할 일이 없다.

<br>

### 1 ) 각 인스턴스가 본질적으로 고유하다

값을 표현하는게 아니라 동작하는 개체를 표현하는 클래스를 의미한다. 예를 들면 Thread 객체를 예로 들 수 있다. Object 의 equals() 는 이런 클래스에 맞게 구현되어 있다.<br>

<br>

### 2 ) 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.

논리적인 동치를 판단하는 것이 굳이 필요하다고 생각되지 않는다면, equals() 메서드는 오버라이딩하지 않는 것이 좋은 선택이다.<br>

java.util.regex.Pattern 클래스를 예로 들어보자. Pattern 클래스를 이용해 서로 다른 인스턴스 두개를 만들었다고 해보자. 이 때 두 인스턴스가 바인딩하고 있는 정규표현식이 서로 같은지를 검사하려고 할 수 있다. 보통 이런 경우 설계자 입장에서는 클라이언트 측에서 이런 기능이 필요하지 않다고 생각할 수 있고, 또는 애초에 필요하지 않다고 판단할 수 있다. 이렇게 필요없는 논리적 동치성에 대해서는 equals() 메서드를 재정의하지 않는 것이 낫다.<br>

<br>

### 3 ) 상위 클래스에서 재정의한 equals 가 하위 클래스에도 딱 들어맞는다.

예를 들면,`AbstractSet`, `AbstractMap` 을 예로 들 수 있다. 대부분의 Set 구현체, Map 구현체들은 `AbstractSet`, `AbstractMap` 을 상속받아 정의되어 있다. 이 AbstractSet, AbstractMap 에서는 equals() 메서드를 구현하고 있는데, 이 것이 하위 클래스에도 적합하게 적용가능하다. 따라서 Set, Map 타입의 컬렉션을 구현해야 할 일 이 생긴다면 equals() 메서드를 오버라이딩을 하지 않아도 된다.<br>

<br>

### 4 ) 클래스가 private 이거나 package-private 이고, equals 메서드를 호출할 일이 없다.

> Package-private 는 protected 키워드와 유사한 의미이다.

- 클래스가 private 이거나, package-private 일 경우,
- Equals 메서드를 호출할 일이 없을 경우

이런 경우 equals 메서드를 굳이 오버라이딩하지 않아도 된다.<br>

이런 경우 만약 위험을 피하기 위해 equals가 실수로라도 호출되는 것을 막으려 한다면 아래와 같이 코드를 구현해두자.

```java
@Override public boolean equals(Object o){
  throw new AssertionError(); // 호출 금지.
}
```

<br>

## equals() 를 재정의해야 하는 경우들

객체 식별성(object identity)가 아니라 논리적 동치성을 확인해야 할때 사용한다. 

> 여기서 객체 식별성(object identity)는 내가 만든 말이 아니고, 저자 조슈아 블로크 님이 사용하신 언어다. 

주로 값 클래스들이 여기에 해당한다. 값 클래스란, Integer, String 과 같은 값을 표현하는 클래스를 의미한다. 이런 값 클래스를 정의할 때, 값의 동등함을 비교하기 위해 equals() 메서드를 오버라이딩해야 한다. 이렇게 값의 동등함을 비교하는 것을 책에서는 논리적 동치성 이라는 용어로 설명하고 있다.<br>

만약 값 클래스라고 하더라도 인스턴스가 2개 이상 만들어지지 않음을 보장하는 인스턴스 통제 클래스일 경우, equals 를 재정의하지 않아도 된다. Enum(아이템 34)도 여기에 해당한다. 이렇게 논리적 동치성과 객체 식별성이 같은 경우에는 Object의 equals 메서드가 논리적인 동치성까지도 확인해주는 것이 가능하다고 볼수 있다.<br>

논리적인 동치성 비교라는 개념에 대한 간단한 예를 들어보면 아래와 같다.<br>

`Fruit` 라는 클래스가 있다고 해보자.

```java
public class Fruit{
  private String kind;
  private Integer price;
  
  public Fruit(String kind, Integer price){
    this.kind = kind;
    this.price = price;
  }
}
```

<br>

이 Fruit 클래스를 객체로 생성한 아래와 같은 코드를 보자.

```java
Fruit apple1 = new Fruit("Apple", 1000);
Fruit apple2 = new Fruit("Apple", 1000);
```

<br>

위의 두 인스턴스 apple1, apple2 의 논리적인 동치성을 확인하기 위해 아래의 코드를 입력해보자.

```java
@Test
public void 객체동치성과_값동치성_비교_테스트(){
  Fruit apple1 = new Fruit("Apple", 1000);
  Fruit apple2 = new Fruit("Apple", 1000);

  System.out.println(apple1.equals(apple2));
  System.out.println(apple1 == apple2);
}
```

Fruit 클래스에 별도의 equals() 메서드를 오버라이딩 해두지 않는다면, 값을 비교할 기준이 없어서 apple1, apple2 객체를 equals() 로 비교한 결괏값이 false가 된다. apple1 == apple2 인지를 검사할 수 있는 기능이 필요한데, 이렇게 equals() 메서드를 오버라이딩하지 않으면, Object의 equals() 메서드를 사용하게 되어 값 클래스의 객체에 대한 비교를 수행하지 못하게 된다.<br>

<br>

## equals() 메서드 구현시 동치관계 성립을 위해 만족해야 하는 조건들

여기서는 수학적인 개념이 많이 등장한다. 저자인 조슈아블로크는 논리적으로 A와 B가 동일함을 보장할 때 수학적으로 이야기하는 개념들로 대칭성, 추이성, 반사성을 적용해서 설명해주고 있다. 일관성, null-아님 개념의 경우는 컴퓨터공학에서 자주 이야기 되는 개념으로 보인다. (예를 들면 3단 논법이라던가, A=B 이고 B=C 이면 A=C 이다 등등 그런 개념이어서 마냥 어렵다고 생각하면서 받아들이지만 않으면 금방 파악할 수 있는 개념들이다.)

- 반사성(reflexivity)
  - null 이 아닌 모든 참조값 x 에 대해 x.equals(x)는 true 다.

- 대칭성(symmetry)
  - null 이 아닌 모든 참조값 x,y 에 대해 x.equals(y)가 true 면 y.equals(x) 도 true 다.

- 추이성(transitivity)
  - null 이 아닌 모든 참조값 x,y 에 대해 x.equals(y) == true 이고, y.equals(z) == true 이면, x.equals(z) 도 true이다.
  - 리스코프 치환원칙이란?
  - 상속 대신 컴포지션을 사용하라
    - 상속을 이용한 객체비교보다는, 컴포지션관계로 풀어내 equals가 가능하도록 구현

- 일관성(consistency)
  - null 아 아닌 모든 참조값 x,y 에 대해 x.equals(y) 를 반복해서 호출하면 항상 true 를 반환하거나, false 를 반환한다.

- null-아님
  - null 아 아닌 모든 참조값 x에 대해 x.equals(null) 은 false 다.


<br>

### 반사성 (reflexivity)

**null 아닌 모든 참조값 x 에 대해 x.equals(x) 는 true 다.**<br>

객체는 자기 자신과 같아야 한다는 뜻이다. 객체 자체를 변경하지 않는한 안지켜지는 경우는 거의 없기에, 그다지 크게 신경쓰지 않아도 되는 원칙이다.

```java
@Test
public void equals1_reflexivity(){
  Fruit apple1 = new Fruit("Apple", 1000);
  System.out.println(apple1.equals(apple1));
}
```

<br>

### 대칭성 (symmetry)

**null 아 아닌 모든 참조값 x,y 에 대해, x.equals(y) 가 true 면 y.equals(x) 도 true다.**<br>

두 객체는 서로에 대한 동치 여부에 똑같이 답해야 한다는 의미다.<br>

이번 개념은 언뜻 말로 이해하기에는 쉽다. 예제를 한번 보자.<br>

**CaseInsensitiveString.java**<br>

먼저... CaseInsensitiveString 이라는 이름의 클래스를 정의했다.

```java
public class CaseInsensitiveString1{
  private final String s;

  public CaseInsensitiveString1(String s){
    this.s = Objects.requireNonNull(s);
  }

  @Override
  public boolean equals(Object obj) {
    if(obj instanceof CaseInsensitiveString1)
      return s.equalsIgnoreCase(((CaseInsensitiveString1)obj).s);
    if(obj instanceof String)
      return s.equalsIgnoreCase((String)obj);
    return false;
  }
}
```

<br>

테스트 코드

```java
public class EqualsTest {

  // ...

	@Test
	public void equals2_symmetry1(){
		CaseInsensitiveString1 cis = new CaseInsensitiveString1("Polish");
		String s = "Polish";
		System.out.println(cis.equals(s));

		List<CaseInsensitiveString1> list = new ArrayList<>();
		list.add(cis);
		System.out.println(list.contains(s));
	}
}
```

결과값은 아래와 같다.

```
true
false
```

<br>

현재 사용중인 OpenJDK 16 버전에서는  `list.contains(s)` 의 결과값이 false로 나왔다. 사람의 판단에 의해서도 false가 맞다. 컴파일러 역시도 false로 잘 판단해주었다. 하지만, 이것은 JDK 버전에 따라서 어떤 결과가 나올지 자신없는 코드를 만들어내게 된다. 즉, equals 규약을 어겼기 때문에, 이 객체를 사용하는 다른 객체들이 어떻게 반응할지 예측이 쉽지 않다.<br>

이런 문제를 해결하기 위해서는 CaseInsensitiveString 의 equals() 메서드를 String 과도 연동하지 않아야 한다. String과 CaseInsensitiveString1 과도 비교하는 것으로 인해 equals() 비교해 모호함이 생겼다. 위의 코드는 아래와 같은 코드로 단순하게 바꿀 수 있다.<br>

<br>

**CaseInsensitiveString2.java**<br>

```java
class CaseInsensitiveString2{
  private final String s;

  public CaseInsensitiveString2(String s){
    this.s = Objects.requireNonNull(s);
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof CaseInsensitiveString2 && ((CaseInsensitiveString2)obj).s.equalsIgnoreCase(s);
  }
}
```

<br>

테스트코드

```java
@Test
public void equals2_symmetry2(){
  CaseInsensitiveString2 cis = new CaseInsensitiveString2("Polish");
  String s = "Polish";
  System.out.println(cis.equals(s));

  List<CaseInsensitiveString2> list = new ArrayList<>();
  list.add(cis);
  System.out.println(list.contains(s));
}
```

<br>

출력결과

```plain
false
false
```

<br>

### 추이성 (transitivity)

#### 참고) 자바 라이브러리에서 추이성이 깨지는 사례

구체 클래스 하나를 확장(extends - 상속) 해서 새로운 자식 클래스와 구체클래스간 equals 연산을 성립시키려 할 때 추이성이 깨질 수밖에 없다. 그런데, 자바 라이브러리에 구체 클래스를 확장해 값을 추가해서 equals 연산시 추이성이 깨지는 클래스가 종종 있다. 저자는 이런 클래스들을 이용해 equals 연산을 수행하지 않을 것을 이야기 해주고 있다.<br>

`java.sql.Timestamp` 클래스는 `java.util.Date` 클래스를 확장했다. 그리고 `java.sql.Timestamp` 클래스 내에는 nanoseconds 필드가 있다. 그 결과로 Timestamp 의 equals 는 **대칭성을 위배**해 Date 객체와 한 컬렉션에 넣커나 섞어서 사용하면 엉뚱하게 동작할 가능성이 있다. 이런 이유로 Timestamp 의 API 설명에는 Date 와 섞어 쓸 때의 주의사항을 언급하고 있다. 자칫 실수하면 디버깅하기 어려운 이상한 오류를 경험하게 될 수 있다. 따라서 Timestamp 와 Date 객체가 섞이지 않도록 주의해야 한다. <br>



**null 이 아닌 모든 참조값 x, y 에 대해 x.equals(y) == true 이고, y.equals(z) == true 이면 x.equals(z) 도 true 이다.**<br>

첫 번째 객체와 두 번째 객체가 같고, 두 번째 객체와 세 번째 객체가 같다면, 첫 번째 객체와 세 번째 객체도 같아야 한다는 뜻이다.<br>

<br>

언뜻 보면 굉장히 당연한 이야기이고, 쉬운 문제처럼 보여 가볍게 여기기 쉬운 개념이다. 책에서는 두 가지 경우를 예로 들어 추이성이 깨지는 경우를 설명하고 있다. 사실 이런 법칙은 단순히 Java 뿐만 아니라, 다른 언어를 사용해야 할때 equals() 류의 메서드를 overridding 할 때에도 경험을 높여줄 수 있는 요소인것 같다.<br>

**클래스 확장(extends - 상속)시에 equals()를 하위타입과 비교시, 추이성은 보존될까 ? **<br>

좌표를 표현하는 Point 라는 클래스가 있다고 하자. 그런데, 여기에 색상정보까지 포함하고 있는 ColorPoint 라는 클래스가 있다. ColorPoint 클래스는 Point 클래스를 상속하고 있다. 따라서 ColorPoint 클래스에 별다른 오버라이딩을 하지 않는 다면, ColorPoint 클래스의 equals() 메서드는 Point 클래스의 equals() 메서드를 사용하게 된다.<br>

이때 ColorPoint 클래스로 다른 Point 타입의 객체와 equals 연산을 할 수 있는 방법은 있을까?<br>

<br>

**클래스를 확장(extends - 상속)하는 방식으로 equals() 를 만족시키려 할 경우**<br>

결론부터 말하자면 구체 클래스를 확장(extends - 상속)하는 방식으로는 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다. 이 규칙이 어떤 때에 안맞고, 안맞는 경우들에 대한 설명은 아래에 따로 예시로 정리해두기로 했다.<br>

<br>

**상속 대신 컴포지션을 사용할 경우(아이템 18)**<br>

괜찮은 우회방법이다. **Point를 상속(확장 - extends)하는 대신 Point 를 ColorPoint 의 private 필드로 두고**, ColorPoint 와 같은 위치의 Point 를 반환하는 **뷰(view) 메서드(아이템 6)를 public 으로 추가하는 방식**이다.<br>

이 경우 역시 예제로 정리해두었다. 가끔 이런 경우를 보면, 어떤 문제를 어려워보이게 집착해서 풀기보다는 현명하게 우회해서 다른 예외의 경우는 제외하고올바른 비교를 하게끔 유도하는 방식으로 풀어내는 경우가 있다는 생각이 든다. 이렇게 view 메서드로 형변환을 해서 직접 올바른 타입을 사용해 비교하도록 유도하는 방식은 다른 언어에서도 유연하게 적용이 가능한 방식인 것 같고, 다른 언어에서도 경험이 많은 프로그래머가 비슷한 방식으로 정리한 내용들이 있지 않을까 싶다.(조슈아 블로크라는 자바의 아버지 격인 저자가 설명해주었듯이.)<br>

<br>

**리스코프 치환원칙**<br>

리스코프 치환원칙에 대해서도 설명이 나오는데, 여기에 따로 요약해서 정리 예정. (예제는 따로 정리하지 않을 예정이다.)<br>

<br>

#### case 1) 대칭성을 위배하는 경우

```java
public enum Color { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET }

class Point{
  private final int x;
  private final int y;

  public Point(int x, int y){
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Point)) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
  }
}

class ColorPoint1 extends Point{
  private final Color color;

  public ColorPoint1(int x, int y, Color color){
    super(x, y);
    this.color = color;
  }

  // 대칭성 위배.
  // Point 를 ColorPoint 와 비교한 결과와 이 둘을 비교한 결과가 다를 수 있다.
  // point.equals(cp) 는 true, 
  // cp.equals(point) 는 false 를 반환할 수 있다.
  @Override
  public boolean equals(Object o) {
    // 오직 ColorPoint 객체일 경우에 대해서만 비교를 수행할 수 있도록 필터링 로직을 추가
    if(!(o instanceof ColorPoint1)) return false;
    return super.equals(o) && ((ColorPoint1) o).color == color;
  }
}
```

위 코드는 ColorPoint 타입 입장에서 Point 타입 객체는 배제하고 오직 ColorPoint 만을 비교해 ColorPoint 타입의 객체에 한해서만 equals() 가 성립되도록 하는 코드다.<br>

위 코드에 대해서 아래와 같이 객체를 만들어서 비교를 수행한다고 해보자.

```java
@Test
public void 테스트_잘못된코드_대칭성위배(){
  Point p = new Point(1,2);
  ColorPoint1 cp = new ColorPoint1(1,2,Color.BLUE);

  System.out.println("p.equals(cp) == " + p.equals(cp));
  System.out.println("cp.equals(p) == " + cp.equals(p));
}
```

출력결과는 아래와 같다. 대칭성에 위배되어 버렸다.

```
p.equals(cp) == true
cp.equals(p) == false
```

<br>

- p.equals(cp) == true
  - Point 타입  p 입장에서는 ColorPoint 객체는 그냥 Point 타입처럼 보일 뿐이다. 따라서 Point 의 equals를 수행하므로 true 가 반환된다.
- cp.equals(p) == false
  - equals 내에서 ColorPoint 타입의 객체가 아니면 무조건 false 를 반환하도록 했다.
  - 즉, ColorPoint 는 오직 ColorPoint 와 비교하기 위해 ColorPoint 이외의 객체가 올때는 false 를 반환하도록 했다.
  - 따라서 ColorPoint 입장에서 Point 타입 객체는 다른 타입의 객체이므로 false 를 반환한다.

<br>

#### case 2) 추이성을 위배하는 경우

```java
public enum Color { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET }

class Point{
  private final int x;
  private final int y;

  public Point(int x, int y){
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Point)) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
  }
  
  // ColorPoint 의 equals 가 Point 와 비교할 때는 색상을 무시하도록 하는 경우
	// 추이성에 위배된다.
	// ColorPoint p1 과 Point p2 를 비교 == true
	// Point p2 와 ColorPoint p3 를 비교 == true
	// but, p1.equals(p3) == false
	class ColorPoint2 extends Point{
		private final Color color;

		public ColorPoint2(int x, int y, Color color){
			super(x,y);
			this.color = color;
		}


		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Point)) return false;

			if (!(o instanceof ColorPoint2)) return o.equals(this);

			return super.equals(o) && ((ColorPoint2) o).color == color;
		}
	}
}

```

<br>

추이성을 지키려면 위의 코드는 아래와 같은 조건을 만족해야 한다.<br>

> - ColorPoint 타입 p1 과 Point 타입 p2 가 같고, <br>
>
> - Point 타입 p2 와 ColorPoint 타입 p3가 같을 경우<br>
> - p1과 p3 는 같아야 한다.<br>

<br>

하지만, 위의 코드는 추이성을 지키지 못한다.<br>

테스트 코드를 보자.<br>

```java
@Test
public void 테스트_잘못된코드_추이성위배(){
  ColorPoint2 p1 = new ColorPoint2(1, 2, Color.RED);
  Point p2 = new Point(1,2);
  ColorPoint2 p3 = new ColorPoint2(1, 2, Color.BLUE);

  System.out.println("p1.equals(p2) ==> " + p1.equals(p2));
  System.out.println("p2.equals(p3) ==> " + p2.equals(p3));
  System.out.println("p1.equals(p3) ==> " + p1.equals(p3));
}
```

출력결과는 아래와 같다.<br>

```
p1.equals(p2) ==> true
p2.equals(p3) ==> true
p1.equals(p3) ==> false
```

p1 == p3 를 만족시키지 못하게 되어 버렸다.<br>

<br>

####  case 3) instanceof 비교 대신 getClass 로 비교를 할 경우

이 경우 리스코프 치환원칙을 위배하게 된다.<br>

**리스코프 치환원칙이란?**<br>

>  어떤 타입에 있어 중요한 속성이라면 그 하위 타입에서도 마찬가지로 중요하다. 

문장만 봐서는 정말 무슨 이야기인가 싶다. 예를 들어 정리해보면 이렇다.

- Fruit 라는 클래스(=타입)이 있다고 해보자. 그리고 이 Fruit 클래스(타입)을 상속받는 Mango 클래스가 있다고 해보자. 이 Fruit 클래스에 만약 price 라는 속성이 중요하다고 해보자. 
- 이때 Fruit 클래스에서 price 속성이 중요하면, Mango 클래스 역시도 price 속성이 중요해야 한다는 의미다.

<br>

정리해보면, 리스코프 치환원칙이 지켜져야 한다는 것은  

- Fruit 타입이 가진 모든 메서드는 하위 타입인 Mango 클래스에서도 똑같이 정상적으로 작동해야 한다는 것

을 의미한다.<br>

<br>

**instanceof 대신 getClass 를 사용할 경우**<br>

지금까지 Point 클래스를 사용해서 instanceof 를 사용해 equals 를 구현할 때 확장 클래스에 추이성을 지키려 했다가 추이성이 지켜지지 않았었다. <br>

instanceof 가 안되니 getClass 로 했을때도 되는지 확인해볼 수 있겠다. 결론만 말하자면 getClass 를 사용하는 코드를 사용할경우 추이성이 지켜지지 않는다. 이때 리스코프 원칙에 위배되게 된다.<br>

예를 들면 아래와 같은 equals 구문이 있다고 해보자. 지금까지는 equals() 내에서 instanceof 연산을 사용했었는데, 이번에는 instanceof 대신 getClass 를 사용한다.

**ex) instanceof 대신 getClass() 를 equals() 에서 사용하는 예**

```java
@Override
public boolean equals(Object o){
  if(o == null || o.getClass() != getClass())
    return false;
  Point p = (Point) o;
  return p.x == x && p.y == y;
}
```

<br>

위의 equals 는 같은 구현 클래스의 객체와 비교할 때만 true 를 리턴한다. 즉, io.fruit.Fruit 와 io.fruit.Fruit 타입의 객체를 비교할때만 true 이고, io.fuit.Fruit 와 io.fruit.Mango를 비겨할 때는 false 를 반환한다.<br>

위의 예제에서 살펴본 Point 의 하위 클래스는 정의상 여전히 Point 로 취급된다. ColorPoint도 Point 변수로 받으면 Point 로 취급받게 된다. 그런데, 이번 예제에서 사용한 getClass() 를 이용한 equals 에서는 Point p == ColorPoint cp 가 성립되지 않는다. 추이성 연산을 시작하기도 전에 깨진다. 이것을 리스코프 원칙을 위배한다고 의미한다.<br>

책에서는 아래의 예제를 하나 더 설명하고 있다. 그런데, 이번 문서에서는 생략하려 한다.<br>

<br>

#### case 3) 상속 대신 컴포지션을 사용해, 별도의 객체를 사용해 equals 를 수행하도록 유도하기

>  asPoint() 같은 함수를 통해 형변환을 수행하고, 변환된 타입과 비교하려는 객체를 비교하도록 하는 방식이다.

**상속관계의 객체들간의 equals 연산을 성립시키려 할 때 추이성을 지키기는 쉽지 않다.**<br>

지금까지 살펴본 색상이 적용된 Point 객체인 ColorPoint 와 일반 좌표를 표현하는 객체인 Point 간의 값 비교 연산은 서로 가지고 있는 변수가 다르기에 추이성을 지키기 쉽지 않았었다. 추이성이 뭔지 다시 한번 정리해보면 이런 개념이다.<br>

> x == y 이고 y==z 이면, x == z 이다. 

라는 개념이다. 결론부터 이야기 하자면, 이 추이성이라는 개념을 상속 개념으로는 지키기가 쉽지 않다.<br>

즉, 구체클래스를 확장(extends - 상속)해서 새로운 클래스를 정의해서 그 안에 새로운 값을 추가했을때, equals 규약을 만족시킬 방법은 존재하지 않는다.<br>

이런 현상은 모든 객체 지향 언어에서 동치 관계를 구현하려고 할때 나타나는 근본적인 문제다. <br>(cpp 에서도 동일한 문제가 나타날 수 다고 생각하니, 덜 억울하다)<br>

어찌 보면 수학의 집합간의 대응 등등 관련된 그런 문제하고 관련 있는 것 같은 원리라는 생각은 들었는데, 1회차 정리본에서 그리 깊게 생각하고 싶지 않다.<br>

**상속 대신 컴포지션을 사용하라 (아이템 18)**<br>

Point 를 상속받는 대신 Point 인스턴스를 ColorPoint 클래스 내의 멤버필드로 선언해둔다. 그리고 ColorPoint 내에서 일반 좌표계 Point 를 반환하는 뷰 (view) 메서드를 public 으로 추가하는 방식이다.

```java
public enum Color { RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET }

class Point{
  private final int x;
  private final int y;

  public Point(int x, int y){
    this.x = x;
    this.y = y;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof Point)) return false;
    Point p = (Point) o;
    return p.x == x && p.y == y;
  }
}

// 상속대신 컴포지션을 사용한다.
class ColorPoint3 {
  private final Point point;
  private final Color color;

  public ColorPoint3(int x, int y, Color color){
    point = new Point(x, y);
    this.color = Objects.requireNonNull(color);
  }

  public Point asPoint(){
    return point;
  }

  @Override
  public boolean equals(Object o) {
    if(!(o instanceof ColorPoint3)) return false;
    ColorPoint3 cp = (ColorPoint3)o;
    return cp.point.equals(point) && cp.color.equals(color);
  }

  @Override
  public int hashCode() {
    return 31 * point.hashCode() + color.hashCode();
  }
}
```

<br>

### 일관성(consistency)

- **null 이 아닌 모든 참조값 x,y 에 대해 x.equals(y) 를 반복해서 호출하면, 항상 true를 반환하거나 항상 false 를 반환한다.**<br>
- 두 객체가 같다면 앞으로도 영원히 같아야 한다. (단, 어느 하나 혹은 두 객체 모두가 수정되지 않는 한)

<br>

클래스를 만들때는 불변 클래스로 만드는게 나을지 심사숙고해서 만들어야 한다. (아이템 17) 이렇게 불변 클래스로 만든 후, equals가 한번 같다고 한 객체와는 영원이 같아야 하고, 다르다고 한 객체와는 영원히 다르다고 답하게끔 만들어야 한다.<br>

<br>

**일관성을 지키지 못하는 경우**<br>

equals가 일관성을 지키기 위해서는 한번 수행한 equals 의 결과값이 여러번을 반복해도 같은 결과를 내어야 한다. 하지만 외부적인 요인에 의해 비교가 항상 달라지는 경우가 있다. 예를 들면 네트워크와 관련된 질의이다. 네트워크는 네트워크가 끊기는 경우도 있고, 도메인 네임은 그대로이지만, 도메인 네임에 대한 IP 주소가 바뀌어 있을 수도 있다. 즉, 값의 비교를 수행할 때 일관성을 지키기가 쉽지 않다. 외부적인 요인에 의해 달라지기 때문이다.<br>

<br>

**일관성을 지키지 못한 자바 라이브러리 - equals에 사용하면 안되는 예**<br>

Java.net.URL 클래스가 그렇다. equals 는 주어진 URL과 매핑된 호스트의 IP 주소를 이용해 비교한다. 호스트 이름을 IP 주소로 바꾸려면 네트워크를 통해야 하는데 그 결과가 항상 같음을 보장하기 어렵다. Java.net.URL 의 equals 를 이렇게 구현한 것은 커다란 실수였다. 현재는 java.net.URL 클래스의 하위 호환성이 발목을 잡아서 잘못을 바로잡을수도 없다.<br>

<br>

위와 같은 문제들을 피하려면 가급적 equals는 항시 메모리에 존재하는 객체만을 사용해 결정적(deterministic) 계산만 수행하게끔 해주어야 한다.<br>

<br>

### null-아님

**null 이 아닌 모든 참조값 x 에 대해 x.equals(null) 은 false 다.**<br>

> 공식적으로 용어가 따로 없다. 그래서 책의 저자인 조슈아 블로크는 `null-아님` 으로 이번 원칙을 설명하고 있다. 절대 내가 지은 이름이 아니다.

이 null-아님 이라는 원칙은, 생성된 모든 객체가 null 과 같디 않아야 한다는 의미다. 이것을 조금 직관적으로 코드로 이야기하자면 위에 적었듯이 x.equals(null) == false 여야 한다(단, x != null). 이다. <br>

이 null-아님 규칙을 지키기 위해 불필요한 코드를 사용하는 것을 피하라고 저자는 이번 챕터에서 이야기하고 있다. 예를 들면 아래와 같은 코드다.

```java
// ...
@Override public boolean equals(Object o){
  // 명시적 null 검사 = 필요 없다!
  if(o == null) return false;
  // ...
}
```

위와 같은 코드는 그리 필요치 않다. 대신 instanceof 를 통해 입력 매개변수의 타입을 검사하는 과정에서 null 아님을 파악할 수있게 된다. 예를 들면 아래와 같다.<br>

```java
// 묵시적 null 검사 - 이게 더 낫다.
@Override public boolean equals(Object o){
  if(!(o instanceof MyType))
    return false;
  MyType mt = (MyType) o;
  ...
}
```

instanceof 는 두번째 피연산자와 무관하게 첫번째 피연산자가 null 이면 false 를 반환한다. 따라서 입력이 null 이면 확인 단계에서 false 를 반환하므로 null 검사를 명시적으로 하지 않아도 된다.<br>

<br>

## equals 구현 절차

양질의 equals 메서드를 구현시 주의할 사항은 아래와 같다.

- 1 ) == 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다.
  - 자기 자신이면 true 를 반환한다.
  - 성능 최적화를 위한 용도다.
  - 비교작업이 복잡한 상황에서 값어치를 한다.
- 2 ) instanceof 연산자로 입력이 올바른 타입인지 확인한다.
  - 그렇지 않다면 false 를 반환한다.
  - instanceof 연산자로 비교할수 있는 equals가 정의된 올바른 타입이어야 한다.
  - 특정 인터페이스 A 를 구현한 클래스 A1, A2 끼리도 서로 비교할 수 있도록 특정 인터페이스의 equals 규약을 수정하기도 한다.
    - 이런 경우 구현 클래스 A1, A2 는 equals 에서 A1, A2 자신의 equals 가 아닌 인터페이스 A의 equals 를 사용하도록 코드가 구현되어 이어야 한다.
    - 예를 들면 Set, List, Map, Entry 등의 컬렉션 인터페이스들이 여기에 해당된다.
- 3 ) 입력을 올바른 타입으로 형변환 한다.
  - 2 ) 에서 instanceof 검사를 잘 수행한다면 이 단계는 100% 성공한다.
- 4 ) 입력 객체와 자기 자신의 대응되는 '핵심'필드 들이 모두 일치하는지 하나씩 검사한다.
  - (여기서 '핵심'필드라는 것은 모든 필드라는 것이 아니고, 클래스 A와 클래스 B를 비교시 값이 같다고 판단하는데에 사용할 필드들을 의미한다. 위에서 살펴본 내용.)
  - '핵심'필드의 모든 필드와 일치하려면 true 를, 하나라도 다르면 false 를 반환한다.
  - 2단계에서 인터페이스를 사용했다면 ?
    - 입력의 필드 값을 가져올 때도 해당 인터페이스의 메서드를 사용해 값을 가져와야 한다.
  - 타입이 클래스일 경우 
    - 접근 권한에 따라 해당 필드에 직접 접근할 수도 있다.

<br>

## equals 구현시 주의할 점들

### final, double, 참조타입 필드, float, double 필드 비교

final 과 double을 제외한 기본 타입 필드는 `==` 연산자로 비교한다. <br>

참조타입 필드는 각각의 equals 메서드로 비교한다.<br>

float, double 필드는 각각 정적 메서드인 Float.compare(float, float), Double.compare(double, double)로 비교한다.<br>

float 과 double 을 특별히 다르게 취급하는 이유는 Float.NaN, -0.0f 등 특수한 부동 소수 값들을 다뤄야 하기 때문이다. 자세한 설명은 [JLS 15.21.1] 이나 Float.equals 의 API 문서를 참고하면 된다.<br>

Float.compare, Double.compare 메서드 대신 Float.equals, Double.equals 메서드를 사용할 수도 있겠으나, 이 메서드들은 오토박싱을 수반할 수도 있다. 따라서 성능상 좋지 않을 수도 있다.<br>

<br>

### 배열 필드 비교

배열 필드는 원소 각각을 앞서의 지침대로 비교한다. 배열의 모든 원소가 핵심필드라면 Arrays.equals 메서드 들 중 하나를 사용한다.

<br>

### null 도 정상으로 취급하는 참조타입필드 비교

때로는 null 값도 정상 값으로 취급하는 참조타입 필드들이 있다. 이런 필드는 정적 메서드로 구현되어 있는 Objects.equals(Object, Object) 를 사용해 비교를 수행해 Null Pointer Exception  발생을 예방한다. <br>

<br>

### 비교하기 까다로운 필드를 가지고 있는 경우

앞서 살펴본 CaseInsensitiveString 클래스 처럼 비교하기에 아주 복잡한 필드를 가진 클래스도 있다. 이럴 경우 그 필드의 표준형 (Canonical Form)을 저장해 둔 후에 표준형끼리 비교하면 훨씬 경제적이다. 이런 방식은 특히 불변 클래스 (아이템 17)에 적합한 방식이다. 가변 객체라면 값이 바뀔 때마다 표준형을 최신 상태로 갱신해줘야 한다.<br>

<br>

### 필드 비교 순서

어떤 필드를 먼저 비교하는지 역시 equals 의 성능을 좌우한다. 최상의 성능을 원할 경우 다를 가능성이 더 크거나 비교하는 비용이 싼 (또는 두가지 모두 해당하는) 필드를 먼저 우선순위에 두고 비교한다.<br>

또한, 동기화를 위해 생성한 lock 필드와 같은 논리적인 값과 상관 없는 필드를 비교대상으로 하면 안된다.<br>

파생필드 비교가 핵심필드 비교보다 훨씬 더 빠른 경우도 고려해야 한다.<br>

파생 필드가 객체 전체의 상태를 대표하는 상황이 그렇다. 예를 들어 자신의 영역을 캐시해두는 Polygon 클래스가 있다고 해보자. 이 경우 모든 면,정점을 하나 하나 일일이 비교할 필요가 없다. 캐시해둔 영역만 비교하면 결과를 곧바로 확인할 수 있다.<br>

<br>

## AutoValue

equals,hashCode 를 작성하고 테스트하는 일은 지루하고 이를 테스트 하는 코드도 항상 뻔하다. 다행히 이 구글에서 만든 AutoValue 프레임워크를 사용하면 이 작업을 대신해준다. 클래스에 애너테이션 하나만 추가하면 AutoValue가 이 메서드 들을 알아서 작성해주며, 직접 작성하는 것과 근본적으로 같은 코드를 만들어준다.<br>

대다수의 IDE 도 같은 기능을 제공하지만, 생성된 코드가 AutoValue 만큼 깔끔하거나 읽기 좋지는 않다. 또한 IDE 는 나중에 클래스가 수정된 것을 자동으로 알아채지는 못하기에 테스트 코드를 작성해야 한다.<br>

이런 단점을 감안해도, 사람이 직접 equals 를 작성하는 것보다는 IDE에 맡기는 편이 좋다. 적어도 사람처럼 부주의한 실수를 저지르지는 않는다.<br>

여기까지가 조슈아 블로크의 의견이고, 내 의견은 아직 AutoValue를 사용하지 않고 작성하는게 나을지 판단이 잘 안선다. 아직 뭔가 equals를 수행해야 하는 컬렉션 클래스들을 커스터마이징한 프레임워크 같은걸 만들어본적도 없다. 비슷한 책을 최근에 출퇴근할 때 조금씩 읽고 있기는 한데 equals 까지 내용을 다루는지는 나도 잘 모르겠다.



