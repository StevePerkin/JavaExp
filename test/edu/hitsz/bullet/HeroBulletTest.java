package edu.hitsz.bullet;

import edu.hitsz.aircraft.MobEnemy;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HeroBulletTest {

    private HeroBullet heroBullet;

    private MobEnemy mobEnemy;

    @BeforeAll
    static void beforeAll(){
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        heroBullet=new HeroBullet(100,20,0,5,30);
        mobEnemy=new MobEnemy(100,20,0,5,30);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        heroBullet = null;
        mobEnemy = null;
    }
    @DisplayName("Test getPower method")
    @Test
    void getPower() {
        System.out.println("**--- Test getPower method executed ---**");
        assertEquals(30,heroBullet.getPower());
    }
    @DisplayName("Test crash method")
    @Test
    void crash() {
        System.out.println("**--- Test crash method executed ---**");
        assertTrue(heroBullet.crash(mobEnemy));
    }
}