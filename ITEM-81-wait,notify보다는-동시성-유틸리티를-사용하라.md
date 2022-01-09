# Item81. wait, notify 보다는 동시성 유틸리티를 사용하라

얼마 전, 미국 주식 웹소켓 트래픽을 처리하는 데에 어려움을 겪었고, effective java 의 동시성 챕터에서 힌트를 얻은 후 웹 검색에서 언급하는 여러 자료를 읽어보면서 문제를 해결했다. 따로 문서를 만들어 정리해 둘 예정이었다. 그런데 굳이 그렇게 해야 할까 싶기도 하다. 노력 안하고 매번 남의 것 배껴쓰면서 본인이 한것인 마냥 연기하며 지식인처럼 읊는 사람을 직접 두눈으로 봐서... 그냥 에버노트에 정리해둔 것만 남겨두는게 나을까 싶기도 하다.<br>

다른 데이터 API 연동건도 있어서 이 연동 건은 시간이 많이 걸리고, 노가다성 작업이 많았는데, 이 작업과 웹소켓 트래픽 처리 건을 함께 처리하느라 하루에  두시간 밖에 못자면서 일주일 내내 스트레이트로 일했다. 수요일 하루 잠깐 뻗었긴 하지만... 데이터 API 단에서의 처리는 크게 문제가 안됐다. <br>

데이터 API 서버와 웹소켓 서버 사이의 관계는 1:1 이라 데이터 API 서버 측에서의 부하가 크기는 했지만 웹소켓 서버만큼 엄청 큰 정도는 아니었다. (=물론, 어느쪽이 부하가 큰지는 나중에 알았다.) 웹소켓 인스턴스 측에서의 처리가 부하가 더 컸다. 1:N 의 데이터 처리인데, AMD와 같은 회사들은 미국사람 말고도 신흥 아시아국에서도 매수가 많은 주식이라 트래픽이 어마어마 하다. 직접 로그로 확인한것만해도 1초에 1000건이 넘어가는 경우가 꽤 됐던것 같다. 운영 초기이고, 주어진 하드웨어 비용 내에서 적절하게 처리해야 했는데, 다행히도 인프라의 증설 없이 코드개선으로 트래픽을 처리가 잘 됐다. 화요일 자정에 트래픽 유입 테스트 후 상용 배포를 해야 상용서버 배포가 가능하다. 상용 배포 후에는 다중화를 적용해서 부하를 견딜만한 구조로 바꿔야할 것 같다.<br>

요즘 다른 회사를 알아봐야 하나 싶기도 하다. 매번 나한테 뭐 빚진거라도 있나 싶은 마음이 들정도로 담당자라고는 꼴랑 한명(=나) 밖에 없는데, 계속 유지하기에는 체력적으로는 이제는 한계다. 이런 일이 있을 때마다 하루 두시간씩 자면서 새벽 7시 반까지 사무실 출근해서 근무시간을 확보해서 일하는데, 이렇게 일하는 것도 일주일 내지 이주일 정도는 버티지.. 세달 이상을 이렇게 일하니 몸이 좀먹는 기분이다. 몸이 망가졌는지도 병원가서 확인해봐야 할 것 같다. 이렇게 만든 코드도 결국은 어떤 사람은 피말리게 초조한 마음으로 해결한 문제를 누군가는 웃고 떠들고 헬스할 거 헬스하면서 할거 하면서 노는 사람들에게 copy & paste 하게끔 이용될듯하다.<br>

처음부터 레피니티브라는 데이터 벤더사의 업무, 하드웨어 구성을 하나도 모르는 상태로 문제해결을 해가면서 파악해왔는데, 내 느낌상 이건 이용당하는 느낌이 좀 있다. 어떻게 운영업력 1년 이상 된 인원이 나보다 모를수가 있나 하는 생각이 들때가 한두번이 아니었으니까.(=이전 팀에서...)

<br>

## wait, notify 보다는 동시성 유틸리티를 애용하라

최근 버전의 java 에서는 wait, notify 를 사용할 일이 줄어들었다. wait, notify 를 올바르게 사용하는 것은 어렵다. 고수준의 동시성 유틸리티를 사용하면 간편하다. 실제로 wait, notify 기반으로 생산자 소비자 프로그램을 작성해봤었는데, 확실히 가독성이 많이 떨어지고, 클래스와 클래스 사이를 자주 왔다갔다 하면서 확인해야 했었다.<br>

책에서도 `wait 과 notify 는 올바르게 사용하기가 아주 까다로우니 고수준 동시성 유틸리티를 사용하자` 라고 이야기 해주고 있다.<br>

java.util.concurrent 에서 제공하는 고수준 유틸리티는 아래의 3가지 범주로 나눌 수 있다.<br>

- 실행자 프레임워크 (ExecutorService)
- 동시성 컬렉션 (Concurrent Collection)
  - List, Queue, Map 과 같은 표준 컬렉션 인터페이스에 동시성을 가미한 고성능 컬렉션. (아이템 79)
  - 높은 동시성을 실현하기 위해 동기화를 각자의 내부에서 실행한다. 
  - 동시성 컬렉션에 동시성을 무력화하는 것은 불가능하고, 외부에서 락을 사용하면 속도가 오히려 느려지게 된다.
- 동기화장치 (synchronizer)

이번 챕터에서는 동시성 컬렉션, 동기화 장치에 대해서 다룬다. 실행자 프레임워크는 아이템 80에 정리해둘 예정이다.<br>

<br>

## wait, notify

effective java 의 초판의 50 에서는 wait, notify 를 올바르게 사용하는 방법에 대해 설명하고 있었다. <br>

초판에서 언급했었던 wait, notify 를 올바로 사용하는 방법에 대해서는 item81 의 최 하단부에서 설명하고 있다. 이번 글에서도 최 하단부에 정리할 예정이다. <br>

java 5 에서부터 도입된 고수준의 동시성 유틸리티가 도입되었는데, 이 고수준의 동시성 유틸리티를 사용하면 wait, notify 로 직접 하드코딩해야 했던 일들을 처리해주기 때문이다.<br>

<br>

## 1) 동시성 컬렉션 (Concurrent Collection)

정리 예정.<br>

<br>

## 2) 동기화 장치

동기화 장치는 스레드가 다른 스레드를 기다릴 수 있게 해준다. 동기화 장치는 CountDownLatch, Semaphore, CyclicBarrier, Exchanger 가 있다.<br>

이 4가지 중 가장 자주 쓰이는 동기화 장치는 CountDownLatch, Semaphore다.<br>

CyclicBarrier, Exchanger 는 조금 덜 쓰인다.<br>

책에서는 CountDownLatch 만을 언급하고 있다. Semaphore 예제는 따로 문서로 정리해둘 예정이다.(에버노트에 정리되어 있다.)

<br>

### CountDownLatch

스탑워치 기능을 하는 클래스인데, 이 기능을 wait, notify 만으로 구현하기에는 난해하고 지저분하고, 가독성이 떨어지고, 유지보수가 어려운 코드가 만들어지게 된다.<br>

CountDownLatch 의 생성자는 하나만 존재하며 아래와 같은 모양이다. CountDownLatch 는 일회성 장벽이다. 하나 이상의 스레드가 또 다른 하나 이상의 스레드 작업이 끝날 때까지 기다리는 역할을 수행한다.<br><br>

```java
class CountDownLatch{
    public CountDownLatch(int count){
        // ... 
    }
}
```

<br>

생성자의 매개변수 count 는 CountDownLatch 클래스 내의 countDown 메서드를 몇번 호출할지를 결정하는 변수다.<br>

지정된 count 만큼 countDown 메서드가 호출되면 대기중인 스레드 들을 깨우게 된다.<br>

즉, 지정된 count 에 지정한 수는 실행자 또는 동작을 몇 개나 동시에 수행할 수 있는 지를 지정하는 변수를 의미한다. <br>

타이머 스레드가 시계를 시작하기 전에 모든 worker 스레드(작업자 스레드)는 동작을 수행할 준비를 마친다. 모든 worker 스레드가 준비가 되면 시작 방아쇠(트리거)를 트리거 시켜서 모든 worker 스레드(작업자 스레드)가 일을 시작하게끔 한다.<br>

<br>

### 동시시간 카운팅 예제

아래 코드는 카운트다운 래치를 3개 사용한다. ready 래치는 작업자 스레드 들이 준비가 완료됐음을 타이머 스레드 들에 통지할 때 사용한다. wait, notify 를 사용하면 지저분한 코드가 된다. 하지만 CountDownLatch 를 사용하면 직관적으로 구현할 수 있다.

```java
import java.util.concurrent.*;

// Simple framework for timing concurrent execution 327
public class ConcurrentTimer {
    private ConcurrentTimer() { } // Noninstantiable

    public static long time(Executor executor, int concurrency,
                            Runnable action) throws InterruptedException {
        CountDownLatch ready = new CountDownLatch(concurrency);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done  = new CountDownLatch(concurrency);

        for (int i = 0; i < concurrency; i++) {
            executor.execute(() -> {
                ready.countDown(); // Tell timer we're ready
                try {
                    start.await(); // Wait till peers are ready
                    action.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();  // Tell timer we're done
                }
            });
        }

        ready.await();     // Wait for all workers to be ready
        long startNanos = System.nanoTime();
        start.countDown(); // And they're off!
        done.await();      // Wait for all workers to finish
        return System.nanoTime() - startNanos;
    }
}
```

위 코드에서는 카운트다운 래치를 3개 사용한다.

- CountDownLatch ready
  - 작업자 스레드 들이 준비가 완료됐음을 타이머 스레드에 통지하기 위해 사용
  - 준비가 완료됐음을 알리고 나면, start 래치가 열리기를 기다린다.
- CountDownLatch start
  - 마지막 작업자 스레드가 ready.countDown 을 호출하면 기다리던 작업자 스레드들을 깨운다.
  - 이후 세번째 래치, done 이 열리기를 기다린다.
- CountDownLatch done
  - 마미막 작업자 스레드가 동작을 마무리하고 done.countDown 을 호출할 때 열린다.
  - 타이머 스레드는 done 래치가 열리자마자 깨어나서 종료시각을 기록한다.



executor

asdfasdf



## 3) wait, notify 를 사용해야 할 경우

새로운 코드를 작성하는 것이 아닌, 레거시 코드를 유지보수해야 하는 경우가 있다. 이때 wait, notify 로 구현되어 있는 경우가 많다. 

