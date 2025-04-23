# 스프링 부트 @Async를 이용한 비동기처리 예제 프로젝트

## 테스트 내용 요약
- 2022 M1 Ultra 맥 스튜디오에서 로컬 마리아 DB를 사용했고, Runtime.getRuntime().availableProcessors() 명령어로 알아낸 가용 프로세서 개수는 20개였습니다.
- 스레드풀 개수 20개, 최대 스레드풀 개수 20개, 큐 길이 10,050으로 셋팅한 결과, 3000 번의 DB 입력 요청을 @Async로 처리하는 것에 250~290밀리초가 걸렸습니다.
- 같은 조건에서 @Async없이 작업하면 약 1900~2000 밀리초가 걸립니다.
- 스프링 프로젝트에서 @Async를 사용하기 위해서는 부트 앱의 메인 메서드에서 @EnableAsync를 붙여줘야 합니다.
- @Async가 붙은 메서드의 리턴 타입이 void일 경우, 본 리포지토리의 config 패키지의 내용을 참고하여 커스텀 예외 핸들러를 구현해야 합니다. 본 리포지토리에서 테스트 해본 결과 예외를 잘 잡아냈습니다.
- @Async 메서드의 예외처리에 대한 자세한 내용은 [Baeldung의 관련 포스팅](https://www.baeldung.com/spring-async)을 참고하시면 됩니다.

## 중요한 주의사항!!
- [@Async가 붙어 있는 서비스 클래스 메서드를 다수 포함하는 상위 서비스 메서드가 존재한다고 가정해보겠습니다. 이때, UpperNonAsyncService가 subAsyncService1과 subAsyncService2를 순서대로 호출한다 해도 실제로는 여러 스레드가 이 두 개의 메서드를 동시에 처리하기 때문에 처리의 순서가 1 다음 2.. 이런 식으로 보장이 되지 않습니다.]() 이는 외래키 참조관계를 가지는 댓글과 대댓글을 삭제할 때 외래키 참조 에러(== ConstraintViolationException)를 발생시킬 가능성이 매우 큽니다. [따라서 @Async가 붙어 있는 메서드 여러 개를 한 메서드 내부에서 호출하는 일이 없도록 해야 합니다.]()
- [@Async 는 컨트롤러 클래스의 메서드가 아니라 서비스 클래스 내의 메서드에 붙여줘야 효과를 제대로 볼 수 있습니다.]()
- 동일한 조건에서 컨트롤러 측 메서드에 @Async를 붙여주자 실행시간이 961 밀리초로 증가하면서 사실상 @Async의 효과를 누리지 못한 것을 확인할 수 있습니다.
- 좌측 하단에서 수치를 확인할 수 있습니다.
![82AC91D1-E546-4BC8-939B-E393966D9FC7](https://user-images.githubusercontent.com/99060708/213896497-5b419a30-dd88-4a14-81e1-cc6f774d2d47.jpeg)

## 테스트 결과 이미지
- 비동기 테스트 이미지(2025/4/23에 실시 - 좌측 하단에서 시간 확인 가능)
<img width="1596" alt="Image" src="https://github.com/user-attachments/assets/d22b3362-8ac6-414b-9376-fe13f9b53cda" />

- 동기 테스트 이미지(2025/4/23에 실시 - 좌측 하단에서 시간 확인 가능)
<img width="1588" alt="Image" src="https://github.com/user-attachments/assets/bc0120a8-befc-4a28-a510-31301a54a831" />
