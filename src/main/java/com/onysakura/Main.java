package com.onysakura;

import com.alibaba.fastjson.parser.ParserConfig;
import com.onysakura.fx.App;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) throws Exception {
        init();
        Application.launch(App.class);
    }

    private static void init() throws Exception {
        // fastjson safe mode
        ParserConfig.getGlobalInstance().setSafeMode(true);
    }
}
