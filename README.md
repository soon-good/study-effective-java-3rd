# Effective Java 3/E

[Effective Java 3/E](http://www.yes24.com/Product/Goods/65551284) 의 내용들을 정리해두는 곳. 이펙티브 자바는 장점이 명확하다. 개발업무를 하면서 이게 맞나? 하고 혼동되는 내용들에 대해서 하나씩 조언을 해주고 있다. 하지만, 책의 내용이 가끔가다 다소 횡설수설해서 논지를 파악하기 어려울 때가 많다는 점에서 단점 역시도 명확한 것 같다.<br>

이번 Effective Java 책 내용 정리는 5개월 목표로 잡고, 차근 차근 정리해나갈 예정이다.<br>

**2021/12/12**<br>

김포프님의 유튜브를 자주 보는 편이다. 직장 생활을 하면서 중급자에 가까운 경력이 되는 시점에 프로그래머로서 능력이 있는지 판별하는 기준은 기본기라고 말씀을 자주해주시는데, 여기에 동감한다. 요즘 ISTO, 스프링 클라우드 등, 어려운 내용으로 현혹시키는 경우가 자주 있다. 하지만, 개발자의 회사생활의 본질은 공식문서를 보고 문제해결력이 있는지, 제품의 도메인을 제대로 이해하고 있는지가 성패의 관건이다. 누구든 처음해보는 경우가 많은데 이것을 외워서 하는 경우는 없다. 오히려 처음 접해보는 기술에 접근하는 문제 해결력이 중요하다.<br>

하지만, 신기술을 업무에 사용하는 것과 기본기는 다른 것 같다. 새로운 기술을 안써본 것은 부끄럽지 않아도 된다. 오히려 입사해서 이거 같이 개발해볼래요? 하면서 분위기가 좋아질 수 있다. 하지만, 기본기가 떨어지는 것을 면접 때가 아니더라도 언젠가 들키게 되면, 그 사람은 경력이 중급이어도 부끄러운 민낯을 들키는 것이지 않을까 싶다. 올해 5월 에 이직하면서 느낀점은, 인터뷰 때 대부분 단순히 뭔가를 해봤어? 하는 질문류들이 굉장히 많았다. 하지만, 시니어 급의 면접관 분들이 참여하신다면, 조금 이야기가 달라졌던 것 같다.<br>

내 경우는 전 직장에 입사할 때 면접 시에 시니어분들이 effective java 등에서 나왔던 중요한 개념들을 유도해가며 질문을 받았던 경험이 있다. hashCode 의 개념이 무엇인지에서부터, 싱글턴은 뭔지, 어떻게 구현하는지 설명하게끔 유도하고, 스프링의 장점을 설명해보라고 하는 등의 질문을 받았었다. 물론 전직장 개발팀장님 뿐 아니라, OO 서비스의 파트장님도 함께 면접에 참여해서 다 각도의 인성면접과, 참을성(?)테스트 등을 받았던것 같다.<br>

이런 이유가 Effective Java를 굳이 힘들게 정리하려는 이유다. 기본기는 프로그래머가 직접 키워나가야 한다. 누가 가르쳐주지 않는다. 처음에는 Effective Java 의 내용을 정리하는 것이 5개월 이면 될 것 같았다. 하지만, 회사일도 있고, 제대로 내용을 알고 정리하려면, 다른 공부도 병행하면서 내년 4월 까지를 목표를 잡아야 할 것 같다는 생각이 든다. 기본기는 빠르게 쌓이는 것이 아니고, 시간을 들여서 꾸준히 쌓아나가야 한다는 생각으로 정리할 예정이다.<br>

<br>

## Useful Links

깃허브 저장소

- 저자 (조슈아 블로크) 깃허브 저장소
  - [https://github.com/jbloch/effective-java-3e-source-code](https://github.com/jbloch/effective-java-3e-source-code)
  - Java 개발을 해본 사람이라면 누구든 들어봤을 유명한 분. effective 시리즈는 모두 해당 언어에서 유명한 분들이 저술하는 듯해보인다.
- 한국어판 깃허브 저장소
  - [github.com/WegraLee/effective-java-3e-source-code](https://github.com/WegraLee/effective-java-3e-source-code)
  - 역자가 제공해주시는 깃허브 저장소다.
  - 원서 출간 후 반년이 지나도록 저자가 예제 소스를 공개하지 않다가 8개월 째에 소스가 공개되었다고 한다. 위의 링크는 한국어판 예제 코드 저장소다.

<br>

동영상 강의

- 백기선님, 이펙티브 자바 강의 : [https://www.youtube.com/watch?v=X7RXP6EI-5E&list=PLfI752FpVCS8e5ACdi5dpwLdlVkn0QgJJ](https://www.youtube.com/watch?v=X7RXP6EI-5E&list=PLfI752FpVCS8e5ACdi5dpwLdlVkn0QgJJ)

<br>

번역용어 해설

- 이펙티브 자바 3판, 번역 용어 해설
  - [http://bit.ly/2Mr1ksE](https://docs.google.com/document/d/1Nw-_FJKre9x7Uy6DZ0NuAFyYUCjBPCpINxqrP0JFuXk/edit)

<br>

## 목차

1장 들어가기<br>

2장 객체 생성과 파괴<br>

- [아이템 1 - 생성자 대신 정적 팩터리 메서드를 고려하라](https://github.com/gosgjung/study-effective-java-3nd/blob/main/ITEM-1-%EC%83%9D%EC%84%B1%EC%9E%90-%EB%8C%80%EC%8B%A0-%EC%A0%95%EC%A0%81-%ED%8C%A9%ED%84%B0%EB%A6%AC-%EB%A9%94%EC%84%9C%EB%93%9C%EB%A5%BC-%EA%B3%A0%EB%A0%A4%ED%95%98%EB%9D%BC.md)
  - 정리완료 (2021.10.19)
- [아이템 2 - 생성자에 매개변수가 많다면 빌더를 고려하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-2-%EC%83%9D%EC%84%B1%EC%9E%90%EC%97%90-%EB%A7%A4%EA%B0%9C%EB%B3%80%EC%88%98%EA%B0%80-%EB%A7%8E%EB%8B%A4%EB%A9%B4-%EB%B9%8C%EB%8D%94%EB%A5%BC-%EA%B3%A0%EB%A0%A4%ED%95%98%EB%9D%BC.md)
  - 정리완료(2021.10.24)
- [아이템 3 - private 생성자나 열거 타입으로 싱글턴임을 보증하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-3-private-%EC%83%9D%EC%84%B1%EC%9E%90%EB%82%98-%EC%97%B4%EA%B1%B0%ED%83%80%EC%9E%85%EC%9C%BC%EB%A1%9C-%EC%8B%B1%EA%B8%80%ED%84%B4%EC%9E%84%EC%9D%84-%EB%B3%B4%EC%A6%9D%ED%95%98%EB%9D%BC.md)
  - 정리 중, 90% 완료. 몇가지 요약만 추가하고 마무리 예정
- [아이템 4 - 인스턴스화를 막으려거든 private 생성자를 사용하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-4-%EC%9D%B8%EC%8A%A4%ED%84%B4%EC%8A%A4%ED%99%94%EB%A5%BC-%EB%A7%89%EC%9C%BC%EB%A0%A4%EA%B1%B0%EB%93%A0-private-%EC%83%9D%EC%84%B1%EC%9E%90%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
  - 정리 중, 차주 내지 차차주에 정리 예정. (막상 읽어보니 쉬어가는 아이템인지라...)
- [아이템 5 - 자원을 직접 명시하지 말고 의존 객체 주입을 사용하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-5-%EC%9E%90%EC%9B%90%EC%9D%84-%EC%A7%81%EC%A0%91-%EB%AA%85%EC%8B%9C%ED%95%98%EC%A7%80-%EB%A7%90%EA%B3%A0-%EC%9D%98%EC%A1%B4-%EA%B0%9D%EC%B2%B4%EC%A3%BC%EC%9E%85%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
  - 정리완료(2021.10.30)
  - 마음에 드는 정도로 요약된 것은 아니지만, 일단 지금은 이정도 까지만 요약해둔 후 나중에 다시 또 정리하는게 맞겠다는 생각이 들어서 완료 처리
- [아이템 6 - 불필요한 객체 생성을 피하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-6-%EB%B6%88%ED%95%84%EC%9A%94%ED%95%9C-%EA%B0%9D%EC%B2%B4-%EC%83%9D%EC%84%B1%EC%9D%84-%ED%94%BC%ED%95%98%EB%9D%BC.md)
  - 정리 중, 90% 완료. 차주 중으로 마무리 예정
- [아이템 7 - 다 쓴 객체 참조를 해제하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-7-%EB%8B%A4-%EC%93%B4-%EA%B0%9D%EC%B2%B4%EC%B0%B8%EC%A1%B0%EB%A5%BC-%ED%95%B4%EC%A0%9C%ED%95%98%EB%9D%BC.md)
  - 정리 중, 90% 완료. 차주 중으로 마무리 예정
- 아이템 8 - finalizer 와 cleaner 사용을 피하라

3장 모든 객체의 공통 메서드<br>

- [아이템 10 - equals 는 일반 규약을 지켜 재정의하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-10-equals%EB%8A%94-%EC%9D%BC%EB%B0%98-%EA%B7%9C%EC%95%BD%EC%9D%84-%EC%A7%80%EC%BC%9C-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC.md)
  - 정리중. 95%
  - 나중에 조금 더 보완해야 한다.
  - 2021.12.26
    - 일이 바쁜 중에 매일 30분씩 시간을 쪼개서 하느라 늦어진 것도 있지만 ITEM10 은 너무 길었다. 
    - 내일 커밋할까 하는 유혹이 있었지만, 너무 오랫동안 작성해오느라 지쳐서 일단 커밋했다.
- [아이템 11 - equals 를 재정의하려거든 hashCode 도 재정의하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-11-equals%EB%A5%BC-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%A0%A4%EA%B1%B0%EB%93%A0-hashCode%EB%8F%84-%EC%9E%AC%EC%A0%95%EC%9D%98%ED%95%98%EB%9D%BC.md)
  - 정리 중. 90%
  - hashCode 를 재정의하는 여러가지 경우의 수들을 시간을 많이 들여서 정리해볼 예정.
- 아이템 12 - toString 을 항상 재정의하라
- 아이템 13 - clone 재정의는 주의해서 진행하라
- [아이템 14 - Comparable 을 구현할지 고려하라](https://github.com/gosgjung/study-effective-java-3nd/blob/develop/ITEM-14-Comparable%EC%9D%84-%EA%B5%AC%ED%98%84%ED%95%A0%EC%A7%80-%EA%B3%A0%EB%A0%A4%ED%95%98%EB%9D%BC.md)
  - 정리완료(2021.11.11)


4장 클래스와 인터페이스<br>

- 아이템 15 - 클래스와 멤버의 접근 권한을 최소화하라
- 아이템 16 - public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라
- 아이템 17 - 변경 가능성을 최소화하라
- 아이템 18 - 상속보다는 컴포지션을 사용하라
- 아이템 19 - 상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라
- 아이템 20 - 추상 클래스보다는 인터페이스를 우선하라
- 아이템 21 - 인터페이스는 구현하는 쪽을 생각해 설계하라
- 아이템 22 - 인터페이스는 타입을 정의하는 용도로만 사용하라
- 아이템 23 - 태그달린 클래스보다는 클래스 계층구조를 활용하라
- 아이템 24 - 멤버 클래스는 되도록 static 으로 만들라
- 아이템 25 - 톱 레벨 클래스는 한 파일에 하나만 담으라

5장 제네릭<br>

- 아이템 26 - 로(raw) 타입은 사용하지 말라
- 아이템 27 - 비검사 경고를 제거하라
- 아이템 28 - 배열보다는 리스트를 사요하라
- 아이템 29 - 이왕이면 제너릭 타입으로 만들라
- 아이템 30 - 이왕이면 제너릭 메서드로 만들라
- 아이템 31 - 한정적 와일드카드를 사용해 API 유연성을 높이라
- 아이템 32 - 제너릭과 가변인수를 함께 쓸때는 신중하라
- 아이템 33 - 타입안전 이중 컨테이너를 고려하라

6장 열거타입과 애너테이션<br>

- 아이템 34 - int 상수 대신 열거타입을 사용하라
- 아이템 35 - ordinal 메서드 대신 인스턴스 필드를 사용하라
- 아이템 36 - 비트 필드 대신 EnumSet을 사용하라
- 아이템 37 - ordinal 인엑싱 대신 EnumMap 을 사용하라
- 아이템 38 - 확장할 수 있는 열거타입이 필요하면 인터페이스를 사용하라
- 아이템 39 - 명명 패턴보다 애너테이션을 사용하라
- 아이템 40 - @Override 애너테이션을 일관되게 사용하라
- 아이템 41 - 정의하려는 것이 타입이라면 마커 인터페이스를 사용하라

7장 람다와 스트림<br>

- 아이템 42 - 익명 클래스보다는 람다를 사용하라
- 아이템 43 - 람다보다는 메서드 참조를 사용하라
- 아이템 44 - 표준 함수형 인터페이스를 사용하라
- 아이템 45 - 스트림은 주의해서 사용하라
- 아이템 46 - 스트림에서는 부작용(Side Effect)이 없는 함수를 사용하라
- 아이템 47 - 반환 타입으로는 스트림보다 컬렉션이 낫다
- 아이템 48 - 스트림 병렬화는 주의해서 적용하라

8장 메서드<br>

- 아이템 49 - 매개변수가 유효한지 검사하라
- 아이템 50 - 적시에 방어적 복사본을 만들라
- 아이템 51 - 메서드 시그니처를 신중히 설계하라
- 아이템 52 - 다중 정의는 신중히 사용하라
- 아이템 53 - 가변인수는 신중히 사용하라
- 아이템 54 - null 이 아닌, 빈 컬렉션이나 배열을 반환하라
- 아이템 55 - 옵셔널 반환은 신중히 하라
- 아이템 56 - 공개된 API 요소에는 항상 문서화 주석을 작성하라

9장 일반적인 프로그래밍 원칙<br>

- 아이템 57 - 지역변수의 범위를 최소화하라
- [아이템 58 - 전통적인 for 문 보다는 for-each 문을 사용하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-58-%EC%A0%84%ED%86%B5%EC%A0%81%EC%9D%B8-for%EB%AC%B8-%EB%B3%B4%EB%8B%A4%EB%8A%94-for-each-%EB%AC%B8%EC%9D%84-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md) 
  - (정리완료: 2022.02.15)
- [아이템 59 - 라이브러리를 익히고 사용하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-59-%EB%9D%BC%EC%9D%B4%EB%B8%8C%EB%9F%AC%EB%A6%AC%EB%A5%BC-%EC%9D%B5%ED%9E%88%EA%B3%A0-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
  - (정리완료: 2022.02.22)
  - 자바 버전별 스펙 변화 문서 작성 후 별도 링크 추가 필요

- [아이템 60 - 정확한 답이 필요하다면 float와 double 은 피하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-60-%EC%A0%95%ED%99%95%ED%95%9C-%EB%8B%B5%EC%9D%B4-%ED%95%84%EC%9A%94%ED%95%98%EB%8B%A4%EB%A9%B4-float%EC%99%80-double%EC%9D%80-%ED%94%BC%ED%95%98%EB%9D%BC.md)
  - (정리완료: 2022.02.22)

- 아이템 61 - 박싱된 기본타입보다는 기본타입을 사용하라
- 아이템 62 - 다른 타입이 적절하다면 문자열 사용을 피하라
- 아이템 63 - 문자열 연결은 느리니 주의하라
- 아이템 64 - 객체는 인터페이스를 사용해 참조하라
- 아이템 65 - 리플렉션보다는 인터페이스를 사용하라
- 아이템 66 - 네이티브 메서드는 신중히 사용하라
- 아이템 67 - 최적화는 신중히 하라
- 아이템 68 - 일반적으로 통용되는 명명 규칙을 따르라

10장 예외<br>

- 아이템 69 - 예외는 진짜 예외 상황에만 사용하라
- 아이템 70 - 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라
- 아이템 71 - 필요없는 검사 예외 사용은 피하라
- 아이템 72 - 표준 예외를 사용하라
- 아이템 73 - 추상화 수준에 맞는 예외를 던지라
- 아이템 74 - 메서드가 던지는 모든 예외를 문서화하라
- 아이템 75 - 예외의 상세 메시지에 실패 관련 정보를 담으라
- 아이템 76 - 가능한 한 실패 원자적으로 만들라
- 아이템 77 - 예외를 무시하지 말라

11장 동시성<br>

- 아이템 78 - 공유 중인 가변 데이터는 동기화해 사용하라
- 아이템 79 - 과도한 동기화는 피하라
- 아이템 80 - 스레드보다는 실행자, 태스크, 스트림을 애용하라
- [아이템 81 - wait 와 notify 보다는 동시성 유틸리티를 애용하라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-81-wait%2Cnotify%EB%B3%B4%EB%8B%A4%EB%8A%94-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%9C%A0%ED%8B%B8%EB%A6%AC%ED%8B%B0%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%98%EB%9D%BC.md)
- 아이템 82 - 스레드 안전성 수준을 문서화하라
- 아이템 83 - 지연 초기화는 신중히 사용하라
- [아이템 84 - 프로그램의 동작을 스레드 스케쥴러에 기대지 말라](https://github.com/soon-good/study-effective-java-3rd/blob/develop/ITEM-84-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%A8%EC%9D%98-%EB%8F%99%EC%9E%91%EC%9D%84-%EC%8A%A4%EB%A0%88%EB%93%9C-%EC%8A%A4%EC%BC%80%EC%A4%84%EB%9F%AC%EC%97%90-%EA%B8%B0%EB%8C%80%EC%A7%80-%EB%A7%90%EB%9D%BC.md)

12장 직렬화<br>

- 아이템 85 - 자바 직렬화의 대안을 찾으라
- 아이템 86 - Serializable을 구현할 지는 신중히 결정하라
- 아이템 87 - 커스텀 직렬화 형태를 고려해보라
- 아이템 88 - readObject 메서드는 방어적으로 작성하라
- 아이템 89 - 인스턴스 수를 통제해야 한다면 readResolve 보다는 열거타입을 사용하라
- 아이템 90 - 직렬화된 인스턴스 대신 직렬화 프록시 사용을 검토하라

부록. 2판과의 아이템 대조표<br>

참고자료<br>

찾아보기<br>

<br>

<hr/>

## 2월

### 2월 둘째주 MUST DO

현재, Java 병렬 프로그래밍을 정리중이어서 시간이 조금 지체되는중. <br>

아래의 주제들은 가급적 짧게 정리할 수 있는 주제들인 것 같아서 30분 정도씩 짜투리 시간을 활용해 정리해둘 예정이다.<br>

다 못하면 셋째주로 이관 예정<br>

- 아이템 69 - 예외는 진짜 예외 상황에만 사용하라
- 아이템 70 - 복구할 수 있는 상황에는 검사 예외를, 프로그래밍 오류에는 런타임 예외를 사용하라
- 아이템 63 - 문자열 연결은 느리니 주의하라
- 아이템 64 - 객체는 인터페이스를 사용해 참조하라
- 아이템 58 - 전통적인 for 문 보다는 for-each 문을 사용하라

- 아이템 60 - 정확한 답이 필요하다면 float와 double 은 피하라
- 아이템 61 - 박싱된 기본타입보다는 기본타입을 사용하라

<br>