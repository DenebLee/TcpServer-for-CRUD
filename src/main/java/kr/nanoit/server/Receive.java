package kr.nanoit.server;

import kr.nanoit.controller.SocketUtil;
import kr.nanoit.decoder.DecoderLogin;
import kr.nanoit.exception.DecryptException;
import kr.nanoit.exception.ReceiveException;
import kr.nanoit.main.Main;
import kr.nanoit.model.message.LoginMessage;
import kr.nanoit.util.Crypt;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

@Slf4j
public class Receive implements Runnable {

    private SocketUtil socketUtil;
    private Crypt crypt;
    private DecoderLogin decoderLogin;

    public Receive(SocketUtil socketUtil) {
        this.socketUtil = socketUtil;
        crypt = new Crypt();

    }

    @Override
    public void run() {
        try {
            while (true) {
                byte[] receiveByte = socketUtil.receiveByte();
                if (receiveByte != null) {
                    switch (socketUtil.getPacketType(receiveByte)) {
                        case LOGIN:
                            break;

                        case SEND:
                            break;

                        case REPORT:
                            break;
                    }
                }
            }
        } catch (Exception e) {
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
}
