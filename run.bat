@echo off
setlocal

echo ============================================
echo   Basket Egg - Build ^& Run
echo ============================================
echo.

if not exist out mkdir out

set SRC=src
set OUT=out

echo [1/2] Compiling...

javac -d %OUT% ^
  %SRC%\config\GameConfig.java ^
  %SRC%\entities\Renderable.java ^
  %SRC%\entities\Updatable.java ^
  %SRC%\entities\Collidable.java ^
  %SRC%\entities\GameObject.java ^
  %SRC%\entities\EggState.java ^
  %SRC%\entities\Egg.java ^
  %SRC%\entities\Basket.java ^
  %SRC%\physics\CollisionResult.java ^
  %SRC%\physics\CollisionDetector.java ^
  %SRC%\physics\PhysicsEngine.java ^
  %SRC%\game\GameState.java ^
  %SRC%\game\ScoreManager.java ^
  %SRC%\game\InputHandler.java ^
  %SRC%\game\GameLoop.java ^
  %SRC%\game\GamePanel.java ^
  %SRC%\game\Game.java ^
  %SRC%\ui\ScoreDisplay.java ^
  %SRC%\ui\GameRenderer.java ^
  %SRC%\audio\AudioManager.java ^
  %SRC%\BasketEgg.java

if %ERRORLEVEL% neq 0 (
    echo.
    echo [ERROR] Compilation failed. See messages above.
    pause
    exit /b 1
)

echo [2/2] Launching game...
echo.

java -cp %OUT% BasketEgg

endlocal
