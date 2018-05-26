/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.badlogic.gdx.ai.msg.Telegram;
import com.mygdx.platformer.LevelScreen;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.extension.modules.ITest;
import com.mygdx.platformer.extension.modules.RunBot;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.wind.Wind;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Julia
 */
public class RunBotTest {
    
    public RunBotTest() {
    }
    
    /**
     * Test of isJumpIfWind method
     */
    @org.junit.Test
    public void testIsJumpIfWind_calm() {
        System.out.println("testIsJumpIfWind_calm");        
        RunBot bot = new RunBot();
        PlatformManGame game = new PlatformManGame();
        LevelScreen ls = new LevelScreen(game,true,true);
        Wind wind = ls.getWind();
        wind.setSpeed(0);
        ls.setSantaState(SantaState.IDLE);
        bot.load(ls);
        boolean expResult = true;
        boolean result = bot.isJumpIfWind();
        assertEquals(expResult, result);
    }
    @org.junit.Test
    public void testIsJumpIfWind_breeze() {
        System.out.println("testIsJumpIfWind_breeze");        
        RunBot bot = new RunBot();
        PlatformManGame game = new PlatformManGame();
        LevelScreen ls = new LevelScreen(game,true,true);
        Wind wind = ls.getWind();
        wind.setSpeed(3);
        ls.setSantaState(SantaState.IDLE);
        bot.load(ls);
        boolean expResult = true;
        boolean result = bot.isJumpIfWind();
        assertEquals(expResult, result);
    }
    @org.junit.Test
    public void testIsJumpIfWind_high_not_jump() {
        System.out.println("testIsJumpIfWind_high_not_jump");        
        RunBot bot = new RunBot();
        PlatformManGame game = new PlatformManGame();
        LevelScreen ls = new LevelScreen(game,true,true);
        Wind wind = ls.getWind();
        wind.setSpeed(5);
        ls.setSantaState(SantaState.LEFT);
        bot.load(ls);
        boolean expResult = false;
        boolean result = bot.isJumpIfWind();
        assertEquals(expResult, result);
    }
    @org.junit.Test
    public void testIsJumpIfWind_storm_jump() {
        System.out.println("testIsJumpIfWind_storm_jump");        
        RunBot bot = new RunBot();
        PlatformManGame game = new PlatformManGame();
        LevelScreen ls = new LevelScreen(game,true,true);
        Wind wind = ls.getWind();
        wind.setSpeed(7);
        ls.setSantaState(SantaState.RIGHT);
        bot.load(ls);
        boolean expResult = true;
        boolean result = bot.isJumpIfWind();
        assertEquals(expResult, result);
    }
}
