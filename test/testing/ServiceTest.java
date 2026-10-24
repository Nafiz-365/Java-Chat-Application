package testing;

import com.nafiz.auth.AuthService;
import com.nafiz.chat.ChatMessage;
import com.nafiz.chat.LocalChatTransport;
import com.nafiz.chat.LocalConversationService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class ServiceTest {

    private ServiceTest() {
    }

    public static void main(String[] args) {
        testAuthentication();
        testConversationHistory();
        testLocalTransport();
        System.out.println("Service tests passed.");
    }

    private static void testLocalTransport() {
        LocalChatTransport transport = new LocalChatTransport(new LocalConversationService());
        CountDownLatch received = new CountDownLatch(1);
        transport.setListener(new com.nafiz.chat.ChatTransport.Listener() {
            @Override
            public void onConnected() {
            }

            @Override
            public void onMessage(ChatMessage message) {
                require(message.content().equals("Transport message"),
                        "transport should deliver message content");
                received.countDown();
            }

            @Override
            public void onError(String message) {
                throw new AssertionError(message);
            }

            @Override
            public void onDisconnected() {
            }
        });
        transport.connect();
        transport.send("conversation", "nafiz", "Transport message");
        try {
            require(received.await(2, TimeUnit.SECONDS), "transport should deliver asynchronously");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new AssertionError("transport test interrupted", exception);
        } finally {
            transport.disconnect();
        }

        CountDownLatch reconnected = new CountDownLatch(1);
        transport.setListener(new com.nafiz.chat.ChatTransport.Listener() {
            @Override
            public void onConnected() {
            }

            @Override
            public void onMessage(ChatMessage message) {
                reconnected.countDown();
            }

            @Override
            public void onError(String message) {
                throw new AssertionError(message);
            }

            @Override
            public void onDisconnected() {
            }
        });
        transport.connect();
        transport.send("conversation", "nafiz", "Reconnect message");
        try {
            require(reconnected.await(2, TimeUnit.SECONDS),
                    "transport should send after reconnecting");
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new AssertionError("reconnect test interrupted", exception);
        } finally {
            transport.disconnect();
        }
    }

    private static void testAuthentication() {
        String username = "test_" + System.nanoTime();
        AuthService service = new AuthService();
        AuthService.AuthResult registered = service.register(username, "correct-password".toCharArray());
        require(registered.successful(), "registration should succeed");
        require(service.authenticate(username, "correct-password".toCharArray()).successful(),
                "correct password should authenticate");
        require(!service.authenticate(username, "wrong-password".toCharArray()).successful(),
                "wrong password should fail");
    }

    private static void testConversationHistory() {
        LocalConversationService service = new LocalConversationService();
        ChatMessage first = service.send("conversation", "nafiz", "Hello");
        service.send("other", "nafiz", "Not included");
        require(service.history("conversation").size() == 1, "history should be scoped");
        require(first.content().equals("Hello"), "message content should be preserved");
        require(!first.id().isBlank(), "message should have an ID");
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
