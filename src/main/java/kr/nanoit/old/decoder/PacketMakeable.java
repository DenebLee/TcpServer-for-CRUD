package kr.nanoit.old.decoder;

import kr.nanoit.model.message.ReceiveMessage;

public interface PacketMakeable {
    byte[] send_ack(ReceiveMessage receiveMessage);

    byte[] login_ack(String result);

    byte[] report();

}
