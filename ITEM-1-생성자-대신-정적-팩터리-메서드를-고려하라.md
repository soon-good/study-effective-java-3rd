> 여기서 이야기하는 정적 팩터리 메서드는 디자인 패턴에서 이야기하는 팩터리 메서드(Factory Method)가 아니다. 디자인 패턴에는 여기서 이야기하는 정적 팩터리 메서드 패턴이 없다.

## 핵심정리

정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있다. 따라서 각각의 상대적인 장단점을 이해하고 사용하는 것이 좋다. 정적 팩터리를 사용하는 게 유리한 경우가 더 많은 경우도 있기때문에, 무작정 pubilc 생성자를 제공하던 습관이 있다면 고치자.<br>

<br>

## 장점

다섯가지의 장점이 있다.

- 첫 번째, 이름을 가질 수있다.
- 두 번째, 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.
- 세 번째, 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
- 네 번째, 입력 매개 변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
- 다섯 번째, 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

<br>

### 1 ) 첫 번째, 이름을 가질 수 있다.

**생성자에 인자가 매우 많을 경우의 단점**

생성자에 인자가 매우 많을 경우 이 입력 매개변수들을 모두 기억해서 사용하기는 쉽지 않다. 생성자에 넘기는 매개변수와 생성자 자체만으로는 반환될 객체의 특성을 제대로 설명하지 못한다. 시그니처 하나로는 생성자를 하나만 만들수 있다는 점도 단점이다. 물론 입력 매개변수들의 순서를 다르게 해서 생성자를 새로 추가하는 식으로 이 제한점을 회피할 수도 있지만 좋지 않은 방법이다.<br>

**정적 팩터리 메서드의 예**<br>

> ex) BigInteger(int, int, Random) vs BigInteger.probablePrime<br>

`BigInteger(int, int, Random)` 와 `BigInteger.probablePrime` 중 어느 쪽이 `BigInteger 타입의 가능한 소수를 반환한다.`  를 더 잘 설명하는지 비교해보자.  `BigInteger.probablePrime` 은 java4 에서 추가됐다. <br>

**정적 팩터리 메서드를 사용할 경우의 장점**<br>

정적 팩터리 메서드는 이름을 가질 수 있다. 한 클래스에 시그니처가 같은 생성자가 여러 개 필요할 것 같으면, 생성자를 정적 팩터리 메서드로 바꾸고 이 정적 팩터리 메서드의 이름을 각각의 차이를 잘 드러내는 이름으로 지어주면 좋다.<br>

<br>

### 2 ) 두 번째, 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.

> 참고 : 싱글턴 (아이템 3), 인스턴스화 불가(아이템 4), 불변값 클래스(아이템 17), 플라이웨이트패턴(Gamma 95), 열거타입(34)

정적 팩터리 방식의 클래스는 반복되는 요청에 같은 객체를 리턴하도록 하는 방식을 이용해서 언제 어느 인스턴스를 살아있게 할지를 철저히 통제할 수 있다. 이렇게 반복되는 요청에 같은 객체를 반환하는 역할을 하는 메서드를 제공하는 클래스를 인스턴스 통제(instance-controlled) 클래스라고 한다.<br>

인스턴스를 통제하면 클래스를 싱글턴으로 만들 수 있다. 또한, 인스턴스화 불가로 만들 수도 있다. <br>

> 예를 들면 `Boolean.valueOf(boolean)` 메서드는 객체를 아예 생성하지 않는다. 따라서 (생성 비용이 큰) 객체가 자주 생성되는 상황에서 Boolean.valueOf(boolean) 으로 boolean 객체 생성시 성능을 개선할 수 있다.

<br>

이 외에도 불변 값 클래스로 인스턴스가 단 하나 뿐임을 보장하는 것 역시 가능하다. (a == b 일 때만 a.equals(b) 가 성립)<br>

불변 클래스(아이템 17)는 인스턴스를 미리 만들어놓거나 새로 생성한 인스턴스를 캐싱해 재활용하는 방식으로 불필요한 객체 생성을 피한다.(ex. Boolean.valueOf(...))

<br>

이렇게 인스턴스를 통제하는 것은 플라이웨이트 패턴의 근간이 된다. 이 외이도 Enum(열거 타입)은 인스턴스가 하나만 만들어짐을 보장한다.<br>

<br>

### 3 ) 세 번째, 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

> 참고 : 아이템 20, 아이템 64

반환타입의 하위 타입 객체가 여러개일때 하나의 타입에 대해 원하는 하위타입을 선택해서 리턴할 수 있는 것을 의미한다. 이런 요소는 정적 팩터리 메서드를 구현할 때에도 적용이 가능하다. 정적 팩터리 메서드의 반환 타입으로 실제 구현 클래스가 아닌 인터페이스 타입을 반환하도록 해서, 인터페이스 기반의 프레임워크를 만드는 Java의 핵심 기술이기도 하다. (아이템 20)<br>

**Java 의 컬렉션 프레임워크**<br>

Java의 컬렉션 프레임워크는 45개의 구현체 클래스를 공개하지 않기 때문에 API 외견을 훨씬 작게 만들 수 있었다. API가 작아지게 되어서 프로그래머가 API를 사용하려고 할때 익혀야 하는 개념이나, 난이도가 낮춰지게 되었다. 이 구현체 대부분 들을 `java.util.Collections` 에서 정적 팩터리 메서드를 통해 얻도록 했다. `java.util.Collections` 는 인스턴스 불가 클래스이다.<br>

예를 들면 Java의 `List<T>` 와 `ArrayList<T>`, `LinkedList<T>` , `Vector<T>` 등을 예로 들 수 있다.<br>

`List<T>` 는 인터페이스인데, `ArrayList<T>`, `LinkedList<T>` , `Vector<T>`  과 같은 구체적인 인스턴스의 타입을 `List<T>` 로 가리킬 수 있게끔 해준다.<br>

```java
List<String> employees = new ArrayList<String>();
```

<br>

구체적으로 예를 들어보자. 예를 들어 직원들의 리스트를 반환하는 아래와 같은 함수가 있다고 해보자.

```java
public List<String> getEmployees (){
  List<String> employees = new LinkedList<String>();
  employees.add("Eddie");
  employees.add("Haley");
  employees.add("Kyle");
  return employees;
}
```

<br>

`getEmployees()` 함수를 사용하는 다른 로직으로 employees() 의 size() 를 구하는 함수 `countEmployees()` 가 있다고 해보자. `countEmployees()` 메서드의 동작은 이렇다.

- getEmployees() 메서드로 `List<Employee>` 데이터를 가져온다.
- 가져온 `List<Employee>` 데이터의 size() 를 구한다.

<br>

내부 구현은 아래와 같이 되어 있다.

```java
public int countEmployees(){
  return getEmployees().size();
}
```

<br>

countEmployees() 메서드 내에서 `getEmployees()` 메서드로 직원들의 리스트를 구해오고 있다. countEmployees() 메서드는 직원들의 숫자를 구하는 것이 목적이기에 getEmployees() 가 반환하는 List 의 구체 타입(implements 한 타입)이 ArrayList인지, LinkedList 인지 알 필요가 없다. 이펙티브 자바에서는 이렇게 반환 타입의 하위 타입을 리턴하는 것을 장점으로 설명한다. 예를 들면 위의 예제에서 getEmployees() 함수의 아래와 같은 부분이다. 

```java
public List<String> getEmployees (){
  List<String> employees = new LinkedList<String>();
  employees.add("Eddie");
  employees.add("Haley");
  employees.add("Kyle");
  return employees;
}
```

countEmployees() 함수 입장에서 getEmployees() 의 반환 타입이 ArrayList 인지, LinkedList인지까지 모두 맞춰서 프로그래밍하려면 굉장한 스트레스로 다가오게 된다. 이때 List 타입으로 리턴하도록 getEmployees() 메서드의 반환형을 지정해두면, 사용자 측인 countEmployees() 메서드의 경우 List 타입의 size() 메서드를 사용하면 되기에 조금은 각 메서드 간의 의존성이 루즈하게 된다는 점에서 장점이다.<br>

이펙티브 자바 에서는 이렇게 인터페이스타입으로 구체타입을 대표해서 리턴하는 것이 가능한 것이 Java의 장점이라고 설명하고 있다. 그리고, 정적 팩터리 메서드의 경우 이렇게 인터페이스 타입으로 상위 타입을 리턴하는 메서드시그니처를 정의해두면, 반환 타입의 하위 타입 객체를 사용자 측에서 알 필요가 없기 때문에 장점이라고 설명하고 있다. 이번 부분은 책에 비해 다소 길게 설명했고, 어떻게 요약할지 감이 안잡혀서 3시간 정도를 쓰다가 지우다가를 굉장히 자주 반복했는데, 일단 이정도로 설명하는 것이 최선인 것 같다는 생각이 든다.

 <br>

**동반클래스**<br>

> 바로 윗 부분까지는 정적 팩터리를 사용하는 것의 장점을 설명했다. [이팩티브 자바 3/E](http://www.yes24.com/product/goods/65551284) 에서는 정적 팩터리 메서드를 사용하는 세번째 장점을 설명할 때 동반 클래스의 개념과 Java8,9 에서의 변화를 뜬금없이 이야기하는데, 이 동반클래스를 JAVA 프레임워크에서 사용한 예와 동반클래스 내에 사용하지 않더라도 Java8, Java9 에서부터는 인터페이스 내에 정적 팩터리 메서드를 사용할 수 있음을 언급하고 넘어간다. 다소 뜬금 없어서 읽고 나서 이 부분에 대해  `어? 뭐 읽은거지?` 할 수 밖에 없기에 한번 흐름을 요약해봤다.<br>
>
> Java 8 이전의 Java 에서는 동반 클래스를 이용해 정적 팩터리 메서드를 구현했다. 정적팩토리 메서드를 정의한 대표적인 예는 [java.util.Collections](https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html) 클래스이다. 시간이 흘러 Java 8 부터는 인터페이스 내에 public 스코프를 가지는 정적 팩토리 메서드를 구현할 수 있게 되었고 Java 9 부터는 private 스코프를 지정하는 정적팩터리 메서드를 구현하는 것 역시 가능하게 되었다. <br>

<br>

**동반클래스 `Types` 생성 후, 리턴 타입이 `Type` 인 정적 팩터리 메서드를 구현**<br>

Java 8 이전에는 인터페이스에 정적 메서드를 선언할 수 없었다. 이런 이유로 동반 클래스라는 것을 만들어서 동반 클래스 내에 정적 팩토리 메서드를 구현했다. 대표적인 Java 프레임워크 내에서 대표적으로 동반클래스 기반으로 구현된 정적 팩토리 메서드들은  `java.util.Collections`  내에 많이 존재한다. <br>

[java.util.Collections](https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html) 클래스는 대표적으로 아래와 같은 함수들이 있는데 대부분의 메서드들의 시그니처에는 `static <T> 타입명 <T> ` 가 붙어있다. 즉, 정적 팩토리 메서드라는 의미이다.<br>

- `list(Enumeration <T> e) : static <T> ArrayList <T>` 
- `emptySet() : static <T> Set <T>` 
- `singletonList(T o) : static <T> List <T> `
- `singletonMap(K key, V value) : static <K, V> Map <K, V> ` 

더 자세한 내용은 [java.util.Collections](https://docs.oracle.com/javase/7/docs/api/java/util/Collections.html) 을 찾아보면 될 것 같다.<br>

만약 `Type` 이라는 인터페이스를 반환하는 정적 메서드가 필요했다고 해보자. 이럴 때는 먼저, `Types` 라는 이름의 동반 클래스(companion class)를 만든다. 그리고 이 동반 클래스에 정적 팩토리 메서드를 정의한다. 이렇게 하는 것이 Java8 이전의 일반적인 관례였다.<br>

**Java8 에서의 새로운 정적 팩터리 메서드 구현 방식 - 인터페이스 내에 public 정적 팩터리 메서드 구현**<br>

Java8 부터는 public 으로 접근 제한을 적용할 메서드들에 대해서는 인터페이스 내에 정적 메서드를 구현하면 된다. java.util.Collections 클래스에 정적 팩터리를 구현했듯이 동반 클래스로 정적 팩터리 메서드를 구현할 수도 있지만, java 8 부터는 인터페이스 내에 작성하는 것이 더 낫다고 판단되면 인터페이스에 정적 팩터리 메서드를 구현하는 것이 가능하다.<br>

단점으로는 java 8 버전에 한정해서는 package 내에서 private 인 범위(protected)를 갖는 클래스를 가질 수 없다는 점이다. 하지만 java 9 부터는 private 제한자를 적용할 수 있게 되었다.<br>

**Java 9 에서의 정적 팩터리 메서드 구현 방식 - packae-private 스코프가 필요할 경우 private 제한자를 적용할 수 있다.**<br>

정적 팩터리 메서드를 구현하기 위한 코드 들 중 많은 부분 들 중 특정 부분들은 여전히 별도의 package-private 클래스에 두어야 하는 경우가 자주 있다. Java8 에서는 인터페이스에는 public 정적 메서드만 추가할 수 있다. 하지만 Java 9 에서는 private 정적 메서드 까지 허락한다.(but, 정적 필드, 정적 멤버 클래스는 모두 public 이어야 한다.)<br>

<br>

### 4 ) 네 번째, 입력 매개 변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

OpenJDK 버전 Java 의 EnumSet 클래스를 예로 들 수 있다. OpenJDK 버전 EnumSet 클래스는 public 생성자 없이 오직 정적 팩터리 메서드만 제공한다.<br>

EnumSet 클래스는 아래와 같이 동작한다.

- EnumSet에 추가되는 원소의 갯수가 64개 이하이면 원소들을 long 변수 하나로 관리하는 `RegularEnumSet` 인스턴스를 반환한다.
- EnumSet에 추가되는 원소의 갯수가 65개 이상이면 long 배열로 관리하는 `JumboEnumSet` 의 인스턴스를 반환한다.

<br>

클라이언트는 RegularEnumSet, JumboEnumSet 클래스 의 존재를 잘 모르더라도, 코드를 작성할 수 있다. 클라이언트는 EnumSet의 팩토리가 전달해주는 객체가 어떤 클래스의 인스턴스인지 알 수도 없고 알 필요도 없다. EnumSet의 하위 클래스이기만 하면 되는 것이다. JDK 라이브러리를 만드는 입장에서는 개선된 버전을 차기 OpenJDK 버전에 추가해서 배포하고, 반환 타입만 그대로 EnumSet 을 리턴해두도록 해두면 된다.<br>

<br>

### 5 ) 다섯 번째, 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

> 참고 : 리플렉션 (아이템 65) <br>

정적 팩터리 메서드를 사용할 때는, 어떤 구현체를 클라이언트에 반환할지 명시하기 애매할 수 있다. 이럴 때 상위타입의 Interface 객체를 리턴하도록 해두고, 서비스 접근 API 를 활용해서 구체적인 인스턴스를 반환하도록 해두는 방식이다. 대표적으로 Java 에서는 서비스 제공자 프레임워크 라는 개념이 있다. 서비스 제공자 프레임 워크를 대표하는 라이브러리는 JDBC 라이브러리이다. 서비스 제공자 프레임 워크는 아래의 3가지 컴포넌트로 구성된다.<br>

- 서비스 인터페이스(Service Interface)
  - 구현체의 동작을 정의
- 제공자 등록 API (Provider Registration API)
  - 제공자가 구현체를 등록할 때 사용하는 API
- 서비스 접근 API (Service Access API)
  - 클라이언트가 서비스의 인스턴스를 얻을 때 사용하는 API

<br>

API를 사용하는 입장에서 위에 나열한 것들 중 세번째 요소인 `서비스 접근 API`를 사용해 원하는 클래스에 대한 인스턴스를 생성하고자 할 때 원하는 구현체의 조건을 명시할 수 있다. 만약 클라이언트가 아무런 조건을 명시하지 않은 채로 서비스 접근 API를 사용할 때 기본 구현체를 반환하거나, 지원 가능한 구현체들을 하나씩 돌아가면서 반환한다. 이런 서비스 접근 API가 바로 서비스 제공자 프레임워크의 근간인 '유연한 정적 팩터리'의 실체다.<br>

<br>

위의 세가지 컴포넌트에 서비스 제공자 인터페이스(Service Provider Interface)를 추가해서 네가지 컴포넌트로 사용되는 경우도 있다.

- 서비스 인터페이스(Service Interface)
  - 구현체의 동작을 정의
- 제공자 등록 API (Provider Registration API)
  - 제공자가 구현체를 등록할 때 사용하는 API
- 서비스 접근 API (Service Access API)
  - 클라이언트가 서비스의 인스턴스를 얻을 때 사용하는 API
- 서비스 제공자 인터페이스 (Service Provider Interface)
  - 서비스 인터페이스의 인스턴스를 생성하는 팩터리 객체를 설명하는 역할을 수행
  - 서비스 제공자 인터페이스가 없다면 각 구현체를 인스턴스로 만들 때 리플렉션 (아이템 65) 를 사용해야 한다.

<br>

**JDBC 에서의 서비스 제공자 프레임워크 사용 사례**<br>

더 정리 예정... 이펙티브 자바는 가끔 어떤 아이템에서는 굉장히 말을 길게 써서 정리하게 힘들게 만들때가 있다. 아이템 1이 특히 그렇다.

<br>

## 단점

- 상속을 하려면 public 이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.

  - 상속보다 컴포지션을 사용(아이템 18)하도록 유도하고, 불변타입으로 만들려면 이 제약을 지켜야 한다는 점에서 오히려 장점으로 적용됨

- 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.

  - 자바독 처리도 적용되지 않고, API 설명에도 명확히 드러나지 않는다. 따라서 잘 알려진 네이밍 컨벤션을 사용하고, API문서도 잘 작성해놓아야 한다.

  - 생성자의 경우는 API 설명에 명확히 드러나지만, 정적 팩터리 메서드는 API 설명에 명확히 드러나지 않는다.
  - 이런 이유로 정적 팩터리 메서드 방식 클래스를 인스턴스화할 방법을 알아내야 한다. 자바독에서도 이것을 처리해주지 못한다.
  - 따라서 API 문서를 잘 써놓고 메서드 이름도 널리 알려진 규약을 따라 짓는 방식으로 문제를 완화해줘야 한다.

<br>

## 네이밍 컨벤션

- `from` 
  - 매개변수를 하나 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드에 사용
  - ex) `Date d = Date.from(instant)`
- `of` 
  - 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계메서드
  - ex) `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING)` 
- valueOf
  - from 과 of 의 더 자세한 버전
  - ex) `BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE)` 
- instance, getInstance
  - (매개변수를 받는다면) 매개변수로 명시한 인스턴스를 반환하지만, 같은 인스턴스임을 보장하지는 않는다.
  - ex) `StackWalker Luke = StackWalker.getInstance(options)` 
- create, newInstance
  - instance 혹은 getInstance와 같지만 매번 새로운 인스턴스를 생성해 반환함을 보장한다.
  - ex) `Object newArray = Array.newInstance(classObject, arrayLen)`
- getType
  - getInstance와 같으나, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 쓴다.
  - `Type` 은 팩터리 메서드가 반환할 객체의 타입이다.
  - ex) `FileStore fs = Files.getFileStore(path)` 

- newType
  - newInstance 와 같지만, 생성할 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 사용한다.
  - `Type` 은 팩터리 메서드가 반환할 객체의 타입이다.
  - ex) `BufferedReader br = Files.newBufferedReader(path)` 

- type
  - getType과 newType 의 간결한 버전
  - ex) `List<Complaint> litany = Collections.list(legacyLitany)` 

<br>

