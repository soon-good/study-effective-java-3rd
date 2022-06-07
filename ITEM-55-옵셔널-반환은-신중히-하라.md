# ITEM 55. 옵셔널 반환은 신중히 하라

Optional 을 사용하는 것은 처음에는 어렵게 느껴지지만, 자주 사용하다보면 익숙해지게 된다. 테스트 코드 위에서 다이나믹하게 매우 자주 초록불과 빨간불을 보면서 코딩을 하다보면, 옵셔널을 어떻게 쓰는지 굉장히 직관적으로 이해할 수 있지 않을까 싶다.<br><br>

## 핵심정리

값을 반환하지 못할 가능성이 있고, 호출할 때 마다 반환값이 있을 가능성을 염두에 둬야 하는 메서드라면 옵셔널을 반환해야 할 상황일 수 있다. 하지만 옵셔널 반환에는 성능저하가 뒤따르니, 성능에 민감한 메서드라면 `null` 을 반환하거나 예외를 던지는 편이 나을 수 있다. 그리고 **옵셔널을 반환값 이외의 용도로 쓰는 경우는 매우 드물다.**<br>

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

**Optional.flatMap**<br>

> 참고한 예제 : [Java 8 Optional flatMap() Method Example](https://java8example.blogspot.com/2019/08/optional-flatmap.html) <br>

Optional 이 아니더라도 flatMap 은 보통 Stream 에서는 Stream 안에서 Stream을 만들어내서 서로 다른 두 Stream을 합칠때 사용하는 편이다. flatMap 에 대해 감이 잘 안잡힌다면 [은혜로운 flatMap](https://github.com/soon-good/modern-java-in-action/blob/develop/%EC%9E%90%EB%B0%94%EA%B0%9C%EB%B0%9C%EC%9E%90%EB%A5%BC-%EC%9C%84%ED%95%9C-97%EA%B0%80%EC%A7%80-%EC%A0%9C%EC%95%88/47.%EC%9D%80%ED%98%9C%EB%A1%9C%EC%9A%B4-flatMap-%EB%8B%A4%EB%8B%88%EC%97%98-%EC%9D%B4%EB%85%B8%ED%98%B8%EC%82%AC.md) 을 참고하자.<br>

**예제 1)**<br>

`hello2` 는 옵셔널 하나를 감싸는 중첩 옵셔널이다.<br>

즉 `hello2` 는 옵셔널 두개가 중첩되어 있다.<br>

그런데 아래 구문을 실행하고 나면 중첩되어 있던 `Optional` 이  `Optional` 하나로 변환되는 것을 확인할 수 있다.

```java
@Test
public void TEST_OPTIONAL_FLATMAP_CASE1(){
    Optional<String> hello1 = Optional.of("Hello1");
    Optional<Optional<String>> hello2 = Optional.of(hello1);

    Optional<String> result = hello2.flatMap(optHello -> optHello.map(String::toUpperCase));
    System.out.println(result);

    assertThat(result).isNotEmpty();
}
```

<br>

출력결과<br>

```plain
Optional[HELLO1]
```

<br>

예제 2)<br>

- `--1)`
  - `Optional<Optional<Optional<Double>>> ` 타입의 `calorie3` 를 그대로 출력하고 있다.
- `--2)` 
  - `Optional<Optional<Optional<Double>>>` 을 `Optional<Double>` 로 변환해서 출력하고 있다.
- `--3)`
  - `Optional<<Optional<Optional<Double>>>`  을 `Optional<Double>` 로 변환해서 출력하고 있다.

`--2` 와 `--3` 의 차이점은 중간에 `Optional.isPresent()` 로 값이 존재하는지 처리를 할지 `Optional.map` 메서드로 null 이 없으면 없는대로 리턴하게끔 처리를 할지 결정한다는 점이 다르다. 두 코드 모두 의도하는 결과값은 같다. 두 방식중에 마음에 드는 것을 사용하면 될 것 같다.<br>

```java
@Test
public void TEST_OPTIONAL_FLATMAP_CASE2(){
    Optional<Double> calorie = Optional.of(1.1D);
    Optional<Optional<Double>> calorie2 = Optional.of(calorie);
    Optional<Optional<Optional<Double>>> calorie3 = Optional.of(calorie2);

    System.out.println(calorie3); 		// -- 1)

    Optional<Double> calorieData1 = calorie3.flatMap(optOptCalorie -> optOptCalorie.flatMap(
        optCalorie -> {
            if (optCalorie.isPresent()) {
                return Optional.of(optCalorie.get() * 1000);
            }
            return Optional.empty();
        })
                                                    );

    System.out.println(calorieData1); 	// -- 2)

    Optional<Double> calorieData2 = calorie3.flatMap(optOptCalorie -> optOptCalorie.flatMap(
        optCalorie -> {
            return optCalorie.map(aDouble -> aDouble * 1000);
        })
                                                    );

    System.out.println(calorieData2);	// -- 3)
}
```

<br>

출력결과<br>

```plain
Optional[Optional[Optional[1.1]]]
Optional[1100.0]
Optional[1100.0]
```

<br>

예제 3)<br>

```java
@Test
@DisplayName("null 값을 flatMap 으로 돌릴 경우의 예제")
public void TEST_OPTIONAL_FLATMAP_NULL_VALUE(){
    Optional<Double> emptyCalorie = Optional.ofNullable(null);
    Optional<Double> flatMappedEmptyCalorie = emptyCalorie.flatMap(calorie -> Optional.of(Double.MAX_VALUE));

    System.out.println(flatMappedEmptyCalorie);
}
```

<br>

출력결과<br>

```plain
Optional.empty
```

<br>

## `Optional.isPresent` , `Optional.ifPresent`  ,`Optional.map`

Optional.isPresent

```java
@Test
public void OPTIONAL_IS_PRESENT(){
    String bookName = null;
    if(Optional.ofNullable(bookName).isPresent()){
        System.out.println(bookName.length());
    }
}
```

<br>

Optional.ifPresent

```java
@Test
public void OPTIONAL_IF_RESENT_TEST(){
    String bookName = null;
    Optional.ofNullable(bookName).ifPresent(book -> System.out.println(book));
}
```

<br>

Optional.map<br>

```java
@Test
public void OPTIONAL_MAP_TEST(){
    String bookName = null;
    Optional<Integer> len = Optional.ofNullable(bookName)
        .map(book -> book.length());

    len.ifPresent(l -> System.out.println(l));
}
```

<br>

## 옵셔널의 장점

- 불변성
- 편리함

계속 정리

<br>

## 옵셔널 반환하는게 언제나 득이 되는 것은 아니다.

내일정리... 시간이 없어요 ㅠㅠ 이번 옵셔널 챕터는 왜이렇게 내용이 많은 것인가...<br>

<br>

## 오늘 황당했던 일

퇴사를 한달 앞두고 있고, 다음에 들어갈 회사가 없기에 실직 상태가 한달 이상 될수도 있기에 어느정도 쳐낼수 있는 것들은 쳐내고 있는 중인데, 굉장히 어이 없는 이야기를 들었다.<br>

데이터 애플리케이션을 개발하면서, ThreadPoolExecutor 를 직접 구현해서 작성한 코드가 있었다. 이것을 어떤 사람들에게 인수인계를 했었다. FixedTheadPool 이든 뭐든, 상관 없이 이 사람들은 Executor 의 동작 방식을 전혀 모른다는 것을 깨달았다. 개념을 찾아볼 생각 조차도 안한것 같았다. 테스트 코드나 main 문에서도 실행시켜볼 생각을 안했다는 점에서 한번 충격먹었고, 감으로 때려잡은 코드를 수정해서 그대로 상용으로 내보냈다는 점에서 두번 충격받았었다.<br>

지금은 Thread 설정을 인수인계를 받은 사람들이 멋대로 고친게 안되서 인스턴스 스펙을 계속 올리고, 래빗엠큐 concurrent 옵션을 올려서 코어수를 소비하고 있는걸로 보였는데, 처음 입사때로 상황이 원복된거랑 다른게 없는것 같다. 뭘 고칠때는 BEFORE, AFTER 를 비교할 수 있게끔 해야 하는데, 그런것도 없이 상상만으로 상용에 들이미는 사고방식인데, 정말 이제는 포기했다. 그냥 말이 안통한다. 뭘 하기전에 물어보지도 않는다. 비동기라서 그렇게 하지말라고 이야기했는데 그게 무슨 뜻인지도 이해못했나 보다 싶었다.<br>

아예 Executor와 submit이 어떻게 동작하는지도 모르고 코어 수와 인스턴스 스펙만 수정해서 상용에 들이밀고 있는 듯해보였다.<br>

<br>

한달 뒤에 실직자 신세라서, 가급적이면 공부할 시간과 이력서 쓸 시간, 코딩테스트 연습할 시간, 사이드 프로젝트 진행시간이 있어야 하는데 그 마저도 조금의 시간이라도 안주고 있어서 워킹 타임에는 인수인계 문서 작성, 테스트 케이스 작성을 몰빵해서 하고 있는데, 괜히 아는 척 했다가, 한달 뒤에 한강다리 밑에서 노숙하게 될 것 같아서 모르는척 꿀먹은 벙어리처럼 있어야 겠다 싶었다.<br>

지금도 이직준비하느라 하루에 2시간씩 자면서 출근하고 있는데, 괜히 조금 아는 척 더 하다가는 인생 종치는 소리 들을 것 같은 기분이 들었었다. 진짜 액땜 제대로 하는구나 싶다.<br>

<br>
