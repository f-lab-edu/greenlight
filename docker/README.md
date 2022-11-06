### TO-DO
- 임시로 md 작성했으니 시간있을때 md 정리
- doc 네이밍은 무엇으로 할까?? document디렉토리를 만들까?
- docker를 처음해봐서 compose라는 directory에 모아서 하고싶음 가능하겠지??

### Docker-compose version
- [docker-compose](https://docs.docker.com/compose/compose-file/compose-versioning/)
- docker engine version에 따른 docker compose

### 환경변수 { .evn or export }
- docker compose convert 환경변수 적용 확인

### restart [옵션]
- no : 프로세스 종료되어도 컨테이너 재시작 않음 (default)
- on-failure[:max-retries] : container가 정상적으로 종료되지 않은경우(exit codr 0이아님)에만 재시작 시킴 max-retries도 함께 주면 재시작 최대 시도 횟수를 지정가능
- always : container 항상 재시작 exit code 상관없이 항상 재시작한다.
- unless-stopped : container를 stop 전까지 항상 재시작한다.

### 멸령어
```shell
> docker-compose -d up
> docker-compose down
```
