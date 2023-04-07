import java.util.HashMap;
import java.util.Map;

/**
 * Class that facilitates storing and managing multiple clients.
 */
public class ClientCollection {
    private Map<Integer, Client> clients = new HashMap<>();

    /**
     * Add a client to the collection. Assert that there are
     * no other clients with the same id already in the collection.
     * @param client to be added in the collection.
     */
    void add(Client client) {
        if (clients.containsKey(client.getId())) {
            throw new IllegalArgumentException("There is already a client with ID: " + client.getId());
        }
        clients.put(client.getId(), client);
    }

    /**
     * Retrieve a client from the collection, keyed by the client id.
     * @param id of the requested client.
     * @return the client with the corresponding id.
     */
    Client get(int id) {
        return clients.get(id);
    }

    /**
     * Remove a client from the collection.
     * @param id of the client to be removed.
     */
    void remove(int id) {
        clients.remove(id);
    }
}
