package kr.nanoit.old.decoder;

import kr.nanoit.model.message.ReceiveMessageDto;

public interface PacketMakeable {
    byte[] send_ack(ReceiveMessageDto receiveMessageDto);

    byte[] login_ack(String result);

    byte[] report();

}
