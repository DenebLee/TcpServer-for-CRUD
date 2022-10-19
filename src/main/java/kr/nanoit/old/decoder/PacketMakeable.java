package kr.nanoit.old.decoder;

import kr.nanoit.model.message.MessageDto;

public interface PacketMakeable {
    byte[] send_ack(MessageDto messageDto);

    byte[] login_ack(String result);

    byte[] report();

}
