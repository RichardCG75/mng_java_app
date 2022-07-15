package app.unedl.utils;

import java.util.ResourceBundle;
import java.util.function.Supplier;

//Define un Logger especifico para esta aplicacion
public class AppLogger implements System.Logger {

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public boolean isLoggable(Level level) {
        return true;
    }

    @Override
    public void log(Level level, String msg) {
        System.Logger.super.log(level, msg);
    }

    @Override
    public void log(Level level, Supplier<String> msgSupplier) {
        System.Logger.super.log(level, msgSupplier);
    }

    @Override
    public void log(Level level, Object obj) {
        System.Logger.super.log(level, obj);
    }

    @Override
    public void log(Level level, String msg, Throwable thrown) {
        System.Logger.super.log(level, msg, thrown);
    }

    @Override
    public void log(Level level, Supplier<String> msgSupplier, Throwable thrown) {
        System.Logger.super.log(level, msgSupplier, thrown);
    }

    @Override
    public void log(Level level, String format, Object... params) {
        System.Logger.super.log(level, format, params);
    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Throwable throwable) {

    }

    @Override
    public void log(Level level, ResourceBundle resourceBundle, String s, Object... objects) {

    }

    public static final System.Logger LOGGER = System.getLogger(AppLogger.class.getName());
}