# 5.5.3 세마포어

카운팅 세마포어(counting semaphore)는 특정 자원이나 특정 연산을 동시에 사용하거나 호출할 수 있는 스레드의 수를 제한하고자 할때 사용한다.<br>

카운팅 세마포어의 이런 기능을 활용하면 자원 풀(pol) 또는 자원 컬렉션의 크기에 제한을 두고자 할 때 유용하다.<br>

**이진 세마포어**<br>

카운팅 세마포어를 조금 더 간단한 구조로의 형태로 이해하려 한다면 이진 세마포어를 예로 들어서 생각해볼 수 있다. (이진 세마포어는 초기 퍼밋값이 1로 지정된 카운팅 세마포어다.) 

이진 세마포어는 비재진입(nonreentrant) 락 의 역할을 하는 뮤텍스(metex)로 활용할 수 있다. 이진 세마포어의 퍼밋을 가지고 있는 스레드가 뮤텍스를 확보한 것이다.<br>

<br>

## 참고할만한 자료

- [aroundck.tistory.com - Semaphore에 대해 알아보자](https://aroundck.tistory.com/5873)

<br>

## `Semaphore` 클래스

Semaphore 클래스는 가상의 permit 을 만들어 내부 상태를 관리한다. 

**permit 갯수 지정**<br>

Semaphore 클래스의 객체를 생성할때 생성자 내의 파라미터로 permit 의 갯수를 넘겨주어서 permit의 갯수를 지정할 수 있다.<br>

**permit 의 확보/반납**<br>

외부 스레드를 이용해 permit을 요청해 확보하거나 이전에 확보한 permit을 반납하는 것 역시 가능하다.<br>

**aquire 메서드**<br>

남는 permit이 생기거나, 인터럽트가 걸리거나 타임아웃이 걸린  퍼밋이 반납될때까지 기다리고자 할 때 aquire 메서드를 사용할 수 있다.<br>

**release 메서드**<br>

확보했던 permit을 다시 세마포어에게 반납하는 역할을 수행한다.<br>

<br>

## ex) 자원 풀에서의 활용 (ex. 데이터베이스 자원 풀 등)

세마포어는 데이터베이스 연결 풀과 같은 자원 풀에서 요긴하게 사용할 수 있다.

자원 풀을 만들때 자원풀이 꽉 차있을 때 자원 획득 요청이 들어오면 단순히 Exception 을 발생시키도록 구현하는 것은 어렵지 않다. 하지만, 자원풀이 모두 차 있을 대 다른 스레드가 확보했던 객체를 반납받아서 사용하기 위해 기다리도록 하는 로직을 작성하는 것은 그리 쉽지 않다. 

이런 경우에 카운팅 세마포어를 만들어서 최초 permit 의 갯수로 원하는 pool 의 크기를 지정해보자.

pool 에서 자원을 할당받아 가려고 할대는 먼저 acquire 를 호출해 permit 을 확보하도록 하고, 다 사용한 자원을 반납하고 난 다음에는 항상 release를 호출해 permit 도 반납하도록 한다. 그러면 pool 에 자원이 남아있지 않은 경우에 `acquire` 메서드가 대기 상태에 들어가기 때문에 객체가 반납될 때 까지 자연스럽게 대기하게 된다. 이런 기법은 12장에서 다루게 되는 bounded 버퍼 클래스 (길이가 제한된 버퍼 클래스) 에서도 사용된다. <br>

또는 이렇게 크기가 제한된 객체 풀이 필요한 경우에는 BlockingQueue 컬렉션 클래스를 사용하는 것도 하나의 방법이다.<br>

<br>

## 예제 - BoundedHashSet

세마포어를 활용하면 어떤 클래스라도 크기가 제한된 컬렉션 클래스로 활용할 수 있다.

```java
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore sem;
    
    public BoundedHashSet(int bound){
        this.set = Collections.synchronizedSet(new HashSet<T>());
        // Semaphore 를 원하는 크기인 bounded 만큼으로 지정한다.
        sem = new Semaphore(bound);
    }
    
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        
        try{
            wasAdded = set.add(o);
            return wasAdded;
        }
        finally{
            if(!wasAdded){
                sem.release();
            }
        }
    }
    
    public boolean remove(Object o){
        boolean wasRemoved = set.remove(o);
        if(wasRemoved) sem.release();
        
        return wasRemoved;
    }
}
```



Semaphore 객체를 생성할 때 `BoundedHashSet` 의 `bounded` 값에 맞게끔 Semaphore의 크기를 설정하고 있다.

### 생성자

```java
public class BoundedHashSet<T>{
    private final Semaphore sem;
    // ...
    
    public BoundedHashSet(int bound){
        // ...
        sem = new Semaphore(bound);
    }
}
```

<br>

### `add(T o)` 메서드

번역서에서 예제의 add 메서드를 설명하는 부분은 다소 빈약하다. 그래서 따로 아래 예제에 대한 내용을 정리를 해보면 이렇다. `Semaphore` 클래스에 대한 자세한 설명은 [여기](https://aroundck.tistory.com/5873)를 참고하자.

- `sem.acquire();`
  - semaphore 의 permit 을 하나 가져간다. (permit 을 -1 시킨다.)
  - permit 이 양수일 경우는 thread가 계속 run 되는 상태이고
  - 그렇지 않을 경우는 permit 을 획득할 때까지 해당 라인에서 대기하는 상태가 된다.
- `wasAdded = set.add(o)`
  - set 에 데이터가 정상적으로 추가 되는지 체크한다. 
  - 만약 문제가 있어서 set에 데이터가 추가되지 않으면 `wasAdded` 에는 `false` 가 기록되게 된다.
- sem.release();
  - permit 갯수를 +1 시킨다.

```java
public class BoundedHashSet<T>{
	private final Semaphore sem;
    private final Set<T> set;
    
    // ..
    
    public boolean add(T o) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        
        try{
            wasAdded = set.add(o);
            return wasAdded;
        }
        finally {
            if(!wasAdded){
                sem.release();
            }
        }
    }
    
    // ...
}
```

<br>

### remove 메서드

이건 나중에 정리... 흐ㅡㅎㅁㄴㅇ라ㅣ
