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
        boolean rsl = false;
        if (!store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean update(User user) {
        boolean rsl = false;
        if (store.containsKey(user.getId())) {
            store.put(user.getId(), user);
            rsl = true;
        }
        return rsl;
    }

    public synchronized boolean delete(User user) {
        return store.remove(user.getId()) != null;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User source = store.get(fromId);
        User receiver = store.get(toId);
        if (source != null && receiver != null) {
            if (source.getAmount() < amount) {
                throw new IllegalArgumentException("Недостаточно средств на балансе");
            } else {
                source.setAmount(source.getAmount() - amount);
                receiver.setAmount(receiver.getAmount() + amount);
                rsl = true;
            }
        }
        return rsl;
    }
}
