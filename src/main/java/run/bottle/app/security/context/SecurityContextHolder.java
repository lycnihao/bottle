package run.bottle.app.security.context;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import run.bottle.app.model.support.UserDetail;

/**
 * 用户安全管理器
 */
public class SecurityContextHolder {

    private final static ThreadLocal<UserDetail> CONTEXT_HOLDER = new ThreadLocal<>();

    private SecurityContextHolder() {
    }

    /**
     * Gets context.
     *
     * @return security context
     */
    public static UserDetail getContext() {
        // Get from thread local
        UserDetail context = CONTEXT_HOLDER.get();
        if (context == null) {
            // If no context is available now then create an empty context
            context = createEmptyContext();
            // Set to thread local
            CONTEXT_HOLDER.set(context);
        }

        return context;
    }

    /**
     * Sets security context.
     *
     * @param context security context
     */
    public static void setContext(@Nullable UserDetail context) {
        CONTEXT_HOLDER.set(context);
    }

    /**
     * Clears context.
     */
    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * Creates an empty security context.
     *
     * @return an empty security context
     */
    @NonNull
    private static UserDetail createEmptyContext() {
        return new UserDetail();
    }
}
