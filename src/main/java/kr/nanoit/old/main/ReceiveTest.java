package kr.nanoit.old.main;

import kr.nanoit.model.message.ReceiveMessageDto;
import kr.nanoit.model.message.MessageType;
import kr.nanoit.repository.ReceivedMessageRepository;
import kr.nanoit.extension.PathUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Properties;

public class ReceiveTest {
    public static void main(String[] args) throws IOException {
        long id;
        Properties properties = new Properties();
        properties.load(Files.newInputStream(Paths.get(PathUtils.dataBaseProperties())));
        ReceivedMessageRepository receivedMessageRepository = ReceivedMessageRepository.createMybatis(properties);
        try {
            ReceiveMessageDto receiveMessageDto = new ReceiveMessageDto(0, MessageType.SMS, new Timestamp(System.currentTimeMillis()), 2, "0104444", "04404040", "테스트");
            int ok = receivedMessageRepository.save(receiveMessageDto);
            if (ok > 0) {
                id = receiveMessageDto.getId();
                System.out.println(id);
                System.out.println(receivedMessageRepository.findById(id));
                int update_ok = receivedMessageRepository.update(new ReceiveMessageDto(id, MessageType.NONE, new Timestamp(System.currentTimeMillis()), 5, "11111111111", "2222222222", "안녕하세요 업데이트 된 값입니다 확인용으로 넣었습니다 핳핳하ㅏㅎ 날씨가 좋네요 "));
                if (update_ok > 0) {
                    System.out.println(receivedMessageRepository.findById(id));
                }
            }
            receivedMessageRepository.deleteAll();
            ok = receivedMessageRepository.save(receiveMessageDto);
            System.out.println(ok);
            id = receiveMessageDto.getId();
            if (id == 1) {
                System.out.println("테이블 값 전체 삭제 및 pk값 1부터 다시 생성될 수 있도록 초기화 완료");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
