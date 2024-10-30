# Music player

**Music player** is a feature-rich music player built with [Jetpack Compose](https://developer.android.com/compose) and [Kotlin](https://kotlinlang.org/), designed to play audio files stored locally on Android devices. With an intuitive UI, it’s all about making your music collection look and sound great!

### Features
- 🎨 Dynamic UI based on album artwork colors
- 📂 Easy browsing of local audio files
- 🎶 Play, pause, skip tracks with smooth transitions
- 📜 Display track information (title, artist, album)
- 🔊 Volume control and media notifications

## Screenshots
![Music player Home](link-to-home-screen-image)
![Now Playing Screen](link-to-now-playing-image)

### Tech Stack
- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - For creating declarative UI
- **Media3** - For media playback and notifications
- **Palette** - For generating colors based on album art

### Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/zaarirmoh/Music-player.git
   ```
2. Open in Android Studio and build the project.
3. Run the app on an Android device or emulator with audio files.

### Usage
- Open the app to see your local music library.
- Tap on any song to start playback.
- Use the control buttons for play/pause and skipping tracks.

### Architecture
- **MVVM**: To separate UI, business logic, and data
- **ViewModel**: For managing UI-related data
- **Repository Pattern**: To handle data interactions

### Future Improvements
- 🌐 Integrate online streaming and lyrics display
- 💾 Add playlist management
- 🎛️ Equalizer settings for enhanced sound control
