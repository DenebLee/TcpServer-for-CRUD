package kr.nanoit.server;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.decoder.DecoderLogin;
import kr.nanoit.exception.DecryptException;
import kr.nanoit.main.Main;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.model.message.Message;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.util.ClientCrypt;
import kr.nanoit.util.Crypt;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.sql.Timestamp;

@Slf4j
public class Receive implements Runnable {

    private SocketUtil socketUtil;
    private Crypt crypt;
    private DecoderLogin decoderLogin;

    public Receive(SocketUtil socketUtil) {
        this.socketUtil = socketUtil;
        decoderLogin = new DecoderLogin();
        crypt = new Crypt();
    }

    @Override
    public void run() {
        log.info("RECEIVE SERVER START");
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case LOGIN:
                            decodeLogin(receiveByte);
                            break;
                        case SEND:
                            decodeLogin(receiveByte);
                            break;
                        case REPORT:
                            decodeLogin(receiveByte);
                            break;
                        default:

                            log.info("[ERROR] Not Found Packet Type, ID : {}", socketUtil.getId());
                    }
                }
            }
        } catch (Exception e) {
            log.warn("Receive Server Error");
            e.printStackTrace();
        }
    }

    public void decodeLogin(byte[] receive) throws DecryptException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setProtocol("LOGIN_ACK");
        loginMessage.setId(decoderLogin.id(receive));
        loginMessage.setPassword(decoderLogin.password(receive));

        socketUtil.setPacket_login_id(loginMessage.getId());
        socketUtil.setPacket_login_password(loginMessage.getPassword());

        if (Main.verificationMap.containsKey(decoderLogin.id(receive))) {
            crypt.cryptInit(Main.verificationMap.get(loginMessage.getId()).getEncryptKey());
            if (Main.verificationMap.get(decoderLogin.id(receive)).getPassword().contains(new String(crypt.deCrypt(decoderLogin.password(receive))))) {
                log.info("[응답] [로그인 실패] ID : {} PW : {}", loginMessage.getId(), loginMessage.getPassword());
            } else {
                log.info("[응답] [로그인 실패] ID : {} PW : {}", loginMessage.getId(), loginMessage.getPassword());
            }
        } else {
            log.info("[응답] [로그인 실패] ID : {} PW : {}", loginMessage.getId(), loginMessage.getPassword());
        }
        socketUtil.getQueue_for_Send().offer(loginMessage);
    }

    public void decoderSend(byte[] receive) {
        Message message = new Message();
        message.setProtocol("LOGIN_ACK");
        message.setSend_time(new Timestamp(System.currentTimeMillis()));
        message.setSender("이정섭");
        message.setSendstate("success");
        message.setContent("안녕하세요 테스트입니다");

        log.info("[받음] [요청 제출] [메세지 타입 : {}] SEND_DATE : {} SENDER : {} STATE : {} CONTENT : {}  ", MessageType.BASIC, message.getSend_time(), message.getSender(), message.getSendstate(), message.getContent());

        socketUtil.getQueue_for_Send().offer(message); // Client 요청 데이터 대한 응답 sendQueue에 쌓기

    }
}
