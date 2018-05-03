/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.platformer.extension;

import com.mygdx.platformer.LevelScreen;
import java.io.File;

/**
 *
 * @author Julia
 */
public class ModuleEngine {
    public static Module _execute = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args,LevelScreen ls) {
        //String modulePath = "build//classes//moduletest//test";//args[0];
        String modulePath = args[0];
        System.out.print("Module path: ");
        System.out.println(modulePath);
        /**
         * Создаем загрузчик модулей.
         */
        ModuleLoader loader = new ModuleLoader(modulePath, ClassLoader.getSystemClassLoader());

        /**
         * Загружаем и исполняем каждый модуль.
         */
        String module = modulePath;
        if (module.endsWith(".class")) {
            try {
                String moduleName = module.split("modules\\\\")[1];
               moduleName = moduleName.split("\\.class")[0];
                if (moduleName.equals("Module") == false) {
                    System.out.print("Executing loading module: ");
                    System.out.println(moduleName);

                    Class clazz = loader.loadClass( "com.mygdx.platformer.extension.modules." + moduleName);
                    _execute = (Module) clazz.newInstance();
                    _execute.load(ls);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


      }
    
    
}
