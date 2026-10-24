package testing;

import com.nafiz.protocol.ProtocolEnvelope;
import com.nafiz.protocol.ProtocolError;
import com.nafiz.protocol.ProtocolMessageType;

public final class ProtocolTest {

    private ProtocolTest() {
    }

    public static void main(String[] args) {
        ProtocolEnvelope<String> request = ProtocolEnvelope.request(
                ProtocolMessageType.MESSAGE_SEND, "request-1", "hello");
        require(request.version() == 1, "protocol version should be one");
        require(request.type() == ProtocolMessageType.MESSAGE_SEND,
                "message type should be preserved");
        require(!request.isError(), "request should not be an error");

        ProtocolEnvelope<Void> failure = ProtocolEnvelope.failure(
                ProtocolMessageType.MESSAGE_FAILED, "request-1",
                new ProtocolError("MESSAGE_REJECTED", "Message rejected", false));
        require(failure.isError(), "failure envelope should contain an error");
        require(!failure.error().retryable(), "error retryability should be preserved");

        expectFailure(() -> ProtocolEnvelope.request(
                ProtocolMessageType.MESSAGE_SEND, " ", "hello"));
        expectFailure(() -> new ProtocolError(" ", "message", false));
        System.out.println("Protocol tests passed.");
    }

    private static void expectFailure(Runnable action) {
        try {
            action.run();
            throw new AssertionError("invalid protocol input should fail");
        } catch (IllegalArgumentException expected) {
        }
    }

    private static void require(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
