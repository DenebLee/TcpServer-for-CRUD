package kr.nanoit.old.woker;


import kr.nanoit.model.message.MessageDto;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.old.util.Crypt;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Receive implements Runnable {

    //    private SocketUtil socketUtil;
    private Crypt crypt;
    private MessageDto messageDto;

//    public Receive(SocketUtil socketUtil) {
//        this.socketUtil = socketUtil;
//    }

    @Override
    public void run() {
        log.info("RECEIVE SERVER START");
        MessageType messageType;
        String messageKey;
//        try {
//            while (true) {
////                byte[] receiveByte = socketUtil.receiveByte();
//                if (receiveByte != null) {
//                    switch (getPacketType(receiveByte)) {
//                        case LOGIN:
////                            messageType = MessageType.fromPropertyName();
//                            break;
//                        case SEND:
//
//                            break;
//                        case REPORT:
//
//                            break;
//                        default:
//
//                            log.info("[ERROR] Not Found Packet Type, ID : {}", socketUtil.getId());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            log.warn("Receive Server Error");
//            e.printStackTrace();
//        }
//    }

//    private PacketType getPacketType(byte[] receive) throws Exception {
//        return PacketType.fromPropertyName(new String(receive, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE_INDEX, LengthHeader.COMMON_LENGTH_HEADER_PACKET_TYPE).trim());
//    }
    }
}
