# 아이템 14 - Comparable을 구현할지 고려하라

Comparable 인터페이스의 유일 무이한 compareTo 메서드에 대해 알아보자. compareTo 는 Object 의 메서드가 아니다. compareTo 는 Object 의 equals 에서 제공하는 단순 동치성 비교를 가지고 있다. 여기에 더해 1) 순서를 비교할 수 있고 2) 네네릭하다.<br>

즉, compareTo 메서드는 각 필드가 동치인지를 비교하는 것이 아니라, 그 순서를 비교한다.<br>

Comparable 을 구현했다는 것은 그 클래스의 인스턴스들은 자연적인 순서(natural order)가 있음을 뜻한다. Comparable 을 사용하면, Comparable 을 활용하는 수많은 제네릭 알고리즘과 컬렉션의 힘을 누릴 수 있다. 실제로 자바 플랫폼 라이브러리의 모든 값 클래스와 열거 타입(아이템 34)이 Comparable 을 구현했다.<br>

알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable 인터페이스를 구현하자.<br>

**Comparable 인터페이스**<br>

```java
public interface Comparable<T>{
  int compareTo(T t);
}
```

<br>

**Comparable의 compareTo 메서드와 Comparator**<br>

compareTo 메서드는 각 필드가 동치인지를 비교하는 것이 아니라 그 순서를 비교한다. Comparable 을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야 한다면 비교자(Comparator)를 대신 사용한다. Comparator 는 직접 만들거나 자바가 제공하는 것 중에 골라 쓰면 된다.<br>

<br>

Comparable 은 타입을 인수로 받는 제너릭 인터페이스이기 때문에 compareTo 메서드의 인수 타입은 컴파일 타임에 정해진다. 즉 입력인수의 타입을 확인하거나 형변환할 필요가 없다.

## 핵심정리

순서를 고려해야 하는 값 클래스를 작성한다면 꼭 Comparable 인터페이스를 구현하여 그 인스턴스들을 쉽게 정렬하고 검색하고 비교 기능을 제공하는 컬렉션과 어우러지도록 해야 한다. compareTo 메서드에서 필드의 값을 비교할 때 `<` 와 `>` 연산자는 쓰지 말아야 한다.<br>

그 대신 박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드나 Comparator 인터페이스가 제공하는 비교자 생성 메서드를 사용하자.<br>

<br>

## 단순 사용예

Comparable 인터페이스를 implements 한 클래스의 객체들의 배열은 아래와 같이 손쉽게 정렬가능하다.

```java
Arrays.sort(a);
```

<br>

아래의 코드는 TreeSet 에 명령행 인자들을 추가하는데, 이 명령행 인자들을 알파벳 순으로 출력하는 예제다.

```java
public class WordList {
  public static void main(String [] args){
    Set<String> s = new TreeSet<>();
    Collections.addAll(s, args);
    System.out.pritntln(s);
  }
}
```

<br>

## compareTo 메서드의 규칙

Comparable 인터페이스의 compareTo 메서드는 아래와 같이 구현한다. 이렇게 구현하면, 크기 비교, 순서를 비교할 수 있는 값들의 컬렉션 들에 대해 정렬 연산을 수행할 수 있게 된다.

compareTo 메서드는 자기자신과 주어진 객체의 순서를 비교한다. 

- 자기자신이 주어진 객체보다 작으면 음의 정수를 리턴하고, 
- 자기자신이 주어진 객체와 같으면 0을 리턴하고
- 자기자신이 주어진 객체보다 크면 양의 정수를 리턴한다.

자기자신과 비교할 수 없는 타입의 객체가 인자로 전달되면 ClassCastException 을 던진다. 모든 객체에 대해 전역 동치 관계를 부여하는 equals() 메서드와는 달리 compareTo 메서드는 타입이 다른 객체를 신경쓰지 않아도 된다. 타입이 다른 객체가 주어지면, 간단히 ClassCastException 을 던져도 되며, 대부분 그렇게 한다.<br>

<br>

**compare 연산에 대한 규칙들. equals() 와는 유사하지만 조금은 다른 것들**<br>

compareTo 메서드를 구현시에 Comparable 에 의해 비교시 일관되게 정렬된 순서가 적용된 결과를 얻어내려면 아래의 규칙들이 지켜져야 한다. 이때 지켜야 하는 규칙들은 equals() 의 일반적인 규칙과 비슷하지만 다른 점들이 몇가지 있다.<br>

> 아래에서, sgn(표현식) 표기는 수학에서 말하는 부호함수(signum function)를 뜻한다. 표현식의 값이 음수, 0, 양수일 때 -1, 0, 1 을 반환하도록 정의하고 있다.<br>

<br>

아래의 세 규약은 compareTo 메서드로 수행하는 동치성 검사도 equals 규약과 마찬가지로 반사성, 대칭성, 추이성을 충족해야 함을 의미하고 있다.

- (첫번째) 두 객체 참조의 순서를 바꿔 비교해도 예상한 결과가 나와야 한다.
  - Comparable 을 구현한 클래스는 모든 x,y 에 대해 `sgn(x.compareTo(y)) == -sgn(y.compareTo(x))` 여야 한다.
  - 따라서, x.compareTo(y) 는 y.compareTo(x) 가 예외를 던질 때에 한해 예외를 던져야 한다.

- (두번째) 첫번째가 두번째보다 크고 두번째가 세번째보다 크면, 첫번째는 세번째보다 커야 한다.
  - Comparable 을 구현한 클래스는 추이성을 보장해야 한다.
  - 즉, (x.compareTo(y) > 0 && y.compareTo(z) > 0) 이면 x.compareTo(z) > 0 이다.
- (세번째) 크기가 같은 객체들끼리는 어떤 객체와 비교하더라도 항상 같아야 한다.
  - Comparable 을 구현한 클래스는 모든 z에 대해 x.compareTo(y) == 0 이면 sgn(x.compareTo(z)) == sgn(y.compareTo(z)) 다.
- (optional) 크기가 같을 경우 equals() 의 결과와 같아야 하는 규칙. compareTo 메서드 구현에서 필수는 아니지만, 권장사항이다.
  - (x.compareTo(y) == 0) == (x.equals(y)) 여야 한다.
  - Comparable 을 구현하고 나서, 이 권고조항을 지키지 않는 모든 클래스는 이 사실을 명시해야 한다.
  - 예를 들면 아래와 같이 명시하면 적당하다.
    - "주의: 이 클래스의 순서는 equals 메서드와 일관되지 않다."

<br>

**주의사항**<br>

**클래스를 확장(extends)한 구체 클래스에서 새로운 값 컴포넌트를 추가하면 compareTo 규약을 지킬 방법이 없다.**<br>

Comparable 을 구현한 클래스를 확장해 값 컴포넌트를 추가하고 싶다면, 확장하는 대신 독립된 클래스를 만들고, 이 클래스에 원래 클래스의 인스턴스를 가리키는 필드를 두자.<br>

추상화를 통해 abstract 클래스를 상속받아 객체지향의 이점을 취하려 할 때 상속구조를 이용하는 데, 이 때 extends 하는 확장 클래스 내에서는 새로운 값 컴포넌트에 compareTo 규약이 적용되도록 할 방법이 없다는 점이 문제다.<br>

이 단점을 우회하기 위해 어댑터 패턴과 유사한 방식을 사용하게 된다. 독립된 클래스를 만들고 이 클래스에 원래 클래스의 인스턴스를 가리키는 필드를 둔다. 그런다음 내부 인스턴스를 반환하는 '뷰'메서드를 제공하면,바깥 클래스에 우리가 원하는 compareTo 메서드를 구현해 넣을 수 있다. 그리고, 클라이언트는 필요에 따라 바깥 클래스의 인스턴스를 필드 안에 담긴 원래 클래스의 인스턴스로 다룰 수도 있게 된다.<br>

<br>

**compareTo 의 마지막 규약은 필수는 아니지만, 꼭 지키도록 권장되는 편이다.**<br>

위에서 살펴본 마지막 규약은 `compareTo 메서드로 수행한 동치성 테스트의 결과가 equals와 같아야 한다` 였다.이것을 잘 지키면 compareTo로 줄지은 순서와 equals() 의 결과가 일관되게 된다.<br>

참고로, 위의 규약을 지키지 않으면 compareTo로 계산한 결과와 equals 로 계산한 결과가 일관되지 않는다. 이렇게 일관되지 않은 경우도 일단 동작은 한다. 하지만, 이 클래스의 객체를 정렬된 컬렉션에 넣으려 할 경우 해당 컬렉션(ex. TreeSet)이 구현한 인터페이스(Collection, Set, Map)에 정의된 동작과 엇박자를 내게 된다.<br>

Collection, Set, Map 인터페이스들은 equals 메서드의 규약을 따른다고 되어 있지만, 놀랍게도 정렬된 컬렉션들은 동치성을 비교할 때 equals 대신 compareTo를 사용한다. 아주 큰 문제는 아니지만, 주의해야 한다. (당연한 이야기이긴 하겠지만, 개발 작업시 꼭, 해당 구현체 컬렉션의 내부 구현을 어느 정도는 확인해보고 구현해야 한다.)<br>

<br>

**ex) BigDecimal**<br>

> BigDecimal 클래스는 compareTo와 equals 가 일관되지 않는다.
>
> ```java
> Set sample = new HashSet();
> sample.add(new BigDecimal("1.0"));
> sample.add(new BigDecimal("1.00"));
> ```
>
> 여기서 `new BigDecimal("1.0")` 과 `new BigDecimal("1.00")` 은 equals 로 비교하면 서로 다르다. 따라서 HashSet은 원소를 2개 갖게 된다.<br>
>
> 하지만, HashSet이 아닌 TreeSet 을 사용할 경우 원소를 하나만 갖게 된다. compareTo 메서드로 `new BigDecimal("1.0")` 과 `new BigDecimal("1.00")`  인스턴스를 비교하면 같은 인스턴스로 판정되기 때문이다.

<br>

## compareTo 메서드 작성 요령

아래의 몇가지 차이점을 제외하면 compareTo 메서드 작성 요령은 equals 와 비슷하다. 

- compareTo 메서드의 인수의 타입으로 null 이 인수로 전달된다면, `NullPointerException` 을 던져야 한다.
- Comparable 을 구현하지 않은 필드나 표준이 아닌 순서로 비교해야 한다면 비교자 (Comparator)를 대신 사용하는 것이 권장된다.
  - Comparator 는 직접 만들어 사용하거나, Java 에서 기본으로 제공하는 것을 사용할 수 있다.
  - 예를 들면 아래와 같은 코드가 Comparator 를 사용하는 코드다.

<br>

```java
public final class CaseInsensitiveString implements Comparable<CaseInsensitiveString> {
  public int compareTo(CaseInsensitiveString cis){
    return String.CASE_INSENSITIVE_ORDER.compare(s, cis, s);
  }
}
```

