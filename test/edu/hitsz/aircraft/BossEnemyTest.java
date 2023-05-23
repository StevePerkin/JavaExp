package edu.hitsz.aircraft;

import edu.hitsz.bullet.BaseBullet;
import org.junit.jupiter.api.*;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BossEnemyTest {
    private BossEnemy bossEnemy;
    private final List<BaseBullet> enemyBullets=new LinkedList<>();;
    @BeforeAll
    static void beforeAll() {
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        bossEnemy = new BossEnemy(100,30,5,5,300);
    }
    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        bossEnemy = null;
        enemyBullets.clear();
    }
    @DisplayName("Test decreaseHp method")
    @Test
    void decreaseHp() {
        System.out.println("**--- Test decreaseHp method executed ---**");
        bossEnemy.decreaseHp(30);
        assertEquals(270,bossEnemy.getHp());

    }
    @DisplayName("Test shoot method")
    @Test
    void shoot() {
        System.out.println("**--- Test shoot method executed ---**");
        enemyBullets.addAll(bossEnemy.shoot());
        assertEquals(3,enemyBullets.size());
        for (int i = 0; i < 3; i++){
            assertEquals(80+20*i,enemyBullets.get(i).getLocationX());
        }
    }
}