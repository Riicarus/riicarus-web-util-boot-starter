package com.riicarus.webutil.command;

import com.riicarus.comandante.main.CommandLauncher;
import org.springframework.stereotype.Component;

/**
 * [FEATURE INFO]<br/>
 * Web后端指令输入输出
 *
 * @author Riicarus
 * @create 2022-11-9 19:54
 * @since 1.0.0
 */
@Component
public class WebCommandIOHandler {

    public void dispatchCommand(String command) {
        CommandLauncher.dispatchToCache(command);
    }

}
