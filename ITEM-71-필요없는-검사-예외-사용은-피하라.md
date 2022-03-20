# Item 71. 필요없는 검사 예외 사용은 피하라

> 검사 예외라는 용어는 되도록 `Checked Exception` 이라는 용어로 바꿔서 정리하기로 했다.<br>

<br>

**핵심정리**<br>

- 꼭 필요한 곳에만 사용한다면 Checked Excpetion 은 프로그램의 안정성을 높여준다.
- 하지만, 남용하면 쓰기 어려운 API 로 전락한다.
- 해당 메서드를 호출하는 caller 측에서 예외를 복구할만한 방법이 없다면 `Unchecked Exception` 을 던지는 것이 낫다.
  - 쉽게 이야기하자면, 메서드 구현시, caller 측에서 `try ~ catch`  블록의 `catch` 구문 내에서 적절히 처리할 만한 방법이 없다면, `Unchecked Exception` 을 던지도록 구현하는 것이 낫다.
- 만약 복구가 가능하고, 그 처리를 caller 측에서 해야 한다면, Checked Exception 을 적용하기에 앞서서 `Optional` 을 반환할지 먼저 검토하자.
- `Optional` 을 사용하고 나서도 상황을 처리하기 위한 충분한 정보를 제공할 수 없다면 `Checked Exception` 을 던지자.

<br>

**내용 정리**<br>

어떤 메서드 하나를 `Checked Exception` 으로 선언했다고 해보자. 이 메서드를 호출하는 측에서는 아래와 같은 두가지 방식 중의 하나로 처리를 수행하게 된다.

- `try ~ catch` 블록으로 예외를 처리
  - `Checked Exception` 은 해당 `Exception` 을 `throw` 한 것을 `try ~ catch` 로 처리하면 rollback은 이뤄지지 않는다.<br>
- `throw` 해서 바깥으로 예외를 전파

<br>

위의 두가지를 코드로 예를 들면 아래의 두가지 코드 스니펫과 같은 모양이 된다.<br>

**`try ~ catch` 를 사용하는 경우**

```java
} catch (TheCheckedException e){
    e.printStackTrace();
//    System.exit();
}
```

<br>

**throw 를 사용하는 경우**

```java
catch(TheCheckedException e){
    throw new AssertionError();
}
```

<br>

**Checked Exception 의 단점**<br>

한편, Checked Exception(검사 예외) 는 과하게 사용하면 오히려 쓰기 불편한 API가 된다.<br>

더욱이 `Checked Exception` 이 발생하는 메서드는 스트림 안에서 직접 사용할 수 없기 때문에(`아이템 45 ~ 48`) 자바 8 부터는 부담이 더욱 커졌다.<br>

책에서는 Checked Exception 을 하나만 던지는 메서드를 처리하기 위해 `try ~ catch` 문을 사용하는 것은 어느정도의 불편함이 더 커진다고 언급하고 있기도 하다.<br>

<br>

**`Checked Exception` 을 던지는 코드를 리팩토링 하는 다양한 방법들**<br>

중요한 코드가 아니라면 Checked Exception 을 던지는 코드를 피해하는 것이 좋겠지만, Checked Exception 을 `throw` 하는 메서드를 처리해야 하는 경우가 있다. 이와 같은 코드를 조금 더 유연한 구조로 바꾸기 위해서는 아래와 같은 방법들이 사용된다.<br>

- `Optional` 을 사용하는 방식
- `Checked Exception` 을 던지는 메서드를 2개로 분리해,`Unchecked Exception` 을 던지는 메서드로 변경하고, 예외가 던져질지 여부를 `boolean` 조건값으로 체크해 분류하는 방식

<br>

**`Optional` 을 사용하는 방식**<br>

`Checked Exception` 을 던지는 대신 단순히 빈 옵셔널을 던지는 방식이다.(아이템 55) 이렇게 하면, 예외가 발생한 이유를 알려주는 부가 정보를 담을수 없다는 점이 단점이다.<br>

<br>

**Unchecked Exception 메서드로 변경 & 예외 발생 여부 체크 메서드 사용**<br>

예외가 던져질지 여부를 체크해서 `boolean` 값을 리턴하는 `actionPermitted()` 메서드로 예외 여부를 먼저 검사하고, 예외가 발생하지 않을 경우는 그대로 동작을 수행한다. 만약 `actionPermitted()` 메서드로 검사한 값이 `false` 라면 예외 처리를 수행하는데, 아래 코드에서는 `else {...}` 구문이 이것에 해당하는 부분이다.<br>

```java
if(obj.actionPermitted(args)){
    obj.action(args);
}
else{
    ... // 예외 상황에 대처
}
```

<br>

`actionPermitted(..)` 메서드를 사용하는 방식도 단점이 있다.

- 동시성/병렬성 코드 수행시 조건검사식의 동기화 이슈 및 모호한 리턴값 문제 발생
  - 동시성/병렬성 코드를 수행할 때, 여러 스레드가 `actionPermitted()` 메서드 호출을 거의 비슷한 시간대에 거치게 될 수 있는데, 각 스레드 마다 `actionPermitted()` 의 결과값이 다른 채로 다음 라인을 수행하게 되는 단점이 발생한다.

- `actionPermitted()` 메서드가 변경될 경우에 대한 사이드이펙트
  - actionPermitted() 메서드의 조건 검사식이 바뀔 경우 이에 해당하는 다른 로직들의 변경 여부도 검토해야 한다. 