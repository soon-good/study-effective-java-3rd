# ITEM 55. 옵셔널 반환은 신중히 하라

Optional 을 사용하는 것은 처음에는 어렵게 느껴지지만, 자주 사용하다보면 익숙해지게 된다. 테스트 코드 위에서 다이나믹하게 매우 자주 초록불과 빨간불을 보면서 코딩을 하다보면, 옵셔널을 어떻게 쓰는지 굉장히 직관적으로 이해할 수 있지 않을까 싶다.<br>

<br>

## 핵심정리

값을 반환하지 못할 가능성이 있고, 호출할 때 마다 반환값이 있을 가능성을 염두에 둬야 하는 메서드라면 옵셔널을 반환해야 할 상황일 수 있다. 하지만 옵셔널 반환에는 성능저하가 뒤따르니, 성능에 민감한 메서드라면 `null` 을 반환하거나 예외를 던지는 편이 나을 수 있다. 그리고 **옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.**(매우 동감!!)<br>

<br>

## 값이 없거나 null 데이터일 경우의 처리 - Java 8 이전 vs Java 8 이후

Java 8 이전

- 예외 던진다.
- `null` 을 리턴한다.
  - 별도의 null 처리 코드가 기하급수적으로 늘어난다는 것이 단점

Java 8 이후

- `Optional<T>` 라는 선택지
- 옵셔널은 원소를 최대 1 개 가질수 있는 `불변` 컬렉션 (장점이다.)
- 예외를 던지는 메서드보다 유연하다. 사용하기 쉽다.

<br>

## 예제 1) Optional을 사용하는 코드로 전환 후 로직이 단순해 지는 예

> 참고 : 아이템 30 - 이왕이면 제너릭 메서드로 만들라<br>

BEFORE

```java
public static <E extends Comparable<E>> E max(Collection<E> c){
  if(c.isEmpty())
    throw new IllegalArgumentException("빈 컬렉션");
  
  E result = null;
  for(E e : c)
    if (result == null || e.compareTo(result) > 0)
      result = Objects.requireNonNull(e);
  
  return result;
}
```

<br>

AFTER

```java
public static <E extends Comparable<E>> Optional<E> max(Collection<E> e){
  if(c.isEmpty())
    return Optional.empty();
  
  E result = null;
  
  for(E e : c)
    if(result == null || e.compareTo(result) > 0)
      result = Objects.requireNonNull(e);
  
  return Optional.of(result);
}
```

<br>

## Optional.empty(), Optional.of(), Optional.ofNullable()

> 옵셔널을 반환하는 메서드에서 `null` 값을 명시적으로 던지지 말자.<br>

<br>

위의 예제에서는 Optional.empty(), Optional.of() 라는 팩터리 메서드를 사용했다.<br>

옵셔널에서 제공하는 팩터리 메서드 들 중 반환값을 처리할 때 자주 쓰이는 메서드는 아래와 같다.<br>

- Optional.empty()
  - 빈값을 처리할 때 사용. 비어 있는 옵셔널
- Optional.of()
  - 값이 들어있는 옵셔널. 널 값은 허용하지 않는 메서드
  - 만약, Optional.of()에 null 값을 넘기면, `NullPointerException` 을 던진다.
- Optional.ofNullable()
  - null 값도 허용하는 옵셔널을 만들 때 사용

<br>

## 스트림의 종단 연산 들 중 상당 수는 옵셔널을 반환한다 ( `예제 1`  을 스트림연산으로 바꿔보자 )

스트림의 종단연산 들 상당수가 옵셔널을 반환한다. `예제 1` 을 스트림연산으로 바꿔보자.

```java
public static <E extends Comparable<E>> Optional<E> max (Comparable<E> c){
  return c.stream().max(Comparator.naturalOrder());
}
```

<br>

## 옵셔널을 반환해야 하는 경우에 대한 선택 기준

- 옵셔널은 검사예외(CheckedException)과 취지가 비슷하다.
- `Optional.empty` 일 수 있음을 명확히 한다는 것이 장점이다.

<br>

어떤 함수에서 비검사예외를 throw 하거나, null을 리턴하는 경우

- 처리가 쉽지 않고 예상치 못한 결과를 낼 수 있다.

반면, 검사예외를 throw 하면

- 클라이언트 측에서 대응 코드를 작성하게 된다.

<br>

책에서는 이렇게 비검사예외와 검사예외의 차이를 비교하면서 Optional 이 검사예외처럼 클라이언트 측에서 대응코드를 작성하게 한다는 점에서 Optional을 사용하는 것의 장점을 언급하고 있다. <br>

참고로, 검사 예외는 굉장히 신중하게 throw 해야 한다. 하지만 Optional 은 검사예외 만큼 신중하게 사용하지 않아도 된다. 즉, 검사예외보다는 편리하다.(위의 내용 까지는 책에서 언급하는 비유였는데, 뺄지 말지 고민을 하다가, 빼지를 못하겠어서 정리는 해두었다. 개인적으로는 굳이 검사 예외로까지 비교할 필요는 없다는 생각이 있다.)<br>

<br>

개인적으로는, 검사예외와 비슷하다고 하다고 이야기를 하면, 이것으로 인한 여러가지 오해가 있을 수 있기에, 값이 없을 수 있음을 `NullPointerException` 없이 명확하게 표현해준다는 점이 굉장한 장점이라고 정리하는게 더 낫지 않을까 하고 생각했다.<br>

예를 들면, 값이 비어있을 수도 있는 메서드를 호출해서 반환값을 처리할 때 아래와 같은 경우 들로 처리하는 것이 가능해진다.<br>

Ex1) 

```java
public void orderSomething(){
  Optional<String> menu = getMenu();
  
  menu.ifPresent(m -> {
    orderService.order(m);
  });
}
```

또는 아래와 같이 처리하는 것도 가능하다.<br>

<br>

Ex 2)

```java
public void orderSomething(){
  Optional<String> menu = getMenu();
  if(menu.isEmpty()) return;
  
  orderService.order(m);
}
```

<br>

## 값이 없을 경우에 대한 다양한 처리 - orElse, orElseGet, orElseThrow, get

테스트코드 없이 말로만 정리하는 건 또 너무 딱딱하다 역시. 그래서 테스트 코드를 준비했다\~

**orElse**<br>

```java
@Test
public void TEST_OR_ELSE(){
    String word1 = "안뇽하세요";
    String optWord1 = Optional.ofNullable(word1).orElse("인사말을 입력해주세요!!!");
    System.out.println(optWord1);

    String word2 = null;
    String optWord2 = Optional.ofNullable(word2).orElse("인사말을 입력해주세용!!!");
    System.out.println(optWord2);
}

```

<br>

**출력결과**<br>

```plain
안뇽하세요
인사말을 입력해주세용!!!
```

<br>

**orElseGet**<br>

orElseGet에 들어가는 인자값은 `Supplier<T>` 다. 

```java
@Test
public void TEST_OR_ELSE_GET(){
    String word1 = "안뇽하세요요용!!!";
    String optWord1 = Optional.ofNullable(word1).orElseGet(() -> "반갑습니다!!!");
    System.out.println(optWord1);

    String word2 = null;
    String optWord2 = Optional.ofNullable(word2).orElseGet(() -> "반갑습니당 !!!");
    System.out.println(optWord2);
}
```

출력결과

```plain
안뇽하세요요용!!!
반갑습니당 !!!
```

<br>

**orElseThrow**<br>

orElseThrow 는 Optional 로 받은 값이 비어있는 값일 때 Exception 을 던진다.

```java
@Test
public void TEST_OR_ELSE_THROW(){
    String word1 = "반갑습니당";
    String optWord1 = Optional.ofNullable(word1).orElseThrow(RuntimeException::new);
    System.out.println(optWord1);

    String word2 = null;
    assertThatThrownBy(()->{
        Optional.ofNullable(word2).orElseThrow(RuntimeException::new);
    }).isInstanceOf(RuntimeException.class);

    assertThatThrownBy(()->{
        Object word3 = Optional.empty().orElseThrow(RuntimeException::new);
        System.out.println(word3);
    }).isInstanceOf(RuntimeException.class);

    assertThatThrownBy(()->{
        Object o = Optional.empty().orElseThrow(() -> new RuntimeException("비어있는 값은 처리할 수 없어요!!!"));
    }).isInstanceOf(RuntimeException.class)
        .hasMessageContaining("처리할 수 없어요!!!");
}
```

<br>

**출력결과**<br>

```plain
반갑습니당
```

<br>

**Optional.get**<br>

`Optional` 에 값이 있다는 확신이 든다면, 곧바로 `Optional.get()` 메서드로 값을 꺼내서 사용하는 것도 나쁜 방법은 아니다. <br>

```java
@Test
public void TEST_IF_OPTIONAL_HAS_VALUE_YOU_CAN_USE_OPTIONAL_GET_RIGHT_NOW(){
    Optional<String> notEmpty = Optional.of("TEST");

    if(notEmpty.isEmpty()) return; // 옵셔널에 값이 없으면 리턴해버린다.

    String s = notEmpty.get();  // 이미 값이 있는 경우에만 거치도록 필터링되어 있는 상태다.
    System.out.println(s);
}
```

<br>

출력결과

```plain
TEST
```

<br>

## filter, map, flatMap, ifPresent

**Optional.filter**<br>

예제

```java
@Test
public void OPTIONAL_FILTER(){
    Optional<Integer> odd = Optional.ofNullable(3)
        .filter(num -> num % 2 == 0);

    System.out.println(odd);
    assertThat(odd).isEmpty();

    Optional<Integer> even = Optional.ofNullable(2)
        .filter(num -> num % 2 == 0);
    System.out.println(even);
    assertThat(even).isNotEmpty();
}
```

출력결과

```plain
Optional.empty
Optional[2]
```

<br>

**Optional.map**<br>

예제

```java
@Test
public void OPTIONAL_MAP(){
    String hello1 = "Hello";
    Optional<Integer> optNumber1 = Optional.ofNullable(hello1)
        .map(str -> str.length());
    System.out.println(optNumber1);
    assertThat(optNumber1).isNotEmpty();

    String hello2 = null;
    Optional<Integer> optNumber2 = Optional.ofNullable(hello2)
        .map(str -> str.length());
    System.out.println(optNumber2);
    assertThat(optNumber2).isEmpty();
}
```

<br>

출력결과

```plain
Optional[5]
Optional.empty
```

<br>

나머지는 내일부터...!!!

**Optional.flatMap**<br>





## isPresent

<br>

























