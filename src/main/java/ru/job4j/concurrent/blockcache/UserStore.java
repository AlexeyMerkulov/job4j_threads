package ru.job4j.concurrent.blockcache;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStore {
    @GuardedBy("this")
    private final Map<Integer, User> store = new HashMap<>();

    public synchronized boolean add(User user) {
        return store.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return store.replace(user.getId(), store.get(user.getId()), user);
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User source = store.get(fromId);
        User receiver = store.get(toId);
        if (source != null && receiver != null && source.getAmount() >= amount) {
            source.setAmount(source.getAmount() - amount);
            receiver.setAmount(receiver.getAmount() + amount);
            rsl = true;
        }
        return rsl;
    }
}
