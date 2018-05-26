package com.mygdx.platformer.extension.modules;

import com.mygdx.platformer.LevelScreen;
import com.mygdx.platformer.PlatformManGame;
import com.mygdx.platformer.extension.IBot;
import com.mygdx.platformer.extension.Module;
import com.mygdx.platformer.fsm.SantaState;
import com.mygdx.platformer.wind.WindType;

public class RunBot implements Module {
    private IBot bot = null;
    @Override
    public void load(LevelScreen ls) {
        System.out.println("Module " + this.getClass() + " loading ...");
        bot = ls;
    }

    @Override
    public int run(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " running ...");        
        bot.runRight();
        System.out.println("go");
        return Module.EXIT_SUCCESS;
    }
    
        
    public boolean isJumpIfWind() {
        boolean isJumpIfDirectionWind;
        //если слабый ветер, то можно прыгать в любом случае
        if(bot.getWindType() == WindType.calm || bot.getWindType() == WindType.breeze){
            return true;
        }
        //если сильный ветер, то нужно смотреть 
        isJumpIfDirectionWind = ((bot.isInStateSanta(SantaState.RIGHT) || bot.isInStateSanta(SantaState.IDLE)) && bot.getWindSpeed()>0) || 
                                ((bot.isInStateSanta(SantaState.LEFT) || bot.isInStateSanta(SantaState.IDLE)) && bot.getWindSpeed()<0);
        
        return isJumpIfDirectionWind;
    }

    @Override
    public void unload(PlatformManGame game) {
        System.out.println("Module " + this.getClass() + " inloading ...");
    }
}
