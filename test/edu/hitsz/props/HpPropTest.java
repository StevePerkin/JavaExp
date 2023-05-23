package edu.hitsz.props;

import edu.hitsz.aircraft.HeroAircraft;
import edu.hitsz.bullet.HeroBullet;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class HpPropTest {
    private HpProp hpProp;
    private HeroAircraft heroAircraft;
    @BeforeAll
    static void beforeAll(){
        System.out.println("**--- Executed once before all test methods in this class ---**");
    }
    @BeforeEach
    void setUp() {
        System.out.println("**--- Executed before each test method in this class ---**");
        hpProp = new HpProp(100,20,0,5);
        heroAircraft = HeroAircraft.getHeroAircraft();
        heroAircraft.decreaseHp(300);
    }

    @AfterEach
    void tearDown() {
        System.out.println("**--- Executed after each test method in this class ---**");
        hpProp=null;
        heroAircraft=null;
    }
    @DisplayName("Test forward method")
    @Test
    void forward() {
        System.out.println("**--- Test forward method executed ---**");
        hpProp.forward();
        assertEquals(25,hpProp.getLocationY());
    }
    @DisplayName("Test active method")
    @Test
    void effect() {
        System.out.println("**--- Test active method executed ---**");
        hpProp.Effect(heroAircraft);
        assertEquals(800,heroAircraft.getHp());
    }
}