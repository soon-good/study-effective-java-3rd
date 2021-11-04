# ITEM 11. equals를 재정의하려거든 hashCode도 재정의하라

> 해시를 사용하는 컬렉션 내에서 논리적으로 객체가 같음을 보장하기 위해서는 hashCode() 를 잘 작성해야 한다. 

<br>

Chapter2 의 ITEM 들은 대부분 매우 길고, 산만하다. 지금 정리하는 아이템 11도 그렇다. 아이템 10의 경우는 굉장히 긴데, 정리하는데 꽤 시간이 걸릴 것 같다.<br>

<br>

## equals 를 재정의한 클래스에서는 반드시 hashCode 도 재정의해야 한다.

hashCode 를 재정의하지 않으면 hashCode 일반 규약을 어기게 된다. 만약 내가 만들려는 클래스에서 equals 메서드를 재정의해서, 동치관계 검사를 수정했다고 해보자. 이 경우, hashCode 메서드도 수정해주어야 한다. 그래야 HashMap, HashSet 과 같은 컬렉션에서 컬렉션 내의 원소로 사용할 때 문제를 일으키지 않는다.<br>

<br>

## 참고) Object 명세 내의 equals 관련 규약<br>

 - hashCode 메서드는 몇 번을 호출해도 일관되게 항상 같은 값을 반환해야 한다.
   - equals 비교에 사용되는 정보가 변경되지 않았다면, 애플리케이션이 실행되는 동안 같은 객체에 대한 hashCode는 몇번을 호출해도 같은 값을 반환해야 한다. 
 - equals(Object) 가 두 객체를 같다고 판단하면, 두 객체의 hashcode 는 똑같은 값을 반환해야 한다.
 - equals(Object) 가 두 객체를 다르다고 판단했어도, 두 객체의 hashCode가 서로 다른 값을 값을 반환할 필요는 없다.
   - 단, 다른 객체에 대해서는 다른 값을 반환해야 해시테이블의 성능이 좋아진다.
   - 즉, 다른 객체에 대해 hashCode 가 항상 유일해야 하는 것은 필수는 아니지만, 항상 유일하게끔 hashCode를 구현하면, 해시테이블의 성능이 좋아진다.

<br>

## equals(Object) 로 두 객체가 같다고 판정되면 hashCode() 도 같은 값을 반환해 두 객체가 같음을 보장해야 한다

Object 명세 내의 두번째 조항인 **"equals(Object) 가 두 객체를 같다고 판단하면, 두 객체의 hashCode는 똑같은 값을 반환해야 한다"**는, hashCode를 잘못 재정의 했을때 크게 문제가 되는 조항이다. 논리적으로 같은 객체는 같은 해시코드를 반환해야 한다.<br>

예를 들면, hashMap, hashSet과 같은 컬렉션 내에 객체를 키로 사용하는 경우에 모호함이 발생하기 때문이다. <br>

Object 의 hashCode를 그대로 사용하는 객체 PhoneNumber p1 이 있다고 해보자. 그리고 PhoneNumber 객체에는 equals 규약을 지켜서 equals 메서드가 정의되어 있다. 이 경우 p1 과 완전히 같은 값을 복사한 PhoneNumber p2 가 있다고 해보자. 이때 p1.equals(p2)는 성립한다. <br>

하지만 p1.hashCode() 와 p2.hashCode() 값은 서로 다르다. Object의 hashCode를 재정의 하지 않고 그냥 사용했기 때문인데. Object의 hashCode는 객체의 해시코드 문자열을 만들어내기 때문에 PhoneNumber 클래스에 hashCode()메서드를 재정의하지 않으면 p1, p2 의 hashCode()는 다를 수 밖에 없다.<br>

아래의 예제코드를 보자. (단, PhoneNumber 클래스는 hashCode() 메서드를 재정의하지 않은 상태임을 가정한다.)

```java
Map<PhoneNumber, String> m = new HashMap<>();
m.put(new PhoneNumber(707, 867, 5309), "제니");
```

이 코드 다음에 논리적으로 같은 값이 주입된 m.get(new PhoneNumber(707, 867, 5309)) 를 실행하면 "제니"가 나와야 할 것 같지만, 실제로는 null 을 반환한다. hashCode() 를 재정의하지 않아서 Object 의 hashCode() 메서드가 사용되었기 때문이다.<br>

이렇게 되면 get메서드가 엉뚱한 해시버킷에 가서 객체를 찾으려 하게 된다. 또는 두 인스턴스를 같은 버킷에 담게 되더라도 get 메서드는 여전히 null 을 반환하게 된다. HashMap 은 해시코드가 다른 엔트리끼리는 동치성 비교를 시도조차 하지 않도록 최적화되어 있기 때문이다.<br>

이렇게 해시를 사용하는 컬렉션 내에서 논리적으로 객체가 같음을 보장하기 위해서는 hashCode() 를 잘 작성해야 한다. 이 hashCode() 를 작성하는 올바른 원칙과 hashCode() 를 잘못 사용하는 예에 대해서 아래에서 설명하고 있다.<br>

## hashCode() 를 잘못 사용하는 예

## hashCode를 작성하는 올바른 요령



