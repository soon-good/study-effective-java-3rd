# ITEM 10. equals 는 일반 규약을 지켜 재정의하라

보통 일반적인 Object의 equals() 메서드는, 인스턴스 자체의 hashCode를 비교한다. 이렇게 하면, 객체가 같은지 아닌지를 비교하게 된다. 만약 논리적인 값으로 동치성을 비교하고 싶다면, equals() 메서드를 오버라이딩하면 된다. 이번 장에서 다루는 주요 내용들을 요약해서 리스트업 해보면 아래와 같다.

- equals() 메서드를 재정의할 필요가 없는 경우들
- equals() 메서드를 재정의해야 하는 경우들 

<br>

equals() 관련 챕터인 이번 장은 너무 엄청나게 매우 길어서, 3일 ~ 4일은 걸려야 정리가 잘 될 것 같다. 잠시동안 해야하는 다른 일이 있어서 잠시 이펙티브 자바 정리를 미뤄두고 있었는데, 한번 정리할 때 하루에 30~1시간 정도는 투자를 해서 정리를 ㅐㅎ야 할것 같다. 어차피 야간 모니터링을 거의 매의 해야 되니... 멍때리기보다는 스터디를 ... 해야 겠다. 모니터링을 하다보면, 실제 공부를 할 수 있는 시간보다는 모니터링을 하는데에 시간을 많이 뺏기긴 하지만... 그래도 남는 시간 잘 활용해서 지금 상황을 벗어나야 할 것 같다!!!

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

### 2 ) 인스턴스의 '논리적 동치성(logical equality)'을 검사할 일이 없다.

논리적인 동치를 판단하는 것이 굳이 필요하다고 생각되지 않는다면, equals() 메서드는 오버라이딩하지 않는 것이 좋은 선택이다.<br>

java.util.regex.Pattern 클래스를 예로 들어보자. Pattern 클래스를 이용해 서로 다른 인스턴스 두개를 만들었다고 해보자. 이 때 두 인스턴스가 바인딩하고 있는 정규표현식이 서로 같은지를 검사하려고 할 수 있다. 보통 이런 경우 설계자 입장에서는 클라이언트 측에서 이런 기능이 필요하지 않다고 생각할 수 있고, 또는 애초에 필요하지 않다고 판단할 수 있다. 이렇게 필요없는 논리적 동치성에 대해서는 equals() 메서드를 재정의하지 않는 것이 낫다.<br>

### 3 ) 상위 클래스에서 재정의한 equals 가 하위 클래스에도 딱 들어맞는다.

예를 들면,`AbstractSet`, `AbstractMap` 을 예로 들 수 있다. 대부분의 Set 구현체, Map 구현체들은 `AbstractSet`, `AbstractMap` 을 상속받아 정의되어 있다. 이 AbstractSet, AbstractMap 에서는 equals() 메서드를 구현하고 있는데, 이 것이 하위 클래스에도 적합하게 적용가능하다. 따라서 Set, Map 타입의 컬렉션을 구현해야 할 일 이 생긴다면 equals() 메서드를 오버라이딩을 하지 않아도 된다.<br>

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

**null 이 아닌 모든 참조값 x, y 에 대해 x.equals(y) == true 이고, y.equals(z) == true 이면 x.equals(z) 도 true 이다.**<br>

첫 번째 객체와 두 번째 객체가 같고, 두 번째 객체와 세 번째 객체가 같다면, 첫 번째 객체와 세 번째 객체도 같아야 한다는 뜻이다.<br>

<br>

언뜻 보면 굉장히 당연한 이야기이고, 쉬운 문제처럼 보여 가볍게 여기기 쉬운 개념이다. 책에서는 두 가지 경우를 예로 들어 추이성이 깨지는 경우를 설명하고 있다. 사실 이런 법칙은 단순히 Java 뿐만 아니라, 다른 언어를 사용해야 할때 equals() 류의 메서드를 overridding 할 때에도 경험을 높여줄 수 있는 요소인것 같다.<bR>

좌표를 표현하는 Point 라는 클래스가 있다고 하자. 그런데, 여기에 색상정보까지 포함하고 있는 ColorPoint 라는 클래스가 있다. ColorPoint 클래스는 Point 클래스를 상속하고 있다. 따라서 ColorPoint 클래스에 별다른 오버라이딩을 하지 않는 다면, ColorPoint 클래스의 equals() 메서드는 Point 클래스의 equals() 메서드를 사용하게 된다.<br>

이때 ColorPoint 클래스로 다른 Point 타입의 객체와 equals 연산을 할 수 있는 방법은 있을까?<br>

**클래스를 확장(extends - 상속)하는 방식으로 equals() 를 만족시키려 할 경우**<br>

결론부터 말하자면 구체 클래스를 확장(extends - 상속)하는 방식으로는 새로운 값을 추가하면서 equals 규약을 만족시킬 방법은 존재하지 않는다. 이 규칙이 어떤 때에 안맞고, 안맞는 경우들에 대한 설명은 아래에 따로 예시로 정리해두기로 했다.<br>

**상속 대신 컴포지션을 사용할 경우(아이템 18)**<br>

괜찮은 우회방법이다. **Point를 상속(확장 - extends)하는 대신 Point 를 ColorPoint 의 private 필드로 두고**, ColorPoint 와 같은 위치의 Point 를 반환하는 **뷰(view) 메서드(아이템 6)를 public 으로 추가하는 방식**이다.<br>

이 경우 역시 예제로 정리해두었다. 가끔 이런 경우를 보면, 어떤 문제를 어려워보이게 집착해서 풀기보다는 현명하게 우회해서 다른 예외의 경우는 제외하고올바른 비교를 하게끔 유도하는 방식으로 풀어내는 경우가 있다는 생각이 든다. 이렇게 view 메서드로 형변환을 해서 직접 올바른 타입을 사용해 비교하도록 유도하는 방식은 다른 언어에서도 유연하게 적용이 가능한 방식인 것 같고, 다른 언어에서도 경험이 많은 프로그래머가 비슷한 방식으로 정리한 내용들이 있지 않을까 싶다.(조슈아 블로크라는 자바의 아버지 격인 저자가 설명해주었듯이.)<br>

<br>

**리스코프 치환원칙**<br>

리스코프 치환원칙에 대해서도 설명이 나오는데, 여기에 따로 요약해서 정리 예정. (예제는 따로 정리하지 않을 예정이다.)<br>

<br>

#### case 1) 대칭성을 위배하는 경우

내용 정리... 이번 주 내 차츰 정리 예졍

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



#### case 2) 추이성을 위배하는 경우

내용 정리... 이번 주 내 차차 정리 예졍

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

#### case 3) 상속 대신 컴포지션을 사용해, 별도의 객체를 사용해 equals 를 수행하도록 유도하기

내용 정리... 이번 주 내 차차 정리 예졍<br>

asPoint() 같은 함수를 통해 형변환을 수행하고, 변환된 타입과 비교하려는 객체를 비교하도록 하는 방식이다.

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

**null 이 아닌 모든 참조값 x,y 에 대해 x.equals(y) 를 반복해서 호출하면, 항상 true를 반환하거나 항상 false 를 반환한다.**<br>

<br>

### null-아님

**null 이 아닌 모든 참조값 x 에 대해 x.equals(null) 은 false 다.**<br>



<br>

<br>









