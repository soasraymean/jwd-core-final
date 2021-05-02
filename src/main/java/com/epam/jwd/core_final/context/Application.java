package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.Menu;
import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidInputException;
import com.epam.jwd.core_final.exception.InvalidStateException;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;
import me.tongfei.progressbar.ProgressBarStyle;

public interface Application {

    static void start() throws InvalidStateException, InvalidInputException {
//        final Supplier<ApplicationContext> applicationContextSupplier = ()->{
//            return null;
//        }; // todo

        final NassaContext nassaContext = NassaContext.getInstance();
        showProgress("Loading");
        System.out.println("\t!!!Space Rangers 3!!!(beta)\n");
        nassaContext.init();
        ApplicationMenu menu = new Menu();
        while (true) {
            menu.printAvailableOptions();
        }

    }

    static void showProgress(String name) {
        ProgressBarBuilder pbb = new ProgressBarBuilder()
                .setInitialMax(100)
                .setStyle(ProgressBarStyle.ASCII)
                .setUpdateIntervalMillis((int) (15 + Math.random() * 30))
                .setTaskName(name)
                .setMaxRenderedLength(name.length() + 60);
        ProgressBar pb = pbb.build();
        while (pb.getCurrent() != 100) {
            try {
                pb.step();
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        pb.close();
    }


}
