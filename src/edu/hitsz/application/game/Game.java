package edu.hitsz.application.game;

import edu.hitsz.DAOP.Book;
import edu.hitsz.DAOP.Rank;
import edu.hitsz.aircraft.*;
import edu.hitsz.application.HeroController;
import edu.hitsz.application.ImageManager;
import edu.hitsz.application.Main;
import edu.hitsz.bullet.BaseBullet;
import edu.hitsz.basic.AbstractFlyingObject;
import edu.hitsz.factory.enemyFactory.*;
import edu.hitsz.factory.propsFactory.GeneratePropsFactory;
import edu.hitsz.observer.MyObserver;
import edu.hitsz.props.AbstractProps;
import edu.hitsz.props.BombProp;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import thread.MusicThread;
import ui.RankTabel;
import ui.StartMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.concurrent.*;

/**
 * 游戏主面板，游戏启动
 *
 * @author hitsz
 */
public abstract class Game extends JPanel {
    protected int mode = 1;
    private int backGroundTop = 0;

    /**
     * Scheduled 线程池，用于任务调度
     */
    private final ScheduledExecutorService executorService;

    /**
     * 时间间隔(ms)，控制刷新频率
     */
    private int timeInterval = 40;

    private final HeroAircraft heroAircraft;
    private EnemyFactory enemyFactory;
    private final List<AbstractAircraft> enemyAircrafts;
    private final List<BaseBullet> heroBullets;
    private final List<BaseBullet> enemyBullets;
    private final List<AbstractProps> props;

    /**
     * 精英机概率
     * 三种道具出现的概率
     */
    private GeneratePropsFactory whichPropsFactory =new GeneratePropsFactory();

    /**
     * 屏幕中出现的敌机最大数量
     */
    protected int enemyMaxNumber = 5;
    /**
     * Boss机数量
     */
    private int bossNum=0;
    public static int lastBossNum=0;
    private int N = 0; //boss出现次数
    /**
     * 当前得分
     */
    private int score = 0;
    /**
     * 当前时刻
     */
    private int time = 0;

    /**
     * 周期（ms)
     * 指示子弹的发射、敌机的产生频率
     */
    private int cycleDuration = 600;
    private int cycleTime = 0;
    /**
     * 背景音乐线程
     */
    private String bgm = "src/videos/bgm.wav";
    private String bossBgm = "src/videos/bgm_boss.wav";
    public static MusicThread musicThread=new MusicThread("src/videos/bgm.wav",true);

    /**
     * 游戏结束标志
     */
    private boolean gameOverFlag = false;

    public Game() {
        heroAircraft = HeroAircraft.getHeroAircraft();

        enemyAircrafts = new LinkedList<>();
        heroBullets = new LinkedList<>();
        enemyBullets = new LinkedList<>();
        props = new LinkedList<>();

        /**
         * Scheduled 线程池，用于定时任务调度
         * 关于alibaba code guide：可命名的 ThreadFactory 一般需要第三方包
         * apache 第三方库： org.apache.commons.lang3.concurrent.BasicThreadFactory
         */
        this.executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("game-action-%d").daemon(true).build());

        //启动英雄机鼠标监听
        new HeroController(this, heroAircraft);

    }

    /**
     * 游戏启动入口，执行游戏逻辑
     */
    public void action() {
        setMode();
        // 定时任务：绘制、对象产生、碰撞判定、击毁及结束判定
        musicThread.start();
        Runnable task = () -> {
            time += timeInterval;
            if(bossNum==1&& bossNum!=lastBossNum){
                musicThread.setStop();
                musicThread=new MusicThread(bossBgm,true);
                musicThread.start();
            }
            if(bossNum==0&& bossNum!=lastBossNum){
                musicThread.setStop();
                musicThread=new MusicThread(bgm,true);
                musicThread.start();
            }
            lastBossNum=bossNum;

            time += timeInterval;

            // 周期性执行（控制频率）
            if (timeCountAndNewCycleJudge()) {
                System.out.println(time);
                // 新敌机产生
                RandomEnemyFactory randomEnemyFactory=new RandomEnemyFactory(mode);
                Random random = new Random();
                if(score%(400-(mode-2)*100)<=100 && score>=300 && mode!=1){
                    if(bossNum==0){
                        enemyAircrafts.add(randomEnemyFactory.creatBossEnemy(N));
                        bossNum=1;
                        N+=1;
                    }
                }
                if (enemyAircrafts.size() < enemyMaxNumber) {
                    enemyAircrafts.add(randomEnemyFactory.creatEnemyAircraft());
                }
                // 飞机射出子弹
                shootAction();
            }

            // 子弹移动
            bulletsMoveAction();

            // 飞机移动
            aircraftsMoveAction();

            //道具移动
            propsMoveAction();

            // 撞击检测
            crashCheckAction();

            // 后处理
            postProcessAction();

            //每个时刻重绘界面
            repaint();

            // 游戏结束检查英雄机是否存活
            if (heroAircraft.getHp() <= 0) {
                // 游戏结束
                executorService.shutdown();
                gameOverFlag = true;
                System.out.println("Game Over!");
                musicThread.setStop();
                new MusicThread("src/videos/game_over.wav").start();

                RankTabel rankTabel = new RankTabel();
                Main.cardPanel.add(rankTabel.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                String name = JOptionPane.showInputDialog(String.format("游戏结束，你的得分为%d\n请输入名字记录得分：", score));
                if(Objects.equals(name,"")){
                    name = "testUserName";
                }
                if(name!=null){
                    Book book=new Book(name,score,formatter.format(calendar.getTime()));
                    Rank rank=new Rank();
                    rank.readBookDaoIml(StartMenu.getFile_name());
                    rank.addBook(book);
                    rank.writeBookDaoIml(StartMenu.getFile_name());
                }
                RankTabel rankTabel2 = new RankTabel();
                Main.cardPanel.add(rankTabel2.getMainPanel());
                Main.cardLayout.last(Main.cardPanel);
            }

        };

        /**
         * 以固定延迟时间进行执行
         * 本次任务执行完成后，需要延迟设定的延迟时间，才会执行新的任务
         */
        executorService.scheduleWithFixedDelay(task, timeInterval, timeInterval, TimeUnit.MILLISECONDS);

    }

    public abstract void setMode();

    //***********************
    //      Action 各部分
    //***********************

    private boolean timeCountAndNewCycleJudge() {
        cycleTime += timeInterval;
        if (cycleTime >= cycleDuration && cycleTime - timeInterval < cycleTime) {
            // 跨越到新的周期
            cycleTime %= cycleDuration;
            return true;
        } else {
            return false;
        }
    }

    private void shootAction() {
        // 敌机射击
        for(AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyBullets.addAll(enemyAircraft.shoot());
        }
        // 英雄射击
        heroBullets.addAll(heroAircraft.shoot());
    }

    private void bulletsMoveAction() {
        for (BaseBullet bullet : heroBullets) {
            bullet.forward();
        }
        for (BaseBullet bullet : enemyBullets) {
            bullet.forward();
        }
    }

    private void aircraftsMoveAction() {
        for (AbstractAircraft enemyAircraft : enemyAircrafts) {
            enemyAircraft.forward();
        }
    }
    private void propsMoveAction(){
        for (AbstractProps props : props){
            props.forward();
        }
    }

    /**
     * 碰撞检测：
     * 1. 敌机攻击英雄
     * 2. 英雄攻击/撞击敌机
     * 3. 英雄获得补给
     */
    private void crashCheckAction() {
        // 敌机子弹攻击英雄
        for (BaseBullet bullet : enemyBullets){
            if (bullet.notValid()){
                continue;
            }
            if (heroAircraft.notValid()) {
                // 已被其他子弹击毁的英雄机，不再检测
                // 避免多个子弹重复击毁英雄机的判定
                continue;
            }
            if (heroAircraft.crash(bullet)){
                //英雄机撞上子弹
                heroAircraft.decreaseHp(bullet.getPower());
                //英雄机损失生命值
                bullet.vanish();
                //子弹消失
            }
        }
        // 英雄子弹攻击敌机
        for (BaseBullet bullet : heroBullets) {
            if (bullet.notValid()) {
                continue;
            }
            for (AbstractAircraft enemyAircraft : enemyAircrafts) {
                if (enemyAircraft.notValid()) {
                    // 已被其他子弹击毁的敌机，不再检测
                    // 避免多个子弹重复击毁同一敌机的判定
                    continue;
                }
                if (enemyAircraft.crash(bullet)) {
                    // 敌机撞击到英雄机子弹
                    // 敌机损失一定生命值
                    new MusicThread("src/videos/bullet_hit.wav").start();
                    enemyAircraft.decreaseHp(bullet.getPower());
                    bullet.vanish();
                }
                // 英雄机 与 敌机 相撞，均损毁
                if (enemyAircraft.crash(heroAircraft) || heroAircraft.crash(enemyAircraft)) {
                    enemyAircraft.vanish();
                    heroAircraft.decreaseHp(Integer.MAX_VALUE);
                }
            }
        }

        // 我方获得道具，道具生效
        for (AbstractProps prop : props){
            if(heroAircraft.crash(prop)|| prop.crash(heroAircraft)) {
                new MusicThread("src/videos/get_supply.wav").start();
                if (prop instanceof BombProp) {
                    List<MyObserver> observers = new ArrayList<>();
                    for (MyObserver observer : enemyAircrafts) {
                        observers.add(observer);
                    }
                    for (MyObserver observer : enemyBullets) {
                        observers.add(observer);
                    }
                ((BombProp) prop).setEnemyAircrafts(observers);
                prop.Effect(heroAircraft);
                prop.vanish();
                }
                else {
                prop.Effect(heroAircraft);
                prop.vanish();
                }
            }
        }
        for(AbstractAircraft enemyAircraft:enemyAircrafts){
            if (enemyAircraft.notValid()) {
                // 获得分数，产生道具补给
                if(enemyAircraft instanceof EliteEnemy){
                    Random random = new Random();
                    if(random.nextInt(100)<90){  //击败精英机后有百分之90的概率产生道具
                        props.add(whichPropsFactory.createProps(enemyAircraft.getLocationX(),
                                enemyAircraft.getLocationY(),0,10));
                    }
                }
                else if(enemyAircraft instanceof BossEnemy){
                    for (int i = 0; i < 3; i++) {
                        props.add(whichPropsFactory.createProps(enemyAircraft.getLocationX()+i*10-10,
                                enemyAircraft.getLocationY(),0,5));
                    }
                    bossNum=0;
                }
                score += 10;
            }
        }

    }

    /**
     * 后处理：
     * 1. 删除无效的子弹
     * 2. 删除无效的敌机
     * <p>
     * 无效的原因可能是撞击或者飞出边界
     */
    private void postProcessAction() {
        enemyBullets.removeIf(AbstractFlyingObject::notValid);
        heroBullets.removeIf(AbstractFlyingObject::notValid);
        enemyAircrafts.removeIf(AbstractFlyingObject::notValid);
        props.removeIf(AbstractFlyingObject::notValid);
    }


    //***********************
    //      Paint 各部分
    //***********************

    /**
     * 重写paint方法
     * 通过重复调用paint方法，实现游戏动画
     *
     * @param  g
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        // 绘制背景,图片滚动
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop - Main.WINDOW_HEIGHT, null);
        g.drawImage(ImageManager.BACKGROUND_IMAGE, 0, this.backGroundTop, null);
        this.backGroundTop += 1;
        if (this.backGroundTop == Main.WINDOW_HEIGHT) {
            this.backGroundTop = 0;
        }

        // 先绘制子弹，后绘制飞机
        // 这样子弹显示在飞机的下层
        paintImageWithPositionRevised(g, enemyBullets);
        paintImageWithPositionRevised(g, heroBullets);
        paintImageWithPositionRevised(g, props);
        paintImageWithPositionRevised(g, enemyAircrafts);

        g.drawImage(ImageManager.HERO_IMAGE, heroAircraft.getLocationX() - ImageManager.HERO_IMAGE.getWidth() / 2,
                heroAircraft.getLocationY() - ImageManager.HERO_IMAGE.getHeight() / 2, null);

        //绘制得分和生命值
        paintScoreAndLife(g);

    }

    private void paintImageWithPositionRevised(Graphics g, List<? extends AbstractFlyingObject> objects) {
        if (objects.size() == 0) {
            return;
        }

        for (AbstractFlyingObject object : objects) {
            BufferedImage image = object.getImage();
            assert image != null : objects.getClass().getName() + " has no image! ";
            g.drawImage(image, object.getLocationX() - image.getWidth() / 2,
                    object.getLocationY() - image.getHeight() / 2, null);
        }
    }

    private void paintScoreAndLife(Graphics g) {
        int x = 10;
        int y = 25;
        g.setColor(new Color(16711680));
        g.setFont(new Font("SansSerif", Font.BOLD, 22));
        g.drawString("SCORE:" + this.score, x, y);
        y = y + 20;
        g.drawString("LIFE:" + this.heroAircraft.getHp(), x, y);
    }


}
