package message.service;

import kr.nanoit.service.ReceivedMessageServiceImpl;

import java.io.IOException;

class ReceivedMessageServiceImplTest extends TestServiceSetUp {
    private ReceivedMessageServiceImpl receivedMessageService;

    public ReceivedMessageServiceImplTest(String type) throws IOException {
        super("RECEIVE");
    }

}
