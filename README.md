# Android Music Player 🎵

Android 기반 음악 재생 애플리케이션입니다. 간단하고 직관적인 UI로 음악을 재생하고 가사를 확인할 수 있습니다.

## 주요 기능 ✨

- **음악 재생**: MP3 형식의 음악 파일 재생
- **재생 컨트롤**: 재생/일시정지, 다음 곡, 이전 곡 기능
- **SeekBar**: 음악 진행 상황을 확인하고 원하는 위치로 이동 가능
- **앨범 아트**: 각 곡의 앨범 커버 이미지 표시
- **가사 보기**: Bottom Sheet를 통한 가사 확인 기능
- **곡 정보**: 곡 제목과 아티스트 정보 표시
- **재생 시간**: 현재 재생 시간과 전체 재생 시간 표시

## 기술 스택 🛠️

- **언어**: Java
- **최소 SDK**: 24 (Android 7.0 Nougat)
- **타겟 SDK**: 34 (Android 14)
- **빌드 도구**: Gradle (Kotlin DSL)

### 주요 라이브러리

- `androidx.appcompat:appcompat:1.7.0`
- `com.google.android.material:material:1.12.0`
- `androidx.constraintlayout:constraintlayout:2.1.4`
- `com.google.code.gson:gson:2.10.1`

## 프로젝트 구조 📁

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/a2023201023/
│   │   │   ├── MainActivity.java      # 메인 액티비티
│   │   │   └── Song.java              # 노래 데이터 모델
│   │   ├── res/
│   │   │   ├── layout/
│   │   │   │   ├── activity_main.xml        # 메인 화면 레이아웃
│   │   │   │   └── bottom_sheet_layout.xml  # 가사 표시 레이아웃
│   │   │   ├── drawable/                    # 앨범 이미지 등
│   │   │   └── raw/                         # 음악 파일 (MP3)
│   │   └── AndroidManifest.xml
│   └── test/
└── build.gradle.kts
```

## 설치 및 실행 방법 🚀

### 사전 요구사항

- Android Studio (최신 버전 권장)
- JDK 8 이상
- Android SDK (API Level 24 이상)

### 설치 단계

1. **저장소 클론**
   ```bash
   git clone https://github.com/yongqyu49/Android_Music_Player.git
   cd Android_Music_Player
   ```

2. **Android Studio에서 프로젝트 열기**
   - Android Studio 실행
   - "Open an Existing Project" 선택
   - 클론한 프로젝트 폴더 선택

3. **Gradle 동기화**
   - Android Studio가 자동으로 Gradle 파일을 동기화합니다
   - 필요한 경우 "Sync Project with Gradle Files" 클릭

4. **앱 실행**
   - Android 기기를 USB로 연결하거나 에뮬레이터 실행
   - Run 버튼(▶️) 클릭 또는 `Shift + F10` 단축키 사용

### 빌드

```bash
# Debug 빌드
./gradlew assembleDebug

# Release 빌드
./gradlew assembleRelease
```

## 사용 방법 📱

1. **앱 실행**: 앱을 실행하면 첫 번째 곡의 앨범 커버와 정보가 표시됩니다
2. **음악 재생**: 중앙의 재생 버튼을 눌러 음악을 재생합니다
3. **곡 변경**: 좌우 버튼으로 이전/다음 곡으로 이동할 수 있습니다
4. **SeekBar 조작**: SeekBar를 드래그하여 원하는 재생 위치로 이동합니다
5. **가사 보기**: 하단의 가사 버튼을 눌러 현재 곡의 가사를 확인합니다
6. **앱 종료**: 우측 상단의 종료 버튼으로 앱을 종료합니다

## 주요 클래스 설명 📚

### MainActivity.java
메인 화면을 관리하는 액티비티로 다음 기능을 포함합니다:
- MediaPlayer를 이용한 음악 재생 제어
- SeekBar를 통한 재생 위치 조절
- Bottom Sheet를 통한 가사 표시
- UI 업데이트 및 이벤트 처리

### Song.java
노래 정보를 담는 데이터 모델 클래스:
- `music`: 음악 파일 이름
- `title`: 곡 제목
- `artist`: 아티스트명
- `albumImage`: 앨범 커버 이미지 파일명
- `lyrics`: 가사 내용

## 개발 환경 🔧

- **IDE**: Android Studio Hedgehog (2023.1.1) 이상
- **Gradle**: 8.2.0
- **Compile SDK**: 34
- **Build Tools**: 최신 버전

## 라이선스 📄

이 프로젝트는 교육 목적으로 제작되었습니다.

## 기여 🤝

이슈나 개선 사항이 있다면 자유롭게 Issue를 등록하거나 Pull Request를 보내주세요.

## 연락처 📧

프로젝트 관리자: [@yongqyu49](https://github.com/yongqyu49)

---

Made with ❤️ for Android Music Lovers
