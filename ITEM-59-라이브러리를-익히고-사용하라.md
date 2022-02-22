# 아이템 59. 라이브러리를 익히고 사용하라

직접 만들어 사용하는 것보다 가급적 표준 라이브러리에서 지원하는 기능이 있다면 표준 라이브러리를 사용하는 것이 권장된다. <br>

표준 라이브러리에 많은 장점들이 있지만 실상은 많은 프로그래머가 직접 구현해 사용하는 경우가 많다. 이유는 실제로 표준 라이브러리에 그런 기능이 있는지 모르기 때문인 경우가 많다. 메이저릴리즈마다 주목할 만한 수 많은 기능이 라이브러리에 추가된다.<br>

자바는 메이저 릴리즈마다 새로운 기능을 설명하는 웹페이지를 공시하는데, 한번쯤 읽어볼만하다.

- Java8 - [What's New Java 8](https://www.oracle.com/java/technologies/javase/8-whats-new.html)<br>
- Java9 - [What's New Java 9](https://docs.oracle.com/javase/9/whatsnew/toc.htm#JSNEW-GUID-C23AFD78-C777-460B-8ACE-58BE5EA681F6)<br>
- Java10 - [What's New Java 10](https://www.oracle.com/java/technologies/javase/10-relnote-issues.html#NewFeature)<br>
- Java11 - [What's New Java 11](https://www.oracle.com/java/technologies/javase/11-relnote-issues.html)<br>
- Java 16 - [What's New Java 16](https://www.oracle.com/java/technologies/javase/16-relnote-issues.html)<br>

<br>

>  Java 12 ~ Java 15 의 기능은 직접 다시 찾아서 다른 문서에 정리 후 링크 연결 예정. 구글 검색시 검색어를 `new feature java [버전숫자] oracle`  과 같이 입력해서 검색하면 각 버전별 릴리즈 소개 페이지가 나타난다.<br>
>
> 공식 홈페이지 외에도 해외 블로거 등이 작성해둔 요약 페이지가 나타나는데 이 페이지들도 직접 읽어보니 유용했던 것 같다.<br>

<br>

예를 들면 Java 9 에는 curl 과 같은 터미널 명령어와 같은 동작을 하는 기능을 `InputStream` 클래스 내에 `transferTo` 메서드로 제공해주고 있다.

```java
public static void main(String [] args){
    try(InputStream in = new URL(args[0]).openStream()){
        in.transferTo(System.out);
    }
}
```

<br>

## 핵심정리

바퀴를 다시 발명하지 말자. 아주 특별한 나만의 기능이 아니라면 누군가 이미 라이브러리 형태로 구현해놓았을 가능성이 크다. 그런 라이브러리가 있다면 쓰면 된다. 있는지 잘 모르겠다면 찾아보라. 일반적으로 라이브러리의 코드는 여러분이 직접 작성한 것 보다 품질이 좋고, 점차 개선될 가능성이 크다. 여러분의 실력을 폄하하는 것이 아니다. 코드 품질에도 규모의 경제가 적용된다. 즉, 라이브러리 코드는 개발자 각자가 작성하는 것보다 주목을 훨씬 많이 받으므로 코드 품질도 그만큼 높아진다.<br>

<br>

## 표준라이브러리를 사용하는 것의 장점

- 그 코드를 작성한 전문가의 지식과 여러분보다 앞서 사용한 다른 프로그래머들의 경험을 활용할 수 있다.
- 핵심적인 일과 크게 관련 없는 문제를 해결하느라 시간을 허비하지 않아도 된다.
- 따로 노력하지 않아도 성능이 지속적으로 개선된다.
- 기능이 점점 많아진다.
- 여러분이 직접 작성한 코드가 많은 사람들에게 낯익은 코드가 된다. 자연스럽게 다른 개발자들이 더 읽기 좋고, 유지보수하기 좋고 재활용하기 좋은 코드가 된다.

<br>

## 잘 알려진 표준 라이브러리들

자바 API는 매우 범위가 광범위해서 모든 API 를 공부하기에는 벅차다.(저자도 직접 인정함)<br>

라이브러리 들 중에서도 잘 알려지고 유용하고 자주 사용될 수 있는 API가 속한 패키지들은 아래와 같다. 가급적 아래의 패키지들에 있는 라이브러리들에는 익숙해지는 게 좋다.

- `java.lang`
- `java.util`
- `java.io`
- `java.util.concurrent`

<br>

## ex) 난수 생성기능 구현

아래의 코드는 그대로 사용하기에는 문제가 있다. 소스코드는 저자이신 조슈아 블로크 아저씨의 개인 [깃헙](https://github.com/jbloch/effective-java-3e-source-code/blob/master/src/effectivejava/chapter9/item59/RandomBug.java) 에서 가져왔다.

```java
package effectivejava.chapter9.item59;
import java.util.*;

// Random number generation is hard! - Page 215
public class RandomBug {
    // Common but deeply flawed!
    static Random rnd = new Random();

    static int random(int n) {
        return Math.abs(rnd.nextInt()) % n;
    }

    public static void main(String[] args) {
        int n = 2 * (Integer.MAX_VALUE / 3);
        int low = 0;
        for (int i = 0; i < 1000000; i++)
            if (random(n) < n/2)
                low++;
        System.out.println(low);
    }
}
```

<br>

위 코드는 괜찮아 보여도 3가지 문제점을 가지고 있다.<br>

첫번째,<br>

- n 이 그리 크지 않은 2의 제곱수일 경우 얼마 지나지 않아 같은 수열이 반복된다.

두번째,<br>

- n 이 2의 제곱수가 아니라면 몇몇 숫자가 평균적으로 더 자주 반환된다. n 값이 크면 이 현상은 더 두드러진다.

세번째,<br>

- 지정한 범위 '바깥'의 수가 종종 튀어나올 수 있다.
- `rnd.nextInt()` 가 Integer.MIN_VALUE 를 리턴하는 경우를 생각해보자.
- `rnd.nextInt()` 가 `Integer.MIN_VALUE` 를 리턴하면, `rnd.nextInt()` 를 `Math.abs(...)` 로 감싸놓았어도 음수가 나타날 확율이 존재하게 된다. 이유는 아래와 같다.
- int 자료형의 음수는 양수의 갯수보다 하나 더 많은데, Integer.MIN_VALUE 를 사용하게 되면, 양수로 Integer.MIN_VALUE를 표현할 적당한 방법이 없다. Integer.MIN_VALUE 를 Math.abs로 절대값 처리를 해도, 표현범위 바깥이기에 오버플로우 되어 음수가 나타날 가능성이 존재하게 된다.<br>

<br>

이와 같은 문제를 해결하기 위해서는 의사난수 생성기, 정수론, 2의 보수 계산 등에 대해 조예가 깊어야 한다. 위와 같은 문제를 해결한 자바의 API 는 `Random.nextInt(int)` 가 있다. <br>

<br>

**API 라이브러리 개발의 생태계 역시 규모의 경제를 가지고 있다.**<br>

위와 같은 라이브러리는 저명한 대학교에서 수학이나 컴퓨터공학을 전공한 사람들이 Oracle 과 같은 회사에 취직하거나 석/박사 등을 하면서 해당 회사에 인턴쉽으로 취업한 상태에서 일을 하면서 현업에서 개발하던 것을 연구 논문으로 작성하기도 한다고 몇년 전 구글 밋업으로 해외 개발자와 진행했던 스터디에서 얼핏 들은 것 같다. 이 라이브러리를 작성한 사람들은 하루 9시간, 모든 워킹 타임을 월급을 받아가면서 위 코드만을 작성한 것이기에, 라이브러리의 도움을 받는 것이 훨씬 유용하다.<br>

책에서도 `API 라이브러리 개발의 생태계 역시 규모의 경제를 가지고 있다.` 라고 매우 애둘러서 간접적으로 표현하고 있다. 그만큼 시간을 많이 쓰고, 스트레스 많이 받아가면서 라이브러리 개발하고, 그만한 월급을 크게 받는 사람들이 많이 투입되는 등을 간접적으로 규모의 경제를 가지고 있다. 하고 에둘러 표현한건 아닐까 하고 생각했다.<br>

<br>