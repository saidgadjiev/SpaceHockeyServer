package gameMechanics;

import org.eclipse.jetty.websocket.api.BatchMode;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.WriteCallback;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.Future;

/**
 * Created by said on 31.10.15.
 */
public class FakeRemoteEndPoint implements RemoteEndpoint {
    private String data;

    @Override
    public void sendBytes(ByteBuffer data) throws IOException {

    }

    @Override
    public Future<Void> sendBytesByFuture(ByteBuffer data) {
        return null;
    }

    @Override
    public void sendBytes(ByteBuffer data, WriteCallback callback) {

    }

    @Override
    public void sendPartialBytes(ByteBuffer fragment, boolean isLast) throws IOException {

    }

    @Override
    public void sendPartialString(String fragment, boolean isLast) throws IOException {

    }

    @Override
    public void sendPing(ByteBuffer applicationData) throws IOException {

    }

    @Override
    public void sendPong(ByteBuffer applicationData) throws IOException {

    }

    @Override
    public void sendString(String text) throws IOException {
        data = text;
    }

    @Override
    public Future<Void> sendStringByFuture(String text) {
        return null;
    }

    @Override
    public void sendString(String text, WriteCallback callback) {

    }

    @Override
    public BatchMode getBatchMode() {
        return null;
    }

    @Override
    public void flush() throws IOException {

    }

    public String getData() {
        return data;
    }
}
