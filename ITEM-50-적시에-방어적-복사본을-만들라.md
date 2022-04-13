# ITEM 50. 적시에 방어적 복사본을 만들라

> 내일에 걸쳐서 쭉 정리할 예정. 아직 모두 정리한 것은 아니다.윽...<br>

<br>

어떤 객체든 그 객체의 허락 없이는 외부에서 내부를 수정하는 일은 불가능해야 한다.<br>

주의를 기울이지 못하는 코드는, 내부를 수정하게 되는 경우가 생긴다.<br>

불변성을 지원하지 않는 낡은 API를 사용할 때 예제로 `Date` 클래스의 날짜를 위/변조 하는 경우를 예로 들고 있다. 그리고 이것을 방지하기 위해 적시에 방어적 복사본을 만드는 것에 대해서도 설명하고 있다.<br>

매개변수의 유효성 검사를 수행하기 전에 방어적 복사본을 만드는 작업을 수행하면 이런 위험에서 벗어날 수 있다. 이렇게 방어적 복사본을 만드는 것을 컴퓨터 보안 커뮤니티에서는 `검사시점/사용시점(time-of-check/time-of-use)` 공격 또는 `TOCTOU` 공격이라고 지칭한다.<br>

<br>

## 핵심정리

> 책에서 그대로 발췌

클래스가 클라이언트로부터 받는 혹은 클라이언트로 반환하는 구성요소가 가변이라면 그 요소는 반드시 방어적으로 복사해야 한다. 복사 비용이 너무 크거나 클라이언트가 그 요소를 잘못 수정할일이 없음을 신뢰한다면 방어적 복사를 수행하는 대신 해당 구성요소를 수정했을 때의 책임이 클라이언트에 있음을 문서에 명시하도록 하자.<br>

<br>

## ex) 보안취약 사례 - Date 클래스를 외부에서 수정

클라이언트는 언제든 프로그래머가 작성한 클래스의 불변식을 깨뜨리려 혈안이 되어 있다고 가정하고, 객체가 외부에서 수정되지 않게끔 방어적으로 프로그래밍해야 한다.<br>

실제로도 입력을 조작해서, 시스템의 구멍을 찾아내 보안을 뚫거나, 크래킹을 하려는 시도가 많다.<br>

아래의 `Date` 클래스를 사용하는 아래의 `Period` 클래스가 그 예제다. <br>

아래의 코드는 불변식처럼 보이고, 시작시간,종료시간에 대한 유효성 체크가 잘 된 것으로 보인다. 하지만, 그렇지 않다.

```java
public final class Period{
    private final Date start;
    private final Date end;
    
    /**
     * @param start 시작 시각
     * @param end 종료 시각
     * @throws IllegalArgumentException 시작 시각이 종료시각보다 늦을 때 발생한다.
     * @throws NullPointerException start 나 end 가 null 이면 발생한다.
    **/
    public Period(Date start, Date end){
        if(start.compareTo(end) > 0) 
            throw new IllegalArgumentException(start + "가 " + end + "보다 늦다.");
        
        this.start = start;
        this.end = end;
    }
    
    public Date start(){
        return start;
    }
    
    public Date end(){
        return end;
    }
    
    // 나머지 코드 중략
}
```

위의 코드는 불변식을 깨뜨릴 수 있다. 외부에서 내부의 상태값을 조작해서 의도하지 않은 결과를 만들어내서, 오동작을 일으키게 될 소지가 충분하다.<br>

아래의 코드를 보자.

```java
Date start = new Date();
Date end = new Date();
Period p = new Period(start, end);
end.setYear(78); // p의 내부를 수정했다!!!
```

<br>

## ex) 보완책 1 - `Instant`, `ZonedDateTime`, `LocalDateTime`

자바 8 이후부터는 위에서 언급한 `Date` 클래스를 사용하는 것으로 인한 문제를 쉽게 해결할 수 있다. `Date` 대신 불변인 `Instant` , `ZonedDateTime`, `LocalDateTime` 을 사용하면 된다. `Date` 는 오래된 API이고, 실제로 문제가 많은 자바 API 이기에 더 이상 사용하면 안된다.<br>

 `Date` 클래스, `Calendar` 클래스, `SimpleDateFormat` 클래스는 가급적 사용하지 않는 것이 좋다. 여기에 대한 참고자료는 아래와 같다. 추후 별도의 문서로 정리할 예정이다. 오늘 이 문서에서는 그냥 참고할만한 자료들만 정리!!!<br>

<br>

- [d2.naver.com - Java의 날짜와 시간 API](https://d2.naver.com/helloworld/645609)
- [당신이 `SimpleDateFormat` 을 쓰지 말아야 할 이유](https://techlog.myoa-universe.com/archives/786)
- [Java Time, Data 클래스의 문제점과 Java 8](https://hamait.tistory.com/205)

<br>

## ex) 보완책 2 - 복사본을 사용하도록 개선하기

위에서 작성한 `Period` 클래스의 인스턴스의 내부를 보호하려면 생성자에서 받은 가변 매개변수 각각을 방어적으로 복사(defensive copy) 해야 한다.

```java
public Period(Date start, Date end){
    this.start = new Date(start.getTime());
    this.end = new Date(end.getTime());
    
    if(this.start.compareTo(this.end) > 0)
        throw new IllegalArgumentException(this.start + "가 " + this.end + " 보다 늦다.");
}
```

이렇게 새로 작성한 생성자를 사용하면 앞에서 사용한 객체의 내부를 객체 외부에서 수정하는 위/변조 코드는 더 이상 위협이 되지 않는다.<br>

위의 코드에서 매개변수의 유효성을 검사하기에 앞서 방어적 복사본을 만들고, 복사본으로 유효성을 검증한 것에 주목해야 한다.<br>

멀티스레딩 환경에서 원본 객체의 유효성을 검사한 후 복사본을 만드는 그 찰나의 위험한 순간에 다른 스레드가 원본객체를 수정할 위험이 있다. 실제로 누구든 언젠가는 멀티스레딩 환경의 동시성 프로그램을 작성해야 하는 순간이 오는데, 이렇게 멀티 스레딩 환경 코드를 작성할 때 적시에 복사본을 사용하게끔 하는 코드를 작성하면 도움이 된다.<br>