# ITEM 49. 매개변수가 유효한지 검사하라



## 핵심정리

> 책에서 언급하는 내용 발췌

메서드나 생성자를 작성할 때면 그 매개변수들에 어떤 제약이 있을지 생각해야 한다. 그 제약들을 문서화하고 메서드 코드 시작 부분에서 명시적으로 검사해야 한다. 이런 습관을 반드시 기르도록 하자. 그 노력은 유효성 검사가 실제 오류를 처음 걸러낼 때 충분히 보상받을 것이다.<br>

<br>

## 메서드 수행시 필요조건 검사

메서드가 시작되는 시점에 조건검사를 수행하는 경우가 꽤 있다. 예를 들면 `selectEmployee(Long id)` 라는 메서드가 있다고 해보자. 이 메서드는 새로운 사원검색 시스템을 개발하면서, 기획단계에서 아래의 제약조건을 정의했었다.

-  `id` 는 null 이 아니어야 한다.
- 음수 역시 허용하지 않는다.

그리고 이 제약조건은 `selectEmployee(Long id)` 라는 메서드를 구현하면서 `selectEmployee(Long id)` 메서드의 실행 초기에 매개변수가 올바른지 검사하기로 했다. 보통 실무 진행시에는 위와 같은 제약조건을 기획자들이 먼저 명시하고, 문서에 명시하고 있기도 하고, 기술 상으로 데이터베이스 제약조건에 맞게끔 할때 개발 단계에서 명세화하기도 한다. <br>

이런 매개변수 조건 검사는 가급적 메서드 시작 초입에 미리 검사해서 조건을 만족시키지 않는다면 Exception 을 던지거나 Optional.empty 를 리턴하거나, 비어 있는 리스트를 전달하는 등의 동작을 수행하게끔 하는 편이다.<br>

**"오류는 가능한 빨리(발생한 곳에서) 잡아야 한다."**<br>

이렇게 메서드 초입부에서 미리 매개변수의 유효성을 체크하는 것은 "오류는 가능한 빨리 (발생한 곳에서) 잡아야 한다."는 원칙을 지키기 위한 조건이기도 하다. 메서드 실행 전 매개 변수를 검사하면, 잘못된 값이 넘어오면 즉시 깔끔한 방식으로 예외throw/Optional.empty 반환/비어있는 리스트 리턴 등의 방식으로 깔끔하게 처리할 수 있게 된다.<br>

**"매개변수 검사를 제대로 하지 않으면?"**<br>

> 굉장히 당연한 이야기이지만, 그래도 정리해두기로 했다.

- 메서드 수행중 모호한 에러/예외가 발생해서 중단되거나 실패한다. 그리고 찾기 어려운 버그로 남는다.
- 수행은 잘되도 잘못된 결과를 내게 될 수 있다.
- 수행은 잘 되도 이런 객체가 이상한 상태로 만들어져서 미래에 연관성 없는 오류를 내기도 한다.



**문서화**<br>

또한, 이런 내용들은 문서로 문서화가 되어 있어야 한다고 책에서는 언급하고 있는데, 우리가 실무에서 항상 접하는 기획문서가 이 내용에도 맞는 내용이다. 만약 기획문서에 없는 내용인 보안 충족성 검사 등을 수행한 것을 명세화해야 할때 테스트 코드로 남겨두기도 하고, 기획문서를 업데이트해 공지를 올리는 등의 방법이 있을 것 같다.<br>

> 서버 개발이라고 하더라도, UI의 기획을 보고 데이터의 제한점 등을 파악해야 하기에 기획문서를 꼭 보고, 기획자 역시도 이런 기획문서의 내용을 서버개발 파트에 전달해준다. 만약 이런게 없이 서버개발자한테 개발과 기획까지 맡긴다면 가스라이팅이다. 참고 : [중소기업 가스라이팅](https://www.google.com/search?q=%EC%A4%91%EC%86%8C%EA%B8%B0%EC%97%85+%EA%B0%80%EC%8A%A4%EB%9D%BC%EC%9D%B4%ED%8C%85&oq=%EC%A4%91%EC%86%8C%EA%B8%B0%EC%97%85+%EA%B0%80%EC%8A%A4%EB%9D%BC%EC%9D%B4%ED%8C%85&aqs=chrome.0.69i59.5314j0j1&sourceid=chrome&ie=UTF-8) <br>
>

<br>

## 예외 문서화

**매개변수가 잘못되었을 때 던지는 예외를 문서화**<br>

소스코드의 주석 상에서 @throws 태그를 통해 매개변수가 어떠한 잘못된 값으로 던질때 이러한 예외를 던진다 하는 것을 주석으로 남기는 경우 역시 있다. 우리가 자주 쓰는 JPA 내의 라이브러리 역시도 그런 예외를 명시하고있다. ( 이 부분 실제 예제를 찾아서 여기에 설명 추가 )

<br>

**매개변수의 제약을 문서화한다면, 그 제약을 어겼을 때 발생하는 예외에 대해 상세하게 기술해야 한다.**<br>

이렇게 하면, API 사용자가 제약을 지킬 가능성을 크게 높일 수 있다.<br>

ex)<br>

```java
/**
 * 항상 음의 값이 아닌 BigInteger 를 반환한다는 점에서 remainder 메서드와 다르다.
 * 
 * @param m 계수(양수여야 한다.)
 * @return 현재 값 mod m
 * @throws ArithmeticException : m이 0보다 작거나 같으면 발생한다.
 */
public BigInteger mod(BigInteger m){
    if(m.signum() <= 0)
        throw new ArithmeticException("개수(m)는 양수여야 합니다. " + m);
    // 계산 수행
}
```

<br>

자세한 내용은 [ITEM 74. 메서드가 던지는 모든 예외를 문서화하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-74.-%EB%A9%94%EC%84%9C%EB%93%9C%EA%B0%80-%EB%8D%98%EC%A7%80%EB%8A%94-%EB%AA%A8%EB%93%A0-%EC%98%88%EC%99%B8%EB%A5%BC-%EB%AC%B8%EC%84%9C%ED%99%94%ED%95%98%EB%9D%BC.md) 를 참고하자.<br>

<br>

## 제약조건 문서화

클래스의 모든 메서드에 해당되는 매개변수 제약조건이라면, 클래스 레벨에 제약조건을 주석으로 명시하는 것이 가장 깔끔한 방식이다. 단순히 메서드 하나마다 적어두는 것보다 이 방식이 더 권장된다.<br>

> 참고)<br>
>
> `@Nullable` 이나 이와 비슷한 애너테이션을 사용해 특정 매개변수는 `null` 이 될수 있다고 알려줄 수도 있지만, 표준적인 방법은 아니다. 라고 책에서는 언급하고 있다.<br>
>
> 나도 이건 어떤게 올바른 판단인지는 판단이 잘 서지 않는다. 팀 바이 팀 일것 같기도 하고, 내가 아직 이런 부분에 정체성이 안생겼나 싶기도 하다.

<br>

## Objects.requireNonNull

`Objects.requireNonNull` 메서드는 유연하고 사용하기도 편하다. 예외 메시지 역시 미리 정한 포맷이 있다면 알아보기도 편하게 출력하는 것 역시 가능하다.<br>

예제로 정리해봤다.<br>

**ex 1)**<br>

```java
@Test
@DisplayName("테스트 1")
public void test1(){
    Objects.requireNonNull(null,"[1] 안녕하세요. test1() 에서 입력하신 null 은 허용되지 않습니다.");
}
```

<br>

**출력결과**<br>

```plain
java.lang.NullPointerException: [1] 안녕하세요. test1() 에서 입력하신 null 은 허용되지 않습니다.

	at java.base/java.util.Objects.requireNonNull(Objects.java:233)
	at io.study.lang.javastudy2022ty1.effectivejava_temp.item49.ObjectsRequireNonNullTest.test1
```

<br>

**ex 2)**<br>

```java
@Test
@DisplayName("테스트 2")
public void test2(){
    Objects.requireNonNull(null,()->{return "[2] 안녕하세요. test2() 에서 입력하신 null 은 허용되지 않습니다.";});
}
```

<br>

**출력결과**<br>

```plain
java.lang.NullPointerException: [2] 안녕하세요. test2() 에서 입력하신 null 은 허용되지 않습니다.

	at java.base/java.util.Objects.requireNonNull(Objects.java:334)
	at io.study.lang.javastudy2022ty1.effectivejava_temp.item49.ObjectsRequireNonNullTest.test2
```

<br>

## Objects.checkFromIndexSize, checkFromToIndex, checkIndex

자바9부터는 `Objects` 에 범위 검사 기능을 하는 메서드들 역시 추가됐다. 

- checkFromIndexSize
- checkFromToIndex
- checkIndex

아직은 `Objects.requireNonNull` 메서드 만큼 유연하지는 않다.

- 예외메시지를 지정할 수 없다.
- 리스트,배열에만 사용가능하다.
- 더 큰 범위는 조사하지 못한다.
  - 배열 사이즈가 30일 경우 0 \~ 50 은 조사하지 못한다.

<br>

## public 이 아닌 메서드 : assert 를 사용해 매개변수 유효성 체크

공개되지 않은 메서드라면 프로그래머 자신이 직접 호출되는 상황을 통제하게 된다. 유효한 값만이 메서드에 넘겨지리라는 것을 assert (단언문)을 이용해 매개변수의 유효성을 검증할 수 있다. 예를 들면 아래와 같은 방식이다.

```java
private static void sort(long a[], int offset, int length){
    assert a != null;
    assert offset >= 0 && offset <= a.length;
    assert length >= 0 && length <= a.length - offset;
    // ... 계산 수행
}
```

잘 살펴보면 위의 assert문(단언문)들은 단언한 조건이 무조건 참이라고 선언하고 있다. <br>

assert 구문(단언문)은 일반적인 유효성 검사와 다르다.<br>

- 첫번째, 실패하면 `AssertionError` 를 던진다.
- 두번째, 런타임에 아무런 효과도, 아무런 성능 저하도 없다.
  - 단, java 를 실행할 때 명령줄에서 `-ea` 또는 `--enableassertions` 플래그를 설정하면 런타임에 영향을 준다.
- 자세한 사항은 별도의 튜토리얼을 참조

<br>

## 정적 팩터리 메서드

코드 20-1

null 검사를 수행하기에 클라이언트가 null 을 건네면 NullPointerException 을 던진다.<br>

만약 이 검사(null 검사)를 생략했다면, List 메서드 몸체에서 `int [] a` 가 `null` 인 것으로 인한 에러가 메서드 수행 or 리스트 사용시 발생한다.<br>

이렇게 되면 이 List 를 어디서 만든건지 파악이 어려워진다.

<br>

## 생성자

- 생성자 매개변수에도 유효성 검사를 수행하면 좋다.
- 유효성 검사를 생성자 매개변수에 대해 수행하면 클래스 불변식을 어기는 객체가 만들어지는 것을 방지할 수 있게 해준다.

!!TODO!!<br>

클래스 불변식이란??<br>

- Effective Java 4장. '클래스와 인터페이스'의 'ITEM 17 - 변경가능성을 최소화하라' 에서 언급하는 내용인데, 아직 정리하지 않았다. 조만간 정리할 예정

<br>

## 매개변수 유효성 검사를 skip 할만한 예외상황들

- 유효성 검사 비용이 지나치게 높거나 실용적이지 않을때
  - `Collections.sort(List)` : 비교에 앞서 모든 객체가 비교가능한지(Comparable) 검사하는 것은 어느 정도의 비용이 소모된다.
- 계산과정에서 암묵적으로 검사가 수행될 때
  - ex) 계산중 잘못된 매개변수 값을 사용해 발생한 예외와 API 문서에서 던지기로 한 예외가 다를 경우
  - 이 경우 예외번역을 통해 API 문서에 기재된 예외로 번역해야 한다. 
  - 예외번역에 대한 개념인데, 이것은 Item 73 에서 정리해뒀었다. 
    - !!TODO!! 흠... 다시 정리잘 되있나 보고 수정할것 수정해야겠군



