package se.proxus.kanon.event4j;

import se.proxus.kanon.event4j.asm.ASMEventExecutorFactory;

import java.lang.reflect.Method;
import java.util.Optional;

@FunctionalInterface
public interface EventExecutor<E, L> {
    void fire(L listener, E event);

    static <E, L> EventExecutor<E, L> empty() {
        return (listener, event) -> {
            throw new UnsupportedOperationException("Empty event executor");
        };
    }

    @FunctionalInterface
    interface Factory {
        Factory METHOD_HANDLE_LISTENER_FACTORY = MHEventExecutor::new;
        Factory REFLECTION_LISTENER_FACTORY = ReflectionEventExecutor::new;
        @SuppressWarnings("unchecked") // generics r fun
        Optional<Factory> ASM_LISTENER_FACTORY = (Optional) ASMEventExecutorFactory.INSTANCE;

        <E, L> EventExecutor<E, L> create(EventBus<E, L> eventBus, Method method);

        static Factory getDefault() {
            return ASM_LISTENER_FACTORY.orElse(METHOD_HANDLE_LISTENER_FACTORY);
        }
    }
}
