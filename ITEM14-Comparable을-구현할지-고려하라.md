# 아이템 14 - Comparable을 구현할지 고려하라

Comparable 인터페이스의 유일 무이한 compareTo 메서드에 대해 알아보자. compareTo 는 Object 의 메서드가 아니다. compareTo 는 Object 의 equals 에서 제공하는 단순 동치성 비교를 가지고 있다. 여기에 더해 1) 순서를 비교할 수 있고 2) 네네릭하다.<br>

Comparable 을 구현했다는 것은 그 클래스의 인스턴스들은 자연적인 순서(natural order)가 있음을 뜻한다. Comparable 을 사용하면, Comparable 을 활용하는 수많은 제네릭 알고리즘과 컬렉션의 힘을 누릴 수 있다. 실제로 자바 플랫폼 라이브러리의 모든 값 클래스와 열거 타입(아이템 34)이 Comparable 을 구현했다.<br>

알파벳, 숫자, 연대 같이 순서가 명확한 값 클래스를 작성한다면 반드시 Comparable 인터페이스를 구현하자.<br>

**Comparable 인터페이스**<br>

```java
public interface Comparable<T>{
  int compareTo(T t);
}
```

<br>

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

compareTo 메서드는 일반적인 equals의 규칙과 비슷하지만, 다른점 몇가지가 있다.

- 
- 





