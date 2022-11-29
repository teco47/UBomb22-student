/*
 * Copyright (c) 2020. Laurent Réveillère
 */

package fr.ubx.poo.ubomb.engine;

import fr.ubx.poo.ubomb.game.Direction;
import fr.ubx.poo.ubomb.game.Game;
import fr.ubx.poo.ubomb.game.Position;
import fr.ubx.poo.ubomb.go.GameObject;
import fr.ubx.poo.ubomb.go.character.Bomb;
import fr.ubx.poo.ubomb.go.character.Monster;
import fr.ubx.poo.ubomb.go.character.Player;
import fr.ubx.poo.ubomb.go.character.Princess;
import fr.ubx.poo.ubomb.go.decor.Decor;
import fr.ubx.poo.ubomb.go.decor.Door;
import fr.ubx.poo.ubomb.launcher.World;
import fr.ubx.poo.ubomb.view.*;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;


public final class GameEngine {

    private static AnimationTimer gameLoop;
    private final World world;
    private final List<Game> levels;
    private Game currentGame;

    private Player player;
    private Princess princess;
    private Set<Monster> monsters;
    private Set<Bomb> bombs;
    private final List<Sprite> sprites = new LinkedList<>();
    private final Set<Sprite> cleanUpSprites = new HashSet<>();
    private final Stage stage;
    private StatusBar statusBar;
    private Pane layer;
    private Input input;

    public GameEngine(Game game, final Stage stage, World world) {
        this.stage = stage;
        this.world = world;
        this.levels = new ArrayList<>();
        levels.add(game);
        setup(0);
    }
    public GameEngine(List<Game> levels, final Stage stage, World world) {
        this.stage = stage;
        this.world = world;
        this.levels = levels;
        setup(0);
    }

    private void setup(int indexLevel){
        currentGame = levels.get(indexLevel);
        currentGame.started = true;
        sprites.clear();
        this.player = currentGame.player();
        this.princess = currentGame.princess();
        this.monsters = currentGame.monster();
        this.bombs = currentGame.bombs();
        System.out.println("CHangement de monde");
        initialize();
        buildAndSetGameLoop();
    }


    private void initialize() {
        Group root = new Group();
        layer = new Pane();

        int height = currentGame.grid().height();
        int width = currentGame.grid().width();
        int sceneWidth = width * ImageResource.size;
        int sceneHeight = height * ImageResource.size;
        Scene scene = new Scene(root, sceneWidth, sceneHeight + StatusBar.height);
        scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

        stage.setScene(scene);
        stage.setResizable(true);
        stage.sizeToScene();
        stage.show();

        input = new Input(scene);
        root.getChildren().add(layer);
        statusBar = new StatusBar(root, sceneWidth, sceneHeight, currentGame);

        // Create decors sprite
        for (var decor : currentGame.grid().values()) {
            sprites.add(SpriteFactory.create(layer, decor));
            decor.setModified(true);
        }

        sprites.add(new SpritePlayer(layer, player));
        if(princess!=null){
            sprites.add(new SpritePrincess(layer,princess));
            System.out.println("Princess position : "+princess.getPosition());
        }
        else {
            System.out.println("pas de princesse");
        }

        for(Monster m : monsters){
            sprites.add((new SpriteMonster(layer,m)));
        }
    }

    void buildAndSetGameLoop() {
        gameLoop = new AnimationTimer() {
            public void handle(long now) {
                checkTravel();

                // Check keyboard actions
                processInput(now);

                // Do actions
                update(now);
                //createNewBombs(now);
                checkCollision(now);
                checkExplosions();

                checkTrigger();

                // Graphic update
                cleanupSprites();
                render();
                statusBar.update(currentGame);
            }
        };
    }

    private void checkTrigger() {
    }

    private void checkExplosions() {
        // Check explosions of bombs
    }

    private void animateExplosion(Position src, Position dst, Game game) {
        if(game.equals(currentGame)){
            ImageView explosion = new ImageView(ImageResource.EXPLOSION.getImage());
            TranslateTransition tt = new TranslateTransition(Duration.millis(200), explosion);
            tt.setFromX(src.x() * Sprite.size);
            tt.setFromY(src.y() * Sprite.size);
            tt.setToX(dst.x() * Sprite.size);
            tt.setToY(dst.y() * Sprite.size);
            tt.setOnFinished(e -> {
                layer.getChildren().remove(explosion);
            });
            layer.getChildren().add(explosion);
            tt.play();
        }
    }

    private void createNewBombs(/*long now*/) {
        if( !bombs.stream().anyMatch(bb -> bb.getPosition()==player.getPosition())  &&
                currentGame.bombParameter().retrieveBomb(-1)) {
            Bomb bomb = new Bomb(currentGame, player.getPosition(),currentGame.bombParameter().getRange());
            bombs.add(bomb);
            sprites.add(new SpriteBomb(layer,bomb));
        }else{
        }
    }

    private void checkCollision(long now) {
        // Check a collision between a monster and the player
    }
    
    private void checkTravel(){
        for (Door d : currentGame.grid().doorSet()) {
            if(player.getPosition().equals(d.getPosition())){
                gameLoop.stop();
                world.setPlayerLevel(world.getPlayerLevel() + (d.upStair() ? +1 : -1));
                Game ancientGame = currentGame;
                setup(world.getPlayerLevel());
                changeGame(ancientGame);
                gameLoop.start();
            }
        }
    }

    private void changeGame(Game ancientGame){
        ancientGame.player().setPosition(world.getConfig().playerPosition());
        currentGame.player().setLives(ancientGame.player().getLives());
        currentGame.setKey(ancientGame.key());
        currentGame.setBombParameter(ancientGame.bombParameter());
        for (Monster m: monsters) {
            m.setTimerMove((int)(10000/ (world.getConfig().monsterVelocity() + 5 * world.getPlayerLevel())));
        }
    }

    private void processInput(long now) {
        if (input.isExit()) {
            gameLoop.stop();
            Platform.exit();
            System.exit(0);
        } else if (input.isMoveDown()) {
            player.requestMove(Direction.DOWN);
        } else if (input.isMoveLeft()) {
            player.requestMove(Direction.LEFT);
        } else if (input.isMoveRight()) {
            player.requestMove(Direction.RIGHT);
        } else if (input.isMoveUp()) {
            player.requestMove(Direction.UP);
        } else if (input.isBomb()) {
            createNewBombs();
        } else if (input.isKey()){
            player.openDoor();
        }
        input.clear();
    }

    private void showMessage(String msg, Color color) {
        Text waitingForKey = new Text(msg);
        waitingForKey.setTextAlignment(TextAlignment.CENTER);
        waitingForKey.setFont(new Font(60));
        waitingForKey.setFill(color);
        StackPane root = new StackPane();
        root.getChildren().add(waitingForKey);
        Scene scene = new Scene(root, 400, 200, Color.WHITE);
        stage.setScene(scene);
        input = new Input(scene);
        stage.show();
        new AnimationTimer() {
            public void handle(long now) {
                processInput(now);
            }
        }.start();
    }

    private int damage(Position src , Direction direction, int range, Game game){
        boolean blocked= false;
        int i=-1;
        while (!blocked && i<range){
            i++;
            Set<GameObject> list  = game.getGameObjects(direction.nextPosition(src,i));
            list.add(game.grid().get(direction.nextPosition(src,i)));
            list.remove(null);
            for(GameObject iter : list){
                if (iter.explode()){
                    blocked = true;
                }
            }
        }
        return i;
    }

    private void explosion (Bomb bomb){
        animateExplosion(bomb.getPosition(),Direction.DOWN.nextPosition(bomb.getPosition(),damage(bomb.getPosition(),Direction.DOWN,bomb.getRange(),bomb.game)),bomb.game);
        animateExplosion(bomb.getPosition(),Direction.UP.nextPosition(bomb.getPosition(),damage(bomb.getPosition(),Direction.UP,bomb.getRange(),bomb.game)),bomb.game);
        animateExplosion(bomb.getPosition(),Direction.RIGHT.nextPosition(bomb.getPosition(),damage(bomb.getPosition(),Direction.RIGHT,bomb.getRange(),bomb.game)),bomb.game);
        animateExplosion(bomb.getPosition(),Direction.LEFT.nextPosition(bomb.getPosition(),damage(bomb.getPosition(),Direction.LEFT,bomb.getRange(),bomb.game)),bomb.game);

        bomb.game.bombParameter().retrieveBomb(1);
    }

    private void update(long now) {
        player.update(now);

        for (Game g : levels) {
            if(g.started){
                Iterator monsterIt = g.monster().iterator();
                while (monsterIt.hasNext()){
                    Monster m = (Monster) monsterIt.next();
                    if(m.isDeleted()){monsterIt.remove();}
                    else{m.update(now);}
                }

                Iterator bombIt = g.bombs().iterator();
                while (bombIt.hasNext()){
                    Bomb b = (Bomb) bombIt.next();
                    if(b.isDeleted()){
                        explosion(b);
                        bombIt.remove();
                    }else{
                        b.update(now);
                    }
                }
            }
        }

        if (player.getLives() == 0) {
            gameLoop.stop();
            showMessage("Perdu!", Color.RED);
        }
        if (currentGame.getOnPrincess()) {
            gameLoop.stop();
            showMessage("Victoire", Color.RED);
        }
    }

    public void cleanupSprites() {
        sprites.forEach(sprite -> {
            if (sprite.getGameObject().isDeleted()) {
                currentGame.grid().remove(sprite.getPosition());
                cleanUpSprites.add(sprite);
            }
        });
        cleanUpSprites.forEach(Sprite::remove);
        sprites.removeAll(cleanUpSprites);
        cleanUpSprites.clear();
    }

    private void render() {
        sprites.forEach(Sprite::render);
    }

    public void start() {
        gameLoop.start();
    }
}