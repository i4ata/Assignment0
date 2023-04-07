import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClientCollection {
    @Test
    public void testAdd() {
        ClientCollection clientCollection = new ClientCollection();
        Client client = new Client(1, "0%", "1%", "2%");
        clientCollection.add(client);
        assertSame(clientCollection.get(client.getId()), client);
    }

    @Test
    public void testAddConflictingIDs() {
        ClientCollection clientCollection = new ClientCollection();
        clientCollection.add(new Client(1, "10%", "20%", "30%"));
        assertThrows(IllegalArgumentException.class, () -> clientCollection.add(new Client(1, "30%", "20%", "10%")));
    }

    @Test
    public void testGet() {
        ClientCollection clientCollection = new ClientCollection();
        Client client = new Client(1, "0%", "1%", "2%");
        clientCollection.add(client);
        assertSame(client, clientCollection.get(client.getId()));
    }

    @Test
    public void testRemove() {
        ClientCollection clientCollection = new ClientCollection();
        Client client = new Client(1, "0%", "1%", "2%");
        clientCollection.add(client);
        assertNotNull(clientCollection.get(client.getId()));
        clientCollection.remove(client.getId());
        assertNull(clientCollection.get(client.getId()));
    }

}
