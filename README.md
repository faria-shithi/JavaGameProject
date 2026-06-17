# 🥚 Basket Egg
 
> Guide your egg into the baskets. Don't crack it.
> *A Java Swing desktop game with physics, scrolling worlds, and one fragile protagonist.*
 
![Java](https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk&logoColor=white)
![Platform](https://img.shields.io/badge/Platform-Windows-blue?style=flat-square&logo=windows&logoColor=white)
![Engine](https://img.shields.io/badge/Engine-Java%20Swing-yellow?style=flat-square)
 
---

## 🚀 Quick Start
 
> **Requirement:** Java 17 or newer must be installed and available on the `PATH`.
 
```powershell
git clone git@github.com:faria-shithi/JavaGameProject.git
cd JavaGameProject
.\run.bat
```
 
<details>
<summary>🔧 Manual compile & run</summary>
```powershell
javac -d out src\config\GameConfig.java src\entities\*.java src\physics\*.java src\game\*.java src\ui\*.java src\audio\*.java src\BasketEgg.java
 
java -cp out BasketEgg
```
 
</details>
---
 
## 🎮 Controls
 
| Key | Action |
|:---:|---|
| `Space` / `W` / `↑` | Jump |
| `A` / `←` | Move left on the ground |
| `D` / `→` | Move right on the ground |
| `R` | Restart after game over |
 
---
 
## 🏆 Gameplay
 
1. The egg starts on the ground — jump into moving baskets to score points.
2. Before your first basket landing, falling back to the ground is **safe**.
3. After your first landing, hitting the ground **cracks the egg and ends the game**.
4. The world scrolls upward as you climb. New baskets spawn above the screen.
5. Every successful basket landing adds to your score. How high can you go?
> **Pro tip:** Time your jumps carefully — baskets move horizontally and speed up as you progress.
 
---
 
## 📁 Project Structure
 
```
BasketEgg/
├── assets/
├── out/
├── src/
│   ├── BasketEgg.java           ← Main launcher
│   ├── audio/
│   ├── config/
│   │   └── GameConfig.java      ← All tuning values live here
│   ├── entities/
│   │   ├── Egg.java             ← Egg state, movement, cracking, drawing
│   │   └── Basket.java          ← Basket movement, collision, drawing
│   ├── game/
│   │   ├── Game.java            ← Creates the game window
│   │   └── GamePanel.java       ← Game loop, collision, scrolling, restart
│   ├── physics/
│   └── ui/
│       └── GameRenderer.java    ← Scene rendering (background, score, HUD)
├── run.bat
└── README.md
```
 
---
 
## 🗂️ Key Files
 
| File | Purpose |
|---|---|
| `src/BasketEgg.java` | Main launcher |
| `src/game/Game.java` | Creates the game window |
| `src/game/GamePanel.java` | Owns game objects, updates, collision flow, scrolling, and restart |
| `src/entities/Egg.java` | Egg state, movement, landing, cracking, and drawing |
| `src/entities/Basket.java` | Basket movement, collision geometry, and drawing |
| `src/config/GameConfig.java` | Window size, physics, scoring, basket speed, and tuning values |
| `src/ui/GameRenderer.java` | Background, ground, score, basket layering, egg rendering, and game-over screen |
 
---
 
## ⚙️ Tuning
 
All gameplay values are in `src/config/GameConfig.java`:
 
```java
public static final double GRAVITY          = 1400.0;  // downward pull
public static final double JUMP_FORCE       = -750.0;  // initial jump velocity
public static final double MAX_FALL_SPEED   =  900.0;  // terminal velocity cap
public static final double EGG_MOVE_SPEED   =  220.0;  // horizontal movement
public static final double BASKET_MIN_SPEED =   90.0;  // slowest basket
public static final double BASKET_MAX_SPEED =  200.0;  // fastest basket
public static final int    SCORE_PER_LAND   =      1;  // points per basket
```
 
| What to change | Where to look |
|---|---|
| Egg appearance | `src/entities/Egg.java` |
| Basket appearance | `src/entities/Basket.java` |
| Scene / background / HUD | `src/ui/GameRenderer.java` |
| Initial basket layout | `createInitialBaskets()` in `src/game/GamePanel.java` |
| Physics & speed | `src/config/GameConfig.java` |
 
---
 
*Made with ☕ and Java — good luck, don't crack!*
