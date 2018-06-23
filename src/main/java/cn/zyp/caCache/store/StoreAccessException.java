package cn.zyp.caCache.store;

public class StoreAccessException extends Exception {
    public StoreAccessException() {
        super();
    }

    public StoreAccessException(String message) {
        super(message);
    }

    public StoreAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreAccessException(Throwable cause) {
        super(cause);
    }

    protected StoreAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
